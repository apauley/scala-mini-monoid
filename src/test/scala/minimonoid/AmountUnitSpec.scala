package minimonoid


import org.scalacheck._
import Arbitrary._
import org.specs2.ScalaCheck
import org.specs2.mutable._

import Semigroup.SemigroupOps
import Amount._

class AmountUnitSpec extends Specification with ScalaCheck {
  implicit val arbAmount: Arbitrary[Amount] = Arbitrary(Gen.choose(Int.MinValue, Int.MaxValue).map(i => i.goats |+| (i*2).chickens))

  "The Amount monoid" should {
    "pass some concrete tests" in {
      3.goats   |+| 1.goat must_== 4.goats
      1.chicken |+| 7.chickens must_== 8.chickens


      5.goats |+| 9.chickens |+| 2.goats must_== Amount(Map(Chicken -> 9, Goat -> 7))
    }

    "satisfy semigroup associativity" in prop { (values: (Amount, Amount, Amount)) =>
      val (x,y,z) = values
      (x |+| y) |+| z must_== x |+| (y |+| z)
    }

  }

}
