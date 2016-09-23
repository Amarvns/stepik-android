package org.stepic.droid.base;

import android.content.Context;
import android.os.Build;
import android.support.multidex.MultiDex;
import android.support.multidex.MultiDexApplication;

import com.facebook.accountkit.AccountKit;
import com.vk.sdk.VKSdk;
import com.yandex.metrica.YandexMetrica;

import org.stepic.droid.R;
import org.stepic.droid.core.components.AppCoreComponent;
import org.stepic.droid.core.components.DaggerAppCoreComponent;
import org.stepic.droid.core.components.DaggerStorageComponent;
import org.stepic.droid.core.components.StorageComponent;
import org.stepic.droid.core.modules.AppCoreModule;
import org.stepic.droid.core.modules.StorageModule;

import uk.co.chrisjenx.calligraphy.CalligraphyConfig;

public class MainApplication extends MultiDexApplication {

    protected static MainApplication application;
    private AppCoreComponent component;
    private StorageComponent storageComponent;

    //    private RefWatcher refWatcher;

    @Override
    public void onCreate() {
        super.onCreate();
        init();
    }

    private void init() {
//        refWatcher = LeakCanary.install(this);
        application = this;
        CalligraphyConfig.initDefault(new CalligraphyConfig.Builder()
                .setDefaultFontPath("fonts/NotoSans-Regular.ttf")
                .setFontAttrId(R.attr.fontPath)
                .build()
        );
        AccountKit.initialize(getApplicationContext());
        VKSdk.initialize(this);

        StorageModule storageModule = new StorageModule(this);
        storageComponent = DaggerStorageComponent.builder().
                storageModule(storageModule).build();

        component = DaggerAppCoreComponent.builder()
                .appCoreModule(new AppCoreModule(application))
                .storageModule(storageModule)
                .build();


        // Инициализация AppMetrica SDK
        if (Build.VERSION.SDK_INT <= Build.VERSION_CODES.M) {
            YandexMetrica.activate(getApplicationContext(), "fd479031-bdf4-419e-8d8f-6895aab23502");
            // Отслеживание активности пользователей
            YandexMetrica.enableActivityAutoTracking(this);
        }
    }
//    public static RefWatcher getRefWatcher(Context context) {
//        MainApplication application = (MainApplication) context.getApplicationContext();
//        return application.refWatcher;
//    }

    public static AppCoreComponent component(Context context) {
        return ((MainApplication) context.getApplicationContext()).component;
    }


    public static AppCoreComponent component() {
        return application.component;
    }

    public static StorageComponent storageComponent() {
        return application.storageComponent;
    }


    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(base);
        MultiDex.install(this);
    }

    public static Context getAppContext() {
        return application.getApplicationContext();
    }

}
