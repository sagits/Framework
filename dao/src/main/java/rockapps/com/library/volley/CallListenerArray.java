package rockapps.com.library.volley;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.NetworkError;
import com.android.volley.NoConnectionError;
import com.android.volley.Request;
import com.android.volley.Response.ErrorListener;
import com.android.volley.Response.Listener;
import com.android.volley.ServerError;
import com.android.volley.TimeoutError;
import com.android.volley.VolleyError;
import com.google.gson.Gson;

import org.json.JSONArray;
import org.json.JSONObject;

import java.text.Normalizer;
import java.util.Arrays;
import java.util.List;

import rockapps.com.library.R;
import rockapps.com.library.local.LocalDbImplement;
import rockapps.com.library.model.MyModel;
import rockapps.com.library.volley.mymodels.JsonMessage;
import rockapps.com.library.volley.mymodels.RequestHolder;

public abstract class CallListenerArray<E, T extends JsonMessage> implements ErrorListener, Listener<JSONArray>, VolleyOnPreExecute {
    public Activity activity;
    public RelativeLayout parentLayout;
    public Gson gson = new Gson();
    protected Class<E> type;
    protected ProgressDialog dialog;
    protected String defaultErrorMessage = "Houve um erro, por favor tente novamente.";
    protected String defaultErrorTitle = "Problema de conexão";
    protected String defaultSuccessButtonMessage = "Tentar novamente";
    protected String defaultErrorButtonMessage = "Cancelar";
    protected OnDialogButtonClick onDialogButtonClick;
    public String saveLocalName;
    public Boolean saveLocal = false;
    protected Request request;
    protected String objectJson;
    public T jsonMessage;


    private View loadingView;
    private View errorView;
    public DefaultDialog errorDialog;
    private Context context;
    private RequestHolder requestHolder;

    public CallListenerArray() {
        onPreExecute();
    }


    private void setErrorMessages() {
        if (activity != null) {
            defaultErrorMessage = activity.getString(R.string.loading_error);
            defaultErrorTitle = activity.getString(R.string.error_connection);
            defaultSuccessButtonMessage = activity.getString(R.string.try_again);
            defaultErrorButtonMessage = activity.getString(R.string.cancel);
        }
    }

    public CallListenerArray(Class<E> type) {
        this();
        this.type = type;
    }

    public CallListenerArray(Context context, Class<E> type, Boolean saveLocal, String saveLocalName) {
        this();
        this.context = context;
        this.type = type;
        this.saveLocal = saveLocal;
        this.saveLocalName = saveLocalName;
        setErrorMessages();
    }

    public CallListenerArray(Activity activity, Class<E> type) {
        this();
        this.activity = activity;
        this.type = type;
    }

    public CallListenerArray(Activity activity, Class<E> type, OnDialogButtonClick onDialogButtonClick) {
        this();
        this.onDialogButtonClick = onDialogButtonClick;
        this.activity = activity;
        this.type = type;
        setErrorMessages();
    }

    public CallListenerArray(Activity activity, Class<E> type, RelativeLayout parentLayout) {
        this();
        this.activity = activity;
        this.parentLayout = parentLayout;
        this.type = type;
        setErrorMessages();
    }

    public CallListenerArray(Activity activity, Class<E> type, String message) {
        this();
        this.activity = activity;
        dialog = new ProgressDialog(activity);
        dialog.setMessage(message);
        this.type = type;
        setErrorMessages();
    }

    public CallListenerArray(Activity activity, Class<E> type, String message, OnDialogButtonClick onDialogButtonClick) {
        this();
        this.onDialogButtonClick = onDialogButtonClick;
        this.activity = activity;
        if (message != null) {
            dialog = new ProgressDialog(activity);
            dialog.setMessage(message);
        }
        this.type = type;
        setErrorMessages();
    }

    public CallListenerArray(Activity activity, Class<E> type, RelativeLayout parentLayout, OnDialogButtonClick onDialogButtonClick) {
        this();
        this.activity = activity;
        this.onDialogButtonClick = onDialogButtonClick;
        this.parentLayout = parentLayout;
        this.type = type;
        setErrorMessages();
    }

    public CallListenerArray(Activity activity, Class<E> type, RelativeLayout parentLayout, OnDialogButtonClick onDialogButtonClick, Boolean saveLocal, String saveLocalName) {
        this();
        this.activity = activity;
        this.onDialogButtonClick = onDialogButtonClick;
        this.parentLayout = parentLayout;
        this.type = type;
        this.saveLocal = saveLocal;
        this.saveLocalName = saveLocalName;
        setErrorMessages();
    }

