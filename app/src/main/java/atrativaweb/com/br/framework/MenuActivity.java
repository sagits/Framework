package atrativaweb.com.br.framework;

import android.os.Build;
import android.os.Bundle;
import android.support.v4.view.LayoutInflaterCompat;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Spinner;

import com.mikepenz.iconics.context.IconicsLayoutInflater;
import com.mikepenz.materialdrawer.Drawer;

import atrativaweb.com.br.framework.fragment.MainFragment;
import atrativaweb.com.br.framework.utils.BusProvider;
import atrativaweb.com.br.framework.utils.DrawerMenuHelper;
import atrativaweb.com.br.framework.utils.FragmentHelper;
import atrativaweb.com.br.framework.utils.Utils;
import butterknife.Bind;
import butterknife.ButterKnife;
import rockapps.com.library.model.UserModel;

public class MenuActivity extends AppCompatActivity {

    public SearchView searchView;
    private UserModel user;
    @Bind(R.id.toolbar)
    Toolbar toolbar;
    /**
     * Used to store the last screen title. For use in {@link #restoreActionBar()}.
     */
    private CharSequence mTitle;
    public Spinner toolbarSpinner;

    FragmentHelper fragmentHelper;
    DrawerMenuHelper drawerMenuHelper;

    public Drawer getDrawer() {
        return drawerMenuHelper.drawer;
    }

    public void setDrawer(Drawer drawer) {
        this.drawerMenuHelper.drawer = drawer;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        LayoutInflaterCompat.setFactory(getLayoutInflater(), new IconicsLayoutInflater(getDelegate()));
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);

        ButterKnife.bind(this);

        setSupportActionBar(toolbar);

        BusProvider.getInstance().register(this);

        user = getUser();
        mTitle = getTitle();
        setToolbarSpinner();

        fragmentHelper = new FragmentHelper(this);
        drawerMenuHelper = new DrawerMenuHelper(this, fragmentHelper, toolbar);
        drawerMenuHelper.setNavigationDrawer(user, savedInstanceState);
        drawerMenuHelper.setDrawerIcon();

        setData();
        Utils.generateKeyHash(this);

    }

    private UserModel getUser() {
        UserModel user = new UserModel();
        user.setName("Allan Schmitt");
        user.setEmail("Allan@atrativaweb.com.br");
        return user;
    }

    private void setData() {
        replaceFragment(new MainFragment(), null, false);
    }

    public void replaceFragment(android.support.v4.app.Fragment fragment, Bundle bundle, boolean addToBackStack) {
        fragmentHelper.replaceFragment(fragment, bundle, addToBackStack);
    }

    public void setToolbarSpinner() {
        if (toolbarSpinner == null) {
            // toolbarSpinner = new Spinner(getSupportActionBar().getThemedContext());
            // toolbar.addView(toolbarSpinner, 1);
        }
        toolbar.setTitle(getString(R.string.app_name));
    }

    public void restoreActionBar() {
        ActionBar actionBar = getSupportActionBar();
        actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_STANDARD);
        actionBar.setDisplayShowTitleEnabled(true);
        //  actionBar.setTitle(mTitle);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        // Only show items in the action bar relevant to this screen
        // if the drawer is not showing. Otherwise, let the drawer
        // decide what to show in the action bar.
        getMenuInflater().inflate(R.menu.list, menu);
        //       restoreActionBar();

        MenuItem searchMenuItem = menu.findItem(R.id.search);


        if (Build.VERSION.SDK_INT >= 11) {
            searchView =
                    (SearchView) menu.findItem(R.id.search).getActionView();


        }

        return true;
        // return super.onCreateOptionsMenu(list);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
//        if (id == R.id.action_settings) {
//
//            return true;
//        } else if (id == R.id.add) {
//            replaceFragment(CustomerAddFragment.newInstance(), null, true);
//            return true;
//        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onResume() {
        super.onResume();

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        BusProvider.getInstance().unregister(this);
        BusProvider.getInstance().unregister(drawerMenuHelper);
    }


}