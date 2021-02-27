public class Board {

    static int size = 10;
    static String[] labelX = new String[size];
    static String[] labelY = new String[size];
    public static Pawn[][] fields = new Pawn[2 * size][2 * size];

    public Board(int size, int rw) {

        for (int x = 0; x < size; x++) {
            for (int y = 0; y < size; y++) {
                if ((y % 2 == 1 && x % 2 == 0 && x < rw) || (y % 2 == 0 && x % 2 == 1 && x < rw)) {
                    fields[x][y] = new Pawn(x, y,"W");
                }
                else if ((y % 2 == 1 && x % 2 == 0 && x >= size - rw) || (y % 2 == 0 && x % 2 == 1 && x >= size - rw)) {
                    fields[x][y] = new Pawn(x, y,"B");
                }
                else {
                    fields[x][y] = null;
                    //fields[x][y] = new Pawn(x, y,"."); //to create empty-field object
                }
            }
        }

    }

    public static void displayBoard(int size) {

        //fill label X
        for (int i = 0; i < size; i++) {
            labelX[i] = String.valueOf(i);
        }
        //display Label X by for each loop
        System.out.print("+ | ");
        for (String j : labelX) {
            System.out.print(j+" - ");
        }
        System.out.println();

        //DISPLAY GAME BOARD
        for (int x = 0; x < size; x++) {
            System.out.print(labelX[x]+" | ");
            for (int y = 0; y < size; y++) {
                if (fields[x][y] != null) { //field = object
                    System.out.print(fields[x][y].playerSymbol+" | ");
                } else {
                    System.out.print(". | "); //empty field = null
                }
            }System.out.println();
        }
    }




}
