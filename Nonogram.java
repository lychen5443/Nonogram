import java.util.Scanner;
import java.util.ArrayList;

public class Nonogram {
  private static Scanner sc = new Scanner(System.in);
  private static Square[][] board5 = new Square[5][5];
  private static Square[][] board10 = new Square[10][10];
  private static Square[][] board15 = new Square[15][15];
  private static String[][] guessBoard5 = getBaseBoard(5);
  private static String[][] guessBoard10 = getBaseBoard(10);
  private static String[][] guessBoard15 = getBaseBoard(15);
  private static int hint5 = 0, hint10 = 0, hint15 = 0;

  private static String rowA, rowB, rowC, rowD, rowE, rowF, rowG, rowH, rowI, rowJ, rowK, rowL, rowM, rowN, rowO, colA, colB, colC, colD, colE, colF, colG, colH, colI, colJ, colK, colL, colM, colN, colO;
  
  public static void main(String[] args) {
    String level;
    //String[] colNums10 = {colA, colB, colC, colD, colE, colF, colG, colH, colI, colJ};
    //String[] colNums15 = {colA, colB, colC, colD, colE, colF, colG, colH, colI, colJ, colK, colL, colM, colN, colO};

    boolean play1 = true, play2 = true;
    String input;

    printWelcomeBanner();

    while (play1) {
      play2 = true;

      input = getInput("Choose a level:\n1 - 5x5 board\n2 - 10x10 board\n3 - 15x15 board\nEnter choice: ", "levelMenu", "Invalid choice!");
      level = input;

      while (play2) {
        switch(level) {
          case "1":
            playBoard5();
            break;
          case "2":
            playBoard10();
            break;
          case "3":
            playBoard15();
        }

        input = getInput("1 - Choose another level\n2 - Play same level\nAnything else - Program ends\n", "noCheck", "");

        if (input.equals("2"))
          play2 = true;
        else
          play2 = false;
        
        print("═════════════");
      }

      if (input.equals("1"))
        play1 = true;
      else
        play1 = false;
    }
    
    sc.close();
    print("Program Done.");
  }

  private static void playBoard5() {
    String input, temp;
    int start, end;
    int totalLives = 3, livesLeft = 3;
    
    fillBoard(board5);
    checkBoardEmpty(board5);
    guessBoard5 = getBaseBoard(5);
    
    rowA = getSideNums(board5[0]);
    rowB = getSideNums(board5[1]);
    rowC = getSideNums(board5[2]);
    rowD = getSideNums(board5[3]);
    rowE = getSideNums(board5[4]);

    colA = getSideNums(colToRow(board5, 0));
    colB = getSideNums(colToRow(board5, 1));
    colC = getSideNums(colToRow(board5, 2));
    colD = getSideNums(colToRow(board5, 3));
    colE = getSideNums(colToRow(board5, 4));

    String[] colNums5 = {colA, colB, colC, colD, colE};
        
    int max = maxSpace(colNums5);
    
    int totalSquares = getTotalFilledSquares(board5);
    int count = 0;
    int r, c;

    while (count != totalSquares && livesLeft >= 1) {
      print("Filled in: " + count + "/" + totalSquares);
      printBoard5(guessBoard5, rowA, rowB, rowC, rowD, rowE);
      printVertical(colNums5, max);

      print("Lives left: " + livesLeft);
      print("Hints left: " + hint5);
      input = getInput("Enter coordinates: ", "coor5", "Invalid coordinates!");
      temp = input;
      
      if (temp.length() == 5) {
        if (temp.indexOf("ROW") != -1) {
          r = translateCoor(temp.charAt(4));
          input = getInput("Enter starting column: ", "startEndIndex5", "Invalid coordinates!");

          if (input.equals("ALL")) {
            start = 0; 
            end = guessBoard5.length-1;
          }
          else {
            start = translateCoor(input.charAt(0));

            input = getInput("Enter ending column: ", "startEndIndex5", "Invalid coordinates!");

            if (input.equals("ALL")) {
              end = guessBoard5.length-1;
            }

            else {
              end = translateCoor(input.charAt(0));

              if (start > end) {
                end = start;
                start = translateCoor(input.charAt(0));
              }
            }
          }
          livesLeft = fillO(livesLeft, start, end, r, "ROW", board5, guessBoard5);
        }

        else {
          c = translateCoor(temp.charAt(4));
          input = getInput("Enter starting row: ", "startEndIndex5", "Invalid coordinates!");

          if (input.equals("ALL")) {
            start = 0; 
            end = guessBoard5.length-1;
          }
          else {
            start = translateCoor(input.charAt(0));

            input = getInput("Enter ending row: ", "startEndIndex5", "Invalid coordinates!");

            if (input.equals("ALL")) {
              end = guessBoard5.length-1;
            }

            else {
              end = translateCoor(input.charAt(0));

              if (start > end) {
                end = start;
                start = translateCoor(input.charAt(0));
              }
            }
          }
          livesLeft = fillO(livesLeft, start, end, c, "COL", board5, guessBoard5);
        }
      }
      else {
        r = translateCoor(input.charAt(0));
        c = translateCoor(input.charAt(1));

        input = getInput("Enter O or X (or hint): ", "XO", "Invalid symbol!");

        if (input.equals("O") || input.equals("X")) {
          if (!board5[r][c].getFilled("str").equals(input)) {
            print("Warning! Mistake made!");
            livesLeft--;
          }
          guessBoard5[r][c] = board5[r][c].getFilled("str");
        }

        else {
          if (hint5 > 0) {
            guessBoard5[r][c] = board5[r][c].getFilled("str");
            hint5--;
            print("Hints left: " + hint5 + "\n");
          }
          else {
            print("You have no hints.\n");
          }
        }
      }
      
      count = getTotalFilledSquares(guessBoard5);
    }

    if (livesLeft == 0) {
      print("Better luck next time!");
      print("The answer was: ");
    }

    else {
      print("Congratulations! You win!");
      hint5+=livesLeft;
    }

    printBoard5(board5, rowA, rowB, rowC, rowD, rowE);
    printVertical(colNums5, max);
  }

