package rockapps.com.library.volley;

import android.content.Context;

import com.android.volley.AuthFailureError;
import com.android.volley.Response;
import com.android.volley.toolbox.JsonObjectRequest;

import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

import rockapps.com.library.ApplicationController;
import rockapps.com.library.model.UserModel;

/**
 * Created by Renato on 16/11/2014.
 */
public class AuthJsonObjectRequest extends JsonObjectRequest {
    private Context context;

    public AuthJsonObjectRequest(int method, String url, JSONObject jsonRequest, Response.Listener<JSONObject> listener, Response.ErrorListener errorListener) {
        super(method, url, jsonRequest, listener, errorListener);
        if (errorListener instanceof CallListener) {
            ((CallListener) errorListener).setRequest(this);
            context = ((CallListener) errorListener).activity;
        }
    }

    public AuthJsonObjectRequest(String url, JSONObject jsonRequest, Response.Listener<JSONObject> listener, Response.ErrorListener errorListener) {
        super(url, jsonRequest, listener, errorListener);
    }

    @Override
    public Map<String, String> getHeaders() throws AuthFailureError {

        UserModel user = ApplicationController.getInstance().getUser(); //new LocalDbImplement<UserModel>(context).get(UserModel.class);
        HashMap<String, String> params = new HashMap<String, String>();
        if (user != null) {
            String auth = "Bearer " + user.getAuthToken();
            params.put("Authorization", auth);

            SmarterLogMAKER.w("auth credentials: " + auth);
        }

        return params;
    }

}