import gameplay.GameBoard;
import ui.Render;

public class Application {

    public static void main(String[] args) {
        GameBoard gameboard = new GameBoard();
        new Render(gameboard);

    }
}
