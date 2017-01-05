package com.sendsafely.utils;

import java.io.*;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.security.*;
import java.util.Date;
import java.util.Iterator;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;
import javax.xml.bind.DatatypeConverter;

import com.sendsafely.exceptions.PublicKeyEncryptionFailedException;
import org.bouncycastle.bcpg.ArmoredOutputStream;
import org.bouncycastle.crypto.AsymmetricBlockCipher;
import org.bouncycastle.crypto.AsymmetricCipherKeyPair;
import org.bouncycastle.crypto.PBEParametersGenerator;
import org.bouncycastle.crypto.digests.SHA256Digest;
import org.bouncycastle.crypto.engines.RSAEngine;
import org.bouncycastle.crypto.generators.PKCS5S2ParametersGenerator;
import org.bouncycastle.crypto.params.AsymmetricKeyParameter;
import org.bouncycastle.crypto.params.KeyParameter;
import org.bouncycastle.crypto.util.PrivateKeyFactory;
import org.bouncycastle.crypto.util.PublicKeyFactory;
import org.bouncycastle.openpgp.*;
import org.bouncycastle.openpgp.PGPCompressedData;
import org.bouncycastle.openpgp.PGPEncryptedDataGenerator;
import org.bouncycastle.openpgp.PGPEncryptedDataList;
import org.bouncycastle.openpgp.PGPException;
import org.bouncycastle.openpgp.PGPLiteralData;
import org.bouncycastle.openpgp.PGPLiteralDataGenerator;
import org.bouncycastle.openpgp.PGPObjectFactory;
import org.bouncycastle.openpgp.PGPPBEEncryptedData;
import org.bouncycastle.openpgp.PGPUtil;
import org.bouncycastle.openpgp.operator.PBEDataDecryptorFactory;
import org.bouncycastle.openpgp.operator.PGPKeyEncryptionMethodGenerator;
import org.bouncycastle.openpgp.operator.PublicKeyKeyEncryptionMethodGenerator;
import org.bouncycastle.openpgp.operator.bc.*;
import org.bouncycastle.openpgp.operator.jcajce.JcePBESecretKeyDecryptorBuilder;
import org.bouncycastle.openpgp.operator.jcajce.JcePublicKeyDataDecryptorFactoryBuilder;
import org.bouncycastle.util.encoders.Base64;
import org.bouncycastle.util.encoders.Hex;

import com.sendsafely.exceptions.MessageException;
import com.sendsafely.exceptions.PublicKeyDecryptionFailedException;
import com.sendsafely.exceptions.SignatureCreationFailedException;
import com.sendsafely.exceptions.TokenGenerationFailedException;
import org.bouncycastle.util.io.Streams;

public class CryptoUtil 
{

	public static String createChecksum(String keyCode, String packageCode)
	{
		return PBKDF2(keyCode, packageCode, 1024);
	}
	
	public static String Sign(String key, String dataToSign) throws SignatureCreationFailedException
	{
		SecretKeySpec signingKey = new SecretKeySpec(key.getBytes(), "HmacSHA256");

		Mac mac;
		try {
			mac = Mac.getInstance("HmacSHA256");
			mac.init(signingKey);
		} catch (InvalidKeyException e) {
			throw new SignatureCreationFailedException(e);
		} catch (NoSuchAlgorithmException e) {
			throw new SignatureCreationFailedException(e);
		}

		byte[] rawHmac = mac.doFinal(dataToSign.getBytes());
		
		// Hex encode and return
		return DatatypeConverter.printHexBinary(rawHmac);
	}
	
	public static String GenerateKeyCode() throws TokenGenerationFailedException
	{
		byte[] randomBytes = new byte[32];
		
		SecureRandom sr;
		try {
			sr = SecureRandom.getInstance("SHA1PRNG", "SUN");
			sr.nextBytes(randomBytes);
		} catch (NoSuchAlgorithmException e) {
			throw new TokenGenerationFailedException(e);
		} catch (NoSuchProviderException e) {
			throw new TokenGenerationFailedException(e);
		}
		
		return DatatypeConverter.printBase64Binary(randomBytes);
	}
	
	public static String PBKDF2(String token, String salt, int iterations)
	{
		PBEParametersGenerator generator = new PKCS5S2ParametersGenerator(new SHA256Digest());
		
	    generator.init(PBEParametersGenerator.PKCS5PasswordToUTF8Bytes(token.toCharArray()), salt.getBytes(), iterations);
	    KeyParameter params = (KeyParameter)generator.generateDerivedParameters(256);
	
		//return DatatypeConverter.printHexBinary(params.getKey().clone());	
	    return new String(Hex.encode(params.getKey().clone()));
	}
	
