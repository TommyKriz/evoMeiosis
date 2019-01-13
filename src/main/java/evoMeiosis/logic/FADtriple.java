package evoMeiosis.logic;

import org.apache.commons.lang3.RandomUtils;
import org.apache.commons.math3.util.FastMath;

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
		phase1 = RandomUtils.nextFloat(0f, 1.0f) * 2 - 1;
		phase2 = RandomUtils.nextFloat(0f, 1.0f) * 2 - 1;
	}

	public void setFAD(float f, float a, float d) {
		frequency = f;
		amplitude = a;
		dampingCoefficient = d;
	}

   public double getXOffset(double i) {
	   double offset = amplitude * Math.cos(i * frequency + phase1); 
	   return offset;// * (float)
	}
	
	 public double getYOffset(double i) {
		 double offset = amplitude * Math.cos(i * frequency + phase2); 
		   return offset;// * (float)

	 }
}