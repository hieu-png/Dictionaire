import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.File;
import java.io.IOException;

public class DictionaryApplication {

    private JFrame mainFrame;
    private JPanel controlPanel;

    public DictionaryApplication() {
        prepareGUI();
    }


    public void prepareGUI() {
        mainFrame = new JFrame("Dictionary 2.0");
        mainFrame.setSize(800, 600);

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

    public void showExample() throws IOException {
        final JTextField searchBar = new HintTextField("Search...");

        //JLabel label = new JLabel("ssssss");
        //mainFrame.add(label);
        //label.setBounds(20,40,100,30);

        Font f = new Font("Arial", Font.BOLD, 14);
        searchBar.setFont(f);
        mainFrame.add(searchBar);
        searchBar.setBounds(15,10,700,40);

        Image image = ImageIO.read(new File("C:\\Users\\pc\\OneDrive\\" +
                "Máy tính\\beautiful blue color background\\book.png"))
                .getScaledInstance(18,18,Image.SCALE_DEFAULT);
        JButton showAllWord = new JButton();
        showAllWord.setIcon(new ImageIcon(image));


        mainFrame.add(showAllWord);
        showAllWord.setBounds(0,50,28,28);









        mainFrame.setVisible(true);
    }

    public static void main(String[] args) throws IOException {
        DictionaryApplication dictionary = new DictionaryApplication();
        dictionary.showExample();
    }
}