package trig

import func.Function

import java.math.MathContext
import scala.math.BigDecimal.RoundingMode


class Cos extends Function {
  private val sin = new Sin()

  override def calculate(x: BigDecimal, precision: BigDecimal): BigDecimal = {
    validate(x, precision)

    val mc = MathContext(precision.scale + 10, java.math.RoundingMode.HALF_EVEN)
    val pi_2 = Function.PI / 2

    val arg = (pi_2 - x).setScale(precision.scale + 5, RoundingMode.HALF_EVEN)
    sin.calculate(arg, precision / 10)
  }
}