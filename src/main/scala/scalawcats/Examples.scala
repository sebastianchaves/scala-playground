package scalawcats

import java.util.Date

import cats.{Eq, Show}
import cats.instances.int._
import cats.instances.long._
import cats.instances.option._
import cats.instances.string._
import cats.syntax.show._
import cats.syntax.eq._

object ShowExamples extends App {

  implicit val catShow: Show[Cat] = Show.show(c => s"${c.name} is a ${c.age} year-old ${c.color} cat.")

  println(1.show)
  println(Cat("Zeus", 5, "grey").show)

}

object EqExamples extends App {

  implicit val dateEq: Eq[Date] = (a: Date, b: Date) => a.getTime === b.getTime
  implicit val catEq: Eq[Cat] = (x: Cat, y: Cat) => x.name === y.name && x.age === y.age && x.color === y.color

  val zeus = Cat("Zeus", 5, "grey")
  val eros = Cat("Eros", 1, "white")

  println(zeus === eros)
  println(zeus =!= eros)
  println(Option(zeus) === None)
  println(Option(zeus) =!= None)

}
