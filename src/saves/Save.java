package saves;

import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.util.Scanner;

import GameObjectPkg.Inventory;
import GameObjectPkg.Player;
import gameManagement.Map;
import itemPkg.Candle;
import itemPkg.Item;
import mainPkg.Defines;
import mainPkg.Game;

public class Save {
    public static void saveEverything(Game game){
        Save.savePlayer(game.player);
        Save.saveInventory(game.player.inv);
        Save.saveSingleMap(game.map);
    }

    public static void savePlayer(Player player){
        FileWriter file = null;

        try{
            file = new FileWriter("src/saves/player.txt");
            file.append(player.getX() + "\n" + player.getY() + "\n\n");
            file.close();

            Save.saveInventory(player.inv);
        }catch(IOException e){
            e.printStackTrace();
        }
    }

    public static void saveInventory(Inventory inv){
        FileWriter file = null;
        
        try{
            file = new FileWriter("src/saves/inventory.txt");
            
            for (int i = 0; i < inv.inv.length; i++){
                if (inv.inv[i] == null){
                    file.append("\n");
                    continue;
                }

                file.append(inv.inv[i].ID + "\n");
                if (!(inv.inv[i] instanceof Candle))
                    continue;
                
                Candle c = (Candle)inv.inv[i];
                file.append(""+c.getCandleCount());
            }

            file.close();
        }catch (IOException e){
            e.printStackTrace();
        }
    }

    public static void saveSingleMap(Map map){
        FileWriter file = null;

        try{
            file = new FileWriter("src/saves/mapSave.txt");

            file.write("");
            file.append(map.lit + "\n\n");
            file.append(map.start + "\n\n");
            file.append(map.width + "\n" + map.height + "\n\n");
            file.append(map.nextRoom + "\n");
            for (int i = 0; i < map.height; i++){
                for (int ii = 0; ii < map.width; ii++){
                    int prntNum = map.map[ii][i].type;

                    if (prntNum <= 9)
                        file.append("0");
                    file.append(prntNum + " ");
                }
                file.append("\n");
            }

            file.append("\n\n\n");
            for (int i = 0; i < map.interactibles.size(); i++){
                if (map.map[map.interactibles.get(i).x][map.interactibles.get(i).y].type >= 10 &&
                map.map[map.interactibles.get(i).x][map.interactibles.get(i).y].type < 14)
                    continue;

                file.append(map.interactibles.get(i).x + " " + map.interactibles.get(i).y + "\n");
            }
            file.close();
        } catch(IOException e){
            e.printStackTrace();
        }
    }

    public static void loadSingleMap(Game game){
        game.map.load(game.map.getClass().getResourceAsStream("../saves/mapSave.txt"), game, 99999);
    }

    public static void loadInventory(Game game){
        String it[] = new String[Defines.inventorySize];
        InputStream is = game.getClass().getResourceAsStream("../saves/inventory.txt");
        Integer count = null;
        if (is == null){
            System.err.println("INVENTORY SAVE FILE NOT FOUND");
            return;
        }
        Scanner scan = new Scanner(is);

        for (int i = 0; i < Defines.inventorySize; i++){
            if (!scan.hasNext()){
                scan.close();
                return;
            }

            it[i] = scan.next();

            if (it[i].equals("Item@Candle")){
                count = scan.nextInt();
            }
        }

        game.player.inv = new Inventory(it, count);
        scan.close();
    }

    public static void loadSave(Game game){
        InputStream is = game.getClass().getResourceAsStream("../saves/player.txt");
        if (is == null){
            System.err.println("ERROR, COULDNT FIND PLAYER SAVEFILE");
            return;
        }
        
        Scanner scan = new Scanner(is);
        game.player = new Player(scan.nextDouble(), scan.nextDouble(), Defines.tileSize, Defines.tileSize);
        scan.close();

        is = game.getClass().getResourceAsStream("../saves/inventory.txt");
        if (is == null){
            System.err.println("ERROR, COULDNT FIND INVENTORY SAVEFILE");
            return;
        }
        scan = new Scanner(is);
        
        scan.close();
    }
}
