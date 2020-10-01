import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.border.EtchedBorder;
import javax.swing.border.TitledBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.File;
import java.io.IOException;

public class DictionaryApplication {

    private JFrame mainFrame;
    private JPanel controlPanel;

    //Link hinh anh

    //String imagePath = "C:\\Users\\pc\\OneDrive\\" +
    //        "Máy tính\\beautiful blue color background\\image.png";
    String imagePath = "D:\\CocoTit.png";

    public DictionaryApplication() {
        prepareGUI();
    }




    public void prepareGUI() {
        mainFrame = new JFrame("Dictionary 2.0");
        mainFrame.setSize(800, 600);
        mainFrame.getContentPane().setBackground(Color.RED);
        Image image = null;
        try {
            image = ImageIO.read(new File(imagePath))
                    .getScaledInstance(800, 600, Image.SCALE_DEFAULT);
        } catch (IOException e) {
            e.printStackTrace();
        }
        Panel bg = new Panel();

        mainFrame.setContentPane(bg);



        mainFrame.setLayout(null);
        mainFrame.addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent windowEvent) {
                System.exit(0);
            }
        });


        controlPanel = new JPanel();
        controlPanel.setLayout(null);
        mainFrame.add(controlPanel);
        mainFrame.setVisible(true);


    }

    public void UI(){

        mainFrame.setLocationRelativeTo(null);
        JTextArea searchBar = new HintTextArea("Search...");

        //JLabel label = new JLabel("ssssss");
        //mainFrame.add(label);
        //label.setBounds(20,40,100,30);

        Font f = new Font("Arial", Font.BOLD, 20);
        searchBar.setFont(f);
        mainFrame.add(searchBar);
        searchBar.setBounds(15, 10, 700, 40);

        Image image = null;
        try {
            image = ImageIO.read(new File(imagePath))
                    .getScaledInstance(800, 600, Image.SCALE_DEFAULT);
        } catch (IOException e) {
            e.printStackTrace();
        }
        JButton ShowAllWordButton = new JButton();
        assert image != null;
        ShowAllWordButton.setIcon(new ImageIcon(image));


        mainFrame.add(ShowAllWordButton);
        ShowAllWordButton.setBounds(0, 50, 28, 28);


        JButton OpenAddWordButton = new JButton("Add+");
        mainFrame.add(OpenAddWordButton);
        OpenAddWordButton.setBounds(200, 50, 80, 28);

        OpenAddWordButton.addActionListener(new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                AddWordWindow();

            }
        });


        mainFrame.setVisible(true);
    }



    //------------function display AddWordWindow--------------------------------

//----------------------------------------DKA xem cai layout cua AddBar va DefineBar xem lam sao cho no tach nhau ra y------------------------
   /* public static void AddWordWindow(){
        JFrame AddWordFrame = new JFrame("Add new word");

        AddWordFrame.setSize(400, 200);

        JPanel midControl = new JPanel();

        midControl.setSize(400, 200);

        midControl.setLayout(new GridBagLayout());




        Font font = new Font("Arial", Font.BOLD, 14);
        JTextField AddBar = new HintTextField("Add...");


        AddBar.setFont(font);
        AddBar.setBounds(15, 10, 500, 40);


        /*
        JTextArea DefineBar = new HintTextArea("");

        JScrollPane DefineBarScrollPane = new JScrollPane(DefineBar);
        DefineBarScrollPane.setVerticalScrollBarPolicy ( ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS );
        DefineBar.setRows(20);
        DefineBar.setColumns(20);
       midControl.add(DefineBarScrollPane);
        DefineBar.setFont(font);
        DefineBar.setBounds(15, 70, 500, 250);
        AddWordFrame.add(DefineBar);




        AddWordFrame.getContentPane().add(midControl);

        AddWordFrame.setLocationRelativeTo(null);


        AddWordFrame.setVisible(true);
    }
    */
    public static void AddWordWindow() {



        JTextField text = new JTextField(5);
        JTextField definition = new JTextField(5);

        //Text and Definition text field component must have the same name
        Object[] components = {
            new JLabel("Text"),text,
            new JLabel("Definition"), definition
        };

       int addWordResult = JOptionPane.showConfirmDialog(null, components,
                "Enter the word's text and it's definition", JOptionPane.OK_CANCEL_OPTION);


        if (addWordResult == JOptionPane.OK_OPTION) {
        if (text.getText() != null && definition.getText() != null) {
            //TODO :ADD WORD


        }

        } else {

        }
    }


    public static void main(String[] args) {
        DictionaryApplication dictionary = new DictionaryApplication();
        dictionary.UI();
    }
}
