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
public class display extends Activity
{
	TextView t1;
	boolean fs;
	SharedPreferences sp;
	SharedPreferences.Editor se;
	LinearLayout l1;
	EditText e1;
	EditText e2;
	CheckBox c1;
	EditText e3;
	TextView t2;
	int normalh;
	int normalw;
	int nowh;
	int noww;
	int nowden;
	int normalden;
	AlertDialog dia;
	boolean exit;
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
		sp=getSharedPreferences("main",0);
		se=sp.edit();
		fs=sp.getBoolean("first_display",true);
        setContentView(R.layout.display);
		getActionBar().setDisplayHomeAsUpEnabled(true);

		c1=(CheckBox)findViewById(R.id.displayCheckBox1);
		c1.setVisibility(View.GONE);
		l1=(LinearLayout)findViewById(R.id.displayLinearLayout1);
		e1=(EditText)findViewById(R.id.displayEditText1);
        e2=(EditText)findViewById(R.id.displayEditText2);
		e3=(EditText)findViewById(R.id.displayEditText3);
		t1=(TextView)findViewById(R.id.displayTextView1);
		t2=(TextView)findViewById(R.id.displayTextView2);
		refresh();
		
		
	}
	public void ss(){
		dia=new AlertDialog.Builder(this)
			.setTitle("安全模式")
			.setMessage("将在5秒后恢复所有设置")
			.setCancelable(false)
			.setPositiveButton("不恢复",new DialogInterface.OnClickListener(){
				public void onClick(DialogInterface d1,int p2){
					exit=true;
				}
			})
			.create();
		dia.show();
		exit=false;
		new Thread(new ssclock()).start();
	}
	public class ssclock implements Runnable{
		public void run(){
			int t=0;
			while(true){
				Log.i("ok","ok");
				if(t>=5){
					break;
				}
				if(exit){
					break;
				}
				try
				{
					Thread.sleep(1000);
				}
				catch (InterruptedException e)
				{}
				t++;
				
			}
			if(!exit){
				Log.i("ok","1");
				try
				{
					java.lang.Process p=Runtime.getRuntime().exec("su");
					OutputStreamWriter o=new OutputStreamWriter(p.getOutputStream());
					BufferedReader b=new BufferedReader(new InputStreamReader(p.getInputStream()));
					o.write("wm density "+normalden+"\n");
					o.write("wm size "+normalw+"x"+normalh+"\necho Success\n");
					o.flush();
					b.readLine();

					refresh();

				}
				catch (IOException e)
				{
					Log.e("ok","e");
				}
		
			}
			
			
			runOnUiThread(new Runnable(){
					public void run(){
						dia.dismiss();
					}
				});
		}
	}
	public void bili(View v){
		refresh();
	}
	public void refresh(){
		if(c1.isChecked()){
			l1.setVisibility(View.GONE);
		}
		else{
			l1.setVisibility(View.VISIBLE);
		}
		try
		
		{
			java.lang.Process p=Runtime.getRuntime().exec("su");
			OutputStreamWriter o=new OutputStreamWriter(p.getOutputStream());
			BufferedReader b=new BufferedReader(new InputStreamReader(p.getInputStream()));
			o.write("wm size\necho xzr467706992\n");
			o.flush();
		    String s=b.readLine();
			int i=s.length();
			int k=0;
			String str;
			int x;
			while (true){
				str=s.substring(k,k+1);
				try
				{
					x = Integer.parseInt(str);
					break;
				}
				catch (RuntimeException e)
				{
					k=k+1;
				}

			}
			String text=s.substring(k,i);
			String cache;
			int kk=0;
			while(true){
				cache=text.substring(kk,kk+1);
				if(cache.equals("x")){
					noww=Integer.parseInt(text.substring(0,kk));
					normalw=Integer.parseInt(text.substring(0,kk));
					e1.setText(text.substring(0,kk));
					kk++;
					break;
				}
				kk++;
			}
			e2.setText(text.substring(kk,text.length()));
			normalh=Integer.parseInt(text.substring(kk,text.length()));
			t1.setText("默认分辨率: "+s.substring(k,i)+"\n");

			String s2=b.readLine();
			if(!s2.equals("xzr467706992")){
				int i2=s2.length();
				int k2=0;
				String str2;
				int x2;
				while (true){
					str2=s2.substring(k2,k2+1);
					try
					{
						x2 = Integer.parseInt(str2);
						break;
					}
					catch (RuntimeException e)
					{
						k2=k2+1;
					}

				}
				text=s2.substring(k2,i2);
				cache="";
				kk=0;
				while(true){
					cache=text.substring(kk,kk+1);
					if(cache.equals("x")){
						e1.setText(text.substring(0,kk));
						noww=Integer.parseInt(text.substring(0,kk));
						kk++;
						break;
					}
					kk++;
				}
				e2.setText(text.substring(kk,text.length()));
				t1.append("当前分辨率: "+s2.substring(k2,i2));
			}
			
			try
			{
				java.lang.Process p2=Runtime.getRuntime().exec("su");
				OutputStreamWriter o2=new OutputStreamWriter(p2.getOutputStream());
				BufferedReader b2=new BufferedReader(new InputStreamReader(p2.getInputStream()));
				o2.write("wm density\necho xzr467706992\n");
				o2.flush();
				String s3=b2.readLine();
				int i3=s3.length();
				int k3=0;
				String str3;
				int x3;
				while (true){
					str3=s3.substring(k3,k3+1);
					try
					{
						x3 = Integer.parseInt(str3);
						break;
					}
					catch (RuntimeException e)
					{
						k3++;
					}

				}
				String text3=s3.substring(k3,i3);
				t2.setText("默认屏幕密度:"+text3);
				normalden=Integer.parseInt(text3);
				nowden=Integer.parseInt(text3);
				e3.setText(text3);
				
				String s4=b2.readLine();
				if(!s4.equals("xzr467706992")){
				int i4=s4.length();
				int k4=0;
				String str4;
				int x4;
				while (true){
					str4=s4.substring(k4,k4+1);
					try
					{
						x4 = Integer.parseInt(str4);
						break;
					}
					catch (RuntimeException e)
					{
						k4++;
					}

				}
				String text4=s4.substring(k4,i4);
				nowden=Integer.parseInt(text4);
				
				t2.append("\n当前屏幕密度:"+text4);
				e3.setText(text4);
				}
				}
				catch(IOException e){
					
				}



		}
		catch (IOException e)
		{

		}
	}
	public void setsize(View v){
		if(c1.isChecked()){
			int den=nowden/(nowh/Integer.parseInt(e1.getText().toString()));
			try
			{
				java.lang.Process p=Runtime.getRuntime().exec("su");
				OutputStreamWriter o=new OutputStreamWriter(p.getOutputStream());
				BufferedReader b=new BufferedReader(new InputStreamReader(p.getInputStream()));
				o.write("wm density "+den+"\necho Success");
				
				o.flush();
				Toast.makeText(getApplicationContext(),b.readLine(),Toast.LENGTH_SHORT).show();

				

			}
			catch (IOException e)
			{}
			
		}
		

			try
			{
				java.lang.Process p=Runtime.getRuntime().exec("su");
				OutputStreamWriter o=new OutputStreamWriter(p.getOutputStream());
				BufferedReader b=new BufferedReader(new InputStreamReader(p.getInputStream()));
				o.write("wm size "+e1.getText().toString()+"x"+e2.getText().toString()+"\necho Success\n");
				o.flush();
				Toast.makeText(getApplicationContext(),b.readLine(),Toast.LENGTH_SHORT).show();

				refresh();

			}
			catch (IOException e)
			{}
			
		}
	
	public void setdensity(View v){
		try
		{
			java.lang.Process p=Runtime.getRuntime().exec("su");
			OutputStreamWriter o=new OutputStreamWriter(p.getOutputStream());
			BufferedReader b=new BufferedReader(new InputStreamReader(p.getInputStream()));
			o.write("wm density "+e3.getText().toString()+"\necho Success\n");
			o.flush();
			Toast.makeText(getApplicationContext(),b.readLine(),Toast.LENGTH_SHORT).show();

			refresh();
			

		}
		catch (IOException e)
		{}
		
	}
	public void hf(View v){
		try
		{
			java.lang.Process p=Runtime.getRuntime().exec("su");
			OutputStreamWriter o=new OutputStreamWriter(p.getOutputStream());
			BufferedReader b=new BufferedReader(new InputStreamReader(p.getInputStream()));
			o.write("wm density "+normalden+"\n");
			o.write("wm size "+normalw+"x"+normalh+"\necho Success\n");
			o.flush();
			Toast.makeText(getApplicationContext(),b.readLine(),Toast.LENGTH_SHORT).show();

			refresh();

		}
		catch (IOException e)
		{}
	}
}
