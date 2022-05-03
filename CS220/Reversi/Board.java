/**
 * Reversi Board implementation for Assignments Seriess 2, Fall 2017.
 * <p>
 * - Updated to pass all unit tests and to not use any packages.
 *
 * @author Ethan Booker
 * @version 1.0
 *          since 12/03/2021@since 12/03/2021
 */
public class Board implements BoardMethods {
    /**
     * The turnCounter identifier represents players turn throughout the game
     */
    private int turnCounter;

    /**
     * The gameBoard identifier represents the gameboard.
     */
    private String[][] gameBoard;

    /**
     * Player token values for player1 and player2
     */
    private static final String PLAYER_1_TOKEN = "x";
    private static final String PLAYER_2_TOKEN = "o";
    private static final String EMPTY_POSITION_TOKEN = ".";
    private static final int MIN_SWAPS_REQUIRED = 1;

    private static final int MAX_ROWS = 8;
    private static final int MAX_COLUMNS = 8;

    /**
     * Directions for token movements
     */
    private final int MOVE_BACKWARDS = -1;
    private final int MOVE_FORWARDS = 1;

    public Board() {
        setUpInitalBoard();
    }

    /**
     * Constructor for Board that contains the state of the game after given move
     *
     * @param oldBoard (required) helps create an updated Board object
     * @param row      (required) used to invoke move method
     * @param column   (required) used to invoke move method
     */
    Board(final Board oldBoard, final int row, final int column) {
        setUpInitalBoard();
        Board updatedBoard = oldBoard.move(row, column);
        copyGameStatus(updatedBoard);
    }

    private void copyGameStatus(Board boardToCopy) {
        for (int row = 0; row < MAX_ROWS; row++) {
            System.arraycopy(boardToCopy.gameBoard[row], 0, gameBoard[row], 0, MAX_COLUMNS);
        }
        turnCounter = boardToCopy.turnCounter;
    }

    /**
     * Helper method for creating contents of the board
     */
    private void setUpInitalBoard() {
        gameBoard = new String[MAX_ROWS][MAX_COLUMNS];
        for (String[] row : gameBoard) {
            for (int i = 0; i < row.length; i++) {
                row[i] = EMPTY_POSITION_TOKEN;
            }
        }

        // Set up the middle of the gameboard
        gameBoard[3][3] = PLAYER_2_TOKEN;
        gameBoard[3][4] = PLAYER_1_TOKEN;
        gameBoard[4][3] = PLAYER_1_TOKEN;
        gameBoard[4][4] = PLAYER_2_TOKEN;
    }

    /**
     * Displays an 8 eight-line string representing the board.
     *
     * @return This returns a presentation of the board
     * @Override Creates the gameboard representation
     */
    public String toString() {
        StringBuilder boardDisplay = new StringBuilder();
        for (String[] row : gameBoard) {
            for (String col : row) {
                boardDisplay.append(col);
            }
            boardDisplay.append("\n");
        }

        // Removing last new line
        boardDisplay.deleteCharAt(boardDisplay.length() - 1);

        return boardDisplay.toString();
    }

    public boolean empty(int row, int column) {
        return !isInvalidPosition(row, column) && gameBoard[row - 1][column - 1].equals(EMPTY_POSITION_TOKEN);
    }

    public boolean token(int row, int column) throws IndexOutOfBoundsException {
        checkPosition(row, column);
        return !empty(row, column) && PLAYER_1_TOKEN.equals(gameBoard[row - 1][column - 1]);
    }

    // row and column should not be adjusted before calling this
    private boolean isInvalidPosition(int row, int column) {
        return row <= 0 || column <= 0 || row - 1 >= MAX_ROWS || column - 1 >= MAX_COLUMNS;
    }

    private void checkPosition(int row, int column) throws IndexOutOfBoundsException {
        if (isInvalidPosition(row, column)) {
            throw new IndexOutOfBoundsException();
        }
    }

    public boolean gameOver() {
        return moves().length == 0;
    }

    public boolean toPlay() {
        return turnCounter % 2 == 0;
    }

    boolean hasCurrentPlayerToken(int row, int column) {
        if (isInvalidPosition(row, column) || empty(row, column)) {
            return false;
        }

        return toPlay() ? gameBoard[row - 1][column - 1].equals(PLAYER_1_TOKEN)
                : gameBoard[row - 1][column - 1].equals(PLAYER_2_TOKEN);
    }

    boolean hasCurrentOpponentToken(final int row, final int column) {
        if (isInvalidPosition(row, column) || empty(row, column)) {
            return false;
        }

        return !toPlay() ? gameBoard[row - 1][column - 1].equals(PLAYER_1_TOKEN)
                : gameBoard[row - 1][column - 1].equals(PLAYER_2_TOKEN);
    }

