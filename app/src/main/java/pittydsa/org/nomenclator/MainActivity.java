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

        // ArrayAdapter<Person> adapter;
        // adapter = new ArrayAdapter<>(this, R.layout.activity_main, R.id.scrollView,
        //        Person.people);

        // ScrollView sv = findViewById(R.id.scrollView);
        Person.init();
        Person[] people = Person.people;
        // LinearLayout linearLayout = findViewById(R.id.roster);

        //

        LinearLayout linearLayout = (LinearLayout) findViewById(R.id.roster);
        View layout2 = getLayoutInflater().inflate(R.layout.single_message_view, linearLayout, false);

        Button button = (Button) layout2.findViewById(R.id.button);
        TextView textView  = (TextView) layout2.findViewById(R.id.textView);
        TextView textView2 = (TextView) layout2.findViewById(R.id.textView2);

        // button.setText();
        textView.setText(people[0].getName());
        textView2.setText(people[0].getPhoneNumber());

        linearLayout.addView(layout2);
    }
}
