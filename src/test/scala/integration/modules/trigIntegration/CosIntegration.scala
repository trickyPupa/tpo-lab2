package integration.modules.trigIntegration

import eq.Equation
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import stub.*
import trig.{Cos, Sin}

class CosIntegration {
  private val precision = 0.001

  private val sin = new Sin()
  private val cos = new Cos()

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
  def testSinAndCosIdentity(): Unit = {
    val testPoints = List(0.0, 0.5236, 0.7854, 1.0472, 1.5708)

    testPoints.foreach { x =>
      val sinx = sin.calculate(BigDecimal(x), BigDecimal(precision))
      val cosx = cos.calculate(BigDecimal(x), BigDecimal(precision))
      val sum = sinx * sinx + cosx * cosx

      assertEquals(1.0, sum.doubleValue(), 0.01, s"sin^2+cos^2 != 1 for x=$x")
    }
  }

  @Test
  def testCosBasicValues(): Unit = {
    val testCases = Map(
      0.0 -> 1.0,
      1.0472 -> 0.5,
      1.5708 -> 0.0,
      3.1416 -> -1.0
    )

    testCases.foreach { case (x, expected) =>
      val actual = cos.calculate(BigDecimal(x), BigDecimal(precision))
      assertEquals(expected, actual.doubleValue(), 0.001, s"Cos($x) failed")
    }
  }

  @Test
  def testEquationLeftBranchWithRealSinCos(): Unit = {
    val testPoints = List(-0.5, -1.0, -2.0, -3.0)

    testPoints.foreach { x =>
      val result = equation.calculate(BigDecimal(x), BigDecimal(precision))
      assert(!result.doubleValue().isNaN, s"Result is NaN for x=$x")
    }
  }
}
