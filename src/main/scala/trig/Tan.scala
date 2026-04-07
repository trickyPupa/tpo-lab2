package trig

import func.Function

class Tan extends Function {
  private val sin = Sin()
  private val cos = Cos()

  override def calculate(x: BigDecimal, precision: BigDecimal): BigDecimal = {
    validate(x, precision)

    val s = sin.calculate(x, precision)
    val c = cos.calculate(x, precision)

    s / c
  }

  override def validate(x: BigDecimal, precision: BigDecimal): Unit = {
    super.validate(x, precision)
    require((x % (Function.PI * 2)).abs < Function.PI / 2, s"Тангенс не имеет значений при x = $x")
  }
}
