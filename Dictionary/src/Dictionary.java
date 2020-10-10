import java.util.ArrayList;

public class Dictionary {
    ArrayList<Word> wordArrayList = new ArrayList<Word>();
    public void addWord(String wordText, String wordDef){

        wordArrayList.add(new Word(wordText, wordDef));
        System.out.println("Word added: " + wordText);

    }

    public String[] wordTextArray() {
        String [] textWordArray = new String[wordCount()];
        for (int i = 0; i < wordCount(); i++) {
            textWordArray[i] = wordByIndex(i).getText();
        }
        return  textWordArray;
    }

    public ArrayList<Word> getWordArrayList() {
        return wordArrayList;
    }

    public void addWord(Word word){
        wordArrayList.add(new Word(word));
        //System.out.println("Word added: " + word.getText());

    }

    public void removeWord(int index) {
        System.out.println("Word removed: " + wordArrayList.get(index).getText());

        wordArrayList.remove(index);
    }

    public Word wordByIndex(int i) {
        return wordArrayList.get(i);
    }

    public int wordCount(){
        return wordArrayList.size();
    }

    public String[] searchFilter(String wordToSearch) {
        wordToSearch = wordToSearch.toLowerCase();
        ArrayList<String> filteredWord = new ArrayList<>();
        for(Word word : wordArrayList) {

            if(wordToSearch.length() > word.getText().length()) continue;
            Boolean same = true;
            for(int i = 0; i < wordToSearch.length(); i++) {
                if(wordToSearch.charAt(i) != word.getText().charAt(i))
                    same = false;
            }
            if(same) {
                filteredWord.add(word.getText());
            }
        }
        return filteredWord.toArray(new String[0]);
    }

    //------------MC Danh------------23-9-----------------------------------
    public void removeWord(String wordText) {
        DictionaryManagement d = new DictionaryManagement();
        int i = d.dictionaryLookup(wordArrayList, wordText);
        if (i != -1) {
            wordArrayList.remove(wordArrayList.get(i));
        }
        else {
            System.out.println("Word Not Found!");
        }
    }
//------------MC Danh--------End 23-9-----------------------------------

}
