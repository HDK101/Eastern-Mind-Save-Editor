
import java.io.*;
import java.nio.*;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.List;

class EasternSaveFileManipulation {
    public boolean loaded;
    public boolean[] itemList;

    public String[] lines;

    public String fileName;

    private BufferedReader bufferedReader;
    private BufferedWriter bufferedWriter;

    public void SetFileName(String name){
        System.out.println("Save file name:" + name + ".txt");
        fileName = name;
    }

    public void SetItem(int i){
        itemList[i] = !itemList[i];
        System.out.println("Item " + i + " possession set to " + itemList[i]);
    }

    public void LoadFile() throws IOException {

        itemList = new boolean[53];
        SetItem(2);

        try {
            bufferedReader = new BufferedReader(new FileReader(fileName + ".txt"));

            List<String> lineList = new ArrayList();
            String line;

            while ((line = bufferedReader.readLine()) != null) {
                lineList.add(line);
            }
            lines = new String[lineList.size()];
            lines = lineList.toArray(lines);

            //Extract item list
            String extractedItemList = lines[5].replace(",","");
            System.out.println(extractedItemList);
            char[] charItemList = extractedItemList.toCharArray();
            //Set to itemList
            for (int i = 0; i < extractedItemList.length(); i++){
                int number = Character.getNumericValue(charItemList[i]);

                if (number == 0) itemList[i] = false;
                else if (number == 1) itemList[i] = true;

                System.out.println(itemList[i]);
            }


        } catch (IOException ex) {
            System.out.printf("Eastern Mind save file not found: %s%n", ex.getMessage());

        } finally {
            if (bufferedReader != null) {
                bufferedReader.close();
            }
            loaded = true;

            System.out.println("Save file loaded!");
        }
    }

    public void WriteFile() throws IOException {
        System.out.println("Beginning to write file...");

        bufferedWriter = new BufferedWriter(new FileWriter(fileName + ".txt"));

        if (loaded) {

            int currentLine = 0;

            for (String line : lines) {

                currentLine++;

                if (currentLine != 6) {
                    bufferedWriter.write(line);
                    bufferedWriter.newLine();
                } else {
                    bufferedWriter.write(AllItems());
                    bufferedWriter.newLine();
                }

            }

            System.out.println("File saved!");
        }
        else {
             System.out.println("The program can't write the file, because no save file was loaded!");
        }

        if(bufferedWriter != null){
            bufferedWriter.close();
        }

    }

    public static String AllItems() {
        String tempItemList = new String();

        for (int i = 0; i < 52; i++) {
            if (i == 0) {
                tempItemList += "1";
            } else {
                tempItemList += ",1";
            }
        }

        return tempItemList;
    }
}

public class Main extends EasternSaveFileManipulation{

    public static void main(String[] args) throws IOException{
          Main easternSave = new Main();
          easternSave.SetFileName("input");
          easternSave.LoadFile();
          easternSave.WriteFile();

    }

}
