/**
 * A library for representing a Reversi Board 
 * for Assignments Seriess 2, Fall 2017.
 * 
 * 
 * @author Ethan Booker 
 * @version 1.0
 * @since 9/19/2017 
 */
public class Board implements BoardMethods
{
   /**
    * The turnCounter identifier represents players turn throughout the game
    */
   private static int turnCounter;
    
   /**
    * The gameBoard identifier represents the gameboard.  
    */
   private String[][] gameBoard;
    
   /**
    * Standard Reversi Board size
    */
   private final String[][] DEFAULT_REVERSI_BOARD = new String[8][8];
    
   /**
    * Initial value for determining player's turn
    */
   private final short START_COUNT = 0;
    
   /**
    * Player token values for player1 and player2
    */
   private final String P1_TOKEN_VALUE = "x";
   private final String P2_TOKEN_VALUE = "o"; 
    
   /**
    * Directions for token movements
    */
   private final int MOVE_BACKWARDS = -1;
   private final int MOVE_FORWARDS = 1;
    
   /**
    * Maximum token movement 
    */
   private final int MAX_MOVEMENT = 7;
    
   /**
    * Default Constructor
    * sets up the Board object fields
    * 
    */
   Board()
   {    
       //Initialization of fields 
       turnCounter = START_COUNT;
       gameBoard = DEFAULT_REVERSI_BOARD;
        
       //Finishes initializing gameBoard
       setUpInitalBoard();
   }
    
   /**
    * Constructor for Board that contains the state of the game after given move
    * @param Board (required) helps create an updated Board object 
    * @param row (required) used to invoke move method
    * @param column (required) used to invoke move method
    */
   Board(final Board oldBoard, final int row, final int column)
   {
       Board updatedBoard = oldBoard.move(row, column);
       
       //Prevents previous board from being affected 
       gameBoard = updatedBoard.gameBoard.clone();
   }
     
   /**
    * Helper method for creating contents of the board
    */
   private void setUpInitalBoard()
   {
       //Sets up board full of '.' 
       for(int row = 0; row < gameBoard.length; row++)
       {
         for(int column = 0; column < gameBoard[row].length; column++)
         {
           gameBoard[row][column] = ".";
         }
       }
        
       //Setsup the middle of the gameboard
       gameBoard[3][3] = P2_TOKEN_VALUE;
       gameBoard[3][4] = P1_TOKEN_VALUE;
       gameBoard[4][3] = P1_TOKEN_VALUE;
       gameBoard[4][4] = P2_TOKEN_VALUE;
   }
    
   /**
    * Displays an 8 eight-line string representing the board. 
    * 
    * @Override Creates the gameboard representation 
    * @return String This returns a presentation of the board 
    */
   public String toString()
   {
       //Initalization of variables
       String boardDisplay = "";
       for(int row = 0; row < gameBoard.length; row++)
       {
         for(int column = 0; column < gameBoard[row].length; column++)
         {
           boardDisplay += gameBoard[row][column];
         }
         if(row < gameBoard.length-1) //Prevents last row from creating a new line
         {
           boardDisplay += "\n";
         }
       }
       return boardDisplay; 
   }
    
   /**
    * Indicates whether the given board position contains some player
    * token.
    * 
    * @param row (required) Specific gameBoard row
    * @param column (required) Specific gameBoard column
    * @return boolean true if the board position contains a player token 
    */
   public boolean empty(final int row, final int column)
   {
       //Must -1 for row and column as the test conditions are +1 from the gameBoard.
       if(gameBoard[row-1][column-1] != ".")
       {
         return false;
       }
       return true;
   }
    
   /**
    * Identifies the player who has a token at the given position.
    * NOTE: THIS METHOD IS IMPLEMENTED INCORRECTLY, COULD HAD SAVED A LOT OF TROUBLE IN MY CODING IF THIS WASNT WRONG 
    * 
    * @param row (required) Specific gameBoard row
    * @param column (required) Specific gameBoard column
    * @return boolean true depending if the current player has a token at the position
    * @exception IndexOutOfBoundsException   
    */
   public boolean token(final int row, final int column) throws IndexOutOfBoundsException
   {
       //Prevents program from crashing if trying to index out of array
       try
       {
         if(!empty(row, column))
         {
           if(gameBoard[row-1][column-1]== P1_TOKEN_VALUE)
           {
             //Ensures it's player1's token
             return true;
           }
           else 
           {
             //Ensures it's players2's token
             return false;
           }
         }
       }
       catch(IndexOutOfBoundsException e) //Do nothing
       {
       }
        
       //Default value assuming empty method returns true 
       return false;
   }
    
