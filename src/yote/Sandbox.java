package yote;

import yote.player.Player;
import yote.player.RandomPlayer;

/**
 * Created by David et Monireh on 15/11/2016.
 */
public class Sandbox {

    public static void main(String[] args){

        final Player white = new RandomPlayer(Board.Blot.BlotColor.WHITE);
        final Player black = new RandomPlayer(Board.Blot.BlotColor.BLACK);

        final Game game = new Game(white, black);

        game.startGame();

        while (!game.isOver()) {
            System.out.println(game.toString());
            game.play((currentPlayer, board) -> currentPlayer.play(board));
        }
        System.out.println(game.toString());
    }
}
