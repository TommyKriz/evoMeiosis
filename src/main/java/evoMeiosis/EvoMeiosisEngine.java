package evoMeiosis;

import processing.core.PGraphics;
import evoMeiosis.player.PlayerSystem;
import evoMeiosis.trees.TreeSystem;

public class EvoMeiosisEngine {

	TreeSystem treeSystem;

	PlayerSystem playerSystem;

	public EvoMeiosisEngine(
			deepSpace.tuio.DeepSpaceTUIOHelper deepSpaceTUIOHelper) {
		playerSystem = new PlayerSystem(deepSpaceTUIOHelper);
		//
	}

	public void update() {
		// tree update...
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
