import java.util.*;
import edu.duke.*;
import java.io.*;

public class VigenereBreaker {
    public String sliceString(String message, int whichSlice, int totalSlices) {
        StringBuilder sliceOfMessage = new StringBuilder();
        for(int i= whichSlice; i<message.length(); i=i+totalSlices){
            sliceOfMessage.append(message.charAt(i));
        }
        return sliceOfMessage.toString();
    }

    public int[] tryKeyLength(String encrypted, int klength, char mostCommon) {
        int[] key = new int[klength];
        for(int i=0; i<klength; i++){
            String sliceOfMessage = sliceString(encrypted,i,klength);
            CaesarCracker ck = new CaesarCracker(mostCommon);
            int dkey = ck.getKey(sliceOfMessage);
            key[i]=dkey;
        }
        
        return key;
    }

    public void breakVigenere () {
        FileResource fr = new FileResource();
        String encrypted  = fr.asString();
        HashMap<String,HashSet<String>> dictionaries = new HashMap<String,HashSet<String>>();
        DirectoryResource dr = new DirectoryResource();
        for(File f : dr.selectedFiles()){
            dictionaries.put(f.getName(),readDictionary(new FileResource(f)));    
        }
        //FileResource fr1 = new FileResource("dictionaries/English");
        //HashSet<String> realWords = readDictionary(fr1);
        //int[] key = tryKeyLength(encrypted,5,'e');
        breakForAllLangs(encrypted,dictionaries);
        //VigenereCipher vc = new VigenereCipher(key);
        //String decrypted = vc.decrypt(encrypted);
        //System.out.println(decrypted.substring(0,100));
    }
    
    public HashSet<String> readDictionary(FileResource fr){
        HashSet<String> words = new HashSet<String>();
        for(String word : fr.lines()){
            words.add(word.toLowerCase());
        }
        return words;
    }
    
    public int countWords(String message , HashSet<String> dictionary){
        int count = 0;
        for(String word : message.split("\\W")){
            if(dictionary.contains(word.toLowerCase())){
                count++;
            }
        }
        return count;
    }
    
    public String breakForLanguages(String encrypted , HashSet dictionary){
        int maxValue = Integer.MIN_VALUE;
        String decryption ="";
        int kLength=0;
        int[] keyFound;
        for(int i=1; i<100 ; i++){
            char mostCommon = mostCommonCharIn(dictionary);
            int[] key = tryKeyLength(encrypted,i,mostCommon);
            VigenereCipher vc = new VigenereCipher(key);
            String decrypted = vc.decrypt(encrypted);
            int realWordCounts = countWords(decrypted,dictionary);
            if(realWordCounts > maxValue){
                maxValue = realWordCounts;
                decryption = decrypted;
                kLength = i;
                keyFound = key;
            }
        }
        System.out.println("words "+maxValue+" , Length of key " +kLength);
        return decryption;
    }
    
    public char mostCommonCharIn(HashSet<String> dictionary){
        int maxValue = Integer.MIN_VALUE;
        char mostCommon= ' ';
        int[] counts = new int[26];
        String alph ="abcdefghijklmnopqrstuvwxyz";
        for(String word : dictionary){
            for(int i=0;i<word.length();i++){
                int idx = alph.indexOf(Character.toLowerCase(word.charAt(i)));
                if(idx !=- 1){
                    counts[idx] += 1;
                }
            }
        }
        for(int k=0;k<counts.length;k++){
            int value = counts[k];
            if(value>maxValue){
                maxValue=value;
                mostCommon=alph.charAt(k);
            }
        }
        return mostCommon;
    }
    
    public void breakForAllLangs(String encrypted, HashMap<String,HashSet<String>> languages){
        int maxValue = Integer.MIN_VALUE;
        String printLanguageName = "";
        String printDecrypted = "";
        for(String languageName : languages.keySet()){
            HashSet<String> words = languages.get(languageName);
            String decrypted = breakForLanguages(encrypted,words);
            int count = 0;
            for(String word : decrypted.split("\\W")){
                if(words.contains(word.toLowerCase())){
                    count++;
                }
            }
            if(count > maxValue){
                maxValue=count;
                printLanguageName = languageName;
                printDecrypted = decrypted;
            }
        }
        System.out.println(printLanguageName);
        System.out.println(printDecrypted);
    }
    
}
