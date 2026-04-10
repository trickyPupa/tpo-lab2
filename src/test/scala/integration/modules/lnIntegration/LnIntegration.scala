package integration.modules.lnIntegration

import eq.Equation
import log.Ln
import org.junit.jupiter.api.Assertions.{assertEquals, assertThrows}
import org.junit.jupiter.api.Test
import stub.*

class LnIntegration {
  private val precision = 0.0001

  private val sin = new SinStub()
  private val cos = new CosStub()
  private val tan = new TanStub()
  private val cot = new CotStub()
  private val csc = new CscStub()

  private val ln = new Ln()

  private val log2 = new Log2Stub()
  private val log3 = new Log3Stub()
  private val log5 = new Log5Stub()
  private val log10 = new Log10Stub()

  private val equation = new Equation(sin, cos, tan, csc, cot, ln, log2, log3, log5, log10)

  @Test
  def testLnBasicValues(): Unit = {
    val testCases = Map(
      0.5 -> -0.6931,
      1.0 -> 0.0,
      2.0 -> 0.6931,
      Math.E -> 1.0
    )

    testCases.foreach { case (x, expected) =>
      val actual = ln.calculate(BigDecimal(x), BigDecimal(precision))
      assertEquals(expected, actual.doubleValue(), 0.001, s"Ln($x) failed")
    }
  }

  @Test
  def testLnDomainException(): Unit = {
    assertThrows(classOf[IllegalArgumentException], () => {
      ln.calculate(BigDecimal(0), BigDecimal(precision))
    })

    assertThrows(classOf[IllegalArgumentException], () => {
      ln.calculate(BigDecimal(-1), BigDecimal(precision))
    })
  }

  @Test
  def testEquationRightBranchWithRealLn(): Unit = {
    val x = BigDecimal(2.0)
    val result = equation.calculate(x, BigDecimal(precision))
    assertEquals(6.3431, result.doubleValue(), 0.1)
  }
}
