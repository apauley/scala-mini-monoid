package minimonoid

object Amount {

  implicit def amountMonoid: Monoid[Amount] = new Monoid[Amount] {
    def empty: Amount = Amount(Map.empty[StableCurrency, Int])
    def combine(x: Amount, y: Amount): Amount = Amount(MapUtil.unionWith(x.values, y.values)(_ + _))
  }

  implicit class AmountOps(i: Int) {
    def goats = Amount(Map(Goat -> i))
    def goat = goats

    def chickens = Amount(Map(Chicken -> i))
    def chicken = chickens
  }

}

case class Amount(values: Map[StableCurrency, Int])

sealed trait StableCurrency
sealed trait Livestock extends StableCurrency

case object Goat    extends Livestock
case object Chicken extends Livestock
