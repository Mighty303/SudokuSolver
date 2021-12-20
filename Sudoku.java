package Sudoku;
import java.util.ArrayList;
import java.util.List;

public class Sudoku {
    static int backtrackCounter = 0;
    public static void main(String[] args) {
        //Note that this algorithm works with all possible solutions as long as you increase the stack size

        //EMPTY BOARD
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
        coordinates.add(0);
        coordinates.add(0);

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
        if (I == 0 && J == 0 && coord.size() == 0) {
            System.out.println("NO SOLUTION");
            return board;
        }

        if (I == board.length - 1 && J == board.length)
            J--;

        if (I == board.length - 1 && (J == board.length - 1 && board[I][J] != 0)){
            System.out.println("SOLUTION HAS BEEN REACHED");
            System.out.printf("Backtracks: %d\n",backtrackCounter);
            return board;
        }

        if (J == board.length){
            J=0;
            I++;
        }
        
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
        for (int i = board[I][J] + 1; i <= 9; i++){
            if (isCol(board, J, i) && isRow(board, I, i) && isSquare(board, I, J, i)) {
                coord.add(I);
                coord.add(J);
                board[I][J] = i;
                return solve(board, I, J+1, coord);
            }
        }
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
        for (int i = 0; i < board.length; i++){
            if (board[i][col] == num)
                return false;
        }
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
        for (int i = 0; i < board[row].length; i++){
            if (board[row][i] == num)
                return false;
        }
        return true;
    }

    /**
     * Checks if each 3x3 grid is valid
     * @param board 9x9 grid
     * @param I Rows
     * @param J Columns
     * @param num The number we are comparing
     * @return true if the grid is valid, else return false
     */
    public static boolean isSquare(int[][] board, int I, int J, int num) {
        if (I <= 2 && J <= 2)
            return validateSquare(board, 0, 0, num);
        
        else if (I <= 2 && J >= 3 && J <= 5)
            return validateSquare(board, 0, 3, num);
        
        else if (I <= 2 && J >= 6)
            return validateSquare(board, 0, 6, num);
        
        else if (I >= 3 && I <= 5 && J <= 2)
            return validateSquare(board, 3, 0, num);

        else if (I >= 3 && I <= 5 && J >= 3 && J <= 5)
            return validateSquare(board, 3, 3, num);
        
        else if (I >= 3 && I <= 5 && J >= 6)
            return validateSquare(board, 3, 6, num);
        
        else if (I >= 6 && J <= 2)
            return validateSquare(board, 6, 0, num);
        
        else if (I >= 6 && J >= 3 && J <= 5)
            return validateSquare(board, 6, 3, num);
        
        else if (I >= 6 && J >= 6)
            return validateSquare(board, 6, 6, num);

        return false;
    }

    /**
     * Helper method to validate each square
     * @param board 9x9 grid
     * @param row Rows
     * @param col Columns
     * @param num The number we are comparing
     * @return true if the square is valid, else return false
     */
    public static boolean validateSquare(int[][] board, int row, int col, int num){

        for (int i = row; i < row + (board.length / 3); i++){
            for (int j = col; j < col + (board.length / 3); j++){
                if (board[i][j] == num)
                    return false;
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
