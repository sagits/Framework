package atrativaweb.com.br.framework.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.view.View;

import atrativaweb.com.br.framework.R;
import atrativaweb.com.br.framework.adapter.PhotoAdapter;
import atrativaweb.com.br.framework.dao.PhotoDao;
import atrativaweb.com.br.framework.fragment.generic.GenericDaoListFragment;
import atrativaweb.com.br.framework.model.PhotoModel;

/**
 * Created by Renato on 23/08/2016.
 */
public class PhotoListFragment extends GenericDaoListFragment<PhotoModel> {

    public PhotoListFragment() {
        classType = PhotoModel.class;
        layoutXml = R.layout.fragment_grid;
        screenTitle = R.string.photos;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        dao = new PhotoDao(getActivity());
    }

    @Override
    protected void setData() {
        gridAdapter = new PhotoAdapter(getActivity(), list);

        StaggeredGridLayoutManager gridLayoutManager = new StaggeredGridLayoutManager(1, 1);
        gridView.setLayoutManager(gridLayoutManager);
        gridView.setAdapter(gridAdapter);
        gridView.invalidate();
    }
}
