package atrativaweb.com.br.framework.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;

import com.squareup.picasso.Picasso;

import java.util.List;

import atrativaweb.com.br.framework.R;
import butterknife.ButterKnife;

/**
 * Created by Renato on 15/04/2016.
 */
public abstract class GenericRecycleAdapter<E, G extends GenericRecycleAdapter.AdapterViewHolder> extends RecyclerView.Adapter<G> implements Filterable
{
    protected List<E> list;
    protected List<E> originalList;
    protected Context context;
    protected int layout;
    protected OnItemClickListener<E> onItemClickListener;
    protected OnItemClickListener<E> onItemLongClickListener;

    public GenericRecycleAdapter(Context context,
                                 List<E> list)
    {
        this.originalList = list;
        this.list = list;
        this.context = context;
    }

    @Override
    public G onCreateViewHolder(ViewGroup parent, int viewType)
    {
        View layoutView = LayoutInflater.from(parent.getContext()).inflate(
                layout, parent, false);
        G rcv = getAdapterHolder((ViewGroup) layoutView);

        return rcv;
    }

    protected void loadImage(String url, ImageView imageView) {
        Picasso.with(context).load(url).placeholder(R.drawable.bg_loading).into(imageView);
    }

    protected abstract G getAdapterHolder(ViewGroup parent);

    @Override
    public int getItemCount()
    {
        return this.list.size();
    }

    public OnItemClickListener<E> getOnItemClickListener() {
        return onItemClickListener;
    }

    public void setOnItemClickListener(OnItemClickListener<E> onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }

    public OnItemClickListener<E> getOnItemLongClickListener() {
        return onItemLongClickListener;
    }

    public void setOnItemLongClickListener(OnItemClickListener<E> onItemLongClickListener) {
        this.onItemLongClickListener = onItemLongClickListener;
    }

    public abstract class AdapterViewHolder extends RecyclerView.ViewHolder {

        public AdapterViewHolder(View itemView)
        {
            super(itemView);
            ButterKnife.bind(this, itemView);

        }

        public void setOnClickListener(final int position, final E object, final OnItemClickListener onItemClickListener) {

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                   onItemClickListener.onItemClickListener(position, object);
                }
            });
        }

        public void setOnLongClickListener(final int position, final E object, final OnItemClickListener onItemClickListener) {

            itemView.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View v) {
                    onItemClickListener.onItemClickListener(position, object);
                    return true;
                }
            });
        }
    }


    public interface OnItemClickListener<E> {
        void onItemClickListener(int position, E object);
    }

    @Override
    public Filter getFilter() {
        return new Filter() {
            @SuppressWarnings("unchecked")
            @Override
            protected void publishResults(CharSequence constraint, FilterResults results) {
                list = (List<E>) results.values;
                GenericRecycleAdapter.this.notifyDataSetChanged();
            }

            @Override
            protected FilterResults performFiltering(CharSequence constraint) {
                List<E> filteredResults = null;
                if (constraint.length() == 0) {
                    filteredResults = originalList;
                } else {
                    filteredResults = getFilteredResults(constraint.toString().toLowerCase());
                }

                FilterResults results = new FilterResults();
                results.values = filteredResults;

                return results;
            }
        };
    }

    protected List<E> getFilteredResults(String constraint) {
        return null;
    }


}