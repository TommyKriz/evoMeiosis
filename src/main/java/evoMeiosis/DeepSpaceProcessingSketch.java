package evoMeiosis;

import processing.core.PApplet;
import processing.core.PVector;

public class DeepSpaceProcessingSketch extends PApplet {

	private static final int DEEP_SPACE_WIDTH = 3030;

	private static final int DEEP_SPACE_WALL_HEIGHT = 1914;

	private static final int DEEP_SPACE_HEIGHT = 3712;

	private static final int scaleFactor = 4;

	int windowWidth = DEEP_SPACE_WIDTH / scaleFactor;
	int windowHeight = DEEP_SPACE_HEIGHT / scaleFactor;
	int wallHeight = DEEP_SPACE_WALL_HEIGHT / scaleFactor;

	EvoMeiosis evoMeiosis;

	@Override
	public void settings() {
		size(windowWidth, windowHeight);
	}

	@Override
	public void setup() {
		stroke(255);
		evoMeiosis = new EvoMeiosis(this, wallHeight);
		// frameRate(30);
	}

	@Override
	public void draw() {
		float alpha = 30;
		// background(10, alpha);

		// set upper half of window (=wall projection) bluish
		fill(70, 100, 150, alpha);
		rect(0, 0, windowWidth, wallHeight);

		// set lower half of window (=floor projection) reddish
		fill(150, 100, 50, alpha);
		rect(0, wallHeight, windowWidth, windowHeight);

		evoMeiosis.update();
	}

	public static void main(String[] args) {
		PApplet.main("evoMeiosis.DeepSpaceProcessingSketch");
	}

}
