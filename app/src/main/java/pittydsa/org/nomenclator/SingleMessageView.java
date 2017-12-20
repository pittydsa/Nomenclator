package pittydsa.org.nomenclator;

import android.content.Context;
import android.support.constraint.ConstraintLayout;
import android.util.AttributeSet;
import android.widget.Button;
import android.widget.TextView;

/**
 * Created by toor on 12/19/17.
 */

public class SingleMessageView extends ConstraintLayout {
    private Button button;
    private TextView textView;
    private TextView textView2;

    public SingleMessageView(Context context) {
        super(context);
        init();
    }

    public SingleMessageView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public SingleMessageView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        init();
    }

    private void init() {
        inflate(getContext(), R.layout.single_message_view, this);
        button = findViewById(R.id.button3);
        textView = findViewById(R.id.textView);
        textView2 = findViewById(R.id.textView2);
    }

    public void setPersonDetail(Person person) {
        button.setText(person.getName());
        textView.setText(person.getPhoneNumber());
    }
}
