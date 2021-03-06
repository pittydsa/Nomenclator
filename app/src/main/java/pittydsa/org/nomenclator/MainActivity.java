package pittydsa.org.nomenclator;

import android.content.DialogInterface;
import android.content.pm.PackageManager;
import android.os.AsyncTask;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;

public class MainActivity extends AppCompatActivity {

    private LinearLayout linearLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        linearLayout = (LinearLayout) findViewById(R.id.roster);

        // get permissions here
        requestSMSPermissions();

        Object o = getIntent().getExtras().get(ConfigurationScreen.class.toString());
        System.out.println(o.toString());
        Configuration c = (Configuration) o;
        Configuration.Person people[] = c.getPeople();
        Person.message1 = c.getMessage1();
        Person.message2 = c.getMessage2();
        Person.people = new Person[people.length];
        for (int i = 0; i < people.length; i++) {
            Person.people[i] = new Person(people[i].getName(), people[i].getPhone());
        }
    }

    private void requestSMSPermissions() {
        String[] permissions = new String[] {Manifest.permission.SEND_SMS};
        if (ContextCompat.checkSelfPermission(this,
                Manifest.permission.SEND_SMS) !=
                PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, permissions, 0);
        }
    }

    /**
     * The Crowd button callback.
     *
     * This method contains the logic for getting the people from the people
     * class into the view if the ones there are all same status. otherwise,
     * ask for confirmation.
     */
    public void showTestPeople(View view) {
        if (linearLayout == null)
            linearLayout = (LinearLayout) findViewById(R.id.roster);

        if (linearLayout.getChildCount() == 0 || allSameStatus()) {
            replacePeople();
        } else {
            AlertDialog.Builder builder = new AlertDialog.Builder(this);

            builder.setMessage("Some of these messages are sent, still proceed?");
            builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int id) {
                    replacePeople();
                    dialog.dismiss();
                }
            });
            builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int id) {
                    dialog.cancel();
                }
            });
            builder.show();
        }
    }

    private void replacePeople() {
        Person.init();
        linearLayout.removeAllViews();
        showPeopleInRoster(Person.people);
        Button button = (Button) findViewById(R.id.button);
        button.setText(R.string.replace);
    }

    private boolean allSameStatus() {
        SingleMessageView singleMessageView, other;
        int count = linearLayout.getChildCount();

        singleMessageView = (SingleMessageView) linearLayout.getChildAt(0);
        AsyncTask.Status status = singleMessageView.getStatus();
        for (int i = 1; i < count; i++) {
            other = (SingleMessageView) linearLayout.getChildAt(i);
            AsyncTask.Status status1 = other.getStatus();
            if (!status.equals(status1))
                return false;
        }
        return true;
    }

    public void sendAll(View view) {
        try {
            sendAllHavePermissions();
        } catch (RuntimeException security) {
            requestSMSPermissions();
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode,
            String permissions[], int[] grantResults) {
        System.out.println("Got Permission!");
    }

    public void sendAllHavePermissions() {
        if (linearLayout == null)
            linearLayout = (LinearLayout) findViewById(R.id.roster);

        int noPeople = linearLayout.getChildCount();
        for (int i = 0; i < noPeople; i++) {
            SingleMessageView s = (SingleMessageView) linearLayout.getChildAt(i);
            s.sendSingleMessage();
        }
    }

    /**
     * This method is public because it is safe. It takes an array of people
     * and writes their values into newly minted "SingleMessageView"s and
     * adds them into the linearLayout.
     */
    public void showPeopleInRoster(Person[] people) {
        if (linearLayout == null)
            linearLayout = (LinearLayout) findViewById(R.id.roster);

        for (Person person : people) {
            View view = inflateSMV(person);
            linearLayout.addView(view);
        }        
    }

    private View inflateSMV(Person person) {
        if (linearLayout == null)
            linearLayout = (LinearLayout) findViewById(R.id.roster);

        SingleMessageView sMV = (SingleMessageView) getLayoutInflater()
                .inflate(R.layout.single_message_view, linearLayout, false);
        sMV.init(person);
        return sMV;
    }
}
