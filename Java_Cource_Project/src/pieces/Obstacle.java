package pieces;

import java.awt.*;

public class Obstacle extends Piece {

    public Obstacle() {

        this.setPieceId("obstacle");
        this.setHealthPoints(1);
    }

    @Override
    public void renderPiece(Graphics g) {

        int tileSize = 100;
        int tileX = this.getCol() * tileSize;
        int tileY = this.getRow() * tileSize;

        g.setColor(Color.BLACK);
        g.fillOval(tileX + 20, tileY + 20, tileSize - 40, tileSize- 40);
    }

    @Override
    public boolean isMoveInRange(int row, int col, Piece[][] pieceCollection) {
        return false;
    }

    @Override
    public boolean isAttackValid(int row, int col, Piece[][] pieceCollection) {
        return false;
    }
}
