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


}
