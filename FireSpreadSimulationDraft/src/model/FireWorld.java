package model;

import cells.*;
import java.util.ArrayList;
import java.util.Random;

/**
 * CS 142 - Final Project: Fire Spread Simulation
 * Group: Fire Starters - Khoa Cao, Duy Nguyen, Khoi Nguyen
 *
 * WHAT THIS FILE DOES:
 * --------------------
 * This is the MODEL — it stores the entire simulation state.
 * Think of it as the "brain" of the simulation.
 *
 * It holds a 2D grid of Cell objects and is responsible for:
 *   1. Setting up the grid with random terrain at the start
 *   2. Advancing the simulation one tick at a time (update)
 *   3. Letting cells schedule changes (ignite a neighbor, turn to ash)
 *   4. Providing grid info to the GUI so it knows what to draw
 *
 * The GUI calls update() repeatedly on a timer to animate the sim.
 */
public class FireWorld {

    private Cell[][] grid;        // the 2D grid of all cells
    private int rows;
    private int cols;
    private double humidity;      // 0.0 = bone dry, 1.0 = soaking wet
    private String windDirection; // "N", "S", "E", or "W"

    // These lists hold scheduled changes — applied AFTER all cells update
    // (so cells don't affect each other mid-tick)
    private ArrayList<int[]> toIgnite; // cells that should catch fire next
    private ArrayList<int[]> toAsh;    // cells that should become ash next

    private Random rand;

    /**
     * Creates a FireWorld and randomly fills it with terrain.
     * 50% trees, 30% vegetation, 20% empty ground.
     *
     * @param rows         number of rows in the grid
     * @param cols         number of columns in the grid
     * @param humidity     0.0 (dry/easy to burn) to 1.0 (wet/hard to burn)
     * @param windDirection "N", "S", "E", or "W" — boosts fire spread that way
     */
    public FireWorld(int rows, int cols, double humidity, String windDirection) {
        this.rows          = rows;
        this.cols          = cols;
        this.humidity      = humidity;
        this.windDirection = windDirection;
        this.grid          = new Cell[rows][cols];
        this.toIgnite      = new ArrayList<>();
        this.toAsh         = new ArrayList<>();
        this.rand          = new Random();
        populateGrid();
    }

    /**
     * WHAT THIS DOES:
     * Randomly fills every cell in the grid with a terrain type:
     *   - 50% chance → TreeCell (dense fuel)
     *   - 30% chance → VegetationCell (light fuel)
     *   - 20% chance → EmptyCell (no fuel / firebreak)
     */
    private void populateGrid() {
        for (int r = 0; r < rows; r++) {
            for (int c = 0; c < cols; c++) {
                double roll = rand.nextDouble();
                if (roll < 0.50) {
                    grid[r][c] = new TreeCell(r, c);
                } else if (roll < 0.80) {
                    grid[r][c] = new VegetationCell(r, c);
                } else {
                    grid[r][c] = new EmptyCell(r, c);
                }
            }
        }
    }

    /**
     * WHAT THIS DOES:
     * Immediately sets a cell on fire if it is flammable.
     * Used at the start to ignite the first fire.
     *
     * @param row row index of the cell to ignite
     * @param col column index of the cell to ignite
     */
    public void ignite(int row, int col) {
        if (inBounds(row, col) && grid[row][col].isFlammable()) {
            grid[row][col] = new FireCell(row, col);
        }
    }

    /**
     * WHAT THIS DOES:
     * Advances the entire simulation by ONE tick:
     *   Step 1 — Clear the scheduled-change lists
     *   Step 2 — Call update() on every cell (they add to the lists)
     *   Step 3 — Apply all scheduled ignitions (new FireCells)
     *   Step 4 — Apply all scheduled burnouts (new AshCells)
     *
     * The GUI calls this repeatedly on a timer to animate the simulation.
     */
    public void update() {
        toIgnite.clear();
        toAsh.clear();

        // Let every cell decide what it wants to do this tick
        for (int r = 0; r < rows; r++) {
            for (int c = 0; c < cols; c++) {
                grid[r][c].update(this);
            }
        }

        // Now apply the scheduled changes (AFTER all cells have updated)
        for (int[] pos : toIgnite) {
            ignite(pos[0], pos[1]);
        }
        for (int[] pos : toAsh) {
            grid[pos[0]][pos[1]] = new AshCell(pos[0], pos[1]);
        }
    }

    /**
     * WHAT THIS DOES:
     * Called by FireCell to request that a neighbor catches fire.
     * The change is queued and applied after all cells finish updating.
     *
     * @param row row of the cell to ignite
     * @param col col of the cell to ignite
     */
    public void scheduleIgnite(int row, int col) {
        toIgnite.add(new int[]{row, col});
    }

    /**
     * WHAT THIS DOES:
     * Called by FireCell when its burn timer runs out.
     * Queues this cell to become an AshCell after all cells finish updating.
     *
     * @param row row of the cell to turn to ash
     * @param col col of the cell to turn to ash
     */
    public void scheduleAsh(int row, int col) {
        toAsh.add(new int[]{row, col});
    }

    /**
     * Returns the Cell at the given grid position.
     * Used by the GUI to know what color to draw each cell.
     *
     * @param row row index
     * @param col column index
     * @return the Cell at that position
     */
    public Cell getCell(int row, int col) {
        return grid[row][col];
    }

    /**
     * Returns whether a given position is inside the grid.
     * Used by FireCell to avoid checking out-of-bounds neighbors.
     *
     * @param row row index
     * @param col column index
     * @return true if the position is valid
     */
    public boolean inBounds(int row, int col) {
        return row >= 0 && row < rows && col >= 0 && col < cols;
    }

    /** @return number of rows in the grid */
    public int getRows() { return rows; }

    /** @return number of columns in the grid */
    public int getCols() { return cols; }

    /** @return humidity level (0.0 to 1.0) */
    public double getHumidity() { return humidity; }

    /** @return wind direction string ("N", "S", "E", "W") */
    public String getWindDirection() { return windDirection; }

    /**
     * WHAT THIS DOES:
     * Builds a text version of the grid using each cell's symbol.
     * Used by FireSimulation.java to print the grid to the console.
     *
     * T = Tree   V = Vegetation   F = Fire   . = Empty   A = Ash
     *
     * @return multi-line String showing the full grid
     */
    public String toText() {
        StringBuilder sb = new StringBuilder();
        sb.append("Wind: ").append(windDirection)
          .append("  |  Humidity: ").append(humidity).append("\n");
        for (int r = 0; r < rows; r++) {
            for (int c = 0; c < cols; c++) {
                sb.append(grid[r][c].getSymbol()).append(" ");
            }
            sb.append("\n");
        }
        return sb.toString();
    }
}
