package evoMeiosis;

import processing.core.PApplet;

public class TreeSpawner implements ITreeSpawner {

	@Override
	public void spawnTree(PApplet parent, float x, float y) {

		parent.fill(255, 50, 50, 100);
		parent.ellipse(x, y, 40, 40);

	}

}
