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
public class selinux extends Activity
{
	TextView t;
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
        setContentView(R.layout.selinux);
		t=(TextView)findViewById(R.id.selinuxTextView1);
		getActionBar().setDisplayHomeAsUpEnabled(true);
		refresh();
}
	public void refresh(){
		try{
			java.lang.Process p=Runtime.getRuntime().exec("su");
			OutputStreamWriter o=new OutputStreamWriter(p.getOutputStream());
			BufferedReader b=new BufferedReader(new InputStreamReader(p.getInputStream()));
			o.write("getenforce\n");
			o.flush();
			String sk=b.readLine();
			t.setText("当前SELinux状态:"+sk);

		}
		catch(IOException e){}
	
	}
	public void off (View v){
		try
		{
			java.lang.Process p=Runtime.getRuntime().exec("su");
			OutputStreamWriter o=new OutputStreamWriter(p.getOutputStream());
			BufferedReader b=new BufferedReader(new InputStreamReader(p.getInputStream()));
				o.write("setenforce 0\necho Success\n");
				o.flush();
				String sk=b.readLine();
				Toast.makeText(getApplicationContext(),"selinux被关闭! "+sk,Toast.LENGTH_SHORT).show();
				refresh();
			
		
			
			




		}
		catch (IOException e)
		{}
		
	}
	public void on(View v){
		try
		{
			java.lang.Process p=Runtime.getRuntime().exec("su");
			OutputStreamWriter o=new OutputStreamWriter(p.getOutputStream());
			BufferedReader b=new BufferedReader(new InputStreamReader(p.getInputStream()));


			o.write("setenforce 1\necho Success\n");
			o.flush();
			String sk=b.readLine();
			Toast.makeText(getApplicationContext(),"selinux被开启!"+sk,Toast.LENGTH_SHORT).show();
			refresh();



		}
		catch (IOException e)
		{}
	}
	public void run (View v){
		try
		{
			java.lang.Process p=Runtime.getRuntime().exec("su");
			OutputStreamWriter o=new OutputStreamWriter(p.getOutputStream());
			BufferedReader b=new BufferedReader(new InputStreamReader(p.getInputStream()));


			o.write("setenforce 0\n");
			o.flush();
			o.write("mount -o rw,remount /system\n");
			o.flush();
			o.write("echo '#!/system/bin/sh' > /system/etc/init.d/00SELinux\n");
			o.flush();
			o.write("echo setenforce 0 >> /system/etc/init.d/00SELinux\n");
			o.write("chmod 755 /system/etc/init.d/00SELinux\n mount -o ro,remount /system\necho Success\n");
			o.flush();
			
			String sk=b.readLine();
			Toast.makeText(getApplicationContext(),"selinux被关闭!"+sk,Toast.LENGTH_SHORT).show();

			refresh();


		}
		catch (IOException e)
		{}
	}
	public void yjon (View v){
		try
		{
			java.lang.Process p=Runtime.getRuntime().exec("su");
			OutputStreamWriter o=new OutputStreamWriter(p.getOutputStream());
			BufferedReader b=new BufferedReader(new InputStreamReader(p.getInputStream()));


			o.write("setenforce 1\n");
			o.flush();
			o.write("mount -o rw,remount /system\n");
			o.flush();
			o.write("echo '#!/system/bin/sh' > /system/etc/init.d/00SELinux\n");
			o.flush();
			o.write("echo setenforce 1 >> /system/etc/init.d/00SELinux\n");
			o.write("chmod 755 /system/etc/init.d/00SELinux\n mount -o ro,remount /system\necho Success\n");
			o.flush();

			String sk=b.readLine();
			Toast.makeText(getApplicationContext(),"selinux被开启!"+sk,Toast.LENGTH_SHORT).show();

			refresh();


		}
		catch (IOException e)
		{}
	}
	public void clear(View v){
		try
		{
			java.lang.Process p=Runtime.getRuntime().exec("su");
			OutputStreamWriter o=new OutputStreamWriter( p.getOutputStream());
			BufferedReader b=new BufferedReader(new InputStreamReader(p.getInputStream()));
			o.write("mount -o rw,remount /system\n");
			o.flush();
			o.write("rm -f /system/etc/init.d/00SELinux\n");
			o.flush();
			o.write("mount -o ro,remount /system\necho Success\n");
			o.flush();
			Toast.makeText(getApplicationContext(),b.readLine(),Toast.LENGTH_SHORT).show();
			
		}
		catch (IOException e)
		{}

	}
}
