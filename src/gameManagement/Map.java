package gameManagement;

import java.awt.Graphics;
import java.awt.Point;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Scanner;

import GameObjectPkg.Entity;
import GameObjectPkg.InteractiveObject;
import GameObjectPkg.MapObject;
import graphicsPkg.ImageList;
import mainPkg.Defines;
import mainPkg.Game;
import utilsPkg.Camera;
import utilsPkg.RayPoint;

public class Map {
    public MapObject map[][];
    public boolean lit;
    private Point spawn;
    public int nextRoom;
    public int start;
    public int width, height;
    public ArrayList<Point> interactibles;

    public int name;

    public Map(){
        this.map = new MapObject[Defines.mapSizeX][Defines.mapSizeY];
    }

    public void load(InputStream is, Game game, int fileName){
        if (is == null)
            return;
        Scanner scan = new Scanner(is);

        this.map = new MapObject[Defines.mapSizeX][Defines.mapSizeY];

        this.lit = scan.nextBoolean();

        this.start = scan.nextInt();

        width = scan.nextInt();
        height = scan.nextInt();

        Defines.mapSizeX = width;
        Defines.mapSizeY = height;

        nextRoom = scan.nextInt();

        this.map = new MapObject[Defines.mapSizeX][Defines.mapSizeY];

        game.changeDialogue(fileName);

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
                map[ii][i].type = next + this.start;
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
            interactibles.add(next);
        }

        scan.close();
    }

    public Map(int fileName, Game game){
        this.name = fileName;

        interactibles = new ArrayList<>();

        InputStream is = this.getClass().getResourceAsStream("maps/"+fileName+".txt");
        
        this.load(is, game, fileName);
    }

    public void update(Entity[] entityList){
        for (int i = 0; i < Defines.mapSizeX; i++){
            for (int ii = 0; ii < Defines.mapSizeY; ii++){
                map[i][ii].update();
            }
        }

        for (Entity e : entityList)
            Collider.manageCollisions(this, e);
    }

    public void paintComponent(Graphics g, ImageList imageList, Camera cam){
        for (int i = 0; i < Defines.mapSizeX; i++){
            for (int ii = 0; ii < Defines.mapSizeY; ii++){
                if (this.map[i][ii].type < 0)
                    continue;


                map[i][ii].paintComponent(g, imageList, cam);
            }
        }
    }

    public void paintLighting(Graphics g, Game game){
        if (this.lit)
            return;

        game.cam.paintImage(Defines.getCurrentAnimationImage(game.imageList.getImages("0",
                            (int)(Defines.width/Defines.zoom) + Defines.tileSize,
                            (int)(Defines.height/Defines.zoom) + Defines.tileSize)),
                            (int)(game.player.getX() - Defines.width/Defines.zoom/1.85),
                            (int)(game.player.getY() - Defines.height/Defines.zoom/1.8), g);
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

    public MapObject indexedEntry(int i, int ii){
        return this.map[i][ii];
    }
}
