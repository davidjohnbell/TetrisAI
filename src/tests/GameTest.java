package tests;

import ai.TetrisGenome;
import game.Game;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

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
    void step() {
        game.step();
        game.step();
        Assertions.assertTrue(false);
    }

}