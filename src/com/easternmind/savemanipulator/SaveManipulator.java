package com.easternmind.savemanipulator;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class SaveManipulator {

    //region Item variable
    private boolean[] itemList;
    private String[] itemNames;
    //endregion

    //region File variables
    private boolean loadFailed;
    private String fileName;
    private BufferedReader bufferedReader;
    private BufferedWriter bufferedWriter;

    private String[] lines;
    //endregion

    //region Character variables
    private int currentCharacter = 0;
    private String[] characterNames;

    //endregion

    //region Location Variables

    public enum LocationList {
        GreenFace("D_FACE"),
        Market("D_MARKET"),
        Sea("D_SEA"),
        CentralMountain("E_MOUNT"),
        OctogonalShrine("E_RM1"),
        SloughInShrine("E_RM2"),
        StairsMountain("E_STAIRS"),
        ShiChiengField("F_FIELD"),
        ComputerRoom("F_KING"),
        RedTunnel("F_TONNEL"),
        TowerFirstStairs("F_TOWER1"),
        TowerSecondStairs("F_TOWER2"),
        TowerThirdStairs("F_TOWER3"),
        Boulder("G_BOULDE"),
        HelixEntrance("G_PALM"),
        HelixCastle("G_CASTLE"),
        RoomOfAppetite("G_CHIFA1"),
        RoomOfAppetiteVisible("G_CHIFA2"),
        YuiWangEntrance("G_ENTRAN"),
        GFlowerDragons("G_FLOWER"),
        KingGyou("G_KING"),
        PillarRoom("G_NONFAI"),
        YuiWangPalace("G_PALACE"),
        RoomOfImmortality("G_PURYAO"),
        GoldenFlowerHiddenPlace("G_RMBUD"),
        ShaHiddenPlace("G_RMSHA"),
        HelixRoof("G_ROOF"),
        RoomOfDesire("G_SHOWMI"),
        RoomOfGold("G_TONGSI"),
        LeftEarTunnel("G_TONNEL"),
        MingKenField("L_FIELD"),
        MingKenForest("L_FOREST"),
        InsideMokuGyou("L_INNER"),
        MokuGyou("L_KING"),
        Tabelinai("L_OGRE"),
        RightEarTunnel("L_TONNEL"),
        MokuGyouTree("L_WOOD"),
        EyeballOfDreamingPlace("L_WTOR"),
        GyouRitual("R_GYOU2"),
        RetsuCaught("R_LETSB"),
        ReincarnationPlace("R_SYSTEM"),
        ZenLife("R_ZEN2"),
        PingChaoGuardians("W_AUN"),
        SuiGyou("W_KING"),
        MonChienLakeFirstPart("W_LAKE1"),
        MonChienLakeSecondPart("W_LAKE2"),
        PingChaoPalaceFirstPart("W_PALAC1"),
        PingChaoPalaceSecondPart("W_PALAC2"),
        FangShingRoom("W_RM1"),
        PangXieRoom("W_RM2"),
        PitchBlackRoom("W_RM3"),
        VideoTongNouSealRoom("W_RM4"),
        VideoGoldenFlowerRoom("W_RM5"),
        VideoEnteringMokuGyouRoom("W_RM6"),
        VideoComputerRoom("W_RM7"),
        GuardiansRoom("W_RMAUN"),
        MagatamasDumpRoom("W_RMDUMP"),
        RockRoom("W_RMEYE"),
        IllMagatamaRoom("W_RMILL"),
        KaiRoom("W_RMKAI"),
        DragonWithMagatamaRoom("W_RMMAGA"),
        PingChaoMapRoom("W_RMMAP"),
        MingKenRoom("W_RMMIN"),
        ShaRoom("W_RMSHA"),
        YuiWangRoom("W_RMYUI"),
        MonChienTunnel("W_TONNEL");


        private String name;

        LocationList(String name) {
            this.name = name;
        }

        public String getName() {
            return name;
        }
    }

    private LocationList currentLocation;

    public enum OutMarketLocation {
        MingKenField("fBack,l_field.dxr"),
        YuiWangPalace("Back,g_Palace.dxr");


        private String name;

        OutMarketLocation(String name) {
            this.name = name;
        }

        public String getName() {
            return name;
        }
    }

    public OutMarketLocation outMarketCurrentLocation;

    //endregion

    //region Item methods

    public void SetItem(int i) {
        itemList[i] = !itemList[i];
        System.out.println("Item number " + i + ":");
        System.out.println(itemNames[i] + " possession set to " + itemList[i]);
    }

    public void SetAllItem() {
        boolean tempItemState = itemList[0];

        for (int i = 0; i < itemList.length; i++) {
            itemList[i] = !tempItemState;
        }
        System.out.println("All Items possessions set to " + itemList[0]);
    }

    private String AllItems() {
        String tempItemList;
        tempItemList = "";
        int currentItemListAsNumber;

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

    //endregion

    //region IOMethods
    public void LoadFile() throws IOException {

        //region INITIALIZE
        itemList = new boolean[53];
        String allItemNames = "Card_LIN,Card_BYOU,Card_TOH,Card_SHA,Card_KAI," +
                "Card_JIN,Card_LETS,Card_ZEN,Card_GYOU,Maga_METAL,Maga_WATER," +
                "Maga_WOOD,Maga_FIRE, Maga_EARTH,Book,Amulet,Hitogata,Peach,EyeBall," +
                "Ant,Chime,MorningGlory,Koma,Compass,Glasses,GoldIngot,SmallMallet," +
                "Panacea,Hyotan, SunWater,SheepIntestine,Chopsticks,GoldenFlower,key," +
                "SoundBox,Kane,Hue,Koto,Taiko,Wrench,DreamingEye,LeafSack_empty," +
                "LeafSack_full, WoodDisk,Registration,Mirror,Force_EARTH,Force_METAL," +
                "Force_WATER,Force_WOOD,Force_FIRE,Sword";
        itemNames = allItemNames.split(",");

        String allCharacterNames = "Rin,Byou,Tou,Sha,Kai,Jin,Retsu,Zen,Gyou";
        characterNames = allCharacterNames.split(",");
        //endregion

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
            String extractedCharacter = lines[3];
            char[] charCharacter = extractedCharacter.toCharArray();
            currentCharacter = Character.getNumericValue(charCharacter[0]);

            //Extract item list
            String extractedItemList = lines[5].replace(",", "");
            char[] charItemList = extractedItemList.toCharArray();

            //Set to itemList
            for (int i = 0; i < extractedItemList.length(); i++) {
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
            if (!loadFailed) {
                System.out.println("Save file loaded!");
            } else System.out.println("Failed to load the save, check above.");

        }
    }

    public void WriteFile() throws IOException {

        bufferedWriter = new BufferedWriter(new FileWriter(fileName + ".txt"));

        if (!loadFailed) {
            System.out.println("Beginning to write file...");

            int currentLine = 0;

            for (String line : lines) {

                currentLine++;

                if (currentLine == 6) {
                    bufferedWriter.write(AllItems());
                    bufferedWriter.newLine();
                } else if (currentLine == 4) {
                    bufferedWriter.write(String.valueOf(currentCharacter));
                    bufferedWriter.newLine();
                } else {
                    bufferedWriter.write(line);
                    bufferedWriter.newLine();
                }

            }

            System.out.println("File saved!");
        } else {
            System.out.println("The program can't write the file, because no save file was loaded!");
        }

        if (bufferedWriter != null) {
            bufferedWriter.close();
        }

    }

    public void SetFileName(String name) {
        System.out.println("Save file name:" + name + ".txt");
        fileName = name;
    }
    //endregion

    //region Character methods

    public void SetCharacter(int i) {
        currentCharacter = i;
        System.out.println("Character set to " + characterNames[i]);
    }

    //endregion

    //region Location methods

    public void SetLocation(LocationList selectedLocation) {
        if (selectedLocation != null) {
            currentLocation = selectedLocation;
            System.out.println("Location set to " + currentLocation);
        } else {
            System.out.println("Invalid location!");
        }
    }

    public void SetOutMarketLocation(OutMarketLocation selectedOutMarketLocation) {
        if (selectedOutMarketLocation != null) {
            outMarketCurrentLocation = selectedOutMarketLocation;
            System.out.println("A way to marketplace set to " + outMarketCurrentLocation);
        } else {
            System.out.println("Invalid location!");
        }
    }
    //endregion

}