  private static void playBoard10() {
    String input, temp;
    int start, end;
    int totalLives = 3, livesLeft = 3;
    
    fillBoard(board10);
    checkBoardEmpty(board10);
    guessBoard10 = getBaseBoard(10);
    
    rowA = getSideNums(board10[0]);
    rowB = getSideNums(board10[1]);
    rowC = getSideNums(board10[2]);
    rowD = getSideNums(board10[3]);
    rowE = getSideNums(board10[4]);
    rowF = getSideNums(board10[5]);
    rowG = getSideNums(board10[6]);
    rowH = getSideNums(board10[7]);
    rowI = getSideNums(board10[8]);
    rowJ = getSideNums(board10[9]);

    colA = getSideNums(colToRow(board10, 0));
    colB = getSideNums(colToRow(board10, 1));
    colC = getSideNums(colToRow(board10, 2));
    colD = getSideNums(colToRow(board10, 3));
    colE = getSideNums(colToRow(board10, 4));
    colF = getSideNums(colToRow(board10, 5));
    colG = getSideNums(colToRow(board10, 6));
    colH = getSideNums(colToRow(board10, 7));
    colI = getSideNums(colToRow(board10, 8));
    colJ = getSideNums(colToRow(board10, 9));

    String[] colNums10 = {colA, colB, colC, colD, colE, colF, colG, colH, colI, colJ};
        
    int max = maxSpace(colNums10);
    
    int totalSquares = getTotalFilledSquares(board10);
    int count = 0;
    int r, c;
    
    while (count != totalSquares && livesLeft >= 1) {
      print("Filled in: " + count + "/" + totalSquares);
      printBoard10(guessBoard10, rowA, rowB, rowC, rowD, rowE, rowF, rowG, rowH, rowI, rowJ);
      printVertical(colNums10, max);

      print("Lives left: " + livesLeft);
      print("Hints left: " + hint10);
      input = getInput("Enter coordinates: ", "coor10", "Invalid coordinates!");
temp = input;
      
      if (temp.length() == 5) {
        if (temp.indexOf("ROW") != -1) {
          r = translateCoor(temp.charAt(4));
          input = getInput("Enter starting column: ", "startEndIndex10", "Invalid coordinates!");

          if (input.equals("ALL")) {
            start = 0; 
            end = guessBoard10.length-1;
          }
          else {
            start = translateCoor(input.charAt(0));

            input = getInput("Enter ending column: ", "startEndIndex10", "Invalid coordinates!");

            if (input.equals("ALL")) {
              end = guessBoard10.length-1;
            }

            else {
              end = translateCoor(input.charAt(0));

              if (start > end) {
                end = start;
                start = translateCoor(input.charAt(0));
              }
            }
          }
          livesLeft = fillO(livesLeft, start, end, r, "ROW", board10, guessBoard10);
        }

        else {
          c = translateCoor(temp.charAt(4));
          input = getInput("Enter starting row: ", "startEndIndex10", "Invalid coordinates!");

          if (input.equals("ALL")) {
            start = 0; 
            end = guessBoard10.length-1;
          }
          else {
            start = translateCoor(input.charAt(0));

            input = getInput("Enter ending row: ", "startEndIndex10", "Invalid coordinates!");

            if (input.equals("ALL")) {
              end = guessBoard10.length-1;
            }

            else {
              end = translateCoor(input.charAt(0));

              if (start > end) {
                end = start;
                start = translateCoor(input.charAt(0));
              }
            }
          }
          livesLeft = fillO(livesLeft, start, end, c, "COL", board10, guessBoard10);
        }
      }
      else {
        r = translateCoor(input.charAt(0));
        c = translateCoor(input.charAt(1));

        input = getInput("Enter O or X (or hint): ", "XO", "Invalid symbol!");

        if (input.equals("O") || input.equals("X")) {
          if (!board10[r][c].getFilled("str").equals(input)) {
            print("Warning! Mistake made!");
            livesLeft--;
          }
          guessBoard10[r][c] = board10[r][c].getFilled("str");
        }

        else {
          if (hint10 > 0) {
            guessBoard10[r][c] = board10[r][c].getFilled("str");
            hint10--;
            print("Hints left: " + hint10 + "\n");
          }
          else {
            print("You have no hints.\n");
          }
        }
      }
      
      count = getTotalFilledSquares(guessBoard10);
    }

    if (livesLeft == 0) {
      print("Better luck next time!");
      print("The answer was: ");
    }

    else {
      print("Congratulations! You win!");
      hint10+=livesLeft;
    }

    printBoard10(board10, rowA, rowB, rowC, rowD, rowE, rowF, rowG, rowH, rowI, rowJ);
    printVertical(colNums10, max);
  }

  private static void playBoard15() {
    String input, temp;
    int start, end;
    int totalLives = 3, livesLeft = 3;
    
    fillBoard(board15);
    checkBoardEmpty(board15);
    guessBoard15 = getBaseBoard(15);
    
    rowA = getSideNums(board15[0]);
    rowB = getSideNums(board15[1]);
    rowC = getSideNums(board15[2]);
    rowD = getSideNums(board15[3]);
    rowE = getSideNums(board15[4]);
    rowF = getSideNums(board15[5]);
    rowG = getSideNums(board15[6]);
    rowH = getSideNums(board15[7]);
    rowI = getSideNums(board15[8]);
    rowJ = getSideNums(board15[9]);
    rowK = getSideNums(board15[10]);
    rowL = getSideNums(board15[11]);
    rowM = getSideNums(board15[12]);
    rowN = getSideNums(board15[13]);
    rowO = getSideNums(board15[4]);

    colA = getSideNums(colToRow(board15, 0));
    colB = getSideNums(colToRow(board15, 1));
    colC = getSideNums(colToRow(board15, 2));
    colD = getSideNums(colToRow(board15, 3));
    colE = getSideNums(colToRow(board15, 4));
    colF = getSideNums(colToRow(board15, 5));
    colG = getSideNums(colToRow(board15, 6));
    colH = getSideNums(colToRow(board15, 7));
    colI = getSideNums(colToRow(board15, 8));
    colJ = getSideNums(colToRow(board15, 9));
    colK = getSideNums(colToRow(board15, 10));
    colL = getSideNums(colToRow(board15, 11));
    colM = getSideNums(colToRow(board15, 12));
    colN = getSideNums(colToRow(board15, 13));
    colO = getSideNums(colToRow(board15, 14));

    String[] colNums15 = {colA, colB, colC, colD, colE, colF, colG, colH, colI, colJ, colK, colL, colM, colN, colO};
        
    int max = maxSpace(colNums15);
    
    int totalSquares = getTotalFilledSquares(board15);
    int count = 0;
    int r, c;

    while (count != totalSquares && livesLeft >= 1) {
      print("Filled in: " + count + "/" + totalSquares);
      printBoard15(guessBoard15, rowA, rowB, rowC, rowD, rowE, rowF, rowG, rowH, rowI, rowJ, rowK, rowL, rowM, rowN, rowO);
      printVertical(colNums15, max);

      print("Lives left: " + livesLeft);
      print("Hints left: " + hint15);
      input = getInput("Enter coordinates: ", "coor15", "Invalid coordinates!");
temp = input;
      
      if (temp.length() == 5) {
        if (temp.indexOf("ROW") != -1) {
          r = translateCoor(temp.charAt(4));
          input = getInput("Enter starting column: ", "startEndIndex15", "Invalid coordinates!");

          if (input.equals("ALL")) {
            start = 0; 
            end = guessBoard15.length-1;
          }
          else {
            start = translateCoor(input.charAt(0));

            input = getInput("Enter ending column: ", "startEndIndex15", "Invalid coordinates!");

            if (input.equals("ALL")) {
              end = guessBoard15.length-1;
            }

            else {
              end = translateCoor(input.charAt(0));

              if (start > end) {
                end = start;
                start = translateCoor(input.charAt(0));
              }
            }
          }
          livesLeft = fillO(livesLeft, start, end, r, "ROW", board15, guessBoard15);
        }

        else {
          c = translateCoor(temp.charAt(4));
          input = getInput("Enter starting row: ", "startEndIndex15", "Invalid coordinates!");

          if (input.equals("ALL")) {
            start = 0; 
            end = guessBoard15.length-1;
          }
          else {
            start = translateCoor(input.charAt(0));

            input = getInput("Enter ending row: ", "startEndIndex15", "Invalid coordinates!");

            if (input.equals("ALL")) {
              end = guessBoard15.length-1;
            }

            else {
              end = translateCoor(input.charAt(0));

              if (start > end) {
                end = start;
                start = translateCoor(input.charAt(0));
              }
            }
          }
          livesLeft = fillO(livesLeft, start, end, c, "COL", board15, guessBoard15);
        }
      }
      else {
        r = translateCoor(input.charAt(0));
        c = translateCoor(input.charAt(1));

        input = getInput("Enter O or X (or hint): ", "XO", "Invalid symbol!");

        if (input.equals("O") || input.equals("X")) {
          if (!board15[r][c].getFilled("str").equals(input)) {
            print("Warning! Mistake made!");
            livesLeft--;
          }
          guessBoard15[r][c] = board15[r][c].getFilled("str");
        }

        else {
          if (hint15 > 0) {
            guessBoard15[r][c] = board15[r][c].getFilled("str");
            hint15--;
            print("Hints left: " + hint15 + "\n");
          }
          else {
            print("You have no hints.\n");
          }
        }
      }
      
      count = getTotalFilledSquares(guessBoard15);
    }

    if (livesLeft == 0) {
      print("Better luck next time!");
      print("The answer was: ");
    }

    else {
      print("Congratulations! You win!");
      hint15+=livesLeft;
    }

    printBoard15(board15, rowA, rowB, rowC, rowD, rowE, rowF, rowG, rowH, rowI, rowJ, rowK, rowL, rowM, rowN, rowO);
    printVertical(colNums15, max);
  }
  
