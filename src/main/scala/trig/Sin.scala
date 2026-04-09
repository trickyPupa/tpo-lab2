package trig

import func.Function
import java.math.MathContext
import scala.math.BigDecimal.RoundingMode

class Sin extends Function {
  override def calculate(_x: BigDecimal, precision: BigDecimal): BigDecimal = {
    validate(_x, precision)

    val mc = new MathContext(precision.scale + 5, java.math.RoundingMode.HALF_EVEN)

    val x: BigDecimal = (_x % (Function.PI * 2)).setScale(precision.scale, RoundingMode.HALF_EVEN)

    var term = x
    var result = x
    var n = 1

    while (term.abs > precision) {
      val divisor = BigDecimal((2 * n) * (2 * n + 1))
      val factor = ((-x * x) / divisor)(mc)
      term = term * factor
      result = result + term
      n += 1
    }

    result.setScale(precision.scale, RoundingMode.HALF_EVEN)
  }
}