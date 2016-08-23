package atrativaweb.com.br.framework.fragment.generic;

import android.app.Activity;
import android.widget.Filterable;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Renato on 08/03/2015.
 */
public abstract class GenericSearchAdapter<E> extends GenericAdapter<E> implements Filterable {


    public GenericSearchAdapter(Activity a, List<E> list) {
        super(a, list);
    }

    public GenericSearchAdapter(Activity a, ArrayList<E> list) {
        super(a, list);
    }
}
