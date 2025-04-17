package menusPkg;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.Scanner;

import mainPkg.Game;

public class DeathScreen {
    public String message;

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
    }
}
