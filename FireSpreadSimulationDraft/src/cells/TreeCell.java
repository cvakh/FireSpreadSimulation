package cells;

import java.awt.Color;
import model.FireWorld;

/**
 * CS 142 - Final Project: Fire Spread Simulation
 * Group: Fire Starters - Khoa Cao, Duy Nguyen, Khoi Nguyen
 *
 * WHAT THIS FILE DOES:
 * --------------------
 * Represents a cell containing a TREE on the grid.
 *
 * - Has HIGH fuel (8) so it burns for a long time once ignited
 * - Shows as DARK GREEN in the GUI
 * - Shows as 'T' in text output
 * - Does NOT spread fire itself — FireCell neighbors spread TO it
 * - Once ignited by a neighbor, it becomes a FireCell
 */
public class TreeCell extends FuelCell {

    private static final int TREE_FUEL = 8; // trees have the most fuel

    /**
     * Creates a TreeCell with high fuel at the given position.
     * @param row the row index
     * @param col the column index
     */
    public TreeCell(int row, int col) {
        super(row, col, TREE_FUEL);
    }

    /**
     * WHAT THIS DOES:
     * Trees just sit there — fire spreads TO them from neighboring FireCells.
     * The FireCell's update() is what checks neighbors and ignites this tree.
     *
     * @param world the FireWorld grid
     */
    @Override
    public void update(FireWorld world) {
        // Trees do nothing — FireCell handles spreading fire to them
    }

    /**
     * Returns dark green to look like a standing tree in the GUI.
     * @return dark green Color
     */
    @Override
    public Color getColor() {
        return new Color(34, 100, 34);
    }

    /**
     * Returns 'T' for tree in text/console output.
     * @return 'T'
     */
    @Override
    public char getSymbol() {
        return 'T';
    }
}
