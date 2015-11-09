package pl.saramak.notifications.notifications;

import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.location.Location;
import android.net.Uri;
import android.support.v4.app.NotificationCompat;
import android.support.v4.app.NotificationManagerCompat;

/**
 * Created by mario on 09.11.15.
 */
public class NotificationMapSender {
    private Context context;
    private static final String EXTRA_EVENT_ID = "EXTRA_EVENT_ID";

    public NotificationMapSender(Context context) {
        this.context = context;
    }

    public void send(int notificationId, int eventId) {
        // Build intent for notification content

        Intent mapIntent = new Intent(Intent.ACTION_VIEW);
        String location = "51.7749,19.4194";
        Uri geoUri = Uri.parse("geo:0,0?q=" + Uri.encode(location));
        mapIntent.setData(geoUri);
        PendingIntent mapPendingIntent =
                PendingIntent.getActivity(context, 0, mapIntent, 0);
        Intent viewIntent = new Intent(context, ViewEventActivity.class);
        viewIntent.putExtra(EXTRA_EVENT_ID, eventId);
        PendingIntent viewPendingIntent =
                PendingIntent.getActivity(context, 0, viewIntent, 0);
        NotificationCompat.Builder notificationBuilder = new NotificationCompat.Builder(context)
                .setSmallIcon(R.drawable.small)
                .setContentTitle("Content Title" + notificationId)
                .setContentText("Contexnt text" + eventId)
                .setContentIntent(viewPendingIntent)
                .addAction(android.R.drawable.ic_dialog_map,
                        context.getString(R.string.map), mapPendingIntent);
                ;
        // Get an instance of the NotificationManager service
        NotificationManagerCompat notificationManager =
                NotificationManagerCompat.from(context);
        // Build the notification and issues it with notification manager.
        notificationManager.notify(notificationId, notificationBuilder.build());
    }
}
