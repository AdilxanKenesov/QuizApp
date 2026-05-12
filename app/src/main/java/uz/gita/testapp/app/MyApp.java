package uz.gita.testapp.app;

import android.app.Application;

import uz.gita.testapp.data.local.LocalStroge;
import uz.gita.testapp.data.repository.AppRepositoryImpl;


public class MyApp extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        LocalStroge.init(this);
        AppRepositoryImpl.init();
    }
}
