import java.util.Scanner;

public class Game {

    private static String Player1symbol = "\033[0;31m O \033[0m"; //red
    private static String Player2symbol = "\033[0;33m O \033[0m"; // yellow
    private static String emptyField = "\033[0;30m . \033[0m | ";
    private static String[] symbols = {Player1symbol,Player2symbol,emptyField};

    private static int gameBoardSize;

    public static void main(String args[]) {
        boolean game = true;
        int counter=0;
        String currentPlayerSymbol;
        String Player;
        int[] PawnCount;

        start();
        System.out.println();

        while (game) {

            PawnCount = countPawns(gameBoardSize);
            System.out.println("\nPAWN COUNT: Player1="+PawnCount[0]+" ,Player2="+PawnCount[1]);

            if (counter%2==0) {currentPlayerSymbol = Player1symbol;Player="Player1"; } else {currentPlayerSymbol = Player2symbol;Player="Player2";}
            System.out.println("\nCURRENT PLAYER: "+Player);

            Board.displayBoard();

            int[] getOldPosition = getPosition("Select token to move: ");
            int oldX = getOldPosition[0];
            int oldY = getOldPosition[1];

            if (!checkSelectedPawn(oldX,oldY, currentPlayerSymbol)) {
                continue;
            }

            highlightPawn(oldX, oldY, Player);
            Board.displayBoard();

            int[] getNewPosition = getPosition("Define new position: ");
            int newX = getNewPosition[0];
            int newY = getNewPosition[1];

            int[] positions = {oldX, oldY, newX, newY};


            int[] changePositions = positions; //round(Player);

            if (checkMove(changePositions, currentPlayerSymbol)) {
                setPosition(changePositions, currentPlayerSymbol);
                pawnElminated(changePositions, currentPlayerSymbol);
                clearHighlightPawn(newX,newY,Player);
            } else {
                System.out.println("Wrong move, try again! \n");
                clearHighlightPawn(oldX,oldY,Player);
                continue;
            }
            if (checkWinners(countPawns(Game.gameBoardSize))) {
                game = false;
                };
            //System.out.println("\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n");
            counter++;
        }
    }

    public static void start() {
        System.out.println("\nGive a board size: ");
        Scanner boardSize = new Scanner(System.in);
        int n = boardSize.nextInt(); //n between 4-20;
        Game.gameBoardSize = n;
        Board.SetInitData(n, symbols);
        new Board();
    }

    public static int[] round(String Player) {

        int[] oldPosition = getPosition("Select token to move: ");
        int oldX = oldPosition[0];
        int oldY = oldPosition[1];

        highlightPawn(oldX, oldY, Player);

        Board.displayBoard();

        int[] newPosition = getPosition("Define new position: ");
        int newX = newPosition[0];
        int newY = newPosition[1];

        int[] positions = {oldX, oldY, newX, newY};
        return positions;
    }

    public static int[] getPosition(String info) {
        System.out.println(info);
        Scanner userPosition = new Scanner(System.in);
        System.out.print("Type row(X): ");
        int newPosX = userPosition.nextInt();
        System.out.print("Type column(Y): ");
        int newPosY = userPosition.nextInt();

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
    }

    public static boolean checkMove(int[] changePos, String GamerSymbol) {
        int oldX = changePos[0];
        int oldY = changePos[1];
        int newX = changePos[2];
        int newY = changePos[3];

        int oPpositionX = (oldX+newX)/2;
        int oPpositionY = (oldY+newY)/2;

        String SelectedPawnSymbol = Board.fields[oldX][oldY].playerSymbol;

        //check if given position are coordinate of current players pawn
        if (checkSelectedPawn(oldX,oldY,SelectedPawnSymbol)) {
        } else {
            return false;
        }

        if ((Math.abs(newX-oldX) > 2 ) ||  (Math.abs(newY-oldY) > 2)) {
            System.out.print("\nYou can not move there!\n");
            return false;
        }

        if ((Math.abs(newX-oldX) == 2 ) ||  (Math.abs(newY-oldY) == 2)) {
            if (Board.fields[oPpositionX][oPpositionY] == null) {
                System.out.print("\nYou can not move about 2field if you do not attack enemy!\n");
                return false;
            } else if (Board.fields[oPpositionX][oPpositionY].playerSymbol == GamerSymbol) {
                System.out.print("\nYou can not move above your pawn!\n");
                return false;
            }
            return true;
        }

        if (Board.fields[newX][newY] == null) {
            //check that the movement is 1 point on diagonal
            if ((Math.abs(newX-oldX) == 1 ) &&  (Math.abs(newY-oldY) == 1)) {
                return true;
            } else {
                System.out.println("\nYou can move only in the diagonals direction");
                return false;}
        } else {
            return false;
        }
    }

    public static boolean checkSelectedPawn(int x, int y, String GamerSymbol) {
        if (Board.fields[x][y] == null) {
            System.out.println("\nIncorrect coordinates! Grab correct pawn!");
            return false;
        }
        if (Board.fields[x][y].playerSymbol != GamerSymbol) {
            System.out.println("Select your pawn!");
            return false;
        }
        return true;
    }

    public static int[] countPawns(int size) {
        int cP1 = 0;
        int cP2 = 0;
        for (int x = 0; x < size; x++) {
            for (int y = 0; y < size; y++) {
                if (Board.fields[x][y] != null) {
                    if (Board.fields[x][y].playerSymbol == Game.Player1symbol) {
                        cP1++;
                    } else if (Board.fields[x][y].playerSymbol == Game.Player2symbol) {
                        cP2++;
                    }
                }
            }
        }
        int[] results = {cP1, cP2};
        return results;
    }

    public static boolean checkWinners(int[] data) {
        int cP1 = data[0];
        int cP2 = data[1];

        if (cP2 == 0) {
            System.out.println("\n\nGame over. The winner is Player1");
            return true;
        }
        if (cP1 == 0) {
            System.out.println("\n\nGame over. The winner is Player2");
            return true;
        }
        return false;
    }

    public static boolean pawnElminated(int[] changePos, String gamerSymbol) {
        int oldX = changePos[0];
        int oldY = changePos[1];
        int newX = changePos[2];
        int newY = changePos[3];
        //System.out.println(oldX+"|"+oldY+"|"+newX+"|"+newY);
        int oPpositionX = (oldX+newX)/2;
        int oPpositionY = (oldY+newY)/2;

        //double xCheck = Math.sqrt((newX-oldX)*(newX-oldX));
        double xCheck = Math.abs(newX-oldX);
        if (xCheck == 2) {
            System.out.println("ELIMINATED: "+oPpositionX + "|" + oPpositionY);
            if (Board.fields[oPpositionX][oPpositionY].playerSymbol != gamerSymbol) {
                Board.fields[oPpositionX][oPpositionY] = null;
                return true;
            }
            return false;
        }
        return false;
    }

    public static void highlightPawn(int X, int Y, String Gamer) {

        if (Gamer=="Player1") {
            Board.fields[X][Y].playerSymbol = "\033[0;35m O \033[0m";
        } else {
            Board.fields[X][Y].playerSymbol = "\033[1;92m O \033[0m"; //green
        }
    }

    public static void clearHighlightPawn(int X, int Y,String Gamer) {
        //System.out.println(X+" |"+Y+"|"+Gamer);
        if (Gamer=="Player1") {
            Board.fields[X][Y].playerSymbol = Game.Player1symbol; //"\033[0;31m O \033[0m";
        } else {
            Board.fields[X][Y].playerSymbol = Game.Player2symbol; //"\033[0;33m O \033[0m";
        }
    }
}
