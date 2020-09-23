import akka.http.scaladsl.model.{ContentTypes, HttpEntity}
import akka.http.scaladsl.server.Directives._
import org.scalatest.{FlatSpec, Matchers}
import akka.http.scaladsl.testkit.ScalatestRouteTest

class HelloTest extends FlatSpec with Matchers with ScalatestRouteTest {
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

  "route" should "return a status:ok for GET request to the health path" in {
      Get("/health") ~> route ~> check {
        responseAs[String] shouldEqual """{"status" : "OK"}"""
      }
    }

  it should "return a greeting for GET request to the greet path" in {
    Get("/greet/") ~> route ~> check {
      responseAs[String] shouldEqual """{"message" : "Hello "}"""
    }

    Get("/greet/surya") ~> route ~> check {
      responseAs[String] shouldEqual """{"message" : "Hello surya"}"""
    }

    Get("/greet/Test") ~> route ~> check {
      responseAs[String] shouldEqual """{"message" : "Hello Test"}"""
    }
  }
}
