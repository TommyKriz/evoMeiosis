package demos;

import java.util.ArrayList;

import processing.core.PApplet;
import TUIO.TuioBlob;
import TUIO.TuioCursor;
import TUIO.TuioObject;
import TUIO.TuioPoint;
import TUIO.TuioProcessing;
import TUIO.TuioTime;

public class TUIOHelper {

	PApplet parent;

	public TUIOHelper(PApplet parent) {
		this.parent = parent;
	}

	// these is just some helper code to make your code more readable
	// for enhanced usage: http://www.tuio.org/?java

	TuioProcessing tuioClient;
	boolean _DoDebug = false;
	int _wallHeight = 0;
	final int INVALID = -1;

	void initTracking(boolean doDebug, int wallHeight) {
		_DoDebug = doDebug;
		_wallHeight = wallHeight;
		tuioClient = new TuioProcessing(parent);
	}

	int GetNumTracks() {
		return tuioClient.getTuioCursorList().size();
	}

	int GetNumPathPoints(int trackID) {
		if (trackID >= tuioClient.getTuioCursorList().size()) {
			return 0;
		}
		TuioCursor tc = tuioClient.getTuioCursorList().get(trackID);
		return tc.getPath().size();
	}

	int GetPathPointX(int trackID, int pointID) {
		if (trackID >= tuioClient.getTuioCursorList().size()) {
			return INVALID;
		}
		TuioCursor tc = tuioClient.getTuioCursorList().get(trackID);
		ArrayList pointList = tc.getPath();
		if (pointList == null || pointList.size() <= pointID) {
			return INVALID;
		}

		TuioPoint tp = (TuioPoint) pointList.get(pointID);
		return tp.getScreenX(parent.width);
	}

	int GetPathPointY(int trackID, int pointID) {
		if (trackID >= tuioClient.getTuioCursorList().size()) {
			return INVALID;
		}
		TuioCursor tc = tuioClient.getTuioCursorList().get(trackID);
		ArrayList pointList = tc.getPath();
		if (pointList == null || pointList.size() <= pointID) {
			return INVALID;
		}

		TuioPoint tp = (TuioPoint) pointList.get(pointID);
		return tp.getScreenY(parent.height - _wallHeight) + _wallHeight;
	}

	int GetX(int trackID) {
		if (trackID >= tuioClient.getTuioCursorList().size()) {
			return INVALID;
		}
		TuioCursor tc = tuioClient.getTuioCursorList().get(trackID);
		return tc.getScreenX(parent.width);
	}

	int GetY(int trackID) {
		if (trackID >= tuioClient.getTuioCursorList().size()) {
			return INVALID;
		}
		TuioCursor tc = tuioClient.getTuioCursorList().get(trackID);
		return tc.getScreenY(parent.height - _wallHeight) + _wallHeight;
	}

	int GetCursorID(int trackID) {
		if (trackID >= tuioClient.getTuioCursorList().size()) {
			return INVALID;
		}
		TuioCursor tc = tuioClient.getTuioCursorList().get(trackID);
		return tc.getCursorID();
	}

	int GetNumFeet(int trackID) {
		int nFeet = 0;
		ArrayList tuioObjectList = tuioClient.getTuioObjectList();
		for (int i = 0; i < tuioObjectList.size(); i++) {
			TuioObject tobj = (TuioObject) tuioObjectList.get(i);
			if (tobj.getSymbolID() == trackID) {
				nFeet++;
			}
		}
		return nFeet;
	}

	int GetFootX(int trackID, int footID) {
		int nFootCnt = 0;
		ArrayList tuioObjectList = tuioClient.getTuioObjectList();
		for (int i = 0; i < tuioObjectList.size(); i++) {
			TuioObject tobj = (TuioObject) tuioObjectList.get(i);
			if (tobj.getSymbolID() == trackID) {
				if (nFootCnt == footID) {
					return tobj.getScreenX(parent.width);
				}
				nFootCnt++;
			}
		}
		return INVALID;
	}

	int GetFootY(int trackID, int footID) {
		int nFootCnt = 0;
		ArrayList tuioObjectList = tuioClient.getTuioObjectList();
		for (int i = 0; i < tuioObjectList.size(); i++) {
			TuioObject tobj = (TuioObject) tuioObjectList.get(i);
			if (tobj.getSymbolID() == trackID) {
				if (nFootCnt == footID) {
					return tobj.getScreenY(parent.height - _wallHeight)
							+ _wallHeight;
				}
				nFootCnt++;
			}
		}
		return INVALID;
	}

	// these callback methods are called whenever a TUIO event occurs

	// called when an object is added to the scene
	void addTuioObject(TuioObject tobj) {
		if (_DoDebug)
			System.out.println("add object " + tobj.getSymbolID() + " ("
					+ tobj.getSessionID() + ") " + tobj.getX() + " "
					+ tobj.getY() + " " + tobj.getAngle());
	}

	// called when an object is removed from the scene
	void removeTuioObject(TuioObject tobj) {
		if (_DoDebug)
			System.out.println("remove object " + tobj.getSymbolID() + " ("
					+ tobj.getSessionID() + ")");
	}

	// called when an object is moved
	void updateTuioObject(TuioObject tobj) {
		if (_DoDebug)
			System.out.println("update object " + tobj.getSymbolID() + " ("
					+ tobj.getSessionID() + ") " + tobj.getX() + " "
					+ tobj.getY() + " " + tobj.getAngle() + " "
					+ tobj.getMotionSpeed() + " " + tobj.getRotationSpeed()
					+ " " + tobj.getMotionAccel() + " "
					+ tobj.getRotationAccel());
	}

	// called when a cursor is added to the scene
	void addTuioCursor(TuioCursor tcur) {
		if (_DoDebug)
			System.out.println("add cursor " + tcur.getCursorID() + " ("
					+ tcur.getSessionID() + ") " + tcur.getX() + " "
					+ tcur.getY());
	}

	// called when a cursor is moved
	void updateTuioCursor(TuioCursor tcur) {
		if (_DoDebug)
			System.out.println("update cursor " + tcur.getCursorID() + " ("
					+ tcur.getSessionID() + ") " + tcur.getX() + " "
					+ tcur.getY() + " " + tcur.getMotionSpeed() + " "
					+ tcur.getMotionAccel());
	}

	// called when a cursor is removed from the scene
	void removeTuioCursor(TuioCursor tcur) {
		if (_DoDebug)
			System.out.println("remove cursor " + tcur.getCursorID() + " ("
					+ tcur.getSessionID() + ")");
	}

	// called after each message bundle
	// representing the end of an image frame
	void refresh(TuioTime bundleTime) {
		// TODO:should really use?
		parent.redraw();
	}

	void addTuioBlob(TuioBlob tblb) {
		if (_DoDebug)
			System.out.println("add blob " + tblb.getBlobID() + " ("
					+ tblb.getSessionID() + ") " + tblb.getX() + " "
					+ tblb.getY());
	}

	void removeTuioBlob(TuioBlob tblb) {
		if (_DoDebug)
			System.out.println("remove blob " + tblb.getBlobID() + " ("
					+ tblb.getSessionID() + ") " + tblb.getX() + " "
					+ tblb.getY());
	}

	void updateTuioBlob(TuioBlob tblb) {
		if (_DoDebug)
			parent.println("update blob " + tblb.getBlobID() + " ("
					+ tblb.getSessionID() + ") " + tblb.getX() + " "
					+ tblb.getY());
	}
}
