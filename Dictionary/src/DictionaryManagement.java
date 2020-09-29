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
    //-----NT Hieu-----------28-9--------------------------------

    /**
     * Database of that file is too large and causing out of memory error. Scrapped.
     * @param d
     * @param path
     */
    public void insertFromFileAdvanced(Dictionary d, String path) {
    /*
    BufferedReader in = null;
    try {
        String bufferString;
        String previousString = " ";
        in = new BufferedReader(new FileReader(path));
        Word bufferWord = new Word();
        Word emptyWord = new Word();
        while ((bufferString = in.readLine()) != null) {
            if(bufferString.length() > 0 && previousString.length() > 0) {
            if( (previousString.charAt(0) == '-' || previousString.charAt(0) == '=' )
            &&bufferString.charAt(0) == '@') {
                System.out.println("New Word added");
                d.addWord(bufferWord);
                bufferWord = emptyWord;
            }
                System.out.println(bufferString);

                switch (bufferString.charAt(0)) {
                case '@' :
                    bufferWord.setText(bufferString);
                break;

                case '*' :
                    bufferWord.setType(bufferString);
                break;

                case '-' :
                    bufferWord.addDefinition(bufferString);
                break;
                case '=' :
                    bufferWord.addExample(bufferString);
                break;

                default :
                break;


            }
            previousString = bufferString;

        }
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



*/
}

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
