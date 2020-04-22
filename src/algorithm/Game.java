package algorithm;

import java.util.LinkedList;
import java.util.List;
import java.util.Scanner;

import model_board.Board;
import model_chess_pieces.ChessPiece;
import model_chess_pieces.ChessPieceCharacteristics;
import model_chess_pieces.King;

/**
 *
 * @author it21735 , it21754, it21762
 */
public class Game {

    private static Player whiteP = new Player(ChessPieceCharacteristics.Color.w, null);
    private static Player blackP = new Player(ChessPieceCharacteristics.Color.b, null);

    private Board board = new Board();

    boolean playerTurn = true;
    int depth;
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
        System.out.println("Or write -h for help with a recommended move");

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

    public ChessMove readFromUser(Player player) {
        ChessMove userMove = new ChessMove();
        Scanner input = new Scanner(System.in);

        String userInput = input.nextLine();
        userInput = userInput.trim();
        userInput = userInput + " ";
        String[] inputTokens = userInput.split(" ");

        switch (inputTokens.length) {
            case 1:
                if (inputTokens[0].equals("h")) {
                    recommendMove(player.getPlayerColour());
                    userMove = null;
                    break;
                } else {
                    System.out.println("Not valid input");
                    userMove = null;
                    break;
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

                    int curRow = returnRowFromUser(Integer.parseInt(curTokens[1]));
                    int curCol = returnColFromUser(curTokens[0]);
                    userMove.setCurCoor(curRow, curCol);

                    userMove.setP(board.getField()[curRow][curCol].getChessPiece());

                    System.out.println(
                            "currow is: (" + curTokens[1] + ")" + curRow + " curcol is: (" + curTokens[0] + ")" + curCol);

                    int newRow = returnRowFromUser(Integer.parseInt(newTokens[1]));
                    int newCol = returnColFromUser(newTokens[0]);
                    userMove.setNewCoor(newRow, newCol);
                    System.out.println(
                            "newrow is: (" + newTokens[1] + ")" + newRow + " newcol is: (" + newTokens[0] + ")" + newCol);

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
            System.out.print(" >>" + curPos + "-->" + newPos + " ");
            if (counter % 2 == 0) {
                System.out.print("\n");
            }

            //at the moment it returns all possible moves
        }
    }

    public void processMove(ChessMove move) { //to douleuw twra auto -> This could be a solution (?)

        int curRow = move.getCurrent().getRow();
        int curCol = move.getCurrent().getCol();

        int newRow = move.getNewPos().getRow();
        int newCol = move.getNewPos().getCol();

        if (move.getP() != null) {
            ChessPieceCharacteristics.Color color = move.getP().getColor();

            if (ChessMove.isValid(curRow, curCol)) { //row and col number is valid

                //if current field is actually occupied and by the correct color
                if (board.isFieldOccupied(curRow, curCol) && (board.getField()[curRow][curCol].getChessPiece().getColor().equals(color))) {

                    //if target field is occupied by an enemy piece, remove it and take its place
                    if (board.isFieldOccupied(newRow, newCol) && !(board.getField()[newRow][newCol].getChessPiece().getColor().equals(color))) {

                        board.getField()[newRow][newCol].getChessPiece().setIsAlive(false);
                        board.removePiece(move.getNewPos());
                        if (move.getP().isMovePossible(move, board)) {

                            move.getP().makeMove(move, board);
                                                      //check is king is in check, notify the other user 
/*                        blackPieceList = new LinkedList<>();
                        whitePieceList = new LinkedList<>();
                        m = new miniMax();
                        if (board.isFieldOccupied(newRow, newCol) && board.getField()[newRow][newCol].getChessPiece().getName() == ChessPieceCharacteristics.Name.K  && board.getField()[newRow][newCol].getChessPiece().getColor() == ChessPieceCharacteristics.Color.w) {
                                //color of king = white
                                king = new King(ChessPieceCharacteristics.Color.w, ChessPieceCharacteristics.Name.K);
                                if ( king.isKingInCheck(king.get_Kings_position(board,m).getPiecePosition() , true, m.copyBoard(board), blackPieceList, whitePieceList ))
                                    System.out.println("White King is in check! King is under threat!");

                        }else if (board.isFieldOccupied(newRow, newCol) && board.getField()[newRow][newCol].getChessPiece().getName() == ChessPieceCharacteristics.Name.K  && board.getField()[newRow][newCol].getChessPiece().getColor() == ChessPieceCharacteristics.Color.b) {
                                //color of king = black
                                king = new King(ChessPieceCharacteristics.Color.w, ChessPieceCharacteristics.Name.K);
                                if ( king.isKingInCheck(king.get_Kings_position(board,m).getPiecePosition() , false, m.copyBoard(board), blackPieceList, whitePieceList ))
                                   System.out.println("Black King is in check! King is under threat!");
                        }
*/
                        } else {

                            System.out.println("Move not possible.");
                        }

                        //if target field is occupied by a same-color piece, move is not possible
                    } else if (board.isFieldOccupied(newRow, newCol) && (board.getField()[newRow][newCol].getChessPiece().getColor().equals(color))) {

                        System.out.println("Sorry, field is occupied by the same color piece. ");

                    } else { //target field is not occupied

                        if (move.getP().isMovePossible(move, board)) {

                            move.getP().makeMove(move, board);
                            board.printBoard();
                                                      //check is king is in check, notify the other user 
/*                        blackPieceList = new LinkedList<>();
                        whitePieceList = new LinkedList<>();
                        m = new miniMax();
                        if (board.isFieldOccupied(newRow, newCol) && board.getField()[newRow][newCol].getChessPiece().getName() == ChessPieceCharacteristics.Name.K  && board.getField()[newRow][newCol].getChessPiece().getColor() == ChessPieceCharacteristics.Color.w) {
                                //color of king = white
                                king = new King(ChessPieceCharacteristics.Color.w, ChessPieceCharacteristics.Name.K);
                                if ( king.isKingInCheck(king.get_Kings_position(board,m).getPiecePosition() , true, m.copyBoard(board), blackPieceList, whitePieceList ))
                                    System.out.println("White King is in check! King is under threat!");

                        }else if (board.isFieldOccupied(newRow, newCol) && board.getField()[newRow][newCol].getChessPiece().getName() == ChessPieceCharacteristics.Name.K  && board.getField()[newRow][newCol].getChessPiece().getColor() == ChessPieceCharacteristics.Color.b) {
                                //color of king = black
                                king = new King(ChessPieceCharacteristics.Color.w, ChessPieceCharacteristics.Name.K);
                                if ( king.isKingInCheck(king.get_Kings_position(board,m).getPiecePosition() , false, m.copyBoard(board), blackPieceList, whitePieceList ))
                                   System.out.println("Black King is in check! King is under threat!");
                        }
*/
                        } else {

                            System.out.println("Move not possible.");
                        }

                    }

                }
            } else {

                System.out.println("Coordinates do not exist.");

            }
        } else {
            System.out.println("Sorry, this field is not occupied by a piece");
        }

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

//                if (king.isCheckmate(king.get_Kings_position(board, m), true, m.copyBoard(board), whiteP, blackPieceList, whitePieceList)) {
//                    //if( king.isCheckmate(ChessPiece king, boolean isWhite, Board board, Player player, List<ChessMove> blackPieceList,List <ChessMove> whitePieceList) ){
//                    System.out.println(MoveResult.CHECK_MATE + " : White LOST, Black WON");
//                    return MoveResult.CHECK_MATE;
//                } else {
//                    System.out.println("NOT A:  " + MoveResult.CHECK_MATE);
//                }
                System.out.println("White's turn, please enter move: ");
                // = reccomendMove(ChessPieceCharacteristics.Color.w);
                //show List
                ChessMove wMove = new ChessMove();
                wMove = readFromUser(whiteP);

                // wMove.getP().makeMove(wMove, board);
                if (wMove != null) {
                    processMove(wMove);
                    blackP.setTurn(true);
                    whiteP.setTurn(false);
                }

               

            } else {
                //check for check-mate, if yes game is over  
                king = new King(ChessPieceCharacteristics.Color.b, ChessPieceCharacteristics.Name.K);
                blackPieceList = new LinkedList<>();
                whitePieceList = new LinkedList<>();

                //  board = new Board();
                m = new miniMax();

                if (king.isCheckmate(king.get_Kings_position(board, m), false, m.copyBoard(board), blackP, blackPieceList, whitePieceList)) {
                    System.out.println(MoveResult.CHECK_MATE + " : Black LOST, White WON");
                    return MoveResult.CHECK_MATE;
                } else {
                    System.out.println("NOT A:  " + MoveResult.CHECK_MATE);
                }

                System.out.println("Black's turn, AI makes a move: ");
                ChessMove move = bot.getNextMove(board);
                if (move != null) {
                    processMove(move);
                }
                blackP.setTurn(false);
                whiteP.setTurn(true);

            }

        }

        //readFromUser();
    }
}
