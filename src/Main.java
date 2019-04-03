
import java.io.*;
import com.easternmind.savemanipulator.*;


public class Main{

    public static void main(String[] args) throws IOException{
          SaveManipulator easternSave = new SaveManipulator();
          easternSave.SetFileName("input");
          easternSave.LoadFile();
          easternSave.WriteFile();

    }

}
