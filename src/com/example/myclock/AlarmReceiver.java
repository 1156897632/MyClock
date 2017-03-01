package com.example.myclock;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

public class AlarmReceiver extends BroadcastReceiver {
	
	
	@Override
	public void onReceive(Context arg0, Intent arg1) {
		// TODO 自动生成的方法存根
		System.out.println("~~~~~~~~~~~");
		System.out.println("闹钟执行了");
		
		AlarmManager am = (AlarmManager) arg0
				.getSystemService(Context.ALARM_SERVICE);
		am.cancel(PendingIntent.getBroadcast(arg0, getResultCode(), new Intent(
				arg0, AlarmReceiver.class), 0));
		System.out.println("code:"+AlarmView.alarmview.id);
		System.out.println("闹钟被关闭?:"+getResultCode());

		Intent intent = new Intent(arg0, PlayAlarmAty.class);
		intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
		arg0.startActivity(intent);
	}

}
