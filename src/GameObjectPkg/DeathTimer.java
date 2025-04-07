package GameObjectPkg;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;

import EffectPkg.Eyes;
import gameManagement.Map;
import graphicsPkg.ImageList;
import mainPkg.Defines;
import utilsPkg.Camera;

public class DeathTimer {
    public int timer;
    public int time;
    public boolean lit;
    private Eyes eyes;

    public DeathTimer(int time){
        this.time = time;
        this.timer = this.time;
    }

    public void update(Map map, Player player){
        this.lit = false;
        if (map.lit){
            this.lit = true;
            return;
        }
        
        if (Defines.timer > Defines.FPS && (!Defines.lockedDialogue))
            this.timer--;
        
        if (this.timer <= 0){
            this.lit = true;

            //kill the player
        }

        if (this.timer < this.time*(double)(2.0/3.0) && this.eyes == null){
            this.eyes = new Eyes();
        }

        if (this.eyes == null)
            return;
        this.eyes.update(player, this);
    }

    public void paintComponent(Graphics g, ImageList imageList, Camera cam){
        if (this.lit)
            return;

        if (this.eyes != null)
            this.eyes.paintComponent(g, imageList);
        
        cam.untranslate((Graphics2D) g);

        g.setColor(Color.red);
        g.drawString(""+this.timer, Defines.width/2 + (int)Defines.tileSize - (int)(Defines.fontMetrics.stringWidth(""+this.timer))/2, (int)(Defines.height/3));
        
        if (this.eyes != null)
            this.eyes.paintStatic(g, imageList, cam);

        cam.translate((Graphics2D) g);
    }
}
