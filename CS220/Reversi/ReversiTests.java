import org.junit.Test;

import java.lang.reflect.Modifier;

import static org.junit.Assert.*;

/**
 * Tests for {@link Board} Assignment 1
 */
public class ReversiTests {

    /**
     * Instantiate a new {@link Board}.
     */
    Board buildInitial() {
        return new Board();
    }

    /**
     * Verify that {@link Board} implements {@link BoardMethods}.
     */
    @Test(timeout = 1000)
    public void testSuperclass() {
        BoardMethods.class.isAssignableFrom(Board.class);
    }

    /**
     * The constructor should return an instance (and not throw
     * an exception).
     */
    @Test(timeout = 1000)
    public void testConstructor() {
        buildInitial();
    }

    /**
     * Correct value of toPlay for the initial board.
     */
    @Test(timeout = 1000)
    public void testToPlay() {
        assertTrue("It is Player 1's turn", buildInitial().toPlay());
    }

    /**
     * The display of the initial board.
     */
    @Test(timeout = 1000)
    public void testToStringInitial() {
        assertEquals("........\n........\n........\n...ox...\n...xo...\n........\n........\n........",
                buildInitial().toString());
    }

    /**
     * Correct spaces are empty in the initial board.
     */
    @Test(timeout = 1000)
    public void testInitialEmpty() {
        final Board initial = buildInitial();
        for (int row = 1; row < 9; ++row) {
            for (int column = 1; column < 9; ++column) {
                if ((row == 4 || row == 5) && (column == 4 || column == 5)) {
                    assertFalse(("Row " + row + ", column " + column
                            + " not initially empty"),
                            initial.empty(row, column));
                } else {
                    assertTrue(("Row " + row + ", column " + column
                            + " initially empty"),
                            initial.empty(row, column));
                }
            }
        }
    }

    /**
     * Correct pieces on initial board.
     */
    @Test(timeout = 1000)
    public void testInitialToken() {
        final Board initial = buildInitial();
        for (int row = 4; row < 6; ++row) {
            for (int column = 4; column < 6; ++column) {
                if (row == column) {
                    assertFalse("Initial position of Player 2 piece",
                            initial.token(row, column));
                } else {
                    assertTrue("Initial position of Player 1 piece",
                            initial.token(row, column));
                }
            }
        }
    }

    void methodPackageProtected(final Class<?> theClass, final String methodName,
            final Class<?>... argsClasses) {
        try {
            final int mods = theClass.getDeclaredMethod(methodName, argsClasses).getModifiers();
            assertFalse(methodName + " not declared with the public keyword",
                    Modifier.isPublic(mods));
            assertFalse(methodName + " not declared with the protected keyword",
                    Modifier.isProtected(mods));
            assertFalse(methodName + " not declared with the private keyword",
                    Modifier.isPrivate(mods));
        } catch (NoSuchMethodException e) {
            fail(methodName + " not defined");
        }
    }

    /**
     * Ensure the hasCurrentPlayerToken method has package-protected mode
     */
    @Test(timeout = 1000)
    public void hasCurrentPlayerTokenMode() {
        methodPackageProtected(Board.class, "hasCurrentPlayerToken",
                Integer.TYPE, Integer.TYPE);
    }

    /**
     * Test the hasCurrentPlayerToken method on the initial board
     */
    @Test(timeout = 1000)
    public void initialHasCurrentPlayerToken() {
        final Board initial = buildInitial();
        for (int row = -1; row <= 10; ++row) {
            for (int column = -1; column <= 10; ++column) {
                if ((row == 4 && column == 5) || (row == 5 && column == 4)) {
                    assertTrue("Current player token initially at " + row + "," + column,
                            initial.hasCurrentPlayerToken(row, column));
                } else {
                    assertFalse("No current player token initially at "
                            + row + "," + column,
                            initial.hasCurrentPlayerToken(row, column));
                }
            }
        }
    }

    /**
     * Ensure the hasCurrentOpponentToken method has package-protected mode
     */
    @Test(timeout = 1000)
    public void hasCurrentOpponentTokenMode() {
        methodPackageProtected(Board.class, "hasCurrentOpponentToken",
                Integer.TYPE, Integer.TYPE);
    }

