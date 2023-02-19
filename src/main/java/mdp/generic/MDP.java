package mdp.generic;

import mdp.interfaces.MDPI;

import java.util.HashMap;
import java.util.List;

/**
 * Class to represent an MDP model (implements the MDP interface)
 *
 * @author Shiri Rave
 * @since January 2023
 */
public class MDP implements MDPI{

    // The MDP States HashMap - Representing all MDP states along with their IDS , AKA HMap keys.
    protected HashMap<String,State> states;

    // The MDP Rewards HashMap - Representing all MDP rewards along with their IDS , AKA HMap keys.
    protected HashMap<String,Reward> rewards;


    // The MDP Transitions HashMap - Representing all MDP transitions along with their IDS , AKA HMap keys.
    protected HashMap<String,Transition> transitions;


    // The MDP Actions HashMap - Representing all MDP actions along with their IDS , AKA HMap keys.
    protected HashMap<String,Action> actions;

    // isMinimizationProblem - Variable to Check whether the model belongs to a minimization or a maximization problem.
    protected Boolean isMinimizationProblem;



    /**
     * Default MDP Constructor , Initially empty
     */
    public MDP(){}

    /**
     * An MDP Constructor Method
     * @param transitions - The MDP Map of Transitions
     * @param actions - The MDP Map of Actions
     * @param states - The MDP Map of States
     * @param rewards - The MDP Map of Rewards
     * @param isMinimizationProblem - 'True' if this is a minimization problem, 'False' if this is a maximization problem
     */
    public MDP (

            HashMap<String,Transition> transitions,
            HashMap<String,Action> actions,
            HashMap<String,State> states,
            HashMap<String,Reward> rewards,

            Boolean isMinimizationProblem
    ){
            this.actions = actions;
            this.transitions = transitions;
            this.rewards = rewards;
            this.states = states;
            this.isMinimizationProblem = isMinimizationProblem;
    }


    /**
     * Getter Method to return all MDP transitions
     * @return HashMap - All MDP Transitions + TransitionIDs (-its' keys)
     */
    public HashMap<String, Transition> getTransitions() {
        return transitions;
    }

    /**
     * Getter Method to return all MDP actions
     * @return HashMap - All MDP Actions + ActionIDs (-the HMap keys)
     */
    public HashMap<String, Action> getActions() {
        return actions;
    }

    /**
     * Getter Method to return all MDP states
     * @return HashMap - All MDP State + StateID (-The HMap Keys)
     */
    public HashMap<String, State> getStates() {
        return states;
    }

    /**
     * Getter Method to return all MDP rewards
     * @return HashMap - All MDP Reward + RewardID (-The HMap Keys)
     */

    public HashMap<String, Reward> getRewards() {
        return rewards;
    }

    /**
     * Boolean Getter Method - return isMinimizationProblem variable value ( True in case of minimization, false otherwise ).
     * @return Boolean - Return isMinimizationProblem variable value
     */
    public Boolean isMinimizationProblem() {
        return isMinimizationProblem;
    }

}
