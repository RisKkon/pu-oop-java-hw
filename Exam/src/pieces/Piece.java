package pieces;

import java.awt.*;

public abstract class Piece {

    private int row;
    private int col;
    private String pieceId;
    private String piecePlayerId;
    private int attackPoints;
    private int defencePoints;
    private int magicPower;
    private int speed;

    public int getRow() {
        return row;
    }

    public void setRow(int row) {
        this.row = row;
    }

    public String getPiecePlayerId() {
        return piecePlayerId;
    }

    public void setPiecePlayerId(String piecePlayerId) {
        this.piecePlayerId = piecePlayerId;
    }

    public int getCol() {
        return col;
    }

    public void setCol(int col) {
        this.col = col;
    }

    public String getPieceId() {
        return pieceId;
    }

    public void addAttackPoints(int points) { this.attackPoints += points; }

    public void removeAttackPoints(int points) { this.attackPoints -= points; }

    public void addDefencePoints(int points) { this.defencePoints += points; }

    public void setPieceId(String pieceId) {
        this.pieceId = pieceId;
    }

    public int getAttackPoints() {
        return attackPoints;
    }

    public void setAttackPoints(int attackPoints) {
        this.attackPoints = attackPoints;
    }

    public int getDefencePoints() {
        return defencePoints;
    }

    public void setDefencePoints(int defencePoints) {
        this.defencePoints = defencePoints;
    }

    public int getMagicPower() {
        return magicPower;
    }

    public void setMagicPower(int magicPower) {
        this.magicPower = magicPower;
    }

    public int getSpeed() {
        return speed;
    }

    public void setSpeed(int speed) {
        this.speed = speed;
    }

    public abstract void renderPiece(Graphics g);
}
