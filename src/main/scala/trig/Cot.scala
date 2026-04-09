package trig

import func.Function
import java.math.MathContext

class Cot extends Function {
  private val sin = Sin()
  private val cos = Cos()

  override def calculate(x: BigDecimal, precision: BigDecimal): BigDecimal = {
    validate(x, precision)

    val s = sin.calculate(x, precision / 100)
    val c = cos.calculate(x, precision / 100)
    val mc = MathContext(precision.scale + 10)

    (c / s)(mc).setScale(precision.scale, BigDecimal.RoundingMode.HALF_EVEN)
  }

  override def validate(x: BigDecimal, precision: BigDecimal): Unit = {
    super.validate(x, precision)
    val s = sin.calculate(x, precision)
    require(s.abs > Function.EPS, s"Котангенс не имеет значений при x = $x")
  }
}
