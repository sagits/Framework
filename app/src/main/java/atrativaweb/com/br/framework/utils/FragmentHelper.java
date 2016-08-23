package atrativaweb.com.br.framework.utils;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;

import atrativaweb.com.br.framework.R;


/**
 * Created by Renato on 08/06/2016.
 */
public class FragmentHelper {
    AppCompatActivity activity;


    public FragmentHelper(AppCompatActivity activity) {
        this.activity = activity;
    }

    public void replaceFragment(Fragment fragment, Bundle bundle, boolean addToBackStack) {
        FragmentTransaction ft = activity.getSupportFragmentManager().beginTransaction();
        if (bundle != null) {
            fragment.setArguments(bundle);
        }
        ft.replace(R.id.container, fragment, fragment.getClass().getSimpleName());
        ft.setTransition(android.app.FragmentTransaction.TRANSIT_FRAGMENT_OPEN);

        if (addToBackStack) {
            ft.addToBackStack(fragment.getClass().getSimpleName());
        }
        ft.commit();
    }

    private void checkForOldFragment(Fragment fragment) {
        Fragment oldFragment = activity.getSupportFragmentManager().findFragmentByTag(fragment.getClass().getSimpleName());
        if (oldFragment != null && oldFragment.getClass() == fragment.getClass()) {
            FragmentTransaction ft = activity.getSupportFragmentManager().beginTransaction();
            ft.remove(oldFragment);
            ft.commit();
        }
    }

    public static boolean popFromBackStack(AppCompatActivity activity, String name, boolean inclusive) {
        FragmentManager fm = activity
                .getSupportFragmentManager();

        if (fm.findFragmentByTag(name) != null) {
            return fm.popBackStackImmediate(name, inclusive ? FragmentManager.POP_BACK_STACK_INCLUSIVE : 0);
        }
        return false;
    }

    public static Fragment getVisibleFragment(AppCompatActivity activity){
        /*FragmentManager fragmentManager = activity.getSupportFragmentManager();
        List<Fragment> fragments = fragmentManager.getFragments();
        if(fragments != null){
            for(Fragment fragment : fragments){
                if(fragment != null && fragment.isVisible())
                    return fragment;
            }
        }
        return null; */
        return activity.getSupportFragmentManager().findFragmentById(R.id.container);
    }
}
