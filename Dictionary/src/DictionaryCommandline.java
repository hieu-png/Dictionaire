
import static java.lang.System.out;
public class DictionaryCommandline {
   // int lengthBetweenColumn=10;
   // int lengthBetween
    int columnWidth1 = 5;
    int columnWidth2 = 25;

    public String setWidth(int n) {

        return " ".repeat(Math.max(0, n));
    }

    /**
     * Automatically change size to make the column align.
     * @param n
     * @return
     */
    public String setWidthOffset(String str, int n) {

        String sb = str +
                " ".repeat(Math.max(0, n - str.length()));
        return sb;
    }

    public void showAllWords(Dictionary d) {


//       // out.println("No" + setWidth(columnWidth1) + "|English" + setWidth(columnWidth2) + "|Vietnamese");
//       // for (int i = 0; i < d.WordCount();i++)
//       // out.println((i + 1) + setWidth(2 + columnWidth1 - Integer.toString(i).length())
//      //  + "|" + d.wordByIndex(i).getText() + setWidth(columnWidth2) + "|" + d.wordByIndex(i).getDefinition())
//            out.println(setWidthOffset("No",columnWidth1)
//             + setWidthOffset("|English",columnWidth2) + "|Vietnamese");
//            for (int i = 0; i < d.wordCount(); i++) {
//                out.println(setWidthOffset(Integer.toString(i+1),columnWidth1)
//                + setWidthOffset("|" + d.wordByIndex(i).getText(),columnWidth2)
//                + "|" + d.wordByIndex(i).getDefinition());
        //--------------------------------------------------------------------------
                out.printf("%-10s%-25s%-25s\n","No","|English","|Vietnamese");
        for (int i = 0; i < d.wordCount(); i++) {
                out.printf("%-10d%-25s%-25s\n",i+1,"|" +d.wordArrayList.get(i).getText(),"|" +d.wordArrayList.get(i).getDefinition());

            }





    }

}
