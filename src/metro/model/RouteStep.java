package metro.model;

// CONCEPT: Composition Component (RouteStep is created and owned directly by Route)
public class RouteStep {
    private String fromStation;
    private String toStation;
    private String line;

    public RouteStep(String fromStation, String toStation, String line) {
        this.fromStation = fromStation;
        this.toStation = toStation;
        this.line = line;
    }

    public String getFromStation() {
        return fromStation;
    }

    public String getToStation() {
        return toStation;
    }

    public String getLine() {
        return line;
    }

    @Override
    public String toString() {
        return fromStation + " -> " + toStation + " [" + line + " Line]";
    }
}
