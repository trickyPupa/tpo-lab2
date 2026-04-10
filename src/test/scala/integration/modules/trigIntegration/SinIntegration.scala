package integration.modules.trigIntegration

import eq.Equation
import func.*
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import stub.*
import trig.Sin

class SinIntegration {
  private val precision = 0.001

  private val sin = new Sin()

  private val cos = new CosStub()
  private val tan = new TanStub()
  private val cot = new CotStub()
  private val csc = new CscStub()
  private val ln = new LnStub()
  private val log2 = new Log2Stub()
  private val log3 = new Log3Stub()
  private val log5 = new Log5Stub()
  private val log10 = new Log10Stub()

  private val equation = new Equation(sin, cos, tan, csc, cot, ln, log2, log3, log5, log10)

  @Test
  def testSinIntegrationWithStubs(): Unit = {
    val testPoints = List(-3.1416, -1.5708, 0.0, 1.5708, 3.1416)

    testPoints.foreach { x =>
      val result = sin.calculate(BigDecimal(x), BigDecimal(precision))
      println(s"Sin($x) = ${result.doubleValue()}")

      assert(!result.doubleValue().isNaN)
    }
  }

  @Test
  def testEquationLeftBranchWithRealSin(): Unit = {
    val x = BigDecimal(-1.0)
    val result = equation.calculate(x, BigDecimal(precision))

    val expected = 0.9736
    assertEquals(expected, result.doubleValue(), 0.01)
  }
}
