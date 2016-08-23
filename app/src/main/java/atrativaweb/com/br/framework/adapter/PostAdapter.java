package atrativaweb.com.br.framework.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

import atrativaweb.com.br.framework.R;
import atrativaweb.com.br.framework.model.PostModel;
import butterknife.Bind;

/**
 * Created by Renato on 23/08/2016.
 */
public class PostAdapter extends GenericRecycleAdapter<PostModel, PostAdapter.AdapterViewHolder> {


    public PostAdapter(Context context, List<PostModel> list) {
        super(context, list);
        layout = R.layout.adapter_post;
    }

    @Override
    protected AdapterViewHolder getAdapterHolder(ViewGroup parent) {
        return new AdapterViewHolder(parent);
    }

    @Override
    public void onBindViewHolder(AdapterViewHolder holder, int position) {
        final PostModel item = list.get(position);

        holder.titleView.setText(item.getTitle());
        holder.descriptionView.setText(item.getBody());

        if (getOnItemClickListener() != null) {
            holder.setOnClickListener(position, item, getOnItemClickListener());
        }
    }

    public class AdapterViewHolder extends GenericRecycleAdapter.AdapterViewHolder {
        @Bind(R.id.title)
        TextView titleView;
        @Bind(R.id.description)
        TextView descriptionView;

        public AdapterViewHolder(View itemView) {
            super(itemView);
        }
    }
}
