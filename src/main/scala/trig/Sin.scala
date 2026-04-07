package trig

import func.Function

import java.math.{MathContext, RoundingMode}

class Sin extends Function {
  override def calculate(_x: BigDecimal, precision: BigDecimal): BigDecimal = {
    validate(_x, precision)

    val mc = MathContext(precision.scale, RoundingMode.HALF_EVEN)

    val x: BigDecimal = (_x % (Function.PI * 2)).setScale(precision.scale)

    var term = x
    var result = x
    var n = BigDecimal(1)

    while (term.abs > precision) {
      term *= -1 * x * x / ((2 * n) * (2 * n + 1))
      result = result + term
      n += 1
    }

    result
  }
}
