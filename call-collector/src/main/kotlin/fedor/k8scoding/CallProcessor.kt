package fedor.k8scoding


import fedor.k8scoding.service.AppStorage
import org.eclipse.microprofile.reactive.messaging.Incoming
import org.eclipse.microprofile.reactive.messaging.Outgoing
import javax.enterprise.context.ApplicationScoped

@ApplicationScoped
class CallProcessor(
    val appStorage: AppStorage
) {

    @Incoming("call-request-in")
    @Outgoing("call-response-out")
    fun process(callRequest: String): String {
        val resp = callRequest.uppercase()
        appStorage.resps.add(resp)
        return resp
    }
}