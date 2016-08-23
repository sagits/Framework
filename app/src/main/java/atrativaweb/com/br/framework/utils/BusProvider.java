package atrativaweb.com.br.framework.utils;


import com.squareup.otto.Bus;

import java.util.ArrayList;

/**
 * Created by Renato on 08/06/2016.
 */
public class BusProvider {
    private static CustomBus instance;

    public static CustomBus getInstance() {
        if (instance == null) {
            instance = new CustomBus();
        }
        return instance;
    }

    private BusProvider() {
    }

    public static class CustomBus extends Bus {
        private ArrayList registeredObjects = new ArrayList<>();

        @Override
        public void register(Object object) {
            if (!registeredObjects.contains(object)) {
                registeredObjects.add(object);
                super.register(object);
            }
        }

        @Override
        public void unregister(Object object) {
            if (registeredObjects.contains(object)) {
                registeredObjects.remove(object);
                super.unregister(object);
            }
        }
    }
}
