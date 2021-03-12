package ui;

import gameplay.GameBoard;
import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

public class Render extends JFrame implements MouseListener {

    private GameBoard gameBoard;
    private boolean isGameSetupStageComplete;
    private boolean isPieceSelected;
    private boolean didMoveFail;
    private boolean wasMoveSuccessful;
    private boolean isGameOver;

    public Render(GameBoard gameboard) {

        this.gameBoard = gameboard;
        this.isGameSetupStageComplete = false;
        this.isPieceSelected = false;
        this.didMoveFail = false;
        this.wasMoveSuccessful = false;
        this.isGameOver = false;
        setSize(900, 700);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        addMouseListener(this);
        setVisible(true);
    }

    public void setGameOver(boolean gameOver) { isGameOver = gameOver; }

    public boolean isWasMoveSuccessful() { return wasMoveSuccessful; }

    public void setWasMoveSuccessful(boolean wasMoveSuccessful) { this.wasMoveSuccessful = wasMoveSuccessful; }

    public boolean isDidMoveFail() { return didMoveFail; }

    public void setDidMoveFail(boolean didMoveFail) { this.didMoveFail = didMoveFail; }

    public GameBoard getGameBoard() { return gameBoard; }

    public boolean isGameSetupStageComplete() { return isGameSetupStageComplete; }

    public void setGameSetupStageComplete(boolean gameSetupStageComplete) { this.isGameSetupStageComplete = gameSetupStageComplete; }

    public boolean isPieceSelected() { return isPieceSelected; }

    public void setPieceSelected(boolean pieceSelected) { isPieceSelected = pieceSelected; }

    @Override
    public void paint(Graphics g) {
        super.paint(g);

        //Paint's all the tiles and pieces.
        for (int i = 0; i < getGameBoard().getTileCollection().length; i++) {
            for (int j = 0; j < getGameBoard().getTileCollection()[i].length; j++) {

                getGameBoard().getTileCollection()[i][j].renderTile(g);

                if(getGameBoard().getPieceCollection()[i][j] != null) {
                    getGameBoard().getPieceCollection()[i][j].renderPiece(g);
                }
            }
        }
    }

    @Override
    public void mouseClicked(MouseEvent e) {

        int row = getLocationBasedOnCoordinates(e.getY());
        int col = getLocationBasedOnCoordinates(e.getX());

        if (isSetupCompleteAndPieceSelected()) {

            moveExecutionStage(row, col);

        }
        if (isSetupCompleteAndPieceNotSelected()) {

            pieceSelectionStage(row, col);
        }
        setupStage(row, col);
        checkIfMoveFailed();
        checkIfMoveWasSuccessful();
        printInfoToConsole();
        if(checkIfGameIsOver() && this.isGameSetupStageComplete) {
            gameOverMessage();
            System.exit(1);
        }
    }

    @Override
    public void mousePressed(MouseEvent e) {

    }

    @Override
    public void mouseReleased(MouseEvent e) {

    }

    @Override
    public void mouseEntered(MouseEvent e) {

    }

    @Override
    public void mouseExited(MouseEvent e) {

    }

    /**
     * Returns the location in a usable form.
     * @param coordinates
     * @return
     */
    private int getLocationBasedOnCoordinates(int coordinates) {

        return coordinates / 100;
    }

    private void increaseRoundCounter() {

        if(this.isGameSetupStageComplete) {

            getGameBoard().roundCounter++;
        }
    }

    private boolean isGameSetupOver() {

         return getGameBoard().getPlayerA().getPlayerPieceCollection().size() == 0 &&
                getGameBoard().getPlayerB().getPlayerPieceCollection().size() == 0;
    }

    private boolean isSetupCompleteAndPieceSelected() {

        return isGameSetupOver() && isPieceSelected();
    }

    private void checkIfPlayerHasNoPieces() {

        if(getGameBoard().isGameOver() && isGameSetupOver()) {
           setGameOver(true);
        }
    }

    private boolean checkIfGameIsOver() {

        checkIfPlayerHasNoPieces();
        return this.isGameOver;
    }

