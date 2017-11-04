import java.util.Random;

/**
 * Created by Andre on 19.10.2017.
 */
public class Ship {
    public Direction getRandomDirection() {
        return randomDirection;
    }

    public void setRandomDirection(Direction randomDirection) {
        this.randomDirection = randomDirection;
    }

    private Direction randomDirection;
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

    enum Direction {left, right, up, down}

    public static Point getCoordinate() { // метод берет начальную координату и далее из метода region берет количество доступных направлений для дальнейшего расположения корабля
        return new Point((int) (Math.random() * 10 + 1), (int) (Math.random() * 10 + 1));

    }


    public Direction findRandomDirection() {

        int a;
        Random random = new Random();
        a = random.nextInt(4) + 1;
        switch (a) {
            case (1):
                randomDirection = Direction.up;
            case (2):
                randomDirection = Direction.down;

            case (3):
                randomDirection = Direction.left;

            case (4):
                randomDirection = Direction.right;
        }
        return randomDirection;
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
