package comp1110.ass2;

import java.util.*;

public class RailroadInk {
    /**
     * Determine whether a tile placement string is well-formed:
     * - it consists of exactly 5 characters;
     * - the first character represents a die A or B, or a special tile S
     * - the second character indicates which tile or face of the die (0-5 for die A and special tiles, or 0-2 for die B)
     * - the third character represents the placement row A-G
     * - the fourth character represents the placement column 0-6
     * - the fifth character represents the orientation 0-7
     *
     * @param tilePlacementString a candidate tile placement string
     * @return true if the tile placement is well formed
     */
    public static boolean isTilePlacementWellFormed(String tilePlacementString) {
        // FIXME Task 2: determine whether a tile placement is well-formed

        char tileType = tilePlacementString.charAt(0);
        int tileNum = Character.getNumericValue(tilePlacementString.charAt(1));

        if (tilePlacementString.length() != 5) {
            return false;
        } else {
            if (tileType == 'A' || tileType == 'S') {
                return (tileNum >= 0 && tileNum <= 5) && isValidTilePlacement(tilePlacementString);
            } else if (tileType == 'B') {
                return (tileNum >= 0 && tileNum <= 2) && isValidTilePlacement(tilePlacementString);
            } else {
                return false;
            }
        }
    }

    private static boolean isValidTilePlacement(String tilePlacementString) {
        char rowChar = tilePlacementString.charAt(2);
        int colNum = Character.getNumericValue(tilePlacementString.charAt(3));
        int orientation = Character.getNumericValue(tilePlacementString.charAt(4));

        if (rowChar >= 'A' && rowChar <= 'G') {
            return (colNum <= 6 && colNum >= 0) && (orientation >= 0 && orientation <= 7);
        } else {
            return false;
        }
    }

    /**
     * Determine whether a board string is well-formed:
     * - it consists of exactly N five-character tile placements (where N = 1 .. 31);
     * - each piece placement is well-formed
     * - no more than three special tiles are included
     *
     * @param boardString a board string describing the placement of one or more pieces
     * @return true if the board string is well-formed
     */
    public static boolean isBoardStringWellFormed(String boardString) {
        // FIXME Task 3: determine whether a board string is well-formed

        if (boardString == null || boardString.isBlank()) {
            return false;
        } else {
            int strLength = boardString.length();
            boolean lengthMulOfFive = (strLength % 5 == 0) && (strLength <= 31 * 5); // rule 1
            boolean validPlacement = true;  // rule 2
            int specialTileCounter = 0; // rule 3

            if (lengthMulOfFive) {
                for (int i = 0; i < strLength; i += 5) {
                    String tileStr = boardString.substring(i, i + 5);
                    if (tileStr.contains("S")) {
                        specialTileCounter++;
                    }
                    validPlacement &= isTilePlacementWellFormed(tileStr);
                }
            }

            return lengthMulOfFive && validPlacement && (specialTileCounter <= 3);
        }
    }


