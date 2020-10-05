import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

public class DictionaryApplication {

    private JFrame mainFrame;
    private JPanel controlPanel;
    final int width = 800, height = 600;
    private JTextArea WordDefineArea;


    public DictionaryApplication() {
        prepareGUI();
    }


    public void prepareGUI() {
        mainFrame = new JFrame("Dictionary 2.0");
        mainFrame.setSize(width, height);
        mainFrame.getContentPane().setBackground(Color.RED);
        Image image = null;
        try {
            image = ImageIO.read(new File("C:\\Users\\pc\\OneDrive\\" +
                    "Máy tính\\beautiful blue color background\\Blue_Background.png"))
                    .getScaledInstance(width, height, Image.SCALE_DEFAULT);
        } catch (IOException e) {
            e.printStackTrace();
        }
        mainFrame.setContentPane(new ImagePanel(image));


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

    public void UI(DictionaryApplication dictionaryApplication) {
        mainFrame.setLocationRelativeTo(null);

        DictionaryManagement d = new DictionaryManagement();
        ArrayList<String> words = null;
        try {
            words = d.insertFromFileAdvanced();
        } catch (IOException e) {
            e.printStackTrace();
        }
        Dictionary dictionary = d.WordFromBigFile(words);
        int N = dictionary.wordCount();
        String[] textWord = new String[N];
        for (int i = 0; i < N; i++) {
            textWord[i] = dictionary.wordByIndex(i).getText();
            //System.out.println(textWord[i]);
        }
        dictionaryApplication.ShowListMenu(textWord,dictionary);
        SearchBar();
        dictionaryApplication.ShowWordDefine(dictionary);
        ShowWordButton();
        RemoveButton();


        mainFrame.setVisible(true);
    }

    public void SearchBar() {
        JTextField searchBar = new HintTextField("Search...");
        searchBar.setOpaque(false);
        searchBar.setForeground(Color.DARK_GRAY);


        Font f = new Font("Arial", Font.BOLD, 20);
        searchBar.setFont(f);
        mainFrame.add(searchBar);
        searchBar.setBounds(15, 10, width - 100, 40);
    }

    public void ShowWordButton() {
        Image image = null;
        try {
            image = ImageIO.read(new File("C:\\Users\\pc\\OneDrive\\" +
                    "Máy tính\\beautiful blue color background\\book.png"))
                    .getScaledInstance(18, 18, Image.SCALE_DEFAULT);
        } catch (IOException e) {
            e.printStackTrace();
        }
        JButton ShowAllWordButton = new JButton();
        assert image != null;
        ShowAllWordButton.setIcon(new ImageIcon(image));


        mainFrame.add(ShowAllWordButton);
        ShowAllWordButton.setBounds(0, 50, 28, 28);


        JButton AddWordButton = new JButton("Add+");
        mainFrame.add(AddWordButton);
        AddWordButton.setBounds(177, 50, 80, 28);
        AddWordButton.addActionListener(new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                AddWordWindow();

            }
        });
    }

    public void RemoveButton() {
        JButton RemoveButton = new JButton("Remove");
        mainFrame.add(RemoveButton);
        RemoveButton.setBounds(256, 50, 80, 28);
    }

    //------------function display AddWordWindow--------------------------------

    public static void AddWordWindow() {


        JTextField text = new JTextField(5);
        JTextField definition = new JTextField(5);

        //Text and Definition text field component must have the same name
        Object[] components = {
                new JLabel("Text"), text,
                new JLabel("Definition"), definition
        };

        int addWordResult = JOptionPane.showConfirmDialog(null, components,
                "Enter the word's text and it's definition", JOptionPane.OK_CANCEL_OPTION);


        if (addWordResult == JOptionPane.OK_OPTION) {
            if (text.getText() != null) {
                definition.getText();
            }//TODO :ADD WORD
        }
    }

    public void ShowListMenu(String[] s,Dictionary d) {
        JList<String> ListWord = new JList<>(s);
        ListWord.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        ListWord.setVisibleRowCount(20);
        Font font = new Font("Arial", Font.BOLD, 12);
        ListWord.setFont(font);
        ListWord.addListSelectionListener(new ListSelectionListener() {
            @Override
            public void valueChanged(ListSelectionEvent e) {
                if(!e.getValueIsAdjusting()){
                    int i = ListWord.getSelectedIndex();
                    WordDefineArea.setText(d.getWordArrayList().get(i).getText() +"  " + d.getWordArrayList().get(i).getDefinition());
                }
            }
        });
        JScrollPane ListScrollPane = new JScrollPane(ListWord);
        ListScrollPane.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
        mainFrame.add(ListScrollPane);
        ListScrollPane.setBounds(27, 50, 150, height - 110);


    }

    public void ShowWordDefine(Dictionary d) {
        WordDefineArea = new JTextArea(1, 10);
        WordDefineArea.setEditable(true);
        WordDefineArea.setEditable(false);
        JScrollPane outputScrollPane = new JScrollPane(WordDefineArea, ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED,
                ScrollPaneConstants.HORIZONTAL_SCROLLBAR_AS_NEEDED);
        //WordDefineArea.append(" "+d.getWordArrayList().get(1).getDefinition());
        mainFrame.add(outputScrollPane);
        outputScrollPane.setBounds(177, 79, 539, 460);
    }


    public static void Start() {
        DictionaryApplication dictionaryApplication = new DictionaryApplication();
        dictionaryApplication.UI(dictionaryApplication);


    }

    public static void main(String[] args) {
        Start();
    }


}


//    public static void AddWordWindow() {
//        JFrame AddWordFrame = new JFrame("Add new word");
//        AddWordFrame.setSize(600, 400);
//
//        JPanel midControl = new JPanel();
//        midControl.setSize(400, 300);
//        midControl.setLayout(new BoxLayout(midControl, BoxLayout.Y_AXIS));
//
//
//        //AddWordFrame.getContentPane().setBackground(Color.RED);
//        //AddWordFrame.setLayout(null);
////        AddWordFrame.addWindowListener(new WindowAdapter() {
////            @Override
////            public void windowOpened(WindowEvent e) {
////            }
////        });
//
//        Font font = new Font("Arial", Font.BOLD, 14);
//
//        JTextField AddBar = new HintTextField("Add...");
//        AddBar.setOpaque(true);
//        midControl.add(AddBar);
//        AddBar.setFont(font);
//        AddBar.setBounds(15, 10, 500, 40);
//
//        JLabel text = new JLabel("Define");
//        Font font2 = new Font("Arial", Font.BOLD, 10);
//        text.setFont(font2);
//        text.setBounds(15, 60, 150, 20);
//        midControl.add(text);
//
//        JTextArea DefineBar = new HintTextArea("");
//
//        JScrollPane DefineBarScrollPane = new JScrollPane(DefineBar);
//        DefineBarScrollPane.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
//        DefineBar.setRows(20);
//        DefineBar.setColumns(20);
//        midControl.add(DefineBarScrollPane);
//        DefineBar.setFont(font);
//        DefineBar.setBounds(15, 90, 500, 200);
//        //AddWordFrame.add(DefineBar);
//
//
//        AddWordFrame.getContentPane().add(midControl);
//
//
//        AddWordFrame.setLocationRelativeTo(null);
//
//        AddWordFrame.setVisible(true);
//    }