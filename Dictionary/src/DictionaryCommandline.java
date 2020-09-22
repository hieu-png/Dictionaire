import java.io.*;

import static java.lang.System.out;

public class DictionaryCommandline {
    public void showAllWords(Dictionary d) {
        //--------------------------------------------------------------------------
        System.out.printf("%-10s%-25s%-25s\n", "No", "|English", "|Vietnamese");
        for (int i = 0; i < d.wordCount(); i++) {
            out.printf("%-10d%-25s%-25s\n", i + 1, "|" + d.wordArrayList.get(i).getText()
                                                 , "|" + d.wordArrayList.get(i).getDefinition());
        }
    }
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
