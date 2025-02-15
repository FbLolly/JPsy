package gameManagement;

import java.awt.Graphics;
import java.awt.Point;
import java.io.InputStream;
import java.util.Scanner;

import entityPkg.MapObject;
import entityPkg.Player;
import mainPkg.Defines;

public class Map {
    protected MapObject map[][];
    protected boolean existance[][];

    public Map(){
        this.map = new MapObject[Defines.mapSizeX][Defines.mapSizeY];
        this.existance = new boolean[Defines.mapSizeX][Defines.mapSizeY];

        //creates an empty map

        for (int i = 0; i < Defines.mapSizeX; i++){
            for (int ii = 0; ii < Defines.mapSizeY; ii++){
                map[i][ii] = new MapObject(i*Defines.tileSize, ii*Defines.tileSize, Defines.tileSize, Defines.tileSize, true);
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
                map[ii][i] = new MapObject(ii*Defines.tileSize, i*Defines.tileSize, Defines.tileSize, Defines.tileSize, true);
                existance[ii][i] = false;

                if (scan.nextInt() == 1)
                    existance[ii][i] = true;
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

        Collider.manageCollisions(this, player, player.map);
    }

    public void paintComponent(Graphics g){
        for (int i = 0; i < Defines.mapSizeX; i++){
            for (int ii = 0; ii < Defines.mapSizeY; ii++){
                if (!existance[i][ii])
                    continue;

                map[i][ii].paintComponent(g);
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

    public static Point getPointFromMapPos(int i, int ii){
        return new Point(i*(int)Defines.tileSize, ii*(int)Defines.tileSize);
    }
    public static Point getMapPosFromPoint(double x, double y){
        return new Point((int)(x/Defines.tileSize), (int)(y/Defines.tileSize));
    }
}
