package graphicsPkg;

import java.awt.Image;
import java.io.InputStream;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Scanner;

import javax.imageio.ImageIO;

import mainPkg.Defines;

public class ImageList {
    private HashMap<String, LinkedList<Image>> imageMap;

    public ImageList(){
        imageMap = new HashMap<>();
    }

    public LinkedList<Image> getImages(String what, int width, int height){
        if (imageMap.containsKey(what))
            return imageMap.get(what);
        
        int times = 0;

        imageMap.put(what, new LinkedList<Image>());
        
        InputStream is = this.getClass().getResourceAsStream("images/"+what+"/exists.txt");
        if (is == null)
            return null;

        Scanner scan = new Scanner(is);
        if (!scan.hasNextInt()) times = 1;
        else times = scan.nextInt();
        scan.close();
        
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

        LinkedList<Image> finalList = new LinkedList<>();

        for (int i = 0; i < times; i++){
            for (Image img : imageMap.get(what)){
                finalList.add(img);
            }
        }
        imageMap.replace(what, finalList);

        return imageMap.get(what);
    }
}
