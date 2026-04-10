package stub

import func.Function

import scala.io.Source
import scala.collection.mutable
import scala.math.BigDecimal.RoundingMode

abstract class CsvStub(fileName: String) extends Function {
  protected val stubData: Map[BigDecimal, BigDecimal] = {
    val data = mutable.Map[BigDecimal, BigDecimal]()
    val source = Source.fromFile(s"src/test/resources/$fileName.csv")
    try {
      val lines = source.getLines().drop(1)
      lines.foreach { line =>
        val parts = line.split(",")
        if (parts.length == 2) {
          val x = BigDecimal(parts(0).trim)
          val y = parts(1).trim match {
            case "NaN" | "undefined" => null
            case s => BigDecimal(s)
          }
          if (y != null) data.put(x, y)
        }
      }
    } finally {
      source.close()
    }
    data.toMap
  }

  override def calculate(x: BigDecimal, precision: BigDecimal): BigDecimal = {
    val rounded = x.setScale(4, RoundingMode.HALF_EVEN)
    stubData.getOrElse(rounded, {
      throw new IllegalArgumentException(s"No stub data for x = $x")
    })
  }
}

class SinStub extends CsvStub("sin")
class CosStub extends CsvStub("cos")
class TanStub extends CsvStub("tan")
class CotStub extends CsvStub("cot")
class CscStub extends CsvStub("csc")

class LnStub extends CsvStub("ln")
class Log10Stub extends CsvStub("log10")

class LogNStub(base: Int) extends Function {
  private val lnStub = new LnStub()

  override def calculate(x: BigDecimal, precision: BigDecimal): BigDecimal = {
    if (x <= 0) throw new IllegalArgumentException(s"log undefined for x = $x")
    val lnx = lnStub.calculate(x, precision)
    val lnBase = lnStub.calculate(BigDecimal(base), precision)
    lnx / lnBase
  }
}

class Log2Stub extends LogNStub(2)
class Log3Stub extends LogNStub(3)
class Log5Stub extends LogNStub(5)
