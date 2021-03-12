package pieces;

import tiles.Tile;

import java.awt.*;

public abstract class Piece {

    private int row;
    private int col;
    private String pieceId;
    private String piecePlayerId;
    private int attackPoints;
    private int defensePoints;
    private int healthPoints;

    public int getAttackPoints() { return attackPoints; }

    public void setAttackPoints(int attackPoints) { this.attackPoints = attackPoints; }

    public int getDefensePoints() { return defensePoints; }

    public void setDefensePoints(int defensePoints) { this.defensePoints = defensePoints; }

    public int getHealthPoints() { return healthPoints; }

    public void setHealthPoints(int healthPoints) { this.healthPoints = healthPoints; }

    public int getRow() { return row; }

    public void setRow(int row) { this.row = row; }

    public int getCol() { return col; }

    public void setCol(int col) { this.col = col; }

    public String getPieceId() { return pieceId; }

    public void setPieceId(String pieceId) { this.pieceId = pieceId; }

    public String getPiecePlayerId() { return piecePlayerId; }

    public void setPiecePlayerId(String piecePlayerId) { this.piecePlayerId = piecePlayerId; }

    public abstract void renderPiece(Graphics g);

    public abstract boolean isMoveInRange(int row, int col, Piece[][] pieceCollection);

    public abstract boolean isAttackValid(int row, int col, Piece[][] pieceCollection);

    public void showAvailableMoves(Piece[][] pieceCollection, Tile[][] tileCollection) {

        for (int i = 0; i < tileCollection.length; i++) {
            for (int j = 0; j < tileCollection[i].length; j++) {

                if(isMoveInRange(i, j, pieceCollection)) {
                    tileCollection[i][j].setTileStateId("availableBoxToMoveTile");
                }
            }
        }
    }
}
