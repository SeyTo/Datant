package rj.mod.datant;

/**
 * Flow is the most basic concept using by which a package of data can be transferred from one source to another and
 * vice versa. A single flow is thus capable of only one source and one destination. Each flow requires a single updating
 * method which is used to either fetch data or to push data to or from a source.
 * Sometimes there maybe multiple identical fetches/pushes, to avaoid having call to the same source/destination again
 * without a good reason, description is used to identify each fetchs' and pushs'.
 * Created by rj on 20/06/17.
 */
public abstract class Flow {

    protected UpdateMethod fetch;
    protected UpdateMethod push;
    protected FlowOnComplete fetchOnComplete;
    protected FlowOnComplete pushOnComplete;
    protected boolean fetchCalled;
    protected boolean pushCalled;

    public Flow() {
        this(null, null);
    }

    public Flow(UpdateMethod fetch, UpdateMethod push) {
        this.fetch = fetch;
        this.push = push;
    }

    //_______________ Abstract Methods _______________//

    /**
     * Fetch a given data using a given method.
     */
    public abstract void fetch();

    /**
     * Push a given data using a given method.
     */
    public abstract void push();

    /**
     * Reset fetch so it may be called again. However, dev is in control of resetting and refetching.
     */
    public abstract void resetFetch();

    /**
     * Reset push so it may be called again. However, dev is in control of resetting and repushing.
     */
    public abstract void resetPush();

    //_______________ Setters and Getters _______________//

    public void setFetchMethod(UpdateMethod method) {
        this.fetch = method;
    }

    public void setPushMethod(UpdateMethod method) {
        this.push = method;
    }

    public void setFetchOnComplete(FlowOnComplete onComplete) {
        this.fetchOnComplete = onComplete;
    }

    public void setPushOnComplete(FlowOnComplete onComplete) {
        this.pushOnComplete = onComplete;
    }

    public String getFetchDescription() {
        if (fetch == null) return null;
        else return fetch.description();
    }

    public String getPushDescription() {
        if (push  == null) return null;
        else return push.description();
    }

    /**
     * See if fetch has been called before. This can also be possibly reset manually.
     * @return see if was fetched.
     */
    public boolean wasFetched() {
        return fetchCalled;
    }

    /**
     * See if push has been called before. This can also be possibly reset manually.
     * @return see if was pushed.
     */
    public boolean wasPushed() {
        return pushCalled;
    }



}
