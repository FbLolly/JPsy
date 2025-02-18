package graphicsPkg;

import java.awt.Image;
import java.io.InputStream;
import java.util.HashMap;
import java.util.LinkedList;

import javax.imageio.ImageIO;

public class ImageList {
    private HashMap<String, LinkedList<Image>> imageMap;

    public ImageList(){
        imageMap = new HashMap<>();
    }

    public LinkedList<Image> getImages(String what, int width, int height){
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

                imageMap.get(what).add(img.getScaledInstance(width, height, Image.SCALE_DEFAULT));
            }catch(Exception e){
                break;
            }

            idx++;
        }while (is != null);

        return imageMap.get(what);
    }
}