   /**
    *  Determines if the game is over
    *  
    *  @return boolean false if there is a possible move for either player   
    */
   public boolean gameOver()
   {
       //If either player still has a move then game isn't overt
       if( (toPlay() || !toPlay()) & moves().length != 0 )
       {
         return false;
       }        
       return true;
   }
    
   /**
    *  Identifies the player who is next to play on this board. 
    *  
    *  @return boolean true if it's player 1 and false if it's player 2's turn          
    */
   public boolean toPlay() 
   {
       //Determines if even or odd for players turn
       if(turnCounter % 2 == 0)
       {
         return true; 
       }
       return false;
    }
    
   /**
     *  Helper method: determines if the current player has a token at that given spot. 
     *  
     *  @return boolean true for p1 and true for p2 if the token at the board position is the current player's token
     */
   boolean hasCurrentPlayerToken(final int row, final int column)
   {
       if(toPlay() && token(row, column)) //For player 1
       {
         return true;
       }
       else if(!toPlay() && !token(row, column) && gameBoard[row][column] != ".") //For player 2
       {
         return true;  
       }
       return false; 
   }
    
    
   /**
    *  Helper method: checks to see if current spot has an opponent's token 
    *  
    *  @return boolean true or false if the token at the board position is the opponent's token
    *  @exception IndexOutOfBoundsException  
    */
   boolean hasCurrentOpponentToken(final int row, final int column)
   {
       //Prevents indexing out of array. 
       try
       {
         if(toPlay() && gameBoard[row-1][column-1]== P2_TOKEN_VALUE) //For player 1 checking opponenet 
         {
           return true;
         }
         else if(!toPlay() && gameBoard[row-1][column-1]== P1_TOKEN_VALUE) //For player 2 checking opponenet
         {
           return true;   
         }
       }
       catch(IndexOutOfBoundsException e) //Do nothing
       {
       }
       return false;
   }
    
    
    
   /**
    *  Helper method: Looks for a pattern of opponent and player tokens in a given direction from starting point
    *  
    *  @param row (required) determines row in the game board
    *  @param column (required) determines column in the game board
    *  @param rowChange (required) determines movement in row
    *  @param columnChange (required) determines movement in column 
    *  @return int number of opponent player token's 
    */
   int countSwappableFrom(final int row, final int column, final int rowChange, final int columnChange)
   {
       int counter = 0; //Counter to hold number tokens that are valid to be swapped due to the desired move and direction 
       int row2 = row; //Modifiable row
       int column2 = column; //Modifiable column
       
       //Checks for initial bad input
       if(row <= 0 || row > gameBoard.length || column <= 0 || column > gameBoard.length)
       {
         return 0;
       }
       else if(rowChange > 1 || rowChange < -1 || columnChange > 1 || columnChange < -1)
       {
         return 0;
       }
       else if(row + rowChange <= 0 || row + rowChange > gameBoard.length || column + columnChange <= 0 || column + columnChange > gameBoard.length)
       {
         return 0;
       }
       
       //Counts in the certain direction the number of available and valid tokens that can be swapped 
       if(empty(row, column))
       {
         for(int i = START_COUNT; i <= MAX_MOVEMENT; i++) //Used to iterate through entire row / column length 
         {
           //Ensures the next spot due to direction and move spot has an opponent token
           if(hasCurrentOpponentToken(row2 += rowChange, column2 += columnChange) && !empty(row2, column2))
           {
             //Due to (+=), must reset modifable 
             row2 -= rowChange;   
             column2 -=columnChange;
             
             //Stops looping if next spot due to direction and move spot has current player token 
             if(hasCurrentPlayerToken(row2 += rowChange, column2 += columnChange) && !empty(row2, column2)) //Must check if not empty due to token() default value of false when empty() returns true 
             {
               break;
             }
             else
             {
               //Due to (+=), must reset modifable 
               row2 -= rowChange;    
               column2 -=columnChange;  
               
               //When iterating through through the board and encounter another empty spot after the desired move spot then no tokens will be swapped
               if(empty(row2 += rowChange, column2 += columnChange))
               {
                 return 0;
               }
               else
               {
                 counter++;
               }
             }
           }
           else
           {
             //Due to (+=), must reset modifable 
             row2 -= rowChange; 
             column2 -= columnChange;
             
             //Prevents board run offs when counting valid opponent tokens by making the direction and move an invalid move
             if(!hasCurrentOpponentToken(row2 += rowChange, column2 += columnChange) && ((row2 <= 0 || row2 >gameBoard.length || column2 <= 0 || column2 >gameBoard.length )||empty(row2, column2)) )
             {
               return 0;
             }
             break;
           }
         }
       }
       return counter;
   }
    
    
    
