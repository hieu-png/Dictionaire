import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class DictionaryApplication extends DictionaryAppAction implements ActionListener {

    private JFrame mainFrame;
    private final Dictionary mainDictionary = new Dictionary();

    protected JMenuBar mainMenuBar;
    protected JMenu mainMenu;
    protected JMenuItem addWordMenu, removeWordMenu;//, copyWord, importWordSimple, importWordAdvanced;

    SqlDataManagement sdm = new SqlDataManagement();

    final int width = 800;
    final int height = 600;
    final int wordListWidth = 150;
    private TextAreaWithImage WordDefineArea;
    private final String imageFolderPath = System.getProperty("user.dir") + "\\Dictionary\\Image\\";

    private JList<String> ListWord = new JList<>((String[]) null);
    private JTextField searchBar;
    private JScrollPane ListScrollPane = new JScrollPane(null);
    private DictionaryManagement dictionaryManagement;

    int newlyAddedWordIndex = -1;
    public DictionaryApplication() {

        sqlInit();
        prepareMenu();
        prepareGUI();
    }


    public void actionPerformed(ActionEvent e) {
        //Them cai nay lam gi the???
        String[] s = {};
        //
        if (e.getSource() == addWordMenu) {
            addWordWindow(mainDictionary);
            UpdateList(s, 0);
            ListWord.setSelectedIndex(newlyAddedWordIndex);


        } else if (e.getSource() == removeWordMenu) {
            if (ListWord.getSelectedIndex() != -1) {
                removeWord(mainDictionary, DictionaryManagement.dictionaryLookup(mainDictionary.getWordArrayList(),ListWord.getSelectedValue()));
                UpdateList(s, 0);

            }
        }

    }


    public void UI(DictionaryApplication dictionaryApplication) {

        mainFrame.setLocationRelativeTo(null);
        mainFrame.setResizable(false);


        Image Icon = Toolkit.getDefaultToolkit().getImage(imageFolderPath + "bookIcon.png" );
        mainFrame.setIconImage(Icon);
        //rqt
        UpdateList(mainDictionary.wordTextArray(), 1);
        SearchBar();
        dictionaryApplication.ShowWordDefinition();
        mainFrame.setVisible(true);
    }

    public void SearchBar() {
        searchBar = new HintTextField("Search...");
        searchBar.setOpaque(true);
        searchBar.setForeground(Color.LIGHT_GRAY);


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
//                if(e.getKeyCode() == KeyEvent.VK_ENTER) {
//
//                }
                Search();
                mainFrame.setVisible(true);
            }
        });

        searchBar.setBounds(27, 9, wordListWidth, 40);
    }
//rqt
    public void Search() {
        String s = searchBar.getText();
        if (!s.equals("")) {
            searchBar.setForeground(Color.BLACK);

            UpdateList(mainDictionary.searchFilter(s), 1);
        } else {

            searchBar.setForeground(Color.LIGHT_GRAY);

            UpdateList(mainDictionary.wordTextArray(), 0);
        }
    }

    public void UpdateList(String[] s, int flag) {


        //TODO
        // Wordlist refresh after each time a word is added or removed to not throws errors


        //flag to show filteredWord
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
        WordDefineArea = new TextAreaWithImage(1, 10, imageFolderPath + "textBackground.png");
        WordDefineArea.setEditable(false);
        WordDefineArea.setCaretPosition(0);

        Font font = new Font("Arial", Font.BOLD, 16);
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

        Image image = loadImageFromFile(imageFolderPath + "background.png", width, height);

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
        mainDictionary.sort();
    }

    public void removeWord(Dictionary dictionary, int index) {
        sdm.delete(dictionary.wordByIndex(index).getText());
        dictionary.removeWord(index);
        WordDefineArea.setText("");
    }


    public void addWordWindow(Dictionary dictionary) {

        JTextField text = new JTextField(5);
        JTextField definition = new JTextField(5);

        JTextField pronunciation = new JTextField(5);
        //Text and Definition text field component must have the same name
        Object[] components = {
                new JLabel("Text"), text,
                new JLabel("Pronunciation"), pronunciation,
                new JLabel("Definition"), definition
        };

        //Only cancel when press cancel.
        while(true) {
            int addWordResult = JOptionPane.showConfirmDialog(null, components,
                    "Enter the word's properties", JOptionPane.OK_CANCEL_OPTION);


            if (addWordResult == JOptionPane.OK_OPTION) {
                String wordAddMessage;
                if (text.getText().isEmpty()) {
                    wordAddMessage = "Please enter the word";
                } else if (pronunciation.getText().isEmpty()) {
                    wordAddMessage = "Please enter the word's pronunciation";
                } else if (definition.getText().isEmpty()) {
                    wordAddMessage = "Please enter the word's definition";
                } else {
                    int i;
                    if((i = mainDictionary.findWord(text.getText())) != -1) {

                        mainDictionary.wordByIndex(i).addDefinition(definition.getText());
                        sdm.updateWord(text.getText(), mainDictionary.wordByIndex(i).getDefinition());

                        wordAddMessage = String.format(
                                "\"%s\" has got a new definition!",text.getText());

                    } else {
                        String def = "/" + pronunciation.getText() + "/" + "- " + definition.getText();
                        sdm.insertData(text.getText(), def);
                        dictionary.addWord(text.getText(), def);
                        wordAddMessage = String.format(
                                "\"%s\" has been success fully added to the dictionary!",text.getText());

                    }
                    JOptionPane.showMessageDialog(null, wordAddMessage);
                    mainDictionary.sort();
                    newlyAddedWordIndex = mainDictionary.findWord(text.getText());
                    break;
                }
                JOptionPane.showMessageDialog(null, wordAddMessage);


            } else {
                break;
            }
        }
    }

}