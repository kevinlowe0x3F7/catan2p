package src.main.model;

import java.util.HashMap;
import java.util.Collections;
import java.util.List;
import java.util.ArrayList;
import java.awt.Color;

/**
 * Represents the board itself in the game. Helps to handle the placing of buildings and
 * roads, ensuring that is in a valid state and abstracts away the details of the hex setup.
 * The board is the same as the one found here:
 * http://nick.borko.org/games/Catan2Players.pdf
 *
 * However, there is one major difference. Along with the hexes on that grid, there are
 * hex pieces that I call sentinel pieces that completely surround the initial hex board. The
 * purpose of this is to avoid edge cases for the original pieces. For example, consider the
 * leftmost hex (the one with the K on it), it only borders 3 hex pieces when ideally all of
 * them should border 6 pieces so that there aren't any edge cases. This is similar to using
 * a sentinel node in a linked list. The hex pieces will have a value of 13 so that it can
 * never be rolled, otherwise, most methods will have to check to make sure that we are working
 * with valid pieces.
 *
 * @author Kevin Lowe
 */
public class CatanBoard {
    /** 
     *  The internal board representation. The point (key-value) is of the form (r, c),
     *  where r indicates the row of the hex tile, and c indicates the column. The numbering
     *  on the board is the same as the one defined in this link:
     *  http://www.quarkphysics.ca/scripsi/hexgrid/
     *  
     *  (0, 3) is set as the topmost sentinel node, and (2, 1) is the value of the piece with
     *  the K on it.
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
            if (i < tiles.size()) {
                Resource res = tiles.get(i);
            } else {
                Resource res = null;
            }
            HexPoint pos = new HexPoint(hexPoints[i][0], hexPoints[i][1]);
            if (i < diceNums.length) { 
                int num = diceNums[i];
            } else {
                int num = 13;
            }
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
     * Getter method for the valid tiles as a list, ie not including sentinels.
     *
     * @return the list of tiles except for sentinels.
     */
    public List<HexPiece> getTiles() {
        List<HexPiece> tiles = new ArrayList<HexPiece>();
        for (HexPoint p : board.keySet()) {
            HexPiece tile = board.get(p);
            if (tile.roll() != 13) {
                tiles.add(tile);
            }
        }
        return tiles;
    }

    /**
     * Given a point indicating a hex, along with a location, returns the adjacent hex. Because
     * this method is only defined on hexes that are part of the actual board, this method
     * should never be null. It may return a sentinel piece as the adjacent one but this method
     * should never be called on a sentinel piece. If it does, it will return null.
     * TODO test getting adjacent hex
     *
     * @param hex  the point identifying the hex
     * @param location  the location for the adjacent hex with respect to the current hex
     * 
     * @return the point representing the adjacent hex, or null if this method is called with
     *         a sentinel piece as input.
     */
    public HexPoint getAdjacentHex(HexPoint hex, HexPiece.RoadLoc loc) {
        if (!isValidPoint(hex)) {
            return null;
        }
        int col = hex.col();
        int adjRow = hex.row();
        int adjCol = hex.col();
        if (col == 2 || col == 4) {
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
        } else { // col == 1, 3, or 5
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
        return new HexPoint(adjRow, adjCol);
    }

    /**
     * Returns true if this HexPoint is valid, false otherwise. Valid is defined as being
     * in the actual grid (no sentinel pieces)
     * TODO test valid point
     *
     * @param point  the HexPoint to verify
     *
     * @return boolean indicating whether given HexPoint is valid
     */
    public boolean isValidPoint(HexPoint point) {
        int r = point.row();
        int c = point.col();
        for (int[] point : validHexPoints) {
            if (point[0] == r && point[1] == c) {
                return true;
            }
        }
        return false;
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
        Road road = hex.getRoad(loc);
        return road != null;
    }

    /**
     * Returns the road at the specified location on a given HexPoint, or null if none
     * exists.
     *
     * @param hexPt  the hexpoint to get the road from
     * @param loc  the road loc
     *
     * @return the road object, or null if none exists
     */
    public Road getRoad(HexPoint hexPt, HexPiece.RoadLoc loc) {
        if (!isValidPoint(hexPt)) {
            return null;
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

    /**
     * The points of the hex tiles in spiral ordering, used to place the dice numbers.
     * These are the hex points that are considered valid, the ones that are actually on
     * the board.
     */
    private final int[][] validHexPoints = {{3,1},{3,2},{4,3},{3,4},{3,5},{2,5},{1,4},{1,3},
                                            {1,2},{2,1},{2,2},{3,3},{2,4},{2,3}};
    /**
     * A list of all the hex points on the board, including the sentinel points.
     */
    private final int[][] hexPoints = {{3,1},{3,2},{4,3},{3,4},{3,5},{2,5},{1,4},{1,3},
                                        {1,2},{2,1},{2,2},{3,3},{2,4},{2,3},{1,0},{2,0},{3,0},
                                        {4,1},{4,2},{5,3},{4,4},{4,5},{3,6},{2,6},{1,6},{1,5},
                                        {0,4},{0,3},{0,2},{1,1}};

    /** The dice numbers in spiral order, should correspond to validHexPoints. */
    private final int[] diceNums = {5, 2, 6, 3, 8, 10, 9, 11, 4, 8, 10, 9, 5, 4};

    /** The total number of tiles on the board, including sentinel pieces. */
    private static final int TOTAL_TILES = 30;

    /** The total number of valid tiles on the board, not including sentinel pieces. */
    private static final int TOTAL_VALID_TILES = 14;

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
