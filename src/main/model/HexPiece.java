package src.main.model;

import java.util.HashMap;
import java.util.List;
import java.util.ArrayList;

/**
 * A representation of a single hex piece on the game board.
 *
 * @author Kevin Lowe
 */
public class HexPiece {
    /** Indicates the locations in which we can place roads. */
    // TODO test road location methods
    public enum RoadLoc {
        N,
        NE,
        SE,
        S,
        SW,
        NW;

        private static final RoadLoc[] cachedValues = values();

        /**
         * Returns the complementary road direction. Used mainly to help figure out
         * placing a road on two adjacent hexes.
         *
         * @return the complement of that road, which is defined to be the road direction
         *         from the adjacent hex's perspective
         */
        public RoadLoc roadComplement() {
            switch (this) {
                case NW:    return RoadLoc.SE;
                case N:     return RoadLoc.S;
                case NE:    return RoadLoc.SW;
                case SE:    return RoadLoc.NW;
                case S:     return RoadLoc.N;
                case SW:    return RoadLoc.NE;
                default:    return null;
            }
        }

        /**
         * Returns the next road location in clockwise manner
         *
         * @return the next road location, going clockwise
         */
        public RoadLoc next() {
            return cachedValues[(this.ordinal() + 1) % 6];
        }

        /**
         * Returns the previous road location in clockwise manner. Equivalent to the next
         * road location going counter clockwise
         *
         * @return the previous road location, going clockwise
         */
        public RoadLoc prev() {
            return cachedValues[(this.ordinal() - 1) % 6];
        }
    }

    /** Indicates the possible locations in which we can place buildings on the hex piece. */
    // TODO test building location methods
    public enum BuildingLoc {
        NE,
        E,
        SE,
        SW,
        W,
        NW;

        private static final BuildingLoc[] cachedValues = values();

        /**
         * Returns the next building location in clockwise manner
         *
         * @return the next building location, going clockwise
         */
        public BuildingLoc next() {
            return cachedValues[(this.ordinal() + 1) % 6];
        }

        /**
         * Returns the previous building location in clockwise manner. Equivalent to the next
         * building location going counter clockwise
         *
         * @return the previous building location, going clockwise
         */
        public BuildingLoc prev() {
            return cachedValues[(this.ordinal() - 1) % 6];
        }
    }

    /** 
     * The actual roads for this HexPiece. If the value is non-null for some location, that
     * indicates that a player has placed a road on that location.
     */
    private HashMap<RoadLoc, Road> roads;

    /** 
     * The actual buildings for this HexPiece. If the value is non-null for some location,
     * that indicates that a player has placed a building in that location.
     */
    private HashMap<BuildingLoc, Building> buildings;

    /** The die roll number for this hex piece. */
    private int roll;

    /** The type of resource on this hex piece. */
    private Resource resource;

    /** Indicator for whether this hex piece has the robber currently on it. */
    private boolean hasRobber;

    /**
     * Initializes a new hex piece with the specified type of resource and die roll number
     *
     * @param num  the dice roll number that will be on this hex piece
     * @param resource  the type of resource for this hex piece
     */
    public HexPiece(int num, Resource resource) {
        this.roads = new HashMap<RoadLoc, Road>();
        for (RoadLoc loc : RoadLoc.values()) {
            this.roads.put(loc, null);
        }
        this.buildings = new HashMap<BuildingLoc, Building>();
        for (BuildingLoc loc : BuildingLoc.values()) {
            this.buildings.put(loc, null);
        }
        this.roll = num;
        this.resource = resource;
        this.hasRobber = false;
    }

    /**
     * Returns the road specified by some loc
     *
     * @param loc  The requested road
     *
     * @return the road, which can be null if there's no road there
     */
    public Road getRoad(RoadLoc loc) {
        return roads.get(loc);
    }


    /**
     * Returns the building specified by some location
     *
     * @param loc  The requested building
     *
     * @return the building, which can be null
     */
    public Building getBuilding(BuildingLoc loc) {
        return buildings.get(loc);
    }

    /**
     * Returns a list of buildings for this HexPiece. It returns just a list, with no notion
     * of direction at all for each of the buildings. This list will return only non-null
     * entries.
     *
     * @return a list of buildings, or potentially an empty list
     */
    public List<Building> getBuildings() {
        List<Building> buildingList = new ArrayList<Building>();
        for (BuildingLoc l : buildings.keySet()) {
            Building building = buildings.get(l);
            if (building != null) {
                buildingList.add(building);
            }
        }
        return buildingList;
    }

    /** 
     * Gets the roll that is on the hex piece. For the piece that has both 2 and 12 on
     * it, just return 2.
     *
     * @return a number between 2 through 12 indicating the roll. 
     */
    public int roll() {
        return roll;
    }

    /**
     * Gets the resource associated with this hex piece.
     *
     * @return The resource type
     */
    public Resource resource() {
        return resource;
    }

    /**
     * Returns true if this hex piece has the robber on it, false otherwise
     *
     * @return boolean indicating whether this hex piece has the robber
     */
    public boolean hasRobber() {
        return hasRobber;
    }

    /**
     * Place robber on this hex piece
     */
    public void placeRobber() {
        hasRobber = true;
    }

    /**
     * Remove robber from this hex piece
     */
    public void removeRobber() {
        hasRobber = false;
    }
}