   /**
    *  Determines if the particular space is valid move for current player.
    *  Return value isn't specified if the game is over.
    *  
    *  @param row (required) Specific gameBoard row
    *  @param column (required) Specific gameBoard column
    *  @return boolean true if the particular space is a valid move    
    */
   public boolean isMove(final int row, final int column)
   {
       //Must have at least 1 token swap at desired move to be considered a move
       if(swaps(row, column) > 0)
       {
         return true;
       }
       return false;
   }
    
   /** 
    *  Enumerates the valid moves on the board. 
    *  
    *  @return int[][] 2D array containing nonnull element array or zero element array  
    */
   public int[][] moves()
   {
       //Declaration and initialization of variables
       int[][] moves;
       int rowCounter = 0;
       int rowIterator = 0;
       int columnIterator = 0;
       
       //Iterates through to see how many rows the array will need that contains all the valid moves
       for(int row = 0; row <= gameBoard.length; row++)
       {
         for(int column = 0; column <= gameBoard[0].length; column++)
         {
           if(isMove(row, column))
           {
             rowCounter++;
           }
         }
       }
        
       //Initializing the moves[][] array
       if(rowCounter == 0)
       {
         return new int[0][0];
       }
       else 
       {
         moves = new int[rowCounter][2];
       }
        
       //Assigns valid positions into moves[][] array
       for(int row = 0; row <= gameBoard.length; row++)
       {
         for(int column = 0; column <= gameBoard[0].length; column++)
         {
           if(isMove(row, column))
           {
             moves[rowIterator++][0] = row;
             moves[columnIterator++][1] = column;
           }
         }
       }
       return moves;
   }
    
   /** 
    *  The number of oppponent tokens that would be swapped as a result of
    *  a move at the given row and column. If there is no legal move at 
    *  the coordinate or the coordinate is not valid then 0 tokens will 
    *  be returned.
    *  
    *  @param row (required) Specific gameBoard row
    *  @param column (required) Specific gameBoard column
    *  @return int Number of opponent tokens swapped due to move at specific coordinate     
    */
   public int swaps(final int row, final int column)
   {       
       //Declaration and initialization of variable
       int swapCounter = 0;
        
       //Iterates moves at a given spot in all directions
       for(int rowDirection = MOVE_BACKWARDS; rowDirection <= MOVE_FORWARDS; rowDirection++)
       {
         for(int columnDirection = MOVE_BACKWARDS; columnDirection <= MOVE_FORWARDS; columnDirection++)
         {
           if(countSwappableFrom(row, column, rowDirection, columnDirection) >= 1)
           {
             //Total amount of token swaps to occur for desired move
             swapCounter += countSwappableFrom(row, column, rowDirection, columnDirection);
           }
         }
       }   
       return swapCounter;
   }
    
