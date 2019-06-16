package com.easternmind.savemanipulator;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.io.File;
import java.math.*;

public class SaveManipulator {

    //region Global Variable
    private static SaveManipulator self;

    public static SaveManipulator instance() {
        if (self == null) {
            self = new SaveManipulator();
        }
        return self;
    }

    public void Reset() {
        self = new SaveManipulator();
    }
    //endregion

    //region Item variable
    public boolean[] itemList;
    public String[] itemNames;
    //endregion

    //region File variables
    public boolean usingTemplate;
    private boolean fileLoaded;

    public boolean checkIfFileIsLoaded() {
        return fileLoaded;
    }

    private String fileName;
    private BufferedReader bufferedReader;
    private BufferedWriter bufferedWriter;

    private String[] lines;
    //endregion

    //region Character variables
    private int currentCharacter = 0;
    public int GetCurrentCharacterId(){
        return currentCharacter;
    }
    public String[] characterNames;

    //endregion

    //region Tong Nou Seal variables
    public boolean sealsRemoved;
    //endregion

    //region Location Variables

    public int currentFrame;

    public enum LocationList {

        //
        //
        //    Tong Nou main area
        //
        //
        Sea("D_SEA"),
        GreenFace("D_FACE"),
        LeftEarTunnel("G_TONNEL"),
        RightEarTunnel("L_TONNEL"),
        Boulder("G_BOULDE"),

        //
        //
        //    Tong Nou Central Mountain
        //
        //

        CentralMountain("E_MOUNT"),
        OctogonalShrine("E_RM1"),
        SloughInShrine("E_RM2"),
        StairsMountain("E_STAIRS"),
        ShiChiengField("F_FIELD"),

        //
        //
        //    Shi Chieng
        //
        //
        ComputerRoom("F_KING"),
        RedTunnel("F_TONNEL"),
        TowerFirstStairs("F_TOWER1"),
        TowerSecondStairs("F_TOWER2"),
        TowerThirdStairs("F_TOWER3"),

        //
        //
        //    Helix place
        //
        //
        HelixEntrance("G_PALM"),
        HelixCastle("G_CASTLE"),
        RoomOfAppetite("G_CHIFA1"),
        RoomOfAppetiteVisible("G_CHIFA2"),
        YuiWangEntrance("G_ENTRAN"),
        GFlowerDragons("G_FLOWER"),
        KingGyou("G_KING"),
        PillarRoom("G_NONFAI"),
        RoomOfImmortality("G_PURYAO"),

        //
        //
        //    Yui Wang
        //
        //
        YuiWangPalace("G_PALACE"),
        GoldenFlowerHiddenPlace("G_RMBUD"),
        ShaHiddenPlace("G_RMSHA"),
        HelixRoof("G_ROOF"),
        RoomOfDesire("G_SHOWMI"),
        RoomOfGold("G_TONGSI"),

        //
        //
        //    Ming Ken
        //
        //
        MingKenField("L_FIELD"),
        MingKenForest("L_FOREST"),
        InsideMokuGyou("L_INNER"),
        MokuGyou("L_KING"),
        Tabelinai("L_OGRE"),
        MokuGyouTree("L_WOOD"),
        EyeballOfDreamingPlace("L_WTOR"),
        GyouRitual("R_GYOU2"),
        RetsuCaught("R_LETSB"),

        //
        //
        //       Mon Chien
        //
        //
        ZenLife("R_ZEN2"),
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
        MonChienTunnel("W_TONNEL"),
        PingChaoGuardians("W_AUN"),
        SuiGyou("W_KING"),

        //
        //
        //Unique
        //
        //
        Market("D_MARKET"),
        ReincarnationPlace("R_SYSTEM");


        private String name;

        LocationList(String name) {
            this.name = name;
        }

