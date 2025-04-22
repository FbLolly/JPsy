package menusPkg;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;

import mainPkg.Defines;
import mainPkg.Game;
import mainPkg.JApp;
import saves.Save;
import utilsPkg.Button;
import utilsPkg.Mouse;

public class Menu{
    private Button[] btnList;
    private Mouse mouse;
    private JApp app;

    public Menu(JApp caller){
        this.mouse = new Mouse();
        this.app = caller;
    
        Button[] btns = {
            new Button(Defines.width/2 - 250/2, (int)(100*1.8), 250, 20, Color.red, Color.white, "Play"),
            new Button(Defines.width/2 - 250/2, (int)(200*1.8), 250, 20, Color.red, Color.white, "Settings"),
            new Button(Defines.width/2 - 250/2, (int)(300*1.8), 250, 20, Color.red, Color.white, "Load Save"),
            new Button(Defines.width/2 - 250/2, (int)(400*1.8), 250, 20, Color.red, Color.white, "Quit")
        };

        this.btnList = btns;
        this.app.addMouseListener(mouse);
    }

    public void update(){
        this.mouse.updateMousePos(app);

        for (Button b : btnList){
            b.tick(this.mouse.clicked, new Point(this.mouse.x, this.mouse.y));
            
            if (b.active){
                switch (b.getText()) {
                    case "Play":
                        this.app.game = new Game(app);
                    break;
                    case "Settings":
                        System.out.println("settings");
                    break;
                    case "Load Save":
                        Game game = new Game(app);
                        Save.loadSave(game);

                        this.app.game = game;
                    break;
                    case "Quit":
                        System.exit(0);
                    break;
                }
            }
        }
    }

    public void paintComponent(Graphics g){
        g.setColor(Color.BLACK);
        g.fillRect(0, 0, Defines.width, Defines.height);


        for (Button b : this.btnList){
            b.paintComponent((Graphics2D) g);
        }
    }
}
