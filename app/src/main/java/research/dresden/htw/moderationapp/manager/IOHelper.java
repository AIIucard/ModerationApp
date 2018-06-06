package research.dresden.htw.moderationapp.manager;

import android.content.Context;
import android.util.Log;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;

class IOHelper {

    @SuppressWarnings("ResultOfMethodCallIgnored")
    public static String readStringFromFile(Context context, String fileName) {
        try {
            FileInputStream fis = context.openFileInput(fileName);
            InputStreamReader isr = new InputStreamReader(fis);
            char[] inputBuffer = new char[fis.available()];
            isr.read(inputBuffer);
            String data = new String(inputBuffer);
            isr.close();
            fis.close();
            Log.d("readStringFromJSONFile", "Read data: " + data + " from File: " + fileName);
            return data;
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
            Log.d("writeStringToJSONFile", "Wrote data: " + data + " to File: " + fileName);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
