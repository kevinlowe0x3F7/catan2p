package src.main.model;

import java.util.HashMap;
import java.util.Collections;
import java.util.List;
import java.util.ArrayList;
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
    HashMap<Point, HexPiece> board;

    /**
     *  Initialize a new CatanBoard. The resources that are set on each hex piece are
     *  shuffled and selected at random, same with the harbors.
     */
    public CatanBoard() {
        this.board = new HashMap<Point, HexPiece>();
        List<Resource> tiles = fillInTiles();
        for (int i = 0; i < TOTAL_TILES; i++) {
            Resource res = tiles.get(i);
            Point pos = new Point(hexPoints[i][0], hexPoints[i][1]);
            int num = diceNums[i];
            this.board.put(pos, new HexPiece(num, res));
        }
    }

    /**
     * Fill in the tiles, with the correct number of each based on the 2-player variant.
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
        for (Point p : board.keySet()) {
            tiles.add(board.get(p));
        }
        return tiles;
    }

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
