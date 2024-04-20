package cs3500.reversi.view;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;


import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JButton;
import javax.swing.JTextField;
import javax.swing.JCheckBox;
import javax.swing.JOptionPane;


import cs3500.reversi.features.ViewEvent;
import cs3500.reversi.model.CubicPosn;
import cs3500.reversi.model.DiskState;
import cs3500.reversi.model.ReadOnlyReversiModel;
import cs3500.reversi.view.components.IReversiComponent;
import cs3500.reversi.view.components.ShowHint;

/**
 * {@code ReversiGraphicsView} is a graphical representation of the Reversi game,
 * extending {@link JFrame} and implementing the {@link IView} interface. It visualizes
 * the game board, handles user interactions, and displays game information, like scores
 * and current player turns.
 *
 * <p>The view is composed of a main game board panel and a button panel. The game board
 * is interactive, allowing players to click on the board to place pieces. The button
 * panel includes controls for game actions like passing a turn and displays the current
 * game score.
 *
 * <p>This class is designed to work with a {@link ReadOnlyReversiModel} to display the
 * current state of the game, and it uses {@link ViewEvent.ViewEventListener} to communicate
 * player actions back to the controller. It encapsulates all graphical elements and user
 * interface logic required for a game of Reversi, making it a self-contained component
 * for the graphical part of the MVC architecture.
 *
 * <p>Usage:
 * <pre>
 *   ReadOnlyReversiModel model = ...; // Get or create a game model
 *   ReversiGraphicsView view = new ReversiGraphicsView(model);
 *   view.setVisible(true); // Display the game window
 * </pre>
 */
public class ReversiGraphicsView extends JFrame implements IView {

  private final List<ViewEvent.ViewEventListener> listeners = new ArrayList<>();
  private final ReversiPanel boardPanel;

  private final JPanel buttonPanel;

  private final ReadOnlyReversiModel model;

  private final JTextField score = new JTextField(" ");

  private final List<IReversiComponent> components;

  private DiskState turn;

  /**
   * constructor for the graphic view.
   *
   * @param model the game to be drawn
   */
  public ReversiGraphicsView(ReadOnlyReversiModel model, String gameType) {
    super("ReversiGame");
    components = new ArrayList<>();
    // Window setup
    this.model = model;
    if (Objects.equals(gameType, "square")) {
      boardPanel = new SquareReversiPanel(model);
    } else {
      boardPanel = new ReversiPanel(model);
    }
    boardPanel.setPreferredSize(new Dimension(500, 500));
    this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    this.setLayout(new BorderLayout());
    this.add(boardPanel, BorderLayout.CENTER);

    // Click listener
    boardPanel.addMouseListener(new MouseAdapter() {
      @Override
      public void mouseClicked(MouseEvent e) {

        this.handleMouseClick(e);
      }

      private void handleMouseClick(MouseEvent e) {
        boardPanel.renderSelection(e.getX(), e.getY());
        refresh();
      }
    });

    // Button panel
    this.buttonPanel = new JPanel();
    this.buttonPanel.setLayout(new FlowLayout());
    this.add(this.buttonPanel, BorderLayout.SOUTH);

    // Toggleable decorator checkbox
    JCheckBox hintToggle = new JCheckBox("Decorator Toggle");
    hintToggle.addActionListener(e -> toggle());

    this.buttonPanel.add(hintToggle);

    // Command Execute Button
    JButton commandButton = new JButton("Place");
    commandButton.addActionListener((ActionEvent e) -> {
      this.onMoveListener(this.boardPanel.getSelectedPosn());
    });
    this.buttonPanel.add(commandButton);

    JButton passButton = new JButton("Pass");
    passButton.addActionListener((ActionEvent e) -> {
      this.onPassListener();
    });

    buttonPanel.add(passButton);
    buttonPanel.add(score);

    this.pack();
  }

  /**
   * Toggles the visibility or state of certain components in the view.
   * This method is typically used for enabling or disabling visual features like hints.
   */
  private void toggle() {
    for (IReversiComponent component : components) {
      component.toggle();
    }
  }

  @Override
  public void paint(Graphics g) {
    super.paint(g); // Ensures that other components (like buttons) are painted

    for (IReversiComponent component : components) {
      component.render(g);
    }
  }

  @Override
  public void showError(String error) {
    JOptionPane.showMessageDialog(this,
            error, "Error", JOptionPane.ERROR_MESSAGE);
  }

  @Override
  public void refresh() {
    this.repaint();
  }

  @Override
  public void addViewEventListener(ViewEventListener listener) {
    listeners.add(listener);
    this.turn = listener.getTurn();
    addHintComponent();
  }

  @Override
  public void updateScore(DiskState state) {
    String text = "Score: " + model.getScore(state);
    this.score.setText(text);
  }

  @Override
  public void updatePlayer(DiskState state) {
    JTextField player = new JTextField(state.toString());
    buttonPanel.add(player);
  }

  /**
   * notifies its listener of a piece place command.
   * @param posn the position of the command
   */
  private void onMoveListener(CubicPosn posn) {
    for (ViewEvent.ViewEventListener listener : listeners) {
      listener.onMove(posn);
    }
    this.refresh();
  }

  /**
   * notifies its listener of a pass command.
   */
  private void onPassListener() {
    for (ViewEvent.ViewEventListener listener : listeners) {
      listener.onPass();
    }
  }

  @Override
  public void addHintComponent() {
    ShowHint hint = new ShowHint(model, this.turn);
    hint.addUnderlay(boardPanel);
    this.components.add(hint);
  }
}