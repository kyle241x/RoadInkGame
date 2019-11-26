package comp1110.ass2;

import java.util.Set;

public class Tile {
    public String tileID;
    /** shape string represents Top -> Left -> Bottom -> Right routes */
    public String shape;
    public int orientation;

    public static final Set<String> SPECIAL_TILES = Set.of("S0", "S1", "S2", "S3", "S4");
    public static final Set<String> TWO_PORTS_TILES = Set.of("A0", "A1", "A4", "A5", "B0", "B1");
    public static final Set<String> THREE_PORTS_TILES = Set.of("A2", "A3");
    public static final Set<String> FOUR_PORTS_TILES = Set.of("S0", "S1", "S2", "S3", "S4", "S5", "B2");

    Tile(String ID) {
        tileID = ID;
        orientation = 0;
        switch (tileID.substring(0,2)) {
            case "S0":
                shape = "HHRH";
                break;
            case "S1":
                shape = "HRRR";
                break;
            case "S2":
                shape = "HHHH";
                break;
            case "S3":
                shape = "RRRR";
                break;
            case "S4":
                shape = "HHRR";
                break;
            case "S5":
                shape = "HRHR";
                break;
            case "A0":
                shape = "RR00"; // railways
                break;
            case "A1":
                shape = "R0R0";
                break;
            case "A2":
                shape = "R0RR";
                break;
            case "A3":
                shape = "H0HH"; // highways
                break;
            case "A4":
                shape = "H0H0";
                break;
            case "A5":
                shape = "HH00";
                break;
            case "B0":  // stations
                shape = "H0R0";
                break;
            case "B1":
                shape = "H00R";
                break;
            case "B2":  // overpass
                shape = "HRHR";
        }
    }

    public static int getPortNum(String tileID) {
        if (TWO_PORTS_TILES.contains(tileID)) {
            return 2;
        } else if (THREE_PORTS_TILES.contains(tileID)) {
            return 3;
        } else if (FOUR_PORTS_TILES.contains(tileID)) {
            return 4;
        } else {
            return 0;
        }
    }


    /*
            clockwise rotation.
         */
    public Tile rotate90Degree() {
        shape = shape.substring(1) + shape.charAt(0);
        return this;
    }

    public Tile rotate180Degree() {
        return this.rotate90Degree().rotate90Degree();
    }

    public Tile rotate270Degree() {
        return this.rotate180Degree().rotate90Degree();
    }

    // To mirror the tile over the y-axis.
    public Tile horizontalFlip() {
        shape = "" + shape.charAt(0) + shape.charAt(3) + shape.charAt(2) + shape.charAt(1);
        return this;
    }

    // To mirror the tile over the x-axis.
    public Tile verticalFlip() {
        shape = "" + shape.charAt(2) + shape.charAt(1) + shape.charAt(0) + shape.charAt(3);
        return this;
    }

    public Tile changeOrientation(int orientation) {
        switch (orientation) {
            case 0:
                break;
            case 1:
                rotate90Degree().orientation = 1;
                break;
            case 2:
                rotate180Degree().orientation = 2;
                break;
            case 3:
                rotate270Degree().orientation = 3;
                break;
            case 4:
                horizontalFlip().orientation = 4;
                break;
            case 5:
                horizontalFlip().rotate90Degree().orientation = 5;
                break;
            case 6:
                horizontalFlip().rotate180Degree().orientation = 6;
                break;
            case 7:    // case 7
                horizontalFlip().rotate270Degree().orientation = 7;
                break;
        }

        return this;
    }

    public String getTileShape() {
        return this.shape;
    }
}

