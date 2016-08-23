package rockapps.com.library.volley.mymodels;

import com.google.gson.annotations.SerializedName;

/**
 * Created by Renato on 09/11/2014.
 */
public class JsonMessage<E> {
    protected E object;

    @SerializedName("meta")
    protected MetaModel meta = new MetaModel();

    public boolean success() {

        return meta != null && meta.getStatus() != null && meta.getStatus().equals("SUCCESS");
    }

    public MetaModel getMeta() {
        return meta;
    }

    public void setMeta(MetaModel meta) {
        this.meta = meta;
    }

    public String getMessage() {
        return meta.getStatus();
    }

    public E getObject() {
        return object;
    }

    public void setObject(E object) {
        this.object = object;
    }
}
