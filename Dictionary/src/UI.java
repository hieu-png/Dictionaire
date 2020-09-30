import javax.swing.*;

public class UI

{

    JFrame mainWindow;

    JPanel addWordPanel;

    int height = 600;
    int width = 700;

    


    //Dimen
    //JFrame insertWordWindow;



    public  UI() {


    mainWindow = new JFrame();
    mainWindow.setSize(width,height);
    mainWindow.setVisible(true);
    addWordPanel = new JPanel();
   // addWordPanel.setLayout(new GroupLayout(4));
    }



    public void close() {
    mainWindow.setVisible(false);

    }




}
