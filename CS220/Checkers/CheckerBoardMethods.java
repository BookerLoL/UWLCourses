/**
 * An interface that contains the necessary methods to be able to play a normal game of checkers
 * 
 * @author Ethan Booker 
 * @version 1.0
 * @since 10/1/2017
 */
public interface CheckerBoardMethods extends BoardGameMethods
{
    public boolean isEmpty(final int row, final int column);
    public void takePiece();
    public boolean isStalement();
    public int turnCounter(); 
    public void declareDraw();
    
    public void setUpInitialBoard();
    public boolean isGameOver();
    public void gameOver();
    public void forfeit(); 
    public boolean isMove(int row, int column);
    public boolean whosTurn();
    public int[][] validBoardMoves();
    public String toString();
    public Object move(int row, int column);
}
