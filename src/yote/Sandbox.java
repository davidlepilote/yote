package yote;

/**
 * Created by David et Monireh on 15/11/2016.
 */
public class Sandbox {

    public static void main(String[] args){

        final Board board = new Board();
        final Player white = new Player(Board.Blot.BlotColor.WHITE);
        final Player black = new Player(Board.Blot.BlotColor.BLACK);

        boolean whiteTurn = true;
        while(!white.legalMoves(board).isEmpty() && !black.legalMoves(board).isEmpty()){
            final Player currentPlayer = whiteTurn ? white : black;
            final Player otherPlayer = whiteTurn ? black : white;
            whiteTurn = !whiteTurn;
            final Player.Move move = currentPlayer.play(board);
            board.playMove(currentPlayer, otherPlayer, move);
            int i = 0;
        }
        int i = 0;
    }
}
