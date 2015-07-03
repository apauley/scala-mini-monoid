package minimonoid

object Amount {

  implicit def goatAmountMonoid: Monoid[GoatAmount] = new Monoid[GoatAmount] {
    def id: GoatAmount = GoatAmount(0)
    def op(x: GoatAmount, y: GoatAmount): GoatAmount = GoatAmount(x.amount + y.amount)
  }

  implicit def chickenAmountMonoid: Monoid[ChickenAmount] = new Monoid[ChickenAmount] {
    def id: ChickenAmount = ChickenAmount(0)
    def op(x: ChickenAmount, y: ChickenAmount): ChickenAmount = ChickenAmount(x.amount + y.amount)
  }

  implicit class AmountOps(i: Int) {
    def goats: GoatAmount = GoatAmount(i)
    def goat = goats

    def chickens: ChickenAmount = ChickenAmount(i)
    def chicken = chickens
  }

}

trait Amount {
  val amount: Int
  val currency: StableCurrency
}

case class GoatAmount(amount: Int) extends Amount {
  val currency = Goat
}

case class ChickenAmount(amount: Int) extends Amount {
  val currency = Chicken
}

sealed trait StableCurrency
sealed trait Livestock extends StableCurrency

case object Goat    extends Livestock
case object Chicken extends Livestock
