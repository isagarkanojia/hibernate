package com.mv.hibernate.config;

import com.mv.hibernate.FirstLevelCache.DifferentTransactionsCacheExample;
import com.mv.hibernate.FirstLevelCache.FirstLevelCacheExample;
import com.mv.hibernate.FirstLevelCache.SaveAndCacheExample;
import com.mv.hibernate.FirstLevelCache.UpdatesInCacheExample;
import com.mv.hibernate.ManyToMany.ManyToManyJPAExample;
import com.mv.hibernate.SecondLevelCache.SecondLevelCacheExample;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
public class Runner implements CommandLineRunner {

    @Autowired
    private ManyToManyJPAExample manyToManyExample;

    @Autowired
    private FirstLevelCacheExample firstLevelCacheExample;

    @Autowired
    private DifferentTransactionsCacheExample differentTransactionsExample;

    @Autowired
    private UpdatesInCacheExample updatesInCacheExample;

    @Autowired
    private SaveAndCacheExample saveAndCacheExample;

    @Autowired
    private SecondLevelCacheExample secondLevelCacheExample;

    @Override
    public void run(String... args) throws Exception {

//        // Example 1: Basic First Level Cache
//        firstLevelCacheExample.demonstrateFirstLevelCache();

        // Example 2: Different Transactions = Different Cache
//        differentTransactionsExample.demonstrate();

        // Example: Caffeine Second Level Cache
        secondLevelCacheExample.demonstrateSecondLevelCache();
//        secondLevelCacheExample.demonstrateQueryCache();

//        // Example 3: Updates in Cache
//        try {
//            updatesInCacheExample.demonstrate();
//        } catch (RuntimeException e) {
//            System.out.println("✓ Transaction rolled back as expected\n");
//        }
//
//        // Example 4: Save and Cache
//        try {
//            saveAndCacheExample.demonstrate();
//        } catch (RuntimeException e) {
//            System.out.println("✓ Transaction rolled back as expected\n");
//        }

    }
}