   /**
    *  Changes the Board resulting from the player using toPlay method playing
    *  a token at a board posistion and swapping all allowed oppponent tokens. 
    *  Modifications to the array musts have no effect on this Board.
    *  
    *  @param row (required) Specific gameBoard row
    *  @param colum (required) Specific gameBoard column
    *  @return Board This returns Board 
    *  @exception IllegalArgumentException throw exception if coordinates isn't a valid move for current player 
    *  @exception IndexOutOfBoundsException throw exception if coordiinates isn't valid board space
    */
   public Board move(final int row, final int column)
   {
       try
       {
         if(row <= 0 || row > gameBoard.length || column <= 0 || column > gameBoard[0].length)
         {
           throw new IndexOutOfBoundsException();
         }
         else if(!isMove(row, column))
         {
           throw new IllegalArgumentException();
         }
         else 
         {
           if(toPlay()) //Player 1's move
           {
             {
               //Used as modifiable row's and column's for iteration purposes 
               int row2 = row-1;
               int col2 = column-1;
               
               //Checks all directions to see if tokens need to be swapped in any of the directions then swaps them accordingly
               for(int rowDirection = MOVE_BACKWARDS; rowDirection <= MOVE_FORWARDS; rowDirection++)
               {
                 for(int columnDirection = MOVE_BACKWARDS; columnDirection <= MOVE_FORWARDS; columnDirection++)
                 {
                   int mover = countSwappableFrom(row, column, rowDirection, columnDirection);
                   
                   for(int moveCounter = 0; moveCounter < mover; moveCounter++)
                   {
                     gameBoard[row2+=rowDirection][col2+=columnDirection] = P1_TOKEN_VALUE; ;
                   }
                   //Must reset modifiable row's and column's due to ( += ) affecting their current value
                   row2 = row-1;
                   col2 = column-1;
                 }
               }
               gameBoard[row-1][column-1] = P1_TOKEN_VALUE; //Puts token at desired board move 
               turnCounter++; //Advances to the other player
             }
           }
           else 
           {
             {
               //Used as modifiable row's and column's for iteration purposes 
               int row2 = row-1;
               int col2 = column-1;
               
               //Checks all directions to see if tokens need to be swapped in any of the directions then swaps them accordingly
               for(int rowDirection = MOVE_BACKWARDS; rowDirection <= MOVE_FORWARDS; rowDirection++)
               {
                 for(int columnDirection = MOVE_BACKWARDS; columnDirection <= MOVE_FORWARDS; columnDirection++)
                 {
                   int mover = countSwappableFrom(row, column, rowDirection, columnDirection);
                   
                   for(int moveCounter = 0; moveCounter < mover; moveCounter++)
                   {
                     gameBoard[row2+=rowDirection][col2+=columnDirection] = P2_TOKEN_VALUE; ;
                   }
                   //Must reset modifiable row's and column's due to ( += ) affecting their current value
                   row2 = row-1;
                   col2 = column-1;
                          }
                        }
               gameBoard[row-1][column-1] = P2_TOKEN_VALUE; //Puts token at desired board move 
               turnCounter++; //Advances to the other player
             }
           }
         }
       }
       catch(IllegalArgumentException e)
       {
         throw e;
       }
       catch(IndexOutOfBoundsException e)
       {
         throw e;
       }
       
       //If next player's turn has no moves then other players turn again
       if(moves().length == 0)
       {
         turnCounter++;
       }
       return this;
   }
    
    
   /**
    *  Determines the number of board positions occupied by a token of player 1
    *  
    *  @return int This returns an int value between zero and 64 (inclusive)    
    */
   public int score1()
   {
      //initialization for token counter for p1
      int counter = 0;
       
      //Iterates through game board and counts tokens that are P1's
      for(int row = 0; row < gameBoard.length; row++)
      {
        for(int column = 0; column < gameBoard[0].length; column++)
        {
          if(gameBoard[row][column] == P1_TOKEN_VALUE)
          {
            counter++;
          }
        }
      }
      return counter;
   }
    
   /**
    *  Determines the number of board positions occupied by a token of player 2
    *  
    *  @return int This returns an int value between zero and 64 (inclusive)    
    */
   public int score2()
   {
      //initialization for token counter for p1
      int counter = 0;
       
      //Iterates through game board and counts tokens that are P1's
      for(int row = 0; row < gameBoard.length; row++)
      {
        for(int column = 0; column < gameBoard[0].length; column++)
        {
          if(gameBoard[row][column] == P2_TOKEN_VALUE)
          {
            counter++;
          }
        }
      }
      return counter;
   }
}
