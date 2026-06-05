package cells;

import java.awt.Color;
import model.FireWorld;
import java.util.Random;

/**
 * CS 142 - Final Project: Fire Spread Simulation
 * Group: Fire Starters - Khoa Cao, Duy Nguyen, Khoi Nguyen
 *
 * WHAT THIS FILE DOES:
 * --------------------
 * Represents an ACTIVELY BURNING cell on the grid.
 * This is the most important cell — it drives the whole simulation.
 *
 * Every tick it does TWO things:
 *   1. Tries to SPREAD fire to each of its 4 neighbors (N/S/E/W)
 *      - Spread chance is affected by humidity (wet = harder to spread)
 *      - Spread chance is boosted in the wind direction
 *   2. Counts DOWN a burn timer — when it hits 0, becomes AshCell
 *
 * - Shows as ORANGE in the GUI
 * - Shows as 'F' in text output
 */
public class FireCell extends FuelCell {

    private static final int FIRE_FUEL      = 3;    // how many ticks it burns
    private static final double BASE_SPREAD = 0.4;  // 40% base chance to spread

    private int burnTimer;  // counts down to 0, then becomes ash
    private Random rand;

    /**
     * Creates a FireCell that will burn for a fixed number of ticks.
     * @param row the row index
     * @param col the column index
     */
    public FireCell(int row, int col) {
        super(row, col, FIRE_FUEL);
        this.burnTimer = FIRE_FUEL;
        this.rand = new Random();
    }

    /**
     * WHAT THIS DOES (the main logic of the whole simulation):
     *
     * Step 1 — Try to spread fire to each of the 4 neighbors:
     *   - Calculate spread chance based on humidity and wind direction
     *   - If the neighbor is flammable and the random roll succeeds,
     *     schedule that neighbor to become a FireCell next tick
     *
     * Step 2 — Count down the burn timer:
     *   - Each tick the timer decreases by 1
     *   - When it hits 0, schedule this cell to become AshCell
     *
     * @param world the FireWorld grid (used to check neighbors and schedule changes)
     */
    @Override
    public void update(FireWorld world) {
        // The 4 neighbors: north, south, west, east
        int[][] neighbors = {
            {getRow() - 1, getCol()}, // north
            {getRow() + 1, getCol()}, // south
            {getRow(), getCol() - 1}, // west
            {getRow(), getCol() + 1}  // east
        };

        for (int[] pos : neighbors) {
            int r = pos[0];
            int c = pos[1];

            if (world.inBounds(r, c)) {
                Cell neighbor = world.getCell(r, c);

                // Base spread chance reduced by humidity (wetter = harder to burn)
                double spreadChance = BASE_SPREAD * (1.0 - world.getHumidity());

                // Wind gives a bonus in the direction it's blowing
                String wind = world.getWindDirection();
                if (wind.equals("N") && r < getRow()) spreadChance += 0.2;
                if (wind.equals("S") && r > getRow()) spreadChance += 0.2;
                if (wind.equals("E") && c > getCol()) spreadChance += 0.2;
                if (wind.equals("W") && c < getCol()) spreadChance += 0.2;

                // Spread if neighbor is flammable, not already on fire, and roll succeeds
                if (neighbor.isFlammable() && !(neighbor instanceof FireCell)
                        && rand.nextDouble() < spreadChance) {
                    world.scheduleIgnite(r, c);
                }
            }
        }

        // Count down the burn timer
        burnTimer--;
        if (burnTimer <= 0) {
            world.scheduleAsh(getRow(), getCol());
        }
    }

    /**
     * Returns true when this cell has finished burning and should become ash.
     * @return true if burn timer is 0 or less
     */
    public boolean isBurnedOut() {
        return burnTimer <= 0;
    }

    /**
     * Returns bright orange to look like active fire in the GUI.
     * @return orange Color
     */
    @Override
    public Color getColor() {
        return new Color(255, 80, 0);
    }

    /**
     * Returns 'F' for fire in text/console output.
     * @return 'F'
     */
    @Override
    public char getSymbol() {
        return 'F';
    }
}
