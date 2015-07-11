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

  implicit val floatMonoid = new Monoid[Float] {
    def id: Float = 0f
    def op(x: Float, y: Float): Float = x+y
  }

  implicit val doubleMonoid = new Monoid[Double] {
    def id: Double = 0d
    def op(x: Double, y: Double): Double = x+y
  }

  implicit val stringMonoid = new Monoid[String] {
    def id: String = ""
    def op(x: String, y: String): String = x+y
  }

  implicit def listMonoid[A: Semigroup]: Monoid[List[A]] = new Monoid[List[A]] {
    def id: List[A] = Nil
    def op(x: List[A], y: List[A]): List[A] = x ++ y
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

  implicit def mapMonoid[K, V: Semigroup]: Monoid[Map[K, V]] = new Monoid[Map[K, V]] {
    def id: Map[K, V] = Map.empty[K, V]
    def op(x: Map[K, V], y: Map[K, V]): Map[K, V] = MapUtil.unionWith(x, y)(_ |+| _)
  }

  implicit def tuple2Semigroup[A: Semigroup, B: Semigroup]: Semigroup[(A,B)] = new Semigroup[(A,B)] {
    def op(x: (A, B), y: (A, B)): (A, B) = (x._1 |+| y._1, x._2 |+| y._2)
  }

}

object MapUtil {
  /** Helpers from scalaz */

  /** Union, resolving collisions with `f`, where the first arg is
    * guaranteed to be from `m1`, the second from `m2`.
    */
  def unionWith[K,A](m1: Map[K, A], m2: Map[K, A])(f: (A, A) => A): Map[K, A] =
    unionWithKey(m1, m2)((_, x, y) => f(x, y))

  /** Like `unionWith`, but telling `f` about the key. */
  def unionWithKey[K,A](m1: Map[K, A], m2: Map[K, A])(f: (K, A, A) => A): Map[K, A] = {
    val diff = m2 -- m1.keySet
    val aug = m1 map {
      case (k, v) => if (m2 contains k) k -> f(k, v, m2(k)) else (k, v)
    }
    aug ++ diff
  }

}
