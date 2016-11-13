package src.main.model;

import java.util.HashMap;
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
        fillBoard(board);
    }

    /**
     * Fill in the internal board structure with the hex tiles
     * 
     * @param board  the board map to be filled in
     */
    private void fillBoard(HashMap<Point, HexPiece> board) {
        List<Resource> tiles = new ArrayList<Resource>();
        // TODO add in tiles based on correct counts in 2 player variant
        int tileIdx = 0;
        for (int c = 0; c < 5; c++) {
            if (c == 0 || c == 4) {
                for (int r = 1; r <= 2; r++) {
                    board.put(new Point(r, c), tiles.get(tileIdx));
                    tileIdx += 1;
                }
            } else if (c == 1 || c == 3) {
                for (int r = 0; r <= 2; r++) {
                    board.put(new Point(r, c), tiles.get(tileIdx));
                    tileIdx += 1;
                }
            } else {
                for (int r = 0; r <= 3; r++) {
                    board.put(new Point(r, c), tiles.get(tileIdx));
                    tileIdx += 1;
                }
            }
        }
    }
}
