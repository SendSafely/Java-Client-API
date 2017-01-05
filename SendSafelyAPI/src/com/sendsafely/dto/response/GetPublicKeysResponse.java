package com.sendsafely.dto.response;

import com.sendsafely.Phonenumber;
import com.sendsafely.dto.PublicKey;

import java.util.List;


public class GetPublicKeysResponse extends BaseResponse {
	
	private List<PublicKey> publicKeys;

    public List<PublicKey> getPublicKeys() {
        return publicKeys;
    }

    public void setPublicKeys(List<PublicKey> publicKeys) {
        this.publicKeys = publicKeys;
    }
}