    /**
     * Determine whether the provided placements are neighbours connected by at least one validly connecting edge.
     * For example,
     * - areConnectedNeighbours("A3C10", "A3C23") would return true as these tiles are connected by a highway edge;
     * - areConnectedNeighbours("A3C23", "B1B20") would return false as these neighbouring tiles are disconnected;
     * - areConnectedNeighbours("A0B30", "A3B23") would return false as these neighbouring tiles have an
     * invalid connection between highway and railway; and
     * areConnectedNeighbours("A0B30", "A3C23") would return false as these tiles are not neighbours.
     *
     * @return true if the placements are connected neighbours
     * @Author Tianjun Peng
     */
    public static boolean areConnectedNeighbours(String tilePlacementStringA, String tilePlacementStringB) {
        // FIXME Task 5: determine whether neighbouring placements are connected

        // Form two corresponding tiles.
        Tile tileA = new Tile(tilePlacementStringA.substring(0, 2));
        Tile tileB = new Tile(tilePlacementStringB.substring(0, 2));
        tileA = tileA.changeOrientation(Character.getNumericValue(tilePlacementStringA.charAt(4)));
        tileB = tileB.changeOrientation(Character.getNumericValue(tilePlacementStringB.charAt(4)));
        // Get the two placement strings.
        String placementA = tilePlacementStringA.substring(2, 4);
        String placementB = tilePlacementStringB.substring(2, 4);

        if (areNeighbours(placementA, placementB)) {
            if (areInSameRow(placementA, placementB)) {
                if (placementA.charAt(1) < placementB.charAt(1)) {  // A is on the left of B.
                    return (tileA.shape.charAt(3) == tileB.shape.charAt(1)) && (tileA.shape.charAt(3) != '0');
                } else {    // B is on the left of A.
                    return (tileA.shape.charAt(1) == tileB.shape.charAt(3)) && (tileA.shape.charAt(1) != '0');
                }
            } else if (areInSameCol(placementA, placementB)){
                if (placementA.charAt(0) < placementB.charAt(0)) {  // A is above B.
                    return (tileA.shape.charAt(2) == tileB.shape.charAt(0)) && (tileA.shape.charAt(2) != '0');
                } else {    // B is above A.
                    return (tileA.shape.charAt(0) == tileB.shape.charAt(2)) && (tileA.shape.charAt(0) != '0');
                }
            }
        }

        return false;
    }

    public static boolean areNeighbours(String placementA, String placementB) {
        //if the input in invalid return false
        if(placementA.length() != 2 || placementB.length() != 2){
            return false;
        }
        //if there are tiles out of board, return false
//        if(!areInBoard(placementA, placementB)){
//            return false;
//        }
        if (areInSameRow(placementA, placementB)) {
            return (Math.abs(placementA.charAt(1) - placementB.charAt(1)) == 1);
        } else if (areInSameCol(placementA, placementB)) {
            return (Math.abs(placementA.charAt(0) - placementB.charAt(0)) == 1);
        }
        return false;
    }

    private static boolean areInSameRow(String placementA, String placementB) {
        return (placementA.charAt(0) == placementB.charAt(0));
    }

    private static boolean areInSameCol(String placementA, String placementB) {
        return (placementA.charAt(1) == placementB.charAt(1));
    }

