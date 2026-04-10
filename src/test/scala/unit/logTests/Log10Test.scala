package unit.logTests

import org.junit.jupiter.api.Assertions.{assertEquals, assertThrows}
import org.junit.jupiter.api.Test
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.CsvFileSource
import log.LogN

class Log10Test {
  private val log10 = LogN(10)
  private val precision = 0.0001

  @ParameterizedTest
  @CsvFileSource(resources = Array("/log10.csv"), delimiter = ',', numLinesToSkip = 1)
  def testLog10WithCsvData(x: Double, expected: Double): Unit = {
    val actual = log10.calculate(BigDecimal(x), BigDecimal(precision))
    assertEquals(expected, actual.doubleValue(), precision)
  }

  @Test
  def testLog10OfOneIsZero(): Unit = {
    val result = log10.calculate(BigDecimal(1), BigDecimal(precision))
    assertEquals(0.0, result.doubleValue(), precision)
  }

  @Test
  def testLog10OfTenIsOne(): Unit = {
    val result = log10.calculate(BigDecimal(10), BigDecimal(precision))
    assertEquals(1.0, result.doubleValue(), 0.0001)
  }

  @Test
  def testLog10ThrowsExceptionForZero(): Unit = {
    assertThrows(classOf[IllegalArgumentException], () => {
      log10.calculate(BigDecimal(0), BigDecimal(precision))
    })
  }

  @Test
  def testLog10ThrowsExceptionForNegative(): Unit = {
    assertThrows(classOf[IllegalArgumentException], () => {
      log10.calculate(BigDecimal(-1), BigDecimal(precision))
    })
  }
}