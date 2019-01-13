package evoMeiosis.logic;

public class Attractor {
	public String type = "point";
	public float intensity = 0.1f;
	public int radius;
	public int xOrig;
	public int yOrig;

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