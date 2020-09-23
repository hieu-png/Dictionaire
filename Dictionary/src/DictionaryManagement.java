import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.Scanner;
import java.util.ArrayList;
import java.util.regex.Pattern;

public class DictionaryManagement {
    //Dictionary d = new Dictionary();

//-----NT Hieu-----------22-9------------------------------------
    public void insertFromCommandline(Dictionary d) {
        Scanner in = new Scanner(System.in);
        /*
        String bufferText,bufferDef;
        bufferText = in.nextLine();
        bufferDef=in.nextLine();*/
        d.addWord(in.nextLine(), in.nextLine());
    }
//-----NT Hieu-----------23-9--------------------------------
    public void insertFromFile(Dictionary d, String path) {

    BufferedReader in = null;
    try {
        String bufferString;

        in = new BufferedReader(new FileReader(path));

        while ((bufferString = in.readLine()) != null) {

            String[] splitString;
            splitString = bufferString.split("\t");

            d.addWord(splitString[0],splitString[1]);
        }
    } catch (IOException e) {
        e.printStackTrace();
    } finally {
        try {
            if (in!=null) {
                in.close();
            }
        } catch (IOException e){
            e.printStackTrace();
        }
    }


    }
    //-----NT Hieu-----------End 23-9--------------------------------


//-----MC Danh-----------23-9------------------------------------
    //ham tim kiem tra va ve chi so cua String s
    public int dictionaryLookup(ArrayList<Word> arrList, String s) {

        for (int i = 0; i < arrList.size(); i++) {
            if (arrList.get(i).getText().equals(s)) {
                return i;
            }
        }
        return -1;
    }
//------MC Danh------------End 23-9------------------------------
}
