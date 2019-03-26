package Driver;


import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Random;
import javax.swing.JFrame;
import Display.Display;
import mazeGenerator.Maze;
import mazeGenerator.MazePrintStream;
import mazeGenerator.Square;

public class Driver {


	static String rawMaze;

	Random rn = new Random();

	public static void main(String[] args) {

		JFrame frame = new JFrame("MazeBalls");
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		Display dis = new Display();
		frame.setResizable(false);
		frame.getContentPane().add(dis);
		frame.pack();
		frame.setLocationRelativeTo(null);
		frame.setVisible(true);
		//Driver d = new Driver();
	}


	public void newMaze(){

		String file ="res/maze.txt";

		Random random = new Random();
		int xSize = 37;
		int ySize = 19;
		Square startingSquare = new Square(0,0);
		MazePrintStream stream = new MazePrintStream(xSize, ySize, System.out, '0', '1');
		Maze maze = new Maze(xSize, ySize, startingSquare, stream);
		boolean keepGenerating = true;
		while (keepGenerating){
			keepGenerating = maze.createRandomPath(random);
		}
		maze.populate();

		//System.out.println(rawMaze);

		rawMaze.trim();

		//System.out.println(rawMaze.length());

		StringBuilder s = new StringBuilder(rawMaze);
		s.replace(rawMaze.length()-3, rawMaze.length(), "0");
		int i = -1;
		do{
			i++;
			if(rawMaze.substring(i, i+1).equals(String.valueOf(0))){
				
				int token = rn.nextInt(500);
				if(token == 2){
					s.replace(i, i+1, "2");	
				}
				if(token == 3){
					s.replace(i, i+1, "3");	
				}
				if(token == 4){
					s.replace(i, i+1, "4");	
				}
				if(token == 5){
					s.replace(i, i+1, "5");	
				}
				

			}
		}while(i<rawMaze.length()-1);
		//System.out.println(s+"\n");


		try (FileOutputStream fos = new FileOutputStream(file)) {

			String text = s.toString();

			byte[] mybytes = text.getBytes();

			fos.write(mybytes);
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public void setRawMaze(String mazeBalls) {
		Driver.rawMaze = mazeBalls;
	}


	public static String getRawMaze() {
		return rawMaze;
	}
}