package research.dresden.htw.moderationapp;

import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.Socket;

public class AddKeywordActivity extends AppCompatActivity {

    EditText e1;
    private static Socket s;
    private static PrintWriter printWriter;
    String message = "";
    private static String ip = "141.56.235.59"; //Achtung eigene IP-Adresse verwenden

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_keyword);

        e1 = findViewById(R.id.editText);
    }

    public void send_text(View v) {
        message = e1.getText().toString();

        AddKeywordActivity.myTask mt = new AddKeywordActivity.myTask();
        mt.execute();

        Toast.makeText(getApplicationContext(), "Data send", Toast.LENGTH_LONG).show();
    }

    class myTask extends AsyncTask<Void, Void, Void> {

        @Override
        protected Void doInBackground(Void... voids) {
            try {
                s = new Socket(ip, 5000);       // connect to the socket at port 50000
                printWriter = new PrintWriter(s.getOutputStream()); // set the output stream
                printWriter.write(message);         // send the message through the socket
                printWriter.flush();
                printWriter.close();
                s.close();


            } catch (IOException e) {
                e.printStackTrace();
            }

            return null;
        }
    }

    /*@Override
    public void onWindowFocusChanged(boolean hasFocus) {
        super.onWindowFocusChanged(hasFocus);
        if (hasFocus) {
            getWindow().getDecorView().setSystemUiVisibility(
                    View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                            | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                            | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                            | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                            | View.SYSTEM_UI_FLAG_FULLSCREEN
                            | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY);
        }
    }*/
}
