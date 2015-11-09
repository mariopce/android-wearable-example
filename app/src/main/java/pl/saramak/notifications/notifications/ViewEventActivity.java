package pl.saramak.notifications.notifications;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.RemoteInput;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.TextView;

public class ViewEventActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_event);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        TextView textView = (TextView)findViewById(R.id.status);
        textView.setText(getMessageText(getIntent()) + " event " + getEventNumber(getIntent()));
    }

    private String getEventNumber(Intent intent) {
        return "EVENTID = " +intent.getIntExtra(NotificationVoiceSender.EXTRA_EVENT_ID, -1);
    }

    private CharSequence getMessageText(Intent intent) {
        Bundle remoteInput = RemoteInput.getResultsFromIntent(intent);
        if (remoteInput != null) {
            return remoteInput.getCharSequence(NotificationVoiceSender.EXTRA_VOICE_REPLY);
        }
        return null;
    }

}
