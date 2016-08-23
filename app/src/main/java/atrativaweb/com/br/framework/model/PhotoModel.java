package atrativaweb.com.br.framework.model;

import rockapps.com.library.model.MyModel;

/**
 * Created by Renato on 23/08/2016.
 */
public class PhotoModel extends MyModel {
    private String thumbnailUrl;
    private String imageUrl = "http://www.leighbones.com/wp-content/uploads/2015/07/vintage-beach.jpg";
    private String title;

    public String getThumbnailUrl() {
        return thumbnailUrl;
    }

    public void setThumbnailUrl(String thumbnailUrl) {
        this.thumbnailUrl = thumbnailUrl;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }
}
