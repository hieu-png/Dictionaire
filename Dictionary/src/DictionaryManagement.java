import java.util.Scanner;

public class DictionaryManagement {
    Dictionary d = new Dictionary();

    public void insertFromCommandline() {
        Scanner in = new Scanner(System.in);
        /*
        String bufferText,bufferDef;
        bufferText = in.nextLine();
        bufferDef=in.nextLine();*/
        d.addWord(in.nextLine(), in.nextLine());
    }

}
