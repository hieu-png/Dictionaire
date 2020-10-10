public class Word {

    private String text;

    //Word definition in vietnamese.
    private String definition;

    public Word() {
        this.definition = "null";
        this.text = "null";
    }
    public Word (String text, String definition) {        //text.charAt(0)

        this.text = text;
        this.definition = definition;
    }


    public Word (Word word) {
        this.text = word.text;
        this.definition = word.definition;
        //this.example = word.example;
        //this.type = word.type;
    }

    public String getText() {
        return text;
    }

    public String getDefinition() {
        return definition;
    }


    //List all the definition on the same line.
    public String getDefinitionSameLine() {
        String tempDef = definition;

        return tempDef.replaceAll("- ",", ");
    }

    //List all definition per line.
    public String getDefinitionLine() {
        String tempDef = definition;
        return tempDef.replaceAll("- ","\n- ");
    }

    public void addDefinition(String definition) {
        if (this.definition == null) {
            this.definition = definition;
        } else {

            this.definition += "-" + definition;
        }
    }

    public void setDefinition(String definition) {
        this.definition = definition;
    }

    public void setText(String text) {
        this.text = text;
    }


    //Hieu 28/09--------------------------------------

    /*

    private String pronunciation;

    private String example;

    private String type;

    public String getExample() {
        return example;
    }

    public String getPronunciation() {
        return pronunciation;
    }

    public String getType() {
        return  type;
    }

    public void addDefinition(String definition) {
        if (this.definition == null) {
            this.definition = definition;
        } else {

            this.definition += ", " + definition;
        }
    }


    public  void addExample(String example) {
        if(this.example == null) {
            setExample(example);
        } else {
            this.example += ", " + example;
        }

    }

    public void setExample(String example) {
        this.example = example;
    }

    public void setPronunciation(String pronunciation) {
        this.pronunciation = pronunciation;
    }

    public void setType(String type) {
        this.type = type;
    }
    */

    //End Hieu 28/09-------------------------
}
