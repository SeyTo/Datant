package rj.mod.datant.flows;

import rj.mod.datant.Flow;

/**
 * A simple implementation of Flow
 * Created by rj on 20/06/17.
 */
public class SimpleFlow extends Flow {

    public SimpleFlow() {
        super();
        fetchCalled = false;
        pushCalled = false;
    }

    public void fetch() {
        fetchCalled = true;
        fetch.method(fetchOnComplete, this);
    }

    public void push() {
        pushCalled = true;
        push.method(pushOnComplete, this);
    }

    public void resetFetch() {
        fetchCalled = false;
    }

    public void resetPush() {
        pushCalled = false;
    }
}
