import javax.swing.*;
import java.awt.*;
import java.awt.event.*;


public class DictionaryApplication extends DictionaryAppAction implements ActionListener {

    private JFrame mainFrame;
    private final Dictionary dictionaryMain = new Dictionary();
    private final Dictionary dictionaryFavourite = new Dictionary();
    private final Dictionary dictionaryHistory = new Dictionary();
    private Dictionary dictionaryToShow;
    protected JMenuBar mainMenuBar;
    protected JMenu mainMenu, fileMenu;
    protected JMenuItem addWordOption, removeWordOption, speakWordOption, editWordOption,
                        exitOption


    ;//, copyWord, importWordSimple, importWordAdvanced;


    final int width = 800;
    final int height = 600;
    final int wordListWidth = 150;
    private JButton speakButton, addButton, removeButton, editButton;

    private TextAreaWithImage textAreaDefinition;
    private final String imageFolderPath = System.getProperty("user.dir") + "\\Dictionary\\Image\\";

    private JList<String> wordList = new JList<>((String[]) null);
    private JScrollPane wordListScrollBar = new JScrollPane(null);

    private JTextField textSearchBar;
    private final textToSpeech speaker = new textToSpeech();
    //A null string
    String[] nullString = {};
    public DictionaryApplication() {

        sqlInit(dictionaryMain);
        prepareMenu();
        prepareGUI();
    }



    public void UI(DictionaryApplication dictionaryApplication) {

        mainFrame.setLocationRelativeTo(null);

        mainFrame.setResizable(false);

        mainFrame.setIconImage(iconFromFile("bookIcon"));

        dictionaryToShow = dictionaryMain;

        prepareWordList();

        UpdateList(dictionaryMain.wordTextArray(), 1);

        prepareTextSearchBar();
        dictionaryApplication.prepareTextAreaDefinition();
        mainFrame.setVisible(true);
    }

    Image iconFromFile(String fileName) {
        return Toolkit.getDefaultToolkit().getImage(imageFolderPath + fileName);
    }
    /**
     *
     * @param s string array to show in list.
     * @param flag whether or not to show the filtered word.
     */
    public void UpdateList(String[] s, int flag) {

        if (flag == 1) {
            wordList = new JList<>(s);
        } else {
            wordList = new JList<>(dictionaryMain.wordTextArray());
        }


        wordList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        wordList.setVisibleRowCount(20);
        wordListScrollBar.remove(wordList);
        mainFrame.remove(wordListScrollBar);

        Font font = new Font("Arial", Font.BOLD, 20);
        wordList.setFont(font);

        //action for defineArea
        wordList.addListSelectionListener(e -> {
            if (!e.getValueIsAdjusting()) {
                String wordNewAfterFilter = wordList.getSelectedValue();
                int i = DictionaryManagement.dictionaryLookup(
                        dictionaryToShow.getWordArrayList(), wordNewAfterFilter);

                textAreaDefinition.setText(dictionaryToShow.getWordArrayList().get(i).getText() +
                        "  " + dictionaryToShow.getWordArrayList().get(i).getDefinitionLine());
            }
        });

        wordListScrollBar = new JScrollPane(wordList);
        wordListScrollBar.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);

