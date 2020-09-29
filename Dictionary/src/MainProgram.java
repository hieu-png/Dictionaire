
import static java.lang.System.out;
import javax.swing.*;
import java.io.IOException;

public class MainProgram
{
    /* TODO
    insert from file DONE
    dictionary look up DONE

    dictionary advanced DONE
    add remove with command in dictionary management

    dictionary searcher DONE
    dictionary export to file DONE
    */


    //Testing ground
    public static void main(String[] args) throws IOException {
        /*
        JFrame f = new JFrame();
        f.setSize(400,500);
        f.setLayout(null);
        f.setVisible(true);*/
        Dictionary d = new Dictionary();
        DictionaryCommandline dc = new DictionaryCommandline();
        DictionaryManagement dm = new DictionaryManagement();
       // dm.insertFromFile(d,"D:\\dictionary.txt");
        dm.insertFromFileAdvanced(d,"D:\\anhviet109K.txt");
      //  dc.dictionarySearcher(d);
       // dc.dictionarySearcher(d);
        /*
       Word w1 = new Word("Kill","Giet");
        d.addWord(w1);
       d.addWord("Eat","An");1
       d.addWord("SuperNenechii","Strong");*/

       // dm.insertFromFile(d,"D:\\dictionary.txt");
      //  dc.showAllWords(d);
       // dc.dictionarySearcher(d);
        //dc.dictionaryExportToFile(d,"D:\\dictionaryExport.txt");
        //dc.dictionaryAdvanced(d,"D:\\dictionary.txt");
        /*test function LookUp,removeWord,ExportToFile (23-9)
        d.removeWord("Eat");


        dc.dictionaryExportToFile(d,"D:\\dictionaryExport.txt");
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
