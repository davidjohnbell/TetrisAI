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
            new Matrix(original),
            new Matrix(original),
            new Matrix(original)};
        for(int i = 0; i < 4; i++) {

            this.rotations[i].rotateRight(i);
        }
        rotationIndex = 0;
        x = 0;
        y = 0;
    }

    public void rotate(int n) {
        n = Math.abs(n);
        for(int i = 0; i < n; i++) {
            rotationIndex++;
            if(rotationIndex >= rotations.length) {
                rotationIndex = 0;
            }
        }
    }

    public Matrix getCurrent() {
        return new Matrix(rotations[rotationIndex]);
    }

    @Override
    public boolean equals(Object obj) {
        if (!(obj instanceof Shape))
            return false;
        if (obj == this)
            return true;
        Shape shape = (Shape) obj;
        return shape.getCurrent().equals(shape.getCurrent());
    }
}
