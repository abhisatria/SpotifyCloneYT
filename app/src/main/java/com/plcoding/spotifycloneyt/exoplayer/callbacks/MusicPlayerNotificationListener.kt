package com.plcoding.spotifycloneyt.exoplayer.callbacks

import android.app.Notification
import android.content.Intent
import androidx.core.content.ContextCompat
import com.google.android.exoplayer2.ui.PlayerNotificationManager
import com.plcoding.spotifycloneyt.exoplayer.MusicService
import com.plcoding.spotifycloneyt.other.Constants.NOTIFICATION_ID

class MusicPlayerNotificationListener (
    private val musicService: MusicService
) : PlayerNotificationManager.NotificationListener{

    override fun onNotificationCancelled(notificationId: Int, dismissedByUser: Boolean) {
        super.onNotificationCancelled(notificationId, dismissedByUser)
        musicService.apply {
            stopForeground(true)
            isForeGroundService = false;
            stopSelf()
        }
    }

    override fun onNotificationPosted(
        notificationId: Int,
        notification: Notification,
        ongoing: Boolean
    ) {
        super.onNotificationPosted(notificationId, notification, ongoing)
        musicService.apply {
            if(ongoing && !isForeGroundService){
                ContextCompat.startForegroundService(
                    this,
                    Intent(applicationContext,this::class.java)
                )
                startForeground(NOTIFICATION_ID,notification)
                isForeGroundService = true
            }
        }
    }
}