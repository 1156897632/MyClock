package com.example.myclock;

import java.util.Calendar;

import android.content.Context;
import android.os.Handler;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

public class TimeView extends LinearLayout {
	
	TextView textView;
	
	private Handler handler = new Handler(){
		public void handleMessage(android.os.Message msg) {
			refreshTime();
			if(getVisibility() == View.VISIBLE){
				handler.sendEmptyMessageDelayed(0, 1000);
			}
		};
	};
	
	public TimeView(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
		
	}

	public TimeView(Context context, AttributeSet attrs) {
		super(context, attrs);
		
	}

	public TimeView(Context context) {
		super(context);
		
	}
	
	@Override
	protected void onFinishInflate() {
		super.onFinishInflate();
		textView = (TextView) findViewById(R.id.tvtime);
		textView.setText("Hello");
		textView.setGravity(Gravity.CENTER);
	}
	
	@Override
	protected void onVisibilityChanged(View changedView, int visibility) {
		super.onVisibilityChanged(changedView, visibility);
		if(visibility == View.VISIBLE){
			handler.sendEmptyMessage(0);
		}
		else{
			handler.removeMessages(0);
		}
	}
	private void refreshTime() {
		Calendar c = Calendar.getInstance();
		
		int hour = c.get(Calendar.HOUR);
		int minute = c.get(Calendar.MINUTE);
		int second = c.get(Calendar.SECOND);
		String string = hour + ":" + minute + ":" + second;
		
		textView.setText(string);
	}
	
}
