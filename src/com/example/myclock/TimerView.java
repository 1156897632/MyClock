package com.example.myclock;

import java.util.Timer;
import java.util.TimerTask;

import android.content.Context;
import android.os.Handler;
import android.os.Message;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.AttributeSet;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;

public class TimerView extends LinearLayout implements OnClickListener {

	EditText et_hour, et_minute, et_second;
	Button btn_start, btn_pause, btn_reset;
	private int time = 0;
	private Timer timer;
	private TimerTask timertask;

	public TimerView(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
	}

	public TimerView(Context context, AttributeSet attrs) {
		super(context, attrs);
	}

	public TimerView(Context context) {
		super(context);
	}

	@Override
	protected void onFinishInflate() {
		super.onFinishInflate();

		et_hour = (EditText) findViewById(R.id.et_hour);
		et_hour.addTextChangedListener(new TextWatcher() {

			@Override
			public void onTextChanged(CharSequence arg0, int arg1, int arg2,
					int arg3) {
				if (!TextUtils.isEmpty(arg0)) {
					int value = Integer.parseInt(arg0.toString());
					if (value > 59) {
						et_hour.setText("59");
					} else if (value < 0) {
						et_hour.setText("0");
					}
				}
				else{
					et_hour.setText("0");
				}
				isStartEnable();
			}

			@Override
			public void beforeTextChanged(CharSequence arg0, int arg1,
					int arg2, int arg3) {
			}

			@Override
			public void afterTextChanged(Editable arg0) {
			}
		});
		et_minute = (EditText) findViewById(R.id.et_minute);
		et_minute.addTextChangedListener(new TextWatcher() {

			@Override
			public void onTextChanged(CharSequence arg0, int arg1, int arg2,
					int arg3) {
				if (!TextUtils.isEmpty(arg0)) {
					int value = Integer.parseInt(arg0.toString());
					if (value > 59) {
						et_minute.setText("59");
					} else if (value < 0) {
						et_minute.setText("0");
					}
				}
				else{
					et_minute.setText("0");
				}
				isStartEnable();
			}

			@Override
			public void beforeTextChanged(CharSequence arg0, int arg1,
					int arg2, int arg3) {
			}

			@Override
			public void afterTextChanged(Editable arg0) {
			}
		});
		et_second = (EditText) findViewById(R.id.et_second);
		et_second.addTextChangedListener(new TextWatcher() {

			@Override
			public void onTextChanged(CharSequence arg0, int arg1, int arg2,
					int arg3) {
				if (!TextUtils.isEmpty(arg0)) {
					int value = Integer.parseInt(arg0.toString());
					if (value > 59) {
						et_second.setText("59");
					} else if (value < 0) {
						et_second.setText("0");
					}
				}
				else{
					et_second.setText("0");
				}
				isStartEnable();
			}

			@Override
			public void beforeTextChanged(CharSequence arg0, int arg1,
					int arg2, int arg3) {
			}

			@Override
			public void afterTextChanged(Editable arg0) {
			}
		});

		btn_start = (Button) findViewById(R.id.btn_start);
		btn_pause = (Button) findViewById(R.id.btn_pause);
		btn_reset = (Button) findViewById(R.id.btn_reset);

		setIsVisibility(1, 0, 0);
		btn_start.setEnabled(false);
		btn_start.setOnClickListener(this);
		btn_pause.setOnClickListener(this);
		btn_reset.setOnClickListener(this);
	}

	private void setIsVisibility(int a, int b, int c) {
		btn_start.setVisibility(View.GONE);
		btn_pause.setVisibility(View.GONE);
		btn_reset.setVisibility(View.GONE);
		if (a == 1) {
			btn_start.setVisibility(View.VISIBLE);
		}
		if (b == 1) {
			btn_pause.setVisibility(View.VISIBLE);
		}
		if (c == 1) {
			btn_reset.setVisibility(View.VISIBLE);
		}
	}

	@Override
	public void onClick(View arg0) {
		switch (arg0.getId()) {
		case R.id.btn_start:
			start();
			setIsVisibility(0, 1, 1);
			break;
		case R.id.btn_pause:
			pause();
			setIsVisibility(1, 0, 1);
			break;
		case R.id.btn_reset:
			reset();
			setIsVisibility(1, 0, 0);
			break;
		}
	}

	private void isStartEnable() {
		if (Integer.parseInt(et_hour.getText().toString()) == 0
				&& Integer.parseInt(et_minute.getText().toString()) == 0
				&& Integer.parseInt(et_second.getText().toString()) == 0) {
			btn_start.setEnabled(false);
		} else {
			btn_start.setEnabled(true);
		}
	}
	
	private Handler mHandler = new Handler(){
		public void handleMessage(android.os.Message msg) {
			if(msg.arg1>=0){
				refresh();
				start();
			}
			else{
				timer.cancel();
			}
		};
	};

	private void start() {
		int hour = Integer.parseInt(et_hour.getText().toString());
		int minute = Integer.parseInt(et_minute.getText().toString());
		int second = Integer.parseInt(et_second.getText().toString());
		time = hour*60*60+minute*60+second;
		timer = new Timer();
		timertask = new TimerTask() {
			
			@Override
			public void run() {
				time--;
				Message message = mHandler.obtainMessage();
				message.arg1 = time;
				mHandler.sendMessage(message);
			}
		};
		timer.schedule(timertask, 1000);
	}

	private void pause() {
		timer.cancel();
	}

	private void reset() {
		timer.cancel();
		et_hour.setText(0+"");
		et_minute.setText(0+"");
		et_second.setText(0+"");
	}
	
	private void refresh() {
		int hour = time/60/60;
		int minute = time/60;
		int second = time%60;
		et_hour.setText(hour+"");
		et_minute.setText(minute+"");
		et_second.setText(second+"");
	}
}
