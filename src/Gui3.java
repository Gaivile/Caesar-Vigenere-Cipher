import java.awt.*;
import java.awt.event.*;
import java.io.File;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Paths;
import javax.swing.*;


public class Gui3 extends JPanel implements ActionListener{
	// set contents for JPanel
    private JButton encrypt, chooseFile;
    private JPanel contentPanel;
    private JTextArea key, encrypted, path;
    String answer, ciChosen, opChosen, text, filePath;
    
    public Gui3(String k, String opt){
    	super();
    	
    	ciChosen = k;
    	
    	// string for the title of the scroll pane for the key
    	String encryptKey;
    	if(k.equals("cc")){
    		encryptKey = "Key as an int:";
    	}
    	else{
    		encryptKey = "Key as a string:";
    	}
    	
    	// create buttons
    	encrypt = new JButton("Encrypt");
    	encrypt.setMnemonic(KeyEvent.VK_E);
    	encrypt.setPreferredSize(new Dimension(200, 40));
    	encrypt.addActionListener(this);
    	chooseFile = new JButton("Choose File");
    	chooseFile.setMnemonic(KeyEvent.VK_C);
    	chooseFile.setPreferredSize(new Dimension(200, 40));
        chooseFile.addActionListener(this);
    	
    	// create text area for the program's output
    	encrypted = new JTextArea("");
    	encrypted.setEditable(false);
    	encrypted.setFont(new Font("Serif", Font.ITALIC, 16));
    	encrypted.setLineWrap(true);
    	encrypted.setWrapStyleWord(true);
    	
    	// create a scroll pane for the program's output
		JScrollPane areaEncrypted = new JScrollPane(encrypted);
	    areaEncrypted.setVerticalScrollBarPolicy(
	                     JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
	    areaEncrypted.setPreferredSize(new Dimension(300, 300));
	    areaEncrypted.setBorder(
	         BorderFactory.createCompoundBorder(
	             BorderFactory.createCompoundBorder(
	                             BorderFactory.createTitledBorder("Encrypted text:"),
	                             BorderFactory.createEmptyBorder(5,5,5,5)),
	             areaEncrypted.getBorder()));
    	
    	// create text area to enter a key
    	key = new JTextArea("");
    	key.setFont(new Font("Serif", Font.ITALIC, 16));
    	key.setLineWrap(true);
    	key.setWrapStyleWord(true);
    	
    	// create a scroll pane for the key
    	JScrollPane areaKey = new JScrollPane(key);
        areaKey.setVerticalScrollBarPolicy(
                        JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
        areaKey.setPreferredSize(new Dimension(200, 100));
        areaKey.setBorder(
                BorderFactory.createCompoundBorder(
                    BorderFactory.createCompoundBorder(
                                    BorderFactory.createTitledBorder(encryptKey),
                                    BorderFactory.createEmptyBorder(5,5,5,5)),
                    key.getBorder()));
    	
        // create text area for the file path
        path = new JTextArea("");
        path.setFont(new Font("Serif", Font.ITALIC, 16));
        path.setLineWrap(true);
        path.setWrapStyleWord(true);
        path.setEditable(false);
        path.setText(filePath);
        
        // create a scroll pane for the file path
        JScrollPane areaScrollPane = new JScrollPane(path);
        areaScrollPane.setHorizontalScrollBarPolicy(
                        JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
        areaScrollPane.setPreferredSize(new Dimension(600, 60));
        areaScrollPane.setBorder(
            BorderFactory.createCompoundBorder(
                BorderFactory.createCompoundBorder(
                                BorderFactory.createTitledBorder("File path:"),
                                BorderFactory.createEmptyBorder(5,5,5,5)),
                areaScrollPane.getBorder()));
        
        // add and lay out elements on the content panel
        contentPanel = new JPanel();
        contentPanel.setLayout(new BorderLayout());
        
        contentPanel.add(areaKey, BorderLayout.LINE_START);
        contentPanel.add(areaScrollPane, BorderLayout.PAGE_START);
        contentPanel.add(chooseFile, BorderLayout.CENTER);
        contentPanel.add(encrypt, BorderLayout.LINE_END);
        contentPanel.add(areaEncrypted, BorderLayout.SOUTH);
        
        add(contentPanel);
        setBorder(BorderFactory.createEmptyBorder(0,0,0,0));
    }
    
    /* Attempt to open a chosen file for reading. New  variables are created and parsed to the 
     * right method by the cipher chosen: Caesar cipher shows error dialog if entered key is 
     * not an integer; Vigenere cipher shows error dialog and breaks form the loop if any character 
     * in the key is not a a letter.
     */
	@Override
	public void actionPerformed(ActionEvent e){
		String textToEncrypt = "";
		String keytoParse = "";
		
		// choose a file and get file path, set text area to a right value
		JButton src = (JButton) e.getSource();
		if (src.equals(chooseFile)){
			filePath = choose();
		}
		path.setText(filePath);
		
		// read file only if file path is not null
		if(filePath != null){		
			try {
				textToEncrypt = readFile(filePath, Charset.defaultCharset());
			} 
			catch (IOException e1) {
				e1.printStackTrace();
			}
		}
		
		// read a key used for encryption
		for(String enteredKey: key.getText().split("\\n"))
			keytoParse = enteredKey;
			
		// try to parse entered key as an int, on error - key is left unchanged (0), add error dialog
		if (ciChosen.equals("cc")){
			int finalKey = 0;
			try{
				finalKey = Integer.parseInt(keytoParse);
			}
			catch (NumberFormatException nfe){
				 JOptionPane.showMessageDialog(contentPanel, "Invalid key!",
						 	"Oops!",
						    JOptionPane.ERROR_MESSAGE);
			}
			// encrypt a string and show it on the panel
			Caesar c = new Caesar(finalKey);
			answer = c.encrypt(textToEncrypt);
			encrypted.setText(answer);
		}
		else{
			// check every character to be a letter, add error dialog
			for (int i = 0; i < keytoParse.length(); i++){
				if (!(Character.isLetter(keytoParse.charAt(i)))){
					JOptionPane.showMessageDialog(contentPanel, "Invalid key!",
							"Oops!",
						    JOptionPane.ERROR_MESSAGE);
					break;
				}
			}
			// encrypt a string and show it on the panel
			Vigenere v = new Vigenere(keytoParse);
			answer = v.encrypt(textToEncrypt);
			encrypted.setText(answer);
		}
	}
	
	// attempt to read a file and return it's content as a string
	static String readFile(String path, Charset encoding) 
			  throws IOException 
			{
			  byte[] encoded = Files.readAllBytes(Paths.get(path));
			  return new String(encoded, encoding);
			}
	
	// prompt user to choose a file, return absolute path
	public String choose(){
		JFileChooser fileChooser = new JFileChooser();
		fileChooser.setCurrentDirectory(new File(System.getProperty("user.home")));
		int result = fileChooser.showOpenDialog(null);
		if (result == JFileChooser.APPROVE_OPTION) 
		{
			File selectedFile = fileChooser.getSelectedFile();
			String txtPath = selectedFile.getAbsolutePath();
			
			// check if file extension is ".txt", if not, show error dialog and return null
			if(txtPath.endsWith(".txt")){
				return txtPath;
			}
			else{
				JOptionPane.showMessageDialog(contentPanel, "Invalid file!",
					    "Oops!",
					    JOptionPane.ERROR_MESSAGE);
				return null;
			}
		}
		return null;
	}
	
	// create the GUI and show it
	void createAndShowGUI3(String k, String opt) {
        //Create and set up the window.
		String cipher;
    	if(k.equals("cc")){
    		cipher = "Caesar Cipher";
    	}
    	else{
    		cipher = "Vigener Cipher";
    	}
        JFrame frame = new JFrame(cipher);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        //Create and set up the content pane.
        JComponent newContentPane = new Gui3(k, opt);
        newContentPane.setOpaque(true); //content panes must be opaque
        frame.setContentPane(newContentPane);

        //Display the window.
        frame.setSize(900, 500);
        frame.setVisible(true);
    }
}
