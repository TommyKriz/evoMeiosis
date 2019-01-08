package evoMeiosis.audio;

import processing.core.PApplet;
import processing.sound.SoundFile;

public class Audio {

	SoundFile treeCreation;
	SoundFile agentSpawn;
	SoundFile BGphase1;
	SoundFile BGphase2;
	SoundFile BGphase3;

	void setup(PApplet parent) {
		/**
		 * These files must be placed in the audio folder !
		 */
		treeCreation = new SoundFile(parent, "treecreation_sound.wav");
		agentSpawn = new SoundFile(parent, "agentspawn.wav");
		BGphase1 = new SoundFile(parent, "BGStage1.aiff");
		BGphase2 = new SoundFile(parent, "BGStage2_96k.wav");
		BGphase3 = new SoundFile(parent, "BGStage3_96k.wav");

	}

	void newTreeCreated() {
		treeCreation.play();
		// play creation sound -> internet
		// starts when tree is being created until it is finished -> rising
		// intensity
		// maybe change sound depending on which agents merged -> get involved
		// agents -> filter
	}

	void agentsSpawning() {
		agentSpawn.play();
		// play spawn sound -> internet
		// maybe change sound depending on which agents spawn -> get involved
		// agents -> filter
	}

	void atmosphericSound() {
		// fade agent-bound frequencies in and out -> get list of existing
		// agents -> get frequencies
		// maybe go slowly through all frequencies from high to low and
		// easy-ease in and -out of one over the next to the third
		// -> loop through all frequencies and play slowly
		// at <= 10 agents only 3 frequencies, at 10<x<=20 6 frequencies and so
		// on, to get more intensity
	}

	void backgroundJingle(int phase) {
		if (phase == 1) {
			BGphase1.loop();
			return;
		}
		if (phase == 2) {
			while (BGphase1.percent() != 100) {
				// method needs coroutine so the whole program doesn't get stuck
				// in while
			}
			BGphase1.stop();
			BGphase2.loop();
		}
		if (phase == 3) {
			while (BGphase2.percent() != 100) {
				// method needs coroutine so the whole program doesn't get stuck
				// in while
			}
			BGphase2.stop();
			BGphase3.loop();
		}
	}

}
