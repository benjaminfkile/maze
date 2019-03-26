package mazeGenerator;

import java.util.ArrayList;
import java.util.Random;

public class Maze {

    int xSize;
    int ySize;

    int markedSquares;

    boolean[][] marked;

    byte[][] maze;

    ArrayList<Square> openSquares = new ArrayList<Square>();

    MazeOutput output;

    public Maze(int xSize, int ySize, Square start, MazeOutput output){
        maze = new byte[xSize][ySize];
        marked = new boolean[xSize][ySize];
        this.xSize = xSize;
        this.ySize = ySize;
        openSquares.add(start);
        this.output = output;
    }

    private Square getSquareInDirection(Square square, Direction direction){
        return new Square(square.x + direction.x, square.y + direction.y);
    }

    private byte setValueAtSquare(Square square, byte value){
        return maze[square.x][square.y] = value;
    }

    private byte getValueAtSquare(Square square){
        return maze[square.x][square.y];
    }

    private byte getValueAtSquare(Square square, Direction direction){
        return getValueAtSquare(getSquareInDirection(square, direction));
    }

    private void markSquare(Square square){
        if (!marked[square.x][square.y]) markedSquares++;
        marked[square.x][square.y] = true;
    }

    //Return whether the input square is surrounded by squares it cannot link to
    private boolean isFinished(Square square){
        boolean isFinished = true;
        if (square.x > 0) isFinished = getValueAtSquare(square, Direction.LEFT) != 0;
        if (square.y > 0) isFinished = getValueAtSquare(square, Direction.DOWN) != 0 && isFinished;
        if (square.x < xSize - 1) isFinished = getValueAtSquare(square, Direction.RIGHT) != 0 && isFinished;
        if (square.y < ySize - 1) isFinished = getValueAtSquare(square, Direction.UP) != 0 && isFinished;
        return isFinished;
    }

    private Square getRandomOpenSquare(Random random){
        if (openSquares.size() == 0){
            return null;
        } else {
            return openSquares.get(random.nextInt(openSquares.size()));
        }
    }

    private void makeRandomPath(Square square, Random random){
        if (!openSquares.contains(square)) return;
        Direction[] directions = {Direction.UP, Direction.RIGHT, Direction.LEFT, Direction.DOWN};
        //Shuffle Directions array
        for (int i = directions.length - 1; i > 0; i--){
            int index = random.nextInt(i + 1);
            Direction dir = directions[index];
            directions[index] = directions[i];
            directions[i] = dir;
        }
        for (Direction direction: directions){
            Square targetSquare = getSquareInDirection(square, direction);
            //If going in this direction would not go off the grid or on to an occupied square
            if (targetSquare.x >= 0 && targetSquare.x < xSize && targetSquare.y >= 0
                    && targetSquare.y < ySize && getValueAtSquare(targetSquare) == 0){
                output.createLine(square, direction);
                setValueAtSquare(square, (byte) (getValueAtSquare(square) | direction.value));
                setValueAtSquare(targetSquare, (byte) (getValueAtSquare(targetSquare) | direction.opposite));
                if (isFinished(square)){
                    openSquares.remove(square);
                }
                if (!isFinished(targetSquare)){
                    openSquares.add(targetSquare);
                }
                markSquare(square);
                markSquare(targetSquare);
                return;
            }
        }
    }

    public boolean createRandomPath(Random random){
        Square square = getRandomOpenSquare(random);
        makeRandomPath(square, random);
        return markedSquares < xSize * ySize;
    }

    public void populate(){
        output.display();
    }
}
