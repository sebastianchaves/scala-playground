// Parametric polymorphism
// No matters what T is, the implementation of .last is the same
val integers = List[String]("1", "2", "3")
integers.last

// subtype polymorphism -> runtime
object SubtypeP {
  trait A {
    def doSome(): Int
  }

  class B extends A {
    def doSome() = 1
  }

  class C extends A {
    def doSome() = 2
  }
}

// ad-hoc polymorphism -> compile time
// The implementation depends on the type
// Example 1 = method overload
object Overload {
  def method(a: Int, b: Int): Int = a + b
  def method(a: String, b: String): String = a + b
}

