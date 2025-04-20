package saves;

import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Scanner;

import GameObjectPkg.playerPkg.Inventory;
import GameObjectPkg.playerPkg.Player;
import gameManagement.Map;
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
        String add = "";

        try{
            file = new FileWriter("src/saves/player.txt");
            add += player.getX() + "\n" + player.getY() + "\n\n";

            file.write(Encrypter.Encrypt(add));
            file.close();

            Save.saveInventory(player.inv);
        }catch(IOException e){
            e.printStackTrace();
        }
    }

    public static void saveInventory(Inventory inv){
        FileWriter file = null;
        String add = "";
        
        try{
            file = new FileWriter("src/saves/inventory.txt");
            file.write("");
            
            for (int i = 0; i < inv.inv.length; i++){
                if (inv.inv[i] == null){
                    add += "\n";
                    continue;
                }

                add += inv.inv[i].type + "\n" + inv.inv[i].attributesInt.size() + "\n";
                
                for (String s : inv.inv[i].attributesInt.keySet())
                    add += s + " " + inv.inv[i].attributesInt.get(s) + "\n";

                add += inv.inv[i].attributesBool.size() + "\n";
                
                for (String s : inv.inv[i].attributesBool.keySet())
                    add += s + " " + inv.inv[i].attributesBool.get(s) + "\n";

                add += "\n\n";
            }

            file.write(Encrypter.Encrypt(add));
            file.close();
        }catch (IOException e){
            e.printStackTrace();
        }
    }

    public static void saveSingleMap(Map map){
        FileWriter file = null;
        String add = "";

        try{
            file = new FileWriter("src/saves/mapSave.txt");

            add += map.lit + "\n\n";
            add += map.start + "\n\n";
            add += map.width + "\n" + map.height + "\n\n";
            add += map.nextRoom + "\n";
            for (int i = 0; i < map.height; i++){
                for (int ii = 0; ii < map.width; ii++){
                    int prntNum = map.map[ii][i].type;

                    if (prntNum > 10 && prntNum < 14)
                        prntNum = 10;
                    
                    if (prntNum <= 9)
                        add += "0";
                    add += prntNum + " ";
                }
                add += "\n";
            }

            add += "\n\n\n";
            for (int i = 0; i < map.interactibles.size(); i++){
                if (map.map[map.interactibles.get(i).x][map.interactibles.get(i).y].type >= 10 &&
                map.map[map.interactibles.get(i).x][map.interactibles.get(i).y].type < 14)
                    continue;

                add += map.interactibles.get(i).x + " " + map.interactibles.get(i).y + "\n";
            }

            file.write(Encrypter.Encrypt(add));
            file.close();
        } catch(IOException e){
            e.printStackTrace();
        }
    }

    public static void loadSingleMap(Game game){
        String s = StringInputStream.fileToString(game.map.getClass().getResourceAsStream("../saves/mapSave.txt"));
        InputStream is = StringInputStream.StringToFile(Encrypter.Decrypt(s));

        game.map.load(is, game, 99999);
    }

    public static void loadInventory(Game game){
        Item items[] = new Item[Defines.inventorySize];
        InputStream is = game.getClass().getResourceAsStream("../saves/inventory.txt");
        String s = Encrypter.Decrypt(StringInputStream.fileToString(is));
        is = StringInputStream.StringToFile(s);

        if (is == null){
            System.err.println("INVENTORY SAVE FILE NOT FOUND");
            return;
        }
        Scanner scan = new Scanner(is);

        for (int i = 0; i < Defines.inventorySize; i++){
            if (!scan.hasNext()){
                scan.close();
                break;
            }

            int type;
            HashMap<String, Integer> attInt = new HashMap<>();
            HashMap<String, Boolean> attBool = new HashMap<>();

            type = scan.nextInt();

            int nint = scan.nextInt();
            for (int ii = 0; ii < nint; ii++){
                attInt.put(scan.next(), scan.nextInt());
            }

            int nbool = scan.nextInt();
            for (int ii = 0; ii < nbool; ii++){
                attBool.put(scan.next(), scan.nextBoolean());
            }

            items[i] = new Item(type, attInt, attBool);
        }

        game.player.inv = new Inventory(items);
        scan.close();
    }

    public static void loadSave(Game game){
        Save.loadInventory(game);
        Save.loadSingleMap(game);
    }
}