    /**
     * Given a well-formed board string representing an ordered list of placements,
     * determine whether the board string is valid.
     * A board string is valid if each tile placement is legal with respect to all previous tile
     * placements in the string, according to the rules for legal placements:
     * - A tile must be placed such that at least one edge connects to either an exit or a pre-existing route.
     *   Such a connection is called a valid connection.
     * - Tiles may not be placed such that a highway edge connects to a railway edge;
     *   this is referred to as an invalid connection.
     *   Highways and railways may only join at station tiles.
     * - A tile may have one or more edges touching a blank edge of another tile;
     *   this is referred to as disconnected, but the placement is still legal.
     *
     * @param boardString a board string representing some placement sequence
     * @return true if placement sequence is valid
     * @Author Tianjun Peng
     */
    public static boolean isValidPlacementSequence(String boardString) {
        // FIXME Task 6: determine whether the given placement sequence is valid
        // Split all tile placements from boardString into an array.
        List<String> tilePlacementList = new ArrayList<>();
        int connectedCount = 0;
        int legalConnectedCount = 0;
        for (int i = 0; i < boardString.length(); i += 5){
            tilePlacementList.add(boardString.substring(i, i + 5));
        }
        for(int i =0; i< tilePlacementList.size()-1; i++){
            String placementA = tilePlacementList.get(i).substring(2,4);
            for(int j = i + 1 ; j<tilePlacementList.size() ;j++){
                Tile tileA = new Tile(tilePlacementList.get(i));
                Tile tileB = new Tile(tilePlacementList.get(j));
                tileA = tileA.changeOrientation(Character.getNumericValue(tilePlacementList.get(i).charAt(4)));
                tileB = tileB.changeOrientation(Character.getNumericValue(tilePlacementList.get(j).charAt(4)));
                String shapeA = tileA.shape;
                String shapeB = tileB.shape;
                String placementB = tilePlacementList.get(j).substring(2,4);
                if(placementA.equals(placementB)){
                    return false;
                }
                if(areNeighbours(placementA,placementB)){
                    connectedCount++;
                    if(placementA.charAt(0)-placementB.charAt(0)==1){
                        //A is below B, either one of their edge should be 0 or they should be vell connected;
                        if(shapeA.charAt(0) == '0' || shapeB.charAt(2) == '0' || shapeA.charAt(0) == shapeB.charAt(2)){
                            legalConnectedCount++;
                        }
                    }
                    else if(placementA.charAt(0) - placementB.charAt(0) == -1){
                        //A is on B, either one of their edge should be 0 or they should be well connected;
                        if(shapeA.charAt(2) == '0' || shapeB.charAt(0) == '0' || shapeA.charAt(2) == shapeB.charAt(0)){
                            legalConnectedCount++;
                        }
                    }
                    else if(placementA.charAt(1) - placementB.charAt(1) == 1 ){
                        //A is on the right of B, criteria ditto
                        if(tileA.shape.charAt(1) == '0' || tileB.shape.charAt(3) == '0' || tileA.shape.charAt(1) == tileB.shape.charAt(3)){
                            legalConnectedCount ++;
                        }
                    }
                    else if(placementA.charAt(1) - placementB.charAt(1) == -1 ){
                        //A is on the left of B, criteria ditto
                        if(shapeA.charAt(3) == '0' || shapeB.charAt(1) == '0' || shapeA.charAt(3) == shapeB.charAt(1)){
                            legalConnectedCount ++;
                        }
                    }
                }
            }
        }
        return allValidConnections(tilePlacementList) && legalConnectedCount == connectedCount;
    }

    private static boolean isConnectedToExit(String tilePlacement){
        String[] exits = ExtendedBoard.EXITS;
        for (int i = 0; i < exits.length; i++){
            if (areConnectedNeighbours(tilePlacement, exits[i])){
                return true;
            }
        }
        return false;
    }
    /*  Test whether every tile has at least one valid connection with
        other tiles or the exits.
     */
    private static boolean allValidConnections(List<String> list){
        if (isConnectedToExit(list.get(0))){    // First tile must connect to one of the exits.
            int validConnectionCounter = 1;

            for (int a = 1; a < list.size(); a++){
                /*  Loop through the existing tile placements to test whether
                    the current tile is connected to at least one of the existing tiles or the exits.
                 */
                boolean isValidConnection = false;
                for (int b = 0; b < a; b++){
                    isValidConnection |= (areConnectedNeighbours(list.get(a), list.get(b)) || isConnectedToExit(list.get(a)));
                }

                /*  If the current tile placement has at least one valid connection,
                    add one to the counter.
                 */
                if (isValidConnection) {
                    validConnectionCounter++;
                }
            }
            return validConnectionCounter == list.size();
        } else {
            return false;
        }
    }



    /**
     * Generate a random dice roll as a string of eight characters.
     * Dice A should be rolled three times, dice B should be rolled once.
     * Die A has faces numbered 0-5.
     * Die B has faces numbered 0-2.
     * Each die roll is composed of a character 'A' or 'B' representing the dice,
     * followed by a digit character representing the face.
     *
     * @return a String representing the die roll e.g. A0A4A3B2
     */
    public static String generateDiceRoll() {
        // FIXME Task 7: generate a dice roll
        return Dice.generateFourTiles();
    }


