package com.dogtim.audiorepeatrun;

import android.app.Activity;
import android.content.Context;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.media.Ringtone;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

public class MainActivity extends Activity {
	private static final String TAG = MainActivity.class.getSimpleName();
	/**
	 * Called when the activity is first created.
	 */
	private MediaPlayer mMediaPlayer = new MediaPlayer();

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		prepareMediaPlayer();
		//prepareRingtone()
		setButtons();
	}

	private void prepareMediaPlayer() {
		Uri alert = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_ALARM);
		if (alert == null) {
			alert = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
		}
		if (alert == null) {
			alert = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_RINGTONE);
		}

		try {
			mMediaPlayer = new MediaPlayer();
			mMediaPlayer.setDataSource(this, alert);
			Log.e(TAG, "setDataSource ");

			final AudioManager audioManager = (AudioManager) getSystemService(Context.AUDIO_SERVICE);
			Log.e(TAG, "audioManager.getStreamVolume(AudioManager.STREAM_RING) " + audioManager.getStreamVolume(AudioManager.STREAM_ALARM));
			if (audioManager.getStreamVolume(AudioManager.STREAM_ALARM) != 0) {

				mMediaPlayer.setAudioStreamType(AudioManager.STREAM_ALARM);
				mMediaPlayer.setLooping(true);
				mMediaPlayer.prepare();
			}
		} catch (
				Exception e
				)

		{
			Log.e(TAG, "prepareMediaPlayer " + e.getLocalizedMessage());
		}

	}

	private void prepareRingtone() {
		Uri alert = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_ALARM);

		if (alert == null) {
			// alert is null, using backup
			alert = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);

			// I can't see this ever being null (as always have a default notification)
			// but just incase
			if (alert == null) {
				// alert backup is null, using 2nd backup
				alert = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_RINGTONE);
			}
		}

		Ringtone r = RingtoneManager.getRingtone(getApplicationContext(), alert);

	}

	private void setButtons() {
		findViewById(R.id.btn_start_audio).setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				mMediaPlayer.start();

			}
		});

		findViewById(R.id.btn_stop_audio).setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				mMediaPlayer.pause();
			}
		});
	}
}
