package pittydsa.org.nomenclator;

import android.content.pm.PackageManager;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.telephony.SmsManager;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.ArrayList;

import static pittydsa.org.nomenclator.Person.people;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    /**
     * This method initializes 30 copies of David Ankin in Person.java and
     * puts them into the view.
     */
    public void showTestPeople(View view) {
        Person.init();
        Person[] people = Person.people;
        showPeopleInRoster(people);
    }

    public void sendAll(View view) {
        Person[] people = Person.people;
        String[] permissions = new String[] {Manifest.permission.SEND_SMS};


        if (ContextCompat.checkSelfPermission(this,
            Manifest.permission.SEND_SMS) !=
            PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, permissions, 0);
        } else {
            sendAllHavePermissions();
        }
    }

     public void sendAllHavePermissions() {
        SmsManager smsManager = SmsManager.getDefault();
         for (Person p :
                 Person.people) {
             ArrayList<String> parts = smsManager.divideMessage(p.toString());
             smsManager.sendMultipartTextMessage(p.getPhoneNumber(), null, parts, null, null);
         }
     }

    @Override
    public void onRequestPermissionsResult(int requestCode,
            String permissions[], int[] grantResults) {
        
        if (grantResults.length > 0
            && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
            sendAllHavePermissions();
        }
    }

//    private String formatMessage(Person person, String message)

    /**
     * This method is public because it is safe. It takes an array of people
     * and writes their values into newly minted "SingleMessageView"s and
     * adds them into the linearLayout.
     */
    public void showPeopleInRoster(Person[] people) {
        // find view so we know where to draw the SingleMessageView
        LinearLayout linearLayout = (LinearLayout) findViewById(R.id.roster);
        for (Person person : people) {
            View view = inflateSMV(person);
            linearLayout.addView(view);
        }        
    }

    private View inflateSMV(Person person) {
        // find view so we know how big to draw it
        LinearLayout linearLayout = (LinearLayout) findViewById(R.id.roster);
        View sMV = getLayoutInflater().inflate(R.layout.single_message_view, linearLayout, false);

        //
        Button button      = (Button) sMV.findViewById(R.id.button);
        TextView textView  = (TextView) sMV.findViewById(R.id.textView);
        TextView textView2 = (TextView) sMV.findViewById(R.id.textView2);
        TextView textView3 = (TextView) sMV.findViewById(R.id.textView3);

        // button.setText();
        textView.setText(person.getName());
        textView2.setText(person.getPhoneNumber());
        textView3.setText(person.toString());

        return sMV;
    }
}
