package minimonoid

import org.specs2.mutable._

import Semigroup.SemigroupOps
import Amount.GoatOps

class AmountUnitSpec extends Specification {

  "The Goats monoid" should {
    "pass some concrete tests" in {
      3.goats |+| 1.goat must_== 4.goats
    }
  }

}