  private static String getSideNums(Square[] arr) {
    String str = "";
    boolean clump = false;
    int count = 0;

    for (int i = 0; i < arr.length; i++) {
      if (arr[i].isFilled()) {
        if (!clump)
          clump = true;
        count++;
      }

      else {
        if (clump)
          clump = false;

        if (count > 0)
          str += count + " ";

        count = 0;
      }
    }

    if (count > 0)
      str += count;

    str = str.trim();
    str = " " + str + " ";
    return str;
  } 

  private static void printVertical(String[] colNums, int maxSpace) {
    int[] indicies = new int[colNums.length];
    int index1 = 0, index2 = 0;
    String colNum = "", str = "";

    p("   ");
    for (int i = 0; i < indicies.length; i++) {
      colNum = colNums[i];
      index1 = colNum.indexOf(" ");
      index2 = colNum.indexOf(" ", index1+1);
      str = colNum.substring(index1+1, index2);

      if (i == 0) {
        if (str.length() == 1)
          p(" | " + str + "   ");
        else if (str.length() == 2)
          p(" |" + str + "   ");
        else
          p(" |    ");
      }
      else if (i == 4 || i == 9 || i == 14) {
        if (str.length() == 1)
          p(str + " | ");
        else if (str.length() == 2)
          p(str + "| ");
        else
          p("     ");
      }
      else {
        if (str.length() == 1)
          p(str + "   ");
        else if (str.length() == 2)
          p(str + "  ");
        else
          p("     ");
      }

      indicies[i] = index2;
    }

    print();

    for (int i = 2; i < maxSpace; i++) {
      p("   ");
      
      for (int j = 0; j < indicies.length; j++) {
        colNum = colNums[j];

        if (indicies[j] == colNum.length()-1) {
          if (j == 0) 
            p(" |     ");
          else if (j == 4 || j == 9 || j == 14)
            p("  | ");
          else
            p("    ");
        }
          
        else {
          index1 = colNum.indexOf(" ", indicies[j]);
          index2 = colNum.indexOf(" ", index1+1);

          if (index2 == -1)
            str = " ";
          else {
            str = colNum.substring(index1+1, index2);
      
            if (j == 0) {
              if (str.length() == 1)
                p(" | " + str + "   ");
              else if (str.length() == 2)
                p(" |" + str + "   ");
              else
                p(" |    ");
            }
            else if (j == 4 || j == 9 || j == 14) {
              if (str.length() == 1)
                p(str + " | ");
              else if (str.length() == 2)
                p(str + "| ");
              else
                p("     ");
            }
            else {
              if (str.length() == 1)
                p(str + "   ");
              else if (str.length() == 2)
                p(str + "  ");
              else
                p("     ");
            }
      
            indicies[j] = index2;
          }
        }
      }
      print();
    }

    print();
  }

  private static int translateCoor(char a) {
    switch(a) {
      case 'A':
        return 0;
      case 'B':
        return 1;
      case 'C':
        return 2;
      case 'D':
        return 3;
      case 'E':
        return 4;
      case 'F':
        return 5;
      case 'G':
        return 6;
      case 'H':
        return 7;
      case 'I':
        return 8;
      case 'J':
        return 9;
      case 'K':
        return 10;
      case 'L':
        return 11;
      case 'M':
        return 12;
      case 'N':
        return 13;
      case 'O':
        return 14;
    }

    return -1;
  }

  private static Square[] colToRow(Square[][] board, int col) {
    Square[] arr = new Square[board.length];

    for (int i = 0; i < arr.length; i++) {
      arr[i] = board[i][col];
    }

    return arr;
  }

  private static int fillO(int livesLeft, int start, int end, int num, String type, Square[][] answerBoard, String[][] guessBoard) {
    if (type.equals("ROW")) {
      int r = num;
      for (int i = start; i <= end; i++) {
        if (answerBoard[r][i].getFilled("str").equals("O"))
          guessBoard[r][i] = "O";
        else {
          guessBoard[r][i] = "X";
          print("Warning! Mistake made!");
          livesLeft--;
          break;
        }
      }
    }

    else {
      int c = num;
      for (int i = start; i <= end; i++) {
        if (answerBoard[i][c].getFilled("str").equals("O"))
          guessBoard[i][c] = "O";
        else {
          guessBoard[i][c] = "X";
          print("Warning! Mistake made!");
          livesLeft--;
          break;
        }
      }
    }

    return livesLeft;
  }

  private static int maxSpace(String[] arr) {
    int count = 0, max = 0, index;
    for (int i = 0; i < arr.length; i++) {
      count = 0;
      index = arr[i].indexOf(" ");
      while (index != -1) {
        count++;
        index = arr[i].indexOf(" ", index+1);
      }

      if (count > max)
        max = count;
    }

    return max;
  }

  private static int getTotalFilledSquares(Square[][] board) {
    int count = 0;

    for (int i = 0; i < board.length; i++) {
      for (int j = 0; j < board[i].length; j++) {
        if (board[i][j].isFilled())
          count++;
      }
    }

    return count;
  }

  private static int getTotalFilledSquares(String[][] board) {
    int count = 0;

    for (int i = 0; i < board.length; i++) {
      for (int j = 0; j < board[i].length; j++) {
        if (board[i][j].equals("O"))
          count++;
      }
    }

    return count;
  }

