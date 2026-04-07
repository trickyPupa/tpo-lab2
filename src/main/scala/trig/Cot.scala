package trig

import func.Function

class Cot extends Function {
  private val sin = Sin()
  private val cos = Cos()
  
  override def calculate(x: BigDecimal, precision: BigDecimal): BigDecimal = {
    validate(x, precision)
    
    val s = sin.calculate(x, precision)
    val c = cos.calculate(x, precision)

    c / s
  }

  override def validate(x: BigDecimal, precision: BigDecimal): Unit = {
    super.validate(x, precision)
    val t: BigDecimal = x % (Function.PI * 2).abs
    require(t < Function.PI && t != 0 , s"Котангенс не имеет значений при x = $x")
  }
}
