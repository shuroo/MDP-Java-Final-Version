package utils;

import ctp.CTPEdge;
import java.util.*;

public class CollectionUtils<T> {


    public List<T> flattenList(List<Set<T>> nestedList) {
        List<T> flatList = new ArrayList<T>();
        nestedList.forEach(flatList::addAll);
        return flatList;
    }


    public static HashMap<String, CTPEdge> edgeToMap(Collection<CTPEdge> edges) {
        HashMap<String,CTPEdge> statuses = new HashMap<String,CTPEdge>();
        edges.stream().forEach(st->{statuses.put(st.getEdge().getId(), st);});
        return statuses;
    }

    public  HashMap<String, T> objToHMap(Collection<T> objs ) {
        HashMap<String,T> results = new HashMap<String,T>();
        objs.stream().forEach(st->{results.put(st.toString(), st);});
        return results;
    }

}
