package rj.mod.datant.control;

import rj.mod.datant.Flow;
import rj.mod.datant.FlowOnComplete;

import java.util.ArrayList;
import java.util.List;


/**
 * Is a singleton controller for providing a single interface to all the added flows. Allows sequential calling of all
 * the flows.
 * Created by rj on 20/06/17.
 */
public class DatantC<T extends Flow> {

    private static DatantC control;
    private List<T> flows;

    private DatantC() {
        flows = new ArrayList<>();
    }

    public static DatantC __() {
        if (control == null) {
            control = new DatantC();
        }

        return control;
    }

    public void add(T flow) {
        flows.add(flow);
    }

    public void remove(T flow) {
        flows.remove(flow);
    }

    public int size() {
        return flows.size();
    }

    public T getFlowIfFetch(String description) {
        for (T f : flows) {
            if (f.getFetchDescription() == null) continue;
            if (f.getFetchDescription().equals(description)) return f;
        }

        return null;
    }

    public T getFlowIfPush(String description) {
        for (T f : flows) {
            if (f.getPushDescription() == null) continue;
            if (f.getPushDescription().equals(description)) return f;
        }

        return null;
    }

    public T getFlow(String fetchDesc, String pushDesc) {
        for (T f : flows) {
            boolean fetchOk = false;
            boolean pushOk = false;
            System.out.println(f.getFetchDescription());
            System.out.println(f.getPushDescription());
            if ((f.getFetchDescription() == null && fetchDesc == null) ||
                    (f.getFetchDescription() != null && f.getFetchDescription().equals(fetchDesc))) {
                fetchOk = true;
            }

            if ((f.getPushDescription() == null && pushDesc == null) ||
                    (f.getPushDescription() != null && f.getPushDescription().equals(pushDesc))) {
                pushOk = true;
            }

            if (pushOk && fetchOk) {
                return f;
            }
        }

        return null;
    }

    //_______________ Sequential fetcher _______________//  //TODO move to util

    private int fetchCount = 0;
    private FlowOnComplete callback;

    public synchronized void fetchAll(String prefix, boolean unfetchedOnly, final IncompleteCallBack incompletes) {
        //get flows that has fetch description
        final List<Flow> pendingFetch = new ArrayList<>();
        for (T flow : flows) {
            if (flow.getFetchDescription() != null && (!unfetchedOnly || !flow.wasFetched())) { //get a/c to fetch description and unfetchedOnly attr
                if (prefix == null || flow.getFetchDescription().startsWith(prefix)) {
                    pendingFetch.add(flow);
                }
            }
        }

        if (pendingFetch.size() == 0) {
            incompletes.setIncomplete(null);
            return;
        }

        final List<String> descs = new ArrayList<>();
        fetchCount = 0;

        callback = new FlowOnComplete() {
            public void onComplete(Flow flow) {
                if (!flow.wasFetched()) {
                    descs.add(flow.getFetchDescription());
                }

                if (!fetchNext(pendingFetch)) incompletes.setIncomplete(descs);
            }
        };

        //sequentially call fetch for each of them without overloading the request queue

        fetchNext(pendingFetch);
    }

    private boolean fetchNext(List<Flow> pendingFetch) {
        if (fetchCount >= pendingFetch.size()) return false;
        Flow pend = pendingFetch.get(fetchCount);
        pend.setFetchOnComplete(callback);
        pend.fetch();
        ++fetchCount;
        return true;
    }

    public synchronized void pushAll() {
        //TODO
    }


}
