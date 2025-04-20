package particlesPkg;

import java.awt.Graphics;
import java.awt.Point;
import java.util.ArrayList;

import graphicsPkg.ImageList;

public class Particles {
    private static ArrayList<ObjectParticles> particles;

    public Particles(){
        particles = new ArrayList<>();
    }

    public static void addParticle(Point startingPoint, Point direction, String type, double minSpeed, double maxSpeed, int amount){ //direction = 0,0 -> on every side
        //automatically converts player facing to particle facing
        
        if (direction.x == 0 && direction.y == 0){
            particles.add(
                new ObjectParticles(startingPoint, new Point(1, 1), type, minSpeed, maxSpeed, amount/2)
            );

            particles.add(
                new ObjectParticles(startingPoint, new Point(-1, 1), type, minSpeed, maxSpeed, amount/2)
            );
            particles.add(
                new ObjectParticles(startingPoint, new Point(1, -1), type, minSpeed, maxSpeed, amount/2)
            );

            particles.add(
                new ObjectParticles(startingPoint, new Point(-1, -1), type, minSpeed, maxSpeed, amount/2)
            );
        }

        if (direction.x == 0){
            particles.add(
                new ObjectParticles(startingPoint, new Point(1, direction.y), type, minSpeed, maxSpeed, amount/2)
            );

            particles.add(
                new ObjectParticles(startingPoint, new Point(-1, direction.y), type, minSpeed, maxSpeed, amount/2)
            );
        
            return;
        }
        
        if (direction.y == 0){
            particles.add(
                new ObjectParticles(startingPoint, new Point(direction.x, 1), type, minSpeed, maxSpeed, amount)
            );

            particles.add(
                new ObjectParticles(startingPoint, new Point(direction.x, -1), type, minSpeed, maxSpeed, amount)
            );
        
            return;
        }

        particles.add(new ObjectParticles(startingPoint, direction, type, minSpeed, maxSpeed, amount));
    }

    public void update(){
        try{
            for (ObjectParticles objp : particles){
                objp.update();
            }
        } catch (Exception e){}
    }

    public void paintComponent(Graphics g, ImageList imageList){
        for (ObjectParticles objp : particles){
            objp.paintComponent(g, imageList);
        }
    }
}
