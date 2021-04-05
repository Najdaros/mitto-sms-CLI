package mitto.sms.hibernate;

import java.math.BigDecimal;
import java.util.Optional;

public class StatsDTO {
    private final String statsName;
    private final Long occurency;
    private final Optional<BigDecimal> optionalTotalPrice;

    public StatsDTO(String statsName, Long occurrence) {
        this(statsName, occurrence, null);
    }

    public StatsDTO(String statsName, Long occurrence, BigDecimal totalPrice) {
        this.statsName = statsName;
        this.occurency = occurrence;
        this.optionalTotalPrice = Optional.ofNullable(totalPrice);
    }

    @Override
    public String toString() {
        StringBuilder statsBuilder = new StringBuilder();
        statsBuilder.append(statsName).append(" ").append(occurency);
        optionalTotalPrice.ifPresent(bigDecimal -> statsBuilder.append(" ").append(bigDecimal.toString()));
        return statsBuilder.toString();
    }
}
