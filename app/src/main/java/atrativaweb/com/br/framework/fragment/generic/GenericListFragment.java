package atrativaweb.com.br.framework.fragment.generic;


import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.baoyz.widget.PullRefreshLayout;
import com.gc.materialdesign.views.ButtonFloat;
import com.google.gson.Gson;
import com.mikepenz.fontawesome_typeface_library.FontAwesome;
import com.mikepenz.iconics.IconicsDrawable;

import java.util.ArrayList;
import java.util.List;

import atrativaweb.com.br.framework.MenuActivity;
import atrativaweb.com.br.framework.R;
import atrativaweb.com.br.framework.adapter.GenericRecycleAdapter;
import atrativaweb.com.br.framework.utils.BusProvider;
import atrativaweb.com.br.framework.utils.DrawerMenuHelper;
import butterknife.Bind;
import butterknife.ButterKnife;


public abstract class GenericListFragment<E> extends GenericFragment<E> implements SearchView.OnQueryTextListener {
    @Nullable
    @Bind(R.id.recyclerView)
    protected RecyclerView gridView;
    @Nullable
    @Bind(R.id.swipeRefreshLayout)
    protected PullRefreshLayout swipeRefreshLayout;
    @Nullable
    @Bind(R.id.floatBtt)
    protected ButtonFloat floatBtt;
    @Nullable
    @Bind(R.id.listView)
    protected ListView listView;
    protected GenericListAdapter<E> adapter;
    protected GenericRecycleAdapter gridAdapter;
    public SearchView searchView;
    public List<E> list = new ArrayList<>();
    public Fragment singleFragment;


    public GenericListFragment() {
        menuXml = R.menu.list;
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        rootView = inflater.inflate(layoutXml, container, false);
        ButterKnife.bind(this, rootView);

        setHasOptionsMenu(true);

        if (gridView != null) {
            gridView.setHasFixedSize(true);

            StaggeredGridLayoutManager gridLayoutManager = new StaggeredGridLayoutManager(2, 1);
            gridView.setLayoutManager(gridLayoutManager);

        }

        if (swipeRefreshLayout != null && hasReflesh) {
            swipeRefreshLayout.setOnRefreshListener(new PullRefreshLayout.OnRefreshListener() {
                @Override
                public void onRefresh() {
                    getData();
                }
            });
        }


        return rootView;
    }

    @Override
    public void onResume() {
        super.onResume();
        getData();
    }

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        if (hasUserVisibleHint) {
            if (isVisibleToUser && rootView != null) {
                BusProvider.getInstance().post(new DrawerMenuHelper.DrawerIconEvent(getString(screenTitle)));
                //BusProvider.getInstance().post(new MainFragment.SetCartButtonEvent(this.getClass() == InvoiceSingleFragment.class ? false : true));
            }
        }
    }

    protected abstract void getData();

    protected void setData() {

        if (listView != null) {
            listView.setAdapter(adapter);
        } else if (gridView != null) {
            StaggeredGridLayoutManager gridLayoutManager = new StaggeredGridLayoutManager(2, 1);
            gridView.setLayoutManager(gridLayoutManager);
            gridView.setAdapter(gridAdapter);
            gridView.invalidate();
        }
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        menu.clear();
        inflater.inflate(menuXml, menu);

        MenuItem searchMenuItem = menu.findItem(R.id.search);

        if (searchMenuItem != null && Build.VERSION.SDK_INT >= 11) {
            searchView =
                    (SearchView) menu.findItem(R.id.search).getActionView();
            searchView.setQueryHint(getString(R.string.search));
            searchView.setOnQueryTextListener(this);

            menu.getItem(0).setIcon(new IconicsDrawable(getActivity(), FontAwesome.Icon.faw_filter).color(Color.WHITE).sizeDp(18));
        }


    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        return super.onOptionsItemSelected(item);
    }

    public void seeSingle(Object object) {
        Bundle bundle = new Bundle();
        bundle.putString("object", new Gson().toJson(object));
        ((MenuActivity) getActivity()).replaceFragment(singleFragment, bundle, true);
    }

    @Override
    public boolean onQueryTextSubmit(String query) {
        return false;
    }

    @Override
    public boolean onQueryTextChange(String newText) {
        return false;
    }
}
