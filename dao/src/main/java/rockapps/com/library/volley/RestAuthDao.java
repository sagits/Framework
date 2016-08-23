package rockapps.com.library.volley;

import android.content.Context;

import com.android.volley.Request;

import org.json.JSONObject;

import rockapps.com.library.volley.mymodels.RequestHolder;

public class RestAuthDao<E> extends GenericAbstractDao<E> {

    public RestAuthDao(Context activity) {
        super(activity);
    }




    public RequestHolder getAll(CallListListener callListener) {
        AuthJsonObjectRequest request = new AuthJsonObjectRequest(Request.Method.GET, serverUrl + modelUrl, null, callListener, callListener);
        //  addRequest(request);

        RequestHolder holder = new RequestHolder(request, this, callListener);

        return holder;
    }


    public RequestHolder getById(CallSingleListener callListener, int id) {
        AuthJsonObjectRequest request = new AuthJsonObjectRequest(Request.Method.GET, serverUrl + modelUrl + "/" + id, null, callListener, callListener);

        RequestHolder holder = new RequestHolder(request, this, callListener);

        return holder;
    }


    public RequestHolder add(CallSingleListener callListener, E model) {
        try {
            String objectJson = new JSONObject(this.gson.toJson(model)).toString();
            SmarterLogMAKER.w(objectJson.toString());
            AuthJsonObjectRequest request = new AuthJsonObjectRequest(Request.Method.POST, serverUrl + modelUrl, new JSONObject(this.gson.toJson(model)), callListener, callListener);
            // addRequest(request);

            RequestHolder holder = new RequestHolder(request, this, callListener);

            return holder;
        } catch (Exception e) {
            return null;
        }
    }


    public RequestHolder edit(CallSingleListener callListener, E model) {
        try {
            String objectJson = new JSONObject(this.gson.toJson(model)).toString();
            SmarterLogMAKER.w(objectJson.toString());
            AuthJsonObjectRequest request = new AuthJsonObjectRequest(Request.Method.PUT, serverUrl + modelUrl, new JSONObject(gson.toJson(model)), callListener, callListener);
           // addRequest(request);

            RequestHolder holder = new RequestHolder(request, this, callListener);

            return holder;
        } catch (Exception e) {
            return null;
        }
    }

    public RequestHolder delete(CallSingleListener callListener, int id) {
        try {
            AuthJsonObjectRequest request = new AuthJsonObjectRequest(Request.Method.DELETE, serverUrl + modelUrl + "/" + id, null, callListener, callListener);
            //addRequest(request);

            RequestHolder holder = new RequestHolder(request, this, callListener);

            return holder;
        } catch (Exception e) {
            return null;
        }
    }

    public RequestHolder getAll(CallListListener callListener, String url) {
        AuthJsonObjectRequest request = new AuthJsonObjectRequest(Request.Method.GET, serverUrl + url, null, callListener, callListener);
       // addRequest(request);

        RequestHolder holder = new RequestHolder(request, this, callListener);

        return holder;
    }

    ;

    public RequestHolder getById(CallSingleListener callListener, int id, String url) {
        AuthJsonObjectRequest request = new AuthJsonObjectRequest(Request.Method.GET, serverUrl + url + "/" + id, null, callListener, callListener);
       // addRequest(request);

        RequestHolder holder = new RequestHolder(request, this, callListener);

        return holder;
    }


    public RequestHolder getSingle(CallSingleListener callListener, String url) {
        AuthJsonObjectRequest request = new AuthJsonObjectRequest(Request.Method.GET, serverUrl + url, null, callListener, callListener);
       // addRequest(request);

        RequestHolder holder = new RequestHolder(request, this, callListener);

        return holder;
    }


    public RequestHolder add(CallSingleListener callListener, E model, String url) {
        try {
            String gson = new JSONObject(this.gson.toJson(model)).toString();
            SmarterLogMAKER.w(gson.toString());
            AuthJsonObjectRequest request = new AuthJsonObjectRequest(Request.Method.POST, serverUrl + url, new JSONObject(this.gson.toJson(model)), callListener, callListener);
           // addRequest(request);

            RequestHolder holder = new RequestHolder(request, this, callListener);

            return holder;
        } catch (Exception e) {
            return null;
        }
    }


    public RequestHolder edit(CallSingleListener callListener, E model, String url) {
        try {
            AuthJsonObjectRequest request = new AuthJsonObjectRequest(Request.Method.PUT, serverUrl + url, new JSONObject(gson.toJson(model)), callListener, callListener);
            //addRequest(request);

            RequestHolder holder = new RequestHolder(request, this, callListener);

            return holder;
        } catch (Exception e) {
            return null;
        }
    }

    public RequestHolder delete(CallSingleListener callListener, int id, String url) {
        try {
            AuthJsonObjectRequest request = new AuthJsonObjectRequest(Request.Method.DELETE, serverUrl + url + "/" + id, null, callListener, callListener);
            //addRequest(request);

            RequestHolder holder = new RequestHolder(request, this, callListener);

            return holder;
        } catch (Exception e) {
            return null;
        }
    }
}
