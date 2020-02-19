/**
 * Methods to be implemented by the {@link Board} class for CS220
 * Assignments Series 2, Fall 2017.
 */
public interface BoardMethods {

  /** The result of the <code>toString</code> method on
   * <code>Board</code> should return an eight-line string
   * representing the board.  As in the examples in this assignment,
   * use a period (<code>.</code>) to represent a blank space, a
   * lower-case letter <code>x</code> to represent the tokens of the
   * first player, and a lower-case letter <code>o</code> to represent
   * the tokens of the second player.  In between the eight characters
   * for each row there should be the single carriage return denoted
   * in Java strings as <code>\n</code>.  There should be <i>no</i>
   * carriage return at the end of the string.
   */
  public String toString();

  /** Indicates whether the given board position contains some player
   * token.
   *
   * The <code>empty</code> method returns <code>true</code> when
   * there is no player token at the given <code>row</code> and
   * <code>column</code>, and <code>false</code> where there is a
   * token there.
   */
  public boolean empty(int row, int column);

  /** Identifies the player who has a token at the given position.
   *
   * Whenever <code>empty</code> returns <code>false</code> for some
   * <code>row</code> and <code>column</code>, the <code>token</code>
   * method returns <code>true</code> when the given <code>row</code>
   * and <code>column</code> holds a token of the first
   * (<code>x</code>) player, and <code>false</code> if of the second
   * (<code>o</code>) player.  Whenever the <code>empty</code> method
   * returns <code>true</code>, the return value of <code>token</code>
   * is not specified.
   *
   * If the <code>row</code> and <code>column</code> do not address a
   * valid board space, then this method should throw an
   * {@link java.lang.IndexOutOfBoundsException IndexOutOfBoundsException}.
   */
  public boolean token(int row, int column);

  /** Returns whether the game is over.
   *
   * The <code>gameOver</code> method should return <code>true</code>
   * exactly when it is no longer possible for either player to move,
   * and therefore the game is over.
   */
  public boolean gameOver();

  /** Identifies the player who is next to play on this board.
   *
   * Whenever the <code>gameOver</code> method returns
   * <code>false</code>, the <code>toPlay</code> method should return
   * <code>true</code> if it is the first player's turn
   * (<code>x</code>), or <code>false</code> if it is the second
   * player's turn.  The result of the <code>toPlay</code> method is
   * not specified when the <code>gameOver</code> method returns
   * <code>true</code>.
   */
  public boolean toPlay();

  /** Determine whether a particular space is a valid move for the
   * current player.
   *
   * If the game is over, the return value of this method is not
   * specified..
   */
  public boolean isMove(int row, int column);

  /** Enumerates the valid moves on this board.
   *
   * The <code>moves</code> method returns an array of arrays of
   * integers.  Each contained array is a row-column coordinate as
   * described above.  This method should never return
   * <code>null</code>; if there are no valid moves, it must return a
   * zero-element array.
   *
   * Modifications to the array returned by this method must have no
   * effect on this <code>Board</code>.
   */
  public int[][] moves();

  /** Returns the number of opponent tokens which would be swapped as
   * a result of a move at the given <code>row</code> and
   * <code>column</code>.
   *
   * If there is no legal move at the given coordinate, or if the
   * coordinate is not a valid coordinate, this method should return
   * 0.
   */
  public int swaps(int row, int column);

  /** Returns the <code>Board</code> which results from the player
   * indicated by <code>toPlay</code> playing a token at the given
   * board position and swapping all allowed opponent tokens.
   *
   * If the <code>row</code> and <code>column</code> do not address a
   * valid board space, then this method should throw an
   * {@link java.lang.IndexOutOfBoundsException IndexOutOfBoundsException}.
   *
   * If the <code>row</code> and <code>column</code> do not address a
   * space which is a valid move for the current player, then this
   * method should throw an
   * {@link java.lang.IllegalArgumentException IllegalArgumentException}.
   *
   * Calling this method should not alter this object in any way.
   */
  public Board move(int row, int column);

  /** Returns the number of board positions occupied by a token of the
   * first player (<code>x</code>).
   *
   * @return A value between zero and 64 (inclusive)
   */
  public int score1();

  /** Returns the number of board positions occupied by a token of the
   * second player (<code>o</code>).
   *
   * @return A value between zero and 64 (inclusive)
   */
  public int score2();
}
