package metro.exception;

// CONCEPT: Custom Exception Handling
public class RouteNotFoundException extends Exception {
    public RouteNotFoundException(String src, String dest) {
        super("Route Not Found: No connection exists between " + src + " and " + dest + ".");
    }
}
