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
        
        tabtHost.addTab(tabtHost.newTabSpec("tabTime").setIndicator("时钟").setContent(R.id.tabTime));
        tabtHost.addTab(tabtHost.newTabSpec("tabAlarm").setIndicator("闹钟").setContent(R.id.tabAlarm));
        tabtHost.addTab(tabtHost.newTabSpec("tavTimer").setIndicator("计时器").setContent(R.id.tabTimer));
        tabtHost.addTab(tabtHost.newTabSpec("tavStopWatch").setIndicator("秒表").setContent(R.id.tabStopWatch));
    }

    
}
