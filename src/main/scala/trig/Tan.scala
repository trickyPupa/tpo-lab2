package trig

import func.Function
import java.math.MathContext

class Tan extends Function {
  private val sin = Sin()
  private val cos = Cos()

  override def calculate(x: BigDecimal, precision: BigDecimal): BigDecimal = {
    validate(x, precision)

    val s = sin.calculate(x, precision / 1000)
    val c = cos.calculate(x, precision / 1000)
    val mc = MathContext(precision.scale + 15)

    (s / c)(mc).setScale(precision.scale, BigDecimal.RoundingMode.HALF_EVEN)
  }

  override def validate(x: BigDecimal, precision: BigDecimal): Unit = {
    super.validate(x, precision)
    val c = cos.calculate(x, precision)
    require(c.abs > Function.EPS, s"Тангенс не имеет значений при x = $x")
  }
}
