package atrativaweb.com.br.framework.fragment.generic;

import android.app.Activity;
import android.view.LayoutInflater;
import android.widget.BaseAdapter;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by renato on 16/03/2015.
 */
public abstract class GenericAdapter<E> extends BaseAdapter {


    protected Activity activity;
    protected List<E> list;
    protected List<E> originalList;
    protected static LayoutInflater inflater = null;


    public GenericAdapter(Activity a, List<E> list) {
        activity = a;
        this.list = list;
        this.originalList = list;
        inflater = LayoutInflater.from(a.getApplicationContext());
    }

    public GenericAdapter(Activity a, ArrayList<E> list) {
        activity = a;
        this.list = list;
        this.originalList = list;
        inflater = LayoutInflater.from(a.getApplicationContext());
    }

    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public E getItem(int i) {
        return list.get(i);
    }

    public void addItem(E object) {
        list.add(object);
        notifyDataSetChanged();
    }

    public void addItems(List<E> objects) {
        for (Object object : objects) {
            addItem((E) object);
        }
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }


}