  private static Square[][] specialBoard() {
    Square[][] board = new Square[5][5];
    for (int i = 0; i < board.length; i++) {
      for (int j = 0; j < board[i].length; j++) {
        board[i][j] = new Square();
      }
    }
    board[0][0].setFilled(true);
    board[0][2].setFilled(true);
    board[0][3].setFilled(true);
    board[1][1].setFilled(true);
    board[1][3].setFilled(true);
    board[1][4].setFilled(true);
    board[2][0].setFilled(true);
    board[2][2].setFilled(true);
    board[2][4].setFilled(true);
    board[3][3].setFilled(true);
    board[3][4].setFilled(true);
    board[4][0].setFilled(true);
    board[4][3].setFilled(true);
    board[4][4].setFilled(true);

    return board;
  }
  
  private static void fillBoard(Square[][] board) {
    int rand = 0;
    for (int i = 0; i < board.length; i++) {
      for (int j = 0; j < board[i].length; j++) {
        rand = getRandomNumber(1, 100);
        board[i][j] = new Square(rand <= 60);
      }
    }
  }

  private static void checkBoardEmpty(Square[][] board) {
    int rand = 0;
    
    for (int i = 0; i < board.length; i++) {
      if (isEmpty(board[i])) {
        rand = getRandomNumber(1, board[i].length-1);
        
        for (int j = 0; j < rand; j++) {
          board[i][getRandomNumber(0, board[i].length-1)].setFilled(true);
        }
      }
    }

    for (int i = 0; i < board[0].length; i++) {
      if (isEmpty(colToRow(board, i))) {
        rand = getRandomNumber(1, board.length-1);
        
        for (int j = 0; j < rand; j++) {
          board[getRandomNumber(0, board.length-1)][i].setFilled(true);
        }
      }
    }
  }

  private static String[][] getBaseBoard(int len) {
    String[][] arr = new String[len][len];

    for (int i = 0; i < arr.length; i++) {
      for (int j = 0; j < arr[i].length; j++) {
        arr[i][j] = " ";
      }
    }

    return arr;
  }

  private static boolean isEmpty(Square[] row) {
    for (int i = 0; i < row.length; i++) {
      if (row[i].isFilled())
        return false;
    }
    return true;
  }

  private static boolean isValidCoor5(char a) {
    char[] arr = {'A', 'B', 'C', 'D', 'E'};

    for (int i = 0; i < arr.length; i++) {
      if (arr[i] == a)
        return true;
    }

    return false;
  }

  private static boolean isValidCoor10(char a) {
    char[] arr = {'A', 'B', 'C', 'D', 'E', 'F', 'G', 'H', 'I', 'J'};

    for (int i = 0; i < arr.length; i++) {
      if (arr[i] == a)
        return true;
    }

    return false;
  }

  private static boolean isValidCoor15(char a) {
    char[] arr = {'A', 'B', 'C', 'D', 'E', 'F', 'G', 'H', 'I', 'J', 'K', 'L', 'M', 'N', 'O'};

    for (int i = 0; i < arr.length; i++) {
      if (arr[i] == a)
        return true;
    }

    return false;
  }
  
  private static int getRandomNumber(int min, int max) {
    return (int) (Math.random() * (max-min+1) + min);
  }

  private static String getInput(String question, String type, String errorMessage) {
    p(question);
    String input = sc.nextLine();
    input = input.trim();
    input = input.toUpperCase();

    boolean good = checkInput(input, type);
    
    while (!good) {
      print("Error! " + errorMessage);
      p("\n" + question);
      input = sc.nextLine();
      input = input.trim();
      input = input.toUpperCase();
      good = checkInput(input, type);
    }

    print();
    
    return input;
  }

  private static boolean checkInput(String input, String type) {
    switch (type) {
      case "levelMenu":
        return input.equals("1") || input.equals("2") || input.equals("3");

      case "coor5":
        return (input.length() == 5 && (input.substring(0, 4).equals("ROW ") || input.substring(0, 4).equals("COL ")) && isValidCoor5(input.charAt(4))) || (input.length() == 2 && isValidCoor5(input.charAt(0)) && isValidCoor5(input.charAt(1)));

      case "coor10":
        return (input.length() == 5 && (input.substring(0, 4).equals("ROW ") || input.substring(0, 4).equals("COL ")) && isValidCoor10(input.charAt(4))) || (input.length() == 2 && isValidCoor10(input.charAt(0)) && isValidCoor10(input.charAt(1)));

      case "coor15":
        return (input.length() == 5 && (input.substring(0, 4).equals("ROW ") || input.substring(0, 4).equals("COL ")) && isValidCoor15(input.charAt(4))) || (input.length() == 2 && isValidCoor15(input.charAt(0)) && isValidCoor15(input.charAt(1)));

      case "XO":
        return input.equals("X") || input.equals("O") || input.equals("HINT");

      case "startEndIndex5":
        return input.equals("ALL") || (input.length() == 1 && isValidCoor5(input.charAt(0)));

      case "startEndIndex10":
        return input.equals("ALL") || (input.length() == 1 && isValidCoor10(input.charAt(0)));

      case "startEndIndex15":
        return input.equals("ALL") || (input.length() == 1 && isValidCoor15(input.charAt(0)));

      case "isWholeNum":
        return isNum(input);

      case "isInt":
        if (input.indexOf("-") == 0)
          return isNum(input.substring(1));
        else if (input.indexOf("-") == -1)
          return isNum(input);
        else
          return false;

      case "isDeci":
        if (input.indexOf(".") != input.lastIndexOf("."))
          return false;
        
        if (input.indexOf("-") == 0)
          return isNum(input.substring(1));
        else if (input.indexOf("-") == -1)
          return isNum(input);
        else
          return false;

      case "noCheck":
        return true;
      
      default:
        return false;
    }
  }

  private static boolean isNum(String str) {
    if (str.length() == 0)
      return false;
    
    for (int i = 0; i < str.length(); i++) {
      if (Character.isDigit(str.charAt(i)) == false)
        return false;
    }

    return true;
  }

  private static void printBoard5(Square[][] a, String rowA, String rowB, String rowC, String rowD, String rowE) {
    print("\t| A   B   C   D   E |");
    print("—————————————————————————————————");
    print("A   | " + a[0][0] + " | " + a[0][1] + " | " + a[0][2] + " | " + a[0][3] + " | " + a[0][4] + " | " + rowA);
    //print("\t---------------------");
    print("\t—————————————————————");
    print("B   | " + a[1][0] + " | " + a[1][1] + " | " + a[1][2] +  " | " + a[1][3] + " | " + a[1][4] + " | " + rowB);
    print("\t—————————————————————");
    print("C   | " + a[2][0] + " | " + a[2][1] + " | " + a[2][2] + " | " + a[2][3] + " | " + a[2][4] + " | " + rowC);
    print("\t—————————————————————");
    print("D   | " + a[3][0] + " | " + a[3][1] + " | " + a[3][2] + " | " + a[3][3] + " | " + a[3][4] + " | " + rowD);
    print("\t—————————————————————");
    print("E   | " + a[4][0] + " | " + a[4][1] + " | " + a[4][2] + " | " + a[4][3] + " | " + a[4][4] + " | " + rowE);
    //print("\t-----------------------------");
    print("—————————————————————————————————");
  }

