import Game.Game;
import junit.framework.TestCase;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertThrows;

public class GameTest extends TestCase {
    String[][] gameField;
    Game game;

    @BeforeEach
    @Override
    public void setUp() throws Exception{
        game = new Game();

    }

    //CheckEnterMove test
    @Test
    @DisplayName("Out-of-field check")
    void testOutOfField(){
        assertThrows(Throwable.class, () -> .addCustomer(input)
    }
    public void testStep(){

    }
    public void testlineСheck(){

    }
    public void testRatingEntry(){

    }
    public void testReadFromJSON(){

    }
    public void testReadFromXML(){

    }
}
