package atrativaweb.com.br.framework.utils;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;

import com.mikepenz.fontawesome_typeface_library.FontAwesome;
import com.mikepenz.materialdrawer.AccountHeader;
import com.mikepenz.materialdrawer.AccountHeaderBuilder;
import com.mikepenz.materialdrawer.Drawer;
import com.mikepenz.materialdrawer.DrawerBuilder;
import com.mikepenz.materialdrawer.model.PrimaryDrawerItem;
import com.mikepenz.materialdrawer.model.ProfileDrawerItem;
import com.mikepenz.materialdrawer.model.SectionDrawerItem;
import com.mikepenz.materialdrawer.model.interfaces.IDrawerItem;
import com.mikepenz.materialdrawer.model.interfaces.IProfile;
import com.squareup.otto.Bus;
import com.squareup.otto.Subscribe;

import atrativaweb.com.br.framework.LoginActivity;
import atrativaweb.com.br.framework.R;
import rockapps.com.library.model.UserModel;
import rockapps.com.library.volley.SmarterLogMAKER;

/**
 * Created by Renato on 08/06/2016.
 */
public class DrawerMenuHelper {
    private static final String TAG = "DrawerMenuHelper";
    public Bus bus;
    AppCompatActivity activity;
    Toolbar toolbar;
    public Drawer drawer;
    FragmentHelper fragmentHelper;

    public DrawerMenuHelper(AppCompatActivity activity, FragmentHelper fragmentHelper, Toolbar toolbar) {
        this.activity = activity;
        this.fragmentHelper = fragmentHelper;
        this.toolbar = toolbar;
        bus = BusProvider.getInstance();
        bus.register(this);
    }

    public void setNavigationDrawer(UserModel user, Bundle savedInstanceState) {
        ProfileDrawerItem profile = null;
        if (user == null) {
            profile = new ProfileDrawerItem().withName("").withEmail("");
        } else if (user.getImage() != null) {
            profile = new ProfileDrawerItem().withName(user.getName()).withEmail(user.getEmail()).withIcon(user.getImage());
        } else {
            profile = new ProfileDrawerItem().withName(user.getName()).withEmail(user.getEmail()).withIcon(FontAwesome.Icon.faw_user);
        }


        // Set up the drawer.

        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                activity.onBackPressed();
            }
        });


        AccountHeader headerResult = new AccountHeaderBuilder()
                .withActivity(activity)
                .withHeaderBackground(R.drawable.menu_bg)
                .addProfiles(
                        profile
                )
                .withSelectionListEnabledForSingleProfile(false)
                .withOnAccountHeaderListener(new AccountHeader.OnAccountHeaderListener() {
                    @Override
                    public boolean onProfileChanged(View view, IProfile profile, boolean currentProfile) {
                        SmarterLogMAKER.w("clicked on header");
                        return false;
                    }
                })
                .build();

        drawer = new DrawerBuilder()
                .withActivity(activity)
                //.withTranslucentStatusBar(false)
                .withAccountHeader(headerResult) //set the AccountHeader we created earlier for the header

                .withToolbar(toolbar)
                .withOnDrawerNavigationListener(new Drawer.OnDrawerNavigationListener() {
                    @Override
                    public boolean onNavigationClickListener(View clickedView) {
                        //this method is only called if the Arrow icon is shown. The hamburger is automatically managed by the MaterialDrawer
                        //if the back arrow is shown. close the activity
                        activity.onBackPressed();
                        //return true if we have consumed the event
                        return true;
                    }
                })
                .withActionBarDrawerToggleAnimated(true)
        /*        .withDisplayBelowToolbar(true) */
                .withActionBarDrawerToggle(true)
                .addDrawerItems(
                        new SectionDrawerItem().withName(activity.getString(R.string.menu).toUpperCase()).withDivider(false),
                        new PrimaryDrawerItem().withName(R.string.home_menu).withIcon(FontAwesome.Icon.faw_home).withTag("home"),
                        //     new PrimaryDrawerItem().withName(R.string.ask_table).withIcon(FontAwesome.Icon.faw_table).withTag("table"),
                        new PrimaryDrawerItem().withName(R.string.terms).withIcon(FontAwesome.Icon.faw_file_o).withTag("terms")
                  //      new PrimaryDrawerItem().withName(R.string.contact).withIcon(FontAwesome.Icon.faw_envelope).withTag("contact")


                ).withOnDrawerItemClickListener(new Drawer.OnDrawerItemClickListener() {
                    @Override
                    public boolean onItemClick(View view, int position, IDrawerItem drawerItem) {


                        switch (drawerItem.getTag().toString()) {
                            case "login":

                                break;
                            case "logout":
                                activity.startActivity(new Intent(activity, LoginActivity.class));
                                activity.finish();
                                break;
                            case "home":
                               // fragmentHelper.replaceFragment(new MainFragment(), null, true);
                                break;
                            case "terms":
                                break;
                            default:
                                break;
                        }
                        return false;
                    }
                }).withSavedInstance(savedInstanceState)
                .withShowDrawerOnFirstLaunch(true)
                .build();

        if (user != null) {
            drawer.addStickyFooterItem(new PrimaryDrawerItem().withName(activity.getResources().getString(R.string.exit)).withIcon(FontAwesome.Icon.faw_sign_out).withTag("logout"));
        } else {
            drawer.addStickyFooterItem(new PrimaryDrawerItem().withName(activity.getResources().getString(R.string.enter)).withIcon(FontAwesome.Icon.faw_sign_in).withTag("login"));
        }


    }

    @Subscribe
    public void setDrawerIcon(DrawerIconEvent event) {
        setDrawerIcon();
        if (event.title != null) {
            activity.getSupportActionBar().setDisplayShowTitleEnabled(true);
            activity.getSupportActionBar().setTitle(event.title);
            //   toolbarSpinner.setVisibility(View.GONE);

        } else {
            activity.getSupportActionBar().setDisplayShowTitleEnabled(false);
            //   toolbarSpinner.setVisibility(View.VISIBLE);
        }
    }

    @Subscribe
    public void setBackIcon(BackIconEvent event) {
        Log.w(TAG, "backIconEvent");
        setBackIcon();
        activity.getSupportActionBar().setTitle(event.title);
    }

    public void setBackIcon() {
        drawer.getActionBarDrawerToggle().setDrawerIndicatorEnabled(false);
        activity.getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        activity.getSupportActionBar().setDisplayShowTitleEnabled(true);

        // toolbarSpinner.setVisibility(View.GONE);
    }

    public void setDrawerIcon() {
        activity.getSupportActionBar().setDisplayHomeAsUpEnabled(false);
        drawer.getActionBarDrawerToggle().setDrawerIndicatorEnabled(true);
    }


    public static abstract class ChangeNavigationBarEvent {
        String title;

        public ChangeNavigationBarEvent(String title) {
            this.title = title;
        }
    }

    public static class DrawerIconEvent extends ChangeNavigationBarEvent {

        public DrawerIconEvent(String title) {
            super(title);
        }
    }

    public static class BackIconEvent extends ChangeNavigationBarEvent {

        public BackIconEvent(String title) {
            super(title);
        }
    }

}
