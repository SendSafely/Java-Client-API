package com.sendsafely.utils;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;
import java.security.SecureRandom;
import java.util.Date;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;
import javax.xml.bind.DatatypeConverter;

import org.bouncycastle.crypto.PBEParametersGenerator;
import org.bouncycastle.crypto.digests.SHA256Digest;
import org.bouncycastle.crypto.generators.PKCS5S2ParametersGenerator;
import org.bouncycastle.crypto.params.KeyParameter;
import org.bouncycastle.openpgp.PGPEncryptedDataGenerator;
import org.bouncycastle.openpgp.PGPException;
import org.bouncycastle.openpgp.PGPLiteralData;
import org.bouncycastle.openpgp.PGPLiteralDataGenerator;
import org.bouncycastle.openpgp.operator.PGPKeyEncryptionMethodGenerator;
import org.bouncycastle.openpgp.operator.bc.BcPBEKeyEncryptionMethodGenerator;
import org.bouncycastle.openpgp.operator.bc.BcPGPDataEncryptorBuilder;
import org.bouncycastle.util.encoders.Hex;

import com.sendsafely.exceptions.SignatureCreationFailedException;
import com.sendsafely.exceptions.TokenGenerationFailedException;

public class CryptoUtil 
{

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
