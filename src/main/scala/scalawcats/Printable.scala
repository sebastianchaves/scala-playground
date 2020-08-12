package scalawcats

import Printable._

object PrintableExample extends App {
  1.print
  "asd".print
  Cat("Zeus", 5, "grey").print
}

trait Printable[A] {
  def format(a: A): String
}

object Printable {
  implicit val stringPrintable: Printable[String] = s => s
  implicit val intPrintable: Printable[Int] = i => i.toString

  implicit class PrintableOps[A](a: A) {
    def format(implicit ev: Printable[A]): String = ev.format(a)
    def print(implicit ev: Printable[A]): Unit = println(ev.format(a))
  }
}

final case class Cat(name: String, age: Int, color: String)

object Cat {
  implicit val catPrintable: Printable[Cat] = c => s"${c.name} is a ${c.age} year-old ${c.color} cat."
}
