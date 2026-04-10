package log

import func.Function

import java.math.MathContext

class Ln extends Function {
  override def calculate(_x: BigDecimal, precision: BigDecimal): BigDecimal = {
    validate(_x, precision)

    var x = _x

    if (x == 1) return Function.ZERO

    val e = BigDecimal(Math.E, MathContext(precision.scale + 2))
    var k = Function.ZERO
    while (x > 2) {
      x /= e
      k += 1
    }
    while (x < 0.5) {
      x *= e
      k -= 1
    }

    val y: BigDecimal = (x - 1) / (x + 1)
    var result = Function.ZERO
    var term = y
    var n: Int = 1

    while (term.abs > (precision / 100)) {
      result += term / n
      term *= y * y
      n += 2
    }

    2 * result + k
  }

  override def validate(x: BigDecimal, precision: BigDecimal): Unit = {
    super.validate(x, precision)
    require(x > 0, s"Натуральный логарифм не имеет значений при x = $x")
  }
}
