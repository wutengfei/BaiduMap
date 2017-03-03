package cn.busmap;

import android.app.Application;
import com.baidu.mapapi.SDKInitializer;

public class DemoApplication extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        // 在使用 SDK 各组间之前初始化 context 信息，传入 ApplicationContext.这样就不用在每个activity中添加下面这句话
        //需要在清单文件中设置
        SDKInitializer.initialize(this);
    }
}