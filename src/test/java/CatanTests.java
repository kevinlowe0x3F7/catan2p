package src.test.java;

import src.main.model.*;
import org.junit.Test;
import static org.junit.Assert.*;

import org.junit.runner.JUnitCore;
import org.junit.runner.Result;
import org.junit.runner.notification.Failure;

import java.util.Scanner;
import java.awt.Color;

/**
 * Tests for Settlers of Catan
 * @author Kevin Lowe
 */

public class CatanTests {
    @Test
    public void testPlayer() {
        Player p = new Player(Color.WHITE);
        assertEquals(p.color(), Color.WHITE);
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
