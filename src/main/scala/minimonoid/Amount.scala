package minimonoid

object Amount {

  implicit def amountMonoid: Monoid[Amount] = new Monoid[Amount] {
    def id: Amount = Amount(0, Goat)
    def op(x: Amount, y: Amount): Amount = Amount(x.amount + y.amount, id.currency)
  }


  implicit class GoatOps(i: Int) {
    def goats: Amount = Amount(i, Goat)
    def goat = goats
  }

}

case class Amount(amount: Int, currency: StableCurrency)

sealed trait StableCurrency
sealed trait Livestock extends StableCurrency
case object Goat extends Livestock
