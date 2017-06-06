package com.xzr.La.systemtoolbox;

import android.app.*;
import android.content.*;
import android.os.*;
import android.view.*;
import android.widget.*;
import android.widget.AdapterView.*;
import java.util.*;
import android.util.*;
import java.io.*;

public class cpu extends Activity
{
	String[] temp=new String[100];
	String[] tempb=new String[100];
	String[] action=new String[100];
	String[] info=new String[100];
	int count=0;
	EditText e1;
	EditText e2;
	String cmd;
	EditText e3;
	EditText e4;
	EditText e5;
	EditText e6;
	EditText e7;
	EditText e8;
	TextView t1;
	TextView t2;
	TextView t3;
	TextView t4;
	boolean inited;
	AlertDialog dia;
	ProgressBar p1;
	boolean fs;
	CheckBox c1;
	CheckBox c2;
	SharedPreferences sp;
	SharedPreferences.Editor se;
	LinearLayout l1;
	String ppp;
	@Override
    public boolean onOptionsItemSelected(MenuItem item) {
    	switch (item.getItemId()) {
			case android.R.id.home:
				finish();
				break;

			default:
				break;
		}
    	return super.onOptionsItemSelected(item);
    }
	@Override
    protected void onCreate(Bundle savedInstanceState)
    {

	
		
		
        super.onCreate(savedInstanceState);
        setContentView(R.layout.cpu);
	    getActionBar().setDisplayHomeAsUpEnabled(true);
		e1=(EditText)findViewById(R.id.cpuEditText1);
		e2=(EditText)findViewById(R.id.cpuEditText2);
		e3=(EditText)findViewById(R.id.cpuEditText3);
		e4=(EditText)findViewById(R.id.cpuEditText4);
		e5=(EditText)findViewById(R.id.cpuEditText5);
		e6=(EditText)findViewById(R.id.cpuEditText6);
		e7=(EditText)findViewById(R.id.cpuEditText7);
		e8=(EditText)findViewById(R.id.cpuEditText8);
		t1=(TextView)findViewById(R.id.cpuTextView1);
		t2=(TextView)findViewById(R.id.cpuTextView2);
		t3=(TextView)findViewById(R.id.cpuTextView3);
		t4=(TextView)findViewById(R.id.cpuTextView4);
		p1=(ProgressBar)findViewById(R.id.cpuProgressBar1);
		l1=(LinearLayout)findViewById(R.id.cpuLinearLayout1);
		c1=(CheckBox)findViewById(R.id.cpuCheckBox1);
		c1.setVisibility(View.GONE);
		c2=(CheckBox)findViewById(R.id.cpuCheckBox2);
		sp=getSharedPreferences("main",0);
		ppp=sp.getString("store_path",null);
		se=sp.edit();
		fs=sp.getBoolean("firstcpu",true);
		if(sp.getBoolean("loadboot",false)){
			c1.setChecked(true);
		}
		if(sp.getBoolean("wkboot",false)){
			c2.setChecked(true);
		}
		new Thread(new init()).start();
		se.putBoolean("firstcpu",false);
		showtemp();
		
}
public void bfwk(View v){
	cmd="cp -f /system/etc/thermal-engine.conf "+ppp+"/thermal-engine.conf";
	su();
}
public void restore(View v){
	if(new File(""+ppp+"/thermal-engine.conf").exists()){
	cmd="cp -f "+ppp+"/thermal-engine.conf /system/etc/thermal-engine.conf";
		su();
		}
		else{
			Toast.makeText(getApplicationContext(),"你尚未备份",Toast.LENGTH_SHORT).show();
		}
}
public void write(View v){
	try
	{
		java.lang.Process p=Runtime.getRuntime().exec("su");
		OutputStreamWriter o=new OutputStreamWriter( p.getOutputStream());
		BufferedReader b=new BufferedReader(new InputStreamReader(p.getInputStream()));
		o.write("mount -o rw,remount /system\n");
		o.flush();
		o.write("echo [cpu_monitor] > /system/etc/thermal-engine.conf\n");
		o.write("echo algo_type monitor >> /system/etc/thermal-engine.conf\n");
		o.write("echo sensor tsens_tz_sensor0 >> /system/etc/thermal-engine.conf\n");
		o.write("echo sampling 1000 >> /system/etc/thermal-engine.conf\n");
		int empty=1;
		//获取空组
		while (true){
			if(temp[empty]==null){
				break;
			}
			else{
				empty++;
			}
		}
		String temp1="";
		String tempb1="";
		String action1="";
		String info1="";
		for(int x=1;x<empty;x++){
			temp1=temp1+"  "+temp[x];
			tempb1=tempb1+"  "+tempb[x];
			action1=action1+"  "+action[x];
			info1=info1+"  "+info[x];


		}
		o.write("echo 'thresholds  "+temp1+"' >> /system/etc/thermal-engine.conf\n");
		o.write("echo 'thresholds_clr  "+tempb1+"' >> /system/etc/thermal-engine.conf\n");
		o.write("echo 'actions  "+action1+"' >> /system/etc/thermal-engine.conf\n");
		o.write("echo 'action_info  "+info1+"' >> /system/etc/thermal-engine.conf\n");
		o.flush();
		o.write("mount -o ro,remount /system\necho Success\n");
		o.flush();
		Toast.makeText(getApplicationContext(),b.readLine(),Toast.LENGTH_SHORT).show();

	}
	catch (IOException e)
	{}
}
public void xr(View v){
     int empty=1;
	 temp[0]="2333333";
	 //获取空组
	 while (true){
		 if(temp[empty]==null){
			 break;
		 }
		 else{
			 empty++;
		 }
	 }
	 
	 String temp1=e5.getText().toString();
	 String tempb1=e6.getText().toString();
	 String action1=e7.getText().toString();
	 String info1=e8.getText().toString();
	if((temp[empty-1]).equals(temp1)){
		action[empty-1]=action[empty-1]+"+"+action1;
		info[empty-1]=info[empty-1]+"+"+info1;
	}
	else{
	 temp[empty]=temp1;
	 tempb[empty]=tempb1;
	 action[empty]=action1;
	 info[empty]=info1;
	 
	 }
	showtemp();
}
public void showtemp(){
	int empty=1;
	//获取空组
	while (true){
		if(temp[empty]==null){
			break;
		}
		else{
			empty++;
		}
	}
	
	t1.setText("起始温度 ");
	t2.setText("还原温度 ");
	t3.setText("动作    ");
	t4.setText("动作参数    ");
	
for(int x=1;x<empty;x++){
	t1.append("  "+temp[x]);
	t2.append("  "+tempb[x]);
	t3.append("  "+action[x]);
	t4.append("  "+info[x]);
	
	
}
	
}
public void onboot(View v){
	if(c1.isChecked()){
	try
	{
		se.putBoolean("loadboot",true);
		se.commit();
		java.lang.Process p=Runtime.getRuntime().exec("su");
		OutputStreamWriter o=new OutputStreamWriter(p.getOutputStream());
		BufferedReader b=new BufferedReader(new InputStreamReader(p.getInputStream()));
		o.write("mount -o rw,remount /system\n");
		o.flush();
		o.write("echo '#!/system/bin/sh' > /system/etc/init.d/00Lacpu\n");
		o.flush();
		
		o.write("echo 'echo "+e1.getText().toString()+" > /proc/sys/kernel/sched_upmigrate' >> /system/etc/init.d/00Lacpu\n");
		o.write("echo 'echo "+e2.getText().toString()+" > /proc/sys/kernel/sched_downmigrate' >> /system/etc/init.d/00Lacpu\n");
		o.write("echo 'echo "+e3.getText().toString()+" > /proc/sys/kernel/sched_spill_nr_run' >> /system/etc/init.d/00Lacpu\n");
		o.write("echo 'echo "+e4.getText().toString()+" > /proc/sys/kernel/sched_spill_load' >> /system/etc/init.d/00Lacpu\n");
		

		o.flush();
		o.write("chmod 755 /system/etc/init.d/00Lacpu\n mount -o ro,remount /system\necho Success\n");
		o.flush();

		String sk=b.readLine();
		Toast.makeText(getApplicationContext(),sk,Toast.LENGTH_SHORT).show();



	}
	catch (IOException e)
	{}
	}
	else{
		try
		{
			se.putBoolean("loadboot",false);
			se.commit();
			java.lang.Process p=Runtime.getRuntime().exec("su");
			OutputStreamWriter o=new OutputStreamWriter( p.getOutputStream());
			BufferedReader b=new BufferedReader(new InputStreamReader(p.getInputStream()));
			o.write("mount -o rw,remount /system\n");
			o.flush();
			o.write("rm -f /system/etc/init.d/00Lacpu\n");
			o.flush();
			o.write("mount -o ro,remount /system\necho Success\n");
			o.flush();
			Toast.makeText(getApplicationContext(),b.readLine(),Toast.LENGTH_SHORT).show();

		}
		catch (IOException e)
		{}
	}
}
	public void wkboot(View v){
		if(c2.isChecked()){
			try
			{
				se.putBoolean("wkboot",true);
				se.commit();
				java.lang.Process p=Runtime.getRuntime().exec("su");
				OutputStreamWriter o=new OutputStreamWriter(p.getOutputStream());
				BufferedReader b=new BufferedReader(new InputStreamReader(p.getInputStream()));
				o.write("mount -o rw,remount /system\n");
				o.flush();
				o.write("echo '#!/system/bin/sh' > /system/etc/init.d/00Lacputemp\n");
				o.flush();

				o.write("echo stop thermald >> /system/etc/init.d/00Lacputemp\n");
				o.write("echo stop thermal-engine >> /system/etc/init.d/00Lacputemp\n");

				o.flush();
				o.write("chmod 755 /system/etc/init.d/00Lacpu\n mount -o ro,remount /system\necho Success\n");
				o.flush();

				String sk=b.readLine();
				Toast.makeText(getApplicationContext(),sk,Toast.LENGTH_SHORT).show();



			}
			catch (IOException e)
			{}
		}
		else{
			try
			{
				se.putBoolean("wkboot",false);
				se.commit();
				java.lang.Process p=Runtime.getRuntime().exec("su");
				OutputStreamWriter o=new OutputStreamWriter( p.getOutputStream());
				BufferedReader b=new BufferedReader(new InputStreamReader(p.getInputStream()));
				o.write("mount -o rw,remount /system\n");
				o.flush();
				o.write("rm -f /system/etc/init.d/00Lacputemp\n");
				o.flush();
				o.write("mount -o ro,remount /system\necho Success\n");
				o.flush();
				Toast.makeText(getApplicationContext(),b.readLine(),Toast.LENGTH_SHORT).show();

			}
			catch (IOException e)
			{}
		}
	}
public class clock implements Runnable{
	public void run(){
		try
		{
			Thread.sleep(1000);
		}
		catch (InterruptedException e)
		{}
		if(!inited){
			l1.setVisibility(View.GONE);
		}
	}
}
public void ref(View v){
	new Thread(new init()).start();
}
public void su(){
	try
	{
		java.lang.Process p=Runtime.getRuntime().exec("su");
		OutputStreamWriter o=new OutputStreamWriter(p.getOutputStream());
		BufferedReader b=new BufferedReader(new InputStreamReader(p.getInputStream()));
		o.write(cmd+"\necho Success\n");
		o.flush();
		Toast.makeText(getApplicationContext(),b.readLine(),Toast.LENGTH_SHORT).show();

	}
	catch (IOException e)
	{
		
	}
}
public void ton(View v){
	cmd="start thermald\nstart thermal-engine";
	su();
}
	public void toff(View v){
		cmd="stop thermald\nstop thermal-engine";
		su();
	}
public void dhy(View v){
	try{
		java.lang.Process p=Runtime.getRuntime().exec("su");
		OutputStreamWriter o=new OutputStreamWriter(p.getOutputStream());
		BufferedReader b=new BufferedReader(new InputStreamReader(p.getInputStream()));
		o.write("echo 0 > /proc/sys/kernel/sched_upmigrate\n");
		o.write("echo 0 > /proc/sys/kernel/sched_downmigrate\n");
		o.write("echo 10 > /proc/sys/kernel/sched_spill_nr_run\n");
		o.write("echo 85 > /proc/sys/kernel/sched_spill_load\necho Success\n");
		
		
		o.flush();
		e1.setText("0");
		e2.setText("0");
		e3.setText("10");
		e4.setText("85");
		Toast.makeText(getApplicationContext(),b.readLine(),Toast.LENGTH_SHORT).show();

	}
	catch (IOException e)
	{}
}
public void ok(View v){
	try{
		java.lang.Process p=Runtime.getRuntime().exec("su");
		OutputStreamWriter o=new OutputStreamWriter(p.getOutputStream());
		BufferedReader b=new BufferedReader(new InputStreamReader(p.getInputStream()));
		o.write("echo "+e1.getText().toString()+" > /proc/sys/kernel/sched_upmigrate\n");
		
		o.write("echo "+e2.getText().toString()+" > /proc/sys/kernel/sched_downmigrate\n");
		
		o.write("echo "+e3.getText().toString()+" > /proc/sys/kernel/sched_spill_nr_run\n");
		
		o.write("echo "+e4.getText().toString()+" > /proc/sys/kernel/sched_spill_load\necho Success\n");
		o.flush();
		Toast.makeText(getApplicationContext(),b.readLine(),Toast.LENGTH_SHORT).show();

	}
	catch (IOException e)
	{}
}
public class init implements Runnable{
	public void run(){
		
					try{
					java.lang.Process p=Runtime.getRuntime().exec("su");
					OutputStreamWriter o=new OutputStreamWriter(p.getOutputStream());
					BufferedReader b=new BufferedReader(new InputStreamReader(p.getInputStream()));
					o.write("cat /proc/sys/kernel/sched_upmigrate\n");
					o.flush();
					final String s1=b.readLine();
					o.write("cat /proc/sys/kernel/sched_downmigrate\n");
					o.flush();
					final String s2=b.readLine();
					o.write("cat /proc/sys/kernel/sched_spill_nr_run\n");
					o.flush();
					final String s3=b.readLine();
					o.write("cat /proc/sys/kernel/sched_spill_load\n");
					o.flush();
					final String s4=b.readLine();
					
					runOnUiThread(new Runnable(){
						public void run(){
							e1.setText(s1);
							e2.setText(s2);
							e3.setText(s3);
							e4.setText(s4);
							p1.setVisibility(View.INVISIBLE);
							inited=true;
							if(fs){
								se.putString("sched_up",s1);
								se.putString("sched_down",s2);
								se.putString("sched_run",s3);
								se.putString("sched_load",s4);
								se.commit();
							}
						}
					});
				}
					catch (IOException e)
					{}
					
			
			

	
		
	}
}
public void hfn(View v){
	e1.setText(sp.getString("sched_up",null));
	e2.setText(sp.getString("sched_down",null));
	e3.setText(sp.getString("sched_run",null));
	e4.setText(sp.getString("sched_load",null));
	Toast.makeText(getApplicationContext(),"默认设置已加载(未应用)",Toast.LENGTH_SHORT).show();
}


}
