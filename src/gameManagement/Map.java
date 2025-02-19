package gameManagement;

import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.io.InputStream;
import java.util.Scanner;

import GameObjectPkg.InteractiveObject;
import GameObjectPkg.MapObject;
import GameObjectPkg.Player;
import graphicsPkg.ImageList;
import mainPkg.Defines;
import utilsPkg.Camera;
import utilsPkg.RayPoint;

public class Map {
    public MapObject map[][];
    public boolean lit;
    private Point spawn;

    public Map(){
        this.map = new MapObject[Defines.mapSizeX][Defines.mapSizeY];

        //creates an empty map

        for (int i = 0; i < Defines.mapSizeX; i++){
            for (int ii = 0; ii < Defines.mapSizeY; ii++){
                map[i][ii] = new MapObject(i*Defines.tileSize, ii*Defines.tileSize, Defines.tileSize, Defines.tileSize, true, 1);
            }
        }
    }
    public Map(String fileName){
        InputStream is = this.getClass().getResourceAsStream("maps/"+fileName+".txt");
        if (is == null)
            return;
        Scanner scan = new Scanner(is);

        int width;
        int height;

        this.lit = scan.nextBoolean();

        width = scan.nextInt();
        height = scan.nextInt();

        Defines.mapSizeX = width;
        Defines.mapSizeY = height;

        this.map = new MapObject[Defines.mapSizeX][Defines.mapSizeY];


        for (int i = 0; i < height; i++){
            for (int ii = 0; ii < width; ii++){
                map[ii][i] = new MapObject(ii*Defines.tileSize, i*Defines.tileSize, Defines.tileSize, Defines.tileSize, true, 0);

                int next = scan.nextInt();

                switch (next){
                    case -1:
                        this.spawn = new Point(ii, i);
                    //the brake is not there for a reason
                    case 0:
                        next = (int)(Math.random()*(double)Defines.floors)+10;
                    break;
                }
                map[ii][i].type = next;
                if (next >= Defines.collidable.length){
                    map[ii][i].setCollidable(false);
                    continue;
                }

                map[ii][i].setCollidable(Defines.collidable[next]);
            }
        }

        while (scan.hasNextInt()){
            Point next = new Point(scan.nextInt(), scan.nextInt());

            if (!this.isValidPoint(next))
                break;

            map[next.x][next.y] = new InteractiveObject(next.x*Defines.tileSize, next.y*Defines.tileSize,
                                                        Defines.tileSize, Defines.tileSize, map[next.x][next.y].isCollidable(),
                                                        map[next.x][next.y].type);
        }

        scan.close();
    }

    public void update(Player player){
        for (int i = 0; i < Defines.mapSizeX; i++){
            for (int ii = 0; ii < Defines.mapSizeY; ii++){
                map[i][ii].update();
            }
        }

        Collider.manageCollisions(this, player);
    }

    public void paintComponent(Graphics g, ImageList imageList){
        for (int i = 0; i < Defines.mapSizeX; i++){
            for (int ii = 0; ii < Defines.mapSizeY; ii++){
                if (this.map[i][ii].type < 0)
                    continue;

                map[i][ii].paintComponent(g, imageList);
            }
        }
    }

    public void paintLighting(Graphics g, ImageList imageList, Camera cam){
        if (this.lit)
            return;

        cam.untranslate((Graphics2D) g);

        g.drawImage(Defines.getCurrentAnimationImage(imageList.getImages("0", Defines.width, Defines.height)), 0, 0, null);

        cam.translate((Graphics2D) g);
    }

    public boolean isValidPoint(Point p){
        if (p.x < 0 || p.x >= this.map.length)
            return false;
        if (p.y < 0 || p.y >= this.map[0].length)
            return false;

        return true;
    }

    public RayPoint getSpawn(){
        return new RayPoint(this.spawn.x * Defines.tileSize, this.spawn.y * Defines.tileSize);
    }

    public static Point getPointFromMapPos(int i, int ii){
        return new Point(i*(int)Defines.tileSize, ii*(int)Defines.tileSize);
    }
    public static Point getMapPosFromPoint(double x, double y){
        x += Defines.tileSize/2;
        y += Defines.tileSize/2;

        return new Point((int)(x/Defines.tileSize), (int)(y/Defines.tileSize));
    }
}
