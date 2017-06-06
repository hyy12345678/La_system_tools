package com.xzr.La.systemtoolbox;

import android.app.*;
import android.content.*;
import android.os.*;
import android.view.*;
import android.widget.*;
import android.widget.AdapterView.*;
import java.io.*;
import java.util.*;

public class MainActivity extends Activity 
{
	
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
ListView list;
SharedPreferences sp;
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
		sp=getSharedPreferences("main",0);
		File f=new File(sp.getString("store_path",null));
		f.mkdirs();
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
		try
		{
			java.lang.Process p=Runtime.getRuntime().exec("su");
			OutputStreamWriter o=new OutputStreamWriter(p.getOutputStream());
			BufferedReader b=new BufferedReader(new InputStreamReader(p.getInputStream()));
			o.write("echo by xzr467706992\n");
			o.flush();
			if(!b.readLine().equals("by xzr467706992")){
				Toast.makeText(getApplicationContext(),"root权限什么的都是不存在的",Toast.LENGTH_SHORT).show();
				finish();
			}
			
		}
		catch (IOException e)
		{
			Toast.makeText(getApplicationContext(),"root权限什么的都是不存在的",Toast.LENGTH_SHORT).show();
			finish();
		}
		catch(RuntimeException e){
			Toast.makeText(getApplicationContext(),"root权限什么的都是不存在的",Toast.LENGTH_SHORT).show();
			finish();
		}
		//getActionBar().setDisplayHomeAsUpEnabled(true);
		list=(ListView)findViewById(R.id.mainListView1);
		List<HashMap<String,String>> builder=new ArrayList<HashMap<String,String>>();
		HashMap<String,String> map3=new HashMap<String,String>();
		map3.put("title","关于/设置");
		map3.put("subtitle","关于镧·系统工具箱的全部");
		builder.add(map3);
		HashMap<String,String> map0=new HashMap<String,String>();
		map0.put("title","SeLinux配置");
		map0.put("subtitle","是它。就是它。阻止了你修改adm的线程数，还使性能监视器无法正常运行。");
		builder.add(map0);
		HashMap<String,String> map=new HashMap<String,String>();
		map.put("title","分区管理工具");
		map.put("subtitle","管理你设备中的分区");
		builder.add(map);
		
		HashMap<String,String> map2=new HashMap<String,String>();
		map2.put("title","处理器高级配置");
		map2.put("subtitle","修改处理器高级设置");
		builder.add(map2);
		HashMap<String,String> map4=new HashMap<String,String>();
		map4.put("title","高级重启");
		map4.put("subtitle","更高级的重新启动");
		builder.add(map4);
		HashMap<String,String> map5=new HashMap<String,String>();
		map5.put("title","显示配置");
		map5.put("subtitle","配置与屏幕显示有关的内容");
		builder.add(map5);
		HashMap<String,String> map6=new HashMap<String,String>();
		map6.put("title","杂项配置");
		map6.put("subtitle","奇怪的工具");
		builder.add(map6);
		SimpleAdapter a=new SimpleAdapter(this,builder,R.layout.list,new String[]{"title","subtitle"},new int[]{R.id.title,R.id.subtitle});
		list.setAdapter(a);
		
		OnItemClickListener mItemClickListener = new OnItemClickListener() {
    @Override
    public void onItemClick(AdapterView arg0, View arg1, int arg2, long arg3) {
		if(arg2==1){
			startActivity(new Intent(getApplicationContext(),selinux.class));
		}
         if(arg2==2){
			 startActivity(new Intent(getApplicationContext(),rec.class));
			 
		 }
		if(arg2==3){
			startActivity(new Intent(getApplicationContext(),cpu.class));

		}
		if(arg2==0){
			startActivity(new Intent(getApplicationContext(),about.class));

		}
		if(arg2==4){
			startActivity(new Intent(getApplicationContext(),reboot.class));

		}
		if(arg2==5){
			startActivity(new Intent(getApplicationContext(),display.class));

		}
		if(arg2==6){
			startActivity(new Intent(getApplicationContext(),blue.class));

		}
		}
};

list.setOnItemClickListener(mItemClickListener);
    }
}