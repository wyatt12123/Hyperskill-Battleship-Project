package com.mycompany.battleship;

import java.util.ArrayList;
import java.util.Scanner;

public class Gamefield {
    private String[][] gameField;
    private String[][] fogOfWar;
    private ArrayList<Ship> shipList;

    public Gamefield() {
        this.gameField = new String[10][10];
        this.fogOfWar = new String[10][10];
        this.shipList = new ArrayList<>();
    }

    public void addShip(Ship ship) {
        shipList.add(ship);
    }

    public void initializeField() {
        for (int i = 0; i < gameField.length; i++) {
            for (int j = 0; j < gameField.length; j++) {
                gameField[i][j] = "~";
            }
        }

        for (int i = 0; i < fogOfWar.length; i++) {
            for (int j = 0; j < fogOfWar.length; j++) {
                fogOfWar[i][j] = "~";
            }
        }
    }

    public void printField() {
        char chDecimal = 65;

        for (int i = 1; i <= 10; i++) {
            System.out.print(" " + i);
        }

        System.out.println();

        for (int i = 0; i < gameField.length; i++) {
            System.out.print(chDecimal);
            for (int j = 0; j < gameField.length; j++) {
                System.out.print(" " + gameField[i][j]);
            }
            chDecimal++;
            System.out.println();
        }

        System.out.println();
    }

    public void printFogOfWar() {
        char chDecimal = 65;

        for (int i = 1; i <= 10; i++) {
            System.out.print(" " + i);
        }

        System.out.println();

        for (int i = 0; i < fogOfWar.length; i++) {
            System.out.print(chDecimal);
            for (int j = 0; j < fogOfWar.length; j++) {
                System.out.print(" " + fogOfWar[i][j]);
            }
            chDecimal++;
            System.out.println();
        }

        System.out.println();
    }

    public void placeShip() throws Exception {
        int listKeeper = 0;
        char[] rowValues = {97, 98, 99, 100, 101, 102, 103, 104, 105, 106};


        while(true) {
            if(listKeeper > 4){
                break;
            }

            Ship ship = shipList.get(listKeeper);

            System.out.printf("Enter the coordinates of the %s (%d cells)\n", ship.getName(), ship.getSize());
            Scanner scanner = new Scanner(System.in);
            String userInput = scanner.nextLine();
            userInput = userInput.trim();

            String[] coords = userInput.split(" ");
            String coord1 = coords[0];
            String coord2 = coords[1];
            int row = 0;
            int row2 = 0;
            int column = Integer.valueOf(coord1.substring(1)) - 1;
            int column2 = Integer.valueOf(coord2.substring(1)) - 1;

            for (int i = 0; i < rowValues.length; i++) {
                if (coord1.toLowerCase().charAt(0) == rowValues[i]) {
                    row = i;
                }

                if (coord2.toLowerCase().charAt(0) == rowValues[i]) {
                    row2 = i;
                }
            }

            if (row > row2) {
                int temp = row2;
                row2 = row;
                row = temp;
            }

            if (column > column2) {
                int temp = column2;
                column2 = column;
                column = temp;
            }

            if (occupiedSpaceCheck(row, column, row2, column2, ship, gameField) && validPlacementCheck(row, column, row2, column2, ship, gameField)) {
                if (row == row2) {
                    for (int i = column; i <= column2; i++) {
                        gameField[row][i] = "O";
                        ship.addCoords(String.valueOf(row) + String.valueOf(i));
                    }

                } else if (column == column2) {
                    for (int i = row; i <= row2; i++) {
                        gameField[i][column] = "O";
                        ship.addCoords(String.valueOf(i) + String.valueOf(column));
                    }

                }

                printField();
                listKeeper++;
            }
        }
    }

