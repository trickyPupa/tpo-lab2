package eq

import func.Function
import log.{Ln, LogN}
import trig.*

import java.math.{MathContext, RoundingMode}

class Equation extends Function {

  private val sin: Sin = Sin()
  private val cos: Cos = Cos()
  private val tan: Tan = Tan()
  private val csc: Csc = Csc()
  private val cot: Cot = Cot()

  private val log2: LogN = LogN(2)
  private val log3: LogN = LogN(3)
  private val log5: LogN = LogN(5)
  private val log10: LogN = LogN(10)
  private val ln: Ln = Ln()

  override def calculate(x: BigDecimal, p: BigDecimal): BigDecimal = {
    // x <= 0 : (((((sin(x) + cos(x)) * csc(x)) - cos(x)) / (cos(x) ^ 3)) * (((sin(x) / sin(x)) ^ 3) * ((sin(x) / tan(x)) / cot(x))))
    if (x.compareTo(Function.ZERO) <= 0) {
      val sinx = c(sin, x, p)
      val cosx = c(cos, x, p)
      val cscx = c(csc, x, p)
      val tanx = c(tan, x, p)
      val cotx = c(cot, x, p)
      
      ((((sinx + cosx) * cscx) - cosx) / cosx.pow(3)) *
        ((sinx / sinx).pow(3) * ((sinx / tanx) / cotx))
          .setScale(p.scale, BigDecimal.RoundingMode.HALF_EVEN)
    }
    // x > 0 : (((((ln(x) / log_5(x)) + ln(x)) / log_2(x)) - ((ln(x) - ln(x)) - log_3(x))) * ((log_10(x) + (log_2(x) + log_2(x))) * ((log_10(x) / log_2(x)) / log_10(x))))
    else {
      val lnx = c(ln, x, p)
      val log2x = c(log2, x, p)
      val log3x = c(log3, x, p)
      val log5x = c(log5, x, p)
      val log10x = c(log10, x, p)
      ((((lnx / log5x) + lnx) / log2x) - ((lnx - lnx) - log3x)) *
        ((log10x + (log2x + log2x)) * ((log10x / log2x) / log10x))
          .setScale(p.scale, BigDecimal.RoundingMode.HALF_EVEN)
    }
  }

  private def c(f: Function, x: BigDecimal, precision: BigDecimal): BigDecimal = {
    f.calculate(x, precision)
  }
}
