package tetracubered.slurpanize.identityProviderApi.account

import io.quarkus.redis.client.reactive.ReactiveRedisClient
import io.quarkus.runtime.StartupEvent
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import javax.enterprise.event.Observes
import javax.inject.Singleton


@Singleton
class AccountServices(
    private val redisClient: ReactiveRedisClient
) {

    companion object {
        private val logger: Logger = LoggerFactory.getLogger(AccountServices::class.java)
    }

    fun subscribeToMessageBroker(@Observes startupEvent: StartupEvent) {
        logger.info("Initializing subscription to message broker")
        redisClient.subscribe(listOf("admin-create"))
            .subscribe()
            .with { response ->
                logger.info(response.toString())
            }
    }
}