package cs3500.reversi;

import org.junit.Before;
import org.junit.Test;

import cs3500.reversi.model.CubicPosn;
import cs3500.reversi.model.DiskState;
import cs3500.reversi.model.Reversi;
import cs3500.reversi.model.ReversiModel;
import cs3500.reversi.player.HumanPlayer;
import cs3500.reversi.player.MachinePlayer;
import cs3500.reversi.strategy.CaptureMostPieces;
import cs3500.reversi.strategy.ReversiStrategy;
import static org.junit.Assert.assertEquals;


/**
 * Tests for the {@link Player} interface, tests simple functionalities and dispatch of higher level
 * interactions with the model in MVC.
 */
public class PlayerTests {
  private HumanPlayer humanPlayer;

  private MachinePlayer machinePlayer;

  private final ReversiStrategy strategy = new CaptureMostPieces();

  private ReversiModel modelHuman;

  private ReversiModel modelMachine;


  @Before
  public void setUp() {
    this.modelHuman = new Reversi();
    this.humanPlayer = new HumanPlayer(modelHuman);
    this.modelMachine = new Reversi();
    this.machinePlayer = new MachinePlayer(modelMachine, strategy);

  }

  // Make move does not require any action from the player interface itself,
  // it waits for input from view, so the class shouldn't mutate the model.
  @Test
  public void testHumanMakeMove() {
    ReversiModel result = new Reversi();
    result.startGame(3);
    modelHuman.startGame(3);
    this.humanPlayer.makeMove();
    assertEquals(result.getBoard(), modelHuman.getBoard());
  }

  @Test
  public void testMachineDispatchStrategy() {
    this.modelMachine.startGame(3);
    this.machinePlayer.makeMove();
    assertEquals(this.modelMachine.getState(new CubicPosn(1,-2)), DiskState.WHITE);
  }
}
