package game;

import utils.Matrix;

public class Shape {
    private Matrix[] rotations;
    private int rotationIndex;
    public int x;
    public int y;

    public Shape(int[][] shapeData) {
        this.rotations = new Matrix[] {
            new Matrix(shapeData),
            new Matrix(shapeData),
            new Matrix(shapeData),
            new Matrix(shapeData)
        };
        for(int i = 0; i < 4; i++) {
            this.rotations[i].rotateRight(i);
        }
        rotationIndex = 0;
        x = 0;
        y = 0;
    }

    public void rotate(int n) {
        n = Math.abs(n);
        rotationIndex += n;
        if(rotationIndex >= rotations.length) {
            rotationIndex %= 4;
        }
    }

    public Matrix getCurrent() {
        return rotations[rotationIndex];
    }
}
