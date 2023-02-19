package mdp.generic;


/**
 * A class representing an MDP Action.
 * Every state s has a probability P(s|s') to move to state s' with action a.
 * @author Shiri Rave
 * @since January 2023
 */
public class Action {

    // The Action ID
    protected String actionId;

    /**
     // The action utility: is the best utility calculated for this current action
     // among the given path expectations \ best policies calculated
     */
    private Double utility;

    /**
     * Getter Method - get the action utility
     * @return Double - the action's calculated utility
     */
    public Double getUtility() {
        return utility;
    }

    /**
     * Setter Method - set the action utility
     * @param utility  - the action's utility to set
     */
    public void setUtility(Double utility) {
        this.utility = utility;
    }

    /**
     * Getter Method - get the action ID
     * @return String - the action ID
     */

    public String getActionId() {
        return actionId;
    }

    /**
     *
     * @param actionId
     */
    public Action(String actionId){
        this.actionId = actionId;
    }

    /**
     *
     * @return
     */

    @Override
    public String toString(){
        return actionId;
    }

}
