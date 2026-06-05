package cells;

import java.awt.Color;
import model.FireWorld;

/**
 * CS 142 - Final Project: Fire Spread Simulation
 * Group: Fire Starters - Khoa Cao, Duy Nguyen, Khoi Nguyen
 *
 * WHAT THIS FILE DOES:
 * --------------------
 * Represents a cell containing DRY GRASS or BRUSH on the grid.
 *
 * - Has MEDIUM fuel (4) — burns faster than trees
 * - Shows as YELLOW-GREEN in the GUI
 * - Shows as 'V' in text output
 * - Like TreeCell, fire spreads TO it from neighboring FireCells
 * - Easier to ignite than trees (FireCell gives it a higher spread chance)
 */
public class VegetationCell extends FuelCell {

    private static final int VEGETATION_FUEL = 4; // less fuel than trees

    /**
     * Creates a VegetationCell with medium fuel at the given position.
     * @param row the row index
     * @param col the column index
     */
    public VegetationCell(int row, int col) {
        super(row, col, VEGETATION_FUEL);
    }

    /**
     * WHAT THIS DOES:
     * Vegetation does nothing on its own — fire spreads TO it from
     * neighboring FireCells. It just waits to be ignited.
     *
     * @param world the FireWorld grid
     */
    @Override
    public void update(FireWorld world) {
        // Vegetation does nothing — FireCell handles spreading fire to it
    }

    /**
     * Returns yellow-green to look like dry grass/brush in the GUI.
     * @return yellow-green Color
     */
    @Override
    public Color getColor() {
        return new Color(154, 205, 50);
    }

    /**
     * Returns 'V' for vegetation in text/console output.
     * @return 'V'
     */
    @Override
    public char getSymbol() {
        return 'V';
    }
}
