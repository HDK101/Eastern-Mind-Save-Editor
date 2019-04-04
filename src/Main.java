
import java.io.*;

import com.easternmind.savemanipulator.*;


public class Main {

    public static void main(String[] args) throws IOException {
        SaveManipulator easternSave = new SaveManipulator();
        easternSave.SetFileName("input");

        easternSave.LoadFile();

        //Set item 2,3,4 availability
        easternSave.SetItem(2);
        easternSave.SetItem(3);
        easternSave.SetItem(4);

        //easternSave.SetAllItem();
        easternSave.WriteFile();

    }

}
