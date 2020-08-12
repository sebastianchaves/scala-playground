package scalawcats

import JsonWriter._

object Example extends App {

  val stringJsonWriter = implicitly[JsonWriter[String]]

  println(Option(2.0).toJson)
  println("asd".toJson)
  println(1.0.toJson)
  println(Person("carlos", 2).toJson)

}

sealed trait Json
final case class JsObject(o: Map[String, Json]) extends Json
final case class JsString(s: String) extends Json
final case class JsDouble(d: Double) extends Json
final case class JsInteger(i: Int) extends Json
final case class JsOption[A](o: Option[A]) extends Json
case object JsNull extends Json

final case class Person(name: String, age: Int)

trait JsonWriter[A] {
  def write(a: A): Json
}

object JsonWriter {
  implicit val stringWriter: JsonWriter[String] = (a: String) => JsString(a)
  implicit val numberWriter: JsonWriter[Double] = (d: Double) => JsDouble(d)
  implicit val personWriter: JsonWriter[Person] = (p: Person) => JsObject(Map[String, Json]("name" -> JsString(p.name), "years" -> JsInteger(p.age)))

  implicit def optionJsonWriter[A](implicit w: JsonWriter[A]): JsonWriter[Option[A]] = {
    case Some(value) => value.toJson(w)
    case None => JsNull
  }

  implicit class JsonWriterOps[A](a: A) {
    def toJson(implicit w: JsonWriter[A]): Json = w.write(a)
  }
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
