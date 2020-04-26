package algorithm;

import java.util.LinkedList;
import java.util.List;
import java.util.Scanner;

import model_board.Board;
import model_board.Field;
import model_chess_pieces.ChessPiece;
import model_chess_pieces.ChessPieceCharacteristics;
import model_chess_pieces.King;

/**
 *
 * @author it21735 , it21754, it21762
 */
public class Game {
    
    private static final Player whiteP = new Player(ChessPieceCharacteristics.Color.w, null);
    private static final Player blackP = new Player(ChessPieceCharacteristics.Color.b, null);
    
    private final Board board = new Board();
    
    boolean playerTurn = true;
    int depth = 1;
    miniMax bot; // AI engine

    King king;
    miniMax m;
    private List<ChessMove> blackPieceList; //Hold a list of black pieces. Could be handy.
    private List<ChessMove> whitePieceList; //Hold a list of white pieces. Could be handy again.

    public List<ChessMove> getBlackPieceList() {
        return blackPieceList;
    }
    
    public List<ChessMove> getWhitePieceList() {
        return whitePieceList;
    }
    
    public void setBoard() {
        board.createBoard();
        board.printBoard();
        System.out.println("Players: w=white and b=black");
        System.out.println("Pieces: K=king, Q=queen, N=knight, B=bishop, R=rook, P=pawn");
        System.out.println("Choose which piece you want to move like this: g7 h5 to move from g7 to h5");
        System.out.println("You can write -h for help with a recommended move");
        System.out.println("Or -q if you wish to quit playing.");
        
    }
    
    public int returnColFromUser(String letter) { //returns the actual column that is assigned by the letters
        int column;
        if (letter.equalsIgnoreCase("a")) {
            column = 0;
        } else if (letter.equalsIgnoreCase("b")) {
            column = 1;
        } else if (letter.equalsIgnoreCase("c")) {
            column = 2;
        } else if (letter.equalsIgnoreCase("d")) {
            column = 3;
        } else if (letter.equalsIgnoreCase("e")) {
            column = 4;
        } else if (letter.equalsIgnoreCase("f")) {
            column = 5;
        } else if (letter.equalsIgnoreCase("g")) {
            column = 6;
        } else if (letter.equalsIgnoreCase("h")) {
            column = 7;
        } else {
            column = -1; // in case of wrong input
        }
        return column;
    }
    
    public int returnRowFromUser(int inputRow) { //returns the actual row on the board
        int row;
        if (inputRow >= 0 && inputRow <= 8) {
            row = inputRow - 1;
        } else {
            row = -1; // in case the user inputs a row that does not exist
        }
        
        return row;
    }
    
    public char userCol(int column) { //returns column as letter
        char col;
        switch (column) {
            case 0:
                col = 'a';
                break;
            case 1:
                col = 'b';
                break;
            case 2:
                col = 'c';
                break;
            case 3:
                col = 'd';
                break;
            case 4:
                col = 'e';
                break;
            case 5:
                col = 'f';
                break;
            case 6:
                col = 'g';
                break;
            case 7:
                col = 'h';
                break;
            default:
                col = 'X'; //for error
        }
        return col;
    }
    
    public int userRow(int row) { //returns row as the user sees it
        int nrow;
        if (row >= 0 || row <= 7) {
            nrow = row + 1;
        } else {
            nrow = -1; //in case of error
        }
        return nrow;
    }
    
    public final boolean containsDigit(String s) {
        boolean containsDigit = false;
        
        if (s != null && !s.isEmpty()) {
            for (char c : s.toCharArray()) {
                if (containsDigit = Character.isDigit(c)) {
                    break;
                }
            }
        }
        
        return containsDigit;
    }
    
    public ChessMove readFromUser(Player player) {
        ChessMove userMove = new ChessMove();
        Scanner input = new Scanner(System.in);
        
        String userInput = input.nextLine();
        userInput = userInput.trim();
        userInput = userInput + " ";
        String[] inputTokens = userInput.split(" ");
        
        OUTER:
        switch (inputTokens.length) {
            case 1:
                switch (inputTokens[0]) {
                    case "-h":
                        recommendMove(player.getPlayerColour());
                        userMove = null;
                        break OUTER;
                    case "-q":
                        System.exit(0);
                    default:
                        System.out.println("Not valid input");
                        userMove = null;
                        break OUTER;
                }
            case 2:
                // inputTokens[0] is the position of the piece, eg--> a7
                // inputTokens[1] is the new position, eg-->a6

                String curPos = inputTokens[0];
                curPos = curPos.trim();
                String newPos = inputTokens[1];
                newPos = newPos.trim();
                String[] curTokens = curPos.split("");
                String[] newTokens = newPos.split("");
                
                if (curTokens.length == 2 && newTokens.length == 2) {
                    if ((containsDigit(curTokens[1]) && containsDigit(newTokens[1])) && !(containsDigit(curTokens[0]) && containsDigit(newTokens[0]))) {
                        int curRow = returnRowFromUser(Integer.parseInt(curTokens[1]));
                        int curCol = returnColFromUser(curTokens[0]);
                        userMove.setCurCoor(curRow, curCol);
                        
                        if (ChessMove.isValid(curRow, curCol)) {
                            userMove.setP(board.getField()[curRow][curCol].getChessPiece());
                        }
                        
                        int newRow = returnRowFromUser(Integer.parseInt(newTokens[1]));
                        int newCol = returnColFromUser(newTokens[0]);
                        
                        userMove.setNewCoor(newRow, newCol);
                        
                    } else {
                        System.out.println("Not valid input");
                        userMove = null;
                    }
                } else {
                    System.out.println("Not valid input");
                    userMove = null;
                }
                break;
            default:
                System.out.println("Not valid input");
                userMove = null;
                break;
        }
        return userMove;
    }
    
