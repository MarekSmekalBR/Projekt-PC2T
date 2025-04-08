package projekt;

public class Telekomunikace extends Student{

	public Telekomunikace(String jmeno, String prijmeni, int rokNarozeni) {
		super(jmeno, prijmeni, rokNarozeni);
		// TODO Auto-generated constructor stub
	}

	@Override
	public void provedDovednost() {
		System.out.println("Jmeno a prijmeni v Morseovce: " + prevodNaMorseovku(getJmeno() + " " + getPrijmeni()));
	}
	
	private String prevodNaMorseovku(String text) {
		String[] morseAbeceda = {".-", "-...", "-.-.", "-..", ".", "..-.", "--.", "....", "..", ".---",
               					 "-.-", ".-..", "--", "-.", "---", ".--.", "--.-", ".-.", "...", "-",
               					 "..-", "...-", ".--", "-..-", "-.--", "--.."
               					};
		text = text.toUpperCase();
		StringBuilder morse = new StringBuilder();						//inicializace StringBuilderu, ktery budeme plnit
		for (char znak  : text.toCharArray()) {							//prevod String na pole znaku, abychom mohli projit kazdy znak zvlast
			if (znak >= 'A' && znak <= 'Z') {							
				morse.append(morseAbeceda[znak - 'A']).append(" ");		//vyuziti ACII kodu pro nalezeni pozadovaneho pismena v morseovce a prida mezeru
			} else if(znak == ' ') {
				morse.append("   ");									//pokud je znak mezera, tak pridame mezeru na oddeleni (jmena a prijmeni)
			}
		}
		return morse.toString().trim();									//Prevod zpatky na String a trim() odstrani prebytecne mezery
	}
}