	public static void encryptFile(OutputStream out, InputStream inputStream,
			char[] passPhrase, String filename, long filesize) throws IOException, PGPException 
	{		
		OutputStream cOut = null;
		
		try 
		{
			BcPGPDataEncryptorBuilder pdeb = new BcPGPDataEncryptorBuilder(PGPEncryptedDataGenerator.AES_256);
			pdeb.setWithIntegrityPacket(true);
			PGPEncryptedDataGenerator cPk = new PGPEncryptedDataGenerator(pdeb);
			PGPKeyEncryptionMethodGenerator method = new BcPBEKeyEncryptionMethodGenerator(passPhrase);
			cPk.addMethod(method);
			
			cOut = cPk.open(out, new byte[1 << 16]);

			writeFileToLiteralData(cOut, PGPLiteralData.BINARY, inputStream, new byte[1 << 16], filename, filesize);
		}
		finally
		{
			if(cOut != null)
				cOut.close();
		}
	}
	
	public String encrypt(String message, String encryptionKey) throws MessageException
	{
		byte[] messageBytes = message.getBytes(Charset.forName("UTF-8"));
		
		String encrypted = "";
        
        try
        {
        	BcPGPDataEncryptorBuilder pdeb = new BcPGPDataEncryptorBuilder(PGPEncryptedDataGenerator.AES_256);
        	pdeb.setWithIntegrityPacket(true);
	        PGPEncryptedDataGenerator cPk = new PGPEncryptedDataGenerator(pdeb);
        	
	        ByteArrayOutputStream encOut = new ByteArrayOutputStream();
	        OutputStream out = encOut;
	        ByteArrayOutputStream bOut = new ByteArrayOutputStream();
	        PGPLiteralDataGenerator lData = new PGPLiteralDataGenerator();
	
	        OutputStream pOut = lData.open(bOut, // the compressed output stream
	                PGPLiteralData.BINARY, PGPLiteralData.CONSOLE,
	                messageBytes.length, // length of clear data
	                new Date() // current time
	                );
	        pOut.write(messageBytes);
	
	        lData.close();
	        bOut.close();
	
	        PGPKeyEncryptionMethodGenerator method = new BcPBEKeyEncryptionMethodGenerator(encryptionKey.toCharArray());
	        cPk.addMethod(method);
	        
	        byte[] bytes = bOut.toByteArray();
	
	        OutputStream cOut = cPk.open(out, bytes.length);
	
	        cOut.write(bytes); // obtain the actual bytes from the compressed stream
	
	        cOut.close();
	        
	        bOut.close();
	        
	        out.close();
        
	        byte[] encodedValue = Base64.encode(encOut.toByteArray());
	        encrypted = new String(encodedValue);
        }
        catch(Exception e)
        {
        	throw new MessageException(e);
        }
        
        return encrypted;
	}

	public static String decryptKeycode(String privateKey, String encryptedKeycode) throws IOException, NoSuchProviderException, PublicKeyDecryptionFailedException {
		InputStream keyIn = new BufferedInputStream(new ByteArrayInputStream(privateKey.getBytes(StandardCharsets.UTF_8)));
		InputStream in = new BufferedInputStream(new ByteArrayInputStream(encryptedKeycode.getBytes(StandardCharsets.UTF_8)));

		in = PGPUtil.getDecoderStream(in);

		String keycode;
        
		try
		{
			PGPObjectFactory pgpF = new PGPObjectFactory(in);
			PGPEncryptedDataList enc;
			
			Object o = pgpF.nextObject();

			// the first object might be a PGP marker packet.
			if (o instanceof PGPEncryptedDataList)
			{
				enc = (PGPEncryptedDataList)o;
			}
			else
			{
				enc = (PGPEncryptedDataList)pgpF.nextObject();
			}

			// find the secret key
			Iterator it = enc.getEncryptedDataObjects();
			PGPPrivateKey sKey = null;
			PGPPublicKeyEncryptedData pbe = null;
			PGPSecretKeyRingCollection pgpSec = new PGPSecretKeyRingCollection(PGPUtil.getDecoderStream(keyIn));

			while (sKey == null && it.hasNext())
			{
				pbe = (PGPPublicKeyEncryptedData)it.next();
				sKey = findSecretKey(pgpSec, pbe.getKeyID());
			}

			if (sKey == null)
			{
				throw new IllegalArgumentException("secret key for message not found.");
			}

			InputStream clear = pbe.getDataStream(new BcPublicKeyDataDecryptorFactory(sKey));
			PGPObjectFactory plainFact = new PGPObjectFactory(clear);
			Object message = plainFact.nextObject();

			if (message instanceof PGPLiteralData)
			{
				PGPLiteralData ld = (PGPLiteralData)message;
				
				InputStream unc = ld.getInputStream();
				ByteArrayOutputStream out = new ByteArrayOutputStream();
				
				byte[] buff = new byte[1024];
				int ch;
				while ((ch = unc.read(buff)) >= 0){
					out.write(buff, 0, ch);;
				}
				
				keycode = out.toString();
				out.close();
			}
			else if (message instanceof PGPOnePassSignatureList)
			{
				throw new PGPException("encrypted message contains a signed message - not literal data.");
			}
			else
			{
				throw new PGPException("message is not a simple encrypted file - type unknown.");
			}
		}
		catch (PGPException e)
		{
			throw new PublicKeyDecryptionFailedException(e);
		}

		return keycode;
    }

