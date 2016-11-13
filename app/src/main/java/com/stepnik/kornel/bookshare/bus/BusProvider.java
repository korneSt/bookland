package com.stepnik.kornel.bookshare.bus;

import com.squareup.otto.Bus;

/**
 * Created by korSt on 13.11.2016.
 */

public final class BusProvider {

    private static final Bus BUS = new Bus();

    public static Bus getInstance(){
        return BUS;
    }

    private BusProvider(){}
}
