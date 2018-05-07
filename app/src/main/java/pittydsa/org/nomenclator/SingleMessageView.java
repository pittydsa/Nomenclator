package pittydsa.org.nomenclator;

import android.app.Activity;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.AsyncTask;
import android.telephony.SmsManager;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * Created by toor on 12/19/17.
 */

public class SingleMessageView extends RelativeLayout {
    private Button button;
    private TextView textView;
    private TextView textView2;
    private TextView textView3;
    private Person person;
    private AsyncTask<Person, Void, String> sendMessage;
    private Context context;

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
        context = getContext();
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
            sendMessage.execute(person);
        }
    }

    private class SendMessage extends AsyncTask<Person, Void, String> {
        private static final String TAG = "ok";
        private static final String SENT_ACTION = "sent_action_identifier";
        private boolean done = false;
        private boolean success = false;
        private BroadcastReceiver sentReceiver;
        private SmsManager sms = SmsManager.getDefault();
        ArrayList<String> parts;

        @Override
        protected void onPreExecute() {
            registerReciever(person);
        }

        private void registerReciever(Person person) {
            parts = sms.divideMessage(person.toString());
            final AtomicInteger msgParts = new AtomicInteger(parts.size());

            sentReceiver = new BroadcastReceiver() {
                @Override
                public void onReceive(Context context, Intent intent) {
                    Log.d(TAG, "SMS onReceive intent received.");
                    boolean anyError = false;
                    switch (getResultCode()) {
                        case Activity.RESULT_OK:
                            break;
                        case SmsManager.RESULT_ERROR_GENERIC_FAILURE:
                        case SmsManager.RESULT_ERROR_NO_SERVICE:
                        case SmsManager.RESULT_ERROR_NULL_PDU:
                        case SmsManager.RESULT_ERROR_RADIO_OFF:
                            anyError = true;
                            break;
                    }
                    msgParts.getAndDecrement();
                    if (msgParts.get() == 0) {
                        done = true;
                        success = !anyError;
                    }
                }
            };
        }
        private void send(Context context, Person person) {
            // Send the SMS message
            context.registerReceiver(sentReceiver, new IntentFilter(SENT_ACTION));

            ArrayList<PendingIntent> sentIntents = new ArrayList<>();
            int numParts = parts.size();
            for (int i = 0; i < numParts; i++) {
                sentIntents.add(PendingIntent.getBroadcast(context, 0,
                        new Intent(SENT_ACTION), 0));
            }

            sms.sendMultipartTextMessage(
                    person.getPhoneNumber(), null, parts, sentIntents, null);
        }

        @Override
        protected String doInBackground(Person... people) {
            send(context, people[0]);
            try {
                while (!done)
                    Thread.sleep(250);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            return null;
        }
        @Override
        protected void onPostExecute(String result) {
            context.unregisterReceiver(sentReceiver);
            button.setText(success ? "Sent" : "Error");
        }
    }
}
