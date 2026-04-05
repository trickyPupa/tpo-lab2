package function

import function.Function.ZERO

trait Function(private val maxIter: Int) {
  def calculate(x: BigDecimal, precision: BigDecimal): BigDecimal

  protected def validate(x: BigDecimal, precision: BigDecimal): Unit = {
    require(x != null)
    require(precision != null)
    require(precision.compareTo(ZERO) >= 0 && precision.compareTo(ZERO) < 0)
  }
}

object Function {
  val MAX_ITERATIONS = 100_000
  val EPS: BigDecimal = BigDecimal("1e-10")
  val ZERO: BigDecimal = BigDecimal(0)
}