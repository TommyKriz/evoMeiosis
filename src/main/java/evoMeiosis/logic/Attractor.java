package evoMeiosis.logic;

public class Attractor {
	public String type = "point";
	public float intensity = 0.1f;
	public int radius;
	public float xOrig;
	public float yOrig;

	protected Attractor(float x, float y, int radius) {
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