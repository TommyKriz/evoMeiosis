package evoMeiosis.logic;

import org.apache.commons.lang3.RandomUtils;

import evoMeiosis.EvoMeiosisConstants;

public class FADtriple {
	public float frequency;
	public float amplitude;
	public float dampingCoefficient;
	float phase1;
	float phase2;

	public FADtriple(float ampScale, float freqScale) {
		frequency = freqScale
				* RandomUtils.nextFloat(EvoMeiosisConstants.fMin,
						EvoMeiosisConstants.fmax);
		amplitude = ampScale
				* RandomUtils.nextFloat(EvoMeiosisConstants.ampMin,
						EvoMeiosisConstants.ampMax);
		dampingCoefficient = RandomUtils.nextFloat(0.1f, 0.5f);
		phase1 = RandomUtils.nextFloat(-1 - 0f, 1.0f);
		phase2 = RandomUtils.nextFloat(-1.0f, 1.0f);
	}

	public void setFAD(float f, float a, float d) {
		frequency = f;
		amplitude = a;
		dampingCoefficient = d;
	}

	// private double getXOffset(float i) {
	// return amplitude * FastMath.cos(i * frequency + phase1); // * (float)
	// // ((millis()%1000)/1000)
	// // *
	// // t.dampingCoefficient;
	// }
	//
	// private double getYOffset(float i) {
	// return amplitude * Math.sin(i * frequency + phase2); // * (float)
	// // ((millis()%1000)/1000)
	// // *
	// // t.dampingCoefficient;
	// }
}