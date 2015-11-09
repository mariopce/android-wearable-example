package pl.saramak.notifications.notifications;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;

import com.jakewharton.rxbinding.view.RxView;

import rx.functions.Action1;

public class MainActivity extends AppCompatActivity {


    private Button mSendNotifButton;
    private NotificationSender notificationSender;
    private int notificationId;
    private int eventId;
    private Button mSendNotifNextButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });
        mSendNotifButton = (Button) findViewById(R.id.sendnotifsame);
        mSendNotifNextButton = (Button) findViewById(R.id.sendnotifnext);
        notificationSender = new NotificationSender(MainActivity.this);
        notificationId = 10;
        eventId = 001;

        RxView.clicks(mSendNotifButton).subscribe(new Action1<Void>() {
            @Override
            public void call(Void aVoid) {
                eventId ++;
                notificationId = 10;
                notificationSender.send(notificationId, eventId);
            }
        });
        RxView.clicks(mSendNotifNextButton).subscribe(new Action1<Void>() {
            @Override
            public void call(Void aVoid) {
                eventId ++;
                notificationId++;
                notificationSender.send(notificationId, eventId);
            }
        });
        RxView.clicks(findViewById(R.id.send_map_notif)).subscribe(new Action1<Void>() {
            @Override
            public void call(Void aVoid) {
                eventId ++;
                notificationId++;
                new NotificationMapSender(MainActivity.this).send(notificationId, eventId);
            }
        });
        RxView.clicks(findViewById(R.id.send_voice_replay_notif)).subscribe(new Action1<Void>() {
            @Override
            public void call(Void aVoid) {
                eventId ++;
                notificationId++;
                new NotificationVoiceSender(MainActivity.this).send(notificationId, eventId);
            }
        });
        RxView.clicks(findViewById(R.id.send_pages_notif)).subscribe(new Action1<Void>() {
            @Override
            public void call(Void aVoid) {
                eventId ++;
                notificationId++;
                new NotificationAdditionalPageSender(MainActivity.this).send(notificationId, eventId);
            }
        });

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
