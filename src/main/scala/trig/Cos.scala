package trig

import func.Function

import java.math.{MathContext, RoundingMode}

class Cos extends Function {
  private val sin = Sin()

  override def calculate(x: BigDecimal, precision: BigDecimal): BigDecimal = {
    validate(x, precision)

    val mc = MathContext(precision.scale, RoundingMode.HALF_EVEN)

    sin.calculate(BigDecimal(Math.PI, mc) / 2 - x, precision)
  }
}
