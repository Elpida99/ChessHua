package algorithm;

import java.util.LinkedList;
import java.util.List;
import java.util.Scanner;

import model_board.Board;
import model_board.FieldCoordinates;
import model_chess_pieces.ChessPiece;
import model_chess_pieces.ChessPieceCharacteristics;

public class Game {

    private static Player whiteP = new Player(ChessPieceCharacteristics.Color.w, null);
    private static Player blackP = new Player(ChessPieceCharacteristics.Color.b, null);

    private static Board board = new Board();

    boolean playerTurn = true;
    miniMax bot; // AI engine
    int depth;

    public void setBoard() {
        board.createBoard();
        board.printBoard();
        System.out.println("Players: w=white and b=black");
        System.out.println("Pieces: K=king, Q=queen, N=knight, B=bishop, R=rook, P=pawn");
        System.out.println("Choose which piece you want to move like this: g7 h5 to move from g7 to h5");
        System.out.println("Or write -h for help with a recommended move");

    }

    public int returnColFromUser(String letter) { //returns the actual column that is assigned by the letters
        int column = 0;
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
        int row = 0;
        if (inputRow >= 0 && inputRow <= 8) {
            row = inputRow - 1;
        } else {
            row = -1; // in case the user inputs a row that does not exist
        }

        return row;
    }

    public char userCol(int column) { //returns column as letter
        char col = '0';
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

    public int userRow(int row) {
        int nrow = 0;
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

        if (inputTokens.length == 1) {
            if (inputTokens[0].equals("h")) {
                recommendMove(player.getPlayerColour());
            }
        } else if (inputTokens.length == 2) {
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
            }
        } else {
            System.out.println("Not valid input");
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

        ChessPieceCharacteristics.Color color = move.getP().getColor();

        if (ChessMove.isValid(curRow, curCol)) { //row and col number is valid

            //if current field is actually occupied and by the correct color
            if (board.isFieldOccupied(curRow, curCol) && (board.getField()[newRow][newCol].getChessPiece().getColor().toString().equals(color))) {

                //if target field is occupied by an enemy piece, remove it and take its place
                if (board.isFieldOccupied(newRow, newCol) && !(board.getField()[newRow][newCol].getChessPiece().getColor().toString().equals(color))) {

                    board.getField()[newRow][newCol].getChessPiece().setIsAlive(false);
                    board.removePiece(move.getNewPos());
                    if (move.getP().isMovePossible(move, board)) {

                        move.getP().makeMove(move, board);

                    } else {

                        System.out.println("Move not possible.");
                    }

                    //if target field is occupied by a same-color piece, move is not possible
                } else if (board.isFieldOccupied(curRow, curCol) && (board.getField()[newRow][newCol].getChessPiece().getColor().toString().equals(color))) {

                    System.out.println("Sorry, field is occupied by the same color piece. ");

                } else { //target field is not occupied

                    if (move.getP().isMovePossible(move, board)) {

                        move.getP().makeMove(move, board);

                    } else {

                        System.out.println("Move not possible.");
                    }

                }

            }
        } else {

            System.out.println("Coordinates do not exist.");

        }

    }

    public void ChessGame() {

        setBoard();
        whiteP.setTurn(true);
        blackP.setTurn(false);
        miniMax.setPlayer(blackP);
        bot = new miniMax(ChessPieceCharacteristics.Color.b, depth);

        while (true) {

            List<ChessMove> reccomendedMoves = new LinkedList();

            if (whiteP.isTurn()) {

                System.out.println("White's turn, please enter move: ");
                // = reccomendMove(ChessPieceCharacteristics.Color.w);
                //show List
                ChessMove wMove = new ChessMove();
                wMove = readFromUser(whiteP);
                
                wMove.getP().makeMove(wMove, board);
                blackP.setTurn(true);
                whiteP.setTurn(false);

                board.printBoard(); //reprints chess board

            } else {

                System.out.println("Black's turn, AI makes a move: ");
                ChessMove move = bot.getNextMove(board);
                processMove(move);
                blackP.setTurn(false);
                whiteP.setTurn(true);

            }

        }

        //readFromUser();
    }

}
