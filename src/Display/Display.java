package Display;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Random;
import java.util.Scanner;
import java.util.concurrent.ScheduledExecutorService;
import javax.swing.ImageIcon;
import javax.swing.JPanel;

import Driver.Driver;

public class Display extends JPanel implements KeyListener{

	private static final long serialVersionUID = 1L;
	ScheduledExecutorService executor;
	Random rn = new Random();
	Color trailColor = new Color(44,39,39);
	Color playerColor = new Color(204,0,0);
	ImageIcon player = new ImageIcon("res/playerImg.png");
	ImageIcon clear = new ImageIcon("res/0.png");
	ImageIcon blocked = new ImageIcon("res/1.png");
	ImageIcon t1 = new ImageIcon("res/2.png");
	ImageIcon t2 = new ImageIcon("res/3.png");
	ImageIcon t3 = new ImageIcon("res/4.png");
	ImageIcon t4 = new ImageIcon("res/5.png");
	ImageIcon background = new ImageIcon("res/background.png");
	String mazePointer = "res/maze.txt";
	int playerX = 25;
	int playerY = 75;
	int totalRow = 200;
	int totalColumn = 200;
	int collect1 = 0;
	int collect2 = 0;
	int collect3 = 0;
	int collect4 = 0;
	float r;
	float b;
	float g;
	char[][] mazeArray = new char[totalRow][totalColumn];
	ArrayList<Integer> trailX = new ArrayList<Integer>();
	ArrayList<Integer> trailY = new ArrayList<Integer>();
	ArrayList<Float> rList = new ArrayList<Float>();
	ArrayList<Float> gList = new ArrayList<Float>();
	ArrayList<Float> bList = new ArrayList<Float>();
	//////////////////////////////////////////////////////////////////////////////////////////////////////////////
	public Display(){
		fetchMaze();
		//Stage
		setPreferredSize(new Dimension(1900,1000));
		setLayout(new BorderLayout());
		addKeyListener(this);
		setFocusable(true);
		setBackground(Color.BLACK);
	}

