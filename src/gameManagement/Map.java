package gameManagement;

import java.awt.Graphics;
import java.awt.Point;
import java.io.InputStream;
import java.util.Scanner;

import entityPkg.MapObject;
import entityPkg.Player;
import graphicsPkg.ImageList;
import mainPkg.Defines;
import utilsPkg.RayPoint;

public class Map {
    protected MapObject map[][];
    protected boolean existance[][];

    public Map(){
        this.map = new MapObject[Defines.mapSizeX][Defines.mapSizeY];
        this.existance = new boolean[Defines.mapSizeX][Defines.mapSizeY];

        //creates an empty map

        for (int i = 0; i < Defines.mapSizeX; i++){
            for (int ii = 0; ii < Defines.mapSizeY; ii++){
                map[i][ii] = new MapObject(i*Defines.tileSize, ii*Defines.tileSize, Defines.tileSize, Defines.tileSize, true, 1);
                existance[i][ii] = false;
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
        this.existance = new boolean[Defines.mapSizeX][Defines.mapSizeY];


        for (int i = 0; i < height; i++){
            for (int ii = 0; ii < width; ii++){
                map[ii][i] = new MapObject(ii*Defines.tileSize, i*Defines.tileSize, Defines.tileSize, Defines.tileSize, true, 0);
                existance[ii][i] = false;

                int next = scan.nextInt();

                if (next != 0){
                    existance[ii][i] = true;
                    map[ii][i].type = next;
                }
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
                if (!existance[i][ii])
                    continue;

                map[i][ii].paintComponent(g, imageList.getImages("" + this.map[i][ii].type).get(0));
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
        for (int i = 0; i < Defines.mapSizeX; i++){
            for (int ii = 0; ii < Defines.mapSizeY; ii++){
                if (this.existance[i][ii])
                    continue;

                return new RayPoint(i*Defines.tileSize, ii*Defines.tileSize);
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