  private static void printBoard5(String[][] a, String rowA, String rowB, String rowC, String rowD, String rowE) {
    print("\t| A   B   C   D   E |");
    print("—————————————————————————————————");
    print("A   | " + a[0][0] + " | " + a[0][1] + " | " + a[0][2] + " | " + a[0][3] + " | " + a[0][4] + " | " + rowA);
    //print("\t---------------------");
    print("\t—————————————————————");
    print("B   | " + a[1][0] + " | " + a[1][1] + " | " + a[1][2] +  " | " + a[1][3] + " | " + a[1][4] + " | " + rowB);
    print("\t—————————————————————");
    print("C   | " + a[2][0] + " | " + a[2][1] + " | " + a[2][2] + " | " + a[2][3] + " | " + a[2][4] + " | " + rowC);
    print("\t—————————————————————");
    print("D   | " + a[3][0] + " | " + a[3][1] + " | " + a[3][2] + " | " + a[3][3] + " | " + a[3][4] + " | " + rowD);
    print("\t—————————————————————");
    print("E   | " + a[4][0] + " | " + a[4][1] + " | " + a[4][2] + " | " + a[4][3] + " | " + a[4][4] + " | " + rowE);
    //print("\t-----------------------------");
    print("—————————————————————————————————");
  }

  private static void printBoard10(Square[][] a, String rowA, String rowB, String rowC, String rowD, String rowE, String rowF, String rowG, String rowH, String rowI, String rowJ) {
    print("\t| A   B   C   D   E | F   G   H   I   J |");
    print("———————————————————————————————————————————————————————");
    print("A   | " + a[0][0] + " | " + a[0][1] + " | " + a[0][2] + " | " + a[0][3] + " | " + a[0][4] + " | " + a[0][5] + " | " + a[0][6] + " | " + a[0][7] + " | " + a[0][8] + " | " + a[0][9] + " | " + rowA);
    //print("\t-----------------------------------------");
    print("\t—————————————————————————————————————————");
    print("B   | " + a[1][0] + " | " + a[1][1] + " | " + a[1][2] +  " | " + a[1][3] + " | " + a[1][4] + " | " + a[1][5] + " | " + a[1][6] + " | " + a[1][7] + " | " + a[1][8] + " | " + a[1][9] + " | " + rowB);
    print("\t—————————————————————————————————————————");
    print("C   | " + a[2][0] + " | " + a[2][1] + " | " + a[2][2] + " | " + a[2][3] + " | " + a[2][4] + " | " + a[2][5] + " | " + a[2][6] + " | " + a[2][7] + " | " + a[2][8] + " | " + a[2][9] + " | " + rowC);
    print("\t—————————————————————————————————————————");
    print("D   | " + a[3][0] + " | " + a[3][1] + " | " + a[3][2] + " | " + a[3][3] + " | " + a[3][4] + " | " + a[3][5] + " | " + a[3][6] + " | " + a[3][7] + " | " + a[3][8] + " | " + a[3][9] + " | " + rowD);
    print("\t—————————————————————————————————————————");
    print("E   | " + a[4][0] + " | " + a[4][1] + " | " + a[4][2] + " | " + a[4][3] + " | " + a[4][4] + " | " + a[4][5] + " | " + a[4][6] + " | " + a[4][7] + " | " + a[4][8] + " | " + a[4][9] + " | " + rowE);
    print("———————————————————————————————————————————————————————");
    print("F   | " + a[5][0] + " | " + a[5][1] + " | " + a[5][2] + " | " + a[5][3] + " | " + a[5][4] + " | " + a[5][5] + " | " + a[5][6] + " | " + a[5][7] + " | " + a[5][8] + " | " + a[5][9] + " | " + rowF);
    print("\t—————————————————————————————————————————");
    print("G   | " + a[6][0] + " | " + a[6][1] + " | " + a[6][2] +  " | " + a[6][3] + " | " + a[6][4] + " | " + a[6][5] + " | " + a[6][6] + " | " + a[6][7] + " | " + a[6][8] + " | " + a[6][9] + " | " + rowG);
    print("\t—————————————————————————————————————————");
    print("H   | " + a[7][0] + " | " + a[7][1] + " | " + a[7][2] + " | " + a[7][3] + " | " + a[7][4] + " | " + a[7][5] + " | " + a[7][6] + " | " + a[7][7] + " | " + a[7][8] + " | " + a[7][9] + " | " + rowH);
    print("\t—————————————————————————————————————————");
    print("I   | " + a[8][0] + " | " + a[8][1] + " | " + a[8][2] + " | " + a[8][3] + " | " + a[8][4] + " | " + a[8][5] + " | " + a[8][6] + " | " + a[8][7] + " | " + a[8][8] + " | " + a[8][9] + " | " + rowI);
    print("\t—————————————————————————————————————————");
    print("J   | " + a[9][0] + " | " + a[9][1] + " | " + a[9][2] + " | " + a[9][3] + " | " + a[9][4] + " | " + a[9][5] + " | " + a[9][6] + " | " + a[9][7] + " | " + a[9][8] + " | " + a[9][9] + " | " + rowJ);
    //print("\t---------------------------------------------------");
    print("———————————————————————————————————————————————————————");
  }

  private static void printBoard10(String[][] a, String rowA, String rowB, String rowC, String rowD, String rowE, String rowF, String rowG, String rowH, String rowI, String rowJ) {
    print("\t| A   B   C   D   E | F   G   H   I   J |");
    print("———————————————————————————————————————————————————————");
    print("A   | " + a[0][0] + " | " + a[0][1] + " | " + a[0][2] + " | " + a[0][3] + " | " + a[0][4] + " | " + a[0][5] + " | " + a[0][6] + " | " + a[0][7] + " | " + a[0][8] + " | " + a[0][9] + " | " + rowA);
    //print("\t-----------------------------------------");
    print("\t—————————————————————————————————————————");
    print("B   | " + a[1][0] + " | " + a[1][1] + " | " + a[1][2] +  " | " + a[1][3] + " | " + a[1][4] + " | " + a[1][5] + " | " + a[1][6] + " | " + a[1][7] + " | " + a[1][8] + " | " + a[1][9] + " | " + rowB);
    print("\t—————————————————————————————————————————");
    print("C   | " + a[2][0] + " | " + a[2][1] + " | " + a[2][2] + " | " + a[2][3] + " | " + a[2][4] + " | " + a[2][5] + " | " + a[2][6] + " | " + a[2][7] + " | " + a[2][8] + " | " + a[2][9] + " | " + rowC);
    print("\t—————————————————————————————————————————");
    print("D   | " + a[3][0] + " | " + a[3][1] + " | " + a[3][2] + " | " + a[3][3] + " | " + a[3][4] + " | " + a[3][5] + " | " + a[3][6] + " | " + a[3][7] + " | " + a[3][8] + " | " + a[3][9] + " | " + rowD);
    print("\t—————————————————————————————————————————");
    print("E   | " + a[4][0] + " | " + a[4][1] + " | " + a[4][2] + " | " + a[4][3] + " | " + a[4][4] + " | " + a[4][5] + " | " + a[4][6] + " | " + a[4][7] + " | " + a[4][8] + " | " + a[4][9] + " | " + rowE);
    print("———————————————————————————————————————————————————————");
    print("F   | " + a[5][0] + " | " + a[5][1] + " | " + a[5][2] + " | " + a[5][3] + " | " + a[5][4] + " | " + a[5][5] + " | " + a[5][6] + " | " + a[5][7] + " | " + a[5][8] + " | " + a[5][9] + " | " + rowF);
    print("\t—————————————————————————————————————————");
    print("G   | " + a[6][0] + " | " + a[6][1] + " | " + a[6][2] +  " | " + a[6][3] + " | " + a[6][4] + " | " + a[6][5] + " | " + a[6][6] + " | " + a[6][7] + " | " + a[6][8] + " | " + a[6][9] + " | " + rowG);
    print("\t—————————————————————————————————————————");
    print("H   | " + a[7][0] + " | " + a[7][1] + " | " + a[7][2] + " | " + a[7][3] + " | " + a[7][4] + " | " + a[7][5] + " | " + a[7][6] + " | " + a[7][7] + " | " + a[7][8] + " | " + a[7][9] + " | " + rowH);
    print("\t—————————————————————————————————————————");
    print("I   | " + a[8][0] + " | " + a[8][1] + " | " + a[8][2] + " | " + a[8][3] + " | " + a[8][4] + " | " + a[8][5] + " | " + a[8][6] + " | " + a[8][7] + " | " + a[8][8] + " | " + a[8][9] + " | " + rowI);
    print("\t—————————————————————————————————————————");
    print("J   | " + a[9][0] + " | " + a[9][1] + " | " + a[9][2] + " | " + a[9][3] + " | " + a[9][4] + " | " + a[9][5] + " | " + a[9][6] + " | " + a[9][7] + " | " + a[9][8] + " | " + a[9][9] + " | " + rowJ);
    //print("\t---------------------------------------------------");
    print("———————————————————————————————————————————————————————");
  }

