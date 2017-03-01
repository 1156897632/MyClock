package com.example.myclock;

import android.os.Bundle;
import android.app.Activity;
import android.view.Menu;
import android.widget.TabHost;

public class MainActivity extends Activity {
	
	TabHost tabtHost;
	
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        
        tabtHost = (TabHost) findViewById(android.R.id.tabhost);
        tabtHost.setup();
        
        tabtHost.addTab(tabtHost.newTabSpec("tabTime").setIndicator("ʱ��").setContent(R.id.tabTime));
        tabtHost.addTab(tabtHost.newTabSpec("tabAlarm").setIndicator("����").setContent(R.id.tabAlarm));
        tabtHost.addTab(tabtHost.newTabSpec("tavTimer").setIndicator("��ʱ��").setContent(R.id.tabTimer));
        tabtHost.addTab(tabtHost.newTabSpec("tavStopWatch").setIndicator("���").setContent(R.id.tabStopWatch));
    }

    
}
