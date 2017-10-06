package game;

import utils.Matrix;

public class Shape {
    private Matrix[] rotations;
    private int rotationIndex;
    public int x;
    public int y;

    public Shape(int[][] shapeData) {
        Matrix original = new Matrix(shapeData);
        this.rotations = new Matrix[] {
            original,
            Matrix.copy(original),
            Matrix.copy(original),
            Matrix.copy(original)};
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

    @Override
    public boolean equals(Object obj) {
        if (!(obj instanceof Shape))
            return false;
        if (obj == this)
            return true;
        Shape shape = (Shape) obj;
        if(shape.getCurrent().equals(shape.getCurrent()))
            return true;
        else
            return false;
    }
}
