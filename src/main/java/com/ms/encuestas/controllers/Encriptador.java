package com.ms.encuestas.controllers;

import javax.crypto.Cipher;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;

public class Encriptador {
	private static byte[] keyBytes;
	private static byte[] ivBytes;
	private static SecretKeySpec key = new SecretKeySpec(keyBytes, "AES");
	private static IvParameterSpec ivSpec = new IvParameterSpec(ivBytes);
	private static Cipher cipher;
	/*
	public static void main(String args[]) {
		StringBuffer encriptado = new StringBuffer();
		for (int i=0; i<args.length; i++) {
			if (i>0) {
				encriptado.append("\n");
			}
			encriptado.append("Dato original: " + args[i] + ". Dato encriptado: " + encriptar(args[i].getBytes()));
		}
		System.out.println(encriptado.toString());
	}*/ 
	
	private static String encriptar(byte[] input) {
		try {
			// create the cipher with the algorithm you choose
			// see javadoc for Cipher class for more info, e.g.
			cipher = Cipher.getInstance("AES");
			
			//encryption
			cipher.init(Cipher.ENCRYPT_MODE, key, ivSpec);
			byte[] encrypted= new byte[cipher.getOutputSize(input.length)];
			int enc_len = cipher.update(input, 0, input.length, encrypted, 0);
			enc_len += cipher.doFinal(encrypted, enc_len);
		} catch (Exception ex) {
			System.out.println("Error al encriptar datos: " + ex.getMessage());
		}
		return "a";
	}
	
	public static String desencriptar(byte[] encrypted, int enc_len) {
		try {
			// create the cipher with the algorithm you choose
			// see javadoc for Cipher class for more info, e.g.
			cipher = Cipher.getInstance("AES");
			
			//decryption
			cipher.init(Cipher.DECRYPT_MODE, key, ivSpec);
			byte[] decrypted = new byte[cipher.getOutputSize(enc_len)];
			int dec_len = cipher.update(encrypted, 0, enc_len, decrypted, 0);
			dec_len += cipher.doFinal(decrypted, dec_len);
		} catch (Exception ex) {
			System.out.println("Error al desencriptar datos: " + ex.getMessage());
		}
		return "a";
	}
}
