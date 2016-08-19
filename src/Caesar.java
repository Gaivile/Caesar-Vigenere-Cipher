public class Caesar{
    private String alphabet;
    private String shiftedAlphabet;
    private int theKey;
   
    public Caesar(int key) {
    	
    	// get a modulo of a given key; make a shifted alphabet by a key for an encryption
        theKey = key % 26;
        alphabet = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
        shiftedAlphabet = alphabet.substring(theKey) +
                            alphabet.substring(0,theKey);
        alphabet = alphabet + alphabet.toLowerCase();
        shiftedAlphabet = shiftedAlphabet + shiftedAlphabet.toLowerCase();
    }
   
    // check if a letter in the input string exists in the alphabet, if so - get it's index and
    // take the character from a shifted alphabet by that index; if not, return the same char
    private char transformLetter(char c, String from, String to) {
        int idx = from.indexOf(c);
        if (idx != -1) {
            return to.charAt(idx);
        }
        return c;
    }
   
    // iterate through the whole input string, char by char, change each char
    private String transform(String input, String from, String to){
        StringBuilder sb = new StringBuilder(input);
        for (int i = 0; i < sb.length(); i++) {
            char c = sb.charAt(i);
            c = transformLetter(c, from, to);
            sb.setCharAt(i, c);
        }
        return sb.toString();
    }
   
    // return encrypted string
    public String encrypt(String input) {
        return transform(input, alphabet, shiftedAlphabet);
    }
}