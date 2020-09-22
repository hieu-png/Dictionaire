
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
        /* test function LookUp,removeWord,ExportToFile (23-9)
        d.removeWord("Eat");


        DictionaryCommandline dc = new DictionaryCommandline();


        dc.dictionaryExportToFile(d,"C:\\Users\\pc\\OneDrive\\Máy tính\\BTL\\dictionary.txt");
        dc.showAllWords(d);
        DictionaryManagement w = new DictionaryManagement();
        int i= w.dictionaryLookup(d.wordArrayList,"Kill");
        if(i != -1) {
            System.out.println("\nTest dictionaryLookUp\n");
            System.out.printf("%-10s%-25s%-25s\n", "No", "|English", "|Vietnamese");
            System.out.printf("%-10d%-25s%-25s\n", i + 1, "|" + d.wordArrayList.get(i).getText()
                    , "|" + d.wordArrayList.get(i).getDefinition());
        }
        else{
            System.out.print("Word Not Found!");
        }

         */
        
    }
}
