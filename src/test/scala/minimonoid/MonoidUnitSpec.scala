package minimonoid

import org.specs2.mutable._

import Semigroup.SemigroupOps
import ExampleMonoids._

class MonoidUnitSpec extends Specification {

  "The Int monoid" should {
    "pass some concrete tests" in {
      1 |+| 44 must_== 45
    }
  }

}
