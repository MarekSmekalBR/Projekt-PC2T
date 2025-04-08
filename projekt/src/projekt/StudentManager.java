package projekt;

import java.util.ArrayList;

public class StudentManager {
	private ArrayList<Student> studenti = new ArrayList<>();
	
	public void pridejStudenta(Student student) {
		studenti.add(student);
	}
	
	public Student najdiStudenta(int id) {
		for (Student s : studenti) {
			if (s.getId() == id) {
				return s;
			}
		}
		return null;
	}
	
	public boolean odeberStudenta(int id) {
		Student s = najdiStudenta(id);
		if (s != null) {
			studenti.remove(s);
			return true;
		}
		return false;
	}
	
	public void vypisPodlePrijmeni() {
		ArrayList<Student> telekomunikace = new ArrayList<>();
		ArrayList<Student> kyberbezpecnost = new ArrayList<>();
	
		for (Student s : studenti) {
			if (s instanceof Telekomunikace) {
				telekomunikace.add(s);
			} else if(s instanceof Kyberbezpecnost) {
				kyberbezpecnost.add(s);
			}
		}
		
		System.out.println("\n--- Telekomunikace ---");
		if (telekomunikace.isEmpty()) {
			System.out.println("Zadni studenti");
		} else {
			for (Student s : telekomunikace) {
				System.out.print(s);
				System.out.println();
			}
		}
		
		System.out.println("\n--- Kyberbezpecnost ---");
		if (kyberbezpecnost.isEmpty()) {
			System.out.println("Zadni studenti");
		} else {
			for (Student s : kyberbezpecnost) {
				System.out.print(s);
				System.out.println();
			}
		}
	}
	
	
	
}