package trig

import func.Function

class Csc extends Function {
  private val sin = Sin()

  override def calculate(x: BigDecimal, precision: BigDecimal): BigDecimal = {
    validate(x, precision)

    val s = sin.calculate(x, precision)

    1 / s
  }

  override def validate(x: BigDecimal, precision: BigDecimal): Unit = {
    super.validate(x, precision)
    val s = sin.calculate(x, precision)
    require(s.abs > Function.EPS, s"Косеканс не имеет значений при x = $x")
  }
}
