import java.io.*;
import java.util.Scanner;
import static java.lang.System.out;

public class DictionaryCommandline {

    public void showAllWords(Dictionary d) {
        //--------------------------------------------------------------------------
        out.printf("%-10s%-25s%-25s\n", "No", "|English", "|Vietnamese");
        for (int i = 0; i < d.wordCount(); i++) {
            out.printf("%-10d%-25s%-25s\n", i + 1, "|" + d.wordArrayList.get(i).getText()
                                                 , "|" + d.wordArrayList.get(i).getDefinition());
        }
    }
    //-----NT Hieu-----------23-9----------------------------------

    public void dictionaryBasic(Dictionary d) {
        DictionaryManagement dm = new DictionaryManagement();
        dm.insertFromCommandline(d);
        showAllWords(d);
    }

    //-----NT Hieu-----------23-9----------------------------------

    public void dictionaryAdvanced(Dictionary d,String path) {
        DictionaryManagement dm = new DictionaryManagement();
        dm.insertFromFile(d,path);
        dm.insertFromCommandline(d);
        showAllWords(d);

    }

    //-----NT Hieu-----------23-9----------------------------------

    public void dicionarySearcher(Dictionary d) {
        Scanner in = new Scanner(System.in);
        String wordToFind = in.nextLine();

        for (Word word : d.wordArrayList) {
            if(wordToFind.charAt(0) == word.getText().charAt(0) && word.getText().contains(wordToFind)) {
                out.println(word.getText());

            }
        }



    }

    //----NT Hieu-------------End 23-9----------------------------




    //-------------------------------MC Danh 23-9---------------------------------------------------------

    public void dictionaryExportToFile(Dictionary d,String path) throws IOException {
        FileWriter writer = new FileWriter(path);
        for (Word word : d.wordArrayList){
            writer.write(word.getText()+"\t"+ word.getDefinition()+"\n");
        }
        writer.close();
    }

//--------------------------------MC Danh 23-9---------------------------------------
}
