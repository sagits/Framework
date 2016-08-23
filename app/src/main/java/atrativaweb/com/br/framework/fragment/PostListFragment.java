package atrativaweb.com.br.framework.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;

import atrativaweb.com.br.framework.R;
import atrativaweb.com.br.framework.adapter.PostAdapter;
import atrativaweb.com.br.framework.dao.PostDao;
import atrativaweb.com.br.framework.fragment.generic.GenericDaoListFragment;
import atrativaweb.com.br.framework.model.PostModel;

/**
 * Created by Renato on 23/08/2016.
 */
public class PostListFragment extends GenericDaoListFragment<PostModel> {

    public PostListFragment() {
        classType = PostModel.class;
        layoutXml = R.layout.fragment_grid;
        screenTitle = R.string.posts;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        dao = new PostDao(getActivity());
    }

    @Override
    protected void setData() {
        gridAdapter = new PostAdapter(getActivity(), list);

        super.setData();
    }
}
