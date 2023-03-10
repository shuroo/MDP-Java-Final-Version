package mdp;

import mdp.generic.*;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Main class to calculate value iteration algorithm by states.
 * @author shirirave
 * @since 01/2023
 */
public class UtilityCalculator {

    // Epsilon - the maximum allowed error ( calculating a smaller error is indicates convergence )
    private Double epsilon;

    // The discount factor - between 0 and 1
    private Double discountFactor;

    // The current MDP model
    private MDP currentMDP;

    // The maximum possible error, by default set to near infinite value.
    private Double maxLambda = 100000.0;

    /**
     * TBD
     * @param currentMDP
     * @param epsilon
     * @param discountFactor
     */
    public UtilityCalculator(MDP currentMDP, Double epsilon, Double discountFactor) {

        this.currentMDP = currentMDP;
        this.discountFactor = discountFactor;
        this.epsilon = epsilon;
    }

    /**
     * Main Method for Optimal policy calculation based on Value Iteration algorithm.
     *
     * @return MDP - The calculated MDP + policy.
     */
    public MDP setOptimalPolicy() {

        Integer iterationCounter = 0;
        Double stopCondition = epsilon * (1 - discountFactor) / discountFactor;

        List<State> allStates = currentMDP.getStates().values().stream().collect(Collectors.toList());

        while (maxLambda > stopCondition) {

            iterationCounter++;
            System.out.println("Starting iteration number:" + iterationCounter);

            setUtilitiesForStatesIteration(allStates);

            // Check diff to stop...
            for (State state : allStates) {

                Double minimalUtility = state.getUtility();
                Double prevUtility = state.getPreviousUtility();
               if (prevUtility == null) {
                    continue;
                }
                Double diffUtility = Math.abs(minimalUtility - prevUtility);
                // max diff per ALL states ... //
                if (maxLambda > diffUtility && diffUtility > 0.0) {
                    maxLambda = diffUtility;
                }

                if (maxLambda <= stopCondition){

                    System.out.println("***** Stopping at lambda:" + maxLambda + " on iteration:" + iterationCounter + " *****");
                    return currentMDP;
                }
            }

            // Print the current final MDP Utilities:
            currentMDP.getStates().values().stream().forEach(state -> {
                System.out.println("**** Final Utility state:" + state.getId() + " is:" + state.getUtility() + " chosen action is: " + state.getBestAction());
            });

        }

        return currentMDP;
    }

    /**
     * Method to return list of transitions with utility for each ( for grouping them later by action ).
     *
     * @param - states to calc utility from
     * @return updated utilities on actions with sorted list.
     * action_utility <-- 0 if final_state, else Sigma(p(s|s')*U'(s'))
     * .
     * EXAMPLE CALC: stt2_3->stt_3_3 = 0.8*left_action_utility + 0.1 * right_action_utility + 0.1 * stay =  0.8 * 0.76 + 0.2 * -0.04
     */

    public HashMap<String, Action> groupByActionAndSourceState(HashMap<Transition, Double> transtionsWithUtility) {

        // Structure: <String , Double>
        HashMap<String, List<Action>> actionsWithUtility = new HashMap<String, List<Action>>();

        for (Transition tran : transtionsWithUtility.keySet()) {
            String transitionId = tran.getTransitionId();
            String sourceActionKey = tran.getAction().toString()+"_"+transitionId.substring(transitionId.indexOf("_src:"),
                    transitionId.length());
            Double utility = transtionsWithUtility.get(tran);

            Action action = new Action(tran.getAction().getActionId());

            if(!actionsWithUtility.containsKey(sourceActionKey)) {
                actionsWithUtility.put(sourceActionKey, new LinkedList<Action>());
            }

            action.setUtility(utility);
            actionsWithUtility.get(sourceActionKey).add(action);

        }

        HashMap<String, Action> actionsWithGroupedUtility = new HashMap<String, Action>();

        for(String sourceActionId : actionsWithUtility.keySet()){
            List<Action> relatedActions = actionsWithUtility.get(sourceActionId);
            Double accUtility = 0.0;
            if(relatedActions.isEmpty()){
                continue;
            }
            Action sampleAction = relatedActions.get(0);
            for(Action action : relatedActions){

                accUtility+=action.getUtility();
            }

            sampleAction.setUtility(accUtility);
            actionsWithGroupedUtility.put(sourceActionId,sampleAction);
        }

        return actionsWithGroupedUtility;
    }

    /**
     * Method to return list of transitions with utility for each ( for grouping them later by action ).
     *
     * @param - states to calc utility from
     * @return updated utilities on actions with sorted list.
     * action_utility <-- 0 if final_state, else Sigma(p(s|s')*U'(s'))
     * .
     * EXAMPLE CALC: stt2_3->stt_3_3 = 0.8*left_action_utility + 0.1 * right_action_utility + 0.1 * stay =  0.8 * 0.76 + 0.2 * -0.04
     * <p>
     * This method returns a hashmap of:
     * <p>
     * stt_3_3_left_stt_3_1 <- 0.8 *(left_action_utility)
     * stt_3_3_left_stt_3_1 <- 0.1 *(right_action_utility)
     * <p>
     * etc...
     */
    private HashMap<Transition, Double> calcTransitionsUtility() {

        // Init & Build Map<Transition,Utility>

        HashMap<Transition, Double> actionsPerSourceStt = new HashMap<Transition, Double>();

        for (Transition transition : currentMDP.getTransitions().values()) {
            Double actionLocalUtility = calcStateUtility(transition.getSourceState(), transition.getDestState(), transition.getAction());
            if (!actionsPerSourceStt.containsKey(transition)) {
                actionsPerSourceStt.put(transition, actionLocalUtility);
            } else {
                Double prevUtil = actionsPerSourceStt.get(transition);
                actionsPerSourceStt.put(transition, prevUtil + actionLocalUtility);
            }

        }

        return actionsPerSourceStt;
    }

