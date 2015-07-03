package minimonoid

import org.specs2.mutable._

import Semigroup.SemigroupOps
import Amount._

class AmountUnitSpec extends Specification {

  "The Amount monoid" should {
    "pass some concrete tests" in {
      3.goats |+| 1.goat must_== 4.goats
      1.chicken |+| 7.chickens must_== 8.chickens

      // It doesn't make sense to add values of different currencies.
      // The line below must not compile.
      // 1.chicken |+| 2.goats must_== 'donotcompile
    }
  }

}
