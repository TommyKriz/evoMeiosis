package evoMeiosis;


import processing.core.PGraphics;

import java.awt.geom.Point2D;
import java.util.ArrayList;

import evoMeiosis.player.Player;
import evoMeiosis.player.PlayerSystem;
import evoMeiosis.seeds.FreeSeed;
import evoMeiosis.seeds.SeedSystem;
import evoMeiosis.trees.Tree;
import evoMeiosis.trees.TreeSystem;

public class EvoMeiosisEngine {

	public PlayerSystem playerSystem;

	TreeSystem treeSystem;

	SeedSystem seedSystem;

	public EvoMeiosisEngine(
			deepSpace.tuio.DeepSpaceTUIOHelper deepSpaceTUIOHelper) {
		playerSystem = new PlayerSystem(deepSpaceTUIOHelper);
		treeSystem = new TreeSystem();
		seedSystem = new SeedSystem();
		//
	}
	

	public void update() {
		// tree update...
		collectFreeSeeds();
		playerSystem.update();
	}

	void collectFreeSeeds() {

		for (int f = 0; f < seedSystem.freeSeeds.size(); f++) {
			// add to player if in range
			FreeSeed s = seedSystem.freeSeeds.get(f);
			if (!s.collected) {
				for (Player p : playerSystem.getPlayers()) {
					float dist = (float) Point2D.distance(p.xOrig, p.yOrig, s.x, s.y);
					if (dist < EvoMeiosisConstants.PLAYER_CATCH_RADIUS) {
						s.attr = p;
						s.collected = true;
						p.collectedSeeds.add(s);
						System.out.println("player " + p.id + " collected " + s.uniqueID);
					}
				}
			} else {
				for (Tree t : treeSystem.trees) {
					// add to tree if in range
					float dist = (float) Point2D.distance(t.originX, t.originY, s.x, s.y);
					if (dist <= t.radius) {
						s.attr = t;
						t.addSeed(s);
						seedSystem.destroy(s);
					}
				}

			}
		}
	}


	public void paintPlayers(PGraphics canvas) {
		playerSystem.paintPlayers(canvas);
	}

	public void paintSeeds(PGraphics canvas) {
		seedSystem.updateAndPaintSeeds(canvas);
	}
	
	public void paintTrails(PGraphics canvas) {
		seedSystem.paintTrailsV2(canvas);
		/*
		canvas.beginDraw();
		canvas.fill(250, 0, 0, 80);
		canvas.rect(0, 0, canvas.width, canvas.height);
		canvas.endDraw();*/
	}

}
