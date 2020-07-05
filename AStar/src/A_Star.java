import java.util.ArrayList;
import java.util.List;

public class A_Star {
    static String CELLWALL = "|";

    public static void main(String[] args) {
        // Set the starting variables for the pathfinding algorithm
        int sizeX  = 10;
        int sizeY  = 10;
        int startX = 1;
        int startY = 9;
        int endX   = 2;
        int endY   = 5;

        // Run the algorithm and save it to a 2D array of cells
        Cell[][] pathCells = runAStar(sizeX, sizeY, startX, startY, endX, endY);

        // Print the map to the console
        printMap(pathCells);
    }

    static Cell[][] runAStar(int sizeX, int sizeY, int startX, int startY, int endX, int endY) {
        System.out.println("Started Algorithm.");

        // Exit the program if the start and end are in the same spot
        // or if the start or end are out of bounds
        if ((startX == endX && startY == endY) || (endX >= sizeX || startX >= sizeX) || (endY < 0 || startY < 0)) {
            exit();
        }

        // Create the cell map
        Cell[][] pathCells = new Cell[sizeY][sizeX];

        // Populate the map with empty cells
        for (int y = 0; y < sizeY; y ++) {
            for (int x = 0; x < sizeX; x ++) {
                if (startY == y && startX == x) {
                    pathCells[y][x] = Cell.START;
                } else if (endY == y && endX == x) {
                    pathCells[y][x] = Cell.END;
                } else {
                    pathCells[y][x] = Cell.EMPTY;
                }
            }
        }

        boolean finished = false;

        int currentY = startY;
        int currentX = startX;

        // g is the distance from the start to the current spot following
        // the taken path
        int g = 0;

        while (!finished) {
            int chosenY = currentY;
            int chosenX = currentX;

            int f = 99999999;
            for (int y = -1; y <= 1; y ++) {
                if (currentY + y < 0 || currentY + y >= sizeY) {
//                    System.out.println("passedY " + (currentY + y));
                    continue;
                }

                for (int x = -1; x <= 1; x ++) {
                    if ((currentX + x < 0 || currentX + x >= sizeX) || (y == 0 && x == 0)) {
//                        System.out.println("passedX " + (currentX + x));
                        continue;
                    } else if ((y == -1 && x == -1) || (y == -1 && x == 1) || (y == 1 && x == -1) || (y == 1 && x == 1)) {
                        continue;
                    } else if (pathCells[currentY + y][currentX + x] == Cell.END) {
                        finished = true;
                        break;
                    } else if (pathCells[currentY + y][currentX + x] == Cell.PATH || pathCells[currentY + y][currentX + x] == Cell.START) {
                        continue;
                    }

                    int h = calcDist(currentY + y, currentX + x, endY, endX);

                    if ((g + 1) + h < f) {
                        f = (g + 1) + h;

                        chosenY = currentY + y;
                        chosenX = currentX + x;
                    }
                }
                if (finished) {
                    break;
                }

//                printMap(pathCells);

//                System.out.println("Chosen: [" + currentY + ", " + currentX + "]");
            }
            currentY = chosenY;
            currentX = chosenX;

            g++;

            pathCells[currentY][currentX] = Cell.PATH;
        }
        System.out.println("Finished algorithm.");

        return pathCells;
    }

    static int calcDist(int point1Y, int point1X, int point2Y, int point2X) {
        double a = Math.abs(point1Y - point2Y);
        double b = Math.abs(point1X - point2X);

        return (int) (a + b);
    }

    static void printMap(Cell[][] pathCells) {

        // Create a StringBuilder for better memory usage
        StringBuilder builder = new StringBuilder();

        for (int y = 0; y < pathCells.length; y ++) {
            builder.append(CELLWALL);

            for (int x = 0; x < pathCells[0].length; x ++) {
                builder.append(pathCells[y][x].getCellType());
                builder.append(CELLWALL);
            }

            String out = builder.toString();
            System.out.println(out);

            // Clear the StringBuilder and goto a new line
            builder.delete(0, builder.length());
        }
    }

    static void exit() {
        System.exit(0);
    }
}