    public CallListenerArray(Activity activity, Class<E> type, String message, OnDialogButtonClick onDialogButtonClick, Boolean saveLocal, String saveLocalName) {
        this();
        this.onDialogButtonClick = onDialogButtonClick;
        this.activity = activity;
        dialog = new ProgressDialog(activity);
        dialog.setMessage(message);
        dialog.setCancelable(false);
        this.type = type;
        this.saveLocal = saveLocal;
        this.saveLocalName = saveLocalName;
        setErrorMessages();
    }

    public CallListenerArray(Activity activity, Class<E> type, String message, Boolean saveLocal, String saveLocalName) {
        this();
        this.activity = activity;
        if (message != null) {
            dialog = new ProgressDialog(activity);
            dialog.setMessage(message);
            dialog.setCancelable(false);
        }
        this.type = type;
        this.saveLocal = saveLocal;
        this.saveLocalName = saveLocalName;
        setErrorMessages();
    }


    @Override
    public void onErrorResponse(VolleyError error) {

        try {
            String responseBody = new String(error.networkResponse.data, "utf-8");
            SmarterLogMAKER.w("Error: " + new JSONObject(responseBody).toString(4));
            onParse(new JSONArray(responseBody));
        } catch (Exception e) {
            e.printStackTrace();
        }

        onPostExecute("error");

        String errorMsg = "";
        if (error instanceof TimeoutError) {
            errorMsg = "TimeoutError";
        } else if (error instanceof ServerError) {
            errorMsg = "isServerProblem";

        } else if (error instanceof AuthFailureError) {
            errorMsg = "isAuthProblem";
        } else if (error instanceof NetworkError || error instanceof NoConnectionError) {
            errorMsg = "isNetworkProblem";
        }

        Log.w("Error type: ", errorMsg);
        Log.w("Error msg ", error.getMessage() + " ");


        final Integer httpStatusCode = error.networkResponse.statusCode;
        Integer[] acceptedStatusCodes = new Integer[]{400, 403};

        if (Arrays.asList(acceptedStatusCodes).contains(httpStatusCode) && jsonMessage != null) {
            removeErrorDialog();
        }


    }

    public void onPreExecute() {
        if (parentLayout != null) {
            inflateLoadingScreen(parentLayout);
        } else if (dialog != null) {
            dialog.setCancelable(false);
            dialog.show();
        }
    }

    protected void onPostExecute(String status) {
        removeDialog();
        if (parentLayout != null) {
            removeLoadingScreen(parentLayout);
            if (status.equals("error")) {
                inflateErrorScreen(parentLayout);
            }
        } else if (status.equals("error") && onDialogButtonClick != null) {
            createErrorDialog();
        }


        if (status.equals("success")) {
            requestHolder.onDone();
        }
    }

    @Override
    public void onResponse(JSONArray json) {
        try {
            SmarterLogMAKER.w("Success: " + json.toString(4));
        } catch (Exception e) {
        }
        onPostExecute("success");
        onParse(json);

    }

    public void onParse(JSONArray json) {
    }

    public void inflateLoadingScreen(RelativeLayout fragmentLayout) {
        LayoutInflater inflater = activity.getLayoutInflater();
        View loadingView = (View) inflater.inflate(R.layout.loading, fragmentLayout, false);
        fragmentLayout.addView(loadingView);
    }

    public void removeLoadingScreen(View fragmentLayout) {
        //LayoutInflater inflater = getLayoutInflater();
        View loadingView = (View) fragmentLayout.findViewById(R.id.loadingLayout);
        ((RelativeLayout) fragmentLayout).removeView(loadingView);
    }

    public void inflateErrorScreen(RelativeLayout fragmentLayout) {
        LayoutInflater inflater = activity.getLayoutInflater();
        View loadingView = (View) inflater.inflate(R.layout.loading_error, fragmentLayout, false);
        ((TextView) loadingView.findViewById(R.id.errorTextView)).setText(getErrorMessage());
        fragmentLayout.addView(loadingView);

        if (onDialogButtonClick == null) {
            setTryAgainButton(loadingView);
        }
    }

    public void removeErrorScreen(View fragmentLayout) {
        removeLoadingScreen(fragmentLayout);
    }

