package pittydsa.org.nomenclator;

import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

public class ConfigurationScreen extends AppCompatActivity {

    private RequestQueue queue;
    private EditText listId;
    private EditText password;
    private TextView errorText;

    private String getURL() {
        return "http://ankin.info:3035/config?id=" + listId.getText();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_configuration_screen);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        listId = findViewById(R.id.listId);
        password = findViewById(R.id.password);
        errorText = findViewById(R.id.errorText);

        requestInternetPermissions();
        queue = Volley.newRequestQueue(this);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            private View view;

            private void callback(String response) {
                String msg = "Here's a Snackbar" + response;
                Snackbar.make(view, msg, Snackbar.LENGTH_LONG)
                        .setAction("Action", null)
                        .show();
            }

            private void errorCallback(String error) {
                errorText.setText(error);
            }

            @Override
            public void onClick(View view) {
                final View finalView = view;
                this.view = view;
                StringRequest stringRequest = new StringRequest(
                        Request.Method.GET, getURL(),
                        new Response.Listener<String>() {
                            @Override
                            public void onResponse(String response) {
                                callback(response);
                            }
                        }, new Response.ErrorListener() {
                            @Override
                            public void onErrorResponse(VolleyError error) {
                                errorCallback(error.getMessage());
                            }
                        });
                queue.add(stringRequest);
            }
        });
    }

    private void requestInternetPermissions() {
        String[] permissions = new String[] {Manifest.permission.INTERNET};
        if (ContextCompat.checkSelfPermission(this,
                Manifest.permission.INTERNET) !=
                PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, permissions, 0);
        }
    }
}
