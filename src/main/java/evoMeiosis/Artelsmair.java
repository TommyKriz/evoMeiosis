package evoMeiosis;

import javazoom.jl.player.Player;
import processing.core.PApplet;
import processing.core.PFont;
import evoMeiosis.seeds.FreeSeed;
import evoMeiosis.trees.Tree;

public class Artelsmair {

	PFont font;

	int[][] freeSeedFieldColor;
	boolean[] freeSeedFieldParticle;

	float fmax = 2000;
	float fMin = 20;
	float ampMax = 3;
	float ampMin = 0.2f;
	float globalSpeed = 0.03f;

	float trailDecayRate = 10;

	// int catchRadius = 100;

	void setup() {

		freeSeedFieldColor = new int[fieldWidth * fieldHeight][3];
		freeSeedFieldParticle = new boolean[fieldWidth * fieldHeight];

	}

	void draw() {

		// check for tree
		// spawning------------------------------------------------


		// update
		// cycle-------------------------------------------------------------

		collectFreeSeeds();

		// -------------------------------------------
		renderFreeSeeds();
		image(pgTrails, 0, 0);

		// ---------------------------------------------
		renderTrees();
		image(pgTrees, 0, 0);

		drawplayers

	}

	/*
	 * void mouseReleased() { players.get(0).release(); }
	 */

	// -----------------------------------------------------------------------------
	void collectFreeSeeds() {
		for (int f = 0; f < freeSeeds.size(); f++) {
			// add to player if in range
			FreeSeed s = freeSeeds.get(f);
			if (!s.collected) {
				for (Player p : players) {
					float dist = distance(p.xOrig, p.yOrig, s.x, s.y);
					if (dist < catchRadius) {
						s.attr = p;
						s.collected = true;
						p.seeds.add(s);
					}
				}
			} else {
				for (Tree t : globalTrees) {
					// add to tree if in range
					float dist = distance(t.originX, t.originY, s.x, s.y);
					if (dist <= treeRadius) {
						s.attr = t;
						t.addSeed(s);
						s.destroy();
					}
				}

			}
		}
	}

}
