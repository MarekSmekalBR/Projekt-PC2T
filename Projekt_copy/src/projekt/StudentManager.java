package projekt;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.io.FileReader;
import java.io.BufferedReader;

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
            } else if (s instanceof Kyberbezpecnost) {
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
                System.out.println(s);
            }
        }

        System.out.println("\n--- Kyberbezpecnost ---");
        if (kyberbezpecnost.isEmpty()) {
            System.out.println("Zadni studenti");
        } else {
            for (Student s : kyberbezpecnost) {
                System.out.println(s);
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
    
    public void ulozStudentaDoSouboru(int id) {
        Student student = najdiStudenta(id);
        if (student != null) {
            try (BufferedWriter writer = new BufferedWriter(new FileWriter("student_" + id + ".txt"))) {
                writer.write("ID: " + student.getId());
                writer.newLine();
                writer.write("Jméno: " + student.getJmeno());
                writer.newLine();
                writer.write("Příjmení: " + student.getPrijmeni());
                writer.newLine();
                writer.write("Rok narození: " + student.getRokNarozeni());
                writer.newLine();
                writer.write("Studijní průměr: " + student.getStudijniPrumer());
                writer.newLine();
                writer.write("Obor: " + (student instanceof Telekomunikace ? "Telekomunikace" : "Kyberbezpečnost"));
                writer.newLine();
                writer.flush();
                System.out.println("Student byl úspěšně uložen do souboru.");
            } catch (IOException e) {
                System.out.println("Chyba při ukládání studenta do souboru.");
            }
        } else {
            System.out.println("Student s tímto ID nebyl nalezen.");
        }
    }
    
    public Student nactiStudentaZeSouboru(int id) {
        Student student = null;
        try (BufferedReader reader = new BufferedReader(new FileReader("student_" + id + ".txt"))) {
            String line;
            String jmeno = "";
            String prijmeni = "";
            int rokNarozeni = 0;
            double studijniPrumer = 0;
            String obor = "";

            // Čteme řádky ze souboru a získáváme potřebné údaje
            while ((line = reader.readLine()) != null) {
                if (line.startsWith("ID: ")) {
                    // ID studenta ignorujeme, protože je již správně nastaveno
                    continue;
                } else if (line.startsWith("Jméno: ")) {
                    jmeno = line.substring(7);
                } else if (line.startsWith("Příjmení: ")) {
                    prijmeni = line.substring(11);
                } else if (line.startsWith("Rok narození: ")) {
                    rokNarozeni = Integer.parseInt(line.substring(15));
                } else if (line.startsWith("Studijní průměr: ")) {
                    studijniPrumer = Double.parseDouble(line.substring(17));
                } else if (line.startsWith("Obor: ")) {
                    obor = line.substring(6);
                }
            }

            // Na základě oboru vytvoříme studenta
            if (obor.equals("Telekomunikace")) {
                student = new Telekomunikace(jmeno, prijmeni, rokNarozeni);
            } else if (obor.equals("Kyberbezpečnost")) {
                student = new Kyberbezpecnost(jmeno, prijmeni, rokNarozeni);
            }

            if (student != null) {
                // Pokud je studijní průměr a je platný, přidáme známku
                if (studijniPrumer > 0) {
                    student.pridaniZnamky((int) studijniPrumer);
                }
                // Pokud není studijní průměr (studijniPrumer <= 0), žádná známka nebude přidána
            }
        } catch (IOException e) {
            System.out.println("Chyba při načítání studenta ze souboru.");
        }

        return student;
    }
}