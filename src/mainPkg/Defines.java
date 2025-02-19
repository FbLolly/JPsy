package mainPkg;

import java.awt.Color;
import java.awt.FontMetrics;
import java.awt.Image;
import java.util.LinkedList;

import javax.swing.JComponent;

public class Defines {
	public static final int IFW = JComponent.WHEN_IN_FOCUSED_WINDOW;

	public static int FPS = 60;
	public static int waiting = FPS;
	public static int timer = 0;

	public static final float fontSize = 20f;

	public static final int width = 1600;
	public static final int height = 900;

	public static final int buttonAnimationSpeed = 7;

	public static final double defaultSpeed = 3;
	public static final double runningSpeed = 5;

	public static final double tileSize = Defines.width/50;
	public static final double invTileSize = Defines.width/20;
	public static final double invItemSize = Defines.width/25;
	public static final double selectedInvSize = Defines.width/18;

	public static final int lighterDurability = 20;

	public static int mapSizeX = 50;
	public static int mapSizeY = 50;

	public static int cameraX = 0;
	public static int cameraY = 0;

	public static FontMetrics fontMetrics = null;

	public static final Color bgc = new Color(37, 19, 26);
	public static final int floors = 4;

	public static final boolean[] collidable = {false, true, true, true, true, true, true, true, true};

	public static final int inventorySize = 4;

	public static Image getCurrentAnimationImage(LinkedList<Image> imgs){
		if (imgs == null) return null;
		if (imgs.size() == 0) return null;

		double frameOutOfOne = (double)Defines.timer / (double)Defines.FPS;
		double changeFrameOutOfOne = ((double)Defines.FPS/(double)imgs.size())/(double)Defines.FPS;

		int idx = (int)(frameOutOfOne/changeFrameOutOfOne);
		if (idx >= imgs.size())
			idx--;

		if (idx < 0)
			return null;

		return imgs.get(idx);
	}
}