    public void recommendMove(ChessPieceCharacteristics.Color colour) {
        
        List<ChessMove> recommendedMoves = miniMax.getAllMoves(board, colour);
        System.out.println(" \nRecommended moves for white player:");
        int counter = 0;
        for (ChessMove move : recommendedMoves) {
            counter++;
            String curPos = userCol(move.getCurrent().getCol()) + ""
                    + userRow(move.getCurrent().getRow());
            String newPos = userCol(move.getNewPos().getCol())
                    + "" + userRow(move.getNewPos().getRow());
            if (curPos.equals(newPos)) {
                counter--;
                continue;
            }
            System.out.print(" >>" + curPos + "-->" + newPos + " ");
            
            if (counter % 2 == 0) {
                System.out.print("\n");
            }

            //at the moment it returns all possible moves
        }
        System.out.print("\n");
    }
    
    public boolean isKingAlive(King king, Board result_board, boolean is_color_white) { //returns true if the king is alive, else false
        ChessPiece piece = null;
        m = new miniMax();
        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++) {
                if (result_board.isFieldOccupied(i, j) && result_board.getField()[i][j].getChessPiece().getName() == ChessPieceCharacteristics.Name.K && is_color_white == true && result_board.getField()[i][j].getChessPiece().getColor() == ChessPieceCharacteristics.Color.w) {
                    piece = result_board.getField()[i][j].getChessPiece();
                    if (piece != null) {
                        return true;
                    }
                } else if (result_board.isFieldOccupied(i, j) && result_board.getField()[i][j].getChessPiece().getName() == ChessPieceCharacteristics.Name.K && is_color_white == false && result_board.getField()[i][j].getChessPiece().getColor() == ChessPieceCharacteristics.Color.b) {
                    piece = result_board.getField()[i][j].getChessPiece();
                    if (piece != null) {
                        return true;
                    }
                }
            }
        }
        return false;
    }
    
    private void check_if_KingIsInCheck(int newRow, int newCol) {
        blackPieceList = new LinkedList<>();
        whitePieceList = new LinkedList<>();
        m = new miniMax();
        
        if ((board.isFieldOccupied(newRow, newCol) && board.getField()[newRow][newCol].getChessPiece().getColor() == ChessPieceCharacteristics.Color.b)
                || (board.isFieldOccupied(newRow, newCol) && board.getField()[newRow][newCol].getChessPiece().getName() == ChessPieceCharacteristics.Name.K && board.getField()[newRow][newCol].getChessPiece().getColor() == ChessPieceCharacteristics.Color.w)) {
            //the chesspiece that was just moved is of black color, color of king = white
            //OR the chesspiece that was just moved is the king and has white color
            king = new King(ChessPieceCharacteristics.Color.w, ChessPieceCharacteristics.Name.K);
            
            if (isKingAlive(king, board, true)) {
                if (king.isKingInCheck(king.get_Kings_position(board, m, true).getPiecePosition(), true, m.copyBoard(board), blackPieceList, whitePieceList)) {
                    System.out.println("White King is in check! King is under threat!");
                }
            }
        } else if ((board.isFieldOccupied(newRow, newCol) && board.getField()[newRow][newCol].getChessPiece().getColor() == ChessPieceCharacteristics.Color.w)
                || (board.isFieldOccupied(newRow, newCol) && board.getField()[newRow][newCol].getChessPiece().getName() == ChessPieceCharacteristics.Name.K && board.getField()[newRow][newCol].getChessPiece().getColor() == ChessPieceCharacteristics.Color.b)) {
            //the chesspiece that was just moved is of white color,color of king = black 
            //OR the chesspiece that was just moved is the king and has black color
            king = new King(ChessPieceCharacteristics.Color.b, ChessPieceCharacteristics.Name.K);
            if (isKingAlive(king, board, false)) {
                if (king.isKingInCheck(king.get_Kings_position(board, m, false).getPiecePosition(), false, m.copyBoard(board), blackPieceList, whitePieceList)) {
                    System.out.println("Black King is in check! King is under threat!");
                }
            }
        }
    }
    
    public boolean processMove(ChessMove move, Player player) {
        
        boolean validMove = false;
        int curRow = move.getCurrent().getRow();
        int curCol = move.getCurrent().getCol();
        
        Field startField = board.getField(curRow, curCol);
        
        int newRow = move.getNewPos().getRow();
        int newCol = move.getNewPos().getCol();
        Field endField = board.getField(newRow, newCol);
        
        if (move.getP() != null) {
            ChessPieceCharacteristics.Color color = move.getP().getColor();
            if (color == player.getPlayerColour()) {
                if (ChessMove.isValid(curRow, curCol) && ChessMove.isValid(newRow, newCol)) { //row and col number is valid

                    //castle the pieces if it's a castling move
                    if (board.getPieceType(move.getCurrent()) == ChessPieceCharacteristics.Name.K && board.getPieceType(move.getNewPos()) == ChessPieceCharacteristics.Name.R) { //chesspieces that are about to move are: king, rook
                        if (isKingAlive(king, board, true)) {
                            king = new King(startField.getChessPiece().getColor(), ChessPieceCharacteristics.Name.K);
                            if (king.isCastlingPossible(board, startField, endField)) {
                                
                                System.out.println("Player: Castling");
                                king.doCastling(board, startField, endField);
                            }
                        }
                    } else if (board.getPieceType(move.getCurrent()) == ChessPieceCharacteristics.Name.R && board.getPieceType(move.getNewPos()) == ChessPieceCharacteristics.Name.K) {  //chesspieces that are about to move are: king, rook
                        king = new King(endField.getChessPiece().getColor(), ChessPieceCharacteristics.Name.K);
                        if (isKingAlive(king, board, true)) {
                            if (king.isCastlingPossible(board, endField, startField)) {
                                
                                System.out.println("Player: Castling");
                                king.doCastling(board, endField, startField);
                            }
                        }
                    } else if (move.getP().isMovePossible(move, board)) {
                        validMove = true;
                        move.getP().makeMove(move, board);
                        player.setLastMove(move);
                        board.setLastMove(move);
                        check_if_KingIsInCheck(newRow, newCol);                            //check is king is in check, notify the other user 
                        board.printBoard();
                    }
                }
            }
        }
        return validMove;
    }
    
    public MoveResult ChessGame() {
        
        setBoard();
        whiteP.setTurn(true);
        blackP.setTurn(false);
        miniMax.setPlayer(blackP);
        bot = new miniMax(ChessPieceCharacteristics.Color.b, depth);
        
        while (true) {
            
            List<ChessMove> reccomendedMoves = new LinkedList();
            
            if (whiteP.isTurn()) {
                //check for check-mate, if yes game is over 
                king = new King(ChessPieceCharacteristics.Color.w, ChessPieceCharacteristics.Name.K);
                blackPieceList = new LinkedList<>();
                whitePieceList = new LinkedList<>();
                
                miniMax m = new miniMax();
                
                if (king.isCheckmate(king.get_Kings_position(board, m, true), true, board, whiteP, blackPieceList, whitePieceList)) {
                    System.out.println(MoveResult.CHECK_MATE + " : White LOST, Black WON");
                    return MoveResult.CHECK_MATE;
                }
                
                System.out.println("White's turn, please enter move: ");
                
                ChessMove wMove = readFromUser(whiteP);
                
                if (wMove != null) {
                    if (processMove(wMove, whiteP)) {
                        blackP.setTurn(true);
                        whiteP.setTurn(false);
                    } else {
                        System.out.println("Move not valid ");
                    }
                }
                
            } else {
                //check for check-mate, if yes game is over  
                king = new King(ChessPieceCharacteristics.Color.b, ChessPieceCharacteristics.Name.K);
                blackPieceList = new LinkedList<>();
                whitePieceList = new LinkedList<>();
                
                m = new miniMax();
                
                if (king.isCheckmate(king.get_Kings_position(board, m, false), false, board, blackP, blackPieceList, whitePieceList)) {
                    System.out.println(MoveResult.CHECK_MATE + " : Black LOST, White WON");
                    return MoveResult.CHECK_MATE;
                }
                //ai move 
                System.out.println("Black's turn, AI makes a move: ");
                ChessMove move = bot.getNextMove(board);
                
                if (move != null) {
                    processMove(move, blackP);
                }
                blackP.setTurn(false);
                whiteP.setTurn(true);
            }
        }
    }
}
