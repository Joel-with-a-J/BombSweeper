import java.util.*;
/**a GameSquare which behaves in a way to play minesweeper
 * @author Joel Anderson
 */
public class BombSquare extends GameSquare
{
	private boolean thisSquareHasBomb = false;
	public static final int MINE_PROBABILITY = 10;
	private Random r = new Random();
	private boolean discoverd = false;
	private boolean exploded = false;

	/**
	 * creates a new bomb square
	 * @param x x position of Bombsquare
	 * @param y y position of Bombsquare
	 * @param board the board the scuare is made on
	 */
	public BombSquare(int x, int y, GameBoard board)
	{		
		super(x, y, "images/blank.png", board);

		thisSquareHasBomb = (r.nextInt(MINE_PROBABILITY) == 0);
		
	}

	/**
	 * returns1 if the square is a bomb else 0
	 * @return 1 if this square has a bomb else 0
	 */
	public int isBomb(){
		if(thisSquareHasBomb)
			return 1;
		else
			return 0;
	}

	/**
	 * when the square is clicked
	 */
	public void clicked()
	{
		if(!discoverd){
			discoverd = true;

			if(thisSquareHasBomb)
				showBombs();
			else
				this.setImage("images/"+String.valueOf(this.touching())+".png");
			
			expand();

		}
	

	}
	
	/**
	 * counts the number of bombs the square is touching
	 * @return the number of bombs the square is touching
	 */
	public int touching(){
		int touching = 0;
		//loops through all surrounding squares
		for(int i = -1; i < 2; i++)
		for(int j = -1; j < 2; j++)
			try{
				touching += ((BombSquare)board.getSquareAt(xLocation + i, yLocation + j)).isBomb();
			}
			catch(NullPointerException e){}
		

		return touching;
	}

	/**
	 * expands the area when the square clicked is 0
	 */
	private void expand(){
		if(this.touching() == 0)

		//clicks on all the squares surrounding the square
		for(int i = -1; i < 2; i++)
		for(int j = -1; j < 2; j++)
			try{
				((BombSquare)board.getSquareAt(xLocation + i, yLocation + j)).clicked();
			}
			catch(NullPointerException e){}
		
	}

	/**
	 * shows all bombs on the Board
	 */
	private void showBombs(){
		if(!exploded){
			exploded = true;

			if(thisSquareHasBomb)
				this.setImage("images/bomb.png");


			//this is like the expand loop but faster because it doesnt need to go into corners
			try{
				((BombSquare)board.getSquareAt(xLocation + 1, yLocation)).showBombs();
				((BombSquare)board.getSquareAt(xLocation - 1, yLocation)).showBombs();
				((BombSquare)board.getSquareAt(xLocation, yLocation + 1)).showBombs();
				((BombSquare)board.getSquareAt(xLocation, yLocation - 1)).showBombs();
			}
			catch(NullPointerException e){}


		}

	}
}
