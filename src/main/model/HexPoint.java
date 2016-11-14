package src.main.model;

import java.util.Objects;

/**
 * Class used to help identify hex tiles on the board. Every hex can be uniquely determined
 * by a point
 *
 * The definitions of the rows and cols for a hex grid are given here:
 * http://www.quarkphysics.ca/scripsi/hexgrid/
 *
 * @author Kevin Lowe
 */
public class HexPoint {
    /** The row that this tile is on, as used by the 2 player variant. */
    private int row;

    /** The col that this tile is on, as used by the 2 player variant. */
    private int col;

    public HexPoint(int row, int col) {
        this.row = row;
        this.col = col;
    }

    /**
     * Returns the row of this point
     *
     * @return the integer row
     */
    public int row() {
        return row;
    }

    /**
     * Returns the col of this point
     *
     * @return the integer col
     */
    public int col() {
        return col;
    }

    @Override
    public int hashCode() {
        return Objects.hash(row, col);
    }

    @Override
    public boolean equals(Object other) {
        HexPoint otherHP = (HexPoint) other;
        return this.row == otherHP.row() && this.col == otherHP.col();
    }
}
