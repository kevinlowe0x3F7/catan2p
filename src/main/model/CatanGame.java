package src.main.model;

import java.util.HashMap;
import java.util.Collections;
import java.util.List;
import java.util.ArrayList;
import java.util.Random;

/**
 * Represents the game. Handles all game turns and manipulation of player objects
 *
 * @author Kevin Lowe
 */
public class CatanGame {
    /** First player of the game. */
    private Player player1;

    /** Second player of the game. */
    private Player player2;

    /** A reference to the board for this game. */
    private CatanBoard board;

    /** The development deck. */
    private List<DevelopmentCard> devDeck;

    /** The index for the development deck to draw the next card. */
    private int devIndex;

    /** The stack of resources. Internally as a hashmap mapping resource to the count left. */
    private HashMap<Resource, Integer> resources;

    /** The holder of the Longest Army card. Null indicates that no one has claimed it. */
    private Player longestArmyOwner;

    /** The holder of the Longest Road card. Null indicates that not one has claimed it. */
    private Player longestRoadOwner;

    /** Random number generator. */
    private Random rand;

    /**
     * Initializes a new game of Catan.
     * 
     * @param color1  The color for player 1
     * @param color2  The color for player 2
     */
    public CatanGame(Color color1, Color color2) {
        this.player1 = new Player(color1);
        this.player2 = new Player(color2);
        this.board = new CatanBoard();
        initializeDevDeck();
        this.devIndex = 0;
        this.resources = new HashMap<Resource, Integer>();
        for (Resource r : Resource.values()) {
            this.resources.put(r, INITIAL_RESOURCE_SIZE);
        }
        this.longestArmyOwner = null;
        this.longestRoadOwner = null;
        rand = new Random();
    }

    /**
     * Fills in a brand new development deck and shuffles it.
     */
    private void initializeDevDeck() {
        this.devDeck = new ArrayList<DevelopmentCard>();
        for (int i = 0; i < SOLDIERS; i++) {
            devDeck.add(DevelopmentCard.KNIGHT);
        }
        for (int i = 0; i < MONOPOLIES; i++) {
            devDeck.add(DevelopmentCard.MONOPOLY);
        }
        for (int i = 0; i < VP_CARDS; i++) {
            devDeck.add(DevelopmentCard.VICTORY);
        }
        for (int i = 0; i < ROAD_CARDS; i++) {
            devDeck.add(DevelopmentCard.ROADS);
        }
        for (int i = 0; i < PLENTIES; i++) {
            devDeck.add(DevelopmentCard.PLENTY);
        }
        Collections.shuffle(devDeck);
    }

    /**
     * Rolls a dice
     *
     * @return a number from 1 to 6, inclusive
     */
    public int rollDice() {
        return rand.nextInt(6) + 1;
    }

    /**
     * Allocates resources to the two players based on the die roll. First checks to see if
     * there are enough resources in supply for everyone. If there is, then distribute out,
     * otherwise do nothing for that resource.
     *
     * @param num  the number that was just rolled
     */
    public void distributeResources(int num) {
        HashMap<Resource, Integer> requested1 = new HashMap<Resource, Integer>();
        HashMap<Resource, Integer> requested2 = new HashMap<Resource, Integer>();
        for (Resource r : Resource.values()) {
            requested1.put(r, 0);
            requested2.put(r, 0);
        }
        for (HexPiece tile : tilesForNum(num)) {
            if (tile.hasRobber()) {
                continue;
            }
            Resource res = tile.resource();
            for (Building building : tile.getBuildings()) {
                if (building.owner() == player1) {
                    if (building.type().equals(Building.SETTLEMENT)) {
                        requested1.put(res, requested1.get(res) + 1);
                    } else {
                        requested1.put(res, requested1.get(res) + 2);
                    }
                } else {
                    if (building.type().equals(Building.SETTLEMENT)) {
                        requested2.put(res, requested2.get(res) + 1);
                    } else {
                        requested2.put(res, requested2.get(res) + 2);
                    }
                }
            }
        }
        for (Resource r : Resource.values()) {
            int totalRequested = requested1.get(r) + requested2.get(r);
            if (totalRequested <= resources.get(r)) {
                for (int i = 0; i < requested1.get(r); i++) {
                    player1.addResource(r);
                }
                for (int i = 0; i < requested2.get(r); i++) {
                    player2.addResource(r);
                }
                resources.put(r, resources.get(r) - totalRequested);
            }
        }
    }

    /**
     * Gets the hex tiles that correspond to a dice roll
     *
     * @param num  The dice sum number to check for
     *
     * @return a list of hex tiles with the same number as num
     */
    public List<HexPiece> tilesForNum(int num) {
        List<HexPiece> tiles = new ArrayList<HexPiece>();
        for (HexPiece tile : board.getTiles()) {
            if (num == 12) {
                if (tile.num() == 2) {
                    tiles.add(tile);
                    return tiles;
                }
            } else {
                if (tile.num() == num) {
                    tiles.add(tile);
                }
            }
        }
        return tiles;
    }

    /**
     * Builds a road for the player, checking to see that it is valid to do so.
     *
     * @param hex  the point identifying the hex that the road will be built on. note that
     *             a road is technically on two hexes, so this method should update both hexes
     * @param dir  the direction on the given hex that the player wants to build a road on.
     *             Again, a road is technically on two hexes, so this dir is with respect to
     *             the given hex
     * @param player  the player that wants to build this road
     *
     * @return true if the road building is successful, false otherwise
     */
    public boolean buildRoad(HexPoint hex, BuildingDir dir, Player player) {
        // TODO build road method
    }

    // TODO build settlement method (check for harbors as well)
    // TODO build city method
    // TODO initial location choosing
    // TODO handle 7 roll (remove over half of cards)
    // TODO activate robber method
    // TODO victory point devo card
    // TODO road building method
    // TODO year of plenty method
    // TODO monopoly method
    // TODO maritime trade
    // TODO can build settlement (resources, not exceeding max, distance rule)
    // TODO can build road (resources, not exceeding max, connecting rule)
    // TODO can build cities (resources, not exceeding max, on top of previous settlement)
    // TODO check win method
    // TODO end turn method
    // TODO calculate longest road method

    /** The number of cards in the development deck. */
    public static final int DEV_DECK_SIZE = 18;

    /** The initial size of each resource pile. */
    public static final int INITIAL_RESOURCE_SIZE = 19;

    /** The number of soldiers in the development deck. */
    public static final int SOLDIERS = 11;

    /** The number of monopoly cards in the development deck. */
    public static final int MONOPOLIES = 1;

    /** The number of victory point cards in the development deck. */
    public static final int VP_CARDS = 3;

    /** The number of road building cards in the development deck. */
    public static final int ROAD_CARDS = 1;

    /** The number of year of plenty cards in the development deck. */
    public static final int PLENTIES = 2;
}
