package evoMeiosis;

public class Audio {
	
	void newTreeCreated() {
		// play creation sound	-> internet
		// starts when tree is being created until it is finished -> rising intensity
		// maybe change sound depending on which agents merged	-> get involved agents	-> filter
	}
	
	void agentsSpawning() {
		// play spawn sound	-> internet
		// maybe change sound depending on which agents spawn	-> get involved agents	-> filter
	}
	
	void atmosphericSound() {
		// fade agent-bound frequencies in and out	-> get list of existing agents -> get frequencies
		// maybe go slowly through all frequencies from high to low and easy-ease in and -out of one over the next to the third
		// -> loop through all frequencies and play slowly
		// at <= 10 agents only 3 frequencies, at 10<x<=20 6 frequencies and so on, to get more intensity
	}
	
	void backgroundJingle() {
		// play repeatable background sound	-> internet
	}
	
}
