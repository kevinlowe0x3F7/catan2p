package src.test.java;

import src.main.model.*;
import org.junit.Test;
import static org.junit.Assert.*;

import org.junit.runner.JUnitCore;
import org.junit.runner.Result;
import org.junit.runner.notification.Failure;

import java.util.Scanner;
import java.util.HashMap;
import java.util.List;
import java.awt.Color;

/**
 * Tests for Settlers of Catan
 * @author Kevin Lowe
 */

public class CatanTests {
    @Test
    public void testRoadLocEnum() {
        assertEquals(HexPiece.RoadLoc.N.complement(), HexPiece.RoadLoc.S);
        assertEquals(HexPiece.RoadLoc.NE.complement(), HexPiece.RoadLoc.SW);
        assertEquals(HexPiece.RoadLoc.SE.complement(), HexPiece.RoadLoc.NW);
        assertEquals(HexPiece.RoadLoc.S.complement(), HexPiece.RoadLoc.N);
        assertEquals(HexPiece.RoadLoc.SW.complement(), HexPiece.RoadLoc.NE);
        assertEquals(HexPiece.RoadLoc.NW.complement(), HexPiece.RoadLoc.SE);
        
        HexPiece.RoadLoc loc = HexPiece.RoadLoc.N;
        loc = loc.next(); assertEquals(loc, HexPiece.RoadLoc.NE);
        loc = loc.next(); assertEquals(loc, HexPiece.RoadLoc.SE);
        loc = loc.next(); assertEquals(loc, HexPiece.RoadLoc.S);
        loc = loc.next(); assertEquals(loc, HexPiece.RoadLoc.SW);
        loc = loc.next(); assertEquals(loc, HexPiece.RoadLoc.NW);
        loc = loc.next(); assertEquals(loc, HexPiece.RoadLoc.N);

        loc = loc.prev(); assertEquals(loc, HexPiece.RoadLoc.NW);
        loc = loc.prev(); assertEquals(loc, HexPiece.RoadLoc.SW);
        loc = loc.prev(); assertEquals(loc, HexPiece.RoadLoc.S);
        loc = loc.prev(); assertEquals(loc, HexPiece.RoadLoc.SE);
        loc = loc.prev(); assertEquals(loc, HexPiece.RoadLoc.NE);
        loc = loc.prev(); assertEquals(loc, HexPiece.RoadLoc.N);
    }

    @Test
    public void testHexPointHashing() {
        HashMap<HexPoint, Integer> map = new HashMap<HexPoint, Integer>();
        map.put(new HexPoint(2, 3), 5);
        assertTrue(map.containsKey(new HexPoint(2, 3)));
        assertEquals((int) map.get(new HexPoint(2, 3)), 5);
        assertFalse(map.containsKey(new HexPoint(3, 5)));

        map.put(new HexPoint(2, 3), 10);
        assertTrue(map.containsKey(new HexPoint(2, 3)));
        assertEquals((int) map.get(new HexPoint(2, 3)), 10);
    }

    @Test
    public void testBoardRepresentation() {
        CatanBoard board = new CatanBoard();
        int[][] testPoints = {{2,2}, {3,1}, {1,3}, {4, 3}, {2, 5}};
        for (int[] testPoint : testPoints) {
            assertTrue(board.isValidPoint(new HexPoint(testPoint[0], testPoint[1])));
        }
        int[][] testBadPoints = {{1, 0}, {0, 3}, {-1, 5}, {4, 2}, {5, 3}};
        for (int[] testPoint : testBadPoints) {
            assertFalse(board.isValidPoint(new HexPoint(testPoint[0], testPoint[1])));
        }
        int fields = CatanBoard.FIELDS;
        int forests = CatanBoard.FORESTS;
        int pastures = CatanBoard.PASTURES;
        int mountains = CatanBoard.MOUNTAINS;
        int hills = CatanBoard.HILLS;
        for (HexPiece tile : board.getTiles()) {
            switch (tile.resource()) {
                case WHEAT:     fields -= 1;
                                break;
                case WOOD:      forests -= 1;
                                break;
                case SHEEP:     pastures -= 1;
                                break;
                case ORE:       mountains -= 1;
                                break;
                case BRICK:     hills -= 1;
                                break;
            }
            assertTrue(tile.roll() >= 2 && tile.roll() <= 12);
            assertFalse(tile.hasRobber());
        }
        assertEquals(fields, 0);
        assertEquals(forests, 0);
        assertEquals(pastures, 0);
        assertEquals(mountains, 0);
        assertEquals(hills, 0);
    }

