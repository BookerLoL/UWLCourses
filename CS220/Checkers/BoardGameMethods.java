/**
 * Write a description of interface BoardGameMethods here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public interface BoardGameMethods
{
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
