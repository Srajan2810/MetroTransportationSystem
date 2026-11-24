package metro.exception;

// CONCEPT: Custom Exception Handling (Extending Exception)
public class InvalidStationException extends Exception {
    public InvalidStationException(String stationName) {
        super("Invalid Station: '" + stationName + "' does not exist in the Delhi Metro network.");
    }
}
