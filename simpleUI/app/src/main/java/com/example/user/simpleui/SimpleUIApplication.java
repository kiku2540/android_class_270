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
                .applicationId("5KauaUE3Ehu6a9TwNmyR4cRgZI6ftS2WVqPeJmfv")
                .server("https://parseapi.back4app.com/")
//                .clientKey("cfBljS15DpFNjCtfpGsosJWltD9rLWhJoOszjknC")
                .enableLocalDataStore()
                .build());
        //.clientKey(""));
    }
}
