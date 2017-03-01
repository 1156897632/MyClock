package com.example.myclock;

import java.text.BreakIterator;
import java.util.Calendar;

import android.app.AlarmManager;
import android.app.AlertDialog;
import android.app.PendingIntent;
import android.app.TimePickerDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.util.AttributeSet;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemLongClickListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TimePicker;

public class AlarmView extends LinearLayout {
	
	Button btnAddAlarm, btnDeleteAlarm;
	ListView alarmlistview;
	private ArrayAdapter<alarmData> adapter;
	private static final String KEY_ALARMLIST = "alarmlist";
	private AlarmManager alarmManager;
	public static AlarmView alarmview;
	public static int id;
	
	public AlarmView(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
		alarmview = this;
	}

	public AlarmView(Context context, AttributeSet attrs) {
		super(context, attrs);
		alarmview = this;
	}

	public AlarmView(Context context) {
		super(context);
		alarmview = this;
	}

	@Override
	protected void onFinishInflate() {
		// TODO 自动生成的方法存根
		super.onFinishInflate();

		btnAddAlarm = (Button) findViewById(R.id.btnAddAlarm);
		btnDeleteAlarm = (Button) findViewById(R.id.btnDeleteAlarm);
		btnDeleteAlarm.setVisibility(View.GONE);
		alarmlistview = (ListView) findViewById(R.id.Alarmlistview);
		alarmManager = (AlarmManager) getContext().getSystemService(
				Context.ALARM_SERVICE);

		adapter = new ArrayAdapter<AlarmView.alarmData>(getContext(),
				android.R.layout.simple_list_item_1);
		alarmlistview.setAdapter(adapter);
		// adapter.add(new alarmData(50));
		readAlarmList();

		btnAddAlarm.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				// TODO 自动生成的方法存根
				addAlarm();
			}
		});

		alarmlistview.setOnItemLongClickListener(new OnItemLongClickListener() {

			@Override
			public boolean onItemLongClick(AdapterView<?> adapter,
					View alarmData, final int arg2, long arg3) {
				// System.out.println("~~~~~~~~~~~~");
				new AlertDialog.Builder(getContext())
						.setTitle("操作选项")
						.setItems(new CharSequence[] { "删除" },
								new AlertDialog.OnClickListener() {

									@Override
									public void onClick(DialogInterface arg0,
											int arg1) {
										// TODO 自动生成的方法存根
										switch (arg1) {
										case 0:
											deleteAlarm(arg2);
											break;
										}
									}
								}).setNegativeButton("取消", null).show();
				return true;
			}
		});
	}

	private void addAlarm() {
		Calendar c = Calendar.getInstance();
		new TimePickerDialog(getContext(),
				new TimePickerDialog.OnTimeSetListener() {

					@Override
					public void onTimeSet(TimePicker arg0, int hour, int minute) {
						Calendar calendar = Calendar.getInstance();
						calendar.set(Calendar.HOUR, hour);
						calendar.set(Calendar.MINUTE, minute);
						calendar.set(Calendar.SECOND, 0);
						calendar.set(Calendar.MILLISECOND, 0);
						alarmData data = new alarmData(calendar
								.getTimeInMillis());

						Calendar currentTime = Calendar.getInstance();
						if (calendar.getTimeInMillis() < currentTime
								.getTimeInMillis()) {
							calendar.setTimeInMillis(calendar.getTimeInMillis()
									+ 24 * 60 * 60 * 1000);
						}
						alarmManager.setRepeating(AlarmManager.RTC_WAKEUP,
								calendar.getTimeInMillis(), 1 * 60 * 1000,
								PendingIntent.getBroadcast(getContext(), data
										.getid(), new Intent(getContext(),
										AlarmReceiver.class), 0));
						System.out.println("添加闹钟，id："+data.getid());
						adapter.add(data);
						saveAlarmList();
					}
				}, c.get(Calendar.HOUR), c.get(Calendar.MINUTE), true).show();
	}

	private void saveAlarmList() {
		Editor editor = (Editor) getContext().getSharedPreferences(
				AlarmView.class.getName(), Context.MODE_PRIVATE).edit();
		StringBuffer sb = new StringBuffer();
		if (adapter.getCount() != 0) {
			for (int n = 0; n < adapter.getCount(); n++) {
				sb.append(adapter.getItem(n).getTime()).append(",");
			}
			String content = sb.toString().substring(0, sb.length() - 1);

			editor.putString(KEY_ALARMLIST, content);
		} else {
			editor.putString(KEY_ALARMLIST, null);
		}
		editor.commit();
	}

	private void readAlarmList() {
		SharedPreferences sp = getContext().getSharedPreferences(
				AlarmView.class.getName(), Context.MODE_PRIVATE);
		String content = sp.getString(KEY_ALARMLIST, null);
		if (content != null) {
			String[] str = content.split(",");
			for (String string : str) {
				adapter.add(new alarmData(Long.parseLong(string)));
			}
		}
	}

	private void deleteAlarm(int n) {
		AlarmManager am = (AlarmManager) getContext().getSystemService(
				Context.ALARM_SERVICE);
		am.cancel(PendingIntent.getBroadcast(getContext(), adapter.getItem(n)
				.getid(), new Intent(getContext(), AlarmReceiver.class), 0));
		System.out.println("闹钟被取消，id："+adapter.getItem(n).getid());
		adapter.remove(adapter.getItem(n));
		saveAlarmList();
	}

	private static class alarmData {
		private long time;
		private Calendar c;

		public long getTime() {
			return time;
		}

		private int getid() {
			id = (int) (time / 60/60);
			return id;
		}

		public alarmData(long time) {
			this.time = time;
			c = Calendar.getInstance();
			c.setTimeInMillis(time);
		}

		@Override
		public String toString() {
			// TODO 自动生成的方法存根
			String string = "";
			long month = c.get(Calendar.MONTH) + 1;
			long day = c.get(Calendar.DAY_OF_MONTH);
			long hour = c.get(Calendar.HOUR);
			long minute = c.get(Calendar.MINUTE);
			string = month + "月" + day + "日  " + hour + ":" + minute;
			return string;
		}
	}
}
