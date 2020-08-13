package scalawcats.monoidsnsemigroup

import Monoid._

object MonoidAndSemigroup extends App {

  println(booleanAndMonoid.associativeLaw(true, false, true))
  println(booleanAndMonoid.identityLaw(true))

  println(booleanOrMonoid.associativeLaw(true, true, true))
  println(booleanOrMonoid.identityLaw(false))

  println(booleanEitherMonoid.associativeLaw(true, false, true))
  println(booleanEitherMonoid.identityLaw(false))

  println(booleanXnorMonoid.associativeLaw(true, true, true))
  println(booleanXnorMonoid.identityLaw(false))

  val intSetMonoid: Monoid[Set[Int]] = setUnionMonoid[Int]()
  println(intSetMonoid.associativeLaw(Set(1, 2), Set(2, 4), Set(2, 4)))

}

trait Semigroup[A] {
  def combine(x: A, y: A): A // => closed binary operation
}

trait Monoid[A] extends Semigroup[A] {
  def empty: A // => identity

  def associativeLaw(x: A, y: A, z: A): Boolean = combine(x, combine(y, z)) == combine(combine(x, y), z) // => must be associative
  def identityLaw(x: A): Boolean = (combine(x, empty) == x) && (combine(empty, x) == x) // => empty must be an identity element
}

object Monoid {
  implicit val booleanAndMonoid: Monoid[Boolean] = new Monoid[Boolean] {
    def empty: Boolean = true
    def combine(x: Boolean, y: Boolean): Boolean = x && y
  }

  implicit val booleanOrMonoid: Monoid[Boolean] = new Monoid[Boolean] {
    def empty: Boolean = false
    def combine(x: Boolean, y: Boolean): Boolean = x || y
  }

  implicit val booleanEitherMonoid: Monoid[Boolean] = new Monoid[Boolean] {
    def empty: Boolean = false
    def combine(x: Boolean, y: Boolean): Boolean = (x && !y) || (y && !x)
  }

  implicit val booleanXnorMonoid: Monoid[Boolean] = new Monoid[Boolean] {
    def empty: Boolean = true
    def combine(x: Boolean, y: Boolean): Boolean = (!x || y) && (x || !y)
  }

  implicit def setUnionMonoid[A](): Monoid[Set[A]] = new Monoid[Set[A]] {
    def empty: Set[A] = Set.empty[A]
    def combine(x: Set[A], y: Set[A]): Set[A] = x union y
  }

  implicit def setIntersectionMonoid[A](): Semigroup[Set[A]] = (x: Set[A], y: Set[A]) => x intersect y

  implicit def symDiffMonoid[A](): Monoid[Set[A]] = new Monoid[Set[A]] {
    def empty: Set[A] = Set.empty[A]
    def combine(x: Set[A], y: Set[A]): Set[A] = (x diff y) union (y diff x)
  }
}
