package itemPkg;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Point;

import graphicsPkg.ImageList;
import mainPkg.Defines;

public abstract class DurabilityItem {
    public static void paint(Graphics g, Point pos, int type, int durability, ImageList imageList) {
        g.drawImage(Defines.getCurrentAnimationImage(imageList.getImages("" + type, (int) Defines.invItemSize, (int) Defines.invItemSize)), pos.x, pos.y, null);

        g.setColor(Color.gray);
        g.fillRoundRect(pos.x, (int) (pos.y + Defines.invItemSize - Defines.invItemSize / 7.5),
                (int) Defines.invItemSize, (int) (Defines.invItemSize / 10.0), 2, 2);

        if (durability < Defines.lighterDurability / 5) g.setColor(Color.red);
        else if (durability < Defines.lighterDurability / 3) g.setColor(Color.yellow);
        else g.setColor(Color.green);

        g.fillRoundRect(pos.x, (int) (pos.y + Defines.invItemSize - Defines.invItemSize / 7.5),
                (int) (((double) durability / (double) Defines.lighterDurability) * (double) Defines.invItemSize),
                (int) (Defines.invItemSize / 10.0), 2, 2);
    }
}
