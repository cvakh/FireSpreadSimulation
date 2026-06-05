package cells;

import java.awt.Color;
import model.FireWorld;

/**
 * CS 142 - Final Project: Fire Spread Simulation
 * Group: Fire Starters - Khoa Cao, Duy Nguyen, Khoi Nguyen
 *
 * WHAT THIS FILE DOES:
 * --------------------
 * This is a LEVEL 2 abstract class — it sits between Cell and the
 * specific non-burnable cell types (EmptyCell, AshCell).
 *
 * These cells CANNOT catch fire and act as natural firebreaks.
 * They don't need to do anything each tick, so update() is empty.
 *
 * All cells that CANNOT burn extend this class.
 */
public abstract class NonFuelCell extends Cell {

    /**
     * Creates a non-burnable cell at the given position.
     * @param row the row index
     * @param col the column index
     */
    public NonFuelCell(int row, int col) {
        super(row, col);
    }

    /**
     * WHAT THIS DOES:
     * Non-burnable cells do nothing each tick.
     * They just sit there and block fire from passing through.
     *
     * @param world the FireWorld grid (unused here)
     */
    @Override
    public void update(FireWorld world) {
        // Nothing to do — this cell cannot burn
    }

    /**
     * NonFuelCells can never catch fire — always returns false.
     * @return false
     */
    @Override
    public boolean isFlammable() {
        return false;
    }
}
