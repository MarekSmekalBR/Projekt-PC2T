package projekt;

import java.util.Scanner;

public class Main {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		Scanner scanner = new Scanner(System.in);
		StudentManager manager = new StudentManager();
	
		DatabaseHelper.inicializujDatabazi();
		DatabaseHelper.nacistStudentyZDatabaze(manager);

		System.out.println("Počet studentů po načtení z DB: " + manager.getVsechnyStudenty().size());
		
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
	            System.out.print("Vyber možnost: ");
	            String volba = scanner.nextLine();
	            
	            
	            switch (volba) {
                case "1":
                    System.out.print("Zadej jméno: ");
                    String jmeno = scanner.nextLine();
                    System.out.print("Zadej příjmení: ");
                    String prijmeni = scanner.nextLine();
                    System.out.print("Zadej rok narození: ");
                    int rok = Integer.parseInt(scanner.nextLine());

                    System.out.println("Vyber obor:");
                    System.out.println("1 - Telekomunikace");
                    System.out.println("2 - Kyberbezpečnost");
                    String obor = scanner.nextLine();

                    Student novy;
                    if (obor.equals("1")) {
                        novy = new Telekomunikace(jmeno, prijmeni, rok);
                    } else if (obor.equals("2")) {
                        novy = new Kyberbezpecnost(jmeno, prijmeni, rok);
                    } else {
                        System.out.println("Neplatný obor.");
                        break;
                    }
                    
                    manager.pridejStudenta(novy);
                    System.out.println("Student přidán.");
                    break;
                    
                case "2":
                	System.out.print("Zadej ID studenta: ");
                    int idZnamka = Integer.parseInt(scanner.nextLine());
                    Student studentZ = manager.najdiStudenta(idZnamka);
                    
                    if (studentZ != null) {
                        System.out.print("Zadej známku (1–5): ");
                        int znamka = Integer.parseInt(scanner.nextLine());
                        studentZ.pridaniZnamky(znamka);
                    } else {
                        System.out.println("Student s tímto ID nebyl nalezen.");
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
                	        System.out.println("Student s tímto ID neexistuje.");
                	    }
                    break;
                    
                case "5" :
                	System.out.print("Zadej ID studenta: ");
                    int idDovednost = Integer.parseInt(scanner.nextLine());
                    Student studentD = manager.najdiStudenta(idDovednost);
                    if (studentD != null) {
                        studentD.provedDovednost();
                    } else {
                        System.out.println("Student s tímto ID nebyl nalezen.");
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
                	System.out.print("Zadej ID studenta, kterého chceš uložit do souboru: ");
                    int idUlozit = Integer.parseInt(scanner.nextLine());
                    Student studentUlozit = manager.najdiStudenta(idUlozit);
                    if (studentUlozit != null) {
                        StudentManager.ulozStudentaDoSouboru(studentUlozit);
                    } else {
                        System.out.println("Student s tímto ID nebyl nalezen.");
                    }
                    break;
                           
                    
                case "10":
                	System.out.print("Zadej ID studenta, kterého chceš vypsat ze souboru: ");
                    int idZeSouboru = Integer.parseInt(scanner.nextLine());
                    StudentManager.vypisStudentaZeSouboru(idZeSouboru);
                	break;
                	       	
                case "0":
                    bez = false;
                    System.out.println("Program ukoncen");
                    break;

                default:
                    System.out.println("Neplatna volba.");
                	
	            }
	            
		}
		
		DatabaseHelper.ulozVsechnyStudenty(manager);
		System.out.println("Data byla uložena do databáze.");
		
		scanner.close();
	}
}
