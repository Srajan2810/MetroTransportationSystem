package metro.service;

import metro.exception.InvalidStationException;
import metro.exception.RouteNotFoundException;
import java.util.List;

// CONCEPT: Interface definition
public interface Searchable {
    List<String> findShortestRoute(String source, String destination)
            throws InvalidStationException, RouteNotFoundException;
}
