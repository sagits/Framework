package atrativaweb.com.br.framework.fragment;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.gc.materialdesign.views.ButtonFloat;
import com.mikepenz.fontawesome_typeface_library.FontAwesome;
import com.mikepenz.iconics.IconicsDrawable;

import java.util.ArrayList;
import java.util.List;

import atrativaweb.com.br.framework.R;
import atrativaweb.com.br.framework.utils.BusProvider;
import atrativaweb.com.br.framework.utils.DrawerMenuHelper;
import butterknife.Bind;
import butterknife.ButterKnife;
import io.karim.MaterialTabs;

/**
 * Created by Renato on 21/04/2016.
 */
public class MainFragment extends Fragment {

    private View rootView;
    @Bind(R.id.materialTabs)
    MaterialTabs materialTabs;
    @Bind(R.id.viewPager)
    protected ViewPager viewPager;
    @Bind(R.id.floatBtt)
    protected ButtonFloat floatBtt;
    private PagerAdapter pagerAdapter;

    public MainFragment() {
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.fragment_main, container, false);
        ButterKnife.bind(this, rootView);

        floatBtt.setDrawableIcon(new IconicsDrawable(getContext())
                .icon(FontAwesome.Icon.faw_plus)
                .color(getResources().getColor(android.R.color.white))
                .sizeDp(12));

        floatBtt.setBackgroundColor(getResources().getColor(R.color.primary));

        floatBtt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                viewPager.setCurrentItem(2);
                floatBtt.setVisibility(View.GONE);
            }
        });

        return rootView;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        BusProvider.getInstance().register(this);
    }

    @Override
    public void onResume() {
        super.onResume();
        setData();
    }

    private void setData() {
        pagerAdapter = new PagerAdapter(getChildFragmentManager(), getActivity());
        viewPager.setAdapter(pagerAdapter);
        materialTabs.setViewPager(viewPager);

        BusProvider.getInstance().post(new DrawerMenuHelper.DrawerIconEvent(getString(R.string.posts)));


    }


    public class PagerAdapter extends FragmentPagerAdapter {

        public List<String> titles = new ArrayList<>();
        private List<Fragment> fragments = new ArrayList<>();
        private Activity activity;

        public PagerAdapter(FragmentManager fm, Activity activity) {
            super(fm);
            this.activity = activity;

            titles.add(activity.getString(R.string.posts));
            titles.add(activity.getString(R.string.photos));

            fragments = new ArrayList<>();

            fragments.add(new PostListFragment());
            fragments.add(new PhotoListFragment());
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return titles.get(position);
        }

        @Override
        public int getCount() {
            return titles.size();
        }

        @Override
        public android.support.v4.app.Fragment getItem(int position) {

            return fragments.get(position);
        }
    }


    @Override
    public void onDestroy() {
        super.onDestroy();
        BusProvider.getInstance().unregister(this);
    }


}
