package com.mycompany.battleship;

import java.util.ArrayList;
import java.util.HashMap;

public enum Ship {
    
    aircraftCarrier("Aircraft Carrier", 5, 5),
    battleship("Battleship", 4, 4),
    submarine("Submarine", 3, 3),
    cruiser("Cruiser", 3, 3),
    destroyer("Destroyer", 2, 2),
    aircraftCarrierP2("Aircraft Carrier", 5, 5),
    battleshipP2("Battleship", 4, 4),
    submarineP2("Submarine", 3, 3),
    cruiserP2("Cruiser", 3, 3),
    destroyerP2("Destroyer", 2, 2),;

    private String name;
    private int size;
    private int hp;
    private ArrayList<String> coords;
    
    Ship(String name, int size, int hp) {
        this.name = name;
        this.size = size;
        this.hp = hp;
        this.coords = new ArrayList<>();
    }

    public int getSize() {
        return this.size;
    }

    public int getHP() {
        return this.hp;
    }

    public void lowerHp() {
        hp--;
    }

    public String getName() {
        return this.name;
    }

    public void addCoords(String coord) {
        this.coords.add(coord);
    }

    public ArrayList<String> getCoords() {
        return this.coords;
    }

    public boolean checkIfHit(int row, int column) {
        char[] rowValues = {48, 49, 50, 51, 52, 53, 54, 55, 56, 57};

        for (String coord : coords) {
            int row1 = Integer.valueOf(coord.charAt(0));
            int column1 = Integer.valueOf(coord.charAt(1));

            for (int i = 0; i < rowValues.length; i++) {
                if (coord.charAt(0) == rowValues[i]) {
                    row1 = i;
                }

                if (coord.charAt(1) == rowValues[i]) {
                    column1 = i;
                }
            }

            if (row == row1 && column == column1) {
                return true;
            }
        }

        return false;
    }
}
