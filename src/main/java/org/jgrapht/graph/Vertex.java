package org.jgrapht.graph;

import java.util.HashSet;

public class Vertex<T> {

    private boolean isInitial;

    private boolean isFinal;

    private String identifier;

    @Override
    public String toString(){
        return identifier;
    }

    boolean isInitial() {
        return isInitial;
    }

    boolean isFinal() {
        return isFinal;
    }

    public HashSet<Edge> vertexEdges = null;

    public Vertex( String identifier, Boolean isInitial, Boolean isFinal){

        this.identifier = identifier;
        this.isInitial = isInitial;
        this.isFinal = isFinal;
        this.vertexEdges = new HashSet<Edge>();
    }



}
