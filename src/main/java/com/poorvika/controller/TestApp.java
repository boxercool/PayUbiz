 package com.poorvika.controller;


 import com.poorvika.client.HashAlgorithm;

 public class TestApp {
	
	public static void main(String[] args) {
		
	    HashAlgorithm ff=new HashAlgorithm();
		String Hashsequence= "key" +"|"+"txnid"+"|"+"amount"+"|"
			       +"productinfo"+"|"+"firstname"+"|"+"email"+"|"+"|"+"|"+"|"+"|"+"|"+"|"+"|"+"|"+"|"+"|"+"Salt";
			    	 
		
        System.out.println(ff.hashCal2(Hashsequence));
        
    }
}
