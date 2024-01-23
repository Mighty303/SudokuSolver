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
     * @param I Rows
     * @param J Columns
     * @param coord Coordinates of sucessful numbers to backtrack to
     * @return Solution
     */
    public static int[][] solve(int[][] board, int I, int J, List<Integer> coord) {
        //Prevents going out of bounds
        if (I == board.length - 1 && J == board.length)
            J--;
        //Check if there is a solution
        if (I == board.length - 1 && (J == board.length - 1 && board[I][J] != 0)){
            System.out.println("SOLUTION HAS BEEN REACHED");
            System.out.printf("Backtracks: %d\n",backtrackCounter);
            return board;
        }

        //Move to next row
        if (J == board.length){
            J = 0;
            I++;
        }
        
        //We are on the last row
        if (I == board.length)
            J++;
        
        else if (board[I][J] == 0)
            return recurseUtil(board, I, J, coord);
        return solve(board, I, J+1, coord);
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
            if (isCol(board, J, i) && isRow(board, I, i) && isSquare(board, I, J, i)) {
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
     * Checks if the column is valid
     * @param board 9x9 grid
     * @param col Column to check
     * @param num The number we are comparing
     * @return true if the column is valid, else return false
     */
    public static boolean isCol(int[][] board, int col, int num){
        for (int i = 0; i < board.length; i++)
            if (board[i][col] == num)
                return false;
        return true;
    }

    /**
     * Checks if the row is valid
     * @param board 9x9 grid
     * @param row Row to check
     * @param num The number we are comparing
     * @return true if the row is valid, else return false
     */
    public static boolean isRow(int[][] board, int row, int num){
        for (int i = 0; i < board[row].length; i++)
            if (board[row][i] == num)
                return false;
        return true;
    }

    /**
     * Checks if the 3x3 grid containing the cell at (I, J) is valid for the number num.
     * @param board 9x9 Sudoku board
     * @param I Row index of the cell
     * @param J Column index of the cell
     * @param num The number to be checked
     * @return true if the grid is valid for num, false otherwise
     */
    public static boolean isSquare(int[][] board, int I, int J, int num) {
        // Calculate the top-left corner of the 3x3 grid
        int startRow = I - I % 3;
        int startCol = J - J % 3;

        // Check the 3x3 grid for the number
        for (int row = startRow; row < startRow + 3; row++)
            for (int col = startCol; col < startCol + 3; col++) 
                if (board[row][col] == num)
                    return false;
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
