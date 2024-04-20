
FOR FULL CLARIFICATION ON SOME TESTING, PLUS FUNCTIONAL CODE:https://youtu.be/6kyDOHNYEtU

New Classes:

Interfaces:

Features Interface ModelEvent: represents a listener events that will be placed inside of the model
and waits for any changes to the model to notify the controller into making corresponding commands
to the other data structures in the game.

Features Interface ViewEvent: represents a listener events that will be placed inside of the view(s)
and waits for any changes in the view to notify the controller into making corresponding commands to
other data structures in the game.

Player: represents a player in the game of Reversi

    HumanPlayer (HumanPlayer.java):
        Represents a Human player in the game of Reversi

    MachinePlayer (MachinePlayer.java):
        Represents an AI player in the game of Reversi. The AI upon creation has a strategy
        that it utilizes to determine which move it should make.

ReversiController:

AvoidSecondRing (AvoidSecondRing.java):
    strategy that doesn't allow making a move on a hex adjacent to a corner

Minimax (Minimax.java):
    strategy that computes the move(s) that will create an outcome that worsens the opponent's
    best possible move

PrioritizeCorners (PrioritizeCorners.java):
    strategy that will prioritize corners

TwoStrategy (TwoStrategy.java):
    strategy that combines two or more strategies in a tree-based manner

Changes for Part 3:

The strategy computation is separated into two parts. The chooseMove that is used as the general
notion that the strategy outputs a move matching that strategy. This method computes the actual
valid moves for the player and calls on a helper called filter to filter out the remaining moves
that match the strategy. Then it utilizes the tiebreaker of choosing the hex closest to the
top left corner and outputs the hex in the chooseMove. This was done for cleaner abstraction of
strategies since a strategy will always primarily get all valid moves and use a tiebreaker at the
end after filtering.

The listener pattern was added into the model and view. The method to add a listener is put into
the model and the view and it becomes stored as a field. Upon changes in the model or the view,
the listener will be notified to command a corresponding change to other data structures so that
the different structures in the game are acting cohesively.

On that note, listener is added in the graphics view instead of the panel to compile more neatly
and take advantage of different delegations and method interactions.