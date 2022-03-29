import Exceptions.IllegalValuesException;
import Game.Game;
import Game.Step;
import junit.framework.TestCase;
import org.junit.Before;
import org.junit.Test;

public class GameTest extends TestCase {
    String[][] gameField;
    Game game;

    @Before
    public void setUp() throws Exception{
        game = new Game();
    }

    //CheckEnterMove test
    @Test(expected = IllegalValuesException.class)
    public void testStepOutOfLineMinus(){
        Step step = new Step(1,1,-1,1);
        game.checkEnterMove(step);
    }
    @Test(expected = IllegalValuesException.class)
    public void testStepOutOfLinePlus(){
        Step step = new Step(1,1,3,1);
        game.checkEnterMove(step);
    }
    @Test(expected = IllegalValuesException.class)
    public void testStepOutOfColumnMinus(){
        Step step = new Step(1,1,1,-1);
        game.checkEnterMove(step);
    }
    @Test(expected = IllegalValuesException.class)
    public void testStepOutOfColumnPlus(){
        Step step = new Step(1,1,1,3);
        game.checkEnterMove(step);
    }
}
