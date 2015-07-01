package minimonoid

import org.specs2.mutable._

import Monoid.MonoidOps

class MonoidUnitSpec extends Specification {

  "The Goats monoid" should {
    "pass some concrete tests" in {
      Goats(3) |+| Goats(1) must_== Goats(4)
    }
  }
}
