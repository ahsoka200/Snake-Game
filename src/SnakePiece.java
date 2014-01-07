import java.awt.Color;
import java.awt.Point;

import acm.graphics.GLabel;
import acm.graphics.GRect;


public class SnakePiece extends GRect {

	private static int SP_Width = 40;
	private static int SP_Height = 40;
	public int wayIsMoving; //1 is right, 2 is left, 3 is down, and 4 is up.
	private int gridX;
	private int gridY;
	



	public SnakePiece(int x, int  y){

		super(x * 40, y * 40, SP_Width ,SP_Height);

		gridX = x;
		gridY = y;


		setColor(Color.red);
		setFilled(true);
		setFillColor(Color.red);


		wayIsMoving = 1;


	}//makeSnakePiece



		//boundary checking
	private boolean isSpotValid(Point point){

		if(point.x < 0){
			SnakeGame.makeYouLoseLabel();
			SnakeGame.start = false;
			return false; 

		}

		if(point.x >= 20){
			SnakeGame.makeYouLoseLabel();
			SnakeGame.start = false;
			return false;
		}

		if(point.y < 0){
			SnakeGame.makeYouLoseLabel();
			SnakeGame.start = false;
			return false;
		}

		if(point.y >= 15){
			SnakeGame.makeYouLoseLabel();
			SnakeGame.start = false;
			return false;
		}
		
		if(SnakeGame.grid[point.x][point.y] != null){
			SnakeGame.makeYouLoseLabel();
			SnakeGame.start = false;
			return false;
		}

		return true;


	}





	private Point calculateNextSpot(){

		int newGridX = gridX;
		int newGridY = gridY;


		if(wayIsMoving == 1){
			//right
			newGridX++;
		}

		if(wayIsMoving == 2){
			//left
			newGridX--;
		}

		if(wayIsMoving == 3){
			//down
			newGridY++;
		}

		if(wayIsMoving == 4){
			//up
			newGridY--;
		}

		return new Point(newGridX, newGridY);

	}//nextSpot brace






	public void move(){



		//calculate next grid location
		Point nextSpot = calculateNextSpot();

		if(isSpotValid(nextSpot) == true){

			gridX = nextSpot.x;
			gridY = nextSpot.y;
			setLocation(gridX*40, gridY*40);
		}


	}




	public void changeWayIsMovingRight(){

		wayIsMoving = 1;

		System.out.println("right pressed");

	}

	public void changeWayIsMovingLeft(){

		wayIsMoving = 2;

	}

	public void changeWayIsMovingDown(){

		wayIsMoving = 3;

	}

	public void changeWayIsMovingUp(){

		wayIsMoving = 4;

	}



	public int returnGridX(){

		return gridX;


	}


	public int returnGridY(){

		return gridY;


	}
	
	public void setGridLocation(int x, int y){
		
		gridX = x;
		gridY = y;
		
		setLocation(x * 40, y * 40);
		
		
	}//setGridLocation brace


	

}// end brace 
