package com.mv.hibernate.config;

import com.mv.hibernate.FirstLevelCache.DifferentTransactionsCacheExample;
import com.mv.hibernate.FirstLevelCache.FirstLevelCacheExample;
import com.mv.hibernate.FirstLevelCache.SaveAndCacheExample;
import com.mv.hibernate.FirstLevelCache.UpdatesInCacheExample;
import com.mv.hibernate.ManyToMany.ManyToManyJPAExample;
import com.mv.hibernate.NPlusOne.NPlusOneProblemDemo;
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

    @Autowired
    private NPlusOneProblemDemo nPlusOneProblemDemo;

    @Override
    public void run(String... args) {
        // N+1 Problem Demonstration

        // Show the problem
        //nPlusOneProblemDemo.demonstrateNPlusOneProblem();

        nPlusOneProblemDemo.demonstrateSolutionWithJoinFetch();

    }
}
