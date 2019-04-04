package com.easternmind.savemanipulator;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class SaveManipulator {
    public boolean loadFailed;
    public boolean[] itemList;

    public String[] lines;

    public String fileName;

    private BufferedReader bufferedReader;
    private BufferedWriter bufferedWriter;

    public void SetFileName(String name){
        System.out.println("Save file name:" + name + ".txt");
        fileName = name;
    }



    //Item methods
    //
    //

    public void SetItem(int i){
        itemList[i] = !itemList[i];
        System.out.println("Item " + i + " possession set to " + itemList[i]);
    }

    public void SetAllItem(){
        for(int i = 0; i < itemList.length; i++){
           itemList[i] = !itemList[0];
        }
        System.out.println("All Items possessions set to " + itemList[0]);
    }

    public void LoadFile() throws IOException {

        itemList = new boolean[53];

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
            char[] charItemList = extractedItemList.toCharArray();

            //Set to itemList
            for (int i = 0; i < extractedItemList.length(); i++){
                int number = Character.getNumericValue(charItemList[i]);

                if (number == 0) itemList[i] = false;
                else if (number == 1) itemList[i] = true;
            }


        } catch (IOException ex) {
            System.out.println("Eastern Mind save file not found: +" + ex.getMessage());
            loadFailed = true;

        } catch (ArrayIndexOutOfBoundsException ex) {
            System.out.println("Probably not an Eastern Mind save file, Line " + ex.getMessage() + " not found!");
            loadFailed = true;

        } finally {
            if (bufferedReader != null) {
                bufferedReader.close();
            }
            if(!loadFailed) {
                System.out.println("Save file loaded!");
            }
            else System.out.println("Failed to load the save, check above.");

        }
    }

    public void WriteFile() throws IOException {

        bufferedWriter = new BufferedWriter(new FileWriter(fileName + ".txt"));

        if (!loadFailed) {
            System.out.println("Beginning to write file...");

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

    public String AllItems() {
        String tempItemList = new String();
        int currentItemListAsNumber = 0;

        for (int i = 0; i < 52; i++) {
            currentItemListAsNumber = itemList[i] ? 1 : 0;

            if (i == 0) {
                tempItemList += currentItemListAsNumber;
            } else {
                tempItemList += "," + currentItemListAsNumber;
            }
        }

        return tempItemList;
    }
}
