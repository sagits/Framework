package atrativaweb.com.br.framework.utils;

import android.content.Context;
import android.content.Intent;
import android.support.v4.app.Fragment;

import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookSdk;
import com.facebook.login.LoginResult;
import com.facebook.login.widget.LoginButton;

import java.util.Arrays;
import java.util.List;

/**
 * Created by Renato on 26/04/2016.
 */
public class FacebookSignInHelper {

    private CallbackManager callbackManager;

    public void setFacebookLogin(LoginButton facebookButton, FacebookCallback<LoginResult> callback) {
        callbackManager = CallbackManager.Factory.create();
        List<String> permissionNeeds = Arrays.asList("email", "public_profile");
        facebookButton.setReadPermissions(permissionNeeds);
        facebookButton.registerCallback(callbackManager, callback);
    }

    public void setFacebookLogin(Fragment fragment, LoginButton facebookButton, FacebookCallback<LoginResult> callback) {
        callbackManager = CallbackManager.Factory.create();
        List<String> permissionNeeds = Arrays.asList("email", "public_profile");
        facebookButton.setReadPermissions(permissionNeeds);
        facebookButton.setFragment(fragment);
        facebookButton.registerCallback(callbackManager, callback);
    }

    public void initialize(Context context) {
        FacebookSdk.sdkInitialize(context);
    }

    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        callbackManager.onActivityResult(requestCode, resultCode, data);
    }
}
