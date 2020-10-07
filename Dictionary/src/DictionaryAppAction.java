import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.io.File;
import java.io.IOException;

public class DictionaryAppAction {


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

    public void removeWord(Dictionary dictionary, int index) {
        dictionary.removeWord(index);
    }

    public void addWordWindow(Dictionary dictionary) {

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
            String wordAddMessage;
            if (text.getText().isEmpty()) {
                wordAddMessage=  "Please enter the word";
            } else if (definition.getText().isEmpty()) {
                wordAddMessage = "Please enter the word's definition";
            } else {
                dictionary.addWord(text.getText(), definition.getText());
                wordAddMessage = String.format("\"%s\" has been success fully added to the dictionary!",text.getText());
            }
            JOptionPane.showMessageDialog(null, wordAddMessage);


        }
    }

}
