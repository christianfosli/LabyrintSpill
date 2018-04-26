package spiller;
// Lag en statisk metode som tar inn en Streng, gjoer foerste bokstav stor, resten smaa

public class OppgAStorBokstav {
	
	public static String makeStorBokstav(String input) throws IndexOutOfBoundsException {
		String storBokstav = input.substring(0,1).toUpperCase();
		String smaaBokstaver = input.substring(1).toLowerCase();
		String output = storBokstav + smaaBokstaver;
		return output;
	}
	
	
	//test:
	public static void main(String[] args) {
		String streng = "oBaMacAre";
		String streng2 = "JAVA ER KULT";
		String fixetStreng = makeStorBokstav(streng);
		String fixetStreng2 = makeStorBokstav(streng2);
		System.out.print(fixetStreng + "\n" + fixetStreng2);
		
	}
}
