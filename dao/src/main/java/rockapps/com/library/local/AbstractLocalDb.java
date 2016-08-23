package rockapps.com.library.local;

import android.content.Context;
import android.content.SharedPreferences;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import org.json.JSONArray;
import org.json.JSONObject;

import java.text.Normalizer;
import java.util.ArrayList;
import java.util.List;

import rockapps.com.library.model.MyModel;
import rockapps.com.library.volley.JsonListHelper;
import rockapps.com.library.volley.SmarterLogMAKER;

/**
 * Created by Renato on 28/01/2015.
 */
public abstract class AbstractLocalDb<E> {
    protected Context context;
    private Gson gson;
    private final String spName = "MySharedPreferences";

    protected AbstractLocalDb(Context context) {
        this.context = context;
        this.gson = new Gson();
    }

    public boolean getBoolPreference(String name) {
        Boolean config = null;
        try {
            config = (Boolean) get((Class<E>) Boolean.class, name);
        } catch (Exception e) {
            return false;
        }

        return config != null ? config : false;
    }

    public void saveBoolPreference(String name, Boolean value) {
        save((E) value, (Class<E>) Boolean.class, name);
    }

    public void save(E object, Class<E> type, String name) {
        SharedPreferences settings = context.getSharedPreferences(spName, 0);
        SharedPreferences.Editor editor = settings.edit();

        String objectJson = gson.toJson(object, type);

        editor.putString(name != null ? name : type.getSimpleName(), objectJson);
        editor.commit();
    }

    public void save(E object, Class<E> type) {
        save(object, type, null);
    }

    public void clearData() {
        SharedPreferences settings = context.getSharedPreferences(spName, 0);
        SharedPreferences.Editor editor = settings.edit();
        editor.clear();
        editor.commit();
    }

    public E get(Class<E> type, String name) {
        SharedPreferences settings = context.getSharedPreferences(spName, 0);

        E model;
        String objectJson = settings.getString(name != null ? name : type.getSimpleName(), null);
        if (objectJson != null) {
            model = gson.fromJson(objectJson, type);
        } else {
            return null;
        }

        return model;

    }

    public E get(Class<E> type) {
        return get(type, null);
    }

    public E getDefault(Class<E> type) {
        return get(type, type.getSimpleName());
    }

    public void clearObject(String name) {
        SharedPreferences settings = context.getSharedPreferences(spName, 0);
        SharedPreferences.Editor editor = settings.edit();
        editor.remove(name);
        editor.commit();
    }

    public void clearSingleObject(Class<E> type, String name) {
        clearObject(name != null ? name + "SingleObject" : type.getSimpleName() + "SingleObject");
    }

    private List<E> getList(Class<E> type, JSONArray json) throws Exception {

        return gson.fromJson(json.toString(), new JsonListHelper<E>(type));
    }

    private E getObject(Class<E> type, JSONObject json) throws Exception {

        return gson.fromJson(json.toString(), type);
    }

    public void saveList(List<E> objectList, Class<E> type, String name) {
        Gson gsonB = new GsonBuilder().setDateFormat("yyyy-MM-dd HH:mm:ss").create();

        String json = new Gson().toJson(objectList);
        System.out.println(json);

        SharedPreferences settings = context.getSharedPreferences(spName, 0);
        SharedPreferences.Editor editor = settings.edit();

        editor.putString(name != null ? name : type.getSimpleName() + ".list", json);
        editor.commit();
    }

    public void saveToList(E object, Class<E> type, String name) {
        List<E> list = getList(type, name);
        boolean exist = false;

        if (list != null) {
            if (object instanceof MyModel) {
                for (int x = 0; x < list.size(); x++) {
                    MyModel o = (MyModel) list.get(x);
                    if (o.getId() == ((MyModel) object).getId()) {
                        list.set(x, (E) object);
                        exist = true;
                        SmarterLogMAKER.w("Local list of "  + name +" updated");
                        break;
                    }
                }
            }
        } else {
            list = new ArrayList<E>();
        }

        if (!exist) {
            SmarterLogMAKER.w("Item added to local list of " + name);
            list.add((E) object);
        }

        saveList(list, type, name);

    }

    public void removeFromList(E object, Class<E> type, String name) {
        List<E> list = getList(type, name);

        if (list != null) {
            if (object instanceof MyModel) {
                for (int x = 0; x < list.size(); x++) {
                    MyModel o = (MyModel) list.get(x);
                    if (o.getId() == ((MyModel) object).getId()) {
                        list.remove(x);
                        break;
                    }
                }
            }
        } else {
            list = new ArrayList<E>();
        }

        saveList(list, type, name);

    }

    public void updateToList(E object, Class<E> type, String name) {
        saveToList(object, type, name);
    }

    public List<E> getList(Class<E> type, String name) {
        SharedPreferences settings = context.getSharedPreferences(spName, 0);

        String listJson = settings.getString(name != null ? name : type.getSimpleName(), null);
        if (listJson != null) {
            try {
                List<E> list = getList(type, new JSONArray(listJson));
                return list;
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return null;
    }

    public E getObjectFromList(Class<E> type, String name, int id) {
        List<E> objects = getList(type, name);
        if (objects != null) {
            for (E object : objects) {
                if (((MyModel) object).getId() == id) {
                    return object;
                }
            }
        }
        return null;
    }

    public List<E> getDefaultList(Class<E> type) {
        return getList(type, type.getSimpleName());
    }

    public String removeAcentos(String str) {

        str = Normalizer.normalize(str, Normalizer.Form.NFD);
        str = str.replaceAll("[^\\p{ASCII}]", "");
        return str;

    }
}
