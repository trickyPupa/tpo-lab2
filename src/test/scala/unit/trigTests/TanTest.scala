package unit.trigTests

import org.junit.jupiter.api.Assertions.{assertEquals, assertThrows}
import org.junit.jupiter.api.Test
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.{CsvFileSource, ValueSource}
import trig.Tan

class TanTest {
  private val tan = Tan()
  private val precision = 0.0001

  @ParameterizedTest
  @CsvFileSource(resources = Array("/tan.csv"), delimiter = ',', numLinesToSkip = 1)
  def testTanWithCsvData(x: Double, expected: Double): Unit = {
    val actual = tan.calculate(BigDecimal(x), BigDecimal(precision))
    assertEquals(expected, actual.doubleValue(), precision)
  }

  @ParameterizedTest
  @ValueSource(doubles = Array(-2.0, -1.0, -0.5, 0.5, 1.0, 2.0, 3.0, 4.0))
  def testTanIsPeriodic(x: Double): Unit = {
    val original = tan.calculate(BigDecimal(x), BigDecimal(precision))
    val shifted = tan.calculate(BigDecimal(x + Math.PI), BigDecimal(precision))

    assertEquals(original.doubleValue(), shifted.doubleValue(), 0.0001)
  }

  @Test
  def testTanIsOdd(): Unit = {
    val testValues = Seq(0.5, 1.0, 2.0, 2.5)

    testValues.foreach { x =>
      val positive = tan.calculate(BigDecimal(x), BigDecimal(precision))
      val negative = tan.calculate(BigDecimal(-x), BigDecimal(precision))

      assertEquals(positive.doubleValue(), -negative.doubleValue(), precision)
    }
  }

  @Test
  def testTanThrowsExceptionAtPiHalf(): Unit = {
    assertThrows(classOf[IllegalArgumentException], () => {
      tan.calculate(BigDecimal(Math.PI / 2), BigDecimal(precision))
    })
  }

  @Test
  def testTanThrowsExceptionAtThreePiHalf(): Unit = {
    assertThrows(classOf[IllegalArgumentException], () => {
      tan.calculate(BigDecimal(3 * Math.PI / 2), BigDecimal(precision))
    })
  }
}