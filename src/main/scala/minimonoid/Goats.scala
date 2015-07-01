package minimonoid

object Goats {

  implicit object GoatsMonoid extends Monoid[Goats] {
    def id: Goats = Goats(0)

    def op(x: Goats, y: Goats): Goats = Goats(x.numberOfGoats + y.numberOfGoats)
  }

}

case class Goats(numberOfGoats: Int)
