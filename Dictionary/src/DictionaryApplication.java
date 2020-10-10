import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.IOException;
import java.util.ArrayList;

public class DictionaryApplication extends DictionaryAppAction implements ActionListener {

    private JFrame mainFrame;
    private Dictionary mainDictionary= new Dictionary();

    protected JMenuBar mainMenuBar;
    protected JMenu mainMenu;
    protected JMenuItem addWordMenu, removeWordMenu;//, copyWord, importWordSimple, importWordAdvanced;

    SqlDataManagement sdm = new SqlDataManagement();

    final int width = 800;
    final int height = 600;
    final int wordListWidth = 150;
    private JTextArea WordDefineArea;
    private final String imageFolderPath = System.getProperty("user.dir") + "\\Dictionary\\Image\\";

    private JList<String> ListWord = new JList<>((String[]) null);
    private JTextField searchBar;
    //private String[] textWord;
    private JScrollPane ListScrollPane = new JScrollPane(null);
    private DictionaryManagement dictionaryManagement;
    //private ArrayList<String> words = null;

    public DictionaryApplication() {

        sqlInit();
        prepareMenu();
        prepareGUI();
    }



    public void actionPerformed(ActionEvent e) {
        String[] s = {};
        if (e.getSource() == addWordMenu) {
            addWordWindow(mainDictionary);
            UpdateList(s,0);



        } else if (e.getSource() == removeWordMenu) {
            if (ListWord.getSelectedIndex() != -1) {
                removeWord(mainDictionary, ListWord.getSelectedIndex());
                UpdateList(s,0);

            }
        }

    }



    public void UI(DictionaryApplication dictionaryApplication) {

        mainFrame.setLocationRelativeTo(null);
        mainFrame.setResizable(false);
        ImageIcon icon = new ImageIcon(imageFolderPath + "\\2.png");
        mainFrame.setIconImage(icon.getImage());







        //rqt
        UpdateList(mainDictionary.wordTextArray(),1);
        SearchBar();
        dictionaryApplication.ShowWordDefinition();
        mainFrame.setVisible(true);
    }

    public void SearchBar() {
        searchBar = new HintTextField("Search...");
        searchBar.setOpaque(true);
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

                Search();
                mainFrame.setVisible(true);
            }
        });

        searchBar.setBounds(27, 9, wordListWidth, 40);
    }

    public void Search() {
        String s = searchBar.getText();
        if (!s.equals("")) {

            /*
            ArrayList<String> FilteredWords = new ArrayList<>();


            DictionaryManagement.searchFilter(s, words, FilteredWords);

            String[] FilteredWords1 = new String[FilteredWords.size()];
            for (int i = 0; i < FilteredWords.size(); i++) {
                FilteredWords1[i] = FilteredWords.get(i);

            }


            UpdateList(FilteredWords1,1);
            */

            //String[] FilteredWord;
            UpdateList(mainDictionary.searchFilter(s),1);

        }
        else{
            UpdateList(mainDictionary.wordTextArray(),0);
        }
    }

    public void UpdateList(String[] s, int flag) {

        //ListWord.updateUI();
        //TODO
        // Wordlist refresh after each time a word is added or removed to not throws errors


        //flag de show filteredWord
        if (flag == 1) {
            ListWord = new JList<>(s);
        } else {
            ListWord = new JList<>(mainDictionary.wordTextArray());
        }
        ListScrollPane.remove(ListWord);
        mainFrame.remove(ListScrollPane);


        ListWord.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        ListWord.setVisibleRowCount(20);
        Font font = new Font("Arial", Font.BOLD, 14);
        ListWord.setFont(font);
        //action for defineArea
        ListWord.addListSelectionListener(e -> {
            if (!e.getValueIsAdjusting()) {
                String wordNewAfterFilter = ListWord.getSelectedValue();
                int i = DictionaryManagement.dictionaryLookup(
                        mainDictionary.getWordArrayList(), wordNewAfterFilter);

                WordDefineArea.setText(mainDictionary.getWordArrayList().get(i).getText() +
                        "  " + mainDictionary.getWordArrayList().get(i).getDefinitionLine());
            }
        });
        ListScrollPane = new JScrollPane(ListWord);
        ListScrollPane.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);

        mainFrame.add(ListScrollPane);
        ListScrollPane.setBounds(27, 50, wordListWidth, (int) ((double) height * 0.8));

        mainFrame.setVisible(true);
    }





    public static void Start() {
        DictionaryApplication dictionaryApplication = new DictionaryApplication();
        dictionaryApplication.UI(dictionaryApplication);
    }

    public static void main(String[] args) {
        Start();
    }

    public void ShowWordDefinition() {
        WordDefineArea = new JTextArea(1, 10);
        WordDefineArea.setEditable(false);
        WordDefineArea.setCaretPosition(0);

        Font font = new Font("Arial", Font.BOLD, 14);
        WordDefineArea.setFont(font);
        JScrollPane outputScrollPane = new JScrollPane(WordDefineArea,
                ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED,
                ScrollPaneConstants.HORIZONTAL_SCROLLBAR_AS_NEEDED);

        mainFrame.add(outputScrollPane);
        outputScrollPane.setBounds(180, 50, 550, height - 120);
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

    public void prepareGUI() {
        mainFrame = new JFrame("Dictionary 12.0");

        mainFrame.setSize(width, height);
        mainFrame.getContentPane().setBackground(Color.RED);

        Image image = loadImageFromFile(imageFolderPath + "Blue_background.png", width, height);

        mainFrame.setContentPane(new ImagePanel(image));


        //Do what when close window
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

    public void sqlInit() {
        sdm.connect();
        //sdm.createTable();
        //sdm.insertFromFileDirect(System.getProperty("user.dir") +
        //        "\\Dictionary\\anhviet109K.txt");
        sdm.insertToDictionary(mainDictionary);
    }


}