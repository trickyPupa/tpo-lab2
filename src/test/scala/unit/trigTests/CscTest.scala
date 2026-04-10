package unit.trigTests

import org.junit.jupiter.api.Assertions.{assertEquals, assertThrows}
import org.junit.jupiter.api.Test
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.{CsvFileSource, ValueSource}
import trig.Csc

class CscTest {
  private val csc = Csc()
  private val precision = 0.0001

  @ParameterizedTest
  @CsvFileSource(resources = Array("/csc.csv"), delimiter = ',', numLinesToSkip = 1)
  def testCscWithCsvData(x: Double, expected: Double): Unit = {
    val actual = csc.calculate(BigDecimal(x), BigDecimal(precision))
    assertEquals(expected, actual.doubleValue(), precision)
  }

  @ParameterizedTest
  @ValueSource(doubles = Array(0.5, 1.0, 1.5, 2.0, 2.5, 3.0, 3.5, 4.0, 4.5))
  def testCscIsPeriodic(x: Double): Unit = {
    val original = csc.calculate(BigDecimal(x), BigDecimal(precision))
    val shifted = csc.calculate(BigDecimal(x + 2 * Math.PI), BigDecimal(precision))

    assertEquals(original.doubleValue(), shifted.doubleValue(), 0.0001)
  }

  @Test
  def testCscThrowsExceptionAtZero(): Unit = {
    assertThrows(classOf[IllegalArgumentException], () => {
      csc.calculate(BigDecimal(0), BigDecimal(precision))
    })
  }

  @Test
  def testCscThrowsExceptionAtPi(): Unit = {
    assertThrows(classOf[IllegalArgumentException], () => {
      csc.calculate(BigDecimal(Math.PI), BigDecimal(precision))
    })
  }
}