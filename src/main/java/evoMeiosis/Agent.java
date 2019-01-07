package evoMeiosis;

import processing.core.PApplet;
import processing.core.PGraphics;
// need processing sound lib
import processing.sound.Amplitude;
import processing.sound.AudioIn;
import processing.sound.SoundFile;

public class Agent {

	Amplitude amp;
	processing.sound.FFT fft;
	AudioIn in;
	float xPos;
	float yPos;
	float frequency;
	float amplitude;

	float smoothing = 0;
	float[] fftSmooth;

	SoundFile file;
	int bands = 128;

	void setup(PApplet parent) {

		parent.size(3840, 1440);
		parent.background(0);

		xPos = 0;
		yPos = 0;

		amp = new Amplitude(parent);

		// Create an Input stream which is routed into the Amplitude analyzer
		fft = new processing.sound.FFT(parent, bands);

		// AudioIn should be the frequency of the specific agent
		in = new AudioIn(parent, 0);

		// patch Audio to amplitude
		amp.input(in);

		// patch Audio to FFT

		fft.input(in);

		smoothing = 0.92f;
		fftSmooth = new float[bands];
	}

	void setPosition(float agentPosX, float agentPosY) {
		// xPos = agentPosX;
		// yPos = agentPosY;

		if (xPos >= 3880)
			xPos = 0;
		xPos += 5;
		if (yPos >= 1440)
			yPos = 0;
		yPos++;
	}

	void getFrequency(float agentFrequency) {
		frequency = agentFrequency;
	}

	void setAmplitude(float agentAmplitude) {
		amplitude = agentAmplitude;
	}

	void draw(PGraphics canvas) {
		canvas.beginDraw();
		canvas.background(255);
		float[] spectrumAnalyzed = fft.analyze();

		canvas.noStroke();
		setPosition(1000, 500);
		canvas.beginShape();
		for (int i = 10; i < spectrumAnalyzed.length; i++) {

			fftSmooth[i] *= smoothing;
			if (fftSmooth[i] < spectrumAnalyzed[i])
				fftSmooth[i] = spectrumAnalyzed[i];

			// seeds werden je nach frequenz gezeichnet
			float currentAmp = fftSmooth[i];
			float angle = i * 180 / spectrumAnalyzed.length;
			float r = 120 + (currentAmp * 1000) * (240 - 120) / (256);
			canvas.fill(i, 255, 0);
			// strokeWeight(0.02 * i);
			float x = (float) (r * java.lang.Math.cos(angle));
			float y = (float) (r * java.lang.Math.sin(angle));

			canvas.vertex(x, y);
			// ellipse(100, 100, ampt, ampt);
			// The result of the FFT is normalized
			// draw the line for frequency band i scaling it up by 5 to get more
			// amplitude.
			// line( i*10, height, i*10, height - spectrum[i]*height*30);

		}
		canvas.endShape();
	}
}
