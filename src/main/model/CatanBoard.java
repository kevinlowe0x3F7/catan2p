package src.main.model;

import java.util.HashMap;
import java.util.Collections;
import java.util.List;
import java.util.ArrayList;
import java.awt.Color;

/**
 * Represents the board itself in the game. Helps to handle the placing of buildings and
 * roads, ensuring that is in a valid state and abstracts away the details of the hex setup
 *
 * @author Kevin Lowe
 */
public class CatanBoard {
    /** 
     *  The internal board representation. The point (key-value) is of the form (r, c),
     *  where r indicates the row of the hex tile, and c indicates the column. The numbering
     *  on the board is the same as the one defined in this link:
     *  http://www.quarkphysics.ca/scripsi/hexgrid/
     *  (0, 2) is set as the topmost point in the two-player variant of Catan.
     */
    HashMap<HexPoint, HexPiece> board;

    /**
     *  Initialize a new CatanBoard. The resources that are set on each hex piece are
     *  shuffled and selected at random, same with the harbors.
     */
    public CatanBoard() {
        this.board = new HashMap<HexPoint, HexPiece>();
        List<Resource> tiles = fillInTiles();
        for (int i = 0; i < TOTAL_TILES; i++) {
            Resource res = tiles.get(i);
            HexPoint pos = new HexPoint(hexPoints[i][0], hexPoints[i][1]);
            int num = diceNums[i];
            this.board.put(pos, new HexPiece(num, res));
        }
    }

    /**
     * Fill in the tiles, with the correct number of each based on the 2-player variant.
     * TODO check size of tiles is correct
     *
     * @return The list of tiles, shuffled
     */
    private List<Resource> fillInTiles() {
        List<Resource> tiles = new ArrayList<Resource>();
        for (int i = 0; i < FIELDS; i++) {
            tiles.add(Resource.WHEAT);
        }
        for (int i = 0; i < FORESTS; i++) {
            tiles.add(Resource.WOOD);
        }
        for (int i = 0; i < PASTURES; i++) {
            tiles.add(Resource.SHEEP);
        }
        for (int i = 0; i < MOUNTAINS; i++) {
            tiles.add(Resource.ORE);
        }
        for (int i = 0; i < HILLS; i++) {
            tiles.add(Resource.BRICK);
        }
        Collections.shuffle(tiles);
        return tiles;
    }

    /**
     * Getter method for the tiles as a list
     *
     * @return the list of tiles
     */
    public List<HexPiece> getTiles() {
        List<HexPiece> tiles = new ArrayList<HexPiece>();
        for (HexPoint p : board.keySet()) {
            tiles.add(board.get(p));
        }
        return tiles;
    }

    /**
     * Given a point indicating a hex, along with a location, returns the adjacent hex,
     * or null if none exists.
     * TODO test getting adjacent hex
     *
     * @param hex  the point identifying the hex
     * @param location  the location for the adjacent hex with respect to the current hex
     * 
     * @return the point representing the adjacent hex, or null if none exists
     */
    public HexPoint getAdjacentHex(HexPoint hex, HexPiece.RoadLoc loc) {
        if (!isValidPoint(hex)) {
            return null;
        }
        int col = hex.col();
        int adjRow = hex.row();
        int adjCol = hex.col();
        if (col == 1 || col == 3) {
            switch (loc) {
                case NW:    adjCol -= 1;
                            break;
                case N:     adjRow -= 1;
                            break;
                case NE:    adjCol += 1;
                            break;
                case SE:    adjRow += 1;
                            adjCol += 1;
                            break;
                case S:     adjRow += 1;
                            break;
                case SW:    adjRow += 1;
                            adjCol -= 1;
                            break;
            }
        } else { // col == 0, 2, or 4
            switch (loc) {
                case NW:    adjCol -= 1;
                            adjRow -= 1;
                            break;
                case N:     adjRow -= 1;
                            break;
                case NE:    adjCol += 1;
                            adjRow -= 1;
                            break;
                case SE:    adjCol += 1;
                            break;
                case S:     adjRow += 1;
                            break;
                case SW:    adjCol += 1;
                            break;
            }
        }
        HexPoint adjPoint = new HexPoint(adjRow, adjCol);
        if (isValidPoint(adjPoint)) {
            return adjPoint;
        } else {
            return null;
        }
    }

    /**
     * Returns true if this HexPoint is valid, false otherwise
     *
     * @param point  the HexPoint to verify
     *
     * @return boolean indicating whether given HexPoint is valid
     */
    public boolean isValidPoint(HexPoint point) {
        return board.containsKey(point);
    }

    /**
     * Returns true if the hex on a given HexPoint has a road on that location, false otherwise
     *
     * @param hexPt  the hexpoint to check
     * @param loc  the road loc
     *
     * @return true if there is a road on the hex point for that loc, false otherwise
     */
    public boolean hasRoad(HexPoint hexPt, HexPiece.RoadLoc loc) {
        if (!isValidPoint(hexPt)) {
            return false;
        }
        HexPiece hex = board.get(hexPt);
        Color road = hex.getRoad(loc);
        return road != null;
    }

    /**
     * Returns the road color at the specified location on a given HexPoint, or null if none
     * exists.
     *
     * @param hexPt  the hexpoint to get the road from
     * @param loc  the road loc
     *
     * @return the color of the road, or null if none exists
     */
    public Color getRoad(HexPoint hexPt, HexPiece.RoadLoc loc) {
        if (!isValidPoint(hexPt)) {
            return false;
        }
        HexPiece hex = board.get(hexPt);
        return hex.getRoad(loc);
    }

    // TODO helper functions for indicating whether there are settlements or roads on tiles
    // TODO decide on harbors (9 possible, need 6)
    // TODO place settlement method
    // TODO place road method
    // TODO place city method
    // TODO hasSettlement method
    // TODO hasRoad method
    // TODO hasCity method

    /** The points of the hex tiles in spiral ordering, used to place the dice numbers. */
    private final int[][] hexPoints = {{2,0},{2,1},{3,2},{2,3},{2,4},{1,4},{0,3},{0,2},
                                        {0,1},{1,0},{1,1},{2,2},{1,3},{1,2}};
    /** The dice numbers in spiral order, should correspond to hexPoints. */
    private final int[] diceNums = {5, 2, 6, 3, 8, 10, 9, 11, 4, 8, 10, 9, 5, 4};

    /** The total number of tiles on the board. */
    private static final int TOTAL_TILES = 14;

    /** The number of hexes for the wheat resource. */
    public static final int FIELDS = 3;

    /** The number of hexes for the wood resource. */
    public static final int FORESTS = 3;

    /** The number of hexes for the sheep resource. */
    public static final int PASTURES = 3;

    /** The number of hexes for the ore resource. */
    public static final int MOUNTAINS = 2;

    /** The number of hexes for the brick resource. */
    public static final int HILLS = 3;
}
