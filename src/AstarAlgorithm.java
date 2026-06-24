import java.util.*;

public class AstarAlgorithm {

    //implementation of A* algorithm
    static void solve(){

        char[][] level = {};

        Scanner sc = new Scanner(System.in);

        System.out.println("Choose the level you want: 0(very easy) 1(easy) 2(medium) 3(hard) 4(very hard) 5(No solution case)");
        System.out.println("Levels 0-4 have solutions, 5th doesn't");

        int choice = sc.nextInt();
        if (choice == 0) {
            level = new char[][]{
                    {'#', '#', '#', '#', '#', '#', '#', '#', '#', '#'},
                    {' ', '#', ' ', ' ', ' ', '#', '#', '#', '#', '#'},
                    {'#', '#', '*', '#', ' ', '#', '#', '#', '#', '#'},
                    {'#', ' ', '*', '$', ' ', '*', ' ', ' ', ' ', '#'},
                    {'#', ' ', ' ', ' ', '0', ' ', '1', ' ', ' ', '#'},
                    {'#', '#', '#', ' ', ' ', '#', ' ', '#', '#', '#'},
                    {'#', ' ', '#', '#', ' ', ' ', ' ', '#', '#', '#'},
                    {'#', '#', '#', '#', '#', '#', '#', '#', '#', '#'}
            };
        } else if (choice == 1) {
            level = new char[][]{
                    {'#', '#', '#', '#', '#', '#', '#', '#'},
                    {'#', ' ', ' ', ' ', ' ', ' ', ' ', '#'},
                    {'#', ' ', '0', ' ', '*', ' ', '$', '#'},
                    {'#', ' ', ' ', ' ', ' ', ' ', ' ', '#'},
                    {'#', ' ', '*', ' ', ' ', ' ', ' ', '#'},
                    {'#', ' ', ' ', ' ', ' ', ' ', '1', '#'},
                    {'#', ' ', ' ', ' ', ' ', ' ', ' ', '#'},
                    {'#', '#', '#', '#', '#', '#', '#', '#'}
            };
        } else if (choice == 2) {
            level = new char[][]{
                    {'#','#','#','#','#',' ',' ',' ',' ',' '},
                    {' ','#',' ',' ',' ','#',' ',' ',' ',' '},
                    {'#','#','*','#',' ','#','#','#','#','#'},
                    {'#',' ','*',' ','0',' ','*',' ',' ','#'},
                    {'#',' ',' ',' ',' ','$',' ','1',' ','#'},
                    {'#','#','#',' ',' ','#','*','#','#','#'},
                    {' ',' ','#','#',' ',' ',' ','#',' ',' '},
                    {' ',' ',' ','#','#','#','#','#','#','#'}
            };


        } else if (choice == 3) {
            level = new char[][]{
                    {' ','#','#','#','#',' ',' ',' ',' ',' '},
                    {' ','#',' ',' ','#','#','#','#','#','#'},
                    {' ','#',' ',' ',' ',' ',' ',' ',' ','#'},
                    {'#','#',' ','#',' ','#','$','0',' ','#'},
                    {'#',' ',' ','*',' ','#','#',' ','#','#'},
                    {'#',' ','*','*',' ','#',' ','1','#'},
                    {'#','#','#',' ',' ',' ','*',' ','#'},
                    {' ',' ','#','#','#','#',' ',' ','#'},
                    {' ',' ',' ',' ',' ','#','#','#','#'}
            };

        } else if (choice == 4) {
            level = new char[][]{
                    {' ', '#', '#', '#', '#', '#', '#', '#', ' ', ' '},
                    {' ', '#', ' ', ' ', '#', ' ', ' ', '#', '#', '#'},
                    {' ', '#', '0', ' ', ' ', ' ', '0', ' ', ' ', '#'},
                    {' ', '#', ' ', '$', '#', '*', '*', ' ', ' ', '#'},
                    {'#', '#', ' ', '#', '1', ' ', '#', ' ', '#', '#'},
                    {'#', ' ', ' ', '$', '$', '#', '$', ' ', '#', ' '},
                    {'#', ' ', '0', ' ', ' ', ' ', '0', ' ', '#', ' '},
                    {'#', '#', '#', '#', '#', '#', ' ', ' ', ' ', '#'},
                    {' ', ' ', ' ', ' ', ' ', '#', '#', '#', '#', '#'}

            };
        }else if(choice == 5){
            level = new char[][]{
                    {'#', '#', '#', '#', '#', '#', '#', '#', '#', '#'},
                    {'#', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', '#'},
                    {'#', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', '#'},
                    {'#', ' ', ' ', ' ', ' ', '#', ' ', '0', ' ', '#'},
                    {'#', ' ', ' ', ' ', '$', ' ', '0', '$', ' ', '#'},
                    {'#', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', '#'},
                    {'#', ' ', ' ', '1', ' ', ' ', ' ', ' ', ' ', '#'},
                    {'#', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', '#'},
                    {'#', ' ', ' ', ' ', '0', ' ', '#', ' ', '$', '#'},
                    {'#', '#', '#', '#', '#', '#', '#', '#', '#', '#'}
            };
        }


        level = BoardUtils.makeRectangularWithBorder(level);


        System.out.println("Searching for a solution...");

        int countBox = 0;
        int countGoals = 0;
        int countPerson = 0;
        for (char[] chars : level) {
            for (int j = 0; j < level[0].length; j++) {
                if (chars[j] == '0') countBox++;
                if (chars[j] == '$') countGoals++;
                if(chars[j] == '1') countPerson++;
            }
        }
        if (countBox != countGoals) {
            System.out.println("Boxes and Goals can't match,\nNumber of free boxes at the map: " + countBox + "\n" + "Number of free goals at the map: " + countGoals);
            return;
        }

        if (countPerson > 1) {
            throw new ArrayIndexOutOfBoundsException("There is are more than 1 player on board");
        } else if (countPerson == 0) {
            throw new ArrayIndexOutOfBoundsException("There is no player on board");
        }


        int[] startP = BoardUtils.findPlayer(level);
        Node start = new Node(BoardUtils.copyGrid(level), startP[0], startP[1], null, 0, HeuristicEvaluator.heuristic(level));

        PriorityQueue<Node> open = new PriorityQueue<>(Comparator.comparingInt(n -> n.f)); //prioritize smaller f value
        Set<Node> visited = new HashSet<>();    //hashset

        open.add(start);
        visited.add(start);

        long startTime = System.currentTimeMillis();
        while (!open.isEmpty()) {
            Node current = open.poll();

            if (BoardUtils.isGoal(current)) {
                if (BoardUtils.noMoneyOrBox(current.grid)) {
                    printSolutionPath(current);
                    System.out.println("Solution found! timeMs=" + (System.currentTimeMillis() - startTime));
                } else {
                    continue;
                }
                return;
            }

            for (int dir = 0; dir < Main.DIRECTIONS.length; dir++) {
                int newR = current.playerRow + Main.DIRECTIONS[dir][0];
                int newC = current.playerCol + Main.DIRECTIONS[dir][1];

                if (!BoardUtils.isValidMove(newR, newC, current.grid, dir)) continue;
                if (DeadlockDetector.isDeadlock(current.grid)) continue;

                char[][] newGrid = BoardUtils.updateGrid(newR, newC, BoardUtils.copyGrid(current.grid), dir);
                Node child = new Node(newGrid, newR, newC, current, current.g + 1, HeuristicEvaluator.heuristic(newGrid));

                if (!visited.contains(child)) {
                    visited.add(child);
                    open.add(child);
                }
            }
        }
        System.out.println("Not possible to find solution. Time: " + (System.currentTimeMillis() - startTime) + " ms");
    }


    static void printSolutionPath(Node goal) {
        List<Node> path = new ArrayList<>();
        Node cur = goal;
        while (cur != null) {
            path.add(cur);
            cur = cur.parent;
        }
        Collections.reverse(path);
        int step = 0;
        for (Node n : path) {
            System.out.println("Step " + (step++));
            n.print();
            System.out.println("h=" + n.h);
            System.out.println();
            System.out.println("f=" + n.f);
            System.out.println();
            System.out.println("----------------------");
        }
    }

}
