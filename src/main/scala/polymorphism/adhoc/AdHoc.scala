package polymorphism.adhoc

import WithImplicitConversion._

object AdHoc extends App {

  def main(): Unit = {
    println(s"WithImplicitConversion: ${WithImplicitConversion.appendItems(1, 2)}")
    println(s"WithTypeClasses: ${WithTypeClasses.appendItems(1, 2)}")
  }

  main()
}

object WithImplicitConversion {
  def appendItems[A](a1: A, a2: A)(implicit ev: A => Appendable[A]): A = a1 append a2

  trait Appendable[A] {
    def append(a: A): A
  }

  implicit class AppendableInt(i: Int) extends Appendable[Int] {
    def append(a: Int): Int = i + a
  }

  implicit class AppendableString(s: String) extends Appendable[String] {
    def append(a: String): String = s concat a
  }
}

object WithTypeClasses {
  def appendItems[A](a1: A, a2: A)(implicit ev: Appendable[A]): A = ev.append(a1, a2)

  trait Appendable[A] {
    def append(a: A, b: A): A

    def greater(a: A, b: A): A
  }

  object Appendable {
    implicit val appendableInt: Appendable[Int] = new Appendable[Int] {
      def append(a: Int, b: Int): Int = a + b

      def greater(a: Int, b: Int): Int = if (a > b) a else b
    }

    implicit val appendableString: Appendable[String] = new Appendable[String] {
      def append(a: String, b: String): String = a concat b

      def greater(a: String, b: String): String = if (a > b) a else b
    }
  }
}
