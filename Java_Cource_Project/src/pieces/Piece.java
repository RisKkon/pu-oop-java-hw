package pieces;

import java.awt.*;

public abstract class Piece {

    private int row;
    private int col;
    private String pieceId;
    private String piecePlayerId;

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
}
