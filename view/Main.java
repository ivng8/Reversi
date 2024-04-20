package cs3500.reversi.view;

import java.util.Objects;

import cs3500.reversi.controller.ReversiController;
import cs3500.reversi.controller.ReversiControllerMVC;
import cs3500.reversi.model.Reversi;
import cs3500.reversi.model.ReversiModel;
import cs3500.reversi.model.SquareReversi;
import cs3500.reversi.player.HumanPlayer;
import cs3500.reversi.player.MachinePlayer;
import cs3500.reversi.player.Player;
import cs3500.reversi.strategy.AvoidSecondRing;
import cs3500.reversi.strategy.CaptureMostPieces;
import cs3500.reversi.strategy.PrioritizeCorners;
import cs3500.reversi.strategy.TwoStrategy;
import cs3500.reversi.strategy.square.AvoidSquareRings;
import cs3500.reversi.strategy.square.PrioritizeSquareCorners;

/**
 * Serves as the entry point and initialization hub for the Reversi game application.
 * This class orchestrates the setup of the game model, views, and controllers based on
 * command-line arguments provided by the user. It supports different game board types
 * and player configurations.
 */
public final class Main {


  /**
   * The main method that serves as the entry point of the Reversi game application.
   * It interprets the command-line arguments to configure and initialize the game model,
   * views, and players. This method sets up the game environment based on the specified
   * board type (e.g., square or hexagon) and player types (human or various AI strategies).
   *
   * @param args Command-line arguments used to configure the game.
   *             Expected format: [board size] [board type] [player1 type] [player2 type]
   */
  public static void main(String[] args) {
    ReversiModel model;
    if (Objects.equals(args[1], "square")) {
      model = new SquareReversi(Integer.parseInt(args[0]));
    } else if (Objects.equals(args[1], "hexagon")) {
      model = new Reversi(Integer.parseInt(args[0]));
    } else {
      throw new IllegalArgumentException("Invalid game size : " + args[0]);
    }

    IView viewPlayer1 = new ReversiGraphicsView(model, args[1]);
    IView viewPlayer2 = new ReversiGraphicsView(model, args[1]);
    Player player1 = createPlayer(args[2], model, args[1]);
    Player player2 = createPlayer(args[3], model, args[1]);
    ReversiController controller1 = new ReversiControllerMVC(model, player1, viewPlayer1);
    ReversiController controller2 = new ReversiControllerMVC(model, player2, viewPlayer2);
    model.startGame();
  }

  /**
   * Creates and returns a player based on the specified type and game model.
   * This method is responsible for configuring players as human or AI with specific
   * strategies, tailored to the game type (square or hexagon).
   *
   * @param playerArg The type of player to create (e.g., 'human', 'strategy1').
   * @param model     The game model that the player will interact with.
   * @param gameType  The type of game (e.g., 'square', 'hexagon') determining player strategies.
   * @return A configured {@link Player} instance.
   */
  private static Player createPlayer(String playerArg, ReversiModel model, String gameType) {
    if (playerArg.equalsIgnoreCase("human")) {
      return new HumanPlayer(model);

    } else if (gameType.equalsIgnoreCase("square")) {
      return createPlayerSquare(playerArg, model);
    } else if (gameType.equalsIgnoreCase("hexagon")) {
      return createPlayerHex(playerArg, model);
    } else {
      throw new IllegalArgumentException("Invalid gametype: " + gameType);
    }
  }

  /**
   * Creates a player for the square version of the Reversi game based on the specified strategy.
   * This method configures AI players with strategies specifically designed for a square game
   * board. Different strategies can be selected based on the argument provided.
   *
   * @param playerArg A string representing the chosen strategy for the AI player.
   * @param model     The game model to which the player will be associated.
   * @return A {@link Player} configured with the selected strategy for a square game board.
   * @throws IllegalArgumentException If the player argument doesnt correspond to a valid strategy.
   */
  private static Player createPlayerSquare(String playerArg, ReversiModel model) {
    switch (playerArg.toLowerCase()) {
      case "strategy1":
        return new MachinePlayer(model, new CaptureMostPieces());
      case "strategy2":
        return new MachinePlayer(model, new TwoStrategy(new AvoidSquareRings(),
                new CaptureMostPieces()));
      case "strategy3":
        return new MachinePlayer(model, new TwoStrategy(new TwoStrategy(new AvoidSquareRings(),
                new PrioritizeSquareCorners()), new CaptureMostPieces()));
      default:
        throw new IllegalArgumentException("Invalid player argument " + playerArg);
    }
  }

  /**
   * Creates a player for the hexagon version of the Reversi game based on the specified strategy.
   * This method configures AI players with strategies specifically designed for a hexagonal game
   * board. Different strategies can be selected based on the argument provided.
   *
   * @param playerArg A string representing the chosen strategy for the AI player.
   * @param model     The game model to which the player will be associated.
   * @return A {@link Player} configured with the selected strategy for a hexagon game board.
   * @throws IllegalArgumentException If the player argument doesnt correspond to a valid strategy.
   */
  private static Player createPlayerHex(String playerArg, ReversiModel model) {
    switch (playerArg.toLowerCase()) {
      case "strategy1":
        return new MachinePlayer(model, new CaptureMostPieces());
      case "strategy2":
        return new MachinePlayer(model, new TwoStrategy(new AvoidSecondRing(),
                new CaptureMostPieces()));
      case "strategy3":
        return new MachinePlayer(model, new TwoStrategy(new TwoStrategy(new AvoidSecondRing(),
                new PrioritizeCorners()), new CaptureMostPieces()));
      default:
        throw new IllegalArgumentException("Invalid player argument " + playerArg);
    }
  }
}