    /**
     * Find a single state future utility based on its current and future states.
     * @param source - The current state.
     * @param dest - The future state.
     * @param action - The given action to pass from state s to state s'.
     * @return - Double - The calculated Utility Value.
     */
    private Double calcStateUtility(State source, State dest, Action action) {

        if (source.getIsFinal()) {
            return source.getUtility() == null ? 0.0 : source.getUtility();
        } else {

            Transition transition = currentMDP.getTransitions().get(Transition.buildId(action
                    , source, dest));

            // The probability for transition between the above states
            Double joinedProb = transition != null ? transition.getProbability() : 0.0;

            // Utility for the two states to add to the action.
            // U(action) <-- Sigma[ P(s|s')*U(s') ]
            Double actionSubUtility = joinedProb * (dest.getUtility());
            // we DON'T set the source utility at this point YET! choosing minimum.
           return actionSubUtility;
        }
    }

    /**
     *
     * Method to update the current utility per iteration for all states.
     *
     * @param allStates - all possible states
     * @return  List<State> - List of states with updated utilities
     */
    private List<State> setUtilitiesForStatesIteration(List<State> allStates) {
        HashMap<Transition, Double> updatedTransitionsUtility = calcTransitionsUtility();

        HashMap<String, Action> utilityPerActionState = groupByActionAndSourceState(updatedTransitionsUtility);

        for (State state : allStates) {
            setUtilitySingleState(state, utilityPerActionState);
        }

        return allStates;
    }

    /**
     * Method to calc utility and choose best Action for a single state,
     * based on the total minimal utility calculated and best next-action
     * @param state - The current state
     * @param updatedStateActionsUtility - The current map of <state, list of actions> to choose the best from.
     */
    private void setUtilitySingleState(State state, HashMap<String, Action> updatedStateActionsUtility) {

        //  Get all actions belonging to this state:
        HashMap<String, Action> actionsWithUtility = filterStateActions(state, updatedStateActionsUtility);

        Action minimalUtilityAction = null;
        Double minimalUtility = 0.0;

        // Choose between a minimal or a maximal action
        if (!actionsWithUtility.isEmpty()) {
            minimalUtilityAction = currentMDP.isMinimizationProblem() ? findMinimalAction(
                    actionsWithUtility) : findMaximalAction(actionsWithUtility);

            // U(s) <- R(s,a,(s'??)) + Utility(a)

            // Generate a new reward object:
            String currentRewardId = Reward.buildId(
                    state, state, minimalUtilityAction);

            Double reward = minimalUtilityAction != null ? ( currentMDP.getRewards().get(currentRewardId) != null ?
                    currentMDP.getRewards().get(currentRewardId).getReward() : 0.0) : 0.0;
            minimalUtility = minimalUtilityAction != null ? (reward + minimalUtilityAction.getUtility()) : 0.0;

            // Set new updated utility:
            minimalUtility = minimalUtility * this.discountFactor;

            // Set the current utility to previous before re-iterating
            state.setPreviousUtility(state.getUtility());
            state.setUtility(minimalUtility);
            // And set the selected action for setting policy.
            state.setBestAction(minimalUtilityAction);

        }


    }


    /**
     *  Method to find the best next action based of the minimal utility found.
     *  Used in minimization problems only.
     * @param sttActionsWithutility - Map of <State, Actions (With Utility)>
     * @return The Best action found.
     */
    private Action findMinimalAction(HashMap<String, Action> sttActionsWithutility) {

        // The initial utility value should be extremely high, in order to improve iteratively
        Double finalUtility = 10000.0;
        Action result = null;
        for (String actionStateKey : sttActionsWithutility.keySet()) {
            Action currentAction = sttActionsWithutility.get(actionStateKey);
            Double currentUtility = currentAction.getUtility();
            if (currentUtility <= finalUtility) {
                result = currentAction;
                finalUtility = currentUtility;
            }
        }
        return result;
    }

    /**
     *  Method to find the best next action based of the maximal utility found.
     *  Used in maximization problems only.
     * @param sttActionsWithUtility - Map of <State, Actions (With Utility)>
     * @return The Best action found.
     */
    private Action findMaximalAction(HashMap<String, Action> sttActionsWithUtility) {

        // The initial utility value should be extremely low, to improve iteratively
        Double finalUtility = -10000.0;
        Action result = null;
        for (String actionStateKey : sttActionsWithUtility.keySet()) {
            Action currentAction = sttActionsWithUtility.get(actionStateKey);
            Double currentUtility = currentAction.getUtility();
            if (currentUtility > finalUtility) {
                result = currentAction;
                finalUtility = currentUtility;
            }
        }

        return result;
    }

    /**
     * Given a state, filter all given actions related to it with updated calculated utility..
     *
     * @param state - the state to filter
     * @return HashMap<String, Double>
     **/

    private HashMap<String, Action> filterStateActions(final State state, HashMap<String, Action> stateActionsWithUtility) {

        HashMap<String, Action> stateActions = new HashMap<String, Action>();

        stateActionsWithUtility.entrySet().stream().filter(tran -> tran.getKey().endsWith("_src:" +state.getId())).forEach(entry ->
                stateActions.put(entry.getKey(), entry.getValue()));

        return stateActions;
    }

}
