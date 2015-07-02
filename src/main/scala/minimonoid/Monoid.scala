package minimonoid

/**
 * A minimal self-contained Monoid implementation.
 *
 * There are already lots of excellent Monoid implementations out there:
 * have a look at cats or scalaz and rather use those if you can.
 *
 * - https://github.com/non/cats
 * - https://github.com/scalaz/scalaz
 *
 * This implementation may be useful to learn more about Monoids,
 * or if you cannot include other dependencies in your project.
 *
 * https://github.com/apauley/scala-mini-monoid
 */

/**
 * A semigroup is any set `A` with an associative operation (`op`).
 *
 * Have a look at a detailed implementation for comparison:
 * https://github.com/non/spire/blob/master/core/src/main/scala/spire/algebra/Semigroup.scala
 */
trait Semigroup[A] {
  def op(x: A, y: A): A
}

object Semigroup {
  /** Provide a |+| operator to anything that implements Semigroup */
  implicit class SemigroupOps[A](sg1: A)(implicit sg: Semigroup[A]) {
    def |+|(sg2: A): A = sg.op(sg1, sg2)
  }
}

/**
 * A monoid is a semigroup with an identity. A monoid is a specialization of a
 * semigroup, so its operation must be associative. Additionally,
 * `op(x, id) == op(id, x) == x`. For example, if we have `Monoid[String]`,
 * with `op` as string concatenation, then `id = ""`.
 *
 * Have a look at a detailed implementation for comparison:
 * https://github.com/non/spire/blob/master/core/src/main/scala/spire/algebra/Monoid.scala
 */
trait Monoid[A] extends Semigroup[A] {
  def id: A
}

object MonoidInstances {

  import Semigroup.SemigroupOps

  implicit val intMonoid = new Monoid[Int] {
    def id: Int = 0
    def op(x: Int, y: Int): Int = x+y
  }

  implicit val stringMonoid = new Monoid[String] {
    def id: String = ""
    def op(x: String, y: String): String = x+y
  }

  implicit def optionMonoid[A: Semigroup]: Monoid[Option[A]] = new Monoid[Option[A]] {
    def id: Option[A] = None
    def op(ox: Option[A], oy: Option[A]): Option[A] = (ox, oy) match {
      case (Some(x), Some(y)) => Some(x |+| y)
      case (None, None) => None
      case (x, None) => x
      case (None, y) => y
    }
  }

}
