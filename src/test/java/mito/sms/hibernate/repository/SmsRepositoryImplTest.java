package mito.sms.hibernate.repository;

import mitto.sms.hibernate.dao.StatsDTO;
import mitto.sms.hibernate.entity.CountryFee;
import mitto.sms.hibernate.entity.SMS;
import mitto.sms.hibernate.repository.SmsRepositoryImpl;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;


@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@ExtendWith(MockitoExtension.class)
class SmsRepositoryImplTest {

    private SmsRepositoryImpl smsRepository = new SmsRepositoryImpl();

    private Integer LIMIT = 5;

    private List<SMS> getListOfSuccessSmsMessages() {
        ArrayList<SMS> successSMSList = new ArrayList<>();
        CountryFee slovakiaCountryFee = new CountryFee(421, "Slovakia", new BigDecimal("0.100"));
        CountryFee usaCountryFee1 = new CountryFee(222, "USA", new BigDecimal("0.001"));
        successSMSList.add(createSMS("A", "421456789012", "A", slovakiaCountryFee));
        successSMSList.add(createSMS("B", "421456789012", "B", slovakiaCountryFee));
        successSMSList.add(createSMS("C", "421456789012", "C", slovakiaCountryFee));
        successSMSList.add(createSMS("D", "421456789012", "D", slovakiaCountryFee));
        successSMSList.add(createSMS("D", "222222222222", "D1", usaCountryFee1));
        successSMSList.add(createSMS("111111111111", "222222222222", "2", usaCountryFee1));
        successSMSList.add(createSMS("111111111111", "222222222222", "3", usaCountryFee1));
        successSMSList.add(createSMS("111111111111", "421456789012", "4", slovakiaCountryFee));
        return successSMSList;
    }

    private List<SMS> getListOfSmsMessages() {
        ArrayList<SMS> allSMSList = new ArrayList<>();
        allSMSList.add(createSMS("A", "222222222222", "A",null));
        allSMSList.add(createSMS("A", "222222222222", "A",null));
        allSMSList.add(createSMS("A", "222222222222", "A",null));
        allSMSList.addAll(getListOfSuccessSmsMessages());
        return allSMSList;
    }

    private SMS createSMS(String sender, String receiver, String text, CountryFee countryFee) {
        SMS sms = new SMS(sender, receiver, text);
        sms.setCountryFee(countryFee);
        sms.setSuccess(countryFee != null);
        return sms;
    }

    private void verifyStatsDTO(String expectedStatsName, Long expectedOccurrence, BigDecimal expectedTotalPrice, StatsDTO statsDTO) {
        assertEquals(expectedStatsName, statsDTO.getStatsName());
        assertEquals(expectedOccurrence, statsDTO.getOccurrence());
        if(expectedTotalPrice != null) {
            assertEquals(expectedTotalPrice, statsDTO.getTotalPrice());
        } else {
            assertNull(statsDTO.getTotalPrice());
        }
    }
    @Test
    void findTopSendersTest() {
        SmsRepositoryImpl repository = Mockito.spy(smsRepository);
        Mockito.doReturn(getListOfSmsMessages()).when(repository).findAll();

        List<StatsDTO> topSenders = repository.findTopSenders(LIMIT);
        assertNotNull(topSenders);
        assertEquals(LIMIT, topSenders.size());
        verifyStatsDTO("A", 4L, null, topSenders.get(0));
        verifyStatsDTO("111111111111", 3L, null, topSenders.get(1));
        verifyStatsDTO("D", 2L, null, topSenders.get(2));
        verifyStatsDTO("B", 1L, null, topSenders.get(3));
        verifyStatsDTO("C", 1L, null, topSenders.get(4));
    }

    @Test
    void findTopSendersWithFeeTest() {
        SmsRepositoryImpl repository = Mockito.spy(smsRepository);
        Mockito.doReturn(getListOfSuccessSmsMessages()).when(repository).findAllSuccess(Boolean.TRUE);

        List<StatsDTO> topSendersWithFee = repository.findTopSendersWithFee(LIMIT);
        assertNotNull(topSendersWithFee);
        assertEquals(LIMIT, topSendersWithFee.size());
        verifyStatsDTO("111111111111", 3L, new BigDecimal("0.102"), topSendersWithFee.get(0));
        verifyStatsDTO("D", 2L, new BigDecimal("0.101"), topSendersWithFee.get(1));
        verifyStatsDTO("A", 1L, new BigDecimal("0.100"), topSendersWithFee.get(2));
        verifyStatsDTO("B", 1L, new BigDecimal("0.100"), topSendersWithFee.get(3));
        verifyStatsDTO("C", 1L, new BigDecimal("0.100"), topSendersWithFee.get(4));

    }

    @Test
    void getCountryFeeStatsTest() {
        SmsRepositoryImpl repository = Mockito.spy(smsRepository);
        Mockito.doReturn(getListOfSuccessSmsMessages()).when(repository).findAllSuccess(Boolean.TRUE);

        List<StatsDTO> countryFeeStats = repository.getCountryFeeStats();
        assertNotNull(countryFeeStats);
        assertEquals(2, countryFeeStats.size());
        verifyStatsDTO("Slovakia", 5L, new BigDecimal("0.500"), countryFeeStats.get(0));
        verifyStatsDTO("USA", 3L, new BigDecimal("0.003"), countryFeeStats.get(1));
    }


}
