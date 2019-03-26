package mazeGenerator;

import java.io.PrintStream;
import Driver.Driver;

public class MazePrintStream implements MazeOutput {

	Driver d = new Driver();

	int xSize;
	int ySize;

	String s = "";

	char[][] maze;
	PrintStream stream;
	char empty;

	public MazePrintStream(int xSize, int ySize, PrintStream stream, char empty, char filled){
		maze = new char[2 * xSize + 1][2 * ySize + 1];
		for (int y = 0; y < 2 * ySize + 1; y++){
			for (int x = 0; x < 2 * xSize + 1; x++){
				maze[x][y] = filled;
			}
		}
		this.xSize = xSize;
		this.ySize = ySize;
		this.stream = stream;
		this.empty = empty;
	}

	public void createLine(Square location, Direction direction){
		int x = 2 * location.x + 1;
		int y = 2 * location.y + 1;
		int dx = direction.x;
		int dy = direction.y;
		for (int d = 0; d < 3; d++){
			maze[x + d * dx][y + d * dy] = empty;
		}
	}

	public void display(){
		//String newline = System.getProperty("line.separator");
		for (int y = 0; y < 2 * ySize + 1; y++){
			for (int x = 0; x < 2 * xSize + 1; x++){
				//stream.print(maze[x][y]);
				s += maze[x][y];
			}
			s += "\n";
			//stream.print(newline);}
			d.setRawMaze(s);;
		}    
	}
}
