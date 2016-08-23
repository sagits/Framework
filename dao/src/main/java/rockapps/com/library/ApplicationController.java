package rockapps.com.library;

import android.app.Application;

import rockapps.com.library.local.LocalDbImplement;
import rockapps.com.library.model.UserModel;

/**
 * Created by Renato on 12/07/2016.
 */
public class ApplicationController extends Application {
    private static ApplicationController sInstance;


    @Override
    public void onCreate() {
        super.onCreate();

        // initialize the singleton
        sInstance = this;
    }

    public static synchronized ApplicationController getInstance() {
        return sInstance;
    }

    public UserModel getUser() {
        return new LocalDbImplement<UserModel>(this).get(UserModel.class);
    }

    public void saveUser(UserModel user) {
        new LocalDbImplement<UserModel>(this).save(user, UserModel.class, UserModel.class.getSimpleName());
    }

    public void clearUser() {
        new LocalDbImplement<UserModel>(this).clearObject(UserModel.class.getSimpleName());
    }
}
