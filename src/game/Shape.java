package game;

import utils.Matrix;

public class Shape {
    private Matrix[] rotations;
    private int rotationIndex;
    public int x;
    public int y;

    /**
     * Uses the data to initialize a shape and its rotations.
     * The shape should be bound by the smallest rectangle
     * and contain non zero values which represent occupied
     * squares.
     * @param shapeData
     */
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

    /**
     * Apply absolute value n number of rotations to the shape.
     */
    public void rotate(int n) {
        n = Math.abs(n);
        for(int i = 0; i < n; i++) {
            rotationIndex++;
            if(rotationIndex >= rotations.length) {
                rotationIndex = 0;
            }
        }
    }

    /**
     * Returns the shape Matrix having done zero or
     * more rotations to the shape.
     */
    public Matrix getCurrent() {
        return new Matrix(rotations[rotationIndex]);
    }

    /**
     * An object is equal only if it is also an instance of
     * shape and the current rotation has the same elements
     * as this shapes current rotation.
     */
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
