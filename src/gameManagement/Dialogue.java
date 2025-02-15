package gameManagement;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.io.InputStream;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Scanner;

import mainPkg.Defines;
import utilsPkg.Camera;

public class Dialogue {
    private LinkedList<String> dialogue;
    private LinkedList<String> names;

    private String showing;
    private int showingIdx;

    private int height;

    public void refresh(String number){
        //loads dialogue.txt

        showing = "";
        showingIdx = 0;

        InputStream is = this.getClass().getResourceAsStream("dialogue/dialogue" + number + ".txt");
        Scanner scan = new Scanner(is);

        String currentName = "";
        String currentDialogue = "";

        while(scan.hasNext()){
            String next = scan.next();

            if (next.equals("{")){
                currentName = "";
                currentDialogue = "";
                continue;
            }

            if (next.equals("}")){
                dialogue.add(currentDialogue);
                names.add(currentName);
                continue;
            }


            if (currentName.equals("")){
                currentName = next;
                continue;
            }

            currentDialogue += next + " ";
        }

        scan.close();
        updateShowing();
    }

    public Dialogue(String number){
        dialogue = new LinkedList<>();
        names = new LinkedList<>();

        refresh(number);
    }

    public Dialogue(){
        dialogue = new LinkedList<>();
        names = new LinkedList<>();
    }


    public void updateShowing(){
        if (showingIdx >= this.names.size()){
            this.showing = "";
            this.showingIdx = -1;
            return;
        }

        this.showing = this.names.get(showingIdx) + "@@" + this.dialogue.get(showingIdx);

        int counter = 0;
        for (int i = 0; i < this.showing.length(); i++){
            if (this.showing.charAt(i) != '@')
                continue;
            
            counter += 1;
        }

        height = (int)((Defines.fontSize + Defines.width/180)*(counter+1));
    }

    public void update(HashMap<String, Integer> map){
        if (map.get("ENTER") == 1 && this.showingIdx != -1){
            this.showingIdx++;
            this.updateShowing();

            map.replace("ENTER", 0);
        }
    }

    public void paintComponent(Graphics g, Camera cam){
        if (this.showingIdx == -1)
            return;

        cam.untranslate((Graphics2D) g);

        ((Graphics2D) g).setRenderingHint(
            RenderingHints.KEY_TEXT_ANTIALIASING,
            RenderingHints.VALUE_TEXT_ANTIALIAS_GASP);

        g.setColor(Color.lightGray);
        g.fillRoundRect(Defines.width/160, Defines.height - height - Defines.width/80,
                        Defines.width - (Defines.width/80), height + Defines.width/160,
                        Defines.width/160, Defines.width/160);

        g.setColor(Color.black);

        int posX = Defines.width/80;
        int posY = Defines.height - height + Defines.width/160;
        for (int i = 0; i < showing.length(); i++){
            if ((showing.charAt(i) == ' ' && posX > Defines.width - Defines.width/20) || showing.charAt(i) == '@'){
                posY += Defines.width/320 + Defines.fontSize;
                posX = Defines.width/80;
                continue;
            }

            if (showing.charAt(i) == ' ' && posX == Defines.width/80)
                continue;

            g.drawString("" + showing.charAt(i), posX, posY);

            posX += g.getFontMetrics().stringWidth("" + showing.charAt(i));
        }

        cam.translate((Graphics2D) g);
    }
}
