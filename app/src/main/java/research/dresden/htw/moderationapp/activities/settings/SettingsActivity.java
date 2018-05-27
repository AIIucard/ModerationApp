package research.dresden.htw.moderationapp.activities.settings;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.EditText;

import com.github.nkzawa.socketio.client.IO;
import com.github.nkzawa.socketio.client.Socket;

import java.net.URISyntaxException;

import research.dresden.htw.moderationapp.R;
import research.dresden.htw.moderationapp.model.DataViewModel;
import research.dresden.htw.moderationapp.model.SocketSingleton;
import research.dresden.htw.moderationapp.tasks.ConnectionTask;

public class SettingsActivity  extends AppCompatActivity {

    private static DataViewModel viewModel;

    EditText inputWebSocketURIField;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);
        viewModel = ViewModelProviders.of(this).get(DataViewModel.class);
        inputWebSocketURIField = findViewById(R.id.inputWebSocketURIField);
        inputWebSocketURIField.setText(viewModel.getWebSocketURI().getValue());

        // Create the observer which updates the UI.
        final Observer<String> webSocketURIObserver = new Observer<String>() {

            @Override
            public void onChanged(@Nullable final String newURI) {
                inputWebSocketURIField.setText(newURI);
            }
        };

        // Observe the LiveData, passing in this activity as the LifecycleOwner and the observer.
        viewModel.getWebSocketURI().observe(this, webSocketURIObserver);
    }

    public void change_web_socket_uri(View v){
        viewModel.setWebSocketURI(inputWebSocketURIField.getText().toString());
        createWebSocket();
        new ConnectionTask().execute(SocketSingleton.getSocket());
    }

    private void createWebSocket() {
        try {
            Socket mSocket = IO.socket(viewModel.getWebSocketURI().getValue());

            viewModel.setSocket(mSocket);
            if(mSocket.connected()){
                Log.d("onCreate", "Connection detected!");
            }
        } catch (URISyntaxException ue) {
            Log.e("onCreate", "URISyntaxException! " + ue.getLocalizedMessage());
        }
    }

}
