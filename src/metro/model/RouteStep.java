package metro.model;

public class RouteStep {
    private String fromStation;
    private String toStation;
    private String line;
    private boolean isInterchange;
    private String nextLine;

    public RouteStep(String fromStation, String toStation, String line) {
        this(fromStation, toStation, line, false, null);
    }

    public RouteStep(String fromStation, String toStation, String line, boolean isInterchange, String nextLine) {
        this.fromStation = fromStation;
        this.toStation = toStation;
        this.line = line;
        this.isInterchange = isInterchange;
        this.nextLine = nextLine;
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

    public boolean isInterchange() {
        return isInterchange;
    }

    public String getNextLine() {
        return nextLine;
    }

    @Override
    public String toString() {
        if (isInterchange) {
            return String.format("%s -> %s [%s Line] (Change to %s Line)",
                    fromStation, toStation, line, nextLine);
        }
        return fromStation + " -> " + toStation + " [" + line + " Line]";
    }
}
