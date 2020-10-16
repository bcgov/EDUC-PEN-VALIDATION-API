package ca.bc.gov.educ.api.pen.services.config;

import ca.bc.gov.educ.api.pen.services.properties.ApplicationProperties;
import org.redisson.Redisson;
import org.redisson.api.RedissonClient;
import org.redisson.client.codec.StringCodec;
import org.redisson.config.Config;
import org.redisson.spring.data.connection.RedissonConnectionFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * The type Redisson spring data config.
 */
@Configuration
public class RedissonSpringDataConfig {

  /**
   * The Application properties.
   */
  private final ApplicationProperties applicationProperties;

  /**
   * Instantiates a new Redisson spring data config.
   *
   * @param applicationProperties the application properties
   */
  public RedissonSpringDataConfig(ApplicationProperties applicationProperties) {
    this.applicationProperties = applicationProperties;
  }

  /**
   * Redisson connection factory redisson connection factory.
   *
   * @param redisson the redisson
   * @return the redisson connection factory
   */
  @Bean
  public RedissonConnectionFactory redissonConnectionFactory(RedissonClient redisson) {
    return new RedissonConnectionFactory(redisson);
  }

  /**
   * Redisson redisson client.
   *
   * @return the redisson client
   */
  @Bean(destroyMethod = "shutdown")
  public RedissonClient redisson() {
    RedissonClient redisson;
    Config config = new Config();
    if ("local".equals(applicationProperties.getEnvironment())) {
      config.useSingleServer()
          .setAddress(applicationProperties.getRedisUrl());
    } else {
      config.useClusterServers()
          .addNodeAddress(applicationProperties.getRedisUrl());
    }
    redisson= Redisson.create(config);
    redisson.getConfig().setCodec(StringCodec.INSTANCE);
    return redisson;
  }
}
