package atrativaweb.com.br.framework.fragment.generic;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.android.volley.VolleyError;
import com.google.gson.Gson;

import org.json.JSONObject;

import butterknife.ButterKnife;
import rockapps.com.library.volley.CallSingleListener;
import rockapps.com.library.volley.GenericAbstractDao;
import rockapps.com.library.volley.RestAuthDao;
import rockapps.com.library.volley.mymodels.RequestHolder;

/**
 * Created by Renato on 13/07/2016.
 */
public abstract class GenericDaoSingleFragment<E> extends GenericSingleFragment<E> {
    protected GenericAbstractDao<E> dao;
    protected long id;
    protected boolean getDataFromLocalData;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        rootView = inflater.inflate(layoutXml, container, false);
        ButterKnife.bind(this, rootView);

        setHasOptionsMenu(true);

        if (getDataFromLocalData) {
            getDataFromLocalData();
        } else {
            getData();
        }

        return rootView;
    }


    protected void getDataFromLocalData() {
        if (getArguments() != null && getArguments().containsKey("object")) {
            object = new Gson().fromJson(getArguments().getString("object"), classType);
            setData();
        }
    }


    @Override
    protected void getData() {

        CallSingleListener<E> callListener = new CallSingleListener<E>(getActivity(), classType, (RelativeLayout) rootView) {

            @Override
            public void onPreExecute() {
                super.onPreExecute();
                beforeRequest(this);
            }

            @Override
            public void onErrorResponse(VolleyError error) {
                super.onErrorResponse(error);
                stopLayoutReflesh();
            }

            @Override
            public void onResponse(JSONObject response) {
                super.onResponse(response);
                try {

                    if (this.success()) {
                        object = this.jsonMessage.getObject();
                        setData();
                    } else {
                        Toast.makeText(this.activity.getApplicationContext(),
                                this.getErrorMessage(),
                                Toast.LENGTH_LONG).show();
                    }
                    stopLayoutReflesh();

                } catch (Exception e1) {
                    // TODO Auto-generated catch block
                    e1.printStackTrace();
                }
            }
        };

        getDefaultGetDataRequest(callListener).doRequest();
    }

    protected void stopLayoutReflesh() {
        try {
            swipeRefreshLayout.setRefreshing(false);
        } catch (Exception e) {
        }
    }

    protected void beforeRequest(CallSingleListener<E> callListListener) {

    }

    protected RequestHolder getDefaultGetDataRequest(CallSingleListener<E> callListener) {
        return ((RestAuthDao) dao).getById(callListener, (int) id);
    }
}