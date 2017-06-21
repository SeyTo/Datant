package rj.mod.datant.control;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import rj.mod.datant.Flow;
import rj.mod.datant.FlowOnComplete;
import rj.mod.datant.UpdateMethod;
import rj.mod.datant.flows.SimpleFlow;

import java.util.List;

import static org.junit.Assert.*;

/**
 *
 * Created by rj on 21/06/17.
 */
public class DatantCTest {

    @Before
    public void setUp() throws Exception {
        DatantC<SimpleFlow> d = DatantC.__();

        final SimpleFlow flowA = new SimpleFlow();
        final UpdateMethod fetchA = new UpdateMethod() {
            public String description() {
                return "fetchA";
            }

            public void method(final FlowOnComplete flowOnComplete, final Flow flow) {
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        System.out.println("FetchA Complete " + flowA.wasFetched());
                        flowOnComplete.onComplete(flow);
                    }
                }).start();
            }
        };
        final UpdateMethod pushA = new UpdateMethod() {
            public String description() {
                return "pushA";
            }

            public void method(final FlowOnComplete flowOnComplete, final Flow flow) {
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        System.out.println("PushA complete " + flowA.wasPushed());
                        flowOnComplete.onComplete(flow);
                    }
                }).start();
            }
        };
        flowA.setFetchMethod(fetchA);
        flowA.setPushMethod(pushA);

        d.add(flowA);

        final SimpleFlow flowB = new SimpleFlow();
        final UpdateMethod pushB = new UpdateMethod() {
            public String description() {
                return "pushB";
            }

            public void method(final FlowOnComplete flowOnComplete, final Flow flow) {
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        System.out.println("PushB complete " + flowB.wasPushed());
                        flowOnComplete.onComplete(flow);
                    }
                }).start();
            }
        };
        flowB.setPushMethod(pushB);

        d.add(flowB);

        final SimpleFlow flowC = new SimpleFlow();
        final UpdateMethod fetchC = new UpdateMethod() {
            public String description() {
                return "fetchC";
            }

            public void method(final FlowOnComplete flowOnComplete, final Flow flow) {
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        System.out.println("FetchC Complete " + flowC.wasFetched());
                        flowOnComplete.onComplete(flow);
                    }
                }).start();
            }
        };
        flowC.setFetchMethod(fetchC);

        d.add(flowC);

    }

    @Ignore
    public void getFlowIfFetch() throws Exception {
        assertNotNull(DatantC.__().getFlowIfFetch("fetchA"));
        assertNotNull(DatantC.__().getFlowIfFetch("fetchC"));
    }

    @Ignore
    public void getFlowIfPush() throws Exception {
        assertNotNull(DatantC.__().getFlowIfPush("pushA"));
        assertNotNull(DatantC.__().getFlowIfPush("pushB"));
    }

    @Ignore
    public void getFlow() throws Exception {
        assertNotNull(DatantC.__().getFlow("fetchA","pushA"));
        assertNotNull(DatantC.__().getFlow(null,"pushB"));
        assertNotNull(DatantC.__().getFlow("fetchC",null));
    }

    @Test
    public void fetchAll() throws Exception {
        DatantC.__().fetchAll("fe", true, new IncompleteCallBack() {
            @Override
            public void setIncomplete(List<String> incompletes) {
                if (incompletes.size() == 0) {
                    System.out.println("complete");
                } else {
                    System.out.println("incomplete");
                }
            }
        });
        Thread.sleep(800);
    }

}