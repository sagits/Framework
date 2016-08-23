package atrativaweb.com.br.framework.fragment.generic;

import android.widget.RelativeLayout;
import android.widget.Toast;

import com.android.volley.VolleyError;

import org.json.JSONObject;

import rockapps.com.library.volley.CallListListener;
import rockapps.com.library.volley.RestAuthDao;
import rockapps.com.library.volley.mymodels.RequestHolder;

/**
 * Created by Renato on 12/07/2016.
 */
public abstract class GenericDaoListFragment<E> extends GenericListFragment<E> {
    protected RestAuthDao<E> dao;


    @Override
    protected void getData() {

        CallListListener<E> callListener = new CallListListener<E>(getActivity(), classType, (RelativeLayout) rootView) {

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
                        list = this.jsonMessage.getObject();
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

    protected void beforeRequest(CallListListener<E> callListListener) {

    }

    protected RequestHolder getDefaultGetDataRequest(CallListListener callListener) {
        return dao.getAll(callListener);
    }
}
