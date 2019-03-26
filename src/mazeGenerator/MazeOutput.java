package mazeGenerator;

public interface MazeOutput {

    public void createLine(Square location, Direction direction);

    public void display();
}
