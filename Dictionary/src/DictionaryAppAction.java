import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.io.File;
import java.io.IOException;

public class DictionaryAppAction {

    //private DictionaryManagement dictionaryManagement;
    protected SqlDataManagement sdm = new SqlDataManagement();
    protected int newlyAddedWordIndex = -1;


    public Image loadImageFromFile(String path, int width, int height) {
        Image image = null;
        try {
            image = ImageIO.read(new File(path))
                    .getScaledInstance(width, height, Image.SCALE_DEFAULT);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return  image;
    }

    public void sqlInit(Dictionary dictionary) {
        sdm.connect();
        //sdm.createTable();
        //sdm.createTableHistory();
        //sdm.insertFromFileDirect(System.getProperty("user.dir") +
        //        "\\Dictionary\\anhviet109K.txt");
        sdm.insertToDictionary(dictionary);
        dictionary.sort();
    }

    public void removeWord(Dictionary dictionary, int index) {
        String wordToRemove = dictionary.wordByIndex(index).getText();
        JOptionPane.showMessageDialog(null,
                String.format("\"%s\" has been removed from the dictionary!",wordToRemove));

        sdm.delete(wordToRemove);
        dictionary.removeWord(index);

    }

    public boolean editWord(Dictionary dictionary, int index) {
        //JTextField text = new JTextField(5);
        JTextField definition = new JTextField(5);
        JTextField pronunciation = new JTextField(5);

        //text.setText(dictionary.wordByIndex(index).getText());
        //definition.setText(dictionary.wordByIndex(index).getDefinition());
        //pronunciation.setText(dictionary.wordByIndex(index).toString());
        String def = dictionary.wordByIndex(index).getDefinition();
        String splitter[] = def.split("/-");
        String pronoun = splitter[0] + "/";
        def = "-" + splitter[1];
        //Text and Definition text field component must have the same name
        Object[] components = {
                new JLabel("Pronunciation"), pronunciation,
                new JLabel("Definition"), definition
        };
        pronunciation.setText(pronoun);
        definition.setText(def);
        Word word = dictionary.wordByIndex(index);
        //Only cancel when press cancel.

            int addWordResult = JOptionPane.showConfirmDialog(null, components,
                    "Edit the word's properties", JOptionPane.OK_CANCEL_OPTION);


            if (addWordResult == JOptionPane.OK_OPTION) {
                dictionary.wordByIndex(index).setDefinition(pronunciation.getText() + definition.getText());
                sdm.delete(word.getText());
                sdm.insertData(word.getText(),pronoun + def, "words");
                return true;
            }
            else {
                return false;
            }

    }


    public boolean addWordWindow(Dictionary dictionary) {
        boolean inserted = false;
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
                    if((i = dictionary.findWord(text.getText())) != -1) {

                        dictionary.wordByIndex(i).addDefinition(definition.getText());
                        sdm.updateWord(text.getText(), dictionary.wordByIndex(i).getDefinition());

                        wordAddMessage = String.format(
                                "\"%s\" has got a new definition!",text.getText());

                    } else {
                        String def = "/" + pronunciation.getText() + "/" + "- " + definition.getText();
                        sdm.insertData(text.getText(), def, "words");
                        dictionary.addWord(text.getText(), def);
                        wordAddMessage = String.format(
                                "\"%s\" has been success fully added to the dictionary!",text.getText());

                    }
                    JOptionPane.showMessageDialog(null, wordAddMessage);
                    dictionary.sort();
                    newlyAddedWordIndex = dictionary.findWord(text.getText());
                    inserted = true;
                    break;
                }
                JOptionPane.showMessageDialog(null, wordAddMessage);


            } else {
                break;
            }
        }
        return inserted;
    }



}
