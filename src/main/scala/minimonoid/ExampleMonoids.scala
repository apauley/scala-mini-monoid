package minimonoid

object ExampleMonoids {

  implicit val intMonoid = new Monoid[Int] {
    override def id: Int = 0

    override def op(x: Int, y: Int): Int = x+y
  }

}
