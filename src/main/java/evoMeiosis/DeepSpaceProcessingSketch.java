package evoMeiosis;

import processing.core.PApplet;
import processing.core.PVector;

public class DeepSpaceProcessingSketch extends PApplet {

	private static final int scaleFactor = 4;
	int windowWidth = 3030 / scaleFactor; // for real Deep Space this should be
											// // 3030
	int windowHeight = 3712 / scaleFactor; // for real Deep Space this should be
											// 3712
	int wallHeight = 1914 / scaleFactor; // for real Deep Space this should be
											// 1914 (Floor is 1798)

	EvoMeiosis evoMeiosis;

	@Override
	public void settings() {
		size(windowWidth, windowHeight);
	}

	@Override
	public void setup() {
		noStroke();
		evoMeiosis = new EvoMeiosis(this, wallHeight);
	}

	@Override
	public void draw() {
		background(10);

		// set upper half of window (=wall projection) bluish
		fill(70, 100, 150);
		rect(0, 0, windowWidth, wallHeight);

		// set lower half of window (=floor projection) reddish
		fill(150, 100, 50);
		rect(0, wallHeight, windowWidth, windowHeight);

		evoMeiosis.update();
		render(evoMeiosis);
	}

	private void render(EvoMeiosis evo) {
		fill(10);
		for (PVector p : evo.getPlayers()) {
			ellipse(p.x, p.y, 25, 25);
		}
	}

	public static void main(String[] args) {
		PApplet.main("evoMeiosis.DeepSpaceProcessingSketch");
	}

}
