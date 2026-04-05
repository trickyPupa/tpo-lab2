package function

class Cos(private val maxIter: Int = Function.MAX_ITERATIONS) extends Function(maxIter) {
  override def calculate(x: BigDecimal, precision: BigDecimal): BigDecimal = {
    validate(x, precision)
    
    BigDecimal.decimal(1)
  }
}
