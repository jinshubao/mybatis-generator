package com.jean.mybatis.generator.configuration;


import com.jean.mybatis.generator.support.provider.DefaultMetaDataProviderManager;
import com.jean.mybatis.generator.support.provider.IMetaDataProviderManager;
import com.jean.mybatis.generator.support.provider.IMetadataProvider;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.List;

@Configuration
public class Config {

    @Bean
    public IMetaDataProviderManager providerManager(List<IMetadataProvider> providers) {
        return new DefaultMetaDataProviderManager(providers);
    }

}
