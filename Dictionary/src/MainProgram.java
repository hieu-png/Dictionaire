
import static java.lang.System.out;
import javax.swing.*;

public class MainProgram
{





    public static void main(String[] args){
        /*
        JFrame f = new JFrame();
        f.setSize(400,500);
        f.setLayout(null);
        f.setVisible(true);*/
        Dictionary d = new Dictionary();
        Word w1 = new Word("Kill","Giet");
        d.addWord(w1);
        d.addWord("Eat","An");
        d.addWord("SuperNenechii","Strong");
        DictionaryCommandline dc = new DictionaryCommandline();
        dc.showAllWords(d);
    }
}
