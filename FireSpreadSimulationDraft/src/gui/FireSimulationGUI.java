package gui;

import model.FireWorld;
import cells.Cell;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

/**
 * CS 142 - Final Project: Fire Spread Simulation
 * Group: Fire Starters - Khoa Cao, Duy Nguyen, Khoi Nguyen
 *
 * WHAT THIS FILE DOES:
 * --------------------
 * This is the VIEW — it draws the simulation on screen and animates it.
 * Think of it as the "eyes" of the simulation.
 *
 * It creates a window (JFrame) containing a panel (JPanel).
 * A Timer fires every 300ms and does two things:
 *   1. Calls world.update() to advance the simulation one tick
 *   2. Calls repaint() to redraw the updated grid on screen
 *
 * Each cell is drawn as a colored square using the cell's own getColor().
 * A legend at the bottom explains what each color means.
 */
public class FireSimulationGUI extends JPanel {

    // --- Display settings ---
    private static final int CELL_SIZE = 20;  // pixels wide/tall per cell
    private static final int DELAY     = 300; // milliseconds between ticks

    private FireWorld world;
    private Timer timer;
    private int tickCount; // how many ticks have passed (shown on screen)

    /**
     * WHAT THIS DOES:
     * Sets up the panel size based on grid dimensions,
     * and creates the Timer that drives the animation.
     *
     * @param world the FireWorld model to display and animate
     */
    public FireSimulationGUI(FireWorld world) {
        this.world     = world;
        this.tickCount = 0;

        int panelWidth  = world.getCols() * CELL_SIZE;
        int panelHeight = world.getRows() * CELL_SIZE + 60; // +60 for legend bar
        setPreferredSize(new Dimension(panelWidth, panelHeight));
        setBackground(Color.BLACK);

        // Timer: every DELAY ms → advance the sim + redraw
        timer = new Timer(DELAY, new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                world.update();
                tickCount++;
                repaint(); // triggers paintComponent()
            }
        });
    }

    /**
     * WHAT THIS DOES:
     * Starts the animation loop (the Timer begins firing).
     * Call this after the window is visible.
     */
    public void start() {
        timer.start();
    }

    /**
     * WHAT THIS DOES:
     * Draws the entire current state of the grid onto the screen.
     * Called automatically by Java every time repaint() is called.
     *
     * For each cell:
     *   - Fill a rectangle with the cell's color
     *   - Draw a thin black border around it
     *
     * Also draws a top info bar (tick count, wind, humidity)
     * and a bottom legend explaining the colors.
     *
     * @param g the Graphics context provided by Java's paint system
     */
    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        // Draw every cell as a colored rectangle
        for (int r = 0; r < world.getRows(); r++) {
            for (int c = 0; c < world.getCols(); c++) {
                Cell cell = world.getCell(r, c);

                // Fill the cell with its color
                g.setColor(cell.getColor());
                g.fillRect(c * CELL_SIZE, r * CELL_SIZE, CELL_SIZE, CELL_SIZE);

                // Thin black border between cells
                g.setColor(new Color(0, 0, 0, 60));
                g.drawRect(c * CELL_SIZE, r * CELL_SIZE, CELL_SIZE, CELL_SIZE);
            }
        }

        // --- Info bar at the top ---
        g.setColor(new Color(0, 0, 0, 150));
        g.fillRect(0, 0, world.getCols() * CELL_SIZE, 22);
        g.setColor(Color.WHITE);
        g.setFont(new Font("Arial", Font.BOLD, 13));
        g.drawString("Tick: " + tickCount
                   + "   Wind: " + world.getWindDirection()
                   + "   Humidity: " + world.getHumidity(),
                   8, 15);

        // --- Legend bar at the bottom ---
        int legendY = world.getRows() * CELL_SIZE + 5;
        g.setFont(new Font("Arial", Font.PLAIN, 12));
        drawLegendItem(g, new Color(34, 100, 34),  "Tree",       5,   legendY);
        drawLegendItem(g, new Color(154, 205, 50), "Vegetation", 100, legendY);
        drawLegendItem(g, new Color(255, 80, 0),   "Fire",       220, legendY);
        drawLegendItem(g, new Color(194, 178, 128),"Empty",      290, legendY);
        drawLegendItem(g, new Color(80, 80, 80),   "Ash",        370, legendY);
    }

    /**
     * WHAT THIS DOES:
     * Helper method — draws one colored square + label for the legend.
     *
     * @param g     Graphics context
     * @param color the color square to draw
     * @param label the text label next to it
     * @param x     horizontal position
     * @param y     vertical position
     */
    private void drawLegendItem(Graphics g, Color color, String label, int x, int y) {
        g.setColor(color);
        g.fillRect(x, y, 14, 14);
        g.setColor(Color.WHITE);
        g.drawString(label, x + 18, y + 12);
    }

    /**
     * WHAT THIS DOES:
     * Entry point for the GUI version of the simulation.
     * Creates the world, ignites the center, builds the window, and starts.
     *
     * @param args command line arguments (unused)
     */
    public static void main(String[] args) {
        // Create a 25-row x 40-column world, low humidity, wind blowing East
        FireWorld world = new FireWorld(25, 40, 0.2, "E");

        // Light the fire in the center of the grid
        world.ignite(12, 20);

        // Build and show the window
        FireSimulationGUI panel = new FireSimulationGUI(world);

        JFrame frame = new JFrame("Fire Spread Simulation — Fire Starters");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.add(panel);
        frame.pack();
        frame.setLocationRelativeTo(null); // center on screen
        frame.setVisible(true);

        panel.start(); // begin the animation
    }
}
