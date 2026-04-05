package function

class Sin(private val maxIter: Int = Function.MAX_ITERATIONS) extends Function(maxIter) {
  override def calculate(x: BigDecimal, precision: BigDecimal): BigDecimal = {
    validate(x, precision)
    
    BigDecimal.decimal(1)
  }
}
