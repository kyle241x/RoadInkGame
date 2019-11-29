package comp1110.ass2;

public enum Dice {
    A("A", 6, 3),
    B("B", 3, 1);

    public String type;
    public int faces;
    public int times;

    Dice(String type, int faces, int times) {
        this.type = type;
        this.faces = faces;
        this.times = times;
    }

    static String generateATile(Dice die) {
        return  die.type + (int)(Math.random()*die.faces);
    }

    static String generateFourTiles() {
        String tileString = "";
        for(int i=0; i<A.times; i++) {
            tileString += generateATile(A);
        }
        tileString += generateATile(B);
        return tileString;
    }
    //main
    public static void main(String[] args) {
        System.out.println(Dice.generateFourTiles());
    }
}