	//////////////////////////////////////////////////////////////////////////////////////////////////////////////
	public void paintComponent(Graphics g){
		//Offsets to paint right
		int x = -12;
		int y = 10;
		super.paintComponent(g);
		g.drawImage(background.getImage(), 0, 0, null);		
		//paint the grid 
		for (int row = 0; row < totalRow; row++){
			for (int col = 0; col < totalColumn; col++){
				x+=25;
				g.drawImage(clear.getImage(), x, y, null);
				if(Character.toString(mazeArray[row][col]).equals("1")){
					g.drawImage(blocked.getImage(), x, y, null);
				}
				/*
				if(Character.toString(mazeArray[row][col]).equals("2")){
					g.drawImage(t1.getImage(), x, y, null);
				}
				if(Character.toString(mazeArray[row][col]).equals("3")){
					g.drawImage(t2.getImage(), x, y, null);
				}
				if(Character.toString(mazeArray[row][col]).equals("4")){
					g.drawImage(t3.getImage(), x, y, null);
				}
				if(Character.toString(mazeArray[row][col]).equals("5")){
					g.drawImage(t4.getImage(), x, y, null);
				}
				 */
			}
			y = y + 25;
			x = -12;
		}
		int i = -1;
		if((trailX.size()>0)&&(trailY.size()>0)){
			g.setColor(trailColor);
			do{
				i++;
				g.fillRect(trailX.get(i)+12, trailY.get(i)-40, 25, 25);
			}while(i<trailX.size()-1);
		}
		g.setColor(playerColor);
		g.fillRect(playerX+12, playerY-40, 24,24);
		//g.drawImage(player.getImage(), playerX+12, playerY-40, null);
		//g.drawString("playerX: \n" + playerX, playerX, playerY+15);
		//g.drawString("playerY: \n" + playerY, playerX, playerY);
		//g.drawString(Character.toString(mazeArray[playerY/25 -2][playerX/25]), playerX, playerY-15);
	}
	//////////////////////////////////////////////////////////////////////////////////////////////////////////////
	public void fetchMaze(){
		Driver d = new Driver();
		d.newMaze();
		importMaze();
	}
	//////////////////////////////////////////////////////////////////////////////////////////////////////////////
	public void importMaze(){
		File file = new File(mazePointer);
		Scanner scan = null;
		try {
			scan = new Scanner(file);
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		//populate the 2 dimensional array
		for (int row = 0; scan.hasNextLine() && row < totalRow; row++) {
			char[] chars = scan.nextLine().toCharArray();
			for (int i = 0; i < totalColumn && i < chars.length; i++) {
				mazeArray[row][i] = chars[i];
			}
		}
		scan.close();
	}
	//////////////////////////////////////////////////////////////////////////////////////////////////////////////
	@Override
	//////////////////////////////////////////////////////////////////////////////////////////////////////////////
	public void keyPressed(KeyEvent key) {
		//import Movement class and use its moveAround() method
		Movement m = new Movement();
		m.moveAround(key);
	}
	//////////////////////////////////////////////////////////////////////////////////////////////////////////////
	@Override
	//////////////////////////////////////////////////////////////////////////////////////////////////////////////
	public void keyReleased(KeyEvent arg0) {
		// TODO Auto-generated method stub
	}
	//////////////////////////////////////////////////////////////////////////////////////////////////////////////
	@Override
	//////////////////////////////////////////////////////////////////////////////////////////////////////////////
	public void keyTyped(KeyEvent arg0) {
		// TODO Auto-generated method stub
	}
	//*************************************************************************************************************
	public class Movement{
		//////////////////////////////////////////////////////////////////////////////////////////////////////////////
		public void moveAround(KeyEvent key){
			trailX.add(playerX);
			trailY.add(playerY);
			/*
			int i = -1;
			do{
				i++;
				System.out.println("x: " + trailX.get(i));
				System.out.println("Y: " + trailY.get(i) + "\n");	
			}while(i<trailX.size()-1);
			 */
			if(key.getKeyCode() == KeyEvent.VK_UP){
				playerY-=25;
				if(playerY<25){
					playerY = 25;
				}
				if(Character.toString(mazeArray[playerY/25 -2][playerX/25]).equals("1")){
					playerY+=25;
				}
				if(Character.toString(mazeArray[playerY/25 -2][playerX/25]).equals("2")){
					mazeArray[playerY/25 -2][playerX/25] = 0;
					collect1 +=1;
				}
				if(Character.toString(mazeArray[playerY/25 -2][playerX/25]).equals("3")){
					mazeArray[playerY/25 -2][playerX/25] = 0;
					collect2 +=1;
				}
				if(Character.toString(mazeArray[playerY/25 -2][playerX/25]).equals("4")){
					mazeArray[playerY/25 -2][playerX/25] = 0;
					collect3 +=1;
				}
				if(Character.toString(mazeArray[playerY/25 -2][playerX/25]).equals("5")){
					mazeArray[playerY/25 -2][playerX/25] = 0;
					collect4 +=1;
				}
			}
			if(key.getKeyCode() == KeyEvent.VK_DOWN){
				playerY+=25;
				if(playerY>1000){
					playerY = 1000;
				}
				if(Character.toString(mazeArray[playerY/25 -2][playerX/25]).equals("1")){
					playerY-=25;
				}
				if(Character.toString(mazeArray[playerY/25 -2][playerX/25]).equals("2")){
					mazeArray[playerY/25 -2][playerX/25] = 0;
					collect1 +=1;
				}
				if(Character.toString(mazeArray[playerY/25 -2][playerX/25]).equals("3")){
					mazeArray[playerY/25 -2][playerX/25] = 0;
					collect2 +=1;
				}
				if(Character.toString(mazeArray[playerY/25 -2][playerX/25]).equals("4")){
					mazeArray[playerY/25 -2][playerX/25] = 0;
					collect3 +=1;
				}
				if(Character.toString(mazeArray[playerY/25 -2][playerX/25]).equals("5")){
					mazeArray[playerY/25 -2][playerX/25] = 0;
					collect4 +=1;
				}
			}
			if(key.getKeyCode() == KeyEvent.VK_LEFT){
				playerX-=25;
				if(playerX<0){
					playerX = 0;
				}
				if(Character.toString(mazeArray[playerY/25 -2][playerX/25]).equals("1")){
					playerX+=25;
				}
				if(Character.toString(mazeArray[playerY/25 -2][playerX/25]).equals("2")){
					mazeArray[playerY/25 -2][playerX/25] = 0;
					collect1 +=1;
				}
				if(Character.toString(mazeArray[playerY/25 -2][playerX/25]).equals("3")){
					mazeArray[playerY/25 -2][playerX/25] = 0;
					collect2 +=1;
				}
				if(Character.toString(mazeArray[playerY/25 -2][playerX/25]).equals("4")){
					mazeArray[playerY/25 -2][playerX/25] = 0;
					collect3 +=1;
				}
				if(Character.toString(mazeArray[playerY/25 -2][playerX/25]).equals("5")){
					mazeArray[playerY/25 -2][playerX/25] = 0;
					collect4 +=1;
				}
			}
			if(key.getKeyCode() == KeyEvent.VK_RIGHT){
				playerX+=25;
				if(playerX>1850){
					playerX = 25;
					playerY = 75;
					trailX.clear();
					trailY.clear();
					fetchMaze();
				}
				if(Character.toString(mazeArray[playerY/25 -2][playerX/25]).equals("1")){
					playerX-=25;
				}
				if(Character.toString(mazeArray[playerY/25 -2][playerX/25]).equals("2")){
					mazeArray[playerY/25 -2][playerX/25] = 0;
					collect1 +=1;

				}
				if(Character.toString(mazeArray[playerY/25 -2][playerX/25]).equals("3")){
					mazeArray[playerY/25 -2][playerX/25] = 0;
					collect2 +=1;

				}
				if(Character.toString(mazeArray[playerY/25 -2][playerX/25]).equals("4")){
					mazeArray[playerY/25 -2][playerX/25] = 0;
					collect3 +=1;

				}
				if(Character.toString(mazeArray[playerY/25 -2][playerX/25]).equals("5")){
					mazeArray[playerY/25 -2][playerX/25] = 0;
					collect4 +=1;
				}
			}
			requestFocusInWindow();
			repaint();
		}
	}
}