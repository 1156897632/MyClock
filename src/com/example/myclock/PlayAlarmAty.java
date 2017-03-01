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
		// TODO �Զ����ɵķ������
		super.onCreate(savedInstanceState);
		setContentView(R.layout.playalarmview);
		mp = MediaPlayer.create(this, R.raw.music);
		mp.start();
		System.out.println("��������");

		findViewById(R.id.btn_closealarm).setOnClickListener(
				new View.OnClickListener() {

					@Override
					public void onClick(View arg0) {
						// TODO �Զ����ɵķ������
						if (mp.isPlaying()) {
							mp.stop();
							mp.release();
							System.out.println("�������ֱ���ť�ر�");
							// finish();
						}
					}
				});
	}

	@Override
	protected void onPause() {
		// TODO �Զ����ɵķ������
		super.onPause();
//		finish();
		// System.out.println("���ӱ��ر�");
	}

	@Override
	protected void onDestroy() {
		// TODO �Զ����ɵķ������
		super.onDestroy();
		if (mp.isPlaying()) {
			mp.stop();
			mp.release();
			System.out.println("�������ֱ�ҳ��ر�");
		}

	}

}
