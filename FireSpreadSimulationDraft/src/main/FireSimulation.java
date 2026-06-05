package main;

import model.FireWorld;

/**
 * CS 142 - Final Project: Fire Spread Simulation
 * Group: Fire Starters - Khoa Cao, Duy Nguyen, Khoi Nguyen
 *
 * WHAT THIS FILE DOES:
 * --------------------
 * This is the TEXT version of the simulation — no GUI, just console output.
 * It is the simplest way to test that the simulation logic works.
 *
 * Run this first to verify the grid prints correctly and fire spreads.
 * If this works, the GUI version should work too.
 *
 * What it does:
 *   1. Creates a 15x30 grid world
 *   2. Prints the initial grid (all terrain, no fire yet)
 *   3. Ignites the center cell
 *   4. Runs 5 ticks and prints the grid after each one
 *
 * Expected output:
 *   T = Tree   V = Vegetation   F = Fire   . = Empty   A = Ash
 */
public class FireSimulation {

    public static void main(String[] args) {
        // Create a small world for easy reading in the console
        // Parameters: rows, cols, humidity (0=dry, 1=wet), wind direction
        FireWorld world = new FireWorld(15, 30, 0.2, "E");

        System.out.println("=== Fire Spread Simulation (Text Mode) ===");
        System.out.println("T=Tree  V=Vegetation  F=Fire  .=Empty  A=Ash");
        System.out.println();

        System.out.println("--- Initial State (no fire yet) ---");
        System.out.println(world.toText());

        // Start the fire in the center of the grid
        world.ignite(7, 15);
        System.out.println("--- Fire Ignited at center (7, 15) ---");
        System.out.println(world.toText());

        // Simulate 5 ticks and print after each one
        for (int step = 1; step <= 5; step++) {
            world.update();
            System.out.println("--- Step " + step + " ---");
            System.out.println(world.toText());
        }

        System.out.println("Text simulation complete. Run FireSimulationGUI for the animated version.");
    }
}
