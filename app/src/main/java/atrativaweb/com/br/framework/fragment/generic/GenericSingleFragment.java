package atrativaweb.com.br.framework.fragment.generic;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import com.google.gson.Gson;

import butterknife.ButterKnife;

/**
 * Created by Renato on 15/04/2016.
 */
public abstract class GenericSingleFragment<E> extends GenericFragment<E> {

    protected E object;

    public GenericSingleFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        rootView = inflater.inflate(layoutXml, container, false);
        ButterKnife.bind(this, rootView);

        setHasOptionsMenu(true);

        getData();
        return rootView;
    }

    protected void getData() {
        if (getArguments() != null && getArguments().containsKey("object")) {
            object = new Gson().fromJson(getArguments().getString("object"), classType);
            setData();
        }
    }

    protected void setData() {


    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        menu.clear();
        inflater.inflate(menuXml, menu);




    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        return super.onOptionsItemSelected(item);
    }

}