    public static String encryptKeycode(String publicKeyStr, String keycode) throws PublicKeyEncryptionFailedException {

        byte[] messageBytes = keycode.getBytes(Charset.forName("UTF-8"));

        String encrypted;

        try {
            InputStream in=new ByteArrayInputStream(publicKeyStr.getBytes());
            in = PGPUtil.getDecoderStream(in);
            PGPPublicKeyRingCollection pgpPub = new PGPPublicKeyRingCollection(in);
            PGPPublicKey key = null;
            Iterator rIt = pgpPub.getKeyRings();
            while (key == null && rIt.hasNext()) {
                PGPPublicKeyRing kRing = (PGPPublicKeyRing) rIt.next();
                Iterator kIt = kRing.getPublicKeys();
                while (key == null && kIt.hasNext()) {
                    PGPPublicKey k = (PGPPublicKey) kIt.next();
                    if (k.isEncryptionKey()) {
                        key = k;
                    }
                }
            }
            if (key == null) {
                throw new IllegalArgumentException("Can't find encryption key in key ring.");
            }

            // create an encrypted payload and set the public key on the data generator
            BcPGPDataEncryptorBuilder bcpgpdeb = new BcPGPDataEncryptorBuilder(PGPEncryptedData.AES_256);
            PGPEncryptedDataGenerator encryptGen = new PGPEncryptedDataGenerator(bcpgpdeb);
            encryptGen.addMethod(new BcPublicKeyKeyEncryptionMethodGenerator(key));

            ByteArrayOutputStream encOut = new ByteArrayOutputStream();
            ArmoredOutputStream armoredOut = new ArmoredOutputStream(encOut);
            ByteArrayOutputStream bOut = new ByteArrayOutputStream();
            PGPLiteralDataGenerator lData = new PGPLiteralDataGenerator();

            OutputStream pOut = lData.open(bOut, // the compressed output stream
                    PGPLiteralData.BINARY, PGPLiteralData.CONSOLE,
                    messageBytes.length, // length of clear data
                    new Date() // current time
            );
            pOut.write(messageBytes);

            lData.close();
            bOut.close();

            byte[] bytes = bOut.toByteArray();

            OutputStream cOut = encryptGen.open(armoredOut, bytes.length);

            cOut.write(bytes); // obtain the actual bytes from the compressed stream

            cOut.close();

            bOut.close();

            armoredOut.close();

            //byte[] encodedValue = Base64.encode(encOut.toByteArray());
            //encrypted = new String(encodedValue);
            encrypted = new String(encOut.toByteArray());
        } catch(Exception e) {
            throw new PublicKeyEncryptionFailedException(e);
        }

        return encrypted;
    }
	
