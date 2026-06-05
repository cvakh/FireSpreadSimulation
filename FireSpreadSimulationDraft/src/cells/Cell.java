package cells;

import java.awt.Color;
import model.FireWorld;

/**
 * CS 142 - Final Project: Fire Spread Simulation
 * Group: Fire Starters - Khoa Cao, Duy Nguyen, Khoi Nguyen
 *
 * WHAT THIS FILE DOES:
 * --------------------
 * This is the ABSTRACT BASE CLASS for every single cell in the grid.
 * Think of it as a "template" — it defines what ALL cells must have
 * (a position, a color, a symbol) but leaves the specific behavior
 * up to each subclass.
 *
 * Every cell in the simulation (tree, fire, ash, etc.) extends this class.
 */
public abstract class Cell {

    private int row;  // which row this cell is in on the grid
    private int col;  // which column this cell is in on the grid

    /**
     * Creates a cell at a specific position on the grid.
     * @param row the row index (0 = top)
     * @param col the column index (0 = left)
     */
    public Cell(int row, int col) {
        this.row = row;
        this.col = col;
    }

    /**
     * WHAT THIS DOES:
     * Each subclass must define what happens to this cell every tick.
     * Example: FireCell spreads fire to neighbors. EmptyCell does nothing.
     *
     * @param world the full grid, so this cell can check its neighbors
     */
    public abstract void update(FireWorld world);

    /**
     * WHAT THIS DOES:
     * Each subclass must define what color it shows in the GUI.
     * Example: TreeCell returns dark green, FireCell returns orange.
     *
     * @return the Color to draw this cell in the GUI
     */
    public abstract Color getColor();

    /**
     * WHAT THIS DOES:
     * Each subclass must define whether fire can spread to it.
     * Example: TreeCell returns true, EmptyCell returns false.
     *
     * @return true if this cell can catch fire
     */
    public abstract boolean isFlammable();

    /**
     * WHAT THIS DOES:
     * Each subclass must define a single character symbol for text output.
     * Example: TreeCell returns 'T', FireCell returns 'F'.
     *
     * @return single char to represent this cell in the console
     */
    public abstract char getSymbol();

    /**
     * Returns the row index of this cell.
     * @return row
     */
    public int getRow() {
        return row;
    }

    /**
     * Returns the column index of this cell.
     * @return col
     */
    public int getCol() {
        return col;
    }
}