    @Test
    public void testAdjacentHexMethod() {
        CatanBoard board = new CatanBoard();
        HexPoint test1 = new HexPoint(2, 1);
        assertEquals(board.getAdjacentHex(test1, HexPiece.RoadLoc.NW), new HexPoint(1, 0));
        assertEquals(board.getAdjacentHex(test1, HexPiece.RoadLoc.SE), new HexPoint(2, 2));
        HexPoint test2 = new HexPoint(1, 3);
        assertEquals(board.getAdjacentHex(test2, HexPiece.RoadLoc.N), new HexPoint(0, 3));
        assertEquals(board.getAdjacentHex(test2, HexPiece.RoadLoc.NE), new HexPoint(0, 4));
        HexPoint test3 = new HexPoint(3, 3);
        assertEquals(board.getAdjacentHex(test3, HexPiece.RoadLoc.SW), new HexPoint(3, 2));
        HexPoint test4 = new HexPoint(2, 4);
        assertEquals(board.getAdjacentHex(test4, HexPiece.RoadLoc.NE), new HexPoint(2, 5));
        assertEquals(board.getAdjacentHex(test4, HexPiece.RoadLoc.SE), new HexPoint(3, 5));
        assertEquals(board.getAdjacentHex(test4, HexPiece.RoadLoc.NW), new HexPoint(2, 3));
        assertEquals(board.getAdjacentHex(test4, HexPiece.RoadLoc.SW), new HexPoint(3, 3));
        HexPoint test5 = new HexPoint(3, 2);
        assertEquals(board.getAdjacentHex(test5, HexPiece.RoadLoc.SW), new HexPoint(4, 1));
        HexPoint test6 = new HexPoint(3, 5);
        assertEquals(board.getAdjacentHex(test6, HexPiece.RoadLoc.NE), new HexPoint(2, 6));
    }

    @Test
    public void testGameandPlayerInitialization() {
        CatanGame g = new CatanGame(Color.WHITE, Color.BLUE);
        assertEquals(g.devDeckSize(), CatanGame.DEV_DECK_SIZE);
        for (Resource r : Resource.values()) {
            assertEquals(g.resourceLeft(r), CatanGame.INITIAL_RESOURCE_SIZE);
        }
        Player p = new Player(Color.WHITE);
        assertEquals(p.color(), Color.WHITE);
        assertEquals(0, p.resHandSize());
        assertEquals(0, p.roads());
        assertEquals(0, p.settlements());
        assertEquals(0, p.cities());
        assertEquals(0, p.knights());
    }

    @Test
    public void testTilesForNum() {
        CatanGame g = new CatanGame(Color.WHITE, Color.BLUE);
        List<HexPiece> tilesForTwo = g.tilesForNum(2);
        assertEquals(tilesForTwo.size(), 1);
        assertEquals(2, tilesForTwo.get(0).roll());
        tilesForTwo = g.tilesForNum(12);
        assertEquals(tilesForTwo.size(), 1);
        assertEquals(2, tilesForTwo.get(0).roll());

        List<HexPiece> tilesForThree = g.tilesForNum(3);
        assertEquals(tilesForThree.size(), 1);
        assertEquals(3, tilesForThree.get(0).roll());
        List<HexPiece> tilesForSix = g.tilesForNum(6);
        assertEquals(tilesForSix.size(), 1);
        assertEquals(6, tilesForSix.get(0).roll());
        List<HexPiece> tilesForEleven = g.tilesForNum(11);
        assertEquals(tilesForEleven.size(), 1);
        assertEquals(11, tilesForEleven.get(0).roll());

        int[] doubles = {5, 8, 10, 9, 4};
        for (int num : doubles) {
            List<HexPiece> tiles = g.tilesForNum(num);
            assertEquals(tiles.size(), 2);
            assertEquals(tiles.get(0).roll(), num);
            assertEquals(tiles.get(1).roll(), num);
        }
    }

    public static void main(String[] args) {
        Result result = JUnitCore.runClasses(CatanTests.class);
        int tests = result.getRunCount();
        if (result.wasSuccessful()) {
            System.out.printf("Ran %d tests. All passed.%n", tests);
        } else {
            for (Failure failure : result.getFailures()) {
                System.out.println(failure.toString());
                System.out.println(cutStack(failure.getTrace()));
            }
            int passed = tests - result.getFailureCount();
            System.out.printf("Ran %d tests. %d passed.%n", tests, passed);
        }
    }

    /** Helper method to shorten stack trace. */
    public static String cutStack(String fullStack) {
        Scanner input = new Scanner(fullStack);
        String newStack = "";
        while (input.hasNext()) {
            String nextLine = input.nextLine();
            if (nextLine.indexOf("org.junit.") != -1) {
                continue;
            } else if (nextLine.indexOf("java.lang.") != -1) {
                if (nextLine.indexOf("Exception") == -1) {
                    continue;
                }
            } else if (nextLine.indexOf("sun.reflect.") != -1) {
                continue;
            }
            newStack += (nextLine + '\n');
        }
        input.close();
        return newStack.substring(0, newStack.lastIndexOf('\n'));
    }
}
