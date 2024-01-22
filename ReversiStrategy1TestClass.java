package cs3500.reversi;

import org.junit.Before;
import org.junit.Test;

import cs3500.reversi.mocks.MockReversi;
import cs3500.reversi.model.CubicPosn;
import cs3500.reversi.model.DiskState;
import cs3500.reversi.strategy.CaptureMostPieces;
import cs3500.reversi.strategy.ReversiStrategy;

/**
 * test for strategy with the usage of mocks.
 */
public class ReversiStrategy1TestClass {
  private MockReversi mockModel;
  private ReversiStrategy strategy;

  @Before
  public void setUp() {
    mockModel = new MockReversi(3);
    strategy = new CaptureMostPieces();
    // Set specific legal moves for testing
    mockModel.addLegalMove(new CubicPosn(3, -1));
    mockModel.addLegalMove(new CubicPosn(1, 1));
    mockModel.addLegalMove(new CubicPosn(-1, -1));
  }

  /**
   * Possibly use transcript to verify specfic actions in mock
   * SOMETHING LIKE THIS FOR OTHER STRATEGIES
   * assertTrue("Transcript should contain checks for corners",
   * mockModel.getTranscript().contains("isValidMove called with: CubicPosn(0,0), BLACK"));
   */
  @Test
  public void testStrategyBehavior() {
    // Execute the strategy
    CubicPosn chosenMove = strategy.chooseMove(mockModel, DiskState.BLACK);

    // print out moves done by mock, not necessary, but for the strategy transcript part of doc.
    for (String str: mockModel.getTranscript()) {
      System.out.println(str);
    }

    assertEquals(chosenMove, new CubicPosn(3, -1));
  }
}