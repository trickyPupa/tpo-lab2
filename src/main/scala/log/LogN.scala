package log

import func.Function

class LogN(val base: Int) extends Function {
  require(base > 0 && base != 1, s"Основание логарифма должно быть > 0 и != 1, получено $base")

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
