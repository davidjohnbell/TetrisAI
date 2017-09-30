package game;

public class Shape {
    private int[][] shape;
    public int x;
    public int y;

    public Shape(int[][] shapeArray) {
        shape = shapeArray;
        x = 0;
        y = 0;
    }

    public void rotate() {
        int[][] rotation = new int[shape[0].length][shape.length];

        //transpose
        for(int row = 0; row < shape.length; row++) {
            for(int col = 0; col < shape[0].length; col++) {
                rotation[col][row] = shape[row][col];
            }
        }

        //reverse each row
        for (int[] array : rotation) {
            int start = 0, end = array.length - 1;
             while (start <= end) {
                int temp = array[start];
                array[start] = array[end];
                array[end] = temp;
                start++;
                end--;
            }
        }
        shape = rotation;
    }
}
