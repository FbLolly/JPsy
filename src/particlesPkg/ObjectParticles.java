package particlesPkg;

import java.awt.Graphics;
import java.awt.Point;
import java.util.ArrayList;

import graphicsPkg.ImageList;
import graphicsPkg.TransparentImage;
import mainPkg.Defines;

public class ObjectParticles {
    private String type;
    private ArrayList<Particle> particles;

    public ObjectParticles(Point startingPoint, Point direction, String type, double minSpeed, double maxSpeed, int amount){
        particles = new ArrayList<>();
        this.type = type;
    
        for (int i = 0; i < amount; i++){
            particles.add(new Particle(startingPoint, direction, minSpeed, maxSpeed));
        }
    }

    public void update(){
        for (int i = 0; i < particles.size(); i++){
            Particle p = this.particles.get(i);

            p.update();

            if (p.transparency <= 0){
                particles.remove(p);
                p = null;
            }
        }
    }

    public void paintComponent(Graphics g, ImageList imageList){
        for (Particle p : this.particles){
            g.drawImage(
                TransparentImage.getTransparentImage(
                    Defines.getCurrentAnimationImage(
                        imageList.getImages("particles_"+type, (int)(Defines.tileSize/6.0), (int)(Defines.tileSize/6.0))
                    ),
                    p.transparency
                ),
                (int)p.pos.x,
                (int)p.pos.y,
                null
            );
        }
    }
}
