package org.jgrapht.graph;

public class Edge extends DefaultEdge {

    public Double getReward() {
        return reward;
    }

    private Double reward = 1.0;
    private double blockingProbability = 0;
    private Boolean isBlocked = false;

    public Vertex getSource() {
        return (Vertex)source;
    }

    public String getId(){ return this.source+"_"+this.target; }
    public Vertex getDest() {
        return (Vertex)target;
    }

    @Override
    public Object getTarget(){
        return target;
    }

    public double getBlockingProbability() {
        return blockingProbability;
    }

    public void setBlockingProbability(Double blockingProbability) {
        this.blockingProbability = blockingProbability;
    }

    public void setReward(Double reward){this.reward = reward;}

    public void setBlockingAndReward(Double blockingProbability , Double reward){
        setBlockingProbability(blockingProbability);
        setReward(reward);
    }

}