    private void checkIfMoveWasSuccessful() {

        if(isWasMoveSuccessful()) {
            getGameBoard().switchPlayerOnTurn();
            setWasMoveSuccessful(false);
        }
    }

    private void checkIfMoveFailed() {

        if(isDidMoveFail()) {

            setDidMoveFail(false);
        }
    }

    private boolean doesPlayerWantToAttackEnemyPiece(int row, int col) {

        return !getGameBoard().getPieceCollection()[row][col].getPiecePlayerId().equals(
                getGameBoard().getPlayerOnTurn().getPlayerId());
    }

    private boolean doesPlayerWantToHealAPiece(int row, int col) {

        return getGameBoard().getSelectedPiece().getRow() == row &&
               getGameBoard().getSelectedPiece().getCol() == col;
    }

    private boolean doesPlayerWantToBreakObstacle(int row, int col) {

        return getGameBoard().getPieceCollection()[row][col].getPieceId().equals("obstacle");
    }

    private boolean isThereIsAPieceInBox(int row, int col) {

        return getGameBoard().isThereAPieceHere(row, col) && !isWasMoveSuccessful();

    }

    private boolean isSetupCompleteAndPieceNotSelected() {

        return isGameSetupStageComplete() && !isPieceSelected() && !isDidMoveFail() && !isWasMoveSuccessful();
    }

    /**
     * Executes a move on the board.
     * @param row
     * @param col
     */
    private void executeMoveOnBoard(int row, int col) {

        if(getGameBoard().getSelectedPiece().isMoveInRange(row,col, getGameBoard().getPieceCollection())) {

            getGameBoard().executeMove(row, col);
            setPieceSelected(false);
            setWasMoveSuccessful(true);

        } else {

            new Modal(this, "Invalid move!",
                    "You are attempting an invalid move, try again", 400, 100);
            getGameBoard().setSelectedPiece(null);
            setPieceSelected(false);
            setWasMoveSuccessful(false);
            setDidMoveFail(true);
            getGameBoard().roundCounter--;
        }
        repaint();
    }

    /**
     * Executes an attack on the board.
     * @param row
     * @param col
     */
    private void executeAttackOnBoard(int row, int col) {

        if (getGameBoard().getSelectedPiece().isAttackValid(row, col, getGameBoard().getPieceCollection())) {
            //If attack is valid.
            getGameBoard().executeAttack(row, col);
            getGameBoard().removeDeadPieces();
            getGameBoard().setSelectedPiece(null);
            setPieceSelected(false);
            setWasMoveSuccessful(true);
        } else {
            //If attack is not valid
            new Modal(this, "Invalid attack!",
                    "You are attempting an invalid attack try again", 400, 100);
            setPieceSelected(false);
            getGameBoard().roundCounter--;
        }
        repaint();
    }

    /**
     * Executes healing of a piece.
     * @param row
     * @param col
     */
    private void executeHealOnBoard(int row, int col) {

        getGameBoard().executeHeal(row, col);
        int diceNum = getGameBoard().trowDice(1, 100);
        if(diceNum % 2 != 0) {

            new Modal(this, "Lucky", "Player " + getGameBoard()
                    .getPlayerOnTurn().getPlayerId().toUpperCase() + " got lucky, go again", 400, 100);
            getGameBoard().switchPlayerOnTurn();
        }
        repaint();
        setWasMoveSuccessful(true);
        setPieceSelected(false);
    }

    /**
     * Selects a piece.
     * @param row
     * @param col
     */
    private void selectPiece(int row, int col) {

        getGameBoard().setSelectedPiece(getGameBoard().getPieceCollection()[row][col]);
        getGameBoard().getSelectedPiece()
                .showAvailableMoves(getGameBoard().getPieceCollection(), getGameBoard().getTileCollection());
        getGameBoard().getSelectedPieceTile();
        setPieceSelected(true);
        repaint();
    }

