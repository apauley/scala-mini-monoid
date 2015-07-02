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

  "The List monoid" should {
    "pass some concrete tests" in {
      List.empty[Int] |+| List(1,4) must_== List(1,4)
      List(8,12) |+| List(1,4) must_== List(8,12,1,4)
      List(List("hi", "bye"), List("foo")) |+| List(List("bar")) must_== List(List("hi", "bye"), List("foo"), List("bar"))
      List(None, Some("x")) |+| Nil must_== List(None, Some("x"))
    }
  }

  "The Option monoid" should {
    "pass some concrete integer tests" in {
      Option(1) |+| Option(44) must_== Option(45)
      Option(1) |+| None must_== Option(1)
      Option(Option(1)) |+| Option(Option(7)) must_== Option(Option(8))
    }

    "pass some concrete string tests" in {
      Option("Bleep") |+| Option("Blorp") must_== Option("BleepBlorp")
      Option("Bleep") |+| Option("") must_== Option("Bleep")
      None |+| Option("Blorp") must_== Option("Blorp")
      Option("") |+| None must_== Option("")
    }
  }
}
