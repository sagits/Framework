package rockapps.com.library.volley;

import android.app.Activity;
import android.content.Context;
import android.widget.RelativeLayout;

import com.android.volley.VolleyError;

import org.json.JSONArray;

import java.util.List;

import rockapps.com.library.volley.mymodels.JsonMessageList;

public class CallListListenerArray<E> extends CallListenerArray<E, JsonMessageList<E>> {
    public String objectName = "records";

    public CallListListenerArray(Class<E> type) {
        super(type);
    }


    public CallListListenerArray(Context context, Class<E> type, Boolean saveLocal, String saveLocalName) {
        super(context, type, saveLocal, saveLocalName);
    }

    public CallListListenerArray(Activity activity, Class<E> type) {
        super(activity, type);
    }

    public CallListListenerArray(Activity activity, Class<E> type, RelativeLayout parentLayout) {
        super(activity, type, parentLayout);
    }

    public CallListListenerArray(Activity activity, Class<E> type, RelativeLayout parentLayout, OnDialogButtonClick onDialogButtonClick, Boolean saveLocal, String saveLocalName) {
        super(activity, type, parentLayout, onDialogButtonClick, saveLocal, saveLocalName);
    }

    public CallListListenerArray(Activity activity, Class<E> type, String message, OnDialogButtonClick onDialogButtonClick) {
        super(activity, type, message, onDialogButtonClick);
    }

    public CallListListenerArray(Activity activity, Class<E> type, OnDialogButtonClick onDialogButtonClick) {
        super(activity, type, onDialogButtonClick);
    }

    public CallListListenerArray(Activity activity, Class<E> type, String message) {
        super(activity, type, message);
    }

    public CallListListenerArray(Activity activity, Class<E> type, String message, OnDialogButtonClick onDialogButtonClick, Boolean saveLocal, String saveLocalName) {
        super(activity, type, message, onDialogButtonClick, saveLocal, saveLocalName);
    }

    public CallListListenerArray(Activity activity, Class<E> type, String message, OnDialogButtonClick onDialogButtonClick, String objectName) {
        super(activity, type, message, onDialogButtonClick);
        this.objectName = objectName;
    }

    public CallListListenerArray(Activity activity, Class<E> type, String message, OnDialogButtonClick onDialogButtonClick, Boolean saveLocal, String saveLocalName, String objectName) {
        super(activity, type, message, onDialogButtonClick, saveLocal, saveLocalName);
        this.objectName = objectName;
    }

    public CallListListenerArray(Activity activity, Class<E> type, String message, Boolean saveLocal, String saveLocalName) {
        super(activity, type, message, saveLocal, saveLocalName);
    }

    public CallListListenerArray(Activity activity, Class<E> type, RelativeLayout parentLayout, OnDialogButtonClick onDialogButtonClick) {
        super(activity, type, parentLayout, onDialogButtonClick);
    }

    @Override
    public void onErrorResponse(VolleyError error) {
        super.onErrorResponse(error);

    }

    @Override
    public void onResponse(JSONArray json) {
        super.onResponse(json);

    }

    @SuppressWarnings("unchecked")
    @Override
    public void onParse(JSONArray json) {


       this.jsonMessage = new JsonMessageList<E>();


        try {
            List<E> list = getList(type, json);
            if (saveLocal) {
                saveLocal(list, saveLocalName);
            }
            jsonMessage.setObject(list);
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    public List<E> getList(Class<E> type, JSONArray json) throws Exception {

        //   return gsonB.fromJson(removeAcentos(json.toString()), new JsonListHelper<E>(type));
        return gson.fromJson(json.toString(), new JsonListHelper<E>(type));
    }

}
