package EffectPkg;

import java.awt.Graphics;
import java.awt.Point;
import java.util.ArrayList;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

import GameObjectPkg.playerPkg.Player;
import graphicsPkg.ImageList;
import mainPkg.Defines;
import mainPkg.Game;

public class Eyes {
    public ArrayList<Point> randoms;
    private Point playerPos;
    private boolean start;
    private boolean noise;
    private Lock lock;

    private int counter;

    public Eyes() {
        randoms = new ArrayList<>();
        start = false;
        noise = false;
        counter = 0;
        lock = new ReentrantLock();

        randoms.add(new Point(((int) (Math.random() * (Defines.width / 2)) - Defines.width / 4),
                ((int) (Math.random() * (Defines.height / 2)) - Defines.height / 4)));
    }

    public void update(Player player, DeathTimer dt) {
        lock.lock();

        this.playerPos = new Point((int) player.getX(), (int) player.getY());

        if (dt.lit) {
            this.randoms = new ArrayList<>();
            return;
        }

        if (dt.timer > 20) {
            this.start = false;
            return;
        }
        this.start = true;

        if (Defines.animationTimer == Defines.FPS * Defines.maxSlow) {
            int size = this.randoms.size();
            this.randoms = new ArrayList<>();

            for (int i = 0; i < size; i++) {
                randoms.add(new Point(((int) (Math.random() * (Defines.width / 2)) - Defines.width / 4),
                        ((int) (Math.random() * (Defines.height / 2)) - Defines.height / 4)));
            }

            if (counter == 4) {
                counter = 0;

                randoms.add(new Point(((int) (Math.random() * (Defines.width / 2)) - Defines.width / 4),
                        ((int) (Math.random() * (Defines.height / 2)) - Defines.height / 4)));
            }

            counter++;
        } else if (Defines.timer == Defines.FPS / 4) {
            if (counter == 3) {
                randoms.add(new Point(((int) (Math.random() * (Defines.width / 2)) - Defines.width / 4),
                        ((int) (Math.random() * (Defines.height / 2)) - Defines.height / 4)));
            }
        }

        if (dt.timer > 15 || dt.lit) {
            noise = false;
            return;
        }
        this.noise = true;

        lock.unlock();
    }

    public void paintComponent(Graphics g, ImageList imageList) {
        if (!this.start)
            return;

        for (Point p : this.randoms) {
            Point pos = new Point(p.x + playerPos.x, p.y + playerPos.y);

            g.drawImage(Defines.getCurrentAnimationImage(imageList.getImages("eyes", (int) Defines.tileSize * 2, (int) Defines.tileSize * 2)),
                    pos.x, pos.y, null);
        }
    }

    public void paintStatic(Graphics g, Game game) {
        if (!this.noise)
            return;
        if (!this.start)
            return;

        g.drawImage(Defines.getCurrentAnimationImage(game.imageList.getImages("noise", (int) (Defines.width / Defines.zoom), (int) (Defines.height / Defines.zoom))),
                (int) (game.player.getX() - Defines.width / 2 / Defines.zoom),
                (int) (game.player.getY() - Defines.height / 2 / Defines.zoom), null);
    }
}
