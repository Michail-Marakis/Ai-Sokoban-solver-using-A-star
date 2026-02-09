***SOKOBAN AI SOLVER***


**GAME RULES**

At the start of the program, the user is prompted to select the desired SOKOBAN level difficulty.
If a solution exists, each move performed by the player will be printed until the final state is reached.
At the same time, the cost values f and h at each step are displayed.
If no solution exists, an appropriate message is printed.
The goal of the game (final state) is for the player to place each box on a target.
It is NOT allowed to place two or more boxes on the same target.
The correspondence must be one-to-one (1–1).



**SYMBOL MAPPINGS**

 -> 1 = "Player"\
 -> 0 = "Box"\
 -> # = "Wall"\
 -> $ = "Target"\
 -> * = "Box on Target"\
 -> + = "Player on Target"



**CODE STRUCTURE**

-> Main()

-> A* Algorithm implementation(AstarAlgorithm())

-> Core methods(isValidMove(...), updateGrid(...), isDeadlock(...))

-> Helper methods(isCorridorDeadlock(...), isItCorner(...), checkFourDirections(...), noMoneyOrBox(...), findPlayer(...), copyGrid(...), printSolutionPath(...), makeRectangularWithBorder(...), isGoal(...))

-> Heuristic function implementation(heuristic(...), isValid(...), IDSPlayertobox(...), DLS(...))

-> Node Class
(Constructor, equals(...), hashCode(), print())



**FILE STRUCTURE**

*Main.java:*
Contains the entire codebase except the Node class.

*Node.java:*
Contains the Node class, which represents each game state.

### Example: Sokoban Hard Level Solution (A*)

The following images illustrate the execution of the **A\*** search algorithm on a **hard Sokoban level**.

- **Step 0** shows the initial state of the puzzle.
  - The heuristic value is `h = 10`
  - The total cost is `f = 10`
  - The player, boxes, walls, and targets are represented using the predefined symbol mapping.

- **Step 389** shows the final (goal) state.
  - All boxes have been successfully placed on distinct targets (1–1 correspondence).
  - The heuristic value has reached `h = 0`
  - The total path cost is `f = 389`

This example demonstrates:
- The effectiveness of the A\* algorithm on complex Sokoban configurations
- The gradual reduction of the heuristic value until the goal state is reached
- The use of deadlock detection to avoid invalid or unsolvable states


![alt text](step0-HardLevel.png)
![alt text](step389-HardLevel.png)
