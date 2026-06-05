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
 * specific burnable cell types (TreeCell, VegetationCell, FireCell).
 *
 * It adds the concept of FUEL LEVEL — how much burnable material
 * is left in this cell. As a cell burns, its fuel decreases.
 * When fuel hits 0, the cell burns out into ash.
 *
 * All cells that CAN burn extend this class.
 */
public abstract class FuelCell extends Cell {

    private int fuelLevel; // how much burnable material is left

    /**
     * Creates a burnable cell with a starting fuel amount.
     * @param row the row index
     * @param col the column index
     * @param fuelLevel how much fuel this cell starts with (higher = burns longer)
     */
    public FuelCell(int row, int col, int fuelLevel) {
        super(row, col);
        this.fuelLevel = fuelLevel;
    }

    /**
     * WHAT THIS DOES:
     * Returns how much fuel is left in this cell.
     * FireCell uses this to decide when to burn out.
     *
     * @return remaining fuel level
     */
    public int getFuelLevel() {
        return fuelLevel;
    }

    /**
     * WHAT THIS DOES:
     * Decreases the fuel in this cell by a given amount.
     * Called by FireCell when it is actively burning.
     *
     * @param amount how much fuel to remove
     */
    public void reduceFuel(int amount) {
        fuelLevel -= amount;
        if (fuelLevel < 0) {
            fuelLevel = 0;
        }
    }

    /**
     * All FuelCells can catch fire — always returns true.
     * @return true
     */
    @Override
    public boolean isFlammable() {
        return true;
    }
}
