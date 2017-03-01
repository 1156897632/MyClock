package com.example.myclock;

import android.app.Activity;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class PlayAlarmAty extends Activity {

	private MediaPlayer mp;

	// private Button btn_closealarm;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO 自动生成的方法存根
		super.onCreate(savedInstanceState);
		setContentView(R.layout.playalarmview);
		mp = MediaPlayer.create(this, R.raw.music);
		mp.start();
		System.out.println("闹钟启动");

		findViewById(R.id.btn_closealarm).setOnClickListener(
				new View.OnClickListener() {

					@Override
					public void onClick(View arg0) {
						// TODO 自动生成的方法存根
						if (mp.isPlaying()) {
							mp.stop();
							mp.release();
							System.out.println("闹钟音乐被按钮关闭");
							// finish();
						}
					}
				});
	}

	@Override
	protected void onPause() {
		// TODO 自动生成的方法存根
		super.onPause();
//		finish();
		// System.out.println("闹钟被关闭");
	}

	@Override
	protected void onDestroy() {
		// TODO 自动生成的方法存根
		super.onDestroy();
		if (mp.isPlaying()) {
			mp.stop();
			mp.release();
			System.out.println("闹钟音乐被页面关闭");
		}

	}

}
