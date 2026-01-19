package com.mv.hibernate.config;

import com.mv.hibernate.FirstLevelCache.FirstLevelCacheExample;
import com.mv.hibernate.ManyToMany.ManyToManyJPAExample;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
public class Runner implements CommandLineRunner {

    @Autowired
    private ManyToManyJPAExample manyToManyExample;

    @Autowired
    private FirstLevelCacheExample firstLevelCacheExample;

    @Override
    public void run(String... args) throws Exception {

        // First Level Cache Example
        firstLevelCacheExample.demonstrateFirstLevelCache();
        

    }
}
