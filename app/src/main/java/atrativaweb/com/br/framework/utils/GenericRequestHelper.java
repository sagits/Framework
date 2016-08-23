package atrativaweb.com.br.framework.utils;

import android.app.Activity;
import android.widget.Toast;

import com.android.volley.VolleyError;

import org.json.JSONObject;

import rockapps.com.library.volley.CallListListener;
import rockapps.com.library.volley.CallListener;
import rockapps.com.library.volley.CallSingleListener;
import rockapps.com.library.volley.OnDialogButtonClick;
import rockapps.com.library.volley.mymodels.RequestHolder;

/**
 * Created by Renato on 27/07/2016.
 */
public class GenericRequestHelper {

    private  <E> void doListRequest(final Activity activity, final Class<E> classType, final OnListRequest<E> onRequest, final String loadingMessage) {

        OnDialogButtonClick onDialogButtonClick = new OnDialogButtonClick() {
            @Override
            public void onPositiveClick() {
                doListRequest(activity, classType, onRequest, loadingMessage);
            }

            @Override
            public void onNegativeClick() {

            }
        };

        CallListListener<E> callListener = new CallListListener<E>(activity, classType, loadingMessage, onDialogButtonClick) {

            @Override
            public void onErrorResponse(VolleyError error) {
                super.onErrorResponse(error);

                if (onRequest.getClass() == OnListRequestWithError.class) {
                    ((OnListRequestWithError)onRequest).onError(this);
                }
            }

            @Override
            public void onPreExecute() {
                super.onPreExecute();
            }

            @Override
            public void onResponse(JSONObject response) {
                super.onResponse(response);
                try {

                    if (this.success()) {
                        onRequest.onSuccess(this);
                    } else {
                        Toast.makeText(this.activity.getApplicationContext(),
                                this.getErrorMessage(),
                                Toast.LENGTH_LONG).show();
                    }

                } catch (Exception e1) {
                    // TODO Auto-generated catch block
                    e1.printStackTrace();
                }
            }
        };

        onRequest.doRequest(callListener).doRequest();
    }

    private <E> void doSingleRequest(final Activity activity, final Class<E> classType, final OnSingleRequest<E> onRequest, final String loadingMessage) {

        OnDialogButtonClick onDialogButtonClick = new OnDialogButtonClick() {
            @Override
            public void onPositiveClick() {
                doSingleRequest(activity, classType, onRequest, loadingMessage);
            }

            @Override
            public void onNegativeClick() {

            }
        };

        final CallSingleListener<E> callListener = new CallSingleListener<E>(activity, classType, loadingMessage, onDialogButtonClick) {

            @Override
            public void onErrorResponse(VolleyError error) {
                super.onErrorResponse(error);

                if (onRequest.getClass() == OnSingleRequestWithError.class) {
                    ((OnSingleRequestWithError)onRequest).onError(this);
                }
            }

            @Override
            public void onPreExecute() {
                super.onPreExecute();
            }

            @Override
            public void onResponse(JSONObject response) {
                super.onResponse(response);
                try {

                    if (this.success()) {
                        onRequest.onSuccess(this);
                    } else {
                        Toast.makeText(this.activity.getApplicationContext(),
                                this.getErrorMessage(),
                                Toast.LENGTH_LONG).show();
                    }

                } catch (Exception e1) {
                    // TODO Auto-generated catch block
                    e1.printStackTrace();
                }
            }
        };

        onRequest.doRequest(callListener).doRequest();
    }

    public <E> void singleRequest(final Activity activity, final Class<E> classType, final String loadingMessage, final OnSingleRequest<E> onRequest) {
        doSingleRequest(activity, classType, onRequest, loadingMessage);
    }

    public <E> void listRequest(final Activity activity, final Class<E> classType, final String loadingMessage, final OnListRequest<E> onRequest) {
        doListRequest(activity, classType, onRequest, loadingMessage);
    }

    public <E> void singleRequest(final Activity activity, final Class<E> classType, final String loadingMessage, final OnSingleRequestWithError<E> onRequest) {
        doSingleRequest(activity, classType, onRequest, loadingMessage);
    }

    public <E> void listRequest(final Activity activity, final Class<E> classType, final String loadingMessage, final OnListRequestWithError<E> onRequest) {
        doListRequest(activity, classType, onRequest, loadingMessage);
    }


    public static interface OnRequest<E extends CallListener> {
        RequestHolder doRequest(E callListener);
    }

    public static interface OnListRequest<E> extends OnRequest<CallListListener> {
        void onSuccess(CallListListener<E> callListListener);
    }

    public static interface OnListRequestWithError<E> extends OnListRequest<E> {
        void onError(CallListListener<E> callListListener);
    }

    public static interface OnSingleRequest<E> extends OnRequest<CallSingleListener> {
        void onSuccess(CallSingleListener<E> callListListener);
    }

    public static interface OnSingleRequestWithError<E> extends OnSingleRequest<E> {
        void onError(CallSingleListener<E> callListListener);
    }

}
