import java.util.ArrayList;

public class Dictionary {
    ArrayList<Word> wordArrayList = new ArrayList<Word>();
    public void addWord(String wordText, String wordDef){
        wordArrayList.add(new Word(wordText,wordDef));
    }

    public void addWord(Word word){
        wordArrayList.add(new Word(word));


    }

    public Word wordByIndex(int i) {
        return wordArrayList.get(i);
    }

    public int wordCount(){
        return wordArrayList.size();
    }
    public void removeWord(String wordText){


    }

}
