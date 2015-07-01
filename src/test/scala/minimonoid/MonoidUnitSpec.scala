package minimonoid

import org.specs2.mutable._

import Monoid.MonoidOps
import ExampleMonoids._

class MonoidUnitSpec extends Specification {

  "The Int monoid" should {
    "pass some concrete tests" in {
      1 |+| 44 must_== 45
    }
  }

  "The Goats monoid" should {
    "pass some concrete tests" in {
      Goats(3) |+| Goats(1) must_== Goats(4)
    }
  }

}
