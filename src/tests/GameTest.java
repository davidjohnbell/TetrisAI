package tests;

import ai.TetrisGenome;
import game.Board;
import game.Game;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.lang.reflect.Field;

class GameTest {
    Game game;
    TetrisGenome genome;

    @BeforeEach
    void setUp() {
        genome = new TetrisGenome(0.2f, 0.2f, 0);
        game = new Game(genome, 5, 10, 0);
    }

    @AfterEach
    void tearDown() {
        genome = null;
        game = null;
    }

    @Test
    void boardIsValid() {
        for (int i = 0; i < 100; i++) {
            game.step();
        }
        try {
            Field field = game.getClass().getDeclaredField("board");
            field.setAccessible(true);
            Board board = (Board)field.get(game);
            for (int i = 0; i < board.getHeight(); i++) {
                for (int j = 0; j < board.getWidth(); j++) {
                    if(board.getElement(i, j) > 10) {
                        Assertions.fail("Board is not valid");
                    }
                }
            }
        }
        catch (Exception e) {}
    }

}