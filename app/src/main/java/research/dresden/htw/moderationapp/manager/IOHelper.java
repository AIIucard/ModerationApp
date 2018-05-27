package research.dresden.htw.moderationapp.manager;

import android.content.Context;
import android.content.res.AssetManager;
import android.util.Log;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;

/**
 * Created by melardev on 5/21/2017.
 */

public class IOHelper {

    public static String readStreamFromFile(Context context, String fileName) {
        try {
            FileInputStream fis = context.openFileInput(fileName);
            InputStreamReader isr = new InputStreamReader(fis);
            char[] inputBuffer = new char[fis.available()];
            isr.read(inputBuffer);
            String data = new String(inputBuffer);
            isr.close();
            fis.close();
            Log.d("readFromXMLFile", "Read data: " + data + " from File: " + fileName);
            return data;
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static void writeStringToFile(Context context, String fileName, String data) {
        try {
            FileOutputStream fos = context.openFileOutput(fileName, Context.MODE_PRIVATE);
            OutputStreamWriter osw = new OutputStreamWriter(fos);
            osw.write(data);
            osw.flush();
            osw.close();
            fos.close();
            Log.d("writeStringToFile", "Wrote data: " + data + " to File: " + fileName);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