    /**
     * Given the current state of a game board, output an integer representing the sum of all the following factors
     * that contribute to the player's final score.
     * <p>
     * * Number of exits mapped
     * * Number of centre tiles used
     * * Number of dead ends in the network
     *
     * @param boardString a board string representing a completed game
     * @return integer (positive or negative) for score *not* considering longest rail/highway
     * @Author Tianjun Peng
     */
    public static int getBasicScore(String boardString) {
        // FIXME Task 8: compute the basic score
        var centreScore = getCentreScore(boardString);
        var deductionPoint = deductionPoint(boardString);
        var exitList = findPath(boardString);
        var exitCount = - exitList.size();
        for(ArrayList<String> list : exitList) {
            ArrayList<String> temp = new ArrayList<>();
            for(String b: list){
                if(isConnectedToExit(b) && !temp.contains(b)){
                    temp.add(b);
                    exitCount++;
                }
            }
        }
        var exitPoint = exitCount * 4;
        return centreScore + exitPoint - deductionPoint;
    }

    /**
     * Helper method to separate each tile-placement string from a board string
     * @param boardString a board string representing a complete game
     * @return list of strings of length 5
     */
    public static ArrayList<String> separateTilePlacements(String boardString){
        ArrayList<String> tilePlacements = new ArrayList<>();
        for (int i = 0; i < boardString.length(); i += 5){
            tilePlacements.add(boardString.substring(i, i + 5));
        }
        return tilePlacements;
    }

    /**
     * @param boardString a board string representing a completed game
     * @return number of tiles that are placed in the centre
     */
    public static int getCentreScore(String boardString) {
        int score = 0;
        for (int i = 2; i < boardString.length(); i += 5) {
            String location = boardString.substring(i, i+2);
            if(Board.CENTRE.contains(location)) {
                score++;
            }
        }
        return score;
    }

    /**
     * Get a square matrix whose size is the number of tiles placed on the board.
     * If ith tile-placement string connects to jth tile-placement string, the entry (i,j) is 1; otherwise (i,j) is 0.
     *     e.g. If A connects to B, B connects to C
     *       A B C
     *     A 0 1 0
     *     B 1 0 1
     *     C 0 1 0
     * @param boardString a board string representing a complete game
     * @return adjacency matrix of tile placements
     * @Author Tianjun Peng
     */
    public static int[][] getAdjacencyMatrix(String boardString){
        var tilePlacements = separateTilePlacements(boardString);
        int size = tilePlacements.size();

        int[][] matrix = new int[size][size];

        for (int i = 0; i < size; i++) {
            String thisTilePlacement = tilePlacements.get(i);
            for (int j = 0; j < size; j++) {
                String otherTilePlacement = tilePlacements.get(j);
                if (areConnectedNeighbours(thisTilePlacement, otherTilePlacement)) {
                    matrix[i][j] = 1;
                } else {
                    matrix[i][j] = 0;
                }
            }
        }

        return matrix;
    }
    /**
     * Get all valid connection from the boardstring to a certain tile.
     * If ith tile string connects to the given string, it will be stored and the connections number increase
     *
     *       A B C
     *     A 0 1 0
     *     B 1 0 1
     *     C 0 1 0
     * @param boardString a board string representing a complete game
     * @return the total valid connection number at first and followerd by all valid piece stirng
     * @author Tianjun Peng
     */
    public static String allConnectedPiece(String piece, String boardString){
        var tilePlacements =  separateTilePlacements(boardString);
        int connectedCount = 0;
        String allConnectedPiece = new String();

        for(int i =0; i< tilePlacements.size();i++){
            String pieceB = tilePlacements.get(i);
            if(areConnectedNeighbours(piece,pieceB)) {
                allConnectedPiece += pieceB;
                connectedCount++;
            }
        }
        return connectedCount + allConnectedPiece;
    }


