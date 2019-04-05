
import java.io.*;

import com.easternmind.savemanipulator.*;


public class Main {

    public static void main(String[] args) throws IOException {
        SaveManipulator easternSave = new SaveManipulator();
        easternSave.SetFileName("input");

        easternSave.LoadFile();

        //Set item 2,3,4 availability
        easternSave.SetItem(18);
        easternSave.SetItem(8);
        easternSave.SetItem(9);

        //Set character to BYOU
        easternSave.SetCharacter(1);

        //easternSave.SetAllItem();
        easternSave.WriteFile();

    }

}
