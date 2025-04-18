package projekt;

import java.util.List;
import java.util.Scanner;

public class Main {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		Scanner scanner = new Scanner(System.in);
		StudentManager manager = new StudentManager();
	
		List<Student> nacteni = DatabaseManager.nactiStudentyZDatabaze();
		for (Student s : nacteni) {
		    manager.pridejStudenta(s);
		}
		
		boolean bez = true;
		while(bez)  {
			 System.out.println("\n--- MENU ---");
	            System.out.println("1 - Pridani studenta");
	            System.out.println("2 - Zadani nove znamky");
	            System.out.println("3 - Propustit studenta");
	            System.out.println("4 - Nalezeni studenta");
	            System.out.println("5 - Dovednost studenta");
	            System.out.println("6 - Vypis vsech studentu v jednotlivych skupinach");
	            System.out.println("7 - Vypis obecneho studijního prumeru v oborech");
	            System.out.println("8 - Vypis poctu studentu v jednotlivych skupinach");
	            System.out.println("9 - Ulozeni studenta do souboru");
	            System.out.println("10 - Nacteni studenta ze souboru");
	            System.out.println("0 - Konec");
	            System.out.print("Vyber moznost: ");
	            String volba = scanner.nextLine();
	            
	            
	            switch (volba) {
                case "1":
                    System.out.print("Zadej jmeno: ");
                    String jmeno = scanner.nextLine();
                    System.out.print("Zadej prijmeni: ");
                    String prijmeni = scanner.nextLine();
                    System.out.print("Zadej rok narozeni: ");
                    int rok = Integer.parseInt(scanner.nextLine());

                    System.out.println("Vyber obor:");
                    System.out.println("1 - Telekomunikace");
                    System.out.println("2 - Kyberbezpecnost");
                    String obor = scanner.nextLine();

                    Student novy;
                    if (obor.equals("1")) {
                        novy = new Telekomunikace(jmeno, prijmeni, rok);
                    } else if (obor.equals("2")) {
                        novy = new Kyberbezpecnost(jmeno, prijmeni, rok);
                    } else {
                        System.out.println("Neplatny obor.");
                        break;
                    }
                    
                    manager.pridejStudenta(novy);
                    System.out.println("Student pridan.");
                    break;
                    
                case "2":
                	System.out.print("Zadej ID studenta: ");
                    int idZnamka = Integer.parseInt(scanner.nextLine());
                    Student studentZ = manager.najdiStudenta(idZnamka);
                    
                    if (studentZ != null) {
                        System.out.print("Zadej znamku (1–5): ");
                        int znamka = Integer.parseInt(scanner.nextLine());
                        studentZ.pridaniZnamky(znamka);
                    } else {
                        System.out.println("Student s timto ID nebyl nalezen.");
                    }
                    break;
                    
                case "3":
                    System.out.println("Zadej ID studenta: ");
                	int idSmazat = Integer.parseInt(scanner.nextLine());
                	boolean studentSmazat = manager.odeberStudenta(idSmazat);
                	
                	if (studentSmazat) {
						System.out.println("Student byl uspesne smazan.");
					} else {
						System.out.println("Student s timto ID neexistuje.");
					}
                    break;
                    
                case "4":
                	 System.out.print("Zadej ID studenta: ");
                	    int idNajit = Integer.parseInt(scanner.nextLine());
                	    Student nalezeny = manager.najdiStudenta(idNajit);
                	    if (nalezeny != null) {
                	        System.out.println(nalezeny);  
                	    } else {
                	        System.out.println("Student s timto ID neexistuje.");
                	    }
                    break;
                    
                case "5" :
                	System.out.print("Zadej ID studenta: ");
                    int idDovednost = Integer.parseInt(scanner.nextLine());
                    Student studentD = manager.najdiStudenta(idDovednost);
                    if (studentD != null) {
                        studentD.provedDovednost();
                    } else {
                        System.out.println("Student s timto ID nebyl nalezen.");
                    }
                	break;
                	
                case "6" :
                	manager.vypisPodlePrijmeni();
                	break;
                	
                case "7":
                    manager.vypisObecnyStudijnyPrumer();
                    break;
                  
                case "8":
                    manager.vypisPocetStudentu();
                    break;
                    
                case "9":
                	System.out.print("Zadej ID studenta, ktereho chces ulozit do souboru: ");
                    int idUlozit = Integer.parseInt(scanner.nextLine());
                    Student studentUlozit = manager.najdiStudenta(idUlozit);
                    if (studentUlozit != null) {
                        StudentManager.ulozStudentaDoSouboru(studentUlozit);
                    } else {
                        System.out.println("Student s tímto ID nebyl nalezen.");
                    }
                    break;
                           
                    
                case "10":
                	System.out.print("Zadej ID studenta, ktereho chces vypsat ze souboru: ");
                    int idZeSouboru = Integer.parseInt(scanner.nextLine());
                    StudentManager.vypisStudentaZeSouboru(idZeSouboru);
                	break;
                	       	
                case "0":
                	System.out.println("Ukladam data do databaze...");
                    DatabaseManager.ulozStudentyDoDatabaze(manager.getStudenti());
                    System.out.println("Na shledanou!");
                    bez = false;
                    break;

                default:
                    System.out.println("Neplatna volba.");
                	
	            }
	            
		}
		scanner.close();
	}
}
