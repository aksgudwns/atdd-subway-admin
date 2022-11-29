package nextstep.subway.domain;

import javax.persistence.Embeddable;
import java.util.Objects;

@Embeddable
public class Distance {

    private static final int TERMINAL_SECTION_DISTANCE_VALUE = 0;
    private static final int MINIMUM_DISTANCE = 1;
    private static final Distance TERMINAL_SECTION_DISTANCE = new Distance(TERMINAL_SECTION_DISTANCE_VALUE, true);

    private int distance;

    protected Distance() {

    }

    private Distance(int distance, boolean isTerminalSectionDistance) {
        if(isTerminalSectionDistance) {
            this.distance = distance;
            return;
        }
        if (distance < MINIMUM_DISTANCE) {
            throw new IllegalArgumentException(ErrorMessage.INVALID_DISTANCE_VALUE.getMessage());
        }
        this.distance = distance;
    }

    public Distance(int distance) {
        this(distance, false);
    }

    public int getDistance() {
        return distance;
    }

    public Distance subtract(Distance distance) {
        int newDistance = this.distance - distance.getDistance();
        if (newDistance < MINIMUM_DISTANCE) {
            throw new IllegalArgumentException(ErrorMessage.EXCEED_SECTION_DISTANCE.getMessage());
        }
        this.distance = newDistance;
        return this;
    }

    public void setEndSectionDistance() {
        this.distance = TERMINAL_SECTION_DISTANCE_VALUE;
    }

    public Distance add(Distance distance) {
        this.distance += distance.getDistance();
        return this;
    }

    public static Distance getTerminalSectionDistance() {
        return TERMINAL_SECTION_DISTANCE;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Distance distance1 = (Distance) o;
        return distance == distance1.distance;
    }

    @Override
    public int hashCode() {
        return Objects.hash(distance);
    }

}
