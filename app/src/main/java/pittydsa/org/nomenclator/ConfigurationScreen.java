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
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.android.volley.DefaultRetryPolicy;
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
    private Button use;

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
        use = findViewById(R.id.use);
        use.setEnabled(false);

        requestInternetPermissions();
        queue = Volley.newRequestQueue(this);

        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            private View view;

            private void callback(String response) {
                Configuration c = Configuration.parseResponse(response);

                StringBuilder msg = new StringBuilder();
                if (c.getStatus().equals("ok")) {
                    msg.append("Success: ");

                    if (c.getItem().getMessage().length() > 10)
                        msg.append(c.getItem().getMessage().substring(0, 9) + "...");
                    else
                        msg.append(c.getItem().getMessage());
                    msg.append(", to send to ");
                    msg.append(c.getItem().getPeople().length);
                    msg.append(" people.");
                }

                Snackbar.make(view, msg.toString(), Snackbar.LENGTH_LONG)
                        .setAction("Action", null)
                        .show();
                use.setEnabled(true);
            }

            private void errorCallback(String error) {
                errorText.setText(error);
                use.setEnabled(false);
                Snackbar.make(view, R.string.fetchError, Snackbar.LENGTH_LONG)
                        .setAction("Action", null)
                        .show();
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
                stringRequest.setRetryPolicy(
                        new DefaultRetryPolicy(1800, 0, 1)
                );
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
