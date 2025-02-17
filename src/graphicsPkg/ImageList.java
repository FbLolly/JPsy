package graphicsPkg;

import java.awt.Image;
import java.io.InputStream;
import java.util.HashMap;
import java.util.LinkedList;

import javax.imageio.ImageIO;

import mainPkg.Defines;

public class ImageList {
    private HashMap<String, LinkedList<Image>> imageMap;

    public ImageList(){
        imageMap = new HashMap<>();
    }

    public LinkedList<Image> getImages(String what){
        if (imageMap.containsKey(what))
            return imageMap.get(what);
        
        imageMap.put(what, new LinkedList<Image>());
        
        InputStream is = this.getClass().getResourceAsStream("images/"+what+"/exists.txt");
        if (is == null)
            return null;
        
        int idx = 1;
        do{
            is = this.getClass().getResourceAsStream("images/"+what+"/"+idx+".png");
            if (is == null)
                continue;

            try{
                Image img = ImageIO.read(is);

                imageMap.get(what).add(img.getScaledInstance((int)Defines.tileSize, (int)Defines.tileSize, Image.SCALE_DEFAULT));
            }catch(Exception e){
                if (what.equals("0"))
                    break;
            }

            idx++;
        }while (is != null);

        return imageMap.get(what);
    }
}
