package evoMeiosis.logic;

import org.apache.commons.lang3.RandomUtils;
import org.apache.commons.math3.util.FastMath;

public class FADtriple {
	float frequency;
	float amplitude;
	float dampingCoefficient;
	float phase1;
	float phase2;

	FADtriple(float ampScale, float freqScale) {
		frequency = freqScale * RandomUtils.nextFloat(fMin, fmax);
		amplitude = ampScale * RandomUtils.nextFloat(ampMin, ampMax);
		dampingCoefficient = RandomUtils.nextFloat(0.1f, 0.5f);
		phase1 = RandomUtils.nextFloat(-1 - 0f, 1.0f);
		phase2 = RandomUtils.nextFloat(-1.0f, 1.0f);
	}

	void setFAD(float f, float a, float d) {
		frequency = f;
		amplitude = a;
		dampingCoefficient = d;
	}

	private double getXOffset(float i) {
		return amplitude * FastMath.cos(i * frequency + phase1); // * (float)
		// ((millis()%1000)/1000)
		// *
		// t.dampingCoefficient;
	}

	private float getYOffset(float i) {
		return amplitude * sin(i * frequency + phase2); // * (float)
														// ((millis()%1000)/1000)
														// *
														// t.dampingCoefficient;
	}
}