    /**
     * Get all piece in the valid path
     * Use the maze calculated before hand as the indication of all valid connection
     * Use stack to store the possible connected piece that have not been added to the path because the algorithm choose other piece o the current stage
     * @param boardString a board string representing a complete game
     * @return  an arraylist, and each element is a list of path that is extended as long as possible
     * @author Tianjun Peng
     */
    public static ArrayList<ArrayList<String>> findPath(String boardString) {
        //to generate boardstring list
        List<String> listA = separateTilePlacements(boardString);
        StringBuilder newBoardString = new StringBuilder();
        //To replace B2 tile with A4 and A1 in the same place, so that it will not double count the path later on;
        for(int i = 0; i < listA.size(); i++){
            if(listA.get(i).substring(0,2).equals("B2")){
                String placement = listA.get(i).substring(2,4); // Where did the B2 placed
                int orientation = Character.getNumericValue(listA.get(i).charAt(4));//The last digit(orientation) of B2
                String newTileA = "A4"+placement+orientation; // Replacement tileA
                int orientationForB = (orientation+1)%8; // Replacement tile B orientation
                String newTileB = "A1"+placement+orientationForB;// Replacement tile B
                newBoardString.append(newTileA);
                newBoardString.append(newTileB);
            }
            else{
                newBoardString.append(listA.get(i));
            }
        }
        var list = separateTilePlacements(newBoardString.toString()); // list after replacement
        int[][] maze = getAdjacencyMatrix(newBoardString.toString());
        //An arraylist to store all piece that connected with exit
        ArrayList<Integer> connectedExit = new ArrayList<>();
        for(String tile: list){
            if(isConnectedToExit(tile)){
                connectedExit.add(list.indexOf(tile));
            }
        }
        //pathlist is to store all the path we gonna return
        ArrayList<ArrayList<String>> pathList = new ArrayList<>();
        //stack is to store the tile we are going to look up next
        Stack<Integer> stack = new Stack<>();
        //add a -1 to the stack so that stack will not be empty and it is the signal that a path is end when -1 pop
        stack.add(-1);
        //The path we are generating and will be put to the pathlist after there no tile can be found connected to this path
        ArrayList<String> path = new ArrayList<>();
        Loop1: for(int j = 0; j< connectedExit.size(); j++){
            //indexIn is the index of current piece that we are looking at, which is the start of a new path
            int indexIn = connectedExit.get(j); //The exit that we are looking at
            while (!stack.isEmpty()) {
                path.add(list.get(indexIn)); // put the exit at first as a start
                for (int i = 0; i < maze[indexIn].length; i++) {
                    if (maze[indexIn][i] == 1) { // 1 means the ith tile is connected with the tile we are looking at
                        stack.add(i); // add ith tile's index to out stack
                        maze[indexIn][i] = maze[i][indexIn] = 0; // 0 means this tile is already been put in the path, so don't cheak the relationship between ith and jth again
                    }
                }
                indexIn = stack.pop(); // To store all possible tile connected, and last in first out, and all tile that should belongs to this path should be found
                if (connectedExit.contains(indexIn)) {
                    connectedExit.remove(connectedExit.indexOf(indexIn)); // if the exit is already connected to a path, the exit will never be indexIn again
                }
                if(indexIn == -1){
                    pathList.add((ArrayList<String>) path.clone());
                    path.removeAll(path);
                    stack.add(-1);
                    continue Loop1;
                }
            }
        }
        return pathList;
    }
    /**
     * Get the penalty point of a boardstring
     * For each piece that has 4 edges, use all connected piece method to find whether it have 4 connection, if it connected with 4, no point
     * will be deducted, otherwise 4 - connection point will be deducted. Same operation for 2 edge and 3 edge piece.
     * For those piece that in the boundary, one point will be redeemed if they have the way out.
     * Add the encoded exit to the boardString so that those piece connected to exit will not be miscount for the penalty point
     * @param boardString a board string representing a complete game
     * @return the point that should deducted in a given boardString
     * @author Tianjun Peng
     */
    public static int deductionPoint(String boardString){
        int panltypoint = 0;
        //Add the encoded exit to the boardString
        String boardStringWithExit = boardString + ExtendedBoard.getExitsStr();
        for(int i = 0; i< boardString.length();i += 5) {
            String pieceString = boardString.substring(i, i + 5); // The divided boardString
            String piece = boardString.substring(i, i + 2); // The piece id
            String pieceLocation = boardString.substring(i + 2, i + 4); // The placement of piece
            Tile tile = new Tile(piece);
            tile = tile.changeOrientation(Character.getNumericValue(pieceString.charAt(4))); // the orientation of the piece
            String shape = tile.getTileShape(); // The "HHRH" form of shape
            // To redeem the point deducted if there is valid placement in the boundary
            if (pieceLocation.equals("A0")) {
                if (Character.getNumericValue(shape.charAt(0)) != 0) {
                    panltypoint -= 1;
                }
                if (Character.getNumericValue(shape.charAt(1)) != 0) {
                    panltypoint -= 1;
                }
            } else if (pieceLocation.equals("A6")) {
                if (Character.getNumericValue(shape.charAt(0)) != 0) {
                    panltypoint -= 1;
                }
                if (Character.getNumericValue(shape.charAt(3)) != 0) {
                    panltypoint -= 1;
                }
            } else if (pieceLocation.equals("G6")) {
                if (Character.getNumericValue(shape.charAt(3)) != 0) {
                    panltypoint -= 1;
                }
                if (Character.getNumericValue(shape.charAt(2)) != 0) {
                    panltypoint -= 1;
                }
            } else if (pieceLocation.equals("G0")) {
                if (Character.getNumericValue(shape.charAt(2)) != 0) {
                    panltypoint -= 1;
                }
                if (Character.getNumericValue(shape.charAt(1)) != 0) {
                    panltypoint -= 1;
                }
            } else if (pieceLocation.equals("A2") || pieceLocation.equals("A4")) {
                if (Character.getNumericValue(shape.charAt(0)) != 0) {
                    panltypoint -= 1;
                }
            } else if (pieceLocation.equals("C6") || pieceLocation.equals("E6")) {
                if (Character.getNumericValue(shape.charAt(3)) != 0) {
                    panltypoint -= 1;
                }
            } else if (pieceLocation.equals("G2") || pieceLocation.equals("G4")) {
                if (Character.getNumericValue(shape.charAt(2)) != 0) {
                    panltypoint -= 1;
                }
            } else if (pieceLocation.equals("C0") || pieceLocation.equals("E0")) {
                if (Character.getNumericValue(shape.charAt(1)) != 0) {
                    panltypoint -= 1;
                }
            }
            // Four edges pieces
            if (piece.charAt(0) == 'S' || piece.equals("B2")) {
                String allConnectedPiece = allConnectedPiece(pieceString, boardStringWithExit);
                panltypoint += 4 - Character.getNumericValue(allConnectedPiece.charAt(0));
            }
            // Three edges pieces
            else if (piece.equals("A2") || piece.equals("A3")) {
                String allConnectedPiece = allConnectedPiece(pieceString, boardStringWithExit);
                panltypoint += 3 - Character.getNumericValue(allConnectedPiece.charAt(0));
            }
            // Two edges pieces
            else {
                String allConnectedPiece = allConnectedPiece(pieceString, boardStringWithExit);
                panltypoint += 2 - Character.getNumericValue(allConnectedPiece.charAt(0));
            }
        }
        return panltypoint;
    }