    /**
     * Test the hasCurrentOpponentToken method on the initial board
     */
    @Test(timeout = 1000)
    public void initialHasCurrentOpponentToken() {
        final Board initial = buildInitial();
        for (int row = -1; row <= 10; ++row) {
            for (int column = -1; column <= 10; ++column) {
                if ((row == 4 || row == 5) && (row == column)) {
                    assertTrue("Opponent token initially at " + row + "," + column,
                            initial.hasCurrentOpponentToken(row, column));
                } else {
                    assertFalse("No opponent token initially at " + row + "," + column,
                            initial.hasCurrentOpponentToken(row, column));
                }
            }
        }
    }

    /**
     * Test countSwappableFrom from a number of points
     */
    @Test(timeout = 1000)
    public void countSwappableFromMode() {
        methodPackageProtected(Board.class, "countSwappableFrom", Integer.TYPE,
                Integer.TYPE, Integer.TYPE, Integer.TYPE);
    }

    /**
     * Test countSwappableFrom from a number of points
     */
    @Test(timeout = 1000)
    public void initialCountSwappableFrom() {
        final Board initial = buildInitial();

        for (int row = 0; row < 10; row++) {
            for (int column = 0; column < 10; column++) {
                for (int rowChange = -1; rowChange < 2; rowChange++) {
                    for (int columnChange = -1; columnChange < 2; columnChange++) {
                        if ((row == 4 && column == 3 && rowChange == 0 && columnChange == 1)
                                || (row == 3 && column == 4 && rowChange == 1 && columnChange == 0)
                                || (row == 5 && column == 6 && rowChange == 0 && columnChange == -1)
                                || (row == 6 && column == 5 && rowChange == -1 && columnChange == 0)) {
                            assertEquals(("At direction " + row + "," + column + " from position "
                                    + rowChange + "," + columnChange
                                    + " on the initial board, no swaps"),
                                    1, initial.countSwappableFrom(row, column,
                                            rowChange, columnChange));
                        } else if (rowChange != 0 || columnChange != 0) {

                            assertEquals(("At direction " + row + "," + column + " from position "
                                    + rowChange + "," + columnChange
                                    + " on the initial board, no swaps"),
                                    0, initial.countSwappableFrom(row, column,
                                            rowChange, columnChange));
                        }
                    }
                }
            }
        }
    }

    /**
     * Test the swaps method
     */
    @Test(timeout = 1000)
    public void testSwapsInitial() {
        final Board initial = buildInitial();
        for (int row = 0; row < 10; row++) {
            for (int column = 0; column < 10; column++) {
                if ((row == 4 && column == 3) || (row == 3 && column == 4)
                        || (row == 5 && column == 6) || (row == 6 && column == 5)) {
                    assertEquals("One flip on initial board at " + row + "," + column,
                            1, initial.swaps(row, column));
                } else {
                    assertEquals("No swaps on initial board at " + row + "," + column,
                            0, initial.swaps(row, column));
                }
            }
        }
    }

    /**
     * Test whether each space is a move for the initial current player.
     */
    @Test(timeout = 1000)
    public void testIsMoveInitial() {
        final Board initial = buildInitial();
        for (int row = 0; row < 10; row++) {
            for (int column = 0; column < 10; column++) {
                if ((row == 4 && column == 3) || (row == 3 && column == 4)
                        || (row == 5 && column == 6) || (row == 6 && column == 5)) {
                    assertTrue("Possible move at " + row + "," + column,
                            initial.isMove(row, column));
                } else {
                    assertFalse("No move at " + row + "," + column,
                            initial.isMove(row, column));
                }
            }
        }
    }

    private boolean containsCoordinate(final int[][] coordinates,
            final int row, final int column) {
        for (int i = 0; i < coordinates.length; ++i) {
            if (coordinates[i][0] == (row) && coordinates[i][1] == (column)) {
                return true;
            }
        }
        return false;
    }

    /**
     * Test the returned arrays of possible move coordinates.
     */
    @Test(timeout = 1000)
    public void testMovesInitial() {
        final int[][] moves = buildInitial().moves();
        assertTrue("4,3 is initially a move", containsCoordinate(moves, 4, 3));
        assertTrue("3,4 is initially a move", containsCoordinate(moves, 3, 4));
        assertTrue("5,6 is initially a move", containsCoordinate(moves, 5, 6));
        assertTrue("6,5 is initially a move", containsCoordinate(moves, 6, 5));
    }

