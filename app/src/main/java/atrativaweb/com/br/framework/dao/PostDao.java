package atrativaweb.com.br.framework.dao;

import android.content.Context;

import atrativaweb.com.br.framework.model.PostModel;
import rockapps.com.library.volley.RestAuthDao;

/**
 * Created by Renato on 23/08/2016.
 */
public class PostDao extends RestAuthDao<PostModel> {


    public PostDao(Context activity) {
        super(activity);
        modelUrl = "57bcc7410f00004c20e0c51f"; // api restfull fake
    }


}
