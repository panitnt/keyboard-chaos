import java.util.ArrayList;
import java.util.List;

public class WordController {
    private int index = 0;
    String container;
    List<Command> collector = new ArrayList<>();

    public WordController(String str){
        this.container = str;
        for (int i = 0; i < str.length(); i++){
            Command char_i = new Command(str.charAt(i));
            collector.add(char_i);
        }
    }

}
