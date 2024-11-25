package word.processor.utils;

import java.nio.file.Files;
import java.nio.file.Path;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class TextState {

    //handles the states of
    private static Boolean isSaved = false; //check if the file is saved or not
    private static Path filePath; //contains the file path of the file
    private static int wordCount;
    private static int characterCount;
    private static String currentText;
    private static double fontSize;
    //for regex expresson
    private static Pattern pattern;

    public static void setFontSize(double fontSize) {
        TextState.fontSize = fontSize;
    }

    public static double getFontSize() {
        return fontSize;
    }

    public static void incrementFontSize(double size) {
        fontSize += size;
    }
    public static void clearState() {
        isSaved = false;
        filePath = null;
        wordCount = 0;
        characterCount = 0;
    }
    public static void setIsSaved(Boolean isSaved) {
        TextState.isSaved = isSaved;
    }
    public static void setFilePath(Path filePath) {
        TextState.filePath = filePath;
    }
    public static Boolean getIsSaved() {
        return isSaved;
    }
    public static Path getFilePath() {
        return filePath;
    }
    public static void setCharacterCount(int characterCount) {
        TextState.characterCount = characterCount;
    }
    public static int getCharacterCount() {
        return characterCount;
    }
    public static String getCurrentText() {
        return currentText;
    }
    public static void setWordCount(int wordCount) {
        TextState.wordCount = wordCount;
    }
    public static int getWordCount() {
        return wordCount;
    }

    public static void setCurrentText(String currentText) {
        TextState.currentText = currentText;
    }
    //save file to a new file path
    //use text filePath to save 
    public static Boolean saveTextToFile() {
        try {
            Files.writeString(filePath, (currentText == null) ? " " : currentText);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }
    //save as options
    public static Boolean saveTextToFile(Path path) {
        try {
            Files.writeString(path, (currentText == null) ? " " : currentText);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    public static Boolean readFromFile(String path) {
        try {
            filePath = Path.of(path);
            currentText = Files.readString(filePath);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    public static int findNumberOfWords(){
        Pattern wordsPattern = Pattern.compile("\\b\\w+\\b");
        Matcher matches = wordsPattern.matcher(currentText);
        int count = 0;
        while(matches.find()) {
            count += 1;;
        }
        return count;
    }

    public static int findNumberOfMatch(String regex, Boolean insensitive){
        try {
        if(insensitive)
            pattern =  Pattern.compile(regex, Pattern.CASE_INSENSITIVE);
        else 
            pattern =  Pattern.compile(regex);
        Matcher matches = pattern.matcher(currentText);
        int count = 0;
        while(matches.find()) {
            count += 1;;
        }
        return count;
    }catch (Exception ex) {
        ex.printStackTrace();
        return -1;
    }
    }

    public static int findNumberOfMatch(){
        try {
        Matcher matches = pattern.matcher(currentText);
        int count = 0;
        while(matches.find()) {
            count += 1;;
        }
        return count;
    }catch (Exception ex) {
        ex.printStackTrace();
        return -1;
    }
    }

    public static boolean replace(String replacingWord, Boolean all) {
        if(pattern == null) 
            return false;
        Matcher matches = pattern.matcher(currentText);
        if(all) {
           currentText = matches.replaceAll(replacingWord);
        } else
            currentText = matches.replaceFirst(replacingWord);
        //check if all
        
        return true;
    }
}