package EffectPkg;

import java.awt.Color;
import java.awt.Graphics;

import mainPkg.Defines;
import mainPkg.Game;
import menusPkg.JumpScare;

public class DeathTimer {
    public int timer;
    public int time;
    public boolean lit;
    private Eyes eyes;

    public DeathTimer(int time) {
        this.time = time;
        this.timer = this.time;
    }

    public void update(Game game) {
        if (JumpScare.what != null)
            return;

        this.lit = false;
        if (game.map.lit) {
            this.lit = true;
            return;
        }

        if (Defines.timer > Defines.FPS && (!Defines.lockedDialogue))
            this.timer--;

        if (this.timer <= 0) {
            this.lit = true;

            Defines.animationTimer = 1;
            JumpScare.jumpscare("man-jumpscare");
            return;
        }

        if (this.timer < this.time * (double) (2.0 / 3.0) && this.eyes == null) {
            this.eyes = new Eyes();
        }

        if (this.eyes == null)
            return;
        this.eyes.update(game.player, this);
    }

    public void paintComponent(Graphics g, Game game) {
        if (this.lit)
            return;

        if (this.eyes != null)
            this.eyes.paintComponent(g, game.imageList);

        g.setColor(Color.red);
        g.drawString("" + this.timer,
                (int) game.player.getX() + Defines.tileSize / 2 - (int) (Defines.fontMetrics.stringWidth("" + this.timer)) / 2,
                (int) game.player.getY() - Defines.tileSize);

        if (this.eyes != null)
            this.eyes.paintStatic(g, game);
    }
}
