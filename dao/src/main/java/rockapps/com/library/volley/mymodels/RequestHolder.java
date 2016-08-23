package rockapps.com.library.volley.mymodels;

import com.android.volley.Request;

import rockapps.com.library.volley.CallListener;
import rockapps.com.library.volley.GenericAbstractDao;

/**
 * Created by Renato on 13/01/2016.
 */
public class RequestHolder {
    private Request request;
    private GenericAbstractDao dao;
    private CallListener callListener;
    private CallListenerChain callListenerChain;
    private int timeOut;

    public RequestHolder(Request request, GenericAbstractDao dao, CallListener callListener) {
        this.request = request;
        this.dao = dao;
        this.callListener = callListener;
        callListener.setRequestHolder(this);
    }

    public void doRequest() {

        dao.startRequest(this);
    }

    public void onDone() {
        if (callListenerChain != null) {
            callListenerChain.doNextRequest(this);
        }
    }


    public Request getRequest() {
        return request;
    }

    public void setRequest(Request request) {
        this.request = request;
    }

    public GenericAbstractDao getDao() {
        return dao;
    }

    public void setDao(GenericAbstractDao dao) {
        this.dao = dao;
    }

    public CallListener getCallListener() {
        return callListener;
    }

    public void setCallListener(CallListener callListener) {
        this.callListener = callListener;
    }

    public CallListenerChain getCallListenerChain() {
        return callListenerChain;
    }

    public void setCallListenerChain(CallListenerChain callListenerChain) {
        this.callListenerChain = callListenerChain;
    }

    public int getTimeOut() {
        return timeOut;
    }

    public void setTimeOut(int timeOut) {
        this.timeOut = timeOut;
    }
}
