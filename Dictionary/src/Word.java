public class Word {

    private String text;

    //Word definition in vietnamese.
    private String definition;
    Word(){
        this.definition="null";
        this.text="null";
    }
    Word(String text, String definition){
        this.text = text;
        this.definition= definition;
    }
    Word(Word word){
        this.text= word.text;
        this.definition=word.definition;
    }

    public String getText() {
        return text;
    }

    public String getDefinition() {
        return definition;
    }

    public void setDefinition(String definition) {
        this.definition = definition;
    }

    public void setText(String text) {
        this.text = text;
    }
}
