package gameplay;

import pieces.Piece;

import java.util.ArrayList;

public class Player {

    private String playerId;
    private ArrayList<Piece> playerPieceCollection;


    public Player(String playerId) {

        this.playerId = playerId;
        this.playerPieceCollection = new ArrayList<>();
    }

    public String getPlayerId() { return playerId; }

    public ArrayList<Piece> getPlayerPieceCollection() { return playerPieceCollection; }
}
