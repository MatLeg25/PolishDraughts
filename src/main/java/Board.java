public class Board {

    static String Player1symbol;// = "\033[0;31m O \033[0m"; //red
    static String Player2symbol;// = "\033[0;33m O \033[0m"; // yellow
    static String emptyField;

    //create variable of this class
    static int size;
    static int rw;
    static String[] labelX;
    static String[] labelY;
    public static Pawn[][] fields;

    //set the values for variable of this class
    public static void SetInitData(int Bsize, String [] symbols) {
        Board.size =Bsize;

        if (Bsize%2!=0) {Bsize+=1;}
        Board.rw = (Bsize/2)-1; //define number of rows with pawns

        labelX = new String[size];
        labelY = new String[size];
        fields = new Pawn[2 * size][2 * size];

        Player1symbol = symbols[0];
        Player2symbol = symbols[1];
        emptyField = symbols[2];
    }

    public Board() {
        //create 1object BOARD  and objects Pawns with appropriate positions
        for (int x = 0; x < size; x++) {
            for (int y = 0; y < size; y++) {
                if ((y % 2 == 1 && x % 2 == 0 && x < rw) || (y % 2 == 0 && x % 2 == 1 && x < rw)) {
                    fields[x][y] = new Pawn(x, y,Player1symbol);
                }
                else if ((y % 2 == 1 && x % 2 == 0 && x >= size - rw) || (y % 2 == 0 && x % 2 == 1 && x >= size - rw)) {
                    fields[x][y] = new Pawn(x, y,Player2symbol);
                }
                else {
                    fields[x][y] = null;
                    //fields[x][y] = new Pawn(x, y,"."); //to create empty-field object
                }
            }
        }
    }

    public static void displayBoard() {

        //fill label X
        for (int i = 0; i < size; i++) {
            labelX[i] = String.valueOf(i);
        }
        //display Label X by for each loop
        System.out.print("+ |  ");
        for (String j : labelX) {
            System.out.print(j+"  +  ");
        }
        System.out.println();

        //DISPLAY GAME BOARD
        for (int x = 0; x < size; x++) {
            System.out.print(labelX[x]+" | ");
            for (int y = 0; y < size; y++) {
                if (fields[x][y] != null) { //field = object
                    System.out.print(fields[x][y].playerSymbol+" | ");
                } else {
                    System.out.print(emptyField); //empty field = null
                }
            }
            System.out.println();
        }
    }




}
