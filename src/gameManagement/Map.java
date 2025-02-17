package gameManagement;

import java.awt.Graphics;
import java.awt.Point;
import java.io.InputStream;
import java.util.Scanner;

import GameObjectPkg.MapObject;
import GameObjectPkg.Player;
import graphicsPkg.ImageList;
import mainPkg.Defines;
import utilsPkg.RayPoint;

public class Map {
    protected MapObject map[][];

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
        Scanner scan;
        
        try{
            InputStream is = this.getClass().getResourceAsStream("maps/"+fileName+".txt");

            scan = new Scanner(is);
        }catch(Exception e){
            e.printStackTrace();
            scan = new Scanner(System.in);
        }

        int width;
        int height;

        width = scan.nextInt();
        height = scan.nextInt();

        Defines.mapSizeX = width;
        Defines.mapSizeY = height;

        this.map = new MapObject[Defines.mapSizeX][Defines.mapSizeY];


        for (int i = 0; i < height; i++){
            for (int ii = 0; ii < width; ii++){
                map[ii][i] = new MapObject(ii*Defines.tileSize, i*Defines.tileSize, Defines.tileSize, Defines.tileSize, true, 0);

                int next = scan.nextInt();

                if (next == 0){
                    next = (int)(Math.random()*(double)Defines.floors)+10;
                }
                map[ii][i].type = next;
                if (next >= Defines.collidable.length){
                    map[ii][i].setCollidable(false);
                    continue;
                }

                map[ii][i].setCollidable(Defines.collidable[next]);
            }
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

    public boolean isValidPoint(Point p){
        if (p.x < 0 || p.x >= this.map.length)
            return false;
        if (p.y < 0 || p.y >= this.map[0].length)
            return false;

        return true;
    }

    public RayPoint getFirstFree(){
        for (int i = 0; i < Defines.mapSizeY; i++){
            for (int ii = 0; ii < Defines.mapSizeX; ii++){
                if (this.map[ii][i].isCollidable())
                    continue;

                return new RayPoint(ii*Defines.tileSize, i*Defines.tileSize);
            }
        }

        return null;
    }

    public static Point getPointFromMapPos(int i, int ii){
        return new Point(i*(int)Defines.tileSize, ii*(int)Defines.tileSize);
    }
    public static Point getMapPosFromPoint(double x, double y){
        return new Point((int)(x/Defines.tileSize), (int)(y/Defines.tileSize));
    }
}
