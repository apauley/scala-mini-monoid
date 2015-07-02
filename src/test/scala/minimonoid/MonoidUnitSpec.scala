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

  "The String monoid" should {
    "pass some concrete tests" in {
      "Hello, " |+| "world!" must_== "Hello, world!"
      "Foo" |+| "" must_== "Foo"
      "" |+| "Bar" must_== "Bar"
    }
  }

  "The Option monoid" should {
    "pass some concrete integer tests" in {
      Option(1) |+| Option(44) must_== Option(45)
      Option(1) |+| None must_== Option(1)
    }

    "pass some concrete string tests" in {
      Option("Bleep") |+| Option("Blorp") must_== Option("BleepBlorp")
      Option("Bleep") |+| Option("") must_== Option("Bleep")
      None |+| Option("Blorp") must_== Option("Blorp")
      Option("") |+| None must_== Option("")
    }
  }
}