    /**
     * Starts up the game setup round.
     * @param row
     * @param col
     */
    private void gameSetupRound(int row, int col) {

        getGameBoard().showAvailableTiles();
        if(getGameBoard().isPlacementOnValidSide(row) && !getGameBoard().isThereAPieceHere(row, col)) {

            getGameBoard().executeInitialPlacementOnBoard(row, col, getGameBoard().getPieceCollection());
            repaint();
            getGameBoard().switchPlayerOnTurn();
            if (isGameSetupOver()) {
                setGameSetupStageComplete(true);
                getGameBoard().setAllTilesToNormal();
            }

        } else {
            new Modal(this, "Invalid placement",
                    "You can't place a piece here, try again", 400, 100);
        }
    }

    private void setupStage(int row, int col) {

        if(!isGameSetupStageComplete()) {

            gameSetupRound(row, col);
        }
    }

    /**
     * Starts up the piece selection stage.
     * @param row
     * @param col
     */
    private void pieceSelectionStage(int row, int col) {

        if(getGameBoard().isSelectedPieceValid(row, col, getGameBoard().getPieceCollection())) {
           selectPiece(row, col);
        } else {
            new Modal(this, "Wrong piece", "Selected piece is not yours", 400, 100);
        }
    }

    /**
     * Starts up the piece moving stage.
     * @param row
     * @param col
     */
    private void moveExecutionStage(int row, int col) {

        getGameBoard().setAllTilesToNormal();
        if(!isThereIsAPieceInBox(row, col)) {

            executeMoveOnBoard(row, col);
            increaseRoundCounter();
        }
        if(isThereIsAPieceInBox(row, col)) {

            if(doesPlayerWantToBreakObstacle(row, col)) {

                executeAttackOnBoard(row, col);
                increaseRoundCounter();

            } else if(doesPlayerWantToAttackEnemyPiece(row, col)) {

                increaseRoundCounter();
                executeAttackOnBoard(row, col);
            } else {
                if (doesPlayerWantToHealAPiece(row, col)) {
                    executeHealOnBoard(row, col);
                    increaseRoundCounter();
                } else {

                    new Modal(this, "Invalid heal", "Invalid healing attempt, try again",400, 100 );
                    setPieceSelected(false);
                    setDidMoveFail(true);
                    repaint();
                }
            }
        }
    }

    /**
     * Prints the game over message.
     */
    private void gameOverMessage() {

        System.out.println();
        System.out.println("Game Over!!        rounds player: " + getGameBoard()
                                                                 .getRoundCounter());
        System.out.println("Game winner is player" + getGameBoard().getPlayerWinner()
                                                     .getPlayerId().toUpperCase());
        System.out.print("Player B's taken pieces: ");
        getGameBoard().getPlayerA().getPlayerDeadPieces().forEach(e -> System.out.print(e.getPieceId() + " "));
        System.out.println();
        System.out.print("Player A's taken pieces: ");
        System.out.println();
        getGameBoard().getPlayerB().getPlayerDeadPieces().forEach(e -> System.out.print(e.getPieceId() + " "));
        System.out.println("Player A's points: " + getGameBoard().getPlayerA().getPlayerPoints());
        System.out.println("Player B's points: " + getGameBoard().getPlayerB().getPlayerPoints());
    }

    /**
     * Prints info for game state at every round.
     */
    private void printInfoToConsole() {

        System.out.println("==================================================");
        System.out.println("Player a points: " + getGameBoard().getPlayerA().getPlayerPoints()
                + "             round: " + getGameBoard().getRoundCounter());
        System.out.println("Player b points: " + getGameBoard().getPlayerB().getPlayerPoints());
        System.out.println("Player " + getGameBoard().getPlayerOnTurn().getPlayerId().toUpperCase() + " on turn");
        System.out.println("==================================================");

        if(!isGameSetupOver()) {
            System.out.println("Game setup stage");
        }

        if(isSetupCompleteAndPieceNotSelected()) {
            System.out.println("Select piece");
        }
        if(isSetupCompleteAndPieceSelected()) {

            System.out.println("Piece selected on row: " + getGameBoard().getSelectedPiece().getRow()
                    + " and on col: " + getGameBoard().getSelectedPiece().getCol());
        }
    }
}
