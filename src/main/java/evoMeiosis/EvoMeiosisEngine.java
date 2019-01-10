package evoMeiosis;

import javazoom.jl.player.Player;
import processing.core.PGraphics;
import evoMeiosis.player.PlayerSystem;
import evoMeiosis.seeds.FreeSeed;
import evoMeiosis.seeds.SeedSystem;
import evoMeiosis.trees.Tree;
import evoMeiosis.trees.TreeSystem;

public class EvoMeiosisEngine {

	PlayerSystem playerSystem;

	TreeSystem treeSystem;

	SeedSystem seedSystem;

	public EvoMeiosisEngine(
			deepSpace.tuio.DeepSpaceTUIOHelper deepSpaceTUIOHelper) {
		playerSystem = new PlayerSystem(deepSpaceTUIOHelper);
		//
	}

	public void update() {
		// tree update...
	}

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

	/**
	 * 
	 * 
	 * 
	 * 
	 * 
	 * 
	 * 
	 * 
	 * 
	 * 
	 * demo
	 * 
	 * 
	 * 
	 * 
	 * 
	 * 
	 * 
	 * 
	 * 
	 * 
	 * 
	 * 
	 * @param canvas
	 */

	public void paintPlayers(PGraphics canvas) {
		playerSystem.paintPlayers(canvas);
	}

	public void paintTrails(PGraphics canvas) {
		canvas.beginDraw();
		canvas.fill(250, 0, 0, 80);
		canvas.rect(0, 0, canvas.width, canvas.height);
		canvas.endDraw();
	}

}
