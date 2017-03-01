package com.example.myclock;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

public class AlarmReceiver extends BroadcastReceiver {
	
	
	@Override
	public void onReceive(Context arg0, Intent arg1) {
		// TODO �Զ����ɵķ������
		System.out.println("~~~~~~~~~~~");
		System.out.println("����ִ����");
		
		AlarmManager am = (AlarmManager) arg0
				.getSystemService(Context.ALARM_SERVICE);
		am.cancel(PendingIntent.getBroadcast(arg0, getResultCode(), new Intent(
				arg0, AlarmReceiver.class), 0));
		System.out.println("code:"+AlarmView.alarmview.id);
		System.out.println("���ӱ��ر�?:"+getResultCode());

		Intent intent = new Intent(arg0, PlayAlarmAty.class);
		intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
		arg0.startActivity(intent);
	}

}