    public boolean occupiedSpaceCheck(int row1, int column1, int row2, int column2, Ship ship, String[][] gamefield) {
        if (row1 == row2 && column2 - column1 + 1 == ship.getSize()) {
            for (int i = column1; i < column2; i++) {
                if (!(gamefield[row1][i].contains("~"))) {
                    System.out.println("Invalid coordinates\n");
                    return false;
                }
            }
        } else if (column1 == column2 && row2 - row1 + 1 == ship.getSize()) {
            for (int i = row1; i < row2; i++) {
                if (!(gamefield[i][column1].contains("~"))) {
                    System.out.println("Invalid coordinates\n");
                    return false;
                }
            }
        } else {
            System.out.printf("Error! Wrong length of the %s! Try again:\n", ship.getName());
            return false;
        }

        return true;
    }

    public boolean validPlacementCheck(int row, int column, int row2, int column2, Ship ship, String[][] gamefield) {
        //checks everything
        if (column == column2 && column < gamefield.length - 1 && column > 0) {
            for (int i = row; i < row2; i++) {
                if (!gamefield[i][column - 1].contains("~")) {
                    System.out.println("Error! You placed it too close to another one. Try again:");
                    return false;
                } else if (!gamefield[i][column + 1].contains("~")) {
                    System.out.println("Error! You placed it too close to another one. Try again:");
                    return false;
                }
            }
            //checks top and bottom of column
            if (row > 0 && row2 < gamefield.length - 1) {
                if (!gamefield[row - 1][column].contains("~")) {
                    System.out.println("Error! You placed it too close to another one. Try again:");
                    return false;
                } else if (!gamefield[row2 + 1][column].contains("~")) {
                    System.out.println("Error! You placed it too close to another one. Try again:");
                    return false;
                }
                //checks just the top
            } else if (row > 0) {
                if (!gamefield[row - 1][column].contains("~")) {
                    System.out.println("Error! You placed it too close to another one. Try again:");
                    return false;
                }
                //checks just the bottom
            } else if (row2 < gamefield.length - 1) {
                if (!gamefield[row + 1][column].contains("~")) {
                    System.out.println("Error! You placed it too close to another one. Try again:");
                    return false;
                }
            }
            //checks everything besides right side
        } else if (column == column2 && column > 0) {
            for (int i = row; i < row2; i++) {
                if (!gamefield[i][column - 1].contains("~")) {
                    System.out.println("Error! You placed it too close to another one. Try again:");
                    return false;
                }
            }
            //checks top and bottom of column
            if (row > 0 && row2 < gamefield.length - 1) {
                if (!gamefield[row - 1][column].contains("~")) {
                    System.out.println("Error! You placed it too close to another one. Try again:");
                    return false;
                }
                //checks just the top
            } else if (row > 0) {
                if (!gamefield[row - 1][column].contains("~")) {
                    System.out.println("Error! You placed it too close to another one. Try again:");
                    return false;
                }
                //checks just the bottom
            } else if (row2 < gamefield.length - 1) {
                if (!gamefield[row + 1][column].contains("~")) {
                    System.out.println("Error! You placed it too close to another one. Try again:");
                    return false;
                }
            }
            // checks everythings  except left side
        } else if (column == column2 && column < gamefield.length - 1) {
            for (int i = row; i < row2; i++) {
                if (!gamefield[i][column + 1].contains("~")) {
                    System.out.println("Error! You placed it too close to another one. Try again:");
                    return false;
                }
            }
            //checks top and bottom of column
            if (row > 0 && row2 < gamefield.length - 1) {
                if (!gamefield[row - 1][column].contains("~")) {
                    System.out.println("Error! You placed it too close to another one. Try again:");
                    return false;
                } else if (!gamefield[row2 + 1][column].contains("~")) {
                    System.out.println("Error! You placed it too close to another one. Try again:");
                    return false;
                }
                //checks just the top
            } else if (row > 0) {
                if (!gamefield[row - 1][column].contains("~")) {
                    System.out.println("Error! You placed it too close to another one. Try again:");
                    return false;
                }
                //checks just the bottom
            } else if (row2 < gamefield.length - 1) {
                if (!gamefield[row + 1][column].contains("~")) {
                    System.out.println("Error! You placed it too close to another one. Try again:");
                    return false;
                }
            }
        }

        if (row == row2 && row < gamefield.length - 1 && row > 0) {
            for (int i = column; i < column2; i++) {
                if (!gamefield[row - 1][i].contains("~")) {
                    System.out.println("Error! You placed it too close to another one. Try again:");
                    return false;
                } else if (!gamefield[row + 1][i].contains("~")) {
                    System.out.println("Error! You placed it too close to another one. Try again:");
                    return false;
                }
            }
            //checks top and bottom of column
            if (column > 0 && column2 < gamefield.length - 1) {
                if (!gamefield[row][column - 1].contains("~")) {
                    System.out.println("Error! You placed it too close to another one. Try again:");
                    return false;
                } else if (!gamefield[row][column2 + 1].contains("~")) {
                    System.out.println("Error! You placed it too close to another one. Try again:");
                    return false;
                }
                //checks just the top
            } else if (column > 0) {
                if (!gamefield[row][column - 1].contains("~")) {
                    System.out.println("Error! You placed it too close to another one. Try again:");
                    return false;
                }
                //checks just the bottom
            } else if (column2 < gamefield.length - 1) {
                if (!gamefield[row][column + 1].contains("~")) {
                    System.out.println("Error! You placed it too close to another one. Try again:");
                    return false;
                }
            }
            //checks everything besides right side
        } else if (row == row2 && row > 0) {
            for (int i = column; i < column2; i++) {
                if (!gamefield[row - 1][i].contains("~")) {
                    System.out.println("Error! You placed it too close to another one. Try again:");
                    return false;
                }
            }
            //checks top and bottom of column
            if (column > 0 && column2 < gamefield.length - 1) {
                if (!gamefield[row][column - 1].contains("~")) {
                    System.out.println("Error! You placed it too close to another one. Try again:");
                    return false;
                } else if (!gamefield[row][column2 + 1].contains("~")) {
                    System.out.println("Error! You placed it too close to another one. Try again:");
                    return false;
                }
                //checks just the top
            } else if (column > 0) {
                if (!gamefield[row][column - 1].contains("~")) {
                    System.out.println("Error! You placed it too close to another one. Try again:");
                    return false;
                }
                //checks just the bottom
            } else if (column2 < gamefield.length - 1) {
                if (!gamefield[row][column + 1].contains("~")) {
                    System.out.println("Error! You placed it too close to another one. Try again:");
                    return false;
                }
            }
            // checks everythings  except left side
        } else if (row == row2 && row < gamefield.length - 1) {
            for (int i = column; i < column2; i++) {
                if (!gamefield[row + 1][i].contains("~")) {
                    System.out.println("Error! You placed it too close to another one. Try again:");
                    return false;
                }
            }
            //checks top and bottom of column
            if (column > 0 && column2 < gamefield.length - 1) {
                if (!gamefield[row][column - 1].contains("~")) {
                    System.out.println("Error! You placed it too close to another one. Try again:");
                    return false;
                } else if (!gamefield[row][column2 + 1].contains("~")) {
                    System.out.println("Error! You placed it too close to another one. Try again:");
                    return false;
                }
                //checks just the top
            } else if (column > 0) {
                if (!gamefield[row][column - 1].contains("~")) {
                    System.out.println("Error! You placed it too close to another one. Try again:");
                    return false;
                }
                //checks just the bottom
            } else if (column2 < gamefield.length - 1) {
                if (!gamefield[row][column + 1].contains("~")) {
                    System.out.println("Error! You placed it too close to another one. Try again:");
                    return false;
                }
            }
        }

        return true;
    }

    public void setGrid(int row, int column, String status) {
        gameField[row][column] = status;
        fogOfWar[row][column] = status;
    }

    public ArrayList<Ship> getShipList() {
        return shipList;
    }
}

