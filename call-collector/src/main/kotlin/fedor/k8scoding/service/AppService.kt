package fedor.k8scoding.service

import io.smallrye.mutiny.Multi
import org.eclipse.microprofile.config.inject.ConfigProperty
import org.eclipse.microprofile.reactive.messaging.Channel
import org.eclipse.microprofile.reactive.messaging.Emitter
import javax.enterprise.context.ApplicationScoped
import javax.inject.Inject

@ApplicationScoped
class AppService(
    @Inject
    @ConfigProperty(name = "greeting.message", defaultValue = "Hello from default")
    val message: String,


    @Inject
    @Channel("call-response-in")
    val responses: Multi<String>,

    @Inject
    @Channel("call-request-out")
    val callRequestEmitter: Emitter<String>
)