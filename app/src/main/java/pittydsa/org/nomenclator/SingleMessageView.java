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
    private Button button4;
    private TextView textView3;
    private TextView textView4;

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

    }
}
