import akka.actor.ActorSystem
import akka.stream.ActorMaterializer
import akka.http.scaladsl.Http
import akka.http.scaladsl.model._
import akka.http.scaladsl.server.Directives._

object Hello {
  def main(args: Array[String]): Unit = {
    implicit val system = ActorSystem("web-server")
    implicit val materializer = ActorMaterializer()
    val HOST = "localhost"
    val PORT = 8080

    val route =
    concat(
      path("greet" / Remaining) { name => {
        get {
          complete(HttpEntity(ContentTypes.`application/json`, s"""{"message" : "Hello $name"}"""))
        }
      }},
      path("health") {
        get {
          complete(HttpEntity(ContentTypes.`application/json`, """{"status" : "OK"}"""))
        }
      }
    )

    Http().newServerAt(HOST, PORT).bind(route)
    println(s"Server listening at $PORT...")
  }
}
