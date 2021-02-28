import java.util.Scanner;
import java.io.IOException;

public class Game {
    public static void main(String args[]) throws IOException, InterruptedException {
        boolean game = true;
        int counter=0;

        String Player;
        String Player1symbol = "\033[0;31m O \033[0m"; //red
        String Player2symbol = "\033[0;33m O \033[0m"; // yellow

        System.out.println("COUNTER= "+counter);
        String playerSymbol;

        start();

        System.out.println();

        while (game) {

            if (counter%2==0) {playerSymbol = Player1symbol;Player="Player1"; } else {playerSymbol = Player2symbol;Player="Player2";}
            System.out.println("PLAYER: "+Player);

            int[] changePositions = round(Player);

            if (checkMove(changePositions)) {
                setPosition(changePositions, Player);
                pawnElminated(changePositions, playerSymbol);
            } else {
                System.out.println("Wrong move, try again! \n");
                continue;
            }
            if (checkWinners(countPawns(10))) {
                game = false;
                };
            //System.out.println("\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n");
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

    public static int[] round(String Player) {

        int[] oldPosition = getPosition("Select token to move: ");
        int oldX = oldPosition[0];
        int oldY = oldPosition[1];

        higlightPawn(oldX,oldY,Player);

        Board.displayBoard(10);

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

    public static void setPosition(int[] changePos, String Player) {
        int oldX = changePos[0];
        int oldY = changePos[1];
        int newX = changePos[2];
        int newY = changePos[3];
        Board.fields[newX][newY] = Board.fields[oldX][oldY];
        Board.fields[oldX][oldY] = null;
        clearHiglightPawn(newX,newY,Player);
    }

    public static boolean checkMove(int[] changePos) {
        int oldX = changePos[0];
        int oldY = changePos[1];
        int newX = changePos[2];
        int newY = changePos[3];

        int oPpositionX = (oldX+newX)/2;
        int oPpositionY = (oldY+newY)/2;

        if (Board.fields[oldX][oldY] == null) {
            System.out.println("\nGrab correct pawn!");
            return false;
        }

        if ((Math.abs(newX-oldX) > 2 ) ||  (Math.abs(newY-oldY) > 2)) {
            return false;
        }

        if ((Math.abs(newX-oldX) == 2 ) ||  (Math.abs(newY-oldY) == 2)) {
            if (Board.fields[oPpositionX][oPpositionY] == null) {
                return false;
            }
            return true;
        }

        String Player = Board.fields[oldX][oldY].playerSymbol;

        if (Board.fields[newX][newY] == null) {
            return true;
        } else {
            return false;
        }
    }

    public static int[] countPawns(int size) {
        String Player1symbol = "\033[0;31m O \033[0m"; //red
        String Player2symbol = "\033[0;33m O \033[0m"; // yellow
        int cW = 0;
        int cB = 0;
        for (int x = 0; x < size; x++) {
            for (int y = 0; y < size; y++) {
                if (Board.fields[x][y] != null) {
                    if (Board.fields[x][y].playerSymbol == Player1symbol) {
                        cW++;
                    } else if (Board.fields[x][y].playerSymbol == Player2symbol) {
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
            System.out.println("Game over. The winner is RED");
            return true;
        }
        if (cB == 0) {
            System.out.println("Game over. The winner is YELLOW");
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

        //double xCheck = Math.sqrt((newX-oldX)*(newX-oldX));
        double xCheck = Math.abs(newX-oldX);
        if (xCheck == 2) {
            System.out.println("ELIMINATED: "+oPpositionX + "|" + oPpositionY);
            if (Board.fields[oPpositionX][oPpositionY].playerSymbol == opponent) {
                Board.fields[oPpositionX][oPpositionY] = null;
                return true;
            }
            return false;
        }
        return false;
    }

    public static void higlightPawn(int X, int Y, String Gamer) {
        if (Gamer=="Player1") {
            Board.fields[X][Y].playerSymbol = "\033[1;92m O \033[0m"; //green
        } else {
            Board.fields[X][Y].playerSymbol = "\033[0;35m O \033[0m";
        }
    }

    public static void clearHiglightPawn(int X, int Y,String Gamer) {
        System.out.println(X+" |"+Y+"|"+Gamer);
        if (Gamer=="Player1") {
            Board.fields[X][Y].playerSymbol = "\033[0;33m O \033[0m";
        } else {
            Board.fields[X][Y].playerSymbol = "\033[0;31m O \033[0m";
        }
    }
}
