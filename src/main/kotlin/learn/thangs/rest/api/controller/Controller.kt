package learn.thangs.rest.api.controller

import io.vertx.core.Vertx
import io.vertx.ext.web.Router

/**
* controller interface defining base methods a controller should implement. 
* Controller provides a router which is responsible for routing requests to defined respective handlers
*/
interface Controller {
    fun getRouter(vertx: Vertx): Router
}
