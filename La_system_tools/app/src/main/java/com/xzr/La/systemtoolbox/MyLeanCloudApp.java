package com.xzr.La.systemtoolbox;
import android.app.*;
import com.avos.avoscloud.*;

public class MyLeanCloudApp extends Application
{
	@Override
    public void onCreate() {
        super.onCreate();

        // 初始化参数依次为 this, AppId, AppKey
        AVOSCloud.initialize(this,"yhN2DqQ9UOt13Jn2zpAnmOdx-gzGzoHsz","DpCIlFwB0sPer9RU9CdSLqE3");
    }
}
