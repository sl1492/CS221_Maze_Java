/**
* This program takes a .txt file representing a maze as input and prints the maze to the screen. The maze's solution is printed at run-time if the user
* specifies --solve as their 2nd command-line argument.
* @author Paean Luby
* @author Simeng Li
*/
import java.io.File;
import java.io.FileNotFoundException;
import java.util.InputMismatchException;
import java.util.List;
import java.util.Scanner;
import java.util.ArrayList;
import java.util.NoSuchElementException;

/**
* Maze represents a maze that can be navigated. The maze
* should indicate its start and end squares, and where the
* walls are.
*/
public class Maze {
    // Number of rows in the maze.
    private int numRows;

    // Number of columns in the maze.
    private int numColumns;

    // Grid coordinates for the starting maze square
    private int startRow;
    private int startColumn;

    // Grid coordinates for the final maze square
    private int finishRow;
    private int finishColumn;

    // Storing the maze squares in a list
    private List<MazeSquare> mazeSquares;

    // Make every maze square in this maze to be unvisited
    private void unmarkSquares() {
      for (MazeSquare s : mazeSquares) {
        s.unmarkVisited();
      }
    }

    /**
     * Creates an empty maze with no squares.
     */
    public Maze() {
        numRows = 0;
        numColumns = 0;
        startRow = 0;
        startColumn = 0;
        finishRow = 0;
        finishColumn = 0;
        mazeSquares = new ArrayList<>();
    }

    /**
     * @return a neighbor of this maze square that has not been visited yet
     * @return null if all neighbors have been visited
     */
    private MazeSquare getUnvisitedNeighbor(MazeSquare square) {
       int row = square.getRow();
       int col = square.getColumn();

       // Check "UP"
       if (row > 0 && !square.hasTopWall()) { // to make sure row-1 is not negative
           MazeSquare up = getMazeSquare(row-1, col);
           if (!up.getVisited()) {
               return up;
           }
       }

       // Check "DOWN"
       if (row + 1 < numRows) { // to make sure row+1 is not out of bounds
           MazeSquare down = getMazeSquare(row+1, col);
           if (!down.hasTopWall() && !down.getVisited()) {
               return down;
           }
       }

       if (col > 0) { // to make sure col-1 is not negative
           MazeSquare left = getMazeSquare(row, col-1);
           if (!left.getVisited() && !left.hasRightWall()) {
               return left;
           }
       }

       if (col < numColumns && !square.hasRightWall()) { // to make sure col-1 is not negative
           MazeSquare right = getMazeSquare(row, col+1);
           if (!right.getVisited()) {
               return right;
           }
       }
       return null;
    }

    /**
      * Computes and returns a solution to this maze. If there are multiple
      * solutions, only one is returned, and getSolution() makes no guarantees about
      * which one. However, the returned solution will not include visits to dead
      * ends or any backtracks, even if backtracking occurs during the solution
      * process.
      *
      * @return a stack of MazeSquare objects containing the sequence of squares
      * visited to go from the start square (bottom of the stack) to the finish
      * square (top of the stack)
      * @return empty stack if there is no solution
      */
    public MysteryStackImplementation<MazeSquare> getSolution() {
      unmarkSquares();
      MysteryStackImplementation<MazeSquare> storer = new MysteryStackImplementation<>();
      storer.push(getMazeSquare(startRow, startColumn));
      getMazeSquare(startRow, startColumn).markVisited();

      while (true) {
        if (storer.isEmpty()) {
          break;
        } else if (storer.peek().equals(getMazeSquare(finishRow, finishColumn))) {
          break;
        } else if (getUnvisitedNeighbor(storer.peek()) == null) {
            storer.pop();
        } else {
          MazeSquare accessible = getUnvisitedNeighbor(storer.peek());
          accessible.markVisited();
          storer.push(accessible);
        }
      }
      return storer;
    }

    /**
     * Loads the maze that is written in the given fileName.
     * @return false if error thrown while loading the file
     * @return true if file successfully loaded
     */
    public boolean load(String fileName) {
        Scanner scanner = null;
        try {
            // Open a scanner to read the file
            scanner = new Scanner(new File(fileName));
            numColumns = scanner.nextInt();
            numRows = scanner.nextInt();
            startColumn = scanner.nextInt();
            startRow = scanner.nextInt();
            finishColumn = scanner.nextInt();
            finishRow = scanner.nextInt();
            scanner.nextLine();

            if (!isInRange(startRow, 0, numRows)
                    || !isInRange(startColumn, 0, numColumns)
                    || !isInRange(finishRow, 0, numRows)
                    || !isInRange(finishColumn, 0, numColumns)) {
                System.err.println("Start or finish square is not in maze.");
                scanner.close();
                return false;
            }

          for (int i = 0; i < numRows; i++) {
            String line = scanner.nextLine();
            for (int k = 0; k < numColumns; k++) {

              char col = line.charAt(k);

              MazeSquare square = new MazeSquare(col, i, k);
              if (square.isAllowedCharacter(col)) {
                mazeSquares.add(square);
              } else {
                  return false;
                }
            }
          }

        } catch(FileNotFoundException e) {
            System.err.println("The requested file, " + fileName + ", was not found.");
            return false;
        } catch(InputMismatchException e) {
            System.err.println("Maze file not formatted correctly.");
            scanner.close();
            return false;
        }
          catch(NoSuchElementException e) {
            System.err.println("Maze squares inconsistent with the number of rows and columns. Lack maze squares.");
            scanner.close();
            return false;
        }
          catch(IndexOutOfBoundsException e) {
            System.err.println("Maze file not formatted correctly. Too many characters for maze dimensions.");
            scanner.close();
            return false;
        }
          return true;
        }

