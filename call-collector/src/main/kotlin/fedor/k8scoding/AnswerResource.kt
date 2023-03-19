package fedor.k8scoding

import fedor.k8scoding.service.AppService
import fedor.k8scoding.service.AppStorage
import io.smallrye.mutiny.Multi
import javax.ws.rs.GET
import javax.ws.rs.Path
import javax.ws.rs.Produces
import javax.ws.rs.core.MediaType

@Path("/answer")
class AnswerResource(
    val appService: AppService,
    val appStorage: AppStorage,
) {

    @GET
    @Path("/snapshot")
    @Produces(MediaType.TEXT_PLAIN)
    fun snapshot(): String {
        return appStorage.resps.toString()
    }


    @GET
    @Path("/stream")
    @Produces(MediaType.SERVER_SENT_EVENTS)
    fun stream(): Multi<String> {
        return appService.responses
    }
}