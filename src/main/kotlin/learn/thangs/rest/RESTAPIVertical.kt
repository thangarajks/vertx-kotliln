package learn.thangs.rest

import io.netty.handler.codec.http.HttpHeaderNames
import io.netty.handler.codec.http.HttpHeaderValues
import io.vertx.core.AsyncResult
import io.vertx.core.http.HttpServer
import io.vertx.core.json.JsonObject
import io.vertx.ext.web.Router
import io.vertx.ext.web.handler.BodyHandler
import io.vertx.ext.web.handler.LoggerHandler
import io.vertx.kotlin.coroutines.CoroutineVerticle
import learn.thangs.AppConfig
import learn.thangs.rest.api.controller.HealthCheckController
import learn.thangs.util.getLogger


class RESTAPIVertical : CoroutineVerticle() {

    // get logger object to capture and handle logs
    private val logger = getLogger()

    override suspend fun start() {
        val router = Router.router(vertx)

        // LoggerHandler to log all served requests
        router.route("/*").handler(LoggerHandler.create())

        // Parse body and file contents from request to RoutingContext
        router.route("/*").handler(BodyHandler.create())

        // HealthCheck controller attached to path root
        router.route("/api/*").subRouter(HealthCheckController().getRouter(vertx))

        router.route().failureHandler { ctx ->
            logger.error(ctx.failure())
            ctx
                .response()
                .setStatusCode(500)
                .putHeader(HttpHeaderNames.CONTENT_TYPE, HttpHeaderValues.APPLICATION_JSON)
                .end(JsonObject().put("status","not okay").toString())
        }

        vertx
            .createHttpServer()
            .requestHandler { router.handle(it) }
            .listen(AppConfig.HTTP_SERVER_PORT) { listnerResult: AsyncResult<HttpServer?> ->
                if (listnerResult.failed()) {
                    logger.error("Failed to Start HTTP Server on port : ${AppConfig.HTTP_SERVER_PORT}", listnerResult.cause())
                } else {
                    logger.info("Successfully Started HTTP Server on port : ${AppConfig.HTTP_SERVER_PORT}")
                }
            }
    }

    override suspend fun stop() {
        super.stop()
    }

}