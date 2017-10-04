package game;

import utils.Matrix;

public class Board {
    private Matrix board;
    private int score;

    public Board(int rows, int columns) {
        this.board = new Matrix(columns, rows);
    }

    public Matrix applyShape(Shape shape) {
        Matrix shapeData = shape.getCurrent();
        shapeData.resize(board.getWidth(), board.getHeight());
        for(int i = 0; i < shape.y; i++) {
            //shift rows
        }
    }

}
