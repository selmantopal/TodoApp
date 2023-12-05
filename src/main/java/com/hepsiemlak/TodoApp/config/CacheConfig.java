package com.hepsiemlak.TodoApp.config;

import com.couchbase.client.core.service.ServiceType;
import com.couchbase.client.java.diagnostics.WaitUntilReadyOptions;
import com.couchbase.client.java.manager.query.CreatePrimaryQueryIndexOptions;
import com.hepsiemlak.TodoApp.error.CouchbaseCacheErrorHandler;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cache.annotation.CachingConfigurerSupport;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.couchbase.CouchbaseClientFactory;
import org.springframework.data.couchbase.cache.CouchbaseCacheConfiguration;
import org.springframework.data.couchbase.cache.CouchbaseCacheManager;

import java.time.Duration;

@Configuration
@EnableCaching
public class CacheConfig extends CachingConfigurerSupport {

  @Value("${spring.cache.couchbase.expiration}")
  private Duration duration;

  @Bean
  public CouchbaseCacheManager cacheManager(CouchbaseClientFactory couchbaseClientFactory) {
    final CouchbaseCacheManager cacheManager = CouchbaseCacheManager
        .builder(couchbaseClientFactory)
        .initialCacheNames(new HashSet<>(Arrays.asList("cacheUserTodo", "UserLogin")))
        .disableCreateOnMissingCache()
        .cacheDefaults(CouchbaseCacheConfiguration
            .defaultCacheConfig()
            .entryExpiry(duration)).build();

    cacheManager.setTransactionAware(false);
    cacheManager.afterPropertiesSet();

    createIndex(couchbaseClientFactory);
    waitForKvReady(couchbaseClientFactory);

    return cacheManager;
  }

  private void waitForKvReady(CouchbaseClientFactory couchbaseClientFactory) {
    couchbaseClientFactory.getBucket().waitUntilReady(
        Duration.ofSeconds(20),
        WaitUntilReadyOptions.waitUntilReadyOptions().serviceTypes(new HashSet<>(Arrays.asList(ServiceType.KV, ServiceType.QUERY))));
  }

  private void createIndex(CouchbaseClientFactory couchbaseClientFactory) {

    couchbaseClientFactory
        .getCluster()
        .queryIndexes()
        .createPrimaryIndex(
            couchbaseClientFactory.getBucket().name(),
            CreatePrimaryQueryIndexOptions.createPrimaryQueryIndexOptions().ignoreIfExists(true));
  }

  @Override
  public CouchbaseCacheErrorHandler errorHandler() {
    return new CouchbaseCacheErrorHandler();
  }
}
