package log

import func.Function

class LogN(val base: Int) extends Function {
  private val ln = Ln()
  
  override def calculate(x: BigDecimal, precision: BigDecimal): BigDecimal = {
    validate(x, precision)
    
    ln.calculate(x, precision) / ln.calculate(BigDecimal(base), precision)
  }

  override def validate(x: BigDecimal, precision: BigDecimal): Unit = {
    super.validate(x, precision)
    require(x > 0, s"Логарифм с основанием $base не имеет значений при x = $x")
  }
}
