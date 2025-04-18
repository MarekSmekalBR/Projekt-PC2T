package projekt;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class Kyberbezpecnost extends Student{

	public Kyberbezpecnost(String jmeno, String prijmeni, int rokNarozeni) {
		super(jmeno, prijmeni, rokNarozeni);
		// TODO Auto-generated constructor stub
	}
	
	@Override 
	public void provedDovednost(){
		System.out.println("Jmeno a prijmeni ve forme hashe: " + prevodNaHash(getJmeno() + " " + getPrijmeni()));
	}
	
	private String prevodNaHash(String text) {
		try {
			MessageDigest md = MessageDigest.getInstance("SHA-256");
			byte[] hash = md.digest(text.getBytes()); 				//prevod textu na pole bajtu, pak se spocita hash tohoto pole
			StringBuilder hex = new StringBuilder();
			for (byte b : hash) {
				hex.append(String.format("%02x", b));				//kazdy bajt v poli se prevede do hexadec. podoby
			}	
			return hex.toString();
		} catch (NoSuchAlgorithmException e) {
			return "Chyba pri hashovani";
		}
	}
	
}
