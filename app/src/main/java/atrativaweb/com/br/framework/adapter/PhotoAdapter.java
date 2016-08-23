package atrativaweb.com.br.framework.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

import atrativaweb.com.br.framework.R;
import atrativaweb.com.br.framework.model.PhotoModel;
import butterknife.Bind;

/**
 * Created by Renato on 23/08/2016.
 */
public class PhotoAdapter extends GenericRecycleAdapter<PhotoModel, PhotoAdapter.AdapterViewHolder> {


    public PhotoAdapter(Context context, List<PhotoModel> list) {
        super(context, list);
        layout = R.layout.adapter_photo;
    }

    @Override
    protected AdapterViewHolder getAdapterHolder(ViewGroup parent) {
        return new AdapterViewHolder(parent);
    }

    @Override
    public void onBindViewHolder(AdapterViewHolder holder, int position) {
        final PhotoModel item = list.get(position);

        holder.titleView.setText(item.getTitle());
        holder.descriptionView.setText(item.getDescription());
        loadImage(item.getImageUrl(), holder.imageView);

        if (getOnItemClickListener() != null) {
            holder.setOnClickListener(position, item, getOnItemClickListener());
        }
    }

    public class AdapterViewHolder extends GenericRecycleAdapter.AdapterViewHolder {
        @Bind(R.id.title)
        TextView titleView;
        @Bind(R.id.description)
        TextView descriptionView;
        @Bind(R.id.image)
        ImageView imageView;

        public AdapterViewHolder(View itemView) {
            super(itemView);
        }
    }
}
