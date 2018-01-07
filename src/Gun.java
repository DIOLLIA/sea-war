

public class Gun extends Ship {

    static boolean allShipsAreDead1 = false;
    static boolean allShipsAreDead2 = false;
    String DECK = "0 ";
    private static final String SHOOTED_CELL = "W ";
    private static final String STAR = "* ";
    private static int deadShips1 = 0;
    private static int deadShips2 = 0;

    protected boolean isShotFinished(Field field, Ship.Point coordinate, Gamer.TypeOfPlayer playerType) { //todo когда последний корабль подстрелен, пограмма не завершает цикл
        boolean hit = false;
        boolean wonded = false;
        boolean shipAreDead = false;
        int[] shootedCell = {coordinate.getX(), coordinate.getY()};
        String[][] bigGameField = field.getBigGameField();
        if (bigGameField[shootedCell[0]][shootedCell[1]].equals(DECK)) {
            field.seeYourCoordinateCorrect(coordinate);
            bigGameField[shootedCell[0]][shootedCell[1]] = SHOOTED_CELL;
            if (coordinate.getX() > 1) {
                if (bigGameField[shootedCell[0] - 1][shootedCell[1]].equals(DECK)) {
                    wonded = true;
                }
            }
            if (coordinate.getX() < 10) {
                if (bigGameField[shootedCell[0] + 1][shootedCell[1]].equals(DECK)) {
                    wonded = true;
                }
            }
            if (coordinate.getY() > 1) {
                if (bigGameField[shootedCell[0]][shootedCell[1] - 1].equals(DECK)) {
                    wonded = true;
                }
            }
            if (coordinate.getY() < 10)
                if (bigGameField[shootedCell[0]][shootedCell[1] + 1].equals(DECK)) {
                    wonded = true;
                }

            if (wonded == true) {
                System.out.println("wounded!");
            } else {
                System.out.println("drowned");
                if (Game.nameOfKompukter.equals("KoMPukTeP###111")) {
                    deadShips1++;
                } else deadShips2++; //todo при игре комп\комп все очки идут в один счетчик
                shipAreDead = true;
            }
        } else if (bigGameField[shootedCell[0]][shootedCell[1]].equals("* ") || bigGameField[shootedCell[0]][shootedCell[1]].equals("~ ")) {
            field.seeYourCoordinateCorrect(coordinate);
            System.out.println("missed");
            bigGameField[shootedCell[0]][shootedCell[1]] = "\" ";
            hit = true;
        } else if (bigGameField[shootedCell[0]][shootedCell[1]].equals("\" ") || bigGameField[shootedCell[0]][shootedCell[1]].equals("W ")) {
            if (playerType.equals(Gamer.TypeOfPlayer.Human)) {
                System.out.println("That cell is almost shoted");
            } else if (bigGameField[shootedCell[0]][shootedCell[1]].equals("` ") && playerType.equals(Gamer.TypeOfPlayer.Human)) {
                System.out.println("there is no necessity to shot here");
            } else
                hit = false;
        }
        if (shipAreDead) {
            createDeadFieldAroundShip(field.getBigGameField(), field);
        }
        if (deadShips1 >= 10) {
            this.allShipsAreDead1 = true;
            hit = true;
        } else if (deadShips2 >= 10) {
            this.allShipsAreDead2 = true;
            hit = true;

        }

        return hit;
    }

    private void createDeadFieldAroundShip(String[][] bigGameField, Field field) {

        for (int y = 1; y < bigGameField.length; y++) {
            for (int x = 1; x < bigGameField.length; x++) {

                if (bigGameField[y][x].equals(SHOOTED_CELL)) {//5
                    if (x <= 10 && y < 10 && x > 1 && y >= 1) {
                        if (bigGameField[y + 1][x - 1].equals(STAR)) {//1
                            bigGameField[y + 1][x - 1] = ("` ");
                        }
                    }
                    if (x <= 10 && y < 10 && x >= 1 && y >= 1) {
                        if (bigGameField[y + 1][x].equals(STAR)) {//2
                            bigGameField[y + 1][x] = ("` ");
                        }
                    }
                    if (x < 10 && y < 10 && x >= 1 && y >= 1) {
                        if (bigGameField[y + 1][x + 1].equals(STAR)) {//3
                            bigGameField[y + 1][x + 1] = ("` ");
                        }
                    }
                    if (x <= 10 && y <= 10 && x > 1 && y >= 1) {
                        if (bigGameField[y][x - 1].equals(STAR)) {//4
                            bigGameField[y][x - 1] = ("` ");
                        }
                    }
                    if (x < 10 && y <= 10 && x >= 1 && y >= 1) {
                        if (bigGameField[y][x + 1].equals(STAR)) {//6
                            bigGameField[y][x + 1] = ("` ");
                        }
                    }
                    if (x <= 10 && y <= 10 && x > 1 && y > 1) {
                        if (bigGameField[y - 1][x - 1].equals(STAR)) {//7
                            bigGameField[y - 1][x - 1] = ("` ");
                        }
                    }
                    if (x <= 10 && y <= 10 && x >= 1 && y > 1) {
                        if (bigGameField[y - 1][x].equals(STAR)) {//8
                            bigGameField[y - 1][x] = ("` ");
                        }
                    }
                    if (x < 10 && y <= 10 && x >= 1 && y > 1) {
                        if (bigGameField[y - 1][x + 1].equals(STAR)) {//9
                            bigGameField[y - 1][x + 1] = ("` ");
                        }
                    }

                }
            }


        }

    }
}