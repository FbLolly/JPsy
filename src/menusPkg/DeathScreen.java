package menusPkg;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Scanner;

import mainPkg.Game;

public class DeathScreen {
    private String message;
    
    public DeathScreen(Game game){
        InputStream is = this.getClass().getResourceAsStream("../gameManagement/dialogue/deathMessages.txt");
        if (is == null){
            System.out.println("ERROR LOADING DEATH MESSAGES");
        }
        Scanner scan = new Scanner(is);
        ArrayList<String> deathMessages = new ArrayList<>();
        
        while (scan.hasNextLine()){
            deathMessages.add(scan.nextLine());
        }

        scan.close();

        int rand = (int)(Math.random()*deathMessages.size());
        message = deathMessages.get(rand);

        //SAVE EVERYTHING
        saveGame(game);
    }

    public void recovery(){
    }

    public void saveGame(Game game){
        //FileWriter file = null;
        //
        //try {
        //    file = new FileWriter("src/saves/player.txt");
        //
        //    file.write(game.player.getX() + "\n" + game.player.getY() + "\n" );
//
        //    file.close();
        //} catch (IOException e) {
        //    e.printStackTrace();
        //}
    }
}
