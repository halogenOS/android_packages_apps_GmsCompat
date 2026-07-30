package com.google.android.compat.lib.playintegrity;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.Context;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.util.Log;

/**
 * Posts the "Play Integrity API used" notification directly from the library.
 *
 * Upstream this goes through GmsCompatApp.iClientOfGmsCore2Gca(), an internal
 * framework class the ART hidden-API verifier blocks in the library's
 * classloader domain (NoSuchMethodError). Posting locally keeps the user-
 * visible signal for every client app without touching the framework.
 */
final class PiNotifier {
    private static final String TAG = "PiNotifier";
    private static final String CHANNEL_ID = "play_integrity_api";
    private static final int NOTIF_ID = 0x7069; // "pi"

    static void show(Context ctx, boolean isBlocked) {
        try {
            NotificationManager nm = ctx.getSystemService(NotificationManager.class);
            if (nm == null) {
                return;
            }
            NotificationChannel channel = nm.getNotificationChannel(CHANNEL_ID);
            if (channel == null) {
                channel = new NotificationChannel(CHANNEL_ID, "Play Integrity API",
                        NotificationManager.IMPORTANCE_DEFAULT);
                channel.setDescription("Shown when an app uses the Play Integrity API");
                nm.createNotificationChannel(channel);
            }

            PackageManager pm = ctx.getPackageManager();
            ApplicationInfo ai = ctx.getApplicationInfo();
            CharSequence label = ai != null ? ai.loadLabel(pm) : ctx.getPackageName();

            String text = isBlocked
                    ? label + " tried to use the Play Integrity API (blocked)"
                    : label + " used the Play Integrity API";

            Notification.Builder b = new Notification.Builder(ctx, CHANNEL_ID)
                    .setContentTitle("Play Integrity API")
                    .setContentText(text)
                    .setSmallIcon(android.R.drawable.ic_dialog_info)
                    .setAutoCancel(true);
            nm.notify(NOTIF_ID, b.build());
        } catch (Throwable t) {
            // Notification must never break the integrity flow itself.
            Log.e(TAG, "failed to post notification", t);
        }
    }
}
