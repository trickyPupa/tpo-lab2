package logTests

import org.junit.jupiter.api.Assertions.{assertEquals, assertThrows}
import org.junit.jupiter.api.Test
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.CsvFileSource
import log.Ln

class LnTest {
  private val ln = Ln()
  private val precision = 0.0001

  @ParameterizedTest
  @CsvFileSource(resources = Array("/ln.csv"), delimiter = ',', numLinesToSkip = 1)
  def testLnWithCsvData(x: Double, expected: Double): Unit = {
    val actual = ln.calculate(BigDecimal(x), BigDecimal(precision))
    assertEquals(expected, actual.doubleValue(), precision)
  }

  @Test
  def testLnOfOneIsZero(): Unit = {
    val result = ln.calculate(BigDecimal(1), BigDecimal(precision))
    assertEquals(0.0, result.doubleValue(), precision)
  }

  @Test
  def testLnOfEIsOne(): Unit = {
    val result = ln.calculate(BigDecimal(Math.E), BigDecimal(precision))
    assertEquals(1.0, result.doubleValue(), 0.001)
  }

  @Test
  def testLnThrowsExceptionForZero(): Unit = {
    assertThrows(classOf[IllegalArgumentException], () => {
      ln.calculate(BigDecimal(0), BigDecimal(precision))
    })
  }

  @Test
  def testLnThrowsExceptionForNegative(): Unit = {
    assertThrows(classOf[IllegalArgumentException], () => {
      ln.calculate(BigDecimal(-1), BigDecimal(precision))
    })
  }
}