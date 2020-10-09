import java.util.ArrayList;

public class Dictionary {
    ArrayList<Word> wordArrayList = new ArrayList<Word>();
    public void addWord(String wordText, String wordDef){

        wordArrayList.add(new Word(wordText, wordDef));
        System.out.println("Word added: " + wordText);

    }

    public void addWord(String wordText, String wordDef, String wordPronunciation) {

        wordArrayList.add(new Word(wordText, wordDef, wordPronunciation));
        System.out.println("Word added: " + wordText);

    }

    public ArrayList<Word> getWordArrayList() {
        return wordArrayList;
    }

    public void addWord(Word word){
        wordArrayList.add(new Word(word));
        System.out.println("Word added: " + word.getText());

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
