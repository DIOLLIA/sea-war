import java.util.Random;

/**
 * Created by Andre on 19.10.2017.
 */
public class Ship {

    private Point coordinate;
    private int deckAmount;

    public int getDeckAmount() {
        return deckAmount;
    }

    public void setDeckAmount(int deckAmount) {
        this.deckAmount = deckAmount;
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
