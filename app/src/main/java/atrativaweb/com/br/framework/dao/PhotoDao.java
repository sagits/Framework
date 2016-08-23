package atrativaweb.com.br.framework.dao;

import android.content.Context;

import atrativaweb.com.br.framework.model.PhotoModel;
import rockapps.com.library.volley.RestAuthDao;

/**
 * Created by Renato on 23/08/2016.
 */
public class PhotoDao extends RestAuthDao<PhotoModel> {


    public PhotoDao(Context activity) {
        super(activity);
        modelUrl = "57bcc9fa0f0000a620e0c523"; // fake restful api
    }
}
