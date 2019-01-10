package evoMeiosis.logic;

public class Attractor {
	String type = "point";
	float intensity = 0.1f;
	int radius;
	int xOrig;
	int yOrig;

	protected Attractor(int x, int y, int radius) {
		this.xOrig = x;
		this.yOrig = y;
		this.radius = radius;
	}

	float getXAttraction(float x) {
		return (xOrig - x) * intensity;
	}

	float getYAttraction(float y) {
		return (yOrig - y) * intensity;
	}

}