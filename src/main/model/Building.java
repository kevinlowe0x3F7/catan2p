package src.main.model;

/**
 * Object structure indicating a building (either city or settlement), and a color indicating
 * who owns this building.
 *
 * @author Kevin Lowe
 */
public class Building {
    /** The type of building, should be either "settlement" or "city". */
    private String type;

    /** 
     * The player to indicate who owns it. This shouldn't ever be null since a building
     * should only be made if someone buys one. Nevertheless, null can indicate that it
     * is unowned.
     */
    private Player owner;

    /**
     * Construct a new building, should be initially a settlement.
     *
     * @param owner  The owner of this building
     */
    public Building(Player owner) {
        this.type = SETTLEMENT;
        this.owner = owner;
    }

    /**
     * Upgrade to a city.
     */
    public void upgrade() {
        type = CITY;
    }

    /**
     * Returns the type of the building, which should either be "settlement" or "city"
     *
     * @return the type of building as a String
     */
    public String type() {
        return type;
    }

    /** 
     * Returns the owner of this building
     *
     * @return the player who owns the building
     */
    public Player owner() {
        return owner;
    }

    /** A string indicating that it is a settlement. */
    public static final String SETTLEMENT = "settlement";

    /** A string for indicating a city. */
    public static final String CITY = "city";
}
