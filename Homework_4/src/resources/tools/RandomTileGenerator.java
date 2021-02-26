package resources.tools;

import resources.gameboard.GameBoard;
import resources.gameboard.Player;
import resources.tiles.*;

public class RandomTileGenerator {

    public Tile[][] generateRandomGameBoard(GameBoard gameBoard) {

        Tile[][] tileCollection = new Tile[GameBoard.SIDE_SIZE][GameBoard.SIDE_SIZE];

        this.setStartingTile(gameBoard, tileCollection);
        this.setGPSTiles(tileCollection);
        this.setForbiddenTiles(tileCollection);
        this.setRemainingTiles(tileCollection);

        return tileCollection;

    }

    /**
     *Set's a random position for the player's starting tile.
     */
    private void setStartingTile(GameBoard gameBoard, Tile[][] tileCollection) {

        int[][] coordinates = new int[4][2];
        coordinates[0][0] = 0;
        coordinates[0][1] = 0;
        coordinates[1][0] = 0;
        coordinates[1][1] = GameBoard.SIDE_SIZE - 1;
        coordinates[2][0] = GameBoard.SIDE_SIZE - 1;
        coordinates[2][1] = 0;
        coordinates[3][0] = GameBoard.SIDE_SIZE - 1;
        coordinates[3][1] = GameBoard.SIDE_SIZE - 1;

        int num = RandomNumberGenerator.getRandomNum(4);
        int row = coordinates[num][0];
        int col = coordinates[num][1];

        tileCollection[row][col] = new StartPointTile(row, col, "startingTile");
        gameBoard.setPlayer(new Player(row, col));
    }

    /**
     *Spawns all the GPS tiles.
     */
    private void setGPSTiles(Tile[][] tileCollection) {

        for (int i = 0; i < 8; i++) {

            boolean taskDone = false;
            while(!taskDone) {

                int row = RandomNumberGenerator.getRandomNum(8);
                int col = RandomNumberGenerator.getRandomNum(8);

                if(tileCollection[row][col] == null) {

                    GPSTile currentTile = new GPSTile(row, col, "GPSTile");
                    if(i == 0) {
                        currentTile.setWitchHere(true);
                    }
                    tileCollection[row][col] = currentTile;
                    taskDone = true;
                }
            }
        }
    }

    /**
     *Spawns all the forbidden tiles.
     */
    private void setForbiddenTiles(Tile[][] tileCollection) {

        for (int i = 0; i < 5; i++) {

            boolean taskDone = false;
            while(!taskDone) {

                int row = RandomNumberGenerator.getRandomNum(8);
                int col = RandomNumberGenerator.getRandomNum(8);
                if(tileCollection[row][col] == null) {

                    tileCollection[row][col] = new ForbiddenTile(row, col, "forbiddenTile");
                    taskDone = true;
                }
            }
        }
    }

    /**
     *All the remaining tiles are turned into unknown tiles.
     */
    public void setRemainingTiles(Tile[][] tileCollection) {

        for (int i = 0; i < tileCollection.length; i++) {
            for (int j = 0; j < tileCollection[i].length; j++) {

                if(tileCollection[i][j] == null) {

                    tileCollection[i][j] = new UnknownTile(i, j, "unknownTile");
                }
            }
        }
    }
}
