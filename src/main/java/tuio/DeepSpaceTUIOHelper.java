package tuio;

import java.util.ArrayList;

import processing.core.PApplet;
import processing.core.PVector;
import TUIO.TuioCursor;
import TUIO.TuioProcessing;

public class DeepSpaceTUIOHelper {

	private TuioProcessing tuioClient;

	private int _wallHeight = 0;
	private int parentWidth = 0;
	private int parentHeight = 0;
	private static final int INVALID = -1;

	private ArrayList<PVector> players = new ArrayList<>();

	public DeepSpaceTUIOHelper(PApplet parent, int wallHeight) {
		_wallHeight = wallHeight;
		parentWidth = parent.width;
		parentHeight = parent.height;
		tuioClient = new TuioProcessing(parent);
	}

	private int GetNumTracks() {
		return tuioClient.getTuioCursorList().size();
	}

	private int GetX(int trackID) {
		if (trackID >= tuioClient.getTuioCursorList().size()) {
			return INVALID;
		}
		TuioCursor tc = tuioClient.getTuioCursorList().get(trackID);
		return tc.getScreenX(parentWidth);
	}

	private int GetY(int trackID) {
		if (trackID >= tuioClient.getTuioCursorList().size()) {
			return INVALID;
		}
		TuioCursor tc = tuioClient.getTuioCursorList().get(trackID);
		return tc.getScreenY(parentHeight - _wallHeight) + _wallHeight;
	}

	public ArrayList<PVector> getPlayers() {
		players.clear();
		for (int trackID = 0; trackID < GetNumTracks(); trackID++) {
			players.add(new PVector(GetX(trackID), GetY(trackID)));
		}
		return players;
	}
}
