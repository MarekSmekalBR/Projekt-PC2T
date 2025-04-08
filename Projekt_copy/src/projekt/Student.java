package projekt;

import java.util.ArrayList;

abstract class Student {
	private static int nextId = 1; 										//číslování studentů
	private int id;				    
	private String jmeno;
	private String prijmeni;
	private int rokNarozeni;
	private ArrayList<Integer> znamky = new ArrayList<>(); 				//uchování seznam známek
	
	public Student(String jmeno, String prijmeni, int rokNarozeni) {
		this.id= nextId++; 												//zajisteni unikatniho id
		this.jmeno = jmeno;
		this.prijmeni = prijmeni;
		this.rokNarozeni = rokNarozeni;
	}

	public int getId() {
		return id;
	}

	public String getJmeno() {
		return jmeno;
	}

	public void setJmeno(String jmeno) {
		this.jmeno = jmeno;
	}

	public String getPrijmeni() {
		return prijmeni;
	}

	public void setPrijmeni(String prijmeni) {
		this.prijmeni = prijmeni;
	}

	public int getRokNarozeni() {
		return rokNarozeni;
	}

	public void setRokNarozeni(int rokNarozeni) {
		this.rokNarozeni = rokNarozeni;
	}

	public void pridaniZnamky(int znamka) {	
		if (znamka > 0 && znamka < 6) {
			znamky.add(znamka);
		} else {
			System.out.println("Zadejte znamku spravne");
		}
	}
	
	public double getStudijniPrumer() {									 //Vypocet prumeru
		if (znamky.isEmpty()) {
			return 0.0;			
		}	
		int suma = 0;
		for (int z : znamky) {
			suma += z;
		}
		return (double) suma/znamky.size();
	}
	
	public abstract void provedDovednost();								//dovednost (Morseovka a Hash)
	
	public String toString() {
		return "ID: "+ id + ", " + jmeno + " " + prijmeni + ", Rok narozeni: " + rokNarozeni + ", Prumer: " + getStudijniPrumer() + " ";
	}
}