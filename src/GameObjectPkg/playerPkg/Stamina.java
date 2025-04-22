package GameObjectPkg.playerPkg;

import java.awt.Color;
import java.awt.Graphics;

import mainPkg.Defines;

public class Stamina {
    private int maxStamina;
    private int stamina;
    private Player player;

    public Stamina(Player player) {
        this.player = player;

        this.maxStamina = Defines.maxStamina;
        this.stamina = this.maxStamina;
    }

    public void update() {
        if (this.stamina <= 5 && this.player.movement.equals("running")) {
            this.stamina = 0;
        }

        if (this.stamina <= 0) {
            this.player.movement = "idle";
            this.stamina = 1;

            player.setX(player.getX() - player.getSpeedX());
            player.setY(player.getY() - player.getSpeedY());

            return;
        }

        if (player.movement.equals("running")) {
            this.stamina--;
            return;
        }

        if (this.stamina >= this.maxStamina)
            return;

        this.stamina++;
    }

    public void paintComponent(Graphics g) {
        g.setColor(Color.gray);
        g.fillRoundRect((int) (player.getX()), (int) player.getY() + Defines.tileSize, Defines.tileSize, Defines.tileSize / 15, Defines.tileSize / 15, Defines.tileSize / 15);


        double percentage = (double) this.stamina / (double) this.maxStamina;
        percentage *= 100;

        g.setColor(Color.green);
        g.fillRoundRect((int) (player.getX()), (int) player.getY() + Defines.tileSize, (int) (Defines.tileSize * percentage / 100), Defines.tileSize / 15, Defines.tileSize / 15, Defines.tileSize / 15);
    }
}
