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
public class rec extends Activity
{
	String list="";
	String rp;
	SharedPreferences sp;
	SharedPreferences.Editor se;
	TextView t;
	AlertDialog dia;
	boolean done=false;
	boolean init=false;
	String log;
	RadioButton r1;
	RadioButton r2;
	EditText e1;
	EditText e2;
	EditText e3;
	EditText e4;
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
        setContentView(R.layout.rec);
		sp=getSharedPreferences("main",0);
		se=sp.edit();
		rp=sp.getString("path",null);
		t=(TextView)findViewById(R.id.recTextView1);
		r1=(RadioButton)findViewById(R.id.recRadioButton1);
		r2=(RadioButton)findViewById(R.id.recRadioButton2);
		e1=(EditText)findViewById(R.id.recEditText1);
		e2=(EditText)findViewById(R.id.recEditText2);
		e3=(EditText)findViewById(R.id.recEditText3);
		e4=(EditText)findViewById(R.id.recEditText4);
		getActionBar().setDisplayHomeAsUpEnabled(true);
		AlertDialog.Builder a;
		a= new AlertDialog.Builder(this);
		a.setTitle("获取设备信息...")
		.setView(LayoutInflater.from(this).inflate(R.layout.circle,null))
		.setCancelable(false);
		dia=a.create();
		dia.show();
		new Thread(new wait()).start();
		new Thread(new start()).start();
		
}
public class format implements Runnable{
		public void run(){
			try
			{
				final String parti=e3.getText().toString();
				final String fat=e4.getText().toString();
				java.lang.Process p=Runtime.getRuntime().exec("su");
				OutputStreamWriter o=new OutputStreamWriter(p.getOutputStream());
				BufferedReader b=new BufferedReader(new InputStreamReader(p.getInputStream()));
				o.write("umount "+rp+"/"+parti+"\n");
				o.write("mkfs -t "+fat+" "+rp+"/"+parti+"\necho Success\n");
				o.flush();
				final String kk=b.readLine();
				dia.dismiss();
				runOnUiThread(
					new Runnable(){
						public void run(){
							Toast.makeText(getApplicationContext(),kk,Toast.LENGTH_SHORT);
						}
					});
}
		
			catch (IOException e)
			{}
			}
			
}
public void ref(View v){
	new Thread(new format()).start();
	AlertDialog.Builder a;
	a= new AlertDialog.Builder(this);
	a.setTitle("格式化指定分区...")
		.setView(LayoutInflater.from(this).inflate(R.layout.circle,null))
		.setCancelable(false);
	dia=a.create();
	dia.show();
	
	
}
public class runflash implements Runnable{
	public void run(){
		String pif=null;
		String pof=null;
		if(r1.isChecked()){
			pif=e2.getText().toString();
			pof=rp+"/"+e1.getText().toString();
		}
		if(r2.isChecked()){
			pif=rp+"/"+e1.getText().toString();
			pof=e2.getText().toString();
		}
		try
		{
			java.lang.Process p=Runtime.getRuntime().exec("su");
			OutputStreamWriter o=new OutputStreamWriter(p.getOutputStream());
			BufferedReader b=new BufferedReader(new InputStreamReader(p.getInputStream()));
			o.write("dd if="+pif+" of="+pof+"\necho Success\n");
			o.flush();
			
			log=b.readLine();
			done=true;

		}
		catch (IOException e)
		{}
		
	}
}
public class check implements Runnable{
	public void run(){
		while(true){
			try
			{
				Thread.sleep(10);
			}
			catch (InterruptedException e)
			{}
			if(done){
				runOnUiThread(new Runnable(){
					public void run(){
						dia.dismiss();
						Toast.makeText(getApplicationContext(),log,Toast.LENGTH_SHORT).show();
					}
				});
			
			break;
			}
		}
	}
}
public void flash(View v){
	if(r2.isChecked()){
		e2.setText("/mnt/sdcard/"+e1.getText().toString()+".img");
	}
	AlertDialog.Builder a= new AlertDialog.Builder(this);
	a.setTitle("正在刷入/提取")
	.setView(LayoutInflater.from(this).inflate(R.layout.circle,null))
	.setCancelable(false);
	dia=a.create();
	dia.show();
	done=false;
	new Thread(new check()).start();
	new Thread(new runflash()).start();
	
	
}
public class wait implements Runnable{
	public void run(){
		while(true){
		try
		{
			Thread.sleep(10);
		}
		catch (InterruptedException e)
		{}
		
		runOnUiThread(new Runnable(){
			public void run(){
				if(init){
					t.setText(list);
					dia.dismiss();
					
				}
			}
		});
		if(init){
			break;
		}
		}
	}
}
public class start implements Runnable{
	public void run(){
		try
		{
			java.lang.Process p=Runtime.getRuntime().exec("su");
			OutputStreamWriter o=new OutputStreamWriter(p.getOutputStream());
			BufferedReader b=new BufferedReader(new InputStreamReader(p.getInputStream()));
			o.write("ls /dev/block\n");
			o.write("echo xzr467706992\n");
			o.flush();
		    boolean bootdev=false;
			boolean error=false;
			String s;
			while(!(s=b.readLine()).equals("xzr467706992")){
				if(s.equals("bootdevice")){
					bootdev=true;
					rp="/dev/block/bootdevice/by-name";
				}
			}
			Log.i("info",error+","+bootdev);
			if(bootdev){
				o.write("ls /dev/block/bootdevice/by-name\necho xzr467706992\n");
				o.flush();
				
				while(true){
					String k=b.readLine();
					if(k.equals("xzr467706992")){
						break;
					}
					list=list+"\n"+k;
					
				}
				
			}
			else{
				if(rp==null){
				String[] path={"/dev/block/platform/mtk-msdc.0/11230000.msdc0"
				,"/dev/block/platform/omap/omap_hsmmc.0"
				,"/dev/block/platform/omap/omap_hsmmc.1"
					,"/dev/block/platform/sdhci-tegra.3"
					,"/dev/block/platform/sdhci-pxav3.2"
					,"/dev/block/platform/msm_sdcc.1"
					,"/dev/block/platform/15570000.ufs"
					,"/dev/block/platform/155a0000.ufs"
					,"/dev/block/platform/comip-mmc.1"
					,"/dev/block/platform/msm_sdcc.1"
					,"/dev/block/platform/mtk-msdc.0"
					,"/dev/block/platform/sdhci-tegra.3"
	                ,"/dev/block/platform/dw_mmc.0"
		            ,"/dev/block/platform/hi_mci.1"
					,"/dev/block/platform/hi_mci.0"
					,"/dev/block/platform/sdhci-tegra.3"
				    ,"/dev/block/platform/sdhci.1"
					,"/dev/block/platform/dw_mmc"
					,"/dev/block/platform/soc/7464900.sdhci"
					,"/dev/block"};
					boolean found=false;
					for(int i=0;i<path.length;i++){
						if(found){
							break;
						}
						else{
						o.write("ls "+path[i]+"\necho xzr467706992\n");
						o.flush();
						String aa;
						while(!(aa=b.readLine()).equals("xzr467706992")){
							if(aa.equals("by-name")){
								found=true;
								se.putString("path",(rp=(path[i]+"/by-name")));
								se.commit();
								
								break;
								
							}
							}
						}
					}
					if(found){
						try
						{
							Thread.sleep(1000);
						}
						catch (InterruptedException e)
						{}
						o.write("ls " + rp + "\necho xzr467706992\n");
						o.flush();

						while(true){
							String k=b.readLine();
							if(k.equals("xzr467706992")){
								break;
							}
							list=list+"\n"+k;
						}
					}
					}
					else{
				
				o.write("ls "+rp+"\necho xzr467706992\n");
				o.flush();

				while(true){
					String k=b.readLine();
					if(k.equals("xzr467706992")){
						break;
					}
					list=list+"\n"+k;
}
				
				
					
				}
				
				
				}
				
				
			init=true;
		}
		catch (IOException e)
		{}
		catch(RuntimeException e){
			runOnUiThread(new Runnable(){
				public void run(){
					
					Toast.makeText(getApplicationContext(),"root权限似乎boom了",Toast.LENGTH_SHORT).show();
				}
				
			});
		}
	}
}
}