    /**
     * Returns true if number is greater than or equal to lower bound
     * and less than upper bound.
     * @param number
     * @param lowerBound
     * @param upperBound
     * @return true if lowerBound ≤ number < upperBound
     */
    private static boolean isInRange(int number, int lowerBound, int upperBound) {
        return number < upperBound && number >= lowerBound;
    }

     /**
      * Prints the maze with the start and finish squares marked. Does
      * not include a solution. If --solve is provided as the second command-line argument,
      * the maze will be printed with its solution marked with asterisks. Otherwise,
      * the maze will be printed with no solution.
      */
     public void print(boolean solution, MysteryStackImplementation<MazeSquare> storer) {

         List<MazeSquare> stackList = new ArrayList<>();

         if (storer != null) {
           while (!storer.isEmpty()) {
             MazeSquare e = storer.pop();
             stackList.add(e);
            }
         }

         // We'll print off each row of squares in turn.
         for (int row = 0; row < numRows; row++) {

             // Print each of the lines of text in the row
             for (int charInRow = 0; charInRow < 4; charInRow++) {
                 // Need to start with the initial left wall.
                 if (charInRow == 0) {
                     System.out.print("+");
                 } else {
                     System.out.print("|");
                 }

                 for (int col = 0; col < numColumns; col++) {
                     MazeSquare curSquare = this.getMazeSquare(row, col);
                     if (charInRow == 0) {
                         // We're in the first row of characters for this square - need to print
                         // top wall if necessary.
                         if (curSquare.hasTopWall()) {
                             System.out.print(getTopWallString());
                         } else {
                             System.out.print(getTopOpenString());
                         }
                     } else if (charInRow == 1 || charInRow == 3) {
                         // These are the interior of the square and are unaffected by
                         // the start/final state.
                         if (curSquare.hasRightWall()) {
                             System.out.print(getRightWallString());
                         } else {
                             System.out.print(getOpenWallString());
                         }
                     } else {
                         // We must be in the second row of characters.
                         // This is the row where start/finish should be displayed if relevant

                         // Check if we're in the start or finish state
                         if (startRow == row && startColumn == col) {
                             System.out.print("  S  ");
                         } else if (finishRow == row && finishColumn == col) {
                             System.out.print("  F  ");

                         } else if (storer != null && stackList.contains(curSquare)) {
                                   System.out.print("  *  ");
                         } else {
                             System.out.print("     ");
                         }

                         if (curSquare.hasRightWall()) {
                             System.out.print("|");
                         } else {
                             System.out.print(" ");
                         }
                     }
                 }
                 // Now end the line to start the next
                 System.out.print("\n");
                 }
             }

         // Finally, we have to print off the bottom of the maze, since that's not explicitly represented
         // by the squares. Printing off the bottom separately means we can think of each row as
         // consisting of four lines of text.
         printFullHorizontalRow(numColumns);

         if (storer != null) {
           while (stackList.size() > 0) {
             storer.push(stackList.remove(stackList.size() - 1));
           }
         }
     }

    /**
     * Prints the very bottom row of characters for the bottom row of maze squares (which is always walls).
     * numColumns is the number of columns of bottom wall to print.
     */
    private static void printFullHorizontalRow(int numColumns) {
        System.out.print("+");
        for (int row = 0; row < numColumns; row++) {
            // We use getTopWallString() since bottom and top walls are the same.
            System.out.print(getTopWallString());
        }
        System.out.print("\n");
    }

    /**
     * Returns a String representing the bottom of a horizontal wall.
     */
    private static String getTopWallString() {
        return "-----+";
    }

    /**
     * Returns a String representing the bottom of a square without a
     * horizontal wall
     */
    private static String getTopOpenString() {
        return "     +";
    }

    /**
     * Returns a String representing a left wall (for the interior of the row).
     */
    private static String getRightWallString() {
        return "     |";
    }

    /**
     * Returns a String representing no left wall (for the interior of the row).
     */
    private static String getOpenWallString() {
        return "      ";
    }

    /**
     * @return the MazeSquare at the given row and column
     */
    public MazeSquare getMazeSquare(int row, int col) {
       // System.out.println("hello"+numRows*row+col);
       return mazeSquares.get(numColumns*row+col);
    }

    /**
     * You should modify main so that if there is only one
     * command line argument, it loads the maze and prints it
     * with no solution. If there are two command line arguments
     * and the second one is --solve,
     * it should load the maze, solve it, and print the maze
     * with the solution marked. No other command lines are valid.
     */
    public static void main(String[] args) {
      Maze maze = new Maze();

      if (args.length == 0) {
        System.out.println("Please input a valid file name.");
      } else {
          if (maze.load(args[0]) == false) {
              System.err.print("");
          } else {
              if (args.length == 2) {
                if (args[1].equals("--solve")) {
                  maze.print(true, maze.getSolution());
                } else {
                    System.out.println("Invalid second argument. Please type --solve or nothing.");
                  }
              } else if (args.length == 1) {
                  maze.print(false, null);
                } else {
                  System.out.println("Too many command-line arguments.");
                }
            }
        }
    }
}
