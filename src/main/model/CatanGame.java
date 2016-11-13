package src.main.model;

import java.util.HashMap;
import java.util.Collections;
import java.util.List;
import java.util.ArrayList;

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

    /**
     * Initializes a new game of Catan.
     * 
     * @param color1 The color for player 1
     * @param color2 The color for player 2
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
