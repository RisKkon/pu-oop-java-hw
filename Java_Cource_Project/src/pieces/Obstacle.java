package pieces;

import java.awt.*;

public class Obstacle extends Piece {

    public Obstacle() {

        this.setPieceId("obstacle");
    }

    @Override
    public void renderPiece(Graphics g) {

        int tileSize = 100;
        int tileX = this.getCol() * tileSize;
        int tileY = this.getRow() * tileSize;

        g.setColor(Color.BLACK);
        g.fillRect(tileX, tileY, tileSize, tileSize);
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
