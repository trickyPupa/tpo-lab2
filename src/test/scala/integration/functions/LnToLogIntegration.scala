package integration.functions

import log.{Ln, LogN}
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test

class LnToLogIntegration {
  private val precision = 0.001

  @Test
  def testLog2ViaLn(): Unit = {
    val ln = new Ln()
    val log2 = new LogN(2)

    // log2(8) = 3
    val result = log2.calculate(BigDecimal(8), BigDecimal(precision))
    assertEquals(3.0, result.doubleValue(), precision)
  }

  @Test
  def testLog3ViaLn(): Unit = {
    val log3 = new LogN(3)

    // log3(27) = 3
    val result = log3.calculate(BigDecimal(27), BigDecimal(precision))
    assertEquals(3.0, result.doubleValue(), precision)
  }

  @Test
  def testLog5ViaLn(): Unit = {
    val log5 = new LogN(5)

    // log5(125) = 3
    val result = log5.calculate(BigDecimal(125), BigDecimal(precision))
    assertEquals(3.0, result.doubleValue(), precision)
  }

  @Test
  def testLog10ViaLn(): Unit = {
    val log10 = new LogN(10)

    // log10(100) = 2
    val result = log10.calculate(BigDecimal(100), BigDecimal(precision))
    assertEquals(2.0, result.doubleValue(), precision)
  }

  @Test
  def testChangeOfBase(): Unit = {
    val ln = new Ln()
    val log2 = new LogN(2)

    val x = BigDecimal(16)
    val log2x = log2.calculate(x, BigDecimal(precision))
    val lnx = ln.calculate(x, BigDecimal(precision))
    val ln2 = ln.calculate(BigDecimal(2), BigDecimal(precision))

    assertEquals(log2x.doubleValue(), (lnx / ln2).doubleValue(), precision)
  }
}
