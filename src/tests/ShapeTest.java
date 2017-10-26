package tests;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.Assertions;
import game.Shape;
import utils.Matrix;

class ShapeTest {
    private Shape shape = new Shape(
        new int[][] {
            {1,1,0},
            {0,1,1},
            {0,0,0}});
    @Test
    void rotate() {
        Matrix left = new Matrix( new int[][] {
            {0,1,0},
            {1,1,0},
            {1,0,0}});
        shape.rotate(3);
        Matrix current = shape.getCurrent();
        Assertions.assertTrue(current.equals(left));
    }
}