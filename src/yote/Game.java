package yote;

import yote.player.Player;

/**
 * Created by David et Monireh on 29/05/2017.
 */
public class Game {

    public interface Turn {
        public Player.Move play(Player currentPlayer, Board board);
    }

    private Board board = new Board();

    private Player[] players = new Player[2];

    private boolean player1Turn = true;

    private int turn = 0;

    public Game(Player player1, Player player2) {
        players[0] = player1;
        players[1] = player2;
    }

    public void startGame() {
        turn = 0;
        board.clear();
        player1Turn = true;
        for (Player player : players) {
            player.init();
        }
    }

    public boolean isOver() {
        return players[0].hasLost() || players[1].hasLost();
    }

    public Player currentPlayer() {
        return players[player1Turn ? 0 : 1];
    }

    public Player opponentPlayer() {
        return players[player1Turn ? 1 : 0];
    }

    public void play(Turn turn) {
        final Player.Move move = turn.play(currentPlayer(), board);
        board.playMove(currentPlayer(), opponentPlayer(), move);
        player1Turn = !player1Turn;
    }

    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder();
        builder.append(String.format("### TURN %d ###\n", ++turn));
        if(isOver()) {
            builder.append(String.format("Player %d has won", players[0].hasLost() ? 2 : 1));
        } else {
            builder.append(String.format("Player 1 has %d blots left\nPlayer 2 has %d blots left", players[0].blotsLeft(), players[1].blotsLeft()));
        }
        builder.append("\n");
        return builder.toString();
    }
}