    /**
     * On the initial board the game is not over
     */
    @Test(timeout = 1000)
    public void testGameOverInitial() {
        assertFalse("On the initial board the game is not over",
                buildInitial().gameOver());
    }

    /**
     * Initially each player has two tokens on the board
     */
    @Test(timeout = 1000)
    public void testScore1initial() {
        assertEquals("On the initial board, Player 1 has a score of 2",
                2, buildInitial().score1());
    }

    /**
     * Initially each player has two tokens on the board
     */
    @Test(timeout = 1000)
    public void testScore2initial() {
        assertEquals("On the initial board, Player 2 has a score of 2",
                2, buildInitial().score2());
    }

    /**
     * Out-of-range board reference
     */
    @Test(expected = IndexOutOfBoundsException.class)
    public void testMoveLeft() {
        buildInitial().move(3, 0);
    }

    /**
     * Out-of-range board reference
     */
    @Test(expected = IndexOutOfBoundsException.class)
    public void testMoveRight() {
        buildInitial().move(3, 9);
    }

    /**
     * Out-of-range board reference
     */
    @Test(expected = IndexOutOfBoundsException.class)
    public void testMoveAbove() {
        buildInitial().move(-1, 5);
    }

    /**
     * Out-of-range board reference
     */
    @Test(expected = IndexOutOfBoundsException.class)
    public void testMoveBelow() {
        buildInitial().move(10, 5);
    }

    /**
     * Not a move
     */
    @Test(timeout = 1000)
    public void testNotInitiallyValidMove() {
        final Board initial = buildInitial();
        for (int row = 1; row < 9; row++) {
            for (int column = 1; column < 9; column++) {
                if (!((row == 4 && column == 3) || (row == 3 && column == 4)
                        || (row == 5 && column == 6) || (row == 6 && column == 5))) {
                    try {
                        initial.move(row, column);
                        fail("Expected move(" + row + "," + column + ")");
                    } catch (IllegalArgumentException e) {
                        // Nothing to do here
                    }
                }
            }
        }
    }

    /**
     * The constructor should derive a new instance.
     */
    @Test(timeout = 1000)
    public void testDerivedConstructor1() {
        final Board initial = buildInitial(),
                nextBoard = new Board(initial, 3, 4);
        assertEquals("Correct board configuration",
                "........\n........\n...x....\n...xx...\n...xo...\n........\n........\n........",
                nextBoard.toString());
    }

    /**
     * The constructor should derive a new instance.
     */
    @Test(timeout = 1000)
    public void testDerivedConstructor2() {
        final Board initial = buildInitial(),
                nextBoard = new Board(initial, 5, 6);
        assertEquals("Correct board configuration",
                "........\n........\n........\n...ox...\n...xxx..\n........\n........\n........",
                nextBoard.toString());
    }

    /**
     * The constructor should derive a new instance.
     */
    @Test(timeout = 1000)
    public void testMoveInitial1() {
        final Board initial = buildInitial(),
                nextBoard = initial.move(3, 4);
        assertEquals("Correct board configuration",
                "........\n........\n...x....\n...xx...\n...xo...\n........\n........\n........",
                nextBoard.toString());
    }

    /**
     * The constructor should derive a new instance.
     */
    @Test(timeout = 1000)
    public void testMoveInitial2() {
        final Board initial = buildInitial(),
                nextBoard = initial.move(5, 6);
        assertEquals("Correct board configuration",
                "........\n........\n........\n...ox...\n...xxx..\n........\n........\n........",
                nextBoard.toString());
    }

    void constructorPackageProtected(final Class<?> theClass,
            final Class<?>... argsClasses) {
        try {
            final int mods = theClass.getDeclaredConstructor(argsClasses).getModifiers();
            assertFalse("Constructor not declared with the public keyword",
                    Modifier.isPublic(mods));
            assertFalse("Constructor not declared with the protected keyword",
                    Modifier.isProtected(mods));
            assertFalse("Constructor not declared with the private keyword",
                    Modifier.isPrivate(mods));
        } catch (NoSuchMethodException e) {
            fail("Constructor not defined");
        } catch (SecurityException e) {
            fail("Constructor inaccessible");
        }
    }

