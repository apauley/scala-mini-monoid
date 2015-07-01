package minimonoid

/**
 * A semigroup is any set `A` with an associative operation (`op`).
 */
trait Semigroup[A] {
  def op(x: A, y: A): A
}

/**
 * A monoid is a semigroup with an identity. A monoid is a specialization of a
 * semigroup, so its operation must be associative. Additionally,
 * `op(x, id) == op(id, x) == x`. For example, if we have `Monoid[String]`,
 * with `op` as string concatenation, then `id = ""`.
 */
trait Monoid[A] extends Semigroup[A] {
  def id: A
}

object Monoid {
  implicit class MonoidOps[A](m1: A)(implicit m: Monoid[A]) {
    def |+|(m2: A): A = m.op(m1, m2)
  }
}