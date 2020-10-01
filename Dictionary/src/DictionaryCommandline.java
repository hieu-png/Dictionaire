import java.io.*;
import java.util.ArrayList;
import java.util.Scanner;
import static java.lang.System.out;

enum wordFindType {
    matchFirst, matchAll
}


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

//     public void dicionarySearcher(Dictionary d) {
//         Scanner in = new Scanner(System.in);
//         String wordToFind = in.nextLine();

//         for (Word word : d.wordArrayList) {
//             if(wordToFind.charAt(0) == word.getText().charAt(0) && word.getText().contains(wordToFind)) {
//                 out.println(word.getText());

//             }
//         }



//     }
    public void dictionarySearcher(Dictionary d) {
        Scanner in = new Scanner(System.in);
        String wordToFind = in.nextLine();

        for (Word word : dictionaryFinder(d,wordToFind,wordFindType.matchFirst)) {
            out.println(word.getText());
        }
    }



    //-----------------NT Hieu End 23-9----------------------------

    /**
     *
     * @param d dictionary
     * @param wordToFind word string to find
     * @param typeOfWordFind matchFirst is match only look for words have the same beginning
     *                 matchAll is to find all similar substring.
     * @return list of words found
     */
    public ArrayList<Word> dictionaryFinder(Dictionary d, String wordToFind, wordFindType typeOfWordFind) {
        ArrayList<Word> wordsFound = new ArrayList<>();
        switch (typeOfWordFind) {
            case matchFirst:
                int n = wordToFind.length();

                for (Word word : d.wordArrayList) {
                    if (n > word.getText().length()) {

                        continue;
                    }
                    String temp = word.getText().substring(0, n);
                    if (wordToFind.charAt(0) == word.getText().charAt(0) && temp.equals(wordToFind)) {
                        wordsFound.add(word);
                    }
                }
            break;
            case matchAll:
                for (Word word : d.wordArrayList) {
                    if(word.getText().contains(wordToFind)) {
                    wordsFound.add(word);

                    }
                }

            break;
            default:


        }




        return  wordsFound;
    }





    //-----------------NT Hieu 24-9--------------------------

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
