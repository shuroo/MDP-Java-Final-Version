package mdp.generic;

import utils.Constants;
import utils.HashUuidCreator;

/**
 * Class to represent an MDP Reward object in Reqeards  function.
 * Constructed as: R(s,s',a), SXA-> S'
 *
 * @author Shiri Rave
 * @since January 2023
 */
public class Reward {

    // The reward source state according to the rewards function: R(s,s',a)
    private State sourceState;

    // The reward destination state according to the rewards function: R(s,s',a)
    private State destState;

    // The reward action according to the rewards function: R(s,s',a)
    private Action action;

    // The reward numeric (non-negative) value (- "Weight" )
    private Double reward;

    // The reward ID
    private String id;

    /**
     * The Reward constructor method
     * @param sourceState - The Reward source state
     * @param destState - The Reward destination state
     * @param action - The Reward action
     * @param reward - The Reward value (Weight)
     */

    public Reward(State sourceState, State destState, Action action, Double reward) {
        this.sourceState = sourceState;
        this.destState = destState;
        this.action = action;
        this.reward = reward;
        this.id = generateId();
    }

    /**
     * Getter method to return the reward value.
     * @return Double - The reward value.
     */

    public Double getReward() {
        return reward;
    }

    /**
     * Getter method to return the reward ID.
     * @return String - The reward ID.
     */
    public String getId() {
        return id;
    }

    /**
     * Method to generate the current Reward's ID
     * @return - String, The reward ID
     */
    private String generateId() {
        return buildId(this.sourceState,this.destState,this.action);
    }

    /**
     * Method to construct the reward object ID
     * @param source - The reward source state
     * @param dest- The reward dest state
     * @param action - The reward action
     * @return - String, The reward ID
     */
    public static String buildId(State source, State dest, Action action){
        String baseId = action.toString() + "_" + source.toString() + "_" + dest.toString();
        return Constants.rewardsPrefix+HashUuidCreator.getSha1Uuid(baseId);
    }
}
