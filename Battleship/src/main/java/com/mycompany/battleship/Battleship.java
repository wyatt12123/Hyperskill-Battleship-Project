package com.mycompany.battleship;

import java.util.ArrayList;
import java.util.Scanner;

public class Battleship {

    private Gamefield player1;
    private Gamefield player2;

    public Battleship() {
        this.player1 = new Gamefield();
        this.player2 = new Gamefield();
    }

    public void start() throws Exception {
        Scanner scanner = new Scanner(System.in);
        player1.addShip(Ship.aircraftCarrier);
        player1.addShip(Ship.battleship);
        player1.addShip(Ship.submarine);
        player1.addShip(Ship.cruiser);
        player1.addShip(Ship.destroyer);
        player2.addShip(Ship.aircraftCarrierP2);
        player2.addShip(Ship.battleshipP2);
        player2.addShip(Ship.submarineP2);
        player2.addShip(Ship.cruiserP2);
        player2.addShip(Ship.destroyerP2);

        player1.initializeField();
        player2.initializeField();

        System.out.println("Player 1, place your ships on the game field");
        player1.printField();
        player1.placeShip();
        System.out.println("Press Enter and pass the move to another player\n ...");
        scanner.nextLine();

        System.out.println("Player 2, place your ships on the game field");
        player2.printField();
        player2.placeShip();
        System.out.println("Press Enter and pass the move to another player\n ...");
        scanner.nextLine();

        shootGun();
    }

    public void shootGun() {
        char[] rowValues = {97, 98, 99, 100, 101, 102, 103, 104, 105, 106, 107};

        ArrayList<Ship> player1Ships = player1.getShipList();
        ArrayList<Ship> player2Ships = player2.getShipList();

        int player1SunkShips = 0;
        int player2SunkShips = 0;

        int currentPlayer = 1;

        while (true) {
            boolean hitShip = false;
            boolean columnLessThanMax = false;
            boolean successfullyFoundRow = false;

            if(currentPlayer == 1){
                player2.printFogOfWar();
                System.out.println("---------------------");
                player1.printField();
            } else {
                player1.printFogOfWar();
                System.out.println("---------------------");
                player2.printField();
            }

            System.out.println("Player " + currentPlayer + ", it's your turn: \n");

            Scanner scanner = new Scanner(System.in);
            String userInput = scanner.nextLine();
            userInput = userInput.trim();

            int row = 0;
            int column = Integer.valueOf(userInput.substring(1)) - 1;

            if (currentPlayer == 1) {
                if (column < 10) {
                    columnLessThanMax = true;
                }

                for (int i = 0; i < rowValues.length; i++) {
                    if (userInput.toLowerCase().charAt(0) == rowValues[i]) {
                        row = i;
                        successfullyFoundRow = true;
                    }
                }

                if (row < 10 && column < 10 && successfullyFoundRow == true && columnLessThanMax == true) {
                    for (Ship ship : player2Ships) {
                        if (ship.checkIfHit(row, column)) {
                            player2.setGrid(row, column, "X");
                            ship.lowerHp();
                            if (ship.getHP() == 0) {
                                player2SunkShips++;
                                System.out.println("You sank a ship!");
                            } else {
                                System.out.println("You hit a ship!\n");
                            }
                            hitShip = true;
                        }
                    }
                } else {
                    System.out.println("Error! You entered the wrong coordinates! Try again: \n");
                }

                if (hitShip == false && successfullyFoundRow == true && columnLessThanMax == true) {
                    System.out.println("You missed!\n");
                    player2.setGrid(row, column, "M");
                    player2.printFogOfWar();
                }

                if (player2SunkShips == 5) {
                    System.out.println("You sank the last ship. You won. Congratulations!\n");
                    break;
                }

                currentPlayer = 2;

                System.out.println("Press Enter and pass the move to another player\n ...");
                scanner.nextLine();

            } else if (currentPlayer == 2) {
                if (column < 10) {
                    columnLessThanMax = true;
                }

                for (int i = 0; i < rowValues.length; i++) {
                    if (userInput.toLowerCase().charAt(0) == rowValues[i]) {
                        row = i;
                        successfullyFoundRow = true;
                    }
                }

                if (row < 10 && column < 10 && successfullyFoundRow == true && columnLessThanMax == true) {
                    for (Ship ship : player1Ships) {
                        if (ship.checkIfHit(row, column)) {
                            player1.setGrid(row, column, "X");
                            ship.lowerHp();
                            if (ship.getHP() == 0) {
                                player1SunkShips++;
                                System.out.println("You sank a ship!");
                            } else {
                                System.out.println("You hit a ship!\n");
                            }
                            hitShip = true;
                        }
                    }
                } else {
                    System.out.println("Error! You entered the wrong coordinates! Try again: \n");
                }

                if (hitShip == false && successfullyFoundRow == true && columnLessThanMax == true) {
                    System.out.println("You missed!\n");
                    player1.setGrid(row, column, "M");
                    player1.printFogOfWar();
                }

                if (player1SunkShips == 5) {
                    System.out.println("You sank the last ship. You won. Congratulations!\n");
                    break;
                }

                currentPlayer = 1;

                System.out.println("Press Enter and pass the move to another player\n ...");
                scanner.nextLine();
            }
        }
    }
}