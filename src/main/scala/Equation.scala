import function.*

import java.math.{MathContext, RoundingMode}

class Equation(private val maxIter: Int = Function.MAX_ITERATIONS) extends Function(maxIter) {

  private val sin: Sin = Sin(maxIter)
  private val cos: Cos = Cos(maxIter)
  private val tan: Tan = Tan(maxIter)
  private val csc: Csc = Csc(maxIter)
  private val cot: Cot = Cot(maxIter)

  private val log2: LogN = LogN(2, maxIter)
  private val log3: LogN = LogN(3, maxIter)
  private val log5: LogN = LogN(5, maxIter)
  private val log10: LogN = LogN(10, maxIter)
  private val ln: Ln = Ln(maxIter)

  override def calculate(x: BigDecimal, p: BigDecimal): BigDecimal = {
    val mc = MathContext(MathContext.DECIMAL128.getPrecision, RoundingMode.HALF_EVEN)

    // x <= 0 : (((((sin(x) + cos(x)) * csc(x)) - cos(x)) / (cos(x) ^ 3)) * (((sin(x) / sin(x)) ^ 3) * ((sin(x) / tan(x)) / cot(x))))
    if (x.compareTo(Function.ZERO) <= 0) {
      c(csc, x, p)
        .*(c(sin, x, p).+(c(cos, x, p)))
        .-(c(cos, x, p))
        ./(c(cos, x, p).pow(3))
        .*(
          c(sin, x, p)
            ./(c(sin, x, p).pow(3))
            .*(c(sin, x, p)./(c(tan, x, p))./(c(cot, x, p)))
        )
    }
    // x > 0 : (((((ln(x) / log_5(x)) + ln(x)) / log_2(x)) - ((ln(x) - ln(x)) - log_3(x))) * ((log_10(x) + (log_2(x) + log_2(x))) * ((log_10(x) / log_2(x)) / log_10(x))))
    else {
      c(ln, x, p)
        ./(c(log5, x, p))
        .+(c(ln, x, p))
        ./(c(log2, x, p))
        .-(
          c(ln, x, p)
            .-(c(ln, x, p))
            .-(c(log3, x, p))
        )
        .*(
          c(log10, x, p)
            .+(c(log2, x, p).+(c(log2, x, p)))
            .*(c(log10, x, p)./(c(log2, x, p))./(c(log10, x, p)))
        )
    }
  }

  private def c(f: Function, x: BigDecimal, precision: BigDecimal): BigDecimal = {
    f.calculate(x, precision)
  }
}
