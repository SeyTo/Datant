package rj.mod.datant.control;

import java.util.List;

/**
 * Callback to show a list of descriptions.
 * Created by rj on 20/06/17.
 */
public interface IncompleteCallBack {

    /**
     * Callback to show a list of descriptions.
     * @param incompletes
     */
    void setIncomplete(List<String> incompletes);
}
