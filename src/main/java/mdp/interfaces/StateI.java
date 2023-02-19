package mdp.interfaces;


/**
 * Interface for an MDP State
 * @author Shiri Rave
 * @since January 2023
 */
public interface StateI {

    /**
     * Getter Method to return the state ID
     * @return String, the state id
     */
    public String getId();

    /**
     * Getter Method to return the state utility
     * @return Double, the state utility
     */
    public Double getUtility();

    /**
     * Setter Method to set the state utility
     * @param utility, the utility to set
     */
    public void setUtility(Double utility);


}
