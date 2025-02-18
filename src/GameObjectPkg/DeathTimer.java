package GameObjectPkg;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;

import graphicsPkg.ImageList;
import mainPkg.Defines;
import mainPkg.JApp;
import utilsPkg.Camera;

public class DeathTimer {
    public int timer;
    public boolean lit;

    public DeathTimer(){
        this.timer = 30;
    }

    public void update(JApp app){
        this.lit = false;
        if (app.map.lit){
            this.lit = true;
            return;
        }
        
        if (Defines.timer > Defines.FPS)
            this.timer--;
        
        if (this.timer <= 0){
            this.lit = true;
        }
    }

    public void paintComponent(Graphics g, ImageList imageList, Camera cam){
        if (this.lit)
            return;
        
        cam.untranslate((Graphics2D) g);

        g.setColor(Color.red);
        g.drawString(""+this.timer, Defines.width/2 + (int)Defines.tileSize - (int)(Defines.fontMetrics.stringWidth(""+this.timer))/2, (int)(Defines.height/3));

        cam.translate((Graphics2D) g);
    }
}
