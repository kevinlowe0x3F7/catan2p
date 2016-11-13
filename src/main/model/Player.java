package model;

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

    /** The hand of resources that the player owns. */
    private List<Resource> hand;

    /** The hand of development cards that the player owns and has not used. */
    private List<DevelopmentCard> devHand;

    /** 
     *  A list of development cards that the player received on this turn. Used to help
     *  differentiate between dev cards received on this turn since a player can't play
     *  development cards on the turn that they receive them.
     */
    private List<DevelopmentCard> receivedDevCards;

    /** An map for how much of a resource a player needs for maritime trade. Each
     *  resource is initialized to 4, then as the game progresses, if the player has access
     *  to harbors then the cost will go down. */
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
        points = 0;
        settlements = 0;
        roads = 0;
        cities = 0;
        hand = new ArrayList<Resource>();
        devHand = new ArrayList<DevelopmentCard>();
        receivedDevCards = new ArrayList<DevelopmentCard>();
        tradeCosts = new HashMap<Resource, Integer>();
        for (Resource r : Resource.values()) {
            tradeCosts.put(r, 4);
        }
        hasPlayedDev = false;
    }

    /** The maximum number of settlements that a single player can play. */
    public static final int MAX_SETTLEMENTS = 4;

    /** The maximum number of roads that a single player can play. */
    public static final int MAX_ROADS = 13;

    /** The maximum number of cities that a single player can play. */
    public static final int MAX_CITIES = 3;
}
