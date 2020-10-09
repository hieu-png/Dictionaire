import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.IOException;
import java.util.ArrayList;

//import javax.swing.text.DefaultCaret;
//import java.awt.event.ActionEvent;
//import java.io.File;

public class DictionaryApplication extends DictionaryAppAction implements ActionListener {

    private JFrame mainFrame;
    private Dictionary mainDictionary;

    protected JMenuBar mainMenuBar;
    protected JMenu mainMenu;
    protected JMenuItem addWordMenu, removeWordMenu;//, copyWord, importWordSimple, importWordAdvanced;


    final int width = 800, height = 600, outerMargin = 20, wordListWidth = 150;
    final int buttonSizeSquare = 40;
    private JTextArea WordDefineArea;
    private final String imageFolderPath = System.getProperty("user.dir");

    private JList<String> ListWord = new JList<>((String[]) null);
    private JTextField searchBar;
    private String[] textWord;
    private JScrollPane ListScrollPane = new JScrollPane(null);



    public DictionaryApplication() {

        prepareMenu();
        prepareGUI();
    }


    public void prepareGUI() {
        mainFrame = new JFrame("Dictionary 12.0");

        mainFrame.setSize(width, height);
        mainFrame.getContentPane().setBackground(Color.RED);

        Image image = loadImageFromFile(imageFolderPath + "\\Blue_Background.png", width, height);

        mainFrame.setContentPane(new ImagePanel(image));


        mainFrame.setLayout(null);
        mainFrame.addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent windowEvent) {
                System.exit(0);
            }
        });

        JPanel controlPanel = new JPanel();
        controlPanel.setLayout(null);

        mainFrame.add(ListScrollPane);

        mainFrame.setJMenuBar(mainMenuBar);
        mainFrame.add(controlPanel);
        mainFrame.setVisible(true);
    }


    public void prepareMenu() {
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
        if (e.getSource() == addWordMenu) {
            addWordWindow(mainDictionary);

            UpdateList();


        } else if (e.getSource() == removeWordMenu) {
            if (ListWord.getSelectedIndex() != -1) {
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

        assert words != null;
        mainDictionary = d.WordFromBigFile(words);

        int N = mainDictionary.wordCount();

        //rqt
        textWord = new String[N];
        for (int i = 0; i < N; i++) {
            textWord[i] = mainDictionary.wordByIndex(i).getText();
            //System.out.println(textWord[i]);
        }
        dictionaryApplication.ShowListMenu(textWord);
        SearchBar(dictionaryApplication, textWord);
        dictionaryApplication.ShowWordDefinition();
        mainFrame.setVisible(true);
    }

    public void ShowListMenu(String[] s) {
        ListScrollPane.remove(ListWord);
        mainFrame.remove(ListScrollPane);
        ListWord = new JList<>(s);

        ListWord.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        ListWord.setVisibleRowCount(20);
        Font font = new Font("Arial", Font.BOLD, 13);
        ListWord.setFont(font);
        //action for defineArea
        ListWord.addListSelectionListener(e -> {
            if (!e.getValueIsAdjusting()) {
                String wordNewAfterFilter = ListWord.getSelectedValue();
                int i = DictionaryManagement.dictionaryLookup(mainDictionary.getWordArrayList(),wordNewAfterFilter);
                WordDefineArea.setText(mainDictionary.getWordArrayList().get(i).getText() + "  " + mainDictionary.getWordArrayList().get(i).getDefinition());
            }
        });
        ListScrollPane = new JScrollPane(ListWord);
        ListScrollPane.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);

        mainFrame.add(ListScrollPane);
        ListScrollPane.setBounds(27, 50, wordListWidth, (int) ((double) height * 0.8));

        mainFrame.setVisible(true);
    }

    public void SearchBar(DictionaryApplication dictionaryApplication, String[] a) {
        searchBar = new HintTextField("Search...");
        searchBar.setOpaque(false);
        searchBar.setForeground(Color.DARK_GRAY);


        Font f = new Font("Arial", Font.BOLD, 20);
        searchBar.setFont(f);
        mainFrame.add(searchBar);


        //searchBar nhan su event Filter
        searchBar.addKeyListener(new KeyListener() {
            @Override
            public void keyTyped(KeyEvent e) {

            }

            @Override
            public void keyPressed(KeyEvent e) {

            }

            @Override
            public void keyReleased(KeyEvent e) {
                //gỡ Jlist(ListWord) cũ
                //thay đổi nội dung Jlist
                //add lại Jlist vào

                dictionaryApplication.Search(a, dictionaryApplication);
                mainFrame.setVisible(true);
            }
        });

        searchBar.setBounds(50, 10, width - 100, 40);
    }

    public void Search(String[] words, DictionaryApplication dictionaryApplication) {
        String s = searchBar.getText();
        if (!s.equals("")) {


            ArrayList<String> FilteredWords = new ArrayList<>();
            DictionaryManagement.searchFilter(s, words, FilteredWords);

            String[] FilteredWords1 = new String[FilteredWords.size()];
            for (int i = 0; i < FilteredWords.size(); i++) {
                FilteredWords1[i] = FilteredWords.get(i);

            }


            dictionaryApplication.ShowListMenu(FilteredWords1);
        }
    }
    public void UpdateList() {

        //ListWord.updateUI();
        //TODO
        /* Wordlist refresh after each time a word is added or removed to not throws errors
         */

        int N = mainDictionary.wordCount();
        textWord = new String[N];
        for (int i = 0; i < N; i++) {
            textWord[i] = mainDictionary.wordByIndex(i).getText();
        }
        ListScrollPane.remove(ListWord);
        mainFrame.remove(ListScrollPane);
        ListWord = new JList<>(textWord);

        ListWord.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        ListWord.setVisibleRowCount(20);
        Font font = new Font("Arial", Font.BOLD, 13);
        ListWord.setFont(font);
        //action for defineArea
        ListWord.addListSelectionListener(e -> {
            if (!e.getValueIsAdjusting()) {
                String wordNewAfterFilter = ListWord.getSelectedValue();
                int i = DictionaryManagement.dictionaryLookup(mainDictionary.getWordArrayList(),wordNewAfterFilter);
                WordDefineArea.setText(mainDictionary.getWordArrayList().get(i).getText() + "  " + mainDictionary.getWordArrayList().get(i).getDefinition());
            }
        });
        ListScrollPane = new JScrollPane(ListWord);
        ListScrollPane.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);

        mainFrame.add(ListScrollPane);
        ListScrollPane.setBounds(27, 50, wordListWidth, (int) ((double) height * 0.8));

        mainFrame.setVisible(true);
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