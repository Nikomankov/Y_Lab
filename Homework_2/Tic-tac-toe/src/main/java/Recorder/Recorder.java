package Recorder;
import Game.*;

public interface Recorder {

    void stepRecord(int counter, int id, int line, int column);

    void resultRecord(Game.WinStatus winStatus);
}
