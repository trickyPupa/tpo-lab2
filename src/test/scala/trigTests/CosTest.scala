package trigTests

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.{CsvFileSource, CsvSource, ValueSource}
import trig.Cos

class CosTest {
  private val cos = Cos()
  private val precision = 0.0001

  @ParameterizedTest
  @CsvFileSource(resources = Array("/cos.csv"), delimiter = ',', numLinesToSkip = 1)
  def testCosWithCsvData(x: Double, expected: Double): Unit = {
    val actual = cos.calculate(BigDecimal(x), BigDecimal(precision))
    assertEquals(expected, actual.doubleValue(), precision)
  }

  @ParameterizedTest
  @CsvSource(delimiter = ',',
    value = Array(
    "0, 1",
    "1.0472, 0.5",
    "3.1416, -1",
    "1.5708, 0",
    "2.0944, -0.5",
    "2.0944, -0.5",
    "4.7124, 0",
    "5.236, 0.5"
  ))
  def testTableData(x: Double, expected: Double): Unit = {
    val actual = cos.calculate(BigDecimal(x), BigDecimal(precision))
    assertEquals(expected, actual.doubleValue(), precision)
  }

  @ParameterizedTest
  @ValueSource(doubles = Array(0.0, 1.5708, 3.1416, 4.7124))
  def testCosIsPeriodic(x: Double): Unit = {
    val original = cos.calculate(BigDecimal(x), BigDecimal(precision))
    val shifted = cos.calculate(BigDecimal(x + 6.2832), BigDecimal(precision))

    assertEquals(original.doubleValue(), shifted.doubleValue(), 0.0001)
  }
}