        public String getFile() {
            return name + ".dxr";
        }
    }
    private LocationList[] locationValues;
    public LocationList[] GetLocationValues(){
        if(locationValues == null){
            locationValues = LocationList.values();
        }
        return locationValues;
    }

    private LocationList currentLocation;
    public LocationList GetCurrentLocation(){
        return currentLocation;
    }
    private boolean isCustomLocation;
    private String customLocation;

    public enum OutLocation {
        GreenFace("d_face"),

        MingKen("l_field"),

        YuiWang("g_Palace"),

        MonChien("w_Lake1"),

        RockRoom("W_RMEYE"),

        MokuGyouTree("l_Wood"),

        ShiChieng("f_field");


        private String name;

        OutLocation(String name) {
            this.name = name;
        }

        public String getFile() {
            return name + ".dxr";
        }

        public String getName() {
            return name;
        }

    }

    //Location for parameter in line 7.
    public OutLocation outLocation;

    public enum Parameter {
        fBack("fBack"),
        Back("back"),
        Mount("Mount"),
        Right("right'"),
        Sixteen("016'"),
        YFive("Y5'");
        private String name;

        Parameter(String name) {
            this.name = name;
        }

        public String getParameter() {
            return name;
        }
    }

    //Parameter for line 7
    public Parameter currentParameter;

    //Floor for Helix-Place
    public int currentHelixFloor;

    //endregion

    //region Item methods

    public void SetItem(int i,boolean value) {
        itemList[i] = value;
        System.out.println("Item number " + i + ":");
        System.out.println(itemNames[i] + " possession set to " + value);
        System.out.println();
    }

