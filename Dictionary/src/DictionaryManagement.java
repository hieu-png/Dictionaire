import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;

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

                d.addWord(splitString[0], splitString[1]);
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                if (in != null) {
                    in.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }


    }

    public ArrayList<String> insertFromFileAdvanced() throws IOException {

        StringBuilder s = new StringBuilder();
        BufferedReader in = new BufferedReader(new FileReader(System.getProperty("user.dir")+"//Dictionary//anhviet109K.txt"));
        ArrayList<String> words = new ArrayList<>();
        String f;
        f = in.readLine();
        f = f.substring(1, f.length());
        String word = "";
        word = word + f + "\n";

        String NextText = "";
        while (f != null) {

            while (true) {
                if (!NextText.isEmpty()) {
                    word = word + NextText + "\n";
                    NextText = "";
                    //System.out.println("-----------------------------word");
                }

                f = in.readLine();


                //System.out.print(f+'\n');
                if (f != null)
                    if (!f.isEmpty() && f.charAt(0) == '@') {
                        NextText += f;
                        //System.out.println("---------------------"+NextText);

                        break;
                    }

                word = word + f + "\n";

                if (f == null) {
                    word = word.substring(0, word.length() - 5);//substring de xoa \n va null khi f chay den cuoi cung
                    break;
                }

            }
            //System.out.print(word);
            words.add(word);
            word = "";


        }
        return words;
    }

    public Dictionary WordFromBigFile(ArrayList<String> words) {
        ArrayList<String> temp = new ArrayList<>();
        Dictionary dictionary = new Dictionary();
        int N = words.size();
        for (int i = 0; i < N; i++) {
            int h = words.get(i).indexOf('/');
            if (h == -1) continue;
            String s1 = words.get(i).substring(1, h);
            String s2 = words.get(i).substring(h);
            dictionary.addWord(s1, s2);
        }
//        int n = temp.size();
//        //return array of textWord
//        String[] textWord = new String[n];
//        for(int i=0;i<n;i++){
//            textWord[i] = temp.get(i);
//            System.out.println(textWord[i]);
//        }
//        return textWord;
        return dictionary;
    }

    public static void main(String[] args) throws IOException {
        try {
            DictionaryManagement d = new DictionaryManagement();
            ArrayList<String> words = d.insertFromFileAdvanced();
            Dictionary dictionary = new Dictionary();
            d.WordFromBigFile(words);

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
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