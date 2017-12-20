package pittydsa.org.nomenclator;

import android.database.DataSetObserver;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListAdapter;
import android.widget.ScrollView;

import static pittydsa.org.nomenclator.Person.people;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ArrayAdapter<Person> adapter;
        adapter = new ArrayAdapter<>(this, R.layout.activity_main, R.id.scrollView,
                Person.people);

        ScrollView sv = findViewById(R.id.scrollView);

    }
}