    /**
     * Given a valid boardString and a dice roll for the round,
     * return a String representing an ordered sequence of valid piece placements for the round.
     * @param boardString a board string representing the current state of the game as at the start of the round
     * @param diceRoll a String representing a dice roll for the round
     * @return a String representing an ordered sequence of valid piece placements for the current round
     * @see RailroadInk#generateDiceRoll()
     * @Author Tianjun Peng
     */

    public static String generateMove(String boardString, String diceRoll) {
        // FIXME Task 10: generate a valid move
        var list = separateTilePlacements(boardString);
        String[] diceRollString = new String[]{diceRoll.substring(0,2),diceRoll.substring(2,4),diceRoll.substring(4,6),diceRoll.substring(6)};
        ArrayList<String> diceRollList = new ArrayList<>(Arrays.asList(diceRollString));
        String[] specialTile = new String[]{"S0", "S1" , "S2", "S3" , "S4" , "S5"};
        ArrayList<String> specialTileList = new ArrayList<>(Arrays.asList(specialTile));
        StringBuilder generateMove = new StringBuilder();
        boolean hasUsedSpecialTile = false;
        for(String tile:list){
            if(specialTileList.contains(tile.substring(0,2))){
                specialTileList.remove(tile.substring(0,2));
            }
        }
        int worstCase = 10 + 9 + 8 + 7 + 6; // special tile and dicerolltile
        Loop1: for(int i = 0; i< worstCase ; i++) {
            while (!diceRollList.isEmpty()) {
                for (String specilPiece : specialTileList) {
                    var validMove = generateValidMove(boardString, specilPiece);
                    if (!validMove.isEmpty() && !hasUsedSpecialTile && specialTileList.size() >= 4) {
                        generateMove.append(validMove);
                        hasUsedSpecialTile = true;
                        boardString += validMove;
                        continue Loop1;
                    }
                }
                for (int j = 0; j < diceRollList.size(); j++) {
                    var newTile = diceRollList.get(j);
                    var validmove = generateValidMove(boardString, newTile);
                    if (!validmove.isEmpty()) {
                        generateMove.append(validmove);
                        diceRollList.remove(j);
                        boardString += validmove;
                    }
                }
                continue Loop1;
            }
        }
       return generateMove.toString();
    }
    /**
     * Given a valid boardString and a dice roll for the round,
     * return a String representing
     * @param boardString a board string representing the current state of the game as at the start of the round
     * @return a String representing an ordered sequence of valid piece placements for the current round
     * @see RailroadInk#generateDiceRoll()
     * @Author Tianjun Peng
     */

