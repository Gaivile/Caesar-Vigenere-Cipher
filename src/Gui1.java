import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

public class Gui1 extends JPanel implements ActionListener{
	// set contents for JPanel
	static String cc = "Caesar Cipher";
    static String vc = "Vigenere Cipher";
    private JTextField chooseCipher, chooseOption;
    private JRadioButton caesarButton, vigenereButton;
    private JButton text, file;
    private JPanel contentPanel;
    
    public Gui1(){
    	super();
    	
    	// create text fields
    	chooseCipher = new JTextField("Choose a Cipher:", 0);
    	chooseCipher.setEditable(false);
    	chooseOption = new JTextField("Choose an Option:", 0);
    	chooseOption.setEditable(false);
    	
    	// create buttons
    	text = new JButton("Enter Text");
    	text.setMnemonic(KeyEvent.VK_E);
    	text.setPreferredSize(new Dimension(160, 80));
        file = new JButton("Open File");
        file.setMnemonic(KeyEvent.VK_O);
        file.setPreferredSize(new Dimension(160, 80));
        
        // create radio buttons
        caesarButton = new JRadioButton(cc);
        caesarButton.setMnemonic(KeyEvent.VK_C);
        caesarButton.setActionCommand(cc);
        caesarButton.setSelected(true);
        vigenereButton = new JRadioButton(vc);
        vigenereButton.setMnemonic(KeyEvent.VK_V);
        vigenereButton.setActionCommand(vc);
        
        // group radio buttons
        ButtonGroup group = new ButtonGroup();
        group.add(caesarButton);
        group.add(vigenereButton);
        
        // register a listener to radio buttons
        caesarButton.addActionListener(this);
        vigenereButton.addActionListener(this);
        caesarButton.doClick();
        
        // add and lay out elements on the content panel
        contentPanel = new JPanel();
        contentPanel.setLayout(new FlowLayout());
        contentPanel.add(chooseCipher, BorderLayout.PAGE_START);
        contentPanel.add(caesarButton, BorderLayout.LINE_START);
        contentPanel.add(vigenereButton, BorderLayout.LINE_END);
        
        contentPanel.add(chooseOption, BorderLayout.CENTER);
        contentPanel.add(text, BorderLayout.LINE_START);
        contentPanel.add(file, BorderLayout.LINE_END);
        
        add(contentPanel, BorderLayout.CENTER);
        setBorder(BorderFactory.createEmptyBorder(0,0,0,0));
        
        // register and add a listener to the buttons
        Choice c = new Choice();
        text.addActionListener(c);
        file.addActionListener(c);
    }
    
    // variables to store key and option strings
    String k;
    String opt;
    
    // set the value to opt if a button is pressed
    public class Choice implements ActionListener{
        public void actionPerformed(ActionEvent event){
            JButton src = (JButton) event.getSource();
           
            if (src.equals(text)){
            	opt = "text";
            	putTogether(opt);
            }
            if (src.equals(file)){
            	opt = "file";
            	putTogether(opt);
            }
        }
    }
    
    // set the value to k by a radio button chosen
	@Override
	public void actionPerformed(ActionEvent e){
		JRadioButton src = (JRadioButton) e.getSource();
        
        if (src.equals(caesarButton)){
        	k = "cc";
        }
        if (src.equals(vigenereButton)){
        	k = "vc";
        }
	}
		
	// show next GUI
	public void putTogether(String opt){
		if(opt.equals("text")){
			Gui2 g2 = new Gui2(k, opt);
			g2.createAndShowGUI2(k, opt);
		}
		else{
			Gui3 g3 = new Gui3(k, opt);
			g3.createAndShowGUI3(k, opt);
		}
	}
	
	// create the GUI and show it
	void createAndShowGUI(){
        //Create and set up the window.
        JFrame frame = new JFrame("Encrypt the Text");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        //Create and set up the content pane.
        JComponent newContentPane = new Gui1();
        newContentPane.setOpaque(true); //content panes must be opaque
        frame.setContentPane(newContentPane);

        //Display the window.
        frame.setSize(900, 300);
        frame.setVisible(true);
    }
}
