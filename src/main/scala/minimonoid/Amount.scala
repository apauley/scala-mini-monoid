package minimonoid

object Amount {

  implicit def amountMonoid: Monoid[Amount] = new Monoid[Amount] {
    def id: Amount = Amount(0, Goat)
    def op(x: Amount, y: Amount): Amount = Amount(x.amount + y.amount, id.currency)
  }


  implicit class AmountOps(i: Int) {
    def goats: Amount = Amount(i, Goat)
    def goat = goats

    def chickens: Amount = Amount(i, Chicken)
    def chicken = chickens
  }

}

case class Amount(amount: Int, currency: StableCurrency)

sealed trait StableCurrency
sealed trait Livestock extends StableCurrency

case object Goat    extends Livestock
case object Chicken extends Livestock
