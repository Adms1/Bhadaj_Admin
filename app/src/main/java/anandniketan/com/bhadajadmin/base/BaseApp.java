package anandniketan.com.bhadajadmin.base;

import android.app.Application;
import android.content.Context;

import anandniketan.com.bhadajadmin.asynctasks.GetAPIURLTask;

public class BaseApp extends Application {

    public static Context mAppcontext;

    @Override
    public void onCreate() {
        super.onCreate();
       // FontsOverride.setDefaultFont(this, "DEFAULT", "Fonts/opensans_regular.ttf");
        mAppcontext = getApplicationContext();

//        FontsOverride.setDefaultFont(this, "MONOSPACE", "font/TitilliumWeb-Regular.ttf");
//        FontsOverride.setDefaultFont(this, "SERIF", "font/TitilliumWeb-Regular.ttf");
//        FontsOverride.setDefaultFont(this, "SANS_SERIF", "font/TitilliumWeb-Regular.ttf");

        try {
            new GetAPIURLTask(mAppcontext).execute();
        }catch (Exception ex){
            ex.printStackTrace();
        }
    }

    public static Context getAppContext() {
        return mAppcontext;
    }
    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(base);
       // MultiDex.install(this);
    }
}
