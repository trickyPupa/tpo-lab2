import trig._
import log._
import util.CsvWriter

@main
def main(): Unit = {
  val precision = BigDecimal.decimal(0.0001)
  val defX: List[BigDecimal] = List(
    BigDecimal(-5),
    BigDecimal(5),
    BigDecimal(0.5)
  )

  val funcs: List[(func.Function, String)] = List(
    (Sin(), "sin"),
    (Cos(), "cos"),
    (Tan(), "tan"),
    (Csc(), "csc"),
    (Cot(), "cot"),
    (Ln(), "ln"),
    (LogN(10), "logn")
  )

  defX match {
    case start :: end :: step :: Nil =>
      funcs.foreach { case (f, filename) =>
        CsvWriter.write(f, start, end, step, precision, s"src/test/resources/$filename")
      }
    case _ => println("Invalid defX format")
  }
}