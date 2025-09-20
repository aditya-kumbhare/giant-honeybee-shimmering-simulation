# Giant Honeybee Shimmering Simulation
## An agent-based simulation which emulates the ripple effect of a swarm of Apis Dorsata (Asian Giant Honeybee) by replicating their individual behaviors in the presence of a predator.

### Features:
This project is a simulation of Apis Dorsata's behavior when in the presence of a predator. 

Apis Dorsata, or the Giant Honeybee, is a currently poorly-documented species native to Southern Asia, and is known for their "defense waving" behavior, a tactic used to confuse or ward off predators such as wasps or small animals.

This repository features two sequential attempts of mine at simulating the bees' shimmering behavior at an agent-based level. 
- **Version 1** was made to imitate the most basic aspects of the behavior.
- **Version 2** expands on version one by incorporating multiple other natural aspects of the bees' actions.
Each simulation uses Java's Swing library to generate a GUI representing a grid of bees, with each cell blinking black to represent the bees' shimmering.

### Usage:
To run the simulation, download the files into your desired folder, and navigate to the folder of the simulation version you desire (e.g. `parent_folder/giant-honeybee-shimmering-simulation/V2`).

Run the command `javac Beehive.java` to recompile as needed, and use `java Beehive <size>` to run the simulation, with `<size>` replaced with the desired width within bounds.

_Tested on Java 21. Compatibility with earlier versions not guaranteed._
