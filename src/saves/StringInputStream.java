package saves;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.util.Scanner;

public class StringInputStream {
    public static String fileToString(InputStream is){
        Scanner scan = new Scanner(is);
        String string = "";

        while (scan.hasNext()){
            string += scan.next() + "\n";
        }

        scan.close();
        return string;
    }

    public static InputStream StringToFile(String string){
        return new ByteArrayInputStream(string.getBytes(StandardCharsets.UTF_8));
    }
}
