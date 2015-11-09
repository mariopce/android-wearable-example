package pl.saramak.notifications.notifications;

import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.support.v4.app.NotificationCompat;
import android.support.v4.app.NotificationManagerCompat;
import android.support.v4.app.RemoteInput;
import android.util.Log;

/**
 * Created by mario on 09.11.15.
 */
public class NotificationVoiceSender {
    private static final String TAG = "NotificationVoiceSender";
    private Context context;
    public static final String EXTRA_EVENT_ID = "EXTRA_EVENT_ID";

    public NotificationVoiceSender(Context context) {
        this.context = context;
    }

    public static final String EXTRA_VOICE_REPLY = "extra_voice_reply";

    public void send(int notificationId, int eventId) {
        // Build intent for notification content

        PendingIntent mapPendingIntent = createMapPendingIntent();


        Intent viewIntent = new Intent(context, ViewEventActivity.class);
        viewIntent.putExtra(EXTRA_EVENT_ID, eventId);
        PendingIntent viewPendingIntent =
                PendingIntent.getActivity(context, 0, viewIntent, PendingIntent.FLAG_UPDATE_CURRENT);
        // Key for the string that's delivered in the replayVoiceAction's intent
        String replyLabel = context.getResources().getString(R.string.speak_label);
        String[] replyChoices = context.getResources().getStringArray(R.array.reply_choices);
        Log.d(TAG, "replyChoices " + replyChoices.length);
        RemoteInput remoteInput = new RemoteInput.Builder(EXTRA_VOICE_REPLY)
                .setLabel(replyLabel)
                .setChoices(replyChoices)
                .build();
        // Create the reply replayVoiceAction and add the remote input
        NotificationCompat.Action replayVoiceAction =
                new NotificationCompat.Action.Builder(android.R.drawable.ic_btn_speak_now,
                        context.getResources().getString(R.string.speak_label), viewPendingIntent)
                        .addRemoteInput(remoteInput)
                        .build();

        NotificationCompat.Builder notificationBuilder = new NotificationCompat.Builder(context)
                .setSmallIcon(R.drawable.small)
                .setContentTitle("Content Title" + notificationId)
                .setContentText("Contexnt text" + eventId)
                .setContentIntent(viewPendingIntent)
                .extend(new NotificationCompat.WearableExtender().addAction(replayVoiceAction))
                .addAction(android.R.drawable.ic_dialog_map,
                        context.getString(R.string.map), mapPendingIntent)

                ;
        // Get an instance of the NotificationManager service
        NotificationManagerCompat notificationManager =
                NotificationManagerCompat.from(context);
        // Build the notification and issues it with notification manager.
        notificationManager.notify(notificationId, notificationBuilder.build());
    }

    private PendingIntent createMapPendingIntent() {
        Intent mapIntent = new Intent(Intent.ACTION_VIEW);
        String location = "51.7749,19.4194";
        Uri geoUri = Uri.parse("geo:0,0?q=" + Uri.encode(location));
        mapIntent.setData(geoUri);
        return PendingIntent.getActivity(context, 0, mapIntent, 0);
    }
}
