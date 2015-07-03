package minimonoid

import org.specs2.mutable._

import Semigroup.SemigroupOps
import Amount._

class AmountUnitSpec extends Specification {

  "The Amount monoid" should {
    "pass some concrete tests" in {
      3.goats |+| 1.goat must_== 4.goats
      1.chicken |+| 7.chickens must_== 8.chickens
    }
  }

}
