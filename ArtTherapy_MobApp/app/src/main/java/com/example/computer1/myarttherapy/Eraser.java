package com.example.computer1.myarttherapy;

import android.app.IntentService;
import android.content.Intent;
import android.media.MediaPlayer;


public class Eraser extends IntentService {
    public Eraser() {
        super("Eraser");
    }

    protected void onHandleIntent(Intent intent) {

        MediaPlayer mediaPlayer = MediaPlayer.create(Eraser.this, R.raw.eraser);
        mediaPlayer.start();
        while(mediaPlayer.isPlaying())
        {
            System.out.println();

        }
        mediaPlayer.reset();
        mediaPlayer.release();

    }
}