    public static String getAllPlacement(String boardString){
        StringBuilder allPlace = new StringBuilder();
        for(int i = 2 ; i< boardString.length();i += 5){
            allPlace.append(boardString.substring(i,i+2));
        }
        return allPlace.toString();
    }
    private static String generateValidMove(String boardString, String tileID){
        List<String> tileSet = separateTilePlacements(boardString);
        String tilePlaceSet = getAllPlacement(boardString);
        List<String> connectableExits = new ArrayList<>();
        String allExits = "A1A3A5B6D6F6G1G3G5B0D0F0";
        for (int i = 0; i < allExits.length(); i += 2){
            connectableExits.add(allExits.substring(i, i + 2));
        }
        for (int j = 0; j < tilePlaceSet.length(); j += 2){
            if (allExits.contains(tilePlaceSet.substring(j, j + 2))){
                connectableExits.remove(tilePlaceSet.substring(j, j + 2));
            }
        }
        //Whether the tile can put next to other tile
        for (String tile : tileSet){
            List<String> neighbors = getAdjacentTilePlacements(boardString,tile);
            for (String neighbor : neighbors){
                //if there is a valid connection
                if (neighbor.substring(0, 2).equals("NO")){
                    String IdNPlacement = tileID + neighbor.substring(2, 4);
                    var completedPiece = setOrientation(tile, IdNPlacement);
                    if(!completedPiece.isEmpty()){
                        var validMove = completedPiece;
                        boardString += validMove;
                        if (isValidPlacementSequence(boardString)){
                            return validMove;
                        }
                    }
                }
            }
        }
        //Whether the tile can be put next to the exit
        if (!connectableExits.isEmpty()){
            for (String connectableExit : connectableExits){
                String IDNPlacement = tileID + connectableExit;
                for (String exit : ExtendedBoard.EXITS){
                    var completedPiece = setOrientation(exit, IDNPlacement);
                    if(!completedPiece.isEmpty()){
                        var validMove = completedPiece;
                        boardString += validMove;
                        if (isValidPlacementSequence(boardString)){
                            return validMove;
                        }
                    }
                }
            }
        }
        return "";
    }

