import java.applet.AudioClip;
import java.awt.Color;
import java.awt.Font;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

import acm.graphics.*;
import acm.program.GraphicsProgram;
import acm.util.MediaTools;
import acm.util.RandomGenerator;

public class SnakeGame extends GraphicsProgram {

	public static final int APPLICATION_WIDTH = 800;
	public static final int APPLICATION_HEIGHT = 600;


	public static SnakePiece[][] grid = new SnakePiece[20][15];
	public static boolean start = false;
	private GLabel messageLabel = new GLabel("");
	private int TargetPiece_Width = 40;
	private int TargetPiece_Height = 40;
	private GRect TargetPiece;
	private GLabel scoreLabel;
	private static GLabel youLoseLabel;
	public static int SCORE = 0;
	private int pauseLength = 120;
	private int randomTargetX;
	private int randomTargetY;
	private RandomGenerator rgen = RandomGenerator.getInstance();
	private ArrayList<SnakePiece> arraySnakePiece = new ArrayList<SnakePiece>();
	private static SnakeGame game;
	

	//grid dimentions of screen are 20 width and 15 high


	public void run(){

		setup();
		gameLoop();

	}//run brace



	public void setup(){

		setBackground(Color.black);
		addKeyListeners();
		addMouseListeners();
		statusMessage("Click to start");
		makeTargetPiece();
		makeScoreLabel();
		game = this;


		SnakePiece sp = new SnakePiece(5, 5);
		add(sp);
		arraySnakePiece.add(sp);

		


	}//setup brace


	public void gameLoop(){

		boolean alreadyStarted = false;

		while(true){



			//necessary to get start variable to work
			if(!alreadyStarted){
				println(start);
				if(start){
					alreadyStarted = true;
				}
			}

			//makes click to start: o {)
			if(start == true){


				moveAllBackPieces();
				updateGridLocations();
				arraySnakePiece.get(0).move();


				targetPieceColisionDetection();


				pause(pauseLength);

			}// if(start == true) brace








		}//while true brace

	}//gameLoop brace



	//detect if the mouse is clicked. the funcsion does not need to be called.
	public void mouseClicked(MouseEvent mouse){

		start = true;
		remove(messageLabel);
	}


	public void statusMessage(String message){

		remove(messageLabel);
		messageLabel = new GLabel(message,100, 400);
		messageLabel.setFont(new Font(Font.SANS_SERIF, Font.BOLD,30));
		messageLabel.setColor(Color.white);
		messageLabel.setLocation(APPLICATION_WIDTH/2 - messageLabel.getWidth()/2,300);
		add(messageLabel);

	}




	public void keyPressed(KeyEvent a) {

		//if up is pushed
		if(a.getKeyCode() == KeyEvent.VK_UP && arraySnakePiece.get(0).wayIsMoving != 3) {
			arraySnakePiece.get(0).changeWayIsMovingUp();
			

		}

		//if down is pushed
		if(a.getKeyCode() == KeyEvent.VK_DOWN && arraySnakePiece.get(0).wayIsMoving != 4) {
			arraySnakePiece.get(0).changeWayIsMovingDown();
			
		}
		//if left is pushed
		if(a.getKeyCode() == KeyEvent.VK_LEFT && arraySnakePiece.get(0).wayIsMoving != 1) {
			arraySnakePiece.get(0).changeWayIsMovingLeft();
			
		}

		//if right is pushed
		if(a.getKeyCode() == KeyEvent.VK_RIGHT && arraySnakePiece.get(0).wayIsMoving != 2) {
			arraySnakePiece.get(0).changeWayIsMovingRight();
			
		}


	}//keyPressed "a" brace
	


	private void makeTargetPiece(){
		randomTargetPiece();
		TargetPiece = new GRect(randomTargetX * 40, randomTargetY * 40, TargetPiece_Width ,TargetPiece_Height );
		TargetPiece.setColor(Color.blue);
		TargetPiece.setFilled(true);
		TargetPiece.setFillColor(Color.blue);
		add(TargetPiece);

	}//makeTargetPiece


	public void scoreLabel(){

		scoreLabel = new GLabel("You Lost :]", 100, 100);
		scoreLabel.setFont(new Font(Font.SANS_SERIF, Font.BOLD,15));
		scoreLabel.setColor(Color.white);
		add(scoreLabel);

	}//scoreLabel brace


	public void randomTargetPiece(){
		
		randomTargetX = rgen.nextInt(0, 19);
		randomTargetY = rgen.nextInt(0, 14);

		while(grid[randomTargetX][randomTargetY] != null){
			
			randomTargetX = rgen.nextInt(0, 19);
			randomTargetY = rgen.nextInt(0, 14);
			
			
		}
		

	}


	public void targetPieceColisionDetection(){

		if((arraySnakePiece.get(0).returnGridX() == randomTargetX) && 
				(arraySnakePiece.get(0).returnGridY() == randomTargetY)){

			remove(TargetPiece);
			makeTargetPiece();

			SnakePiece sp = new SnakePiece(-1, -1);
			add(sp);
			arraySnakePiece.add(sp);

			SCORE = SCORE + 5;

			scoreLabel.setLabel("score: "+ SCORE);


		}



	}


	public void moveAllBackPieces(){

		for(int j = arraySnakePiece.size()-1; j >0 ; j--){
			SnakePiece currentSP = arraySnakePiece.get(j);
			SnakePiece frontSP = arraySnakePiece.get(j - 1);
			currentSP.setGridLocation(frontSP.returnGridX(), frontSP.returnGridY());

		}

	}//moveAllBackPieces brace


	public void updateGridLocations(){

		grid = new SnakePiece[20][15];
		for(int j = 0; j <  arraySnakePiece.size() ; j++){
			SnakePiece currentSP = arraySnakePiece.get(j);
			grid[currentSP.returnGridX()][currentSP.returnGridY()] = currentSP;
		}

	}


	public void makeScoreLabel(){

		scoreLabel = new GLabel("score: " + SCORE, 5, 15);
		//scoreLabel.setFont(new Font(Font.SANS_SERIF, Font.BOLD,30));
		scoreLabel.setColor(Color.white);
		add(scoreLabel);

	}

	
	
	public static void makeYouLoseLabel(){

		youLoseLabel = new GLabel("You Lose, your score: " + SCORE, 235, 300);
		youLoseLabel.setFont(new Font(Font.SANS_SERIF, Font.BOLD,30));
		youLoseLabel.setColor(Color.white);
		SnakeGame.game.add(youLoseLabel);

	}



}//end brace