    public void SetAllItem(boolean possession) {

        for (int i = 0; i < itemList.length; i++) {
            itemList[i] = possession;
        }
        System.out.println("All Items possessions set to " + possession);
        System.out.println();
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
    public void LoadFile() throws IOException, InvalidEasternMindFileException {

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
        System.out.println(itemNames.length);

        String allCharacterNames = "Rin,Byou,Tou,Sha,Kai,Jin,Retsu,Zen,Gyou";
        characterNames = allCharacterNames.split(",");
        //endregion

        try {
            if (!usingTemplate) {
                bufferedReader = new BufferedReader(new FileReader(String.format("%s", fileName)));
            } else bufferedReader = new BufferedReader(new FileReader("resources\\savetemplate\\SAVEGAME.txt"));

            List<String> lineList = new ArrayList();
            String line;

            while ((line = bufferedReader.readLine()) != null) {
                lineList.add(line);
            }
            lines = new String[lineList.size()];
            lines = lineList.toArray(lines);

            CheckSaveAuthenticity(lines[0]);

            //Extract current character from file
            String extractedCharacter = lines[3];
            char[] charCharacter = extractedCharacter.toCharArray();
            currentCharacter = Character.getNumericValue(charCharacter[0]);

            //Extract item list from file
            String extractedItemList = lines[5].replace(",", "");
            char[] charItemList = extractedItemList.toCharArray();

            //Set to boolean array itemList
            for (int i = 0; i < extractedItemList.length(); i++) {
                int number = Character.getNumericValue(charItemList[i]);

                if (number == 0) itemList[i] = false;
                else if (number == 1) itemList[i] = true;
            }
            //Extract current location from file
            String extractedLocation = lines[2];
            extractedLocation = extractedLocation.replace(GetGamePath(), "");
            extractedLocation = extractedLocation.replace(".dxr", "");
            currentLocation = SetLocationFromString(extractedLocation);
            if (currentLocation == null) {
                System.out.println("Not an usual location, setting as a custom: " + extractedLocation);
                isCustomLocation = true;
                customLocation = extractedLocation;
            }

            //Extract seals variable
            String sealLine = lines[12];
            sealsRemoved = sealLine == "1";

            //Extract current floor in Helix Place/ secondary location
            String lineSix = lines[6];
            if (lineSix != null) {
                if (LineSixIsNumber(lineSix)) {
                    currentHelixFloor = Integer.parseInt(lineSix);
                    System.out.println("Extracted current floor for Helix:" + currentHelixFloor);

                } else {
                    String[] parameterAndPlace = lineSix.split(",");
                    currentParameter = SetParameterFromString(parameterAndPlace[0]);
                    outLocation = SetOutLocationFromString(parameterAndPlace[1]);

                }
            }

            fileLoaded = true;

        } catch (IOException ex) {
            System.out.println("Eastern Mind save file not found: +" + ex.getMessage());
            fileLoaded = false;

        } catch (ArrayIndexOutOfBoundsException ex) {
            System.out.println("Probably not an Eastern Mind save file, Line " + ex.getMessage() + " out of range!");
            fileLoaded = false;

        } catch (InvalidEasternMindFileException ex) {
            System.out.println("Probably not an Eastern Mind save file, First Line: " + ex.saveIdentifierLine() + " Not found!");
        } finally {
            if (bufferedReader != null) {
                bufferedReader.close();
            }
            if (fileLoaded) {
                System.out.println("Save file loaded!");
            } else System.out.println("Failed to load the save, check above.");

        }
        System.out.println();
    }

    public void WriteFile() throws IOException {

        if (fileLoaded) {
            bufferedWriter = new BufferedWriter(new FileWriter(fileName));
            System.out.println("Beginning to write file...");

            int currentLine = 0;
            //
            //Eastern Mind requires Macintosh Format for save-files, use .write("\r") instead of .newLine(), which is platform-dependant
            //

            for (String line : lines) {

                currentLine++;

                //Write itemList to file
                if (currentLine == 6) {
                    bufferedWriter.write(AllItems());
                    bufferedWriter.write("\r");
                }
                //Write currentCharacter to file
                else if (currentLine == 4) {
                    bufferedWriter.write(String.valueOf(currentCharacter));
                    bufferedWriter.write("\r");
                }
                //Write currentLocation
                else if (currentLine == 3) {
                    if (!isCustomLocation) bufferedWriter.write(GetGamePath() + currentLocation.getFile());
                    else bufferedWriter.write(GetGamePath() + customLocation + ".dxr");
                    bufferedWriter.write("\r");
                }
                //Write currentFrame
                else if (currentLine == 5) {
                    bufferedWriter.write(String.valueOf(currentFrame));
                    bufferedWriter.write("\r");
                } else if (currentLine == 7) {
                    if (currentParameter != null)
                        bufferedWriter.write(currentParameter.getParameter() + "," + outLocation.getFile());
                    else if (currentHelixFloor != 0) bufferedWriter.write(String.valueOf(currentHelixFloor));
                    else bufferedWriter.write("");
                    bufferedWriter.write("\r");
                } else if (currentLine == 13) {
                    bufferedWriter.write(GetSealValue());
                    bufferedWriter.write("\r");
                } else {
                    bufferedWriter.write(line);
                    bufferedWriter.write("\r");
                }

            }

            System.out.println("File saved!");
        } else {
            System.out.println("The program can't write the file, because no save file was loaded!");
        }

        if (bufferedWriter != null) {
            bufferedWriter.close();
        }
        System.out.println();
    }

    public void CheckSaveAuthenticity(String line) throws InvalidEasternMindFileException {
        if (!line.equals("***Tong Nou DATA file***")) {
            throw new InvalidEasternMindFileException();
        }
    }

    public void SetFileName(String name) {
        File tempFile = new File(name);
        if (tempFile.exists()) {
            System.out.println("Save file name:" + name);
            System.out.println();
        }
        fileName = name;
    }

    public String GetGamePath() {
        //In Line 2, it is stored the place(.dxr)
        if (lines[2] != null) {
            File path = new File(lines[2]);
            System.out.println("Game path:");
            System.out.println(path.getParent());
            System.out.println();
            return path.getParent() + '\\';
        } else {
            return null;
        }
    }
    //endregion

    //region Character methods

    public void SetCharacter(int i) {
        currentCharacter = i;
        System.out.println("Character set to " + characterNames[i]);
        System.out.println();
    }

    //endregion

    //region Location methods

    public void SetLocation(LocationList selectedLocation) {
        if (selectedLocation != null) {
            currentLocation = selectedLocation;
            System.out.println("Location set to " + currentLocation);
            if (selectedLocation == LocationList.CentralMountain || selectedLocation == LocationList.Market) {
                System.out.println("Warning! Requires outLocation and parameter to be set! Otherwise the game will crash!");
            }
            isCustomLocation = false;
        } else {
            System.out.println("Invalid location!");
        }
        System.out.println();

    }

    public void SetOutLocation(OutLocation selectedOutLocation) {
        if (selectedOutLocation != null) {
            //Special case for Shi Chieng.
            if (currentLocation == LocationList.CentralMountain) {
                if (selectedOutLocation == OutLocation.MingKen) {
                    System.out.println("Special case, Setting to MokuGyouTree and parameter to 016'");
                    currentParameter = Parameter.Sixteen;
                    outLocation = OutLocation.MokuGyouTree;

                } else if (selectedOutLocation == OutLocation.ShiChieng) {
                    currentParameter = Parameter.Mount;
                    outLocation = selectedOutLocation;

                } else if (selectedOutLocation == OutLocation.YuiWang) {
                    currentParameter = Parameter.YFive;
                    outLocation = selectedOutLocation;

                } else if (selectedOutLocation == OutLocation.MonChien) {
                    outLocation = OutLocation.MonChien;
                    currentParameter = Parameter.Back;

                } else if (selectedOutLocation == OutLocation.GreenFace) {
                    outLocation = OutLocation.GreenFace;
                    currentParameter = Parameter.Back;

                } else {
                    System.out.println("Illegal location for Central Mountain: " + selectedOutLocation);
                }
                /*

                OLD CODE

                else if(selectedOutLocation != OutLocation.ShiChiengField && selectedParameter == Parameter.Mount){
                    System.out.println("Illegal parameter for Central Mountain! Mount parameter is exclusive for Shi Chieng");
                    currentParameter = Parameter.Back;
                    outLocation = selectedOutLocation;

                }
                else if(selectedOutLocation != OutLocation.MingKenField && selectedParameter == Parameter.Sixteen){
                    System.out.println("Illegal parameter for Central Mountain! 016' parameter is exclusive for MingKenField(MokuGyouTree)!");
                    currentParameter = Parameter.Back;
                    outLocation = selectedOutLocation;

                }
                else{
                    currentParameter = selectedParameter;
                    outLocation = selectedOutLocation;
                }
                */
            }
            if (currentLocation == LocationList.Market) {
                if (selectedOutLocation == OutLocation.RockRoom) {
                    currentParameter = Parameter.Right;
                    outLocation = selectedOutLocation;
                } else if (selectedOutLocation == OutLocation.MingKen) {
                    currentParameter = Parameter.fBack;
                    outLocation = selectedOutLocation;
                } else if (selectedOutLocation == OutLocation.YuiWang) {
                    currentParameter = Parameter.Back;
                    outLocation = selectedOutLocation;
                } else if (selectedOutLocation == OutLocation.MonChien) {
                    System.out.println("Special case, Setting to RockRoom and parameter to Right");
                    currentParameter = Parameter.Right;
                    outLocation = OutLocation.RockRoom;
                } else {
                    System.out.println("Illegal location for Market: " + selectedOutLocation);
                }
            }
            if (outLocation != null && currentParameter != null) {
                System.out.println("A way out set to " + outLocation);
                System.out.println("Parameter " + currentParameter);
            }
        } else {
            System.out.println("Invalid location!");
        }
        System.out.println();
    }

    public int GetLocationID() {
        int tempID = 0;
        for (LocationList temp : GetLocationValues()) {
            if (currentLocation.name().equals(temp.name())) {
                System.out.printf("Place ID:%d%n", tempID);
                System.out.println();
                break;
            }
            tempID++;
        }
        return tempID;
    }

    public int GetParameterID(){
        int tempID = 0;
        for (Parameter temp : Parameter.values()) {
            if (currentParameter.name().equals(temp.name())) {
                System.out.printf("Parameter ID:%d%n", tempID);
                System.out.println();
                break;
            }
            tempID++;
        }
        return tempID;
    }

    public String[] GetLocations(){
        List<String> tempListLocations = new ArrayList<>();
        for (LocationList temp : GetLocationValues()) {
            tempListLocations.add(temp.name());
        }
        return tempListLocations.toArray(new String[tempListLocations.size()]);
    }

    public boolean LocationIsMarketOrMountain(){
        if(GetCurrentLocation() == LocationList.CentralMountain || GetCurrentLocation() == LocationList.Market){
            return true;
        }
        return false;
    }

    public LocationList SetLocationFromString(String value) {
        LocationList tempLocationList = null;
        for (LocationList temp : GetLocationValues()) {
            if (value.toUpperCase().equals(temp.name)) {
                System.out.printf("Extracted place: %s(%s)%n", temp, temp.getFile());
                System.out.println();
                tempLocationList = temp;
            }
        }
        return tempLocationList;
    }

    public void SetLocationFromID(int value) {
        int tempID = 0;
        for (LocationList temp : GetLocationValues()) {
            if (value == tempID) {
                System.out.println("Location set to " + GetLocationValues()[value]);
                System.out.println();
                currentLocation = GetLocationValues()[value];
                break;
            }
            tempID++;
        }
    }

    public Parameter SetParameterFromString(String value) {
        Parameter tempParameter = Parameter.Back;
        for (Parameter temp : Parameter.values()) {
            if (value.equals(temp.name)) {
                System.out.printf("Extracted parameter: %s(%s)%n", temp, temp.getParameter());
                System.out.println();
                tempParameter = temp;
            }
        }
        return tempParameter;
    }

    public String[] GetParameters(){
        List<String> parametersList = new ArrayList<>();
        for(Parameter temp : Parameter.values()){
            parametersList.add(temp.name());
        }
        return parametersList.toArray(new String[parametersList.size()]);
    }

    public OutLocation SetOutLocationFromString(String value) {
        OutLocation tempOutLocation = OutLocation.MonChien;
        for (OutLocation temp : OutLocation.values()) {
            if (value.toUpperCase().equals(temp.getFile().toUpperCase())) {
                System.out.printf("Extracted secondary location: %s(%s)%n", temp, temp.getName());
                System.out.println();
                tempOutLocation = temp;
            }
        }
        return tempOutLocation;
    }

    public boolean LineSixIsNumber(String value) {
        try {
            Integer.parseInt(value);
            return true;
        } catch (NumberFormatException ex) {
            System.out.println("Line 6 doesn't hold a value, probably holds a parameter and a secondary location");
            System.out.println();
            return false;
        }
    }

    public void SetFrame(int frame) {
        currentFrame = frame;
        System.out.println("Frame set to " + frame);
        System.out.println();
    }

    public void SetHelixFloor(int floor) {
        if (floor < 1 && floor > 18) {
            System.out.println("Invalid floor for Helix-Place");
            System.out.println();
            currentHelixFloor = 0;
        } else {
            System.out.println("Floor for Helix-Place set to " + floor);
            System.out.println();
            currentHelixFloor = floor;
        }
    }
    //endregion

    //region Seals method
    public void SetTongNouSeal(boolean remove) {
        sealsRemoved = remove;
        System.out.println("Seals set to " + remove);
        System.out.println();
    }

    char GetSealValue() {
        return (sealsRemoved) ? '1' : ' ';
    }
    //endregion

}
