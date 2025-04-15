package projekt;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

public class StudentManager {
	private ArrayList<Student> studenti = new ArrayList<>();
	
	public ArrayList<Student> getVsechnyStudenty() {
	    return new ArrayList<>(studenti);
	}
	
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
		
		Comparator<Student> podlePrijmeni = Comparator.comparing(Student::getPrijmeni, String.CASE_INSENSITIVE_ORDER);
        Collections.sort(telekomunikace, podlePrijmeni);
        Collections.sort(kyberbezpecnost, podlePrijmeni);
		
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
	
	public void vypisObecnyStudijnyPrumer() {
        ArrayList<Student> telekomunikace = new ArrayList<>();
        ArrayList<Student> kyberbezpecnost = new ArrayList<>();

        for (Student s : studenti) {
            if (s instanceof Telekomunikace) {
                telekomunikace.add(s);
            } else if (s instanceof Kyberbezpecnost) {
                kyberbezpecnost.add(s);
            }
        }

        if (!telekomunikace.isEmpty()) {
            double sumaTelekomunikace = 0.0;
            for (Student s : telekomunikace) {
                sumaTelekomunikace += s.getStudijniPrumer();
            }
            double prumerTelekomunikace = sumaTelekomunikace / telekomunikace.size();
            System.out.println("\nPrumerny studijní prumer pro Telekomunikace: " + prumerTelekomunikace);
        } else {
            System.out.println("\nŽádní studenti v oboru Telekomunikace.");
        }

        if (!kyberbezpecnost.isEmpty()) {
            double sumaKyberbezpecnost = 0.0;
            for (Student s : kyberbezpecnost) {
                sumaKyberbezpecnost += s.getStudijniPrumer();
            }
            double prumerKyberbezpecnost = sumaKyberbezpecnost / kyberbezpecnost.size();
            System.out.println("\nPrumerny studijní prumer pro Kyberbezpecnost: " + prumerKyberbezpecnost);
        } else {
            System.out.println("\nŽádní studenti v oboru Kyberbezpecnost.");
        }
    }
	
	public void vypisPocetStudentu() {
        int pocetTelekomunikace = 0;
        int pocetKyberbezpecnost = 0;

        for (Student s : studenti) {
            if (s instanceof Telekomunikace) {
                pocetTelekomunikace++;
            } else if (s instanceof Kyberbezpecnost) {
                pocetKyberbezpecnost++;
            }
        }

        System.out.println("\nPočet studentů v oboru Telekomunikace: " + pocetTelekomunikace);
        System.out.println("Počet studentů v oboru Kyberbezpecnost: " + pocetKyberbezpecnost);
    }
	
	public static void ulozStudentaDoSouboru(Student student) {
	    String fileName = "studenti.txt";

	    try (BufferedWriter writer = new BufferedWriter(new FileWriter(fileName, true))) {
	        String obor = (student instanceof Telekomunikace) ? "Telekomunikace" : 
	                      (student instanceof Kyberbezpecnost) ? "Kyberbezpečnost" : "Neznámý";

	        String radek = String.format("ID: %d, Jméno: %s, Příjmení: %s, Rok narození: %d, Průměr: %.2f, Obor: %s",
	                student.getId(),
	                student.getJmeno(),
	                student.getPrijmeni(),
	                student.getRokNarozeni(),
	                student.getStudijniPrumer(),
	                obor);

	        writer.write(radek);
	        writer.newLine();

	        System.out.println("Student byl úspěšně uložen do souboru.");

	    } catch (IOException e) {
	        System.out.println("Chyba při ukládání do souboru: " + e.getMessage());
	    }
	}
	
	public static void vypisStudentaZeSouboru(int hledaneId) {
	    String fileName = "studenti.txt";

	    try (BufferedReader reader = new BufferedReader(new FileReader(fileName))) {
	        String radek;
	        boolean nalezen = false;

	        while ((radek = reader.readLine()) != null) {
	            if (radek.startsWith("ID: ")) {
	                String[] casti = radek.split(", ");
	                int id = Integer.parseInt(casti[0].split(": ")[1]);

	                if (id == hledaneId) {
	                    System.out.println(radek);
	                    nalezen = true;
	                    break;
	                }
	            }
	        }

	        if (!nalezen) {
	            System.out.println("Student s ID " + hledaneId + " nebyl nalezen v souboru.");
	        }

	    } catch (IOException e) {
	        System.out.println("Chyba při čtení souboru: " + e.getMessage());
	    } catch (NumberFormatException | ArrayIndexOutOfBoundsException e) {
	        System.out.println("Chybný formát řádku v souboru.");
	    }
	}
	
	public void nacistStudentyZDatabaze() {
	    String url = "jdbc:sqlite:studenti.db";
	    String sql = "SELECT jmeno, prijmeni, id, vek, obor FROM studenti";

	    try (Connection conn = DriverManager.getConnection(url);
	         Statement stmt = conn.createStatement();
	         ResultSet rs = stmt.executeQuery(sql)) {

	        while (rs.next()) {
	            String jmeno = rs.getString("jmeno");
	            String prijmeni = rs.getString("prijmeni");
	            int id = rs.getInt("id");
	            int vek = rs.getInt("vek");
	            String obor = rs.getString("obor");

	            Student student = new Student(jmeno, prijmeni, id, vek, obor);
	            studenti.add(student); // předpokládám, že máš list "studenti"
	        }

	        System.out.println("Data byla úspěšně načtena z databáze.");

	    } catch (SQLException e) {
	        System.out.println("Chyba při načítání dat z databáze: " + e.getMessage());
	    }
	}

}
