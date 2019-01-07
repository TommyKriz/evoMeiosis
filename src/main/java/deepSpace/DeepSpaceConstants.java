package deepSpace;

public class DeepSpaceConstants {

	// original deep space coordinates

	private static final int DEEP_SPACE_WIDTH = 3030;

	private static final int DEEP_SPACE_WALL_HEIGHT = 1914;

	private static final int DEEP_SPACE_HEIGHT = 3712;

	// adjust for testing on other screens

	private static final int SCALE_FACTOR = 4;

	public static final int WINDOW_WIDTH = DEEP_SPACE_WIDTH / SCALE_FACTOR;

	public static final int WINDOW_HEIGHT = DEEP_SPACE_HEIGHT / SCALE_FACTOR;

	// deep space screen is divided in floor and wall

	public static final int WALL_HEIGHT = DEEP_SPACE_WALL_HEIGHT / SCALE_FACTOR;

	public static final int FLOOR_HEIGHT = WINDOW_HEIGHT - WALL_HEIGHT;
}