    private boolean isInvalidChangeValues(int rowChange, int columnChange) {
        return rowChange > MOVE_FORWARDS || rowChange < MOVE_BACKWARDS || columnChange > MOVE_FORWARDS
                || columnChange < MOVE_BACKWARDS || (rowChange == 0 && columnChange == 0);
    }

    // Do not pre-adjust the row and column when calling this function.
    int countSwappableFrom(int row, int column, int rowChange, int columnChange) {
        if (isInvalidPosition(row, column) || !empty(row, column) || isInvalidChangeValues(rowChange, columnChange)) {
            return 0;
        }

        int opponentCounter = 0;
        // Counting opponent tokens: Move in the direction of the row and column change
        // until encountering current player or end of the board
        while (hasCurrentOpponentToken(row + rowChange, column + columnChange)) {
            opponentCounter++;

            if (rowChange <= MOVE_BACKWARDS) {
                rowChange--;
            } else if (rowChange >= MOVE_FORWARDS) {
                rowChange++;
            }

            if (columnChange <= MOVE_BACKWARDS) {
                columnChange--;
            } else if (columnChange >= MOVE_FORWARDS) {
                columnChange++;
            }
        }

        if (!hasCurrentPlayerToken(row + rowChange, column + columnChange)) {
            return 0;
        }

        return opponentCounter;
    }

    public boolean isMove(final int row, final int column) {
        return swaps(row, column) >= MIN_SWAPS_REQUIRED;
    }

    public int[][] moves() {
        int numMoves = 0;
        for (int row = 1; row <= MAX_ROWS; row++) {
            for (int column = 1; column <= MAX_COLUMNS; column++) {
                if (isMove(row, column)) {
                    numMoves++;
                }
            }
        }

        if (numMoves == 0) {
            return new int[0][0];
        }
        int[][] moves = new int[numMoves][2];
        int moveIndex = 0;
        for (int row = 1; row <= MAX_ROWS; row++) {
            for (int column = 1; column <= MAX_COLUMNS; column++) {
                if (isMove(row, column)) {
                    moves[moveIndex][0] = row;
                    moves[moveIndex][1] = column;
                    moveIndex++;
                }
            }
        }
        return moves;
    }

    public int swaps(int row, int column) {
        if (isInvalidPosition(row, column)) {
            return 0;
        }

        int swapCounter = 0;
        for (int rowDirection = MOVE_BACKWARDS; rowDirection <= MOVE_FORWARDS; rowDirection++) {
            for (int columnDirection = MOVE_BACKWARDS; columnDirection <= MOVE_FORWARDS; columnDirection++) {
                swapCounter += countSwappableFrom(row, column, rowDirection, columnDirection);
            }
        }
        return swapCounter;
    }

    public Board move(int row, int column) throws IndexOutOfBoundsException, IllegalArgumentException {
        checkPosition(row, column);
        if (!isMove(row, column)) {
            throw new IllegalArgumentException();
        }

        String currentPlayer = toPlay() ? PLAYER_1_TOKEN : PLAYER_2_TOKEN;

        for (int rowDirection = MOVE_BACKWARDS; rowDirection <= MOVE_FORWARDS; rowDirection++) {
            for (int columnDirection = MOVE_BACKWARDS; columnDirection <= MOVE_FORWARDS; columnDirection++) {
                int swapCount = countSwappableFrom(row, column, rowDirection, columnDirection);

                int tempRowDirection = rowDirection;
                int tempColumnDirection = columnDirection;
                for (int swap = 0; swap < swapCount; swap++) {
                    gameBoard[row - 1 + tempRowDirection][column - 1 + tempColumnDirection] = currentPlayer;

                    if (tempRowDirection <= MOVE_BACKWARDS) {
                        tempRowDirection--;
                    } else if (tempRowDirection >= MOVE_FORWARDS) {
                        tempRowDirection++;
                    }

                    if (tempColumnDirection <= MOVE_BACKWARDS) {
                        tempColumnDirection--;
                    } else if (tempColumnDirection >= MOVE_FORWARDS) {
                        tempColumnDirection++;
                    }
                }
            }
        }
        gameBoard[row - 1][column - 1] = currentPlayer;
        turnCounter++;

        if (moves().length == 0) {
            turnCounter++;
        }
        return this;
    }

    public int score1() {
        return score(PLAYER_1_TOKEN);
    }

    public int score2() {
        return score(PLAYER_2_TOKEN);
    }

    /**
     * Calculates the number of positions occupied by the given player token.
     *
     * @param playerToken The player token to count.
     * @return Total occupied positions by given player
     */
    private int score(String playerToken) {
        int counter = 0;

        for (String[] row : gameBoard) {
            for (String col : row) {
                if (playerToken.equals(col)) {
                    counter++;
                }
            }
        }

        return counter;
    }
}
