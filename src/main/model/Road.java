package src.main.model;

/**
 * Object structure indicating a road. Used for better abstraction and readability of code
 * instead of using Color for the road.
 *
 * @author Kevin Lowe
 */
public class Road {
    /**
     * The player to indicate who owns it. This shouldn't ever be null since a road should
     * only be made if someone buys one. Nevertheless, null can indicate that it is unowned.
     */
    private Player owner;

    /**
     * Construct a new road.
     *
     * @param owner  the owner of this road
     */
    public Road(Player owner) {
        this.owner = owner;
    }

    /**
     * Return the owner of this road.
     * 
     * @return the player owning this road
     */
    public Player owner() {
        return owner;
    }
}
