package yote.player;

import yote.Board;

/**
 * Created by David et Monireh on 15/11/2016.
 */
public interface Playable{
    public Player.Move play(Board board);

    public void init();
}
