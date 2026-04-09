package func

import java.math.MathContext

trait Function {
  def calculate(x: BigDecimal, precision: BigDecimal): BigDecimal

  protected def validate(x: BigDecimal, precision: BigDecimal): Unit = {
    require(x != null)
    require(precision != null)
    require(precision > Function.ZERO && precision < Function.ONE)
  }
}

object Function {
  val EPS: BigDecimal = BigDecimal("1e-10")
  val ZERO: BigDecimal = BigDecimal(0)
  val ONE: BigDecimal = BigDecimal(1)
  val PI: BigDecimal = BigDecimal(Math.PI, MathContext(MathContext.DECIMAL128.getPrecision))
}