        mainFrame.add(wordListScrollBar);
        wordListScrollBar.setBounds(27, 50, wordListWidth, (int) ((double) height * 0.8));
        mainFrame.setVisible(true);
    }

    public static void main(String[] args) {
        DictionaryApplication dictionaryApplication = new DictionaryApplication();
        dictionaryApplication.UI(dictionaryApplication);
    }

    public void prepareWordList() {

    }
    public void prepareTextAreaDefinition() {
        textAreaDefinition = new TextAreaWithImage(1, 10, imageFolderPath + "textBackground.png");
        textAreaDefinition.setEditable(false);
        textAreaDefinition.setCaretPosition(0);

        Font font = new Font("Arial", Font.BOLD, 16);
        textAreaDefinition.setFont(font);
       // mainFrame.add(textAreaDefinition);
       // textAreaDefinition.setBounds(180, 50, 550, height - 120);
        JScrollPane outputScrollPane = new JScrollPane(textAreaDefinition,
                ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED,
                ScrollPaneConstants.HORIZONTAL_SCROLLBAR_AS_NEEDED);
        mainFrame.add(outputScrollPane);
        outputScrollPane.setBounds(180, 50, 550, height - 120);

            //outputScrollPane.setAlignmentX(0);
            //outputScrollPane.setAlignmentY(0);


    }

    public void prepareMenu() {
        fileMenu = new JMenu("File");
        mainMenu = new JMenu("Edit");

        mainMenuBar = new JMenuBar();

        addWordOption = new JMenuItem("Add a new word");
        addWordOption.addActionListener(this);


        removeWordOption = new JMenuItem("Remove selected word");
        removeWordOption.addActionListener(this);

        editWordOption = new JMenuItem("Edit this word");
        editWordOption.addActionListener(this);

        speakWordOption = new JMenuItem("Speak selected word");
        speakWordOption.addActionListener(this);

        exitOption = new JMenuItem("Exit the program");
        exitOption.addActionListener(this);
        fileMenu.add(exitOption);

        mainMenu.add(addWordOption);
        mainMenu.add(removeWordOption);
        mainMenu.add(editWordOption);

        mainMenu.add(speakWordOption);
        mainMenuBar.add(fileMenu);

        mainMenuBar.add(mainMenu);
    }

    public void appAddWord() {
        if(addWordWindow(dictionaryMain)) {
        UpdateList(dictionaryMain.wordTextArray(), 0);
        wordList.setSelectedIndex(newlyAddedWordIndex);
        }
    }
    public void appRemoveWord() {
        if(wordList.getSelectedIndex() != -1) {
        removeWord(dictionaryMain,
                DictionaryManagement.dictionaryLookup(dictionaryMain.getWordArrayList(),
                        wordList.getSelectedValue())
        );
        UpdateList(nullString, 0);
        textAreaDefinition.setText("");
            textSearchBar.setText("");
            wordList.setSelectedIndex(0);
    }
    }
    public void appEditWord() {
        if(wordList.getSelectedIndex() != -1) {
            if(editWord(dictionaryMain,
                    DictionaryManagement.dictionaryLookup(dictionaryMain.getWordArrayList(),
                            wordList.getSelectedValue())
            )) {
                UpdateList(nullString, 0);
                textAreaDefinition.setText("");
            }
        }
    }
    public void actionPerformed(ActionEvent e) {
        Object source = e.getSource();
        //When add word is clicked
        if (source == addWordOption) {
            appAddWord();

        } else if (source == removeWordOption) {
                appRemoveWord();

        } else if(source == exitOption) {
            System.exit(0);
        } else if(source == speakWordOption) {
            if(wordList.getSelectedIndex() != -1)
                speaker.speakWord(wordList.getSelectedValue());
        } else if(source == editWordOption) {
            appEditWord();
        }

    }



    public void prepareGUI() {
        mainFrame = new JFrame("Dictionaire 1.0");

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

        mainFrame.add(wordListScrollBar);


        prepareButton();

        mainFrame.setJMenuBar(mainMenuBar);
        mainFrame.add(controlPanel);
        mainFrame.setVisible(true);
    }

    public void prepareButton() {
        int offset= 180;
        speakButton = new JButton(new ImageIcon(
                imageFolderPath + "speakerIcon.png"));
        speakButton.setBounds(offset,10,40,40);

        speakButton.addActionListener(e -> {
            if(wordList.getSelectedIndex() != -1)
                speaker.speakWord(wordList.getSelectedValue());
        });

        addButton = new JButton(new ImageIcon(
                imageFolderPath + "addIcon.png"));
        addButton.setBounds(offset + 45,10,40,40);

        addButton.addActionListener(e -> {
            appAddWord();
        });

        removeButton = new JButton(new ImageIcon(
                imageFolderPath + "removeIcon.png"));
        removeButton.setBounds(offset + 90,10,40,40);

        removeButton.addActionListener(e -> {
            appRemoveWord();
        });

        editButton = new JButton(new ImageIcon(
                imageFolderPath + "editIcon.png"));
        editButton.setBounds(offset + 90 + 45,10,40,40);

        editButton.addActionListener(e -> {
            appEditWord();
        });

        mainFrame.add(speakButton);
        mainFrame.add(addButton);
        mainFrame.add(removeButton);
        mainFrame.add(editButton);

    }




    public void prepareTextSearchBar() {
        textSearchBar = new HintTextField("Search...");
        textSearchBar.setOpaque(true);
        textSearchBar.setForeground(Color.LIGHT_GRAY);


        Font f = new Font("Arial", Font.BOLD, 20);
        textSearchBar.setFont(f);
        mainFrame.add(textSearchBar);


        //searchBar nhan su event Filter
        textSearchBar.addKeyListener(new KeyListener() {
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
                Search(dictionaryMain);
                mainFrame.setVisible(true);
            }
        });

        textSearchBar.addActionListener(e-> {
            wordList.setSelectedIndex(0);
        });

        textSearchBar.setBounds(27, 9, wordListWidth, 40);
    }
    //rqt
    public void Search(Dictionary dictionary) {
        String s = textSearchBar.getText();
        if (!s.equals("")) {
            textSearchBar.setForeground(Color.BLACK);

            UpdateList(dictionary.searchFilter(s), 1);
        } else {

            textSearchBar.setForeground(Color.LIGHT_GRAY);

            UpdateList(dictionary.wordTextArray(), 0);
        }
    }

}