package minimonoid

/**
 * A minimal self-contained monoid implementation with no external dependencies.
 *
 * There are already lots of excellent monoid implementations out there:
 * have a look at `cats` or `scalaz` and rather use those if you can.
 *
 * - https://github.com/non/cats
 * - https://github.com/scalaz/scalaz
 *
 * This implementation is based on the monoid from https://github.com/non/algebra
 * As such it should be compatible with the `cats` monoid, since `cats` uses `algebra`.
 *
 * This code may be useful if you want to learn more about monoids and how easy it is to write one,
 * or if you quickly want to use a cats-compatible monoid but cannot include other dependencies in your project.
 *
 * https://github.com/apauley/scala-mini-monoid
 */

/**
 * A semigroup is any set `A` with an associative operation (`combine`).
 *
 * Have a look at a detailed implementation for comparison:
 * https://github.com/non/algebra/blob/master/core/src/main/scala/algebra/Semigroup.scala
 */
trait Semigroup[A] {
  def combine(x: A, y: A): A
}

object Semigroup {
  /** Provide a |+| operator to anything that implements Semigroup */
  @SuppressWarnings(Array("org.brianmckenna.wartremover.warts.ExplicitImplicitTypes"))
  implicit class SemigroupOps[A](sg1: A)(implicit sg: Semigroup[A]) {
    def |+|(sg2: A): A = sg.combine(sg1, sg2)
  }
}

/**
 * A monoid is a semigroup with an identity. A monoid is a specialization of a
 * semigroup, so its operation must be associative. Additionally,
 * `combine(x, empty) == combine(empty, x) == x`. For example, if we have `Monoid[String]`,
 * with `combine` as string concatenation, then `empty = ""`.
 *
 * Have a look at a detailed implementation for comparison:
 * https://github.com/non/algebra/blob/master/core/src/main/scala/algebra/Monoid.scala
 */
trait Monoid[A] extends Semigroup[A] {
  def empty: A
}

object MonoidInstances {

  import Semigroup.SemigroupOps

  implicit val intMonoid: Monoid[Int] = new Monoid[Int] {
    def empty: Int = 0
    def combine(x: Int, y: Int): Int = x+y
  }

  implicit val floatMonoid: Monoid[Float] = new Monoid[Float] {
    def empty: Float = 0f
    def combine(x: Float, y: Float): Float = x+y
  }

  implicit val doubleMonoid: Monoid[Double] = new Monoid[Double] {
    def empty: Double = 0d
    def combine(x: Double, y: Double): Double = x+y
  }

  implicit val stringMonoid: Monoid[String] = new Monoid[String] {
    def empty: String = ""
    def combine(x: String, y: String): String = x+y
  }

  implicit def listMonoid[A: Semigroup]: Monoid[List[A]] = new Monoid[List[A]] {
    def empty: List[A] = Nil
    def combine(x: List[A], y: List[A]): List[A] = x ++ y
  }

  implicit def optionMonoid[A: Semigroup]: Monoid[Option[A]] = new Monoid[Option[A]] {
    def empty: Option[A] = None

    def combine(ox: Option[A], oy: Option[A]): Option[A] = (ox, oy) match {
      case (Some(x), Some(y)) => Some(x |+| y)
      case (None, None) => None
      case (x, None) => x
      case (None, y) => y
    }
  }

  implicit def mapMonoid[K, V: Semigroup]: Monoid[Map[K, V]] = new Monoid[Map[K, V]] {
    def empty: Map[K, V] = Map.empty[K, V]
    def combine(x: Map[K, V], y: Map[K, V]): Map[K, V] = MapUtil.unionWith(x, y)(_ |+| _)
  }

  implicit def tuple2Monoid[A: Monoid, B: Monoid]: Monoid[(A,B)] = new Monoid[(A,B)] {
    def empty: (A,B) = (implicitly[Monoid[A]].empty, implicitly[Monoid[B]].empty)
    def combine(x: (A, B), y: (A, B)): (A, B) = (x._1 |+| y._1, x._2 |+| y._2)
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

    @SuppressWarnings(Array("org.brianmckenna.wartremover.warts.Throw")) // No idea why WartRemover complains about throw here
    val aug = m1 map {
      case (k, v) => if (m2 contains k) k -> f(k, v, m2(k)) else (k, v)
    }
    aug ++ diff
  }

}

object Monoid {
  def reduce[M: Monoid](l: List[M]): M = l.foldLeft(implicitly[Monoid[M]].empty)(implicitly[Monoid[M]].combine)
}

object MonoidOps {

  implicit class ListOps[A: Monoid](l: List[A]) {
    def reduceM: A = Monoid.reduce[A](l)
  }

}
