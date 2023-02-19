package mdp.generic;

import mdp.interfaces.StateI;

/**
 * Class to represent a basic MDP state
 * @author shirirave
 * @since 01/2023
 */
public class State implements StateI {

    // StateID - The state ID
    protected String stateId;

    // The current utility to save ( 0 by default for maximization problems )
    protected Double minimalUtility = 0.0;

    // The State best next-action chosen
    protected Action bestAction;

    // Boolean isFinal - is this a final state for the agent?
    protected Boolean isFinal = false;

    // Boolean isInitial - is this an initial state for the agent?
    protected Boolean isInitial = false;

    // Variable to save the previous state's utility
    protected Double previousUtility;

    /**
     * Dafault state constructor
     * @param stateId - The state ID
     * @param isInitial - Is this an INITIAL state for the agent?
     * @param isFinal - Is this a FIANL state for the agent?
     * @param initialUtility - The Initial Utility ( 0 or Very Large according to the problem type, min or max )
     */
    public State(String stateId, Boolean isInitial, Boolean isFinal,Double initialUtility) {
        this.stateId = stateId;
        this.isInitial = isInitial;
        this.isFinal = isFinal;
        this.minimalUtility = initialUtility;
    }

    /**
     * Getter method for isFinal variable
     * @return Boolean
     */
    public Boolean getIsFinal() {
        return isFinal;
    }

    /**
     * Getter method for previousUtility variable
     * @return The previous utility saved.
     */
    public Double getPreviousUtility() {
        return previousUtility;
    }

    /**
     * Getter method for stateId variable
     * @return String, the state id.
     */
    public String getId() {
        return stateId;
    }

    /**
     * Getter Method for the saved utility variable.
     * @return Double - The saved utility.
     */
    public Double getUtility() {
        return minimalUtility;
    }

    /**
     * Method for setting the current utility
     * @param utility The utility to set
     */
    public void setUtility(Double utility) {
        this.minimalUtility = utility;
    }

    public Action getBestAction() {
        return bestAction;
    }

    /**
     * Setter method for setting the best action chosen.
     * @param action - The action to set
     */
    public void setBestAction(Action action) {
        this.bestAction = action;
    }

    /**
     * Setter method for setting the previous utility ( used in VI algorithm to calc the utility diff )
     * @param prevUtil - The previous utility to set
     */
    public void setPreviousUtility(Double prevUtil) {
        this.previousUtility = prevUtil;
    }


    /**
     * Method to override the default printing method.
     * @return String
     */
    @Override
    public String toString(){

        return "<"+this.stateId+">";
    }
}
