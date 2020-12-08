package com.poorvika.client;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class HashAlgorithm {
    private Integer error;

    public boolean empty(String s) {
        if (s == null || s.trim().equals("")) {
            return true;
        } else {
            return false;
        }
    }
    
    public  String hashCal2(String str) {  
        byte[] hashseq = str.getBytes();  
        StringBuilder hexString = new StringBuilder();  
        try {  
            MessageDigest algorithm = MessageDigest.getInstance("SHA-512");  
            algorithm.reset();  
            algorithm.update(hashseq);  
            byte messageDigest[] = algorithm.digest();  
            for (byte aMessageDigest : messageDigest) {  
                String hex = Integer.toHexString(0xFF & aMessageDigest);  
                if (hex.length() == 1) {  
                    hexString.append("0");  
                }  
                hexString.append(hex);  
            }  
        } catch (NoSuchAlgorithmException ignored) {  
        }  
        return hexString.toString();  
    }

    public String hashCal1(String type, String str) {
        byte[] hashseq = str.getBytes();
        StringBuffer sb = new StringBuffer();// method1
        try {
            MessageDigest algorithm = MessageDigest.getInstance(type);
            algorithm.reset();
            algorithm.update(hashseq);
            byte[] mdbytes = algorithm.digest();
            for (int i = 0; i < mdbytes.length; i++) {
            	sb.append(Integer.toString((mdbytes[i] & 0xff) + 0x100, 16).substring(1));
            }
            System.out.println("Hex format1 : " + sb.toString());

        } catch (NoSuchAlgorithmException nsae) {
        }
        return sb.toString();
    }
    
    public String hashCal(String type,String str){
		byte[] hashseq=str.getBytes();
		StringBuffer hexString = new StringBuffer();
		try{
		MessageDigest algorithm = MessageDigest.getInstance(type);
		algorithm.reset();
		algorithm.update(hashseq);
		byte messageDigest[] = algorithm.digest();
            
		
		for (int i=0;i<messageDigest.length;i++) {
			String hex=Integer.toHexString(0xFF & messageDigest[i]);
			if(hex.length()==1) hexString.append("0");
			hexString.append(hex);
		}
			
		}catch(NoSuchAlgorithmException nsae){ }
		
		return hexString.toString();
	}


}