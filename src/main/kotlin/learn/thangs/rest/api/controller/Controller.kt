package learn.thangs.rest.api.controller

import io.vertx.core.Vertx
import io.vertx.ext.web.Router

interface Controller {
    fun getRouter(vertx: Vertx): Router
}