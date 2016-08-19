import java.util.ArrayList;
import java.util.List;

public class Vigenere {
	private String alphabet;
	private List<Integer> theKey;
	
	public Vigenere(String key){
		// make a shifted alphabet by a key for an encryption for each letter in a key
		alphabet = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
		alphabet = alphabet + alphabet.toLowerCase();
		theKey = new ArrayList<Integer>();
		
		//make an array list out of a string by each character's index in the alphabet
		for (int i = 0; i < key.length(); i++){
			char c = key.charAt(i);
			int index = alphabet.indexOf(c);
			// if an alphabet doesn't contain a letter, set array list to 0 and break out from the loop
			if (index == -1 ){
				index = 0;
				theKey.clear();
				theKey.add(index);
				break;
			}
			theKey.add(index);
		}
	}
	
	// return encrypted string
	public String encrypt(String input){
		StringBuilder sb = new StringBuilder(input);
		int x = 0;
		// iterate through the whole input string, char by char
		for (int i = 0; i < sb.length(); i++){
			char c = input.charAt(i);
			int localKey = theKey.get(x);
			// use Caesar Cipher class to change each character in the string by every other int
			// from the array list
			if(Character.isLetter(c)){
				Caesar cc = new Caesar(localKey);
				c = cc.encrypt(Character.toString(c)).charAt(0);
			}
			// set the right char into it's place in the StringBuilder; wrap a key as many times as needed
			sb.setCharAt(i, c);
			x++;
			if (x == theKey.size()){
				x = 0;
			}
		}
		return sb.toString();
	}
}