    /**
     * Test countSwappableFrom from a number of points
     */
    @Test(timeout = 1000)
    public void internalConstructorMode() {
        constructorPackageProtected(Board.class,
                Board.class, Integer.TYPE, Integer.TYPE);
    }

    /**
     * The constructor should derive a new instance.
     */
    @Test(timeout = 1000)
    public void testSkipTurn() {
        final Board initial = buildInitial();
        final Board oTurn = initial
                .move(5, 6)
                .move(4, 6)
                .move(3, 4)
                .move(6, 6)
                .move(6, 7)
                .move(4, 3)
                .move(6, 5)
                .move(2, 4)
                .move(3, 7)
                .move(6, 4)
                .move(3, 5)
                .move(2, 6)
                .move(6, 3)
                .move(4, 8)
                .move(2, 8)
                .move(4, 7)
                .move(3, 8)
                .move(1, 8)
                .move(3, 6)
                .move(5, 7)
                .move(5, 8)
                .move(6, 8)
                .move(2, 5)
                .move(7, 8)
                .move(7, 7)
                .move(8, 8)
                .move(8, 7)
                .move(8, 6)
                .move(7, 6)
                .move(8, 5)
                .move(1, 7)
                .move(7, 5)
                .move(7, 4)
                .move(8, 4)
                .move(8, 3)
                .move(8, 2)
                .move(5, 3)
                .move(6, 2)
                .move(6, 1)
                .move(2, 7)
                .move(3, 3)
                .move(7, 3)
                .move(1, 5)
                .move(5, 1)
                .move(7, 1)
                .move(8, 1)
                .move(1, 6);
        final Board oTurn2 = oTurn.move(7, 2);

        assertFalse("o's turn", oTurn.toPlay());
        assertFalse("o's turn again", oTurn2.toPlay());
        assertEquals("Correct board configuration",
                "....xxxo\n...xxxoo\n..xxxxoo\n..xxoxoo\no.xooxoo\noxooxooo\noooooooo\noooooooo",
                oTurn2.toString());
    }

    /**
     * The constructor should derive a new instance.
     */
    @Test(timeout = 1000)
    public void testOneMove() {
        final Board initial = buildInitial();
        final Board move1 = initial.move(4, 3);

        assertFalse("o's turn", move1.toPlay());
        assertEquals("Correct board configuration",
                "........\n........\n........\n..xxx...\n...xo...\n........\n........\n........",
                move1.toString());
        final int[][] moves1 = move1.moves();
        assertEquals("Three possible moves", 3, moves1.length);
        for (int row = 1; row <= 8; row++) {
            for (int column = 1; column <= 8; column++) {
                if ((row == 3 && (column == 3 || column == 5)) || (row == 5 && column == 3)) {
                    assertTrue(row + "," + column + " is one of the three moves",
                            move1.isMove(row, column));
                    assertEquals(row + "," + column + " swaps one token", 1,
                            move1.swaps(row, column));
                } else {
                    assertFalse(row + "," + column + " is not one of the three moves",
                            move1.isMove(row, column));
                    assertEquals(row + "," + column + " swaps no tokens", 0,
                            move1.swaps(row, column));
                }
            }
        }
    }

    /**
     * The constructor should derive a new instance.
     */
    @Test(timeout = 1000)
    public void testTwoMoves() {
        final Board initial = buildInitial();
        final Board move1 = initial.move(4, 3);
        final Board move2 = move1.move(5, 3);

        assertTrue("x's turn", move2.toPlay());
        assertEquals("Correct board configuration",
                "........\n........\n........\n..xxx...\n..ooo...\n........\n........\n........",
                move2.toString());
        final int[][] moves2 = move2.moves();
        assertEquals("Five possible moves", 5, moves2.length);
        for (int row = 1; row <= 8; row++) {
            for (int column = 1; column <= 8; column++) {
                if (row == 6 && 1 < column && column < 7) {
                    assertTrue(row + "," + column + " is one of the five moves",
                            move2.isMove(row, column));
                    assertEquals(row + "," + column + " swaps " + " token(s)",
                            1 + column % 2,
                            move2.swaps(row, column));
                } else {
                    assertFalse(row + "," + column + " is not one of the five moves",
                            move2.isMove(row, column));
                    assertEquals(row + "," + column + " swaps no tokens", 0,
                            move2.swaps(row, column));
                }
            }
        }
    }
}
