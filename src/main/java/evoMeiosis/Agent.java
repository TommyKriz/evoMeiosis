package evoMeiosis;

import processing.core.PApplet;
import processing.core.PGraphics;
import processing.core.PVector;
import tuio.DeepSpaceTUIOHelper;

import ddf.minim.*;
import ddf.minim.analysis.*;
import ddf.minim.effects.*;
import ddf.minim.signals.*;
import ddf.minim.spi.*;
import ddf.minim.ugens.*;

// need processing sound lib
import processing.sound.*;

public class Agent {

	Amplitude amp;
	processing.sound.FFT fft;
	AudioIn in;
	float xPos;
	float yPos;
	float frequency;
	float amplitude;
	float smoothing;

	void setup() {

		canvas.size(3840, 1440);
		background(0);

		xPos = 0;
		yPos = 0;

		amp = new Amplitude(this);

		// Create an Input stream which is routed into the Amplitude analyzer
		fft = new processing.sound.FFT(this, bands);

		in = new AudioIn(this, 0);

		// patch Audio to amplitude
		amp.input(in);

		// patch Audio to FFT

		fft.input(in);

		smoothing = 0.92;
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

	void draw() {
		canvas.background(255);
		float[] spectrumAnalyzed = fft.analyze();

		noStroke();
		setPosition(1, 2);
		translate(xPos, yPos); // translate(width / 3, height / 2);
		beginShape();
		for (int i = 10; i < spectrumAnalyzed.length; i++) {

			fftSmooth[i] *= smoothing;
			if (fftSmooth[i] < spectrumAnalyzed[i])
				fftSmooth[i] = spectrumAnalyzed[i];

			// seeds werden je nach frequenz gezeichnet
			float currentAmp = fftSmooth[i];
			float angle = map(i, 0, spectrumAnalyzed.length, 0, 180);
			float r = map(currentAmp * 1000, 0, 256, 120, 240);
			fill(i, 255, 0);
			// strokeWeight(0.02 * i);
			float x = r * cos(angle);
			float y = r * sin(angle);

			vertex(x, y);
			// ellipse(100, 100, ampt, ampt);
			// The result of the FFT is normalized
			// draw the line for frequency band i scaling it up by 5 to get more amplitude.
			// line( i*10, height, i*10, height - spectrum[i]*height*30);

		}
		endShape();
	}
}
