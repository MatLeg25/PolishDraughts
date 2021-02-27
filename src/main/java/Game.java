import java.util.Scanner;

public class Game {
    public static void main(String args[]) {
        boolean game = true;
        int counter=0;
        System.out.println("COUNTER= "+counter);
        String playerSymbol;

        start();

        while (game) {
            if (counter%2==0) {playerSymbol = "W"; } else {playerSymbol = "B";}

            int[] changePositions = round();

            if (checkMove(changePositions)) {
                setPosition(changePositions);
                pawnElminated(changePositions, playerSymbol);
            } else {
                System.out.println("Wrong move, try again! \n");
                continue;
            }
            if (checkWinners(countPawns(10))) {
                game = false;
                };
            Board.displayBoard(10);
            counter++;
        }
    }

    public static void start() {
        /*        System.out.println("Give a board size: ");
        Scanner boardSize = new Scanner(System.in);
        int n = boardSize.nextInt(); //n between 4-20;
        */
        int n = 10;
        int rw = 4;
        new Board(n, rw);
        Board.displayBoard(n);
    }

    public static int[] round() {

        int[] oldPosition = getPosition("Select token to move: ");
        int oldX = oldPosition[0];
        int oldY = oldPosition[1];

        int[] newPosition = getPosition("Define new position: ");
        int newX = newPosition[0];
        int newY = newPosition[1];

        int[] positions = {oldX, oldY, newX, newY};
        return positions;
    }

    public static int[] getPosition(String info) {
        System.out.println(info);
        System.out.print("Type row(X): ");
        Scanner userPositionX = new Scanner(System.in);
        int newPosX = userPositionX.nextInt();
        System.out.print("Type column(Y): ");
        Scanner newUserPositionY = new Scanner(System.in);
        int newPosY = userPositionX.nextInt();

        int[] temp = {newPosX, newPosY};

        return temp;
    }

    public static void setPosition(int[] changePos) {
        int oldX = changePos[0];
        int oldY = changePos[1];
        int newX = changePos[2];
        int newY = changePos[3];
        Board.fields[newX][newY] = Board.fields[oldX][oldY];
        Board.fields[oldX][oldY] = null;
    }

    public static boolean checkMove(int[] changePos) {
        int oldX = changePos[0];
        int oldY = changePos[1];
        int newX = changePos[2];
        int newY = changePos[3];

        if (Board.fields[oldX][oldY] == null) {
            System.out.println("\nGrab correct pawn!");
            return false;
        }

        String Player = Board.fields[oldX][oldY].playerSymbol;

        if (Board.fields[newX][newY] == null) {
            return true;
        } else {
            return false;
        }
    }

    public static int[] countPawns(int size) {
        int cW = 0;
        int cB = 0;
        for (int x = 0; x < size; x++) {
            for (int y = 0; y < size; y++) {
                if (Board.fields[x][y] != null) {
                    if (Board.fields[x][y].playerSymbol == "W") {
                        cW++;
                    } else if (Board.fields[x][y].playerSymbol == "B") {
                        cB++;
                    }
                }
            }
        }
        int[] results = {cW, cB};
        return results;
    }

    public static boolean checkWinners(int[] data) {
        int cW = data[0];
        int cB = data[1];

        if (cW == 0) {
            System.out.println("Game over. The winner is BLACK");
            return true;
        }
        if (cB == 0) {
            System.out.println("Game over. The winner is WHITE");
            return true;
        }
        return false;
    }

    public static boolean pawnElminated(int[] changePos, String opponent) {
        int oldX = changePos[0];
        int oldY = changePos[1];
        int newX = changePos[2];
        int newY = changePos[3];

        int oPpositionX = (oldX+newX)/2;
        int oPpositionY = (oldY+newY)/2;

        double xCheck = Math.sqrt((newX-oldX)*(newX-oldX));
        if (xCheck > 1) {
            System.out.println(oPpositionX + "|" + oPpositionY);
            if (Board.fields[oPpositionX][oPpositionY].playerSymbol == opponent) {
                Board.fields[oPpositionX][oPpositionY] = null;
                return true;
            }
            return false;
        }
        return false;
    }
}
