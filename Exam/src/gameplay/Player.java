package gameplay;

import pieces.*;

import java.util.ArrayList;

public class Player {

    private String playerId;
    private int magicEnergy;
    private ArrayList<Piece> playerPieceDeck;
    private ArrayList<Piece> playerHand;

    public Player(String playerId) {
        this.playerId = playerId;
        this.magicEnergy = 10;
        this.playerPieceDeck = new ArrayList<>();
        this.playerHand = new ArrayList<>();
        fillUpPlayerPieceDeck();
    }

    public String getPlayerId() {
        return playerId;
    }

    public void setPlayerId(String playerId) {
        this.playerId = playerId;
    }

    public int getMagicEnergy() {
        return magicEnergy;
    }

    public void setMagicEnergy(int magicEnergy) {
        this.magicEnergy = magicEnergy;
    }

    public ArrayList<Piece> getPlayerPieceDeck() {
        return playerPieceDeck;
    }

    public void setPlayerPieceDeck(ArrayList<Piece> playerPieceDeck) {
        this.playerPieceDeck = playerPieceDeck;
    }

    private void fillUpPlayerPieceDeck() {

        for (int i = 0; i < 3; i++) {

            getPlayerPieceDeck().add(new DogEatingBug());
            getPlayerPieceDeck().add(new DrunkenKnight());
            getPlayerPieceDeck().add(new MagicCat());
            getPlayerPieceDeck().add(new RecklessCannibal());
            getPlayerPieceDeck().add(new SandTurtle());
            getPlayerPieceDeck().add(new SophisticatedSam());
        }
    }
}
