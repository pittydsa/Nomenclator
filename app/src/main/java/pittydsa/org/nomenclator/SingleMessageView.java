package pittydsa.org.nomenclator;

import android.content.Context;
import android.os.AsyncTask;
import android.telephony.SmsManager;
import android.util.AttributeSet;
import android.view.View;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.TextView;

/**
 * Created by toor on 12/19/17.
 */

public class SingleMessageView extends RelativeLayout {
    private Button button;
    private TextView textView;
    private TextView textView2;
    private TextView textView3;
    private Person person;
    private AsyncTask<Object, Void, String> sendMessage;

    public SingleMessageView(Context context) {
        super(context);
    }

    public SingleMessageView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public SingleMessageView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }

    public void init(Person person) {
        button = findViewById(R.id.button3);
        textView = findViewById(R.id.textView);
        textView2 = findViewById(R.id.textView2);
        textView3 = findViewById(R.id.textView3);
        sendMessage = new SendMessage();

        button.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                sendSingleMessage();
            }
        });

        this.person = person;
        textView.setText(person.getName());
        textView2.setText(person.getPhoneNumber());
        textView3.setText(person.toString());
    }

    public AsyncTask.Status getStatus() {
        return sendMessage.getStatus();
    }

    public void sendSingleMessage() {
        if (getStatus() == AsyncTask.Status.PENDING) {
            button.setEnabled(false);
            sendMessage.execute();
        }
    }

    private class SendMessage extends AsyncTask<Object, Void, String> {
        @Override
        protected String doInBackground(Object... strings) {
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            return null;
        }
        @Override
        protected void onPostExecute(String result) {
            button.setText("Sent");
        }
    }
}
