package cells;

import java.awt.Color;
import model.FireWorld;

/**
 * CS 142 - Final Project: Fire Spread Simulation
 * Group: Fire Starters - Khoa Cao, Duy Nguyen, Khoi Nguyen
 *
 * WHAT THIS FILE DOES:
 * --------------------
 * Represents BARE GROUND on the grid — nothing to burn here.
 *
 * - Has NO fuel, so fire cannot spread to it
 * - Acts as a natural FIREBREAK, stopping fire in its path
 * - Shows as SANDY BROWN in the GUI
 * - Shows as '.' in text output
 * - Never changes — stays empty forever
 */
public class EmptyCell extends NonFuelCell {

    /**
     * Creates an EmptyCell (bare ground) at the given position.
     * @param row the row index
     * @param col the column index
     */
    public EmptyCell(int row, int col) {
        super(row, col);
    }

    /**
     * Returns sandy brown to look like bare dirt/ground in the GUI.
     * @return light brown Color
     */
    @Override
    public Color getColor() {
        return new Color(194, 178, 128);
    }

    /**
     * Returns '.' for empty ground in text/console output.
     * @return '.'
     */
    @Override
    public char getSymbol() {
        return '.';
    }
}
