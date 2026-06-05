Fire Spread Simulation
CS 142 Final Project | Group: Fire Starters
Khoa Cao · Duy Nguyen · Khoi Nguyen

Project Folder Structure
FireSpreadSimulation/
│
├── src/
│   │
│   ├── cells/          ← ALL cell types live here (the inheritance hierarchy)
│   │   ├── Cell.java           Level 1: Abstract base — every cell extends this
│   │   ├── FuelCell.java       Level 2: Abstract — cells that CAN burn
│   │   ├── NonFuelCell.java    Level 2: Abstract — cells that CANNOT burn
│   │   ├── TreeCell.java       Level 3: A tree (high fuel, dark green)
│   │   ├── VegetationCell.java Level 3: Dry grass (medium fuel, yellow-green)
│   │   ├── FireCell.java       Level 3: Active fire (spreads to neighbors, orange)
│   │   ├── EmptyCell.java      Level 3: Bare ground (no fuel, firebreak, brown)
│   │   └── AshCell.java        Level 3: Burned out (no fuel, dark gray)
│   │
│   ├── model/          ← The simulation brain
│   │   └── FireWorld.java      Stores the grid, runs update() each tick
│   │
│   ├── gui/            ← The animated window
│   │   └── FireSimulationGUI.java  Draws the grid, runs on a timer
│   │
│   └── main/           ← Entry points (how you run it)
│       └── FireSimulation.java     Text/console version (no GUI)
│
└── README.md
What Each Part Does
File	What it does
Cell.java	Template for ALL cells — defines position, color, symbol
FuelCell.java	Adds fuel level to burnable cells
NonFuelCell.java	Marks cells that block fire
TreeCell.java	Dark green tree — high fuel, burns long
VegetationCell.java	Yellow-green grass — medium fuel, ignites easily
FireCell.java	The core logic — spreads fire, counts down, becomes ash
EmptyCell.java	Brown dirt — blocks fire, never changes
AshCell.java	Gray ash — burned out, never changes
FireWorld.java	Holds the grid, calls update on all cells each tick
FireSimulationGUI.java	Opens a window, draws the grid, animates it
FireSimulation.java	Prints the grid to the console (text only, no window)

Inheritance Hierarchy
Cell  (abstract)                     ← Level 1
├── FuelCell  (abstract)             ← Level 2
│   ├── TreeCell                     ← Level 3
│   ├── VegetationCell               ← Level 3
│   └── FireCell                     ← Level 3
└── NonFuelCell  (abstract)          ← Level 2
    ├── EmptyCell                    ← Level 3
    └── AshCell                      ← Level 3
Simulation Parameters (edit in FireWorld.java or FireSimulationGUI.java)
Parameter	Default	Effect
humidity	0.2	Higher = harder for fire to spread
windDirection	"E"	Fire spreads faster that direction
Grid size	25 x 40	Bigger = more terrain
