import java.io.File;  // Import the File class
import java.io.FileNotFoundException;  // Import this class to handle errors
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner; // Import the Scanner class to read text files

public class WordEasy implements Word {
    private static String filename = "src/easy.txt";
    public static List<List<java.lang.Character>> allWord = new ArrayList<>() {{
        try {
            File myObj = new File(filename);
            Scanner myReader = new Scanner(myObj);
            while (myReader.hasNextLine()) {
                String data = myReader.nextLine();
                List<java.lang.Character> dataChar = new ArrayList<>();
                for (int n = 0; n < data.length(); n++) {
                    char ch = data.charAt(n);
                    dataChar.add(ch);
                }
                add(dataChar);
            }
            myReader.close();
        } catch (FileNotFoundException e) {
            System.out.println("An error occurred.");
            e.printStackTrace();
        }
    }};

    @Override
    public List<List<java.lang.Character>> getWord() {
        return allWord;
    }

}
