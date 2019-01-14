package evoMeiosis.audio;

import processing.core.PApplet;
import processing.sound.SoundFile;

public class Audio {

	SoundFile treeCreation;
	SoundFile agentSpawn;
	SoundFile BGphase1;
	SoundFile BGphase2;
	SoundFile BGphase3;
	int phase;
	
	boolean BGsoundSkippable;
	boolean skippable;

	public void setup(PApplet parent) {
		treeCreation = new SoundFile(parent, "treecreation_sound.wav");
		agentSpawn = new SoundFile(parent, "agentspawn.wav");
		BGphase1 = new SoundFile(parent, "BGStage1.aiff");
		BGphase2 = new SoundFile(parent, "BGStage2_96k.wav");
		BGphase3 = new SoundFile(parent, "BGStage3_96k.wav");
		phase = 1;
		BGsoundSkippable = false;
		BGphase1.loop();
		skippable = false;
	}

	public void newTreeCreated() {
		treeCreation.amp((float) 0.1);
		treeCreation.play();	// called as new tree creation is started in TreeSystem
		
		// play creation sound -> internet
		// starts when tree is being created until it is finished -> rising
		// intensity
		// maybe change sound depending on which agents merged -> get involved
		// agents -> filter
	}

	public void agentsSpawning(int freeSeeds) {
		agentSpawn.amp(0.5f);
		if(!agentSpawn.isPlaying()) {
			agentSpawn.play();
		}
			// called as seeds spawn in SeedSystem
		if(phase == 1 && freeSeeds >= 15) {
			phase = 2;
			skippable = true;
		} else if(phase == 0 && freeSeeds >= 25) {
			phase = 3;
			skippable = true;
		}
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

	public void soundUpdate() {
		
		if(!skippable) {
			return;
		}
		checkBGcompleteness();
		if(phase == 2 && BGsoundSkippable) {
			BGphase1.stop();
			BGphase2.loop();
			BGsoundSkippable = false;
			phase = 0;
			skippable = false;
		} else if(phase == 3 && BGsoundSkippable) {
			BGphase2.stop();
			BGphase3.loop();
			BGsoundSkippable = false;
			skippable = false;
		}
	}
	
	private void checkBGcompleteness() {
		if (phase == 2 && BGphase1.percent() >= 99.5) {
				BGsoundSkippable = true;
		}
		if (phase == 3 && BGphase2.percent() >= 99.5) {
			BGsoundSkippable = true;
		}
	}

}
