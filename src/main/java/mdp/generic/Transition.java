package mdp.generic;

/**
 * A class representing an MDP Transition T(s,a,s') with its' probability - P(s,a,s')
 *
 * @author Shiri Rave
 * @since Januarry 2023
 */
public class Transition {

    // The Transition source state - s
    protected State sourceState;

    // The Transition destination state - s'
    protected State destState;

    // The Transition destination action - a
    protected Action action;

    // The Transition Probability -  having a value in range: 0.0 =< prob <= 1.0
    protected Double probability;

    // The Transition ID
    protected String transitionId;

    /**
     * Transition Constructor method - Construct a new object
     * @param sourceState - The transition source state
     * @param destState - The transition destination state
     * @param action - The Transition action
     * @param probability - The Transition probability
     */
    public Transition(State sourceState, State destState, Action action, Double probability) {
        this.sourceState = sourceState;
        this.destState = destState;
        this.action = action;
        this.probability = probability;
        this.transitionId = generateId();
    }

    /**
     * Getter Method to return the Transition Source State
     * @return State - The Transition Source state.
     */
    public State getSourceState() {
        return sourceState;
    }

    /**
     * Getter Method to return the Transition Destination State
     * @return State - The Transition Destination state.
     */
    public State getDestState() {
        return destState;
    }

    /**
     * Getter Method to return the Transition ID
     * @return String - The Transition ID
     */
    public String getTransitionId() {
        return transitionId;
    }

    /**
     * Getter Method to return the Transition Action
     * @return Action - The Transition Action
     */
    public Action getAction() {
        return action;
    }

    /**
     * Getter Method to return the Transition Probability
     * @return Double - The Transition Probability
     */
    public Double getProbability() {
        return probability;
    }

    /**
     * Method to generate Transition ID
     * @return String - The Transition ID
     */
    private String generateId() {
        return buildId(this.action, this.sourceState, this.destState);

    }

    /**
     * Methods to construct the Transition ID
     * @param action - The Transition Action
     * @param sourceState - The Transition Source State
     * @param destState - The Transition Destination State
     * @return String - The Transition ID
     */
    public static String buildId(Action action, State sourceState, State destState) {
        return action.getActionId() + "_dest:" + destState.getId() + "_src:" + sourceState.getId();
    }


}
