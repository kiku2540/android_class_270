package com.example.user.simpleui;

import android.app.Application;

import com.parse.Parse;
import com.parse.ParseObject;

/**
 * Created by user on 2016/7/19.
 */
public class SimpleUIApplication extends Application {
    @Override
    public void onCreate()
    {
        super.onCreate();

        ParseObject.registerSubclass(Order.class);
        ParseObject.registerSubclass(Drink.class);

        Parse.initialize(new Parse.Configuration.Builder(this)
                .applicationId("4bVqO3JNTWh19n35mt9ohmMbuKTM9Jye3VLfazjw")
                .server("https://parseapi.back4app.com/")
                .clientKey("cfBljS15DpFNjCtfpGsosJWltD9rLWhJoOszjknC")
                .enableLocalDataStore()
                .build());
        //.clientKey(""));
    }
}
