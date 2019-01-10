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

	public float getXAttraction(float x) {
		return (xOrig - x) * intensity;
	}

	public float getYAttraction(float y) {
		return (yOrig - y) * intensity;
	}

}