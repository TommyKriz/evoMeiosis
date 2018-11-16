package evoMeiosis;

import processing.core.PApplet;

public class MainProcessingSketch extends PApplet {

	public static void main(String[] args) {
		PApplet.main("evoMeiosis.MainProcessingSketch");
	}

	public void settings() {
		size(800, 800);
	}

	public void setup() {
		colorMode(HSB, 255, 100, 100);
		fill(66, 100, 70);
	}

	public void draw() {
		ellipse(width / 2, height / 2, second(), second());
	}

}