  private static void printBoard15(Square[][] a, String rowA, String rowB, String rowC, String rowD, String rowE, String rowF, String rowG, String rowH, String rowI, String rowJ, String rowK, String rowL, String rowM, String rowN, String rowO) {
    print("\t| A   B   C   D   E | F   G   H   I   J | K   L   M   N   O |");
    print("————————————————————————————————————————————————————————————————————————————————");
    print("A   | " + a[0][0] + " | " + a[0][1] + " | " + a[0][2] + " | " + a[0][3] + " | " + a[0][4] + " | " + a[0][5] + " | " + a[0][6] + " | " + a[0][7] + " | " + a[0][8] + " | " + a[0][9] + " | " + a[0][10] + " | " + a[0][11] + " | " + a[0][12] + " | " + a[0][13] + " | " + a[0][14] + " | " + rowA);
    //print("\t-------------------------------------------------------------");
    print("\t—————————————————————————————————————————————————————————————");
    print("B   | " + a[1][0] + " | " + a[1][1] + " | " + a[1][2] +  " | " + a[1][3] + " | " + a[1][4] + " | " + a[1][5] + " | " + a[1][6] + " | " + a[1][7] + " | " + a[1][8] + " | " + a[1][9] + " | " + a[1][10] + " | " + a[1][11] + " | " + a[1][12] + " | " + a[1][13] + " | " + a[1][14] + " | " + rowB);
    print("\t—————————————————————————————————————————————————————————————");
    print("C   | " + a[2][0] + " | " + a[2][1] + " | " + a[2][2] + " | " + a[2][3] + " | " + a[2][4] + " | " + a[2][5] + " | " + a[2][6] + " | " + a[2][7] + " | " + a[2][8] + " | " + a[2][9] + " | " + a[2][10] + " | " + a[2][11] + " | " + a[2][12] + " | " + a[2][13] + " | " + a[2][14] + " | " + rowC);
    print("\t—————————————————————————————————————————————————————————————");
    print("D   | " + a[3][0] + " | " + a[3][1] + " | " + a[3][2] + " | " + a[3][3] + " | " + a[3][4] + " | " + a[3][5] + " | " + a[3][6] + " | " + a[3][7] + " | " + a[3][8] + " | " + a[3][9] + " | " + a[3][10] + " | " + a[3][11] + " | " + a[3][12] + " | " + a[3][13] + " | " + a[3][14] + " | " + rowD);
    print("\t—————————————————————————————————————————————————————————————");
    print("E   | " + a[4][0] + " | " + a[4][1] + " | " + a[4][2] + " | " + a[4][3] + " | " + a[4][4] + " | " + a[4][5] + " | " + a[4][6] + " | " + a[4][7] + " | " + a[4][8] + " | " + a[4][9] + " | " + a[4][10] + " | " + a[4][11] + " | " + a[4][12] + " | " + a[4][13] + " | " + a[4][14] + " | " + rowE);
    print("————————————————————————————————————————————————————————————————————————————————");
    print("F   | " + a[5][0] + " | " + a[5][1] + " | " + a[5][2] + " | " + a[5][3] + " | " + a[5][4] + " | " + a[5][5] + " | " + a[5][6] + " | " + a[5][7] + " | " + a[5][8] + " | " + a[5][9] + " | " + a[5][10] + " | " + a[5][11] + " | " + a[5][12] + " | " + a[5][13] + " | " + a[5][14] + " | " + rowF);
    print("\t—————————————————————————————————————————————————————————————");
    print("G   | " + a[6][0] + " | " + a[6][1] + " | " + a[6][2] +  " | " + a[6][3] + " | " + a[6][4] + " | " + a[6][5] + " | " + a[6][6] + " | " + a[6][7] + " | " + a[6][8] + " | " + a[6][9] + " | " + a[6][10] + " | " + a[6][11] + " | " + a[6][12] + " | " + a[6][13] + " | " + a[6][14] + " | " + rowG);
    print("\t—————————————————————————————————————————————————————————————");
    print("H   | " + a[7][0] + " | " + a[7][1] + " | " + a[7][2] + " | " + a[7][3] + " | " + a[7][4] + " | " + a[7][5] + " | " + a[7][6] + " | " + a[7][7] + " | " + a[7][8] + " | " + a[7][9] + " | " + a[7][10] + " | " + a[7][11] + " | " + a[7][12] + " | " + a[7][13] + " | " + a[7][14] + " | " + rowH);
    print("\t—————————————————————————————————————————————————————————————");
    print("I   | " + a[8][0] + " | " + a[8][1] + " | " + a[8][2] + " | " + a[8][3] + " | " + a[8][4] + " | " + a[8][5] + " | " + a[8][6] + " | " + a[8][7] + " | " + a[8][8] + " | " + a[8][9] + " | " + a[8][10] + " | " + a[8][11] + " | " + a[8][12] + " | " + a[8][13] + " | " + a[8][14] + " | " + rowI);
    print("\t—————————————————————————————————————————————————————————————");
    print("J   | " + a[9][0] + " | " + a[9][1] + " | " + a[9][2] + " | " + a[9][3] + " | " + a[9][4] + " | " + a[9][5] + " | " + a[9][6] + " | " + a[9][7] + " | " + a[9][8] + " | " + a[9][9] + " | " + a[9][10] + " | " + a[9][11] + " | " + a[9][12] + " | " + a[9][13] + " | " + a[9][14] + " | " + rowJ);
    print("————————————————————————————————————————————————————————————————————————————————");
    print("K   | " + a[10][0] + " | " + a[10][1] + " | " + a[10][2] + " | " + a[10][3] + " | " + a[10][4] + " | " + a[10][5] + " | " + a[10][6] + " | " + a[10][7] + " | " + a[10][8] + " | " + a[10][9] + " | " + a[10][10] + " | " + a[10][11] + " | " + a[10][12] + " | " + a[10][13] + " | " + a[10][14] + " | " + rowK);
    print("\t—————————————————————————————————————————————————————————————");
    print("L   | " + a[11][0] + " | " + a[11][1] + " | " + a[11][2] +  " | " + a[11][3] + " | " + a[11][4] + " | " + a[11][5] + " | " + a[11][6] + " | " + a[11][7] + " | " + a[11][8] + " | " + a[11][9] + " | " + a[11][10] + " | " + a[11][11] + " | " + a[11][12] + " | " + a[11][13] + " | " + a[11][14] + " | " + rowL);
    print("\t—————————————————————————————————————————————————————————————");
    print("M   | " + a[12][0] + " | " + a[12][1] + " | " + a[12][2] + " | " + a[12][3] + " | " + a[12][4] + " | " + a[12][5] + " | " + a[12][6] + " | " + a[12][7] + " | " + a[12][8] + " | " + a[12][9] + " | " + a[12][10] + " | " + a[12][11] + " | " + a[12][12] + " | " + a[12][13] + " | " + a[12][14] + " | " + rowM);
    print("\t—————————————————————————————————————————————————————————————");
    print("N   | " + a[13][0] + " | " + a[13][1] + " | " + a[13][2] + " | " + a[13][3] + " | " + a[13][4] + " | " + a[13][5] + " | " + a[13][6] + " | " + a[13][7] + " | " + a[13][8] + " | " + a[13][9] + " | " + a[13][10] + " | " + a[13][11] + " | " + a[13][12] + " | " + a[13][13] + " | " + a[13][14] + " | " + rowN);
    print("\t—————————————————————————————————————————————————————————————");
    print("O   | " + a[14][0] + " | " + a[14][1] + " | " + a[14][2] + " | " + a[14][3] + " | " + a[14][4] + " | " + a[14][5] + " | " + a[14][6] + " | " + a[14][7] + " | " + a[14][8] + " | " + a[14][9] + " | " + a[14][10] + " | " + a[14][11] + " | " + a[14][12] + " | " + a[14][13] + " | " + a[14][14] + " | " + rowO);
    //print("\t----------------------------------------------------------------------------");
    print("————————————————————————————————————————————————————————————————————————————————");
  }

