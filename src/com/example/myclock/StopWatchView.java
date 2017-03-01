package com.example.myclock;

import java.util.Calendar;
import java.util.Timer;
import java.util.TimerTask;

import android.content.Context;
import android.os.Handler;
import android.os.Message;
import android.util.AttributeSet;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Adapter;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

public class StopWatchView extends LinearLayout implements OnClickListener {

	TextView tv_stopwatch_minute, tv_stopwatch_second, tv_stopwatch_millsecond;
	ListView StopWatchlistview;
	Button btn_stopwatch_start, btn_stopwatch_pause, btn_stopwatch_reset,
			btn_stopwatch_save;
	private Timer timer;
	private TimerTask timerTask;
	private Calendar calendar;
	private ArrayAdapter<StopWatchData> adapter; 
	
	private static class StopWatchData{
		private long time;
		private Calendar calendar;
		StopWatchData(long time){
			this.time = time;
			calendar = Calendar.getInstance();
			calendar.setTimeInMillis(time);
		}
		@Override
		public String toString() {
			String str;
			int minute = calendar.get(Calendar.MINUTE);
			int second = calendar.get(Calendar.SECOND);
			int millsecond = calendar.get(Calendar.MILLISECOND);
			str = minute + ":" + second + ":" + millsecond ;
			return str;
		}
		
	}
	
	public StopWatchView(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
	}

	public StopWatchView(Context context, AttributeSet attrs) {
		super(context, attrs);
	}

	public StopWatchView(Context context) {
		super(context);
	}

	@Override
	protected void onFinishInflate() {

		tv_stopwatch_minute = (TextView) findViewById(R.id.tv_stopwatch_minute);
		tv_stopwatch_second = (TextView) findViewById(R.id.tv_stopwatch_second);
		tv_stopwatch_millsecond = (TextView) findViewById(R.id.tv_stopwatch_millsecond);
		StopWatchlistview = (ListView) findViewById(R.id.StopWatchlistview);

		btn_stopwatch_start = (Button) findViewById(R.id.btn_stopwatch_start);
		btn_stopwatch_pause = (Button) findViewById(R.id.btn_stopwatch_pause);
		btn_stopwatch_reset = (Button) findViewById(R.id.btn_stopwatch_reset);
		btn_stopwatch_save = (Button) findViewById(R.id.btn_stopwatch_save);

		btn_stopwatch_start.setOnClickListener(this);
		btn_stopwatch_pause.setOnClickListener(this);
		btn_stopwatch_reset.setOnClickListener(this);
		btn_stopwatch_save.setOnClickListener(this);
		
		adapter = new ArrayAdapter<StopWatchView.StopWatchData>(getContext(), android.R.layout.simple_list_item_1);
		StopWatchlistview.setAdapter(adapter);
		readlist();
		super.onFinishInflate();
	}

	@Override
	public void onClick(View arg0) {
		switch (arg0.getId()) {
		case R.id.btn_stopwatch_start:
			start();
			setisStartEnable(0, 1, 1, 1);
			break;
		case R.id.btn_stopwatch_save:
			save();
			setisStartEnable(0, 1, 1, 1);
			savelist();
			break;
		case R.id.btn_stopwatch_pause:
			pause();
			setisStartEnable(1, 1, 0, 1);
			break;
		case R.id.btn_stopwatch_reset:
			reset();
			setisStartEnable(1, 1, 1, 1);
			break;
		}
	}

	private Handler handler = new Handler() {
		public void handleMessage(android.os.Message msg) {
			refresh();
			start();
		};
	};
	long time;

	private void start() {
		calendar = Calendar.getInstance();
		int minute = Integer.parseInt(tv_stopwatch_minute.getText().toString());
		// System.out.println("---------------------------");
		// System.out.println("minute:"+minute);
		int second = Integer.parseInt(tv_stopwatch_second.getText().toString());
		// System.out.println("second:"+second);
		int millsecond = Integer.parseInt(tv_stopwatch_millsecond.getText()
				.toString());
		// System.out.println("millsecond:"+millsecond);

		calendar.set(Calendar.HOUR, 0);
		calendar.set(Calendar.MINUTE, minute);
		calendar.set(Calendar.SECOND, second);
		calendar.set(Calendar.MILLISECOND, millsecond);
		time = calendar.getTimeInMillis();
		// System.out.println("time:"+time);
		timer = new Timer();
		timerTask = new TimerTask() {

			@Override
			public void run() {
				time = time + 50;
				calendar.setTimeInMillis(time);
				// System.out.println("time+:"+time);
				// Message message = handler.obtainMessage();
				// message.arg1 = time;
				// handler.sendMessage(message);
				handler.sendEmptyMessage(0);
			}
		};
		timer.schedule(timerTask, 50);
	}

	private void save() {
		adapter.add(new StopWatchData(calendar.getTimeInMillis()));
	}

	private void pause() {
		timer.cancel();
	}

	private void reset() {
		timer.cancel();
		adapter.clear();
		tv_stopwatch_minute.setText(0 + "");
		tv_stopwatch_second.setText(0 + "");
		tv_stopwatch_millsecond.setText(0 + "");
	}

	private void setisStartEnable(int a, int b, int c, int d) {
		btn_stopwatch_start.setEnabled(false);
		btn_stopwatch_pause.setEnabled(false);
		btn_stopwatch_reset.setEnabled(false);
		btn_stopwatch_save.setEnabled(false);
		if (a == 1) {
			btn_stopwatch_start.setEnabled(true);
		}
		if (b == 1) {
			btn_stopwatch_save.setEnabled(true);
		}
		if (c == 1) {
			btn_stopwatch_pause.setEnabled(true);
		}
		if (d == 1) {
			btn_stopwatch_reset.setEnabled(true);
		}
	}

	private void refresh() {
		tv_stopwatch_minute.setText(calendar.get(Calendar.MINUTE) + "");
		// System.out.println("MINUTE+:"+calendar.get(Calendar.MINUTE));
		tv_stopwatch_second.setText(calendar.get(Calendar.SECOND) + "");
		// System.out.println("SECOND+:"+calendar.get(Calendar.SECOND));
		tv_stopwatch_millsecond
				.setText(calendar.get(Calendar.MILLISECOND) + "");
		// System.out.println("MILLISECOND+:"+calendar.get(Calendar.MILLISECOND));
	}

	private void savelist() {

	}
	
	private void readlist() {

	}
	
}
