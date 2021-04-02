import gameplay.GameBoard;
import ui.Renderer;

public class Application {

    public static void main(String[] args) {
        GameBoard gameBoard = new GameBoard();
        new Renderer(gameBoard);
    }
}
