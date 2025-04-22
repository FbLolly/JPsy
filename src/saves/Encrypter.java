package saves;

public class Encrypter {
    public static String Encrypt(String string) {
        String ret = "";

        for (int i = 0; i < string.length(); i++) {
            ret += (char) (((int) string.charAt(i)) + 1);
        }

        return ret;
    }

    public static String Decrypt(String string) {
        String ret = "";

        for (int i = 0; i < string.length(); i++) {
            ret += (char) (((int) string.charAt(i)) - 1);
        }

        return ret;
    }
}
