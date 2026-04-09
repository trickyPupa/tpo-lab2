package trigTests

import org.junit.jupiter.api.Assertions.{assertEquals, assertThrows}
import org.junit.jupiter.api.Test
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.{CsvFileSource, ValueSource}
import trig.Cot

class CotTest {
  private val cot = Cot()
  private val precision = 0.0001

  @ParameterizedTest
  @CsvFileSource(resources = Array("/cot.csv"), delimiter = ',', numLinesToSkip = 1)
  def testCotWithCsvData(x: Double, expected: Double): Unit = {
    val actual = cot.calculate(BigDecimal(x), BigDecimal(precision))
    assertEquals(expected, actual.doubleValue(), precision)
  }

  @ParameterizedTest
  @ValueSource(doubles = Array(0.5, 1.0, 1.5, 2.0, 2.5, 3.0, 3.5, 4.0, 4.5))
  def testCotIsPeriodic(x: Double): Unit = {
    val original = cot.calculate(BigDecimal(x), BigDecimal(precision))
    val shifted = cot.calculate(BigDecimal(x + Math.PI), BigDecimal(precision))

    assertEquals(original.doubleValue(), shifted.doubleValue(), 0.0001)
  }

  @Test
  def testCotThrowsExceptionAtZero(): Unit = {
    assertThrows(classOf[IllegalArgumentException], () => {
      cot.calculate(BigDecimal(0), BigDecimal(precision))
    })
  }

  @Test
  def testCotThrowsExceptionAtPi(): Unit = {
    assertThrows(classOf[IllegalArgumentException], () => {
      cot.calculate(BigDecimal(Math.PI), BigDecimal(precision))
    })
  }
}