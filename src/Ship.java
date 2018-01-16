import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

/**
 * Created by Andre on 19.10.2017.
 */
public class Ship {

    private Point coordinate;
    private int deckAmount;

    public int getDeckAmount() {
        return deckAmount;
    }

    public Ship(int deckAmount) {
        this.deckAmount = deckAmount;
    }

    public void setCoordinate(Point coordinate) {
        this.coordinate = coordinate;
    }

    public Point getCoordinate() {
        return coordinate;
    }

    public static Point generateCoordinate() {
        return new Point((int) (Math.random() * 10 + 1), (int) (Math.random() * 10 + 1));
    }

    public static Point getCoordinateFromHuman() {
        boolean successCoordinateFormat = false;
        int x = 0;
        int y = 0;
        BufferedReader bfreader = new BufferedReader(new InputStreamReader(System.in));
        String coordinateFromHuman = null;
        while (!successCoordinateFormat) {
            try {
                coordinateFromHuman = bfreader.readLine();
            } catch (IOException incorrectInsert) {
                System.out.println("неверный ввод");
            }
            char[] coordinateFromHumanToChar = coordinateFromHuman.toUpperCase().toCharArray();

            if (coordinateFromHumanToChar[0] == 'A')
                x = 1;
            else if (coordinateFromHumanToChar[0] == 'B')
                x = 2;
            else if (coordinateFromHumanToChar[0] == 'C')
                x = 3;
            else if (coordinateFromHumanToChar[0] == 'D')
                x = 4;
            else if (coordinateFromHumanToChar[0] == 'E')
                x = 5;
            else if (coordinateFromHumanToChar[0] == 'F')
                x = 6;
            else if (coordinateFromHumanToChar[0] == 'G')
                x = 7;
            else if (coordinateFromHumanToChar[0] == 'H')
                x = 8;
            else if (coordinateFromHumanToChar[0] == 'I')
                x = 9;
            else if (coordinateFromHumanToChar[0] == 'J')
                x = 10;
            if (x>0&&x<=10){
               }
                else {
                System.out.println("incorrect litter format\"litera type");}
            try {
                y = Integer.parseInt(String.valueOf(coordinateFromHumanToChar[1]));
                if (coordinateFromHumanToChar.length == 3 && coordinateFromHumanToChar[1] == '1' && coordinateFromHumanToChar[2] == '0') {
                    y = 10;
                }
                if ((Character) coordinateFromHumanToChar[0] instanceof Character && ((Integer) y instanceof Integer)) {
                    successCoordinateFormat = true;
                }
            } catch (Exception wrongFormatOfCoordinate) {
                System.out.println("Incorrect coordinate format.Try again, for example W5");
                successCoordinateFormat = false;
            }

        }

        return new Point(y, x);
    }

    static class Point {

        private int x;
        private int y;

        public Point(int x, int y) {
            this.x = x;
            this.y = y;
        }
        public int getX() {
            return x;
        }

        public void setX(int x) {
            this.x = x;
        }

        public int getY() {
            return y;
        }

        public void setY(int y) {
            this.y = y;
        }
    }

}
