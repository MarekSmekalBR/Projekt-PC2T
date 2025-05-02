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
		StringBuilder morse = new StringBuilder();						
		for (char znak  : text.toCharArray()) {							
			if (znak >= 'A' && znak <= 'Z') {							
				morse.append(morseAbeceda[znak - 'A']).append(" ");		
			} else if(znak == ' ') {
				morse.append("   ");									
			}
		}
		return morse.toString().trim();									
	}
}
