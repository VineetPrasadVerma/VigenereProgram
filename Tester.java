
/**
 * Write a description of Tester1 here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
import edu.duke.*;
public class Tester{
    public void testCaesarCipher(){
        CaesarCipher cc = new CaesarCipher(0);
        FileResource fr = new FileResource("titus-small.txt");
        String data = fr.asString();
        String encrypted = cc.encrypt(data);
        System.out.println(encrypted);
    }
    
    public void testCaesarCracker(){
        CaesarCracker ck = new CaesarCracker();
        FileResource fr = new FileResource("oslusiadas_key17.txt");
        String data = fr.asString();
        String decrypted = ck.decrypt(data);
        System.out.println(decrypted);
    }
    
    public void testVigenereCipher(){
        int[] key ={17,14,12,4};
        VigenereCipher vc = new VigenereCipher(key);
        FileResource fr = new FileResource("titus-small.txt");
        String data = fr.asString();
        String encrypted = vc.encrypt(data);
        System.out.println(encrypted);
        String decrypted = vc.decrypt(encrypted);
        System.out.println(decrypted);
    }
    
    public void testSliceOfMessage(){
        VigenereBreaker vb = new VigenereBreaker();
        String sliceOfMessage = vb.sliceString("abcdefghijklm", 4, 5);
        System.out.println(sliceOfMessage);
    }
    
    public void testTryKeyLength(){
        VigenereBreaker vb = new VigenereBreaker();
        FileResource fr = new FileResource("athens_keyflute.txt");
        String encrypted = fr.asString();
        int[] key = vb.tryKeyLength(encrypted,5,'e');
        for(int i=0;i<key.length;i++){
            System.out.print(key[i]+",");
        }
    }
}
