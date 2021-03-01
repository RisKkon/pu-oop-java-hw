package gameplay;

import pieces.Piece;

import java.util.ArrayList;

public class Player {

    private String playerId;
    private ArrayList<Piece> playerPieceCollection;
    private int playerPoints;


    public Player(String playerId) {

        this.playerId = playerId;
        this.playerPieceCollection = new ArrayList<>();
        this.setPlayerPoints(0);
    }

    public int getPlayerPoints() {
        return playerPoints;
    }

    public void setPlayerPoints(int playerPoints) {
        this.playerPoints = playerPoints;
    }

    public String getPlayerId() { return playerId; }

    public ArrayList<Piece> getPlayerPieceCollection() { return playerPieceCollection; }
}
