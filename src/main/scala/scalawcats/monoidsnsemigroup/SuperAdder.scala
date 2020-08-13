package scalawcats.monoidsnsemigroup

//import cats.instances.int._
//import cats.instances.option._
import cats.Monoid
import cats.syntax.monoid._

object SuperAdder extends App {

  val res1 = Adder.add(List(1, 2, 3, 4))
  val res2 = Adder.add(List(Option(1), Option(2)))

  println(res1)
  println(res2)

}

trait Adder[A] {
  def add(a: A): Int
}

object Adder {

  def add[A](a: List[A])(implicit monoid: Monoid[A]): A = a.foldLeft(monoid.empty)(_ |+| _)

  implicit val intAdder: Adder[List[Int]] = new Adder[List[Int]] {
    def add(a: List[Int]): Int = a.foldLeft(monoid.empty)(_ |+| _)
  }
}


