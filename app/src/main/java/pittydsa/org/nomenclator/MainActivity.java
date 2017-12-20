package pittydsa.org.nomenclator;

import android.database.DataSetObserver;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ListAdapter;
import android.widget.ScrollView;
import android.widget.TextView;

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
        
    }

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

        // button.setText();
        textView.setText(people[0].getName());
        textView2.setText(people[0].getPhoneNumber());

        return sMV;
    }
}
