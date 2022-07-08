package learn.thangs.rest.api.controller

import io.netty.handler.codec.http.HttpHeaderNames
import io.netty.handler.codec.http.HttpHeaderValues
import io.vertx.core.Vertx
import io.vertx.core.json.JsonObject
import io.vertx.ext.web.Router
import io.vertx.ext.web.RoutingContext
import learn.thangs.util.getLogger

class HealthCheckController : Controller {

    private val logger = getLogger()
    override fun getRouter(vertx: Vertx): Router {
        val router = Router.router(vertx)
        router.get("/healthcheck").handler { ctx -> healthCheckHandler(ctx) }
        return router
    }

    private fun healthCheckHandler(ctx : RoutingContext){
        logger.debug("healthCheckHandler()")
        ctx
            .response()
            .setStatusCode(200)
            .putHeader(HttpHeaderNames.CONTENT_TYPE,HttpHeaderValues.APPLICATION_JSON)
            .end(JsonObject().put("status","ok").toString())
    }
}