  private static void printBoard15(String[][] a, String rowA, String rowB, String rowC, String rowD, String rowE, String rowF, String rowG, String rowH, String rowI, String rowJ, String rowK, String rowL, String rowM, String rowN, String rowO) {
    print("\t| A   B   C   D   E | F   G   H   I   J | K   L   M   N   O |");
    print("————————————————————————————————————————————————————————————————————————————————");
    print("A   | " + a[0][0] + " | " + a[0][1] + " | " + a[0][2] + " | " + a[0][3] + " | " + a[0][4] + " | " + a[0][5] + " | " + a[0][6] + " | " + a[0][7] + " | " + a[0][8] + " | " + a[0][9] + " | " + a[0][10] + " | " + a[0][11] + " | " + a[0][12] + " | " + a[0][13] + " | " + a[0][14] + " | " + rowA);
    //print("\t-------------------------------------------------------------");
    print("\t—————————————————————————————————————————————————————————————");
    print("B   | " + a[1][0] + " | " + a[1][1] + " | " + a[1][2] +  " | " + a[1][3] + " | " + a[1][4] + " | " + a[1][5] + " | " + a[1][6] + " | " + a[1][7] + " | " + a[1][8] + " | " + a[1][9] + " | " + a[1][10] + " | " + a[1][11] + " | " + a[1][12] + " | " + a[1][13] + " | " + a[1][14] + " | " + rowB);
    print("\t—————————————————————————————————————————————————————————————");
    print("C   | " + a[2][0] + " | " + a[2][1] + " | " + a[2][2] + " | " + a[2][3] + " | " + a[2][4] + " | " + a[2][5] + " | " + a[2][6] + " | " + a[2][7] + " | " + a[2][8] + " | " + a[2][9] + " | " + a[2][10] + " | " + a[2][11] + " | " + a[2][12] + " | " + a[2][13] + " | " + a[2][14] + " | " + rowC);
    print("\t—————————————————————————————————————————————————————————————");
    print("D   | " + a[3][0] + " | " + a[3][1] + " | " + a[3][2] + " | " + a[3][3] + " | " + a[3][4] + " | " + a[3][5] + " | " + a[3][6] + " | " + a[3][7] + " | " + a[3][8] + " | " + a[3][9] + " | " + a[3][10] + " | " + a[3][11] + " | " + a[3][12] + " | " + a[3][13] + " | " + a[3][14] + " | " + rowD);
    print("\t—————————————————————————————————————————————————————————————");
    print("E   | " + a[4][0] + " | " + a[4][1] + " | " + a[4][2] + " | " + a[4][3] + " | " + a[4][4] + " | " + a[4][5] + " | " + a[4][6] + " | " + a[4][7] + " | " + a[4][8] + " | " + a[4][9] + " | " + a[4][10] + " | " + a[4][11] + " | " + a[4][12] + " | " + a[4][13] + " | " + a[4][14] + " | " + rowE);
    print("————————————————————————————————————————————————————————————————————————————————");
    print("F   | " + a[5][0] + " | " + a[5][1] + " | " + a[5][2] + " | " + a[5][3] + " | " + a[5][4] + " | " + a[5][5] + " | " + a[5][6] + " | " + a[5][7] + " | " + a[5][8] + " | " + a[5][9] + " | " + a[5][10] + " | " + a[5][11] + " | " + a[5][12] + " | " + a[5][13] + " | " + a[5][14] + " | " + rowF);
    print("\t—————————————————————————————————————————————————————————————");
    print("G   | " + a[6][0] + " | " + a[6][1] + " | " + a[6][2] +  " | " + a[6][3] + " | " + a[6][4] + " | " + a[6][5] + " | " + a[6][6] + " | " + a[6][7] + " | " + a[6][8] + " | " + a[6][9] + " | " + a[6][10] + " | " + a[6][11] + " | " + a[6][12] + " | " + a[6][13] + " | " + a[6][14] + " | " + rowG);
    print("\t—————————————————————————————————————————————————————————————");
    print("H   | " + a[7][0] + " | " + a[7][1] + " | " + a[7][2] + " | " + a[7][3] + " | " + a[7][4] + " | " + a[7][5] + " | " + a[7][6] + " | " + a[7][7] + " | " + a[7][8] + " | " + a[7][9] + " | " + a[7][10] + " | " + a[7][11] + " | " + a[7][12] + " | " + a[7][13] + " | " + a[7][14] + " | " + rowH);
    print("\t—————————————————————————————————————————————————————————————");
    print("I   | " + a[8][0] + " | " + a[8][1] + " | " + a[8][2] + " | " + a[8][3] + " | " + a[8][4] + " | " + a[8][5] + " | " + a[8][6] + " | " + a[8][7] + " | " + a[8][8] + " | " + a[8][9] + " | " + a[8][10] + " | " + a[8][11] + " | " + a[8][12] + " | " + a[8][13] + " | " + a[8][14] + " | " + rowI);
    print("\t—————————————————————————————————————————————————————————————");
    print("J   | " + a[9][0] + " | " + a[9][1] + " | " + a[9][2] + " | " + a[9][3] + " | " + a[9][4] + " | " + a[9][5] + " | " + a[9][6] + " | " + a[9][7] + " | " + a[9][8] + " | " + a[9][9] + " | " + a[9][10] + " | " + a[9][11] + " | " + a[9][12] + " | " + a[9][13] + " | " + a[9][14] + " | " + rowJ);
    print("————————————————————————————————————————————————————————————————————————————————");
    print("K   | " + a[10][0] + " | " + a[10][1] + " | " + a[10][2] + " | " + a[10][3] + " | " + a[10][4] + " | " + a[10][5] + " | " + a[10][6] + " | " + a[10][7] + " | " + a[10][8] + " | " + a[10][9] + " | " + a[10][10] + " | " + a[10][11] + " | " + a[10][12] + " | " + a[10][13] + " | " + a[10][14] + " | " + rowK);
    print("\t—————————————————————————————————————————————————————————————");
    print("L   | " + a[11][0] + " | " + a[11][1] + " | " + a[11][2] +  " | " + a[11][3] + " | " + a[11][4] + " | " + a[11][5] + " | " + a[11][6] + " | " + a[11][7] + " | " + a[11][8] + " | " + a[11][9] + " | " + a[11][10] + " | " + a[11][11] + " | " + a[11][12] + " | " + a[11][13] + " | " + a[11][14] + " | " + rowL);
    print("\t—————————————————————————————————————————————————————————————");
    print("M   | " + a[12][0] + " | " + a[12][1] + " | " + a[12][2] + " | " + a[12][3] + " | " + a[12][4] + " | " + a[12][5] + " | " + a[12][6] + " | " + a[12][7] + " | " + a[12][8] + " | " + a[12][9] + " | " + a[12][10] + " | " + a[12][11] + " | " + a[12][12] + " | " + a[12][13] + " | " + a[12][14] + " | " + rowM);
    print("\t—————————————————————————————————————————————————————————————");
    print("N   | " + a[13][0] + " | " + a[13][1] + " | " + a[13][2] + " | " + a[13][3] + " | " + a[13][4] + " | " + a[13][5] + " | " + a[13][6] + " | " + a[13][7] + " | " + a[13][8] + " | " + a[13][9] + " | " + a[13][10] + " | " + a[13][11] + " | " + a[13][12] + " | " + a[13][13] + " | " + a[13][14] + " | " + rowN);
    print("\t—————————————————————————————————————————————————————————————");
    print("O   | " + a[14][0] + " | " + a[14][1] + " | " + a[14][2] + " | " + a[14][3] + " | " + a[14][4] + " | " + a[14][5] + " | " + a[14][6] + " | " + a[14][7] + " | " + a[14][8] + " | " + a[14][9] + " | " + a[14][10] + " | " + a[14][11] + " | " + a[14][12] + " | " + a[14][13] + " | " + a[14][14] + " | " + rowO);
    //print("\t----------------------------------------------------------------------------");
    print("————————————————————————————————————————————————————————————————————————————————");
  }

