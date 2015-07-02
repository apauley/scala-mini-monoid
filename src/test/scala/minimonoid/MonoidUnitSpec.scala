package minimonoid

import org.specs2.mutable._

import MonoidInstances._
import Semigroup.SemigroupOps

class MonoidUnitSpec extends Specification {

  "The Int monoid" should {
    "pass some concrete tests" in {
      1 |+| 44 must_== 45
    }
  }

  "The Option monoid" should {
    "pass some concrete tests" in {
      Option(1) |+| Option(44) must_== Option(45)
      Option(1) |+| None must_== Option(1)
    }
  }
}
