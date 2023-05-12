import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class WordMedium implements Word {
    private static String filename = "src/medium.txt";
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
