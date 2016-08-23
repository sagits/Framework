package rockapps.com.library.volley;

import android.app.ProgressDialog;
import android.content.Context;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.google.gson.Gson;

import rockapps.com.library.R;
import rockapps.com.library.local.LocalDbImplement;
import rockapps.com.library.volley.mymodels.RequestHolder;

public abstract class GenericAbstractDao<E> {

    protected Context activity;
    protected Gson gson;
    protected ProgressDialog dialog;
    protected String serverUrl;
    protected String modelUrl;

    public GenericAbstractDao(Context activity) {
        this.activity = activity;

        boolean devMode = new LocalDbImplement<Boolean>(activity).getBoolPreference("devMode");

        serverUrl = activity.getString(!devMode ? R.string.productionUrl : R.string.developmentUrl);

        gson = new Gson();

        setDefaultModelUrl();
    }

    public void setDefaultModelUrl() {
        String modelName = this.getClass().getSimpleName().replace("Dao", "").toLowerCase();
        if (modelName.substring(modelName.length() - 1) == "y") {
            modelUrl = modelName.substring(modelName.length() - 2) + "ies" + "/";
        }
        else {
            modelUrl = modelName + "s" + "/";
        }
    }

    protected void addRequest(Request request) {
        request.setRetryPolicy(new DefaultRetryPolicy(DefaultRetryPolicy.DEFAULT_TIMEOUT_MS, 3, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        SmarterLogMAKER.w("Making request " + getRequestType(request.getMethod()) + " to: " + request.getUrl());

        RequestController.getInstance(activity).addToRequestQueue(request);
    }

    protected void addRequest(Request request, int timeout) {
        request.setRetryPolicy(new DefaultRetryPolicy(timeout, 3, 0.1f));
        SmarterLogMAKER.w("Making request " + getRequestType(request.getMethod()) + " to: " + request.getUrl());

        RequestController.getInstance(activity).addToRequestQueue(request);
    }

    protected String getRequestType(int id) {
        String type = new String();
        switch (id) {
            case 0:
                type = "GET";
                break;
            case 1:
                type = "POST";
                break;
            case 2:
                type = "PUT";
                break;
            case 3:
                type = "DELETE";
                break;
            default:
                type = "UNKNOWN";
        }
        return type;
    }

    public void startRequest(RequestHolder requestHolder) {
        requestHolder.getCallListener().start();
        SmarterLogMAKER.w("Started Request");

        if (requestHolder.getTimeOut() > 0) {
            addRequest(requestHolder.getRequest(), requestHolder.getTimeOut());
        }
        else {
            addRequest(requestHolder.getRequest());
        }


    }
}
