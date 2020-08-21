package com.es.phoneshop.services.impl;

import com.es.phoneshop.security.DosUserInfo;
import com.es.phoneshop.services.DosProtectionService;

import java.util.Date;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.TimeUnit;

public class DefaultDosProtectionService implements DosProtectionService {

    private Map<String, DosUserInfo> userCounterMap;

    private final long maxRequests = 20L;
    private final long maxTime = 60L;
    private final long unbanTime = 120L;

    private DefaultDosProtectionService() {
        userCounterMap = new ConcurrentHashMap<>();
    }

    private static class BillPughSingleton {
        private static final DefaultDosProtectionService INSTANCE = new DefaultDosProtectionService();
    }

    public static DefaultDosProtectionService getInstance() {
        return BillPughSingleton.INSTANCE;
    }

    @Override
    public boolean isAllow(String ip) {
        DosUserInfo dosUserInfo = userCounterMap.get(ip);
        if (dosUserInfo == null) {
            dosUserInfo = new DosUserInfo(new Date(), 1L);
        } else if (dosUserInfo.isBlocked()) {
            long secondsDiff = calculateSecondsDiff(dosUserInfo);
            //unban condition
            if (secondsDiff >= unbanTime) {
                resetUser(dosUserInfo);
                return true;
            }
            dosUserInfo.setDate(new Date());
            return false;
        } else {
            long secondsDiff = calculateSecondsDiff(dosUserInfo);
            if (secondsDiff < maxTime) {
                //ban condition
                if (dosUserInfo.getCounter() > maxRequests) {
                    return false;
                }
            } else {
                resetUser(dosUserInfo);
            }
            dosUserInfo.incrementCounter();
        }
        userCounterMap.put(ip, dosUserInfo);
        return true;
    }

    private void resetUser(DosUserInfo user) {
        user.setCounter(1L);
        user.setDate(new Date());
    }

    private long calculateSecondsDiff(DosUserInfo user) {
        long millisecondsDiff = new Date().getTime() - user.getDate().getTime();
        long secondsDiff = TimeUnit.MILLISECONDS.toSeconds(millisecondsDiff);
        return secondsDiff;
    }
}
