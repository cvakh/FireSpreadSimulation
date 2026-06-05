package cells;

import java.awt.Color;
import model.FireWorld;

/**
 * CS 142 - Final Project: Fire Spread Simulation
 * Group: Fire Starters - Khoa Cao, Duy Nguyen, Khoi Nguyen
 *
 * WHAT THIS FILE DOES:
 * --------------------
 * Represents a cell that has ALREADY BURNED OUT completely.
 *
 * - Created when a FireCell's burn timer reaches 0
 * - Cannot catch fire again (acts like EmptyCell)
 * - Shows as DARK GRAY in the GUI (charred ground)
 * - Shows as 'A' in text output
 * - Never changes — once ash, always ash
 */
public class AshCell extends NonFuelCell {

    /**
     * Creates an AshCell (burned out ground) at the given position.
     * @param row the row index
     * @param col the column index
     */
    public AshCell(int row, int col) {
        super(row, col);
    }

    /**
     * Returns dark gray to look like charred ash in the GUI.
     * @return dark gray Color
     */
    @Override
    public Color getColor() {
        return new Color(80, 80, 80);
    }

    /**
     * Returns 'A' for ash in text/console output.
     * @return 'A'
     */
    @Override
    public char getSymbol() {
        return 'A';
    }
}
