package atrativaweb.com.br.framework.model;

import rockapps.com.library.model.MyModel;

/**
 * Created by Renato on 23/08/2016.
 */
public class PostModel extends MyModel {
    private String title;
    private String body;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }
}
