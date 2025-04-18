package mainPkg;

import java.awt.Color;
import java.awt.FontMetrics;
import java.awt.Image;
import java.util.LinkedList;

import javax.swing.JComponent;

import itemPkg.Candle;
import itemPkg.Item;
import itemPkg.Lighters;

public class Defines {
	public static final int IFW = JComponent.WHEN_IN_FOCUSED_WINDOW;

	public static final String key = "KEYKEYKEY_VIGENEREKEY";

	public static int FPS = 60;
	public static int waiting = FPS;

	public static int timer = 0;
	public static int animationTimer = 0;
	public static final int maxSlow = 5;

	public static final float fontSize = 12f;

	public static final int width = 1600;
	public static final int height = 900;

	public static final int buttonAnimationSpeed = 7;

	public static final double defaultSpeed = 3;
	public static final double runningSpeed = 7;

	public static final int tileSize = Defines.width/30;
	public static final int invTileSize = Defines.width/45;
	public static final int invItemSize = Defines.width/50;
	public static final int selectedInvSize = Defines.width/38;

	public static final int lighterDurability = 20;

	public static int mapSizeX = 50;
	public static int mapSizeY = 50;

	public static int cameraX = 0;
	public static int cameraY = 0;

	public static FontMetrics fontMetrics = null;

	public static final Color bgc = new Color(37, 19, 26);
	public static final int floors = 4;

	public static double zoom = 2;
	public static boolean scale = true;

	public static final boolean[] collidable = {false, true, true, true, true, true, true, true, true};

	public static final int standardDeathTimer = 60;

	public static final int inventorySize = 4;

	public static boolean lockedDialogue = false;
	public static boolean timeStop = false;

	public static int getNonScaledX(double mult){
		return (int)(-Defines.width / 3.0 * (Defines.zoom-1.0) * mult);
	}
	public static int getNonScaledY(double mult){
		return (int)(-Defines.height / 3.0 * (Defines.zoom-1.0) * mult);
	}

	public static Image getCurrentAnimationImage(LinkedList<Image> imgs){
		if (imgs == null) return null;
		if (imgs.size() == 0) return null;

		double frameOutOfOne = (double)Defines.animationTimer / (double)(Defines.FPS * Defines.maxSlow);
		double changeFrameOutOfOne = ((double)(Defines.FPS * Defines.maxSlow)/(double)imgs.size())/(double)(Defines.FPS * Defines.maxSlow);

		int idx = (int)(frameOutOfOne/changeFrameOutOfOne);
		if (idx >= imgs.size())
			idx--;

		if (idx < 0)
			return null;

		return imgs.get(idx);
	}

	public static Item getItem(String string){
		Item i = null;
		
		switch (string){
			case "Item@Candle":
				i = new Candle();
				break;
			case "Item@Lighters":
				i = new Lighters();
				break;
		}

		return i;
	}
}
