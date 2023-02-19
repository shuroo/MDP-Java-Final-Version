package mdp.interfaces;

import mdp.generic.Action;
import mdp.generic.Reward;
import mdp.generic.State;
import mdp.generic.Transition;

import java.util.HashMap;

/**
 * Class to represent an MDP Interface
 *
 * @authos Siri Rave
 * @since Januarry 2023
 */
public interface MDPI {

    /**
     * Getter Method to get all the MDP Transitions (AKA, probabilities)
     * Logic: the string key is build in the following way: id = StateID_ActionID
     * Each (key, value) pair of the HashMap represents the following transition:
     * < StateID_ActionID, Transition > == P(s,a,s')
     * @return HashMap < StateID_ActionID, Transition > == P(s,a,s')
     * - The map of all state transitions' probabilities.
     */
    HashMap<String, Transition> getTransitions();

    /**
     * Getter Method to return all the given MDP actions -
     * @return HashMap<String,Action> - constructed as:
     * <String ActionId,Action>
     */
    HashMap<String, Action> getActions();


    /**
     * Getter Method to return all given MDP actions -
     * @return HashMap<String,State> - constructed as:
     *  <String StateId,State>
     */
    HashMap<String, State> getStates();

    // HashMap < StateID_ActionID, Double >  == R(s,a,s')
    /**
     * Getter Method to return all given MDP Rewards -
     * @return HashMap<String,State> - constructed as:
     *  <String StateId,State>
     */
    HashMap<String, Reward> getRewards();
}
