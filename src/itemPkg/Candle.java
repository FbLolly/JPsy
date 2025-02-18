package itemPkg;

import mainPkg.JApp;

public class Candle extends Item {
    public Candle(int type) {
        super(type);
    }
    
    public void update(JApp app){
        app.map.lit = true;
    }
}
