package cn.busmap;

import android.app.Application;
import android.content.Context;

import com.baidu.mapapi.SDKInitializer;

public class MyApplication extends Application {
    private static Context context;
    @Override
    public void onCreate() {
        super.onCreate();
        // 在使用 SDK 各组间之前初始化 context 信息，传入 ApplicationContext.这样就不用在每个activity中添加下面这句话
        //需要在清单文件中设置
        SDKInitializer.initialize(this);
        context=getApplicationContext();
    }
    public static Context getContext(){
        return context;
    }

}