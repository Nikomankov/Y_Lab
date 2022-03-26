package Recorder;
import Game.*;

public interface Recorder {

    void stepRecord(Step step);

    void resultRecord(GameResult gameResult);

    void closeRecord();
}
