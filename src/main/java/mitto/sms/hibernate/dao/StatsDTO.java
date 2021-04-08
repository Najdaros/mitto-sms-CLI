package mitto.sms.hibernate.dao;

import java.math.BigDecimal;
import java.util.Optional;

public class StatsDTO {
    private final String statsName;
    private final Long occurrence;
    private final BigDecimal totalPrice;

    public StatsDTO(String statsName, Long occurrence) {
        this(statsName, occurrence, null);
    }

    public StatsDTO(String statsName, Long occurrence, BigDecimal totalPrice) {
        this.statsName = statsName;
        this.occurrence = occurrence;
        this.totalPrice = totalPrice;
    }

    public String getStatsName() {
        return statsName;
    }

    public Long getOccurrence() {
        return occurrence;
    }

    public BigDecimal getTotalPrice() {
        return totalPrice;
    }

    @Override
    public String toString() {
        StringBuilder statsBuilder = new StringBuilder();
        statsBuilder.append(statsName).append(" ").append(occurrence);
        Optional<BigDecimal> optionalTotalPrice = Optional.ofNullable(this.totalPrice);
        optionalTotalPrice.ifPresent(bigDecimal -> statsBuilder.append(" ").append(bigDecimal.toString()));
        return statsBuilder.toString();
    }
}
