package pittydsa.org.nomenclator;

import android.content.Context;
import android.database.DataSetObserver;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Adapter;
import android.widget.BaseAdapter;

import static pittydsa.org.nomenclator.Person.*;

/**
 * Created by toor on 12/19/17.
 */

public class PersonAdapter extends BaseAdapter {

    @Override
    public int getCount() {
        return people.length;
    }

    @Override
    public Object getItem(int i) {
        return people[i];
    }

    @Override
    public long getItemId(int i) {
        return (long) people[i].hashCode();
    }

    @Override
    public View getView(int i, View convertView, ViewGroup viewGroup) {
        if (convertView == null) {
        }
        return null;
    }
}
