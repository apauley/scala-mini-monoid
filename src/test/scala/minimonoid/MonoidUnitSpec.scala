package minimonoid

import org.specs2.ScalaCheck
import org.specs2.mutable._

import MonoidInstances._
import Semigroup.SemigroupOps

class MonoidUnitSpec extends Specification with ScalaCheck {

  "The Int monoid" should {
    "pass some concrete tests" in {
      1 |+| 44 must_== 45
    }

    "satisfy semigroup associativity" in prop { (values: (Int, Int, Int)) =>
      val (x,y,z) = values
      (x |+| y) |+| z must_== x |+| (y |+| z)
    }

    "satisfy monoid identity laws" in prop { (x: Int) =>
      x |+| intMonoid.empty must_== x
      intMonoid.empty |+| x must_== x
    }

  }

  "The Float monoid" should {
    "pass some concrete tests" in {
      1.2f |+| 4.3f must_== 5.5f
    }

    "satisfy semigroup associativity" in {
      val (x,y,z) = (0.4f, 4.6f, 12.0f)
      (x |+| y) |+| z must_== x |+| (y |+| z)
    }

    "satisfy monoid identity laws" in prop { (x: Float) =>
      x |+| floatMonoid.empty must_== x
      floatMonoid.empty |+| x must_== x
    }

  }

  "The Double monoid" should {
    "pass some concrete tests" in {
      1.2d |+| 4.3d must_== 5.5d
    }

    "satisfy semigroup associativity" in {
      val (x,y,z) = (0.4d, 4.6d, 12.0d)
      (x |+| y) |+| z must_== x |+| (y |+| z)
    }

    "satisfy monoid identity laws" in prop { (x: Double) =>
      x |+| doubleMonoid.empty must_== x
      doubleMonoid.empty |+| x must_== x
    }

  }

  "The String monoid" should {
    "pass some concrete tests" in {
      "Hello, " |+| "world!" must_== "Hello, world!"
      "Foo" |+| "" must_== "Foo"
      "" |+| "Bar" must_== "Bar"
    }

    "satisfy semigroup associativity" in prop { (values: (String, String, String)) =>
      val (x,y,z) = values
      (x |+| y) |+| z must_== x |+| (y |+| z)
    }

    "satisfy monoid identity laws" in prop { (x: String) =>
      x |+| stringMonoid.empty must_== x
      stringMonoid.empty |+| x must_== x
    }

  }

  "The List monoid" should {
    "pass some concrete tests" in {
      List.empty[Int] |+| List(1,4) must_== List(1,4)
      List(8,12) |+| List(1,4) must_== List(8,12,1,4)
      List(List("hi", "bye"), List("foo")) |+| List(List("bar")) must_== List(List("hi", "bye"), List("foo"), List("bar"))
      List(None, Some("x")) |+| Nil must_== List(None, Some("x"))
    }

    "satisfy semigroup associativity" in prop { (values: (List[Int], List[Int], List[Int])) =>
      val (x,y,z) = values
      (x |+| y) |+| z must_== x |+| (y |+| z)
    }

    "satisfy monoid identity laws" in prop { (x: List[Int]) =>
      x |+| listMonoid[Int].empty must_== x
      listMonoid[Int].empty |+| x must_== x
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
      val nothing: Option[String] = None
      nothing |+| Option("Blorp") must_== Option("Blorp")
      Option("") |+| None must_== Option("")
    }

    "satisfy semigroup associativity" in prop { (values: (Option[Int], Option[Int], Option[Int])) =>
      val (x,y,z) = values
      (x |+| y) |+| z must_== x |+| (y |+| z)
    }

    "satisfy monoid identity laws" in prop { (x: Option[Int]) =>
      x |+| optionMonoid[Int].empty must_== x
      optionMonoid[Int].empty |+| x must_== x
    }

  }

  "The Map monoid" should {
    "pass some concrete tests" in {
      Map.empty[Int, String] |+| Map(1 -> "foo", 4 -> "bar") must_== Map(1 -> "foo", 4 -> "bar")
      Map(1 -> "foo", 2 -> "baz") |+| Map(1 -> "bar", 3 -> "hi") must_== Map(1 -> "foobar", 2 -> "baz", 3 -> "hi")
    }

    "satisfy semigroup associativity" in prop { (values: (Map[Char, Int], Map[Char, Int], Map[Char, Int])) =>
      val (x,y,z) = values
      (x |+| y) |+| z must_== x |+| (y |+| z)
    }

    "satisfy monoid identity laws" in prop { (x: Map[Char, Int]) =>
      x |+| mapMonoid[Char, Int].empty must_== x
      mapMonoid[Char, Int].empty |+| x must_== x
    }

  }

  "The Tuple2 monoid" should {
    "pass some concrete tests" in {
      val t1 = (1,2)
      val t2 = (0,6)
      t1 |+| t2 must_== ((1,8))
    }

    "satisfy semigroup associativity" in prop { (values: ((String, Int), (String, Int), (String, Int))) =>
      val (x,y,z) = values
      (x |+| y) |+| z must_== x |+| (y |+| z)
    }

    "satisfy monoid identity laws" in prop { (x: (String, Option[Int])) =>
      x |+| tuple2Monoid[String, Option[Int]].empty must_== x
      tuple2Monoid[String, Option[Int]].empty |+| x must_== x
    }

  }

  "reduceM" should {
    import MonoidOps._

    "easily reduce lists containing integers" in {
      List(1,4,6).reduceM must_== 11
    }

    "easily reduce lists containing strings" in {
      List("foo", "bar", "baz").reduceM must_== "foobarbaz"
    }

    "easily reduce lists containing optional strings" in {
      List(Option("foo"), None, Option("baz")).reduceM must_== Some("foobaz")
    }

    "easily reduce nested lists" in {
      List(List(1,4), List(2,3)).reduceM must_== List(1,4,2,3)
      List(List(1,4), List(2,3)).reduceM.reduceM must_== 1+4+2+3
      List(List("foo", "bar", "baz"), List("Hello", "world")).reduceM.reduceM must_== "foobarbazHelloworld"
    }

  }
}
