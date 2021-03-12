package gameplay;

import pieces.Piece;

import java.util.ArrayList;

public class Player {

    private String playerId;
    private ArrayList<Piece> playerPieceCollection;
    private int playerPoints;
    private ArrayList<Piece> playerDeadPieces;


    public Player(String playerId) {

        this.playerId = playerId;
        this.playerPieceCollection = new ArrayList<>();
        this.playerDeadPieces = new ArrayList<>();
        setPlayerPoints(0);

    }

    public ArrayList<Piece> getPlayerDeadPieces() { return playerDeadPieces; }

    public int getPlayerPoints() {
        return playerPoints;
    }

    public void setPlayerPoints(int playerPoints) {
        this.playerPoints = playerPoints;
    }

    public String getPlayerId() { return playerId; }

    public ArrayList<Piece> getPlayerPieceCollection() { return playerPieceCollection; }

    public void addPlayerPoints(int points) { this.playerPoints += points; }
}
