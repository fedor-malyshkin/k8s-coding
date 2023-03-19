package fedor.k8scoding

import javax.ws.rs.GET
import javax.ws.rs.Path
import javax.ws.rs.core.Response

@Path("/probes")
class ProbeResource {
    @GET
    @Path("/live")
    fun live() = Response.ok().build()

    @GET
    @Path("/ready")
    fun ready() = Response.ok().build()
}