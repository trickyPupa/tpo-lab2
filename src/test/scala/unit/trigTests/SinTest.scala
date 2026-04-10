package unit.trigTests

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.{CsvFileSource, CsvSource, ValueSource}
import trig.Sin

class SinTest {
  private val sin = Sin()
  private val precision = 0.0001

  @ParameterizedTest
  @CsvFileSource(resources = Array("/sin.csv"), delimiter = ',', numLinesToSkip = 1)
  def testSinWithCsvData(x: Double, expected: Double): Unit = {
    val actual = sin.calculate(BigDecimal(x), BigDecimal(precision))
    assertEquals(expected, actual.doubleValue(), precision)
  }

  @ParameterizedTest
  @CsvSource(delimiter = ',',
    value = Array(
      "0, 0",
      "1.0472, 0.8660",
      "1.5708, 1",
      "3.1416, 0",
      "4.7124, -1",
      "5.236, -0.8660",
      "6.2832, 0"
    ))
  def testTableData(x: Double, expected: Double): Unit = {
    val actual = sin.calculate(BigDecimal(x), BigDecimal(precision))
    assertEquals(expected, actual.doubleValue(), precision)
  }

  @ParameterizedTest
  @ValueSource(doubles = Array(0.0, 1.5708, 3.1416, 4.7124))
  def testSinIsPeriodic(x: Double): Unit = {
    val original = sin.calculate(BigDecimal(x), BigDecimal(precision))
    val shifted = sin.calculate(BigDecimal(x + 6.2832), BigDecimal(precision))

    assertEquals(original.doubleValue(), shifted.doubleValue(), 0.0001)
  }
}