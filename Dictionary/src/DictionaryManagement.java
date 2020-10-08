import javax.swing.*;
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

        //StringBuilder s = new StringBuilder();
        BufferedReader in = new BufferedReader(new FileReader("C:\\Users\\pc\\IdeaProjects\\DictionaryApp\\anhviet109K.txt"));
        ArrayList<String> words = new ArrayList<>();
        String f;
        f = in.readLine();
        f = f.substring(1);
        StringBuilder word = new StringBuilder();
        word.append(f).append("\n");

        StringBuilder NextText = new StringBuilder();
        while (f != null) {

            while (true) {
                if (NextText.length() > 0) {
                    word.append(NextText).append("\n");
                    NextText = new StringBuilder();
                    //System.out.println("-----------------------------word");
                }

                f = in.readLine();


                //System.out.print(f+'\n');
                if (f != null)
                    if (!f.isEmpty() && f.charAt(0) == '@') {
                        NextText.append(f);
                        //System.out.println("---------------------"+NextText);

                        break;
                    }

                word.append(f).append("\n");

                if (f == null) {
                    word = new StringBuilder(word.substring(0, word.length() - 5));//substring de xoa \n va null khi f chay den cuoi cung
                    break;
                }

            }
            //System.out.print(word);
            words.add(word.toString());
            word = new StringBuilder();


        }
        return words;
    }

    public Dictionary WordFromBigFile(ArrayList<String> words) {
        Dictionary dictionary = new Dictionary();
        for (String word : words) {
            int h = word.indexOf('/');
            if (h == -1) continue;
            String s1 = word.substring(1, h);
            String s2 = word.substring(h);
            dictionary.addWord(s1, s2);
        }
        return dictionary;
    }

    public static void main(String[] args) throws IOException {
        try {
            DictionaryManagement d = new DictionaryManagement();
            ArrayList<String> words = d.insertFromFileAdvanced();
            d.WordFromBigFile(words);

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }





    public static void searchFilter(String searchWord, String[] words, ArrayList<String> FilteredWords) {
        searchWord = searchWord.toLowerCase();
        for (String temp : words) {
            if (searchWord.length() > temp.length()) continue;

            for (int j = 0; j < searchWord.length(); j++) {
                if (searchWord.charAt(j) != temp.charAt(j)) break;

                if (j == searchWord.length() - 1) FilteredWords.add(temp);
            }
        }
    }

    //-----MC Danh-----------23-9------------------------------------
    //ham tim kiem tra va ve chi so cua String s
    public static int dictionaryLookup(ArrayList<Word> arrList, String s) {

        for (int i = 0; i < arrList.size(); i++) {
            if (arrList.get(i).getText().equals(s)) {
                return i;
            }
        }
        return -1;
    }
//------MC Danh------------End 23-9------------------------------
}