    /**
     * Given a valid boardString and a tile that we are checking
     * return a String List that a new tile can be put to connected with tileA
     * @param boardString a board string representing the current state of the game as at the start of the round
     * @param tileA the tile that we are checking whether there is vacancy. If there is, is the vacancy on the top/ botton/ lefe/ right?
     * @return  a String List that a new tile can be put to connected with tileA ( The vacant place around tileA)
     */
    public static List<String> getAdjacentTilePlacements(String boardString, String tileA){
        var list = separateTilePlacements(boardString);
        String location = tileA.substring(2,4);
        String up = ""+ (char)(location.charAt(0) - 1) + location.charAt(1);
        String down = String.valueOf((char)(location.charAt(0) + 1)) + location.charAt(1);
        String left = location.charAt(0) + String.valueOf((char)((location.charAt(1)) - 1));
        String right = location.charAt(0) + String.valueOf((char)((location.charAt(1)) + 1));
        List<String> adjacentTile = new ArrayList<>();
        boolean upvalid = false;
        boolean downvalid = false;
        boolean leftvalid = false;
        boolean rightvalid = false;
        for(String tileB : list){
            if(tileB.substring(2,4).equals(up)) {
                upvalid = true;
            }
            else if(tileB.substring(2,4).equals(down)) {
                downvalid = true;
            }
            else if(tileB.substring(2,4).equals(left)) {
                leftvalid = true;
            }
            else if(tileB.substring(2,4).equals(right)) {
                rightvalid = true;
            }
        }
        if(!upvalid && up.charAt(0) != '@' ) adjacentTile.add("NO" + up); // if there is an vacancy on the top og tile A
        if(!downvalid && down.charAt(0) != 'H' ) adjacentTile.add("NO" + down); // if there is an vacancy under tileA
        if(!leftvalid && left.charAt(1) != '/') adjacentTile.add("NO" + left); // if there is an vacancy on the left of tileA
        if(!rightvalid && right.charAt(1) != '7') adjacentTile.add("NO" + right); // if there is an vacancy on the right of tileA
        return adjacentTile;
    }

    /**
     * Given a valid boardString and a tile that we are checking
     * return a String with adjusted orientation added to the IdNPlacement
     * @param IdNPlacement is the tile that we want to add orientation to it
     * @param tile is tile that we are trying to put IdNplacement next to
     * @return  a string with adjusted orientation added to the IdNPlacement
     * @Author Tianjun Peng
     */
    private static String setOrientation(String tile, String IdNPlacement){
        StringBuilder validPlacement = new StringBuilder();
        int orientationMax = 8;
        for (int i = 0; i < orientationMax; i ++){
            String diceTile = IdNPlacement + i;
            if (areConnectedNeighbours(tile, diceTile)){
                validPlacement.append(diceTile);
                return validPlacement.toString();
            }
        }
        return validPlacement.toString();
    }
    /**
     * Given the current state of a game board, output an integer representing the sum of all the factors contributing
     * to `getBasicScore`, as well as those attributed to:
     * <p>
     * * Longest railroad
     * * Longest highway
     *
     * @param boardString a board string representing a completed game
     * @return integer (positive or negative) for final score (not counting expansion packs)
     */
    public static int getAdvancedScore(String boardString) {
        // FIXME Task 12: compute the total score including bonus points
        return -1;
    }

}

