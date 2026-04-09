package trig

import func.Function

class Cos extends Function {
  private val sin = new Sin()

  override def calculate(x: BigDecimal, precision: BigDecimal): BigDecimal = {
    validate(x, precision)

    val pi_2 = Function.PI / 2
    val arg = pi_2 - x
    sin.calculate(arg, precision)
  }
}