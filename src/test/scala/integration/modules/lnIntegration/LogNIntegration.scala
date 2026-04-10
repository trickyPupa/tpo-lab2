package integration.modules.lnIntegration

import eq.Equation
import log._
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import stub._

class LogNIntegration {
  private val precision = 0.001

  private val sin = new SinStub()
  private val cos = new CosStub()
  private val tan = new TanStub()
  private val cot = new CotStub()
  private val csc = new CscStub()

  private val ln = new Ln()
  private val log2 = new LogN(2)
  private val log3 = new LogN(3)
  private val log5 = new LogN(5)
  private val log10 = new LogN(10)

  private val equation = new Equation(sin, cos, tan, csc, cot, ln, log2, log3, log5, log10)

  @Test
  def testLog2ViaLn(): Unit = {
    val x = BigDecimal(8)
    val expected = 3.0

    val actual = log2.calculate(x, BigDecimal(precision))
    assertEquals(expected, actual.doubleValue(), 0.01)
  }

  @Test
  def testLog3ViaLn(): Unit = {
    val x = BigDecimal(27)
    val expected = 3.0

    val actual = log3.calculate(x, BigDecimal(precision))
    assertEquals(expected, actual.doubleValue(), 0.01)
  }

  @Test
  def testLog5ViaLn(): Unit = {
    val x = BigDecimal(125)
    val expected = 3.0

    val actual = log5.calculate(x, BigDecimal(precision))
    assertEquals(expected, actual.doubleValue(), 0.01)
  }

  @Test
  def testLog10ViaLn(): Unit = {
    val x = BigDecimal(100)
    val expected = 2.0

    val actual = log10.calculate(x, BigDecimal(precision))
    assertEquals(expected, actual.doubleValue(), 0.01)
  }

  @Test
  def testChangeOfBaseIdentity(): Unit = {
    val x = BigDecimal(16)
    val log2x = log2.calculate(x, BigDecimal(precision))
    val lnx = ln.calculate(x, BigDecimal(precision))
    val ln2 = ln.calculate(BigDecimal(2), BigDecimal(precision))

    assertEquals(log2x.doubleValue(), (lnx / ln2).doubleValue(), 0.01)
  }

  @Test
  def testLogProductIdentity(): Unit = {
    val a = BigDecimal(2)
    val b = BigDecimal(3)

    val log10a = log10.calculate(a, BigDecimal(precision))
    val log10b = log10.calculate(b, BigDecimal(precision))
    val log10ab = log10.calculate(a * b, BigDecimal(precision))

    assertEquals((log10a + log10b).doubleValue(), log10ab.doubleValue(), 0.01)
  }

  @Test
  def testEquationRightBranchWithRealLogs(): Unit = {
    val testPoints = List(0.5, 1.5, 2.0, 3.0, 4.0, 5.0)

    testPoints.foreach { x =>
      val result = equation.calculate(BigDecimal(x), BigDecimal(precision))
      assert(!result.doubleValue().isNaN, s"Result is NaN for x=$x")
    }
  }
}