    public void setTryAgainButton(View loadingView) {
        Button tryAgainButton = (Button) loadingView.findViewById(R.id.tryAgainButton);
        tryAgainButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                removeErrorScreen(parentLayout);
                if (onDialogButtonClick != null) {
                    onDialogButtonClick.onPositiveClick();
                }
            }
        });
    }

    public void removeDialog() {
        try {
            dialog.dismiss();
        } catch (Exception e) {
        }
    }


    public static interface VolleyOnPreExecute {
        void onPreExecute();
    }

    public void createErrorDialog() {
        try {
            errorDialog = DefaultDialog.newInstance(getErrorTitle(), getErrorMessage(), defaultSuccessButtonMessage, defaultErrorButtonMessage, onDialogButtonClick);
            errorDialog.show(activity.getFragmentManager(), null);
            errorDialog.onClick = new OnDialogButtonClick() {
                @Override
                public void onPositiveClick() {

                    getRequestHolder().doRequest();
                }

                @Override
                public void onNegativeClick() {
                }
            };
        } catch (Exception e) {
            SmarterLogMAKER.w("Error trying to create dialog. Please check your activity");
        }
    }

    public void removeErrorDialog() {
        try {
            errorDialog.dismiss();
        } catch (Exception e) {
        }
    }

    public void saveLocal(E object, String name) {
        new LocalDbImplement<E>(activity.getApplicationContext()).save(object, type, name == null ? type.getSimpleName() : name);
    }

    public void saveLocal(List<E> objects, String name) {
        name = name == null ? type.getSimpleName() : name;
        Context myContext = activity != null ? activity.getApplicationContext() : context;

        new LocalDbImplement<E>(myContext).saveList(objects, type, name);
    }

    public E getLocalObjectFromList(Long id) {
        return getLocalObjectFromList(id.intValue());
    }

    public E getLocalObjectFromList(int id) {
        List<E> localList = getLocalList();

        try {
            for (Object o : localList) {
                if (((MyModel) o).getId() == id) {
                    return (E) o;
                }
            }
        } catch (Exception e) {
            return null;
        }
        return null;
    }

    public void saveToList(E object) {
        String name = type.getSimpleName();
        new LocalDbImplement<E>(activity.getApplicationContext()).saveToList(object, type, name);
    }

    public E getLocalObjectFromList(int id, String listName) {
        saveLocalName = listName;
        List<E> localList = getLocalList();

        try {
            for (Object o : localList) {
                if (((MyModel) o).getId() == id) {
                    return (E) o;
                }
            }
        } catch (Exception e) {
            return null;
        }
        return null;
    }

    public E getLocalObject() {
        return new LocalDbImplement<E>(activity.getApplicationContext()).get(type, saveLocalName != null ? saveLocalName : type.getSimpleName());
    }

    public List<E> getLocalList() {
        String name = saveLocalName != null ? saveLocalName : type.getSimpleName();
        return new LocalDbImplement<E>(activity.getApplicationContext()).getList(type, name);
    }

    public Request getRequest() {
        return request;
    }

    public void setRequest(Request request) {
        this.request = request;
    }

    public String getObjectJson() {
        return objectJson;
    }

    public void setObjectJson(String objectJson) {
        this.objectJson = objectJson;
    }

    public RequestHolder getRequestHolder() {
        return requestHolder;
    }

    public void setRequestHolder(RequestHolder requestHolder) {
        this.requestHolder = requestHolder;
    }

    public void start() {
        onPreExecute();
    }

    public void showErrorInDialog() {
        Toast.makeText(this.activity.getApplicationContext(),
                this.getErrorMessage(),
                Toast.LENGTH_LONG).show();
    }

    public String removeAcentos(String str) {

        str = Normalizer.normalize(str, Normalizer.Form.NFD);
        str = str.replaceAll("[^\\p{ASCII}]", "");
        return str;

    }


    public String getErrorMessage() {
        try {
            if (jsonMessage.getMeta().getErrorModel().getValidationMessages() != null &&
                    jsonMessage.getMeta().getErrorModel().getValidationMessages().length > 0) {
                return jsonMessage.getMeta().getErrorModel().getValidationMessages()[0];
            }
            return jsonMessage.getMeta().getErrorModel().getUserMessage();
        } catch (Exception e) {
            return defaultErrorMessage;
        }
    }

    protected String getErrorTitle() {
        try {
            return jsonMessage.getMeta().getErrorModel().getUserMessage();
        } catch (Exception e) {
            return defaultErrorTitle;
        }
    }


    public boolean success() {
        return jsonMessage.success();
    }

    public E getObject() {
        return (E) jsonMessage.getObject();
    }

    public List<E> getObjects() {
        return (List<E>) jsonMessage.getObject();
    }
}
