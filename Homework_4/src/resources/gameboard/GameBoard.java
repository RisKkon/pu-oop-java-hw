package resources.gameboard;

import resources.tiles.ForbiddenTile;
import resources.tools.RandomNumberGenerator;
import resources.tools.RandomTileGenerator;
import resources.tiles.Tile;

import java.awt.*;

public class GameBoard {

    public final static int SIDE_SIZE = 8;
    public Tile[][] tileCollection;
    private Player player;
    private boolean isGameOver = false;


    /**
     * The constructor starts up the random tile generator.
     */
    public GameBoard() {

        RandomTileGenerator tileGenerator = new RandomTileGenerator();
        this.tileCollection = tileGenerator.generateRandomGameBoard(this);
    }

    public Tile[][] getTileCollection() {
        return tileCollection;
    }

    public Player getPlayer() { return player; }

    public void setPlayer(Player player) { this.player = player; }

    /**
     * Checks if given coordinates match with the player's position.
     */
    public boolean doesCoordinatesMathWithPlayer(int row, int col) {

        return this.getPlayer().getRow() == row && this.getPlayer().getCol() == col;
    }

    /**
     * Takes row and col and checks if the selected position
     * is a valid move.
     */
    public boolean isMoveValid(int NewRow, int NewCol) {

        int rowCoefficient = NewRow - this.getPlayer().getRow();
        int colCoefficient = NewCol - this.getPlayer().getCol();

        boolean isActiveByRow = (rowCoefficient == 1 || rowCoefficient == -1)
                && colCoefficient == 0;
        boolean isActiveByCol = (colCoefficient == 1 || colCoefficient == -1)
                && rowCoefficient == 0;
        boolean isGoingOnDiagonal = (rowCoefficient == -1 || rowCoefficient == 1)
                && (colCoefficient == -1 || colCoefficient == 1);

        return isActiveByCol || isActiveByRow || isGoingOnDiagonal;
    }

    public boolean isGameOver() {
        return isGameOver;
    }

    public void setGameOver(boolean gameOver) {
        isGameOver = gameOver;
    }

    /**
     *Changes the position of the player on the gameBoard.
     */
    public void movePlayer(int newRow, int newCol) {

        //If the selected tile is of the type "unknownTile", it executes a different
        //method than the one for a normal tile.
        if(this.checkIfSelectedTileMatches(newRow, newCol, "unknownTile")) {

            this.moveToUnknownTile(newRow, newCol);
        } else {
            this.moveToGPSTile(newRow, newCol);
        }

    }

    /**
     *Checks if a certain tile matches another.
     */
    public boolean checkIfSelectedTileMatches(int row, int col, String tileId) {

        return this.getTileCollection()[row][col].getId().equals(tileId);
    }

    /**
     *At the end of the move, checks if the player is surrounded
     * by forbidden tiles and cannot execute another move.
     */
    public boolean isPlayerSurrounded() {

        int playerRow = this.getPlayer().getRow();
        int playerCol = this.getPlayer().getCol();
        int trapCount = 0;

        trapCount = this.checkTile(playerRow, playerCol + 1, trapCount);
        trapCount = this.checkTile(playerRow, playerCol - 1, trapCount);
        trapCount = this.checkTile(playerRow + 1, playerCol, trapCount);
        trapCount = this.checkTile(playerRow - 1, playerCol, trapCount);
        trapCount = this.checkTile(playerRow + 1, playerCol + 1, trapCount);
        trapCount = this.checkTile(playerRow - 1, playerCol - 1, trapCount);
        trapCount = this.checkTile(playerRow + 1, playerCol - 1, trapCount);
        trapCount = this.checkTile(playerRow - 1, playerCol + 1, trapCount);

        return trapCount == 8;
    }

    /**
     *This method helps the one above, checks if the tile is forbidden, or if
     *the coordinates lead out of the gameBoard.
     */
    private int checkTile(int row, int col, int trapCount) {

        try {
            if(this.checkIfSelectedTileMatches(row, col, "forbiddenTile")) {
                trapCount++;
            }

        } catch (Exception ignored) {
            trapCount++;
        }
        return trapCount;
    }

    /**
     *Executes a move to a unknown tile,
     * 20% chance to spawn a forbidden tile.
     */
    private void moveToUnknownTile(int newRow, int newCol) {

        int chanceNum = RandomNumberGenerator.getRandomNum(6);
        if(chanceNum == 3) {

            this.getTileCollection()[newRow][newCol] = new ForbiddenTile(newRow, newCol, "forbiddenTile");
        } else {

            this.getTileCollection()[newRow][newCol].setColor(Color.decode("#f2ff00"));
            this.getPlayer().setRow(newRow);
            this.getPlayer().setCol(newCol);
        }
    }

    /**
     *Executes a move to a gps tile.
     */
    private void moveToGPSTile(int newRow, int newCol) {

        if(getTileCollection()[newRow][newCol].isWitchHere()) {

            this.setGameOver(true);
        } else {

            this.getTileCollection()[newRow][newCol].setColor(Color.decode("#f2ff00"));
            this.getPlayer().setRow(newRow);
            this.getPlayer().setCol(newCol);
        }
    }
}
