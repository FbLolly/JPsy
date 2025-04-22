package utilsPkg;

import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;

import GameObjectPkg.GameObject;
import mainPkg.Defines;

public class Camera {
    private double x, y;
    private int timer, time;
    private double intensity;
    private int change;

    public Camera(double x, double y) {
        this.x = x;
        this.y = y;
        this.timer = 0;
        this.change = 1;
    }

    public void shake(double intensity, int time) { //time is in frames
        if (this.timer == 0) {
            this.time = time;
            this.intensity = intensity;
        }

        if (((double) this.timer) / ((double) time) >= 1) {
            Defines.lockedDialogue = false;
            this.timer = 0;
            this.time = 0;
            this.intensity = 0;

            return;
        }

        this.timer++;
        Defines.lockedDialogue = true;

        if (this.timer % (int) (this.time / (10 * intensity)) == 0) {
            this.x += intensity * 100 * change;
            change *= -1;
        }
    }

    public void update(GameObject player) {
        if (this.timer != 0) {
            shake(intensity, time);
            return;
        }

        RayRectangle p = player.getRect();

        this.x = -((double) p.x) + Defines.width / Defines.zoom / 2.0 + p.width / 2.0;
        this.y = -((double) p.y) + Defines.height / Defines.zoom / 2.0 + p.width / 2.0;
    }

    public void paintImage(Image image, int x, int y, Graphics g) {
        Graphics2D g2 = (Graphics2D) g;

        g2.drawImage(image, x, y, null);
    }

    public void translate(Graphics2D g) {
        g.translate(this.x, this.y);

        Defines.cameraX = (int) this.x;
        Defines.cameraY = (int) this.y;
    }

    public void untranslate(Graphics2D g) {
        g.translate(-this.x, -this.y);

        Defines.cameraX = (int) this.x;
        Defines.cameraY = (int) this.y;
    }
}
