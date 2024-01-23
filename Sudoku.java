package Sudoku;
import java.util.ArrayList;
import java.util.List;

public class Sudoku {
    static int backtrackCounter = 0;
    public static void main(String[] args) {
        /**Note that this algorithm works with all possible solutions as long as you increase the stack size
         Command to increase stack size to 1000mb: java -Xss1000m Sudoku.java
         The board below is a working board that doesn't require to increase the stack size
        */
        int[][] board = {
            {0,4,0, 8,0,5, 2,0,0},
            {0,2,0, 0,4,0, 0,5,0},
            {5,0,0, 0,0,0, 0,0,4},

            {0,9,0, 0,0,3, 1,2,0},
            {1,0,6, 0,7,8, 0,0,3},
            {3,7,0, 9,0,4, 0,8,0},

            {0,0,0, 0,0,6, 7,0,0},
            {0,0,8, 3,5,9, 0,1,0},
            {0,1,9, 0,0,7, 6,0,0}
        };
    
        List<Integer> coordinates = new ArrayList<Integer>();
        if (isBoardRow(board) && isBoardColumn(board))
            printBoard(solve(board, 0, 0, coordinates));
    }

    /**
     * Engine of the algorithm
     * @param board 9x9 grid
     * @param row Row index
     * @param col Column index
     * @param coord Coordinates of successful numbers to backtrack to
     * @return Solution
     */
    public static int[][] solve(int[][] board, int row, int col, List<Integer> coord) {
        // Prevents going out of bounds
        if (row == board.length - 1 && col == board.length)
            col--;

        // Check if there is a solution
        if (row == board.length - 1 && (col == board.length - 1 && board[row][col] != 0)) {
            System.out.println("SOLUTION HAS BEEN REACHED");
            System.out.printf("Backtracks: %d\n", backtrackCounter);
            return board;
        }

        // Move to next row
        if (col == board.length) {
            col = 0;
            row++;
        }
        
        // We are on the last row
        if (row == board.length)
            col++;
        
        else if (board[row][col] == 0)
            return recurseUtil(board, row, col, coord);
        return solve(board, row, col + 1, coord);
    }


    /**
     * Recursive utility method for solving the algorithm
     * @param board 9x9 grid
     * @param I Rows
     * @param J Columns
     * @param coord Coordinates of sucessful numbers to backtrack to
     * @return
     */
    public static int[][] recurseUtil(int[][] board, int I, int J, List<Integer> coord){
        //Try numbers from current position to length of board
        for (int i = board[I][J] + 1; i <= board.length; i++){
            if (isValidPlacement(board, I, J, i)) {
                coord.add(I);
                coord.add(J);
                board[I][J] = i;
                return solve(board, I, J+1, coord); 
            }
        }
        //Remove the coordinates and backtrack
        backtrackCounter++;
        board[I][J] = 0;

        I = coord.get(coord.size() - 2);
        J = coord.get(coord.size() - 1);

        coord.remove(coord.size() - 1);
        coord.remove(coord.size() - 1);

        return recurseUtil(board, I, J, coord);
    }

    /**
     * Checks if placing a number in a given cell is valid according to Sudoku rules.
     * @param board 9x9 Sudoku board
     * @param row Row index of the cell
     * @param col Column index of the cell
     * @param num The number to be checked
     * @return true if placing num in (row, col) is valid, false otherwise
     */
    public static boolean isValidPlacement(int[][] board, int row, int col, int num) {
        // Check the row
        for (int i = 0; i < board[row].length; i++) {
            if (board[row][i] == num) {
                return false;
            }
        }

        // Check the column
        for (int i = 0; i < board.length; i++) {
            if (board[i][col] == num) {
                return false;
            }
        }

        // Check the 3x3 grid
        int startRow = row - row % 3;
        int startCol = col - col % 3;
        for (int i = startRow; i < startRow + 3; i++) {
            for (int j = startCol; j < startCol + 3; j++) {
                if (board[i][j] == num) {
                    return false;
                }
            }
        }

        return true;
    }

    /**
     * Checks if the rows of the board are valid
     * @param board 9x9 grid
     * @return true if the board is valid, else return false
     */
    public static boolean isBoardRow(int[][] board) {
        for (int i = 0; i < board.length; i++) {
            int[] counter = new int[board.length];
            for (int j = 0; j < board[i].length; j++) {
                if (board[i][j] == 0) 
                    continue;

                counter[board[i][j]-1]++;

                if (counter[board[i][j]-1] > 1){
                    printBoard(board);
                    System.out.println("INVALID BOARD");
                    return false;
                }
            }
        }
        return true;
    }

    /**
     * Checks if the columns of the board are valid
     * @param board 9x9 grid
     * @return true if the columns are valid, else return false
     */
    public static boolean isBoardColumn(int[][] board) {
        for (int i = 0; i < board[i].length-1; i++) {
            int[] counter = new int[board.length];
            for (int j = 0; j < board.length; j++) {
                if (board[j][i] == 0) 
                    continue;
                counter[board[j][i]-1]++;
                if (counter[board[j][i]-1] > 1){
                    printBoard(board);
                    System.out.println("INVALID BOARD");
                    return false;
                }
            }
        }
        return true;
    }

    /**
     * Prints the board with the lines in between them
     * @param board 9x9 grid
     */
    public static void printBoard(int[][] board) {
        int x = 0; int y = 0;
        for (int i = 0; i < board.length; i++){
            x++;
            for (int j = 0; j < board[i].length; j++){
                y++;
                System.out.print(board[i][j] + " ");
                if (y % 3 == 0 && y != 9) System.out.print("| ");
            }
            y = 0;
            System.out.println();
            if (x % 3 == 0 && x != 9) 
                System.out.println("- - - - - - - - - - - ");
        }
        System.out.println("_____________________");
    }
}
