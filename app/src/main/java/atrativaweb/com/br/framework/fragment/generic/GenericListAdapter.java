package atrativaweb.com.br.framework.fragment.generic;

import android.app.Activity;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.ImageView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

import atrativaweb.com.br.framework.R;


public abstract class GenericListAdapter<E> extends GenericSearchAdapter<E> {


    private static final String TAG = "GenericListAdapter";
    protected int layout;

    public GenericListAdapter(Activity a, List<E> list) {
        super(a, list);
    }

    public GenericListAdapter(Activity a, ArrayList<E> list) {
        super(a, list);
    }

    protected void loadImage(String url, ImageView imageView) {
        Picasso.with(activity.getApplicationContext()).load(url).placeholder(R.drawable.placeholder_loading).into(imageView);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        convertView = inflater.inflate(layout, parent, false);

        Log.w(TAG, "convert view");
        setData(convertView, getItem(position), position);

        return convertView;
    }

    protected abstract void setData(View convertView, E item, int position);

    @Override
    public Filter getFilter() {
        return new Filter() {
            @SuppressWarnings("unchecked")
            @Override
            protected void publishResults(CharSequence constraint, FilterResults results) {
                list = (List<E>) results.values;
                GenericListAdapter.this.notifyDataSetChanged();
            }

            @Override
            protected FilterResults performFiltering(CharSequence constraint) {
                List<E> filteredResults = null;
                if (constraint.length() == 0) {
                    filteredResults = originalList;
                } else {
                    filteredResults = getFilteredResults(constraint);
                }

                FilterResults results = new FilterResults();
                results.values = filteredResults;

                return results;
            }
        };
    }

    protected abstract List<E> getFilteredResults(CharSequence constraint);
}