  private static void printWelcomeBanner() {
    print("════════════════════════ -ˋˏ *.·:·.⟐.·:·.* ˎˊ- ════════════════════════");
    print("                         Welcome to Nonogram!");
    print("════════════════════════ -ˋˏ *.·:·.⟐.·:·.* ˎˊ- ════════════════════════");
    print("Rules: ");
    print("1. You can guess whether the box will be filled (O) or not (X).");
    print("2. Enter coordinates (i.e. AA, CE, DB, etc.) to indicate which box you would like to fill. The first letter refers to the row and the second letter refers to the column.");
    print("3. If you would like to fill in more than one box at a time, type \"ROW \" and then the corresponding letter for the row, or type \"COL \" and then the corresponding letter for the column. Then type in the starting index and the ending index, which indicates where you would like the row or column to starting filling in from and where you would like the fill to end.");
    print("4. If you would like to fill in the entire row or column, type in \"ALL\" for the starting index. If you would like the fill to end at the end of the board, type in \"ALL\" for the ending index.");
    print("5. If you would like to use a hint, first type in the corresponding coordinate and they type in \"HINT\" when entering X or O.");
    print("══════");
    print("Notes:");
    print("Hints are granted when the level has been won. Hints can only be used during their corresponding levels. \n\tEx. Hints earned for the 5x5 board can only be used for the 5x5 board.\nThe number of hints you are given corresponds to the number of lives you have left at the end of the level.");
    print("Happy playing!");
    print("═══════════════");
  }
  
  private static void print() {
    System.out.println();
  }
  
  private static void print(String a) {
    System.out.println(a);
  }
  
  private static void print(int a) {
    System.out.println(a);
  }

  private static void print(double a) {
    System.out.println(a);
  }

  private static void print(boolean a) {
    System.out.println(a);
  }

  private static void print(char a) {
    System.out.println(a);
  }

  private static void print(Square a) {
    System.out.println(a);
  }

  private static void p() {
    System.out.print("");
  }

  private static void p(String a) {
    System.out.print(a);
  }

  private static void p(int a) {
    System.out.print(a);
  }

  private static void p(double a) {
    System.out.print(a);
  }

  private static void p(boolean a) {
    System.out.print(a);
  }

  private static void p(char a) {
    System.out.print(a);
  }

  private static void p(Square a) {
    System.out.print(a);
  }
  
  private static void print(String[] a) {
    for (int i = 0; i < a.length; i++) {
      if (i == a.length-1)
        p(a[i]);
      else
        p(a[i] + ", ");
    }
  }

  private static void print(int[] a) {
    for (int i = 0; i < a.length; i++) {
      if (i == a.length-1)
        p(a[i]);
      else
        p(a[i] + ", ");
    }
  }

  private static void print(double[] a) {
    for (int i = 0; i < a.length; i++) {
      if (i == a.length-1)
        p(a[i]);
      else
        p(a[i] + ", ");
    }
  }

  private static void print(boolean[] a) {
    for (int i = 0; i < a.length; i++) {
      if (i == a.length-1)
        p(a[i]);
      else
        p(a[i] + ", ");
    }
  }

  private static void print(char[] a) {
    for (int i = 0; i < a.length; i++) {
      if (i == a.length-1)
        p(a[i]);
      else
        p(a[i] + ", ");
    }
  }

  private static void print(Square[][] a) {
    for (int i = 0; i < a.length; i++) {
      for (int j = 0; j < a[i].length; j++) {
        if (j == a[i].length-1)
          p(a[i][j]);
        else {
          p(a[i][j] + " ");
        }
          
      }
      print();
    }
  }
}

