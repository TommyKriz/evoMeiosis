package evoMeiosis.audio;

import processing.core.PApplet;
import processing.sound.SoundFile;

public class AudioTest {

	SoundFile sound;

	public AudioTest(PApplet parent) {
		sound = new SoundFile(parent, "LOOP.mp3");

	}

	public void playSound() {
		sound.play();
	}

}
