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
        //sdm.insertFromFileDirect(System.getProperty("user.dir") +
        //        "\\Dictionary\\anhviet109K.txt");
        sdm.insertToDictionary(dictionary);
        dictionary.sort();
    }

    public void removeWord(Dictionary dictionary, int index) {
        sdm.delete(dictionary.wordByIndex(index).getText());
        dictionary.removeWord(index);
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
                    if((i = dictionary.findWord(text.getText())) != -1) {

                        dictionary.wordByIndex(i).addDefinition(definition.getText());
                        sdm.updateWord(text.getText(), dictionary.wordByIndex(i).getDefinition());

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
                    dictionary.sort();
                    newlyAddedWordIndex = dictionary.findWord(text.getText());
                    break;
                }
                JOptionPane.showMessageDialog(null, wordAddMessage);


            } else {
                break;
            }
        }
    }



}
