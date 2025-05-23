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
import mainPkg.Game;

public class Dialogue {
    public static LinkedList<String> dialogue;
    public static LinkedList<String> names;
    public static LinkedList<Boolean> locked;

    public static String showing;
    public static int showingIdx;
    public static int height;
    public int currentNum;
    private boolean exists;

    public Dialogue(int number) {
        dialogue = new LinkedList<>();
        names = new LinkedList<>();
        locked = new LinkedList<>();

        refresh(number);
    }

    public Dialogue() {
        dialogue = new LinkedList<>();
        names = new LinkedList<>();
        locked = new LinkedList<>();
    }

    public static void updateShowing() {
        if (showingIdx >= names.size()) {
            showing = "";
            showingIdx = -1;
            Defines.lockedDialogue = false;

            return;
        }

        if (Defines.fontMetrics == null) return;

        Defines.lockedDialogue = locked.get(showingIdx);
        showing = names.get(showingIdx) + "@@" + dialogue.get(showingIdx);

        int counter = 0;
        int posX = Defines.width / 80;
        for (int i = 0; i < showing.length(); i++) {
            if ((showing.charAt(i) == ' ' && posX > Defines.width - Defines.width / 20)) {
                posX = Defines.width / 80;

                showing = showing.substring(0, i - 1) + "@" + showing.substring(i + 1, showing.length());
                counter += 1;
            }
            posX += Defines.fontMetrics.stringWidth("" + showing.charAt(i));

            if (showing.charAt(i) != '@')
                continue;

            counter += 1;
        }

        height = (int) ((Defines.fontSize + Defines.width / 180) * (counter + 1) + (Defines.fontSize));
        height /= Defines.zoom;
    }

    public static void Continue() {
        if (showingIdx == -1)
            return;

        showingIdx++;
        updateShowing();
    }

    public void refresh(int number) {
        //loads dialogue.txt
        this.exists = true;
        this.currentNum = number;

        dialogue = new LinkedList<>();
        names = new LinkedList<>();
        locked = new LinkedList<>();

        showing = "";
        showingIdx = 0;

        InputStream is = this.getClass().getResourceAsStream("dialogue/dialogue" + number + ".txt");
        if (is == null) {
            this.exists = false;
            return;
        }

        Scanner scan = new Scanner(is);

        String currentName = "";
        String currentDialogue = "";
        Boolean currentLocked = false;

        while (scan.hasNext()) {
            String next = scan.next();

            if (next.equals("{")) {
                currentName = "";
                currentDialogue = "";
                currentLocked = false;
                continue;
            }

            if (next.equals("}")) {
                dialogue.add(currentDialogue);
                names.add(currentName);
                locked.add(currentLocked);
                continue;
            }

            if (next.equals("{LOCK")) {
                currentName = "";
                currentDialogue = "";
                currentLocked = true;
                continue;
            }

            if (currentName.equals("")) {
                currentName = next;
                continue;
            }

            currentDialogue += next + " ";
        }

        scan.close();
        updateShowing();
    }

    public void update(HashMap<String, Integer> map) {
        if (map.get("ENTER") == 1 && showingIdx != -1) {
            showingIdx++;
            updateShowing();

            map.replace("ENTER", 0);
        }
    }

    public void paintComponent(Graphics g, Game game) {
        if (showingIdx == -1 || !this.exists)
            return;

        ((Graphics2D) g).setRenderingHint(
                RenderingHints.KEY_TEXT_ANTIALIASING,
                RenderingHints.VALUE_TEXT_ANTIALIAS_GASP);

        g.setColor(new Color(200, 200, 200, 100));

        int x = (int) ((game.player.getX() - (Defines.width / Defines.zoom - (Defines.width / 80 / Defines.zoom)) / 2));
        int y = (int) ((game.player.getY() - (Defines.height / 2 / Defines.zoom)));

        g.fillRoundRect(x,
                y,
                (int) (Defines.width / Defines.zoom - (Defines.width / 25)),
                (int) (height + Defines.width / 32 / Defines.zoom),
                (int) (Defines.width / 80 / Defines.zoom),
                (int) (Defines.width / 80 / Defines.zoom));

        g.setColor(Color.black);

        int posX = Defines.width / 80;
        int posY = Defines.height - height + Defines.width / 160;

        posX = (int) ((game.player.getX() - (Defines.width / Defines.zoom - (Defines.width / 80 / Defines.zoom)) / 2));
        posY = (int) ((game.player.getY() - (Defines.height / 2.2 / Defines.zoom)));

        for (int i = 0; i < showing.length(); i++) {
            if (showing.charAt(i) == '@') {
                posY += Defines.width / 8000 / Defines.zoom + Defines.fontSize;
                posX = (int) ((game.player.getX() - (Defines.width / Defines.zoom - (Defines.width / 80 / Defines.zoom)) / 2));
                continue;
            }

            if (showing.charAt(i) == ' ' && posX == Defines.width / 80)
                continue;

            g.drawString("" + showing.charAt(i), posX, posY);

            posX += Defines.fontMetrics.stringWidth("" + showing.charAt(i));
        }
    }
}
