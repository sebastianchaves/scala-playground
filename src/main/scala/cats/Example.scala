package cats

import cats.JsonWriterInstances._
import cats.JsonSyntax._

object Example extends App {

  println("asd".toJson)
  println(1.0.toJson)
  println(Person("carlos", 2).toJson)

}

sealed trait Json
final case class JsObject(o: Map[String, Json]) extends Json
final case class JsString(s: String) extends Json
final case class JsDouble(d: Double) extends Json
final case class JsInteger(i: Int) extends Json
case object JsNull extends Json

final case class Person(name: String, years: Int)

trait JsonWriter[A] {
  def write(a: A): Json
}

object JsonWriterInstances {
  implicit val stringJsonWriter: JsonWriter[String] = (a: String) => JsString(a)
  implicit val numberJsonWriter: JsonWriter[Double] = (d: Double) => JsDouble(d)
  implicit val personJsonWriter: JsonWriter[Person] = (p: Person) => JsObject(Map[String, Json]("name" -> JsString(p.name), "years" -> JsInteger(p.years)))
}

// Interface Object
object Json {
  def toJson[A](s: A)(implicit ev: JsonWriter[A]): Json = ev.write(s)
}

// Interface Syntax
object JsonSyntax {
  implicit class JsonWriterOps[A](a: A) {
    def toJson(implicit w: JsonWriter[A]): Json = w.write(a)
  }
}
