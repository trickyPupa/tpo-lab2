package integration.functions

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import trig.*

class BaseTrigToComplexTrigIntegration {
  
  private val precision = 0.001
  private val sin = new Sin()
  private val cos = new Cos()
  private val tan = new Tan()
  private val cot = new Cot()
  private val csc = new Csc()

  @Test
  def testTanViaSinCos(): Unit = {
    val x = BigDecimal(0.7854) // pi/4
    val expected = 1.0

    val actual = tan.calculate(x, BigDecimal(precision))
    assertEquals(expected, actual.doubleValue(), 0.001)
  }

  @Test
  def testCotViaTan(): Unit = {
    val x = BigDecimal(0.7854) // pi/4
    val expected = 1.0

    val actual = cot.calculate(x, BigDecimal(precision))
    assertEquals(expected, actual.doubleValue(), 0.001)
  }

  @Test
  def testCscViaSin(): Unit = {
    val x = BigDecimal(1.5708) // pi/2
    val expected = 1.0

    val actual = csc.calculate(x, BigDecimal(precision))
    assertEquals(expected, actual.doubleValue(), 0.001)
  }

  @Test
  def testTanAtPi4(): Unit = {
    val result = tan.calculate(BigDecimal(0.7854), BigDecimal(precision))
    assertEquals(1.0, result.doubleValue(), 0.001)
  }

  @Test
  def testIdentity1PlusTan2EqualsSec2(): Unit = {
    // 1 + tan^2 = sec^2 = 1/cos^2
    val x = BigDecimal(0.5)
    val tanx = tan.calculate(x, BigDecimal(precision))
    val cosx = cos.calculate(x, BigDecimal(precision))
    val sec2 = BigDecimal(1) / (cosx * cosx)

    val left = BigDecimal(1) + tanx * tanx
    assertEquals(sec2.doubleValue(), left.doubleValue(), 0.01)
  }
}
