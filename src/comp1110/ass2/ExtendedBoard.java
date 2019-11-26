package comp1110.ass2;

import java.util.Arrays;

public class ExtendedBoard extends Board {
    /*  @ < A and / < 0 in ASCII table.
        @ represents the row character above row A, and
        / represents the column number to the left of column 0.
     */
    public static final String[] EXITS = {
            "A4@10", "A1@30", "A4@50",
            "A1B/1", "A4D/1", "A1F/1",
            "A4H10", "A1H30", "A4H50",
            "A1B71", "A4D71", "A1F71"
    };
    public static String getExitsStr() {
        var arrayStr = Arrays.toString(EXITS);
        var exitsStr = arrayStr.substring(1, arrayStr.length()-1);
        exitsStr = exitsStr.replace(", ", "");
        return exitsStr;
    }
}
