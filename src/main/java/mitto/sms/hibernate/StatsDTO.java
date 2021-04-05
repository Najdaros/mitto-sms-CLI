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

    public Long getOccurency() {
        return occurency;
    }

    public Optional<BigDecimal> getOptionalTotalPrice() {
        return optionalTotalPrice;
    }

    public String getStatsName() {
        return statsName;
    }

    @Override
    public String toString() {
        StringBuilder statsBuilder = new StringBuilder();
        statsBuilder.append(statsName).append(" ").append(occurency);
        if (optionalTotalPrice.isPresent())
            statsBuilder.append(" ").append(getOptionalTotalPrice().get().toString());
        return statsName.toString();
    }
}
