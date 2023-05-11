import java.util.ArrayList;
import java.util.List;

public class Replay {
    private List<Command> commands = new ArrayList<Command>();
    private boolean isReplay = false;
    public void addCommand(Command command){
        commands.add(command);
    }

    public List<Command> getCommands() {
        return commands;
    }
    public void reset(){
        commands.clear();
    }
}