	public static void decryptFile(InputStream input, OutputStream output, String decryptionKey, Progress progress) throws IOException, PGPException
	{
		input = PGPUtil.getDecoderStream(input);
		PGPObjectFactory pgpF = new PGPObjectFactory(input);
		PGPEncryptedDataList enc;
		Object o = pgpF.nextObject();
		
		if (o instanceof  PGPEncryptedDataList) 
		{
			enc = (PGPEncryptedDataList) o;
		} 
		else 
		{
			enc = (PGPEncryptedDataList) pgpF.nextObject();
		}
		
		PGPPBEEncryptedData pbe = (PGPPBEEncryptedData)enc.get(0);
		BcPGPDigestCalculatorProvider bcPgp = new BcPGPDigestCalculatorProvider();
		PBEDataDecryptorFactory pdf = new BcPBEDataDecryptorFactory(decryptionKey.toCharArray(), bcPgp);
		
		InputStream clear = pbe.getDataStream(pdf); 
		PGPObjectFactory pgpFact = new PGPObjectFactory(clear);
		
		o = pgpFact.nextObject();
		
		if (o instanceof  PGPCompressedData) 
		{
			PGPCompressedData cData = (PGPCompressedData) o;
			pgpFact = new PGPObjectFactory(cData.getDataStream());
			o = pgpFact.nextObject();
		}

		PGPLiteralData ld = (PGPLiteralData) o;
		
		// This will decode for us.
		InputStream unc = ld.getInputStream();
		
		// Save content in file.
		byte[] buffer = new byte[1024];
		int len = unc.read(buffer);
		while (len != -1) {
			output.write(buffer, 0, len);
		    len = unc.read(buffer);
		    progress.updateCurrent(len);
		}
		
		// Finally verify the integrity
		if (pbe.isIntegrityProtected()) 
		{
			if (!pbe.verify()) 
			{
				throw new PGPException("Failed to verify the integrity check");
			} 
		} 
		else 
		{
			throw new PGPException("Package did not include integrity check");
		}
	}
	
	public static String decryptMessage(String message, String decryptionKey) throws MessageException {
		String decryptedString;
        
        byte[] messageBytes = message.getBytes(Charset.forName("UTF-8"));
		byte[] encrypted = Base64.decode(messageBytes);
		
        try
        {
            InputStream in = new ByteArrayInputStream(encrypted);
            in = PGPUtil.getDecoderStream(in);
		
            PGPObjectFactory pgpF = new PGPObjectFactory(in);
            PGPEncryptedDataList enc = null;
            Object o = pgpF.nextObject();
			           
            //
            // the first object might be a PGP marker packet.
            //
            if (o instanceof PGPEncryptedDataList){
                enc = (PGPEncryptedDataList)o;
            }
            else{
                enc = (PGPEncryptedDataList)pgpF.nextObject();
            }
		   
            PGPPBEEncryptedData pbe = (PGPPBEEncryptedData)enc.get(0);
            BcPGPDigestCalculatorProvider bcPgp = new BcPGPDigestCalculatorProvider();
            PBEDataDecryptorFactory pdf = new BcPBEDataDecryptorFactory(decryptionKey.toCharArray(), bcPgp);
            InputStream clear = pbe.getDataStream(pdf);

            PGPObjectFactory pgpFact = new PGPObjectFactory(clear);
			
            PGPLiteralData  ld = (PGPLiteralData)pgpFact.nextObject();
			
            InputStream unc = ld.getInputStream();
			
            ByteArrayOutputStream out = new ByteArrayOutputStream();
			
            byte[] buff = new byte[1024];
            int ch;
            while ((ch = unc.read(buff)) >= 0){
                out.write(buff, 0, ch);;
            }
			
			decryptedString = out.toString();
			out.close();

			// Finally verify the integrity
			if (pbe.isIntegrityProtected())
			{
				if (!pbe.verify())
				{
					throw new MessageException();
				}
			}
        }
		catch(Exception e)
		{
			throw new MessageException(e.getMessage());
		}
		
		return decryptedString;	
	}

    private static PGPPrivateKey findSecretKey(PGPSecretKeyRingCollection pgpSec, long keyID)
            throws PGPException, NoSuchProviderException
    {
        PGPSecretKey pgpSecKey = pgpSec.getSecretKey(keyID);

        if (pgpSecKey == null)
        {
            return null;
        }

        return pgpSecKey.extractPrivateKey(new JcePBESecretKeyDecryptorBuilder().build(null));
    }

	private static void writeFileToLiteralData(OutputStream out, char fileType,
			InputStream inputStream, byte[] buffer, String filename, long filesize) throws IOException 
	{
		PGPLiteralDataGenerator lData = new PGPLiteralDataGenerator();
		OutputStream pOut = lData.open(out, fileType, filename, filesize, new Date());
		
		byte[] buf = new byte[buffer.length];
		
		int len;
		int remainingBytesToRead = (int)filesize;
		int bytesToRead = Math.min(buf.length, remainingBytesToRead);
		while (remainingBytesToRead > 0 && (len = inputStream.read(buf, 0, bytesToRead)) > 0)
		{
			pOut.write(buf, 0, len);
			remainingBytesToRead -= len;
			bytesToRead = Math.min(buf.length, remainingBytesToRead);
		}
		
		inputStream.close();
		pOut.close();
	}
	
}
