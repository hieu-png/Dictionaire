
import java.sql.*;
import java.io.*;
/**
 * @author Nguyen Trung Hieu 8/10.
 */

public class SqlDataManagement {

    private Connection connection = null;
    private Statement statement = null;
    //word_text and word_def and word_pron

    String fileName = "dictionary.db";
    String url = "jdbc:sqlite:" +
            System.getProperty("user.dir").replace("\\","/") +
            "/Dictionary/" + fileName;

    public void connect() {

        System.out.println(url);
        try {
            Class.forName("org.sqlite.JDBC");
            connection = DriverManager.getConnection(url);
            System.out.println("ConnectionSuccess");

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void createTable() {
        try {
            Statement createStatements = connection.createStatement();
            String sqlCreate = "CREATE TABLE" +
                    " IF NOT EXISTS" +
                    " words " + //A table named words
                    "(word_text TEXT," + //This column store the literal text
                    " word_pron TEXT," +  //This column store the pronunciation
                    " word_def TEXT);";  //This column store the definition
            createStatements.executeUpdate(sqlCreate);

        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    public void insertData(String word, String def) {
        //String sqlCommand = "INSERT INTO words(WORD_TEXT STRING, WORD_DEF STRING)  VALUES('" + word + "', '" + def + "')";
        word = word.replaceAll("'", "\\\''");
         executeCommand( String.format("INSERT INTO words VALUES('%s','%s')",
                word,def));

    }

    public void insertData(String word, String def, String pronunciation) {
        //String sqlCommand = "INSERT INTO words(WORD_TEXT STRING, WORD_DEF STRING)  VALUES('" + word + "', '" + def + "')";
        word = word.replaceAll("'", "\\\''");
        pronunciation = pronunciation.replaceAll("'", "`");
        executeCommand( String.format("INSERT INTO words VALUES('%s','%s','%s')",
                word, def, pronunciation));

    }

    public void executeCommand(String command) {
        try  (PreparedStatement preparedStatement = connection.prepareStatement(command)) {
            preparedStatement.executeUpdate();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void insertFromDictionary(Dictionary dictionary) {
        for(Word word : dictionary.getWordArrayList()) {
            insertData(word.getText(), word.getDefinition(), word.getPronunciation());
            System.out.println(word.getText() + " has been added to database");

        }
    }
    public void insertToDictionary(Dictionary dictionary) {

    }

    public void insertFromFileAdvanced(Dictionary d, String path)   {
        BufferedReader in = null;
        try {
            in = new BufferedReader(new FileReader(path));
            String bufferString;
            String wordText = "";
            String wordDef  = "";
            String wordPronunciation = "";
            while((bufferString = in.readLine()) != null) {

                if (!bufferString.isEmpty()) {

                    if (bufferString.charAt(0) == '@') {
                        if (wordDef != "") {
                            d.addWord(wordText, wordDef, wordPronunciation);
                            // System.out.println("New word added: " + wordText + "::" + wordDef);
                            wordDef = "";
                            wordPronunciation = "";
                        }

                        int endPoint = bufferString.indexOf(" /");
                        if (endPoint == -1) {
                            endPoint = bufferString.length();
                        } else {
                            wordPronunciation = bufferString.substring(endPoint, bufferString.length());
                        }
                        wordText = bufferString.substring(1,endPoint);


                    } else if (bufferString.charAt(0) == '-' && wordText != "") {

                        wordDef += bufferString;
                    }

                }
            }
            //Add the last word
            d.addWord(wordText, wordDef, wordPronunciation);

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



    public void printDictionary() {
        try(Statement stmt = connection.createStatement();
        ResultSet resultSet = stmt.executeQuery("SELECT word_text, word_def from words") ){
            while(resultSet.next()) {
                System.out.println(resultSet.getString("word_text")+ "\t" +
                                    resultSet.getString("word_pron") + "\t" +
                                    resultSet.getString("word_def"));
            }


        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        DictionaryManagement dm = new DictionaryManagement();
      //  DictionaryAppAction daa = new DictionaryApplication();
        SqlDataManagement sdm = new SqlDataManagement();
        Dictionary dictionary = new Dictionary();
        //dictionary = dm.WordFromBigFile(dm.insertFromFileAdvanced());

        //dm.insertFromFile(dictionary,System.getProperty("user.dir") + "\\Dictionary\\SmallScaleTest.txt");
        sdm.insertFromFileAdvanced(dictionary,
                System.getProperty("user.dir") + "\\Dictionary\\anhviet109K_lite.txt");
        DictionaryCommandline dc = new DictionaryCommandline();
        dc.showAllWords(dictionary);
        sdm.connect();
        sdm.createTable();
        sdm.insertFromDictionary(dictionary);

        sdm.printDictionary();
        //sdm.insertData("Apple","tao");


    }

}
