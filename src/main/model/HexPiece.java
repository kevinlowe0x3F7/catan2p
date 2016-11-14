package src.main.model;

import java.util.HashMap;

/**
 * A representation of a single hex piece on the game board.
 *
 * @author Kevin Lowe
 */
public class HexPiece {
    /** Indicates the directions in which we can place roads. */
    public enum RoadDir { N, NE, SE, S, SW, NW }

    /** Indicates the possible directions in which we can place buildings on the hex piece. */
    public enum BuildingDir { NE, E, SE, SW, W, NW }

    /** 
     * The actual roads for this HexPiece. If the value is non-null for some direction, that
     * indicates that a player has placed a road on that direction.
     */
    private HashMap<RoadDir, Color> roads;

    /** 
     * The actual buildings for this HexPiece. If the value is non-null for some direction,
     * that indicates that a player has placed a building in that direction.
     */
    private HashMap<BuildingDir, Building> buildings;

    /** The die roll number for this hex piece. */
    private int roll;

    /** The type of resource on this hex piece. */
    private Resource resource;

    /** Indicator for whether this hex piece has the robber currently on it. */
    private boolean hasRobber;

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

    /**
     * Returns a list of buildings for this HexPiece. It returns just a list, with no notion
     * of direction at all for each of the buildings. This list will return only non-null
     * entries.
     *
     * @return a list of buildings, or potentially an empty list
     */
    public List<Building> getBuildings() {
        List<Building> buildingList = new ArrayList<Building>();
        for (BuildingDir d : buildings.keySet()) {
            Building building = buildings.get(d);
            if (building != null) {
                buildingList.add(building);
            }
        }
        return buildingList;
    }

    /**
     * Initializes a new hex piece with the specified type of resource and die roll number
     *
     * @param num  the dice roll number that will be on this hex piece
     * @param resource  the type of resource for this hex piece
     */
    public HexPiece(int num, Resource resource) {
        this.roads = new HashMap<RoadDir, Color>();
        for (RoadDir dir : RoadDir.values()) {
            this.roads.put(dir, null);
        }
        this.buildings = new HashMap<BuildingDir, Building>();
        for (BuildingDir dir : BuildingDir.values()) {
            this.buildings.put(dir, null);
        }
        this.roll = num;
        this.resource = resource;
        this.hasRobber = false;
    }
}
