package pl.saramak.notifications.notifications;

import android.app.Notification;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.support.v4.app.NotificationCompat;
import android.support.v4.app.NotificationManagerCompat;

/**
 * Created by mario on 09.11.15.
 */
public class NotificationAdditionalPageSender {
    private static final String EXTRA_EVENT_ID = "EXTRA_EVENT_ID";
    private Context context;

    public NotificationAdditionalPageSender(Context context) {
        this.context = context;
    }

    public void send(int notificationId, int eventId) {
        // Build intent for notification content

        Intent viewIntent = new Intent(context, ViewEventActivity.class);
        viewIntent.putExtra(EXTRA_EVENT_ID, eventId);
        PendingIntent viewPendingIntent =
                PendingIntent.getActivity(context, 0, viewIntent, PendingIntent.FLAG_UPDATE_CURRENT);
        NotificationCompat.Builder notificationBuilder = new NotificationCompat.Builder(context)
                .setSmallIcon(R.drawable.small)
                .setContentTitle("Content Title" + notificationId)
                .setContentText("Contexnt text" + eventId)
                .setContentIntent(viewPendingIntent);
        NotificationCompat.BigTextStyle s2ndPage = create2ndPage();

        // Create second page notification
        Notification secondPageNotification =
                new NotificationCompat.Builder(context)
                        .setStyle(s2ndPage)
                        .build();

        // Get an instance of the NotificationManager service
        NotificationManagerCompat notificationManager =
                NotificationManagerCompat.from(context);
        // Build the notification and issues it with notification manager.
        Notification pages = notificationBuilder.extend(new NotificationCompat.WearableExtender().addPage(secondPageNotification)).build();
        notificationManager.notify(notificationId, pages);
    }

    private NotificationCompat.BigTextStyle create2ndPage() {
        // Create a big text style for the second page
        NotificationCompat.BigTextStyle secondPageStyle = new NotificationCompat.BigTextStyle();
        secondPageStyle.setBigContentTitle("Page 2")
                .bigText("A lot of text...");
        
        return secondPageStyle;
    }
}
