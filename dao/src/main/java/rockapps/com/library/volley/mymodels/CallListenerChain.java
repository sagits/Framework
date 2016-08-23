package rockapps.com.library.volley.mymodels;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Renato on 13/01/2016.
 */
public class CallListenerChain {
    List<RequestHolder> requestHolders = new ArrayList<>();
    private Callback callback;

    public void add(RequestHolder requestHolder) {
        requestHolders.add(requestHolder);
        requestHolder.setCallListenerChain(this);
    }

    public CallListenerChain() {

    }

    public CallListenerChain(RequestHolder... requestHolders) {
        for (RequestHolder requestHolder : requestHolders) {
            add(requestHolder);
            requestHolder.setCallListenerChain(this);
        }
    }


    public void startQueue(Callback callback) {
        this.callback = callback;
        startQueue();
    }
    public void startQueue() {
        if (requestHolders.size() > 0) {

            requestHolders.get(0).doRequest();
        }
    }

    public void doNextRequest(RequestHolder requestHolder) {
        int position = requestHolders.indexOf(requestHolder);

        if (position != -1 && requestHolders.size()  > position +1) {
            requestHolders.get(position+1).doRequest();
        }
        else {
            requestHolders.clear();
            if (callback != null) {
                callback.onSucces();
            }
        }
    }

    void test() {
      /*  add(new TaskDao(ApplicationController.getInstance().getApplicationContext()).getAll(new CallListListener(TaskModel.class) {
            @Override
            public void onResponse(JSONObject response) {
                super.onResponse(response);

                getCallListenerChain.doNextRequest(this.requestHolder);
            }
        }));

        add(new TaskDao(ApplicationController.getInstance().getApplicationContext()).getAll(new CallListListener(TaskModel.class)));  */

    }

    public interface Callback {
        void onSucces();
    }
}
