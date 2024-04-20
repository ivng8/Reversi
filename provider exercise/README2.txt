New Classes:

Interfaces:

IView: represents a graphic view of anything and communicates with (hypothetical) controller
to render corresponding changes in view for the user.

IPanel: represents the rendering of the game specific sections of a graphic view (such as hexagons
and board pieces).

Classes for the graphical view of the GUI:

	Hexagon (Hexagon.java):
	    Simple class that represents a single hexagon to be rendered.
	    It only needs size and position to carry out its tracing uses.

	ReversiGraphicsView (ReversiGraphicsView.java):
	    The GraphicsView represents a lot of the handling when it comes to text input and buttons,
	    as well as holds the board of the actual hexagonal grid. Preferably we would be able to
	    implement the MouseLListener into this section, however we ran into issues of how the
	    methods actually interacted with each other, and how that interfered with fully rendering
	    the DiskStates. Everything that isn't explicitly related to the visual view of the
	    Hexagonal Grid is managed by this view.

	ReversiPanel (ReversiPanel.java):
	The Panel represents the events of clicking and handling the hexagonal grid. This Panel has a
	Mouse Listener which looks for Mouse Events to determine when a hex is clicked. We felt a
	separation between functionality with the display was necessary, as it helps us isolate where
	issues are when debugging, and it also makes the code easier to digest.

Strategy Implementation:

    ReversiStrategy (ReversiStrategy.java):
        Representations a strategy for the game of Reversi.
        All implementations basically choose a move (or possibly in the future moves) for the
        specifically given user in the current game state. Should return a point on the board for
        the user but will throw an exception if there are no valid moves for the strategy to find.

    CaptureMostPieces (CaptureMostPieces.java):
        Represents the simple strategy where it finds the move that flips the most pieces and
        defies tiebreakers by choosing the one closest to the top left corner.
        Computation Process:
        For the given player, parses the game board for every possible move.
        Then it maps every coordinate to the amount of pieces that they will flip.
        Looks for the max value out of all the possibilities (throws if 0 because it means
        that there are no valid moves to make since all locations flip 0 pieces).
        Puts all the points that match the max value in a list.
        Decides the tiebreaker of this list by finding the point that's closest to the top left.

Changes from Part 1:

	Refactored the non-mutative, view-only behaviors into the ReadOnlyReversiModel interface
	 - Board size
	 - State of a specific cell
	 - If a move is valid
	 - Score of a player
	 - Any valid moves for a player
	 - If the game is over
	 - Get the board state

	Game logic mutation calculation changes in Reversi
	 - Implemented skipping turns and bookkeeping for consecutive skipped turns (to end game)
	 - Added bookkeeping in the model to keep track of whose turn it is