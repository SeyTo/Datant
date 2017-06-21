package rj.mod.datant;

/**
 * Determines a method by which certain action should be executed.
 * Created by rj on 20/06/17.
 */
public interface UpdateMethod {

    /**
     * Description for this update method.
     * @return plain string description
     */
    String description();

    /**
     * the Method to call if a flow is to be executed.
     * @param flowOnComplete action to call on flow completed.
     * @param flow this flow.
     */
    void method(FlowOnComplete flowOnComplete, Flow flow);

}
