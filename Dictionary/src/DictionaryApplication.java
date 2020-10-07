//import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
//import javax.swing.text.DefaultCaret;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
//import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

//import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

public class DictionaryApplication extends DictionaryAppAction implements ActionListener   {

    private JFrame mainFrame;
    private JPanel controlPanel;
    private Dictionary mainDictionary;

    protected JMenuBar mainMenuBar;
    protected JMenu mainMenu;
    protected JMenuItem addWordMenu, removeWordMenu;//, copyWord, importWordSimple, importWordAdvanced;

    JList<String> ListWord;

    final int width = 800, height = 600, outerMargin = 20, wordListWidth = 150;
    final int buttonSizeSquare = 40;
    private JTextArea WordDefineArea;
    private final String imageFolderPath = System.getProperty("user.dir") + "\\Dictionary\\Image\\";



    public DictionaryApplication() {

        prepareMenu();
        prepareGUI();
    }



    public void prepareGUI() {
        mainFrame = new JFrame("Dictionary 2.0");


        mainFrame.setSize(width, height);
        mainFrame.getContentPane().setBackground(Color.RED);

        Image image = loadImageFromFile(imageFolderPath + "Blue_Background.png", width, height);

        mainFrame.setContentPane(new ImagePanel(image));


        mainFrame.setLayout(null);
        mainFrame.addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent windowEvent) {
                System.exit(0);
            }
        });


        controlPanel = new JPanel();
        controlPanel.setLayout(null);

        mainFrame.setJMenuBar(mainMenuBar);
        mainFrame.add(controlPanel);
        mainFrame.setVisible(true);
    }



    public void prepareMenu()  {
        mainMenu = new JMenu("Edit");
        mainMenuBar = new JMenuBar();
        addWordMenu = new JMenuItem("Add a new word");
        addWordMenu.addActionListener(this);


        removeWordMenu = new JMenuItem("Remove selected word");
        removeWordMenu.addActionListener(this);



        mainMenu.add(addWordMenu);
        mainMenu.add(removeWordMenu);

        mainMenuBar.add(mainMenu);

    }

    public void actionPerformed(ActionEvent e) {
        if(e.getSource() == addWordMenu) {
            addWordWindow(mainDictionary);

            UpdateList();

        } else if(e.getSource() == removeWordMenu) {
            if(ListWord.getSelectedIndex() != -1) {
                removeWord(mainDictionary, ListWord.getSelectedIndex());

                UpdateList();
            }
        }

    }

    public void UI(DictionaryApplication dictionaryApplication) {

        mainFrame.setLocationRelativeTo(null);
        mainFrame.setResizable(false);

        DictionaryManagement d = new DictionaryManagement();

        ArrayList<String> words = null;
        try {
            words = d.insertFromFileAdvanced();
        } catch (IOException e) {
            e.printStackTrace();
        }

        mainDictionary = d.WordFromBigFile(words);
        /*
        int N = mainDictionary.wordCount();


        String[] textWord = new String[N];
        for (Word word : mainDictionary.getWordArrayList()) {
            textWord[i] = mainDictionary.wordByIndex(i).getText();
        }*/
        dictionaryApplication.ShowListMenu();

        SearchBar();

        dictionaryApplication.ShowWordDefinition();

        ShowWordButton();
        RemoveButton();


        mainFrame.setVisible(true);
    }

    public void SearchBar() {
        JTextField searchBar = new HintTextField("Search...");
        searchBar.setOpaque(false);
        searchBar.setForeground(Color.DARK_GRAY);


        Font f = new Font("Arial", Font.BOLD, 18);
        searchBar.setFont(f);
        mainFrame.add(searchBar);
        searchBar.setBounds(outerMargin, 10, 150 , 40);
    }

    public void ShowWordButton() {
        Image image = loadImageFromFile(imageFolderPath + "book.png",18,18);
        JButton ShowAllWordButton = new JButton();
        assert image != null;

        ShowAllWordButton.setIcon(new ImageIcon(image));

        mainFrame.add(ShowAllWordButton);
        ShowAllWordButton.setBounds(0, 50, 28, 28);

        JButton AddWordButton = new JButton("Add+");
        mainFrame.add(AddWordButton);
        AddWordButton.setBounds(outerMargin + wordListWidth + 10, 20, 80, 28);
        AddWordButton.addActionListener(new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                //AddWordWindow();

            }
        });
    }

    public void RemoveButton() {

        Image image = loadImageFromFile(imageFolderPath + "trashBin.png",buttonSizeSquare,buttonSizeSquare);

        JButton RemoveButton = new JButton();
        RemoveButton.setIcon(new ImageIcon(image));
        mainFrame.add(RemoveButton);
        RemoveButton.setBounds(width - buttonSizeSquare , buttonSizeSquare,
                buttonSizeSquare, buttonSizeSquare);
    }

    public void UpdateList() {

        //ListWord.updateUI();
        //TODO
        /* Wordlist refresh after each time a word is added or removed to not throws errors


         */
        ArrayList<String> arr = new ArrayList<>();

        for(Word word : mainDictionary.getWordArrayList() ) {
            arr.add(word.getText());
        }

        ListWord = new JList<String>(arr.toArray(new String[0]));
    }

    public void ShowListMenu() {

        UpdateList();

        ListWord.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        ListWord.setVisibleRowCount(20);

        Font font = new Font("Arial", Font.BOLD, 18);
        ListWord.setFont(font);

        //action for defineArea
        ListWord.addListSelectionListener(e -> {
            if(!e.getValueIsAdjusting()) {
                int i = ListWord.getSelectedIndex();
                WordDefineArea.setText(mainDictionary.wordByIndex(i).getText() +
                                "  " + mainDictionary.wordByIndex(i).getDefinition());
            }
        });

        JScrollPane ListScrollPane = new JScrollPane(ListWord);
        ListScrollPane.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);


        mainFrame.add(ListScrollPane);
        ListScrollPane.setBounds(outerMargin, 50, wordListWidth, (int)((double) height * 0.8));


    }



    public void ShowWordDefinition() {
        WordDefineArea = new JTextArea(1, 10);
        WordDefineArea.setEditable(false);
        WordDefineArea.setCaretPosition(0);

        Font font = new Font("Arial", Font.BOLD, 14);
        WordDefineArea.setFont(font);
        JScrollPane outputScrollPane = new JScrollPane(WordDefineArea, ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED,
                ScrollPaneConstants.HORIZONTAL_SCROLLBAR_AS_NEEDED);
        
        mainFrame.add(outputScrollPane);
        outputScrollPane.setBounds(180, 50, 550, height - 120);
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