package atrativaweb.com.br.framework.fragment.generic;

import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.View;

import com.baoyz.widget.PullRefreshLayout;
import com.google.gson.Gson;

import atrativaweb.com.br.framework.R;
import butterknife.Bind;

/**
 * Created by Renato on 12/07/2016.
 */
public abstract class GenericFragment<E> extends Fragment {
    protected int layoutXml;
    protected int menuXml = R.menu.global;
    protected View rootView;
    protected Class<E> classType;
    protected boolean hasUserVisibleHint;
    protected int screenTitle;
    @Nullable
    @Bind(R.id.swipeRefreshLayout)
    protected PullRefreshLayout swipeRefreshLayout;
    protected Gson gson = new Gson();
    protected boolean hasReflesh = true;
}
