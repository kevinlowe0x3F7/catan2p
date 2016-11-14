package src.main.model;

import java.util.List;
import java.util.ArrayList;
import java.util.HashMap;
import java.awt.Color;

/** 
 *  Keeps track of all the information for the player. Most actions are done by the game
 *  so player is primarily for keeping state.
 *  @author Kevin Lowe
 */
public class Player {
    /** The color of the player. */
    private Color color;

    /** Amount of victory points, incremented through buildings or special cards. */
    private int points;

    /** The number of settlements the player has on the board. */
    private int settlements;

    /** The number of roads the player has on the board. */
    private int roads;

    /** The number of cities the player has on the board. */
    private int cities;

    /** The number of knights that the player has played. */
    private int knights;

    /** The hand of resources that the player owns. */
    private List<Resource> hand;

    /** The hand of development cards that the player owns and has not used. */
    private List<DevelopmentCard> devHand;

    /** 
     * A list of development cards that the player received on this turn. Used to help
     * differentiate between dev cards received on this turn since a player can't play
     * development cards on the turn that they receive them.
     */
    private List<DevelopmentCard> receivedDevCards;

    /** 
     * An map for how much of a resource a player needs for maritime trade. Each
     * resource is initialized to 4, then as the game progresses, if the player has access
     * to harbors then the cost will go down.
     */
    private HashMap<Resource, Integer> tradeCosts;

    /** An indicator for whether the player played a development card on this turn. */
    private boolean hasPlayedDev;

    /**
     * Initialize a new player, with a specified color
     *
     * @param color  the color to identify the player
     */
    public Player(Color color) {
        this.color = color;
        this.points = 0;
        this.settlements = 0;
        this.roads = 0;
        this.cities = 0;
        this.knights = 0;
        this.hand = new ArrayList<Resource>();
        this.devHand = new ArrayList<DevelopmentCard>();
        this.receivedDevCards = new ArrayList<DevelopmentCard>();
        this.tradeCosts = new HashMap<Resource, Integer>();
        for (Resource r : Resource.values()) {
            this.tradeCosts.put(r, INIT_MARITIME_COST);
        }
        this.hasPlayedDev = false;
    }

    /**
     * Add a resource into the player's hand.
     *
     * @param res  The resource to add
     */
    public void addResource(Resource res) {
        hand.add(res);
    }

    /**
     * Remove a resource from the player's hand, if the player doesn't have that resource,
     * then do nothing
     *
     * @param res  The type of resource to remove
     */
    public void removeResource(Resource res) {
        hand.remove(res);
    }

    /**
     * Add a road, which for the player just increments the number of roads. The game handles
     * the checking and removal of resources.
     */
    public void buildRoad() {
        roads += 1;
    }

    /**
     * Add a settlement, which for the player just increments the number of settlements.
     * The game handles the checking and removal of resources.
     */
    public void buildSettlement() {
        settlements += 1;
    }

    /**
     * Add a city, which for the player just increments the number of cities. The game
     * handles the checking and removal of resources.
     */
    public void buildCity() {
        cities += 1;
    }

    /**
     * Add a knight, which simply increments the count for the player.
     */
    public void playKnight() {
        knights += 1;
    }

    /**
     * Return the number of roads the player has played.
     *
     * @return the number of roads the player has played.
     */
    public int roads() {
        return roads;
    }

    /**
     * Return the number of settlements the player has played.
     *
     * @return the number of settlements the player has played.
     */
    public int settlements() {
        return settlements;
    }

    /**
     * Return the number of cities the player has played.
     *
     * @return the number of cities the player has played.
     */
    public int cities() {
        return cities;
    }

    /**
     * Return the number of knights the player has played.
     *
     * @return the number of knights the player has played.
     */
    public int knights() {
        return knights;
    }

    /**
     * Return the size of the player's resource hand.
     *
     * @return the size of the player's resource hand.
     */
    public int resHandSize() {
        return hand.size();
    }

    /** The maximum number of settlements that a single player can play. */
    public static final int MAX_SETTLEMENTS = 4;

    /** The maximum number of roads that a single player can play. */
    public static final int MAX_ROADS = 13;

    /** The maximum number of cities that a single player can play. */
    public static final int MAX_CITIES = 3;

    /** The number that a player needs to trade for maritime, initially. */
    public static final int INIT_MARITIME_COST = 4;
}
