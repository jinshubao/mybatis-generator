package com.jean.mybatis.generator.config;


import com.jean.mybatis.generator.core.GeneratorService;
import com.jean.mybatis.generator.factory.DefaultTaskFactory;
import com.jean.mybatis.generator.factory.ITaskFactory;
import com.jean.mybatis.generator.support.provider.DefaultMetaDataProviderManager;
import com.jean.mybatis.generator.support.provider.IMetaDataProviderManager;
import com.jean.mybatis.generator.support.provider.IMetadataProvider;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.concurrent.DefaultManagedAwareThreadFactory;

import java.util.List;
import java.util.concurrent.Executor;
import java.util.concurrent.SynchronousQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * @author jinshubao
 */
@Configuration
public class Config {

    @Bean
    public IMetaDataProviderManager providerManager(List<IMetadataProvider> providers) {
        return new DefaultMetaDataProviderManager(providers);
    }

    @Bean
    public GeneratorService generatorService(@Qualifier("generate-executor") Executor executor) {
        GeneratorService service = new GeneratorService();
        service.setExecutor(executor);
        return service;
    }

    @Bean("generate-executor")
    Executor executor() {
        return new ThreadPoolExecutor(2, 5, 600, TimeUnit.SECONDS,
                new SynchronousQueue<>(),
                new DefaultManagedAwareThreadFactory());
    }

    @Bean
    ITaskFactory taskFactory(@Qualifier("generate-executor") Executor executor,IMetaDataProviderManager providerManager){
        return new DefaultTaskFactory(executor, providerManager);
    }

}
