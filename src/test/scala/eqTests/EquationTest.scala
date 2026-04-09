package eqTests

import eq.Equation
import org.junit.jupiter.api.Assertions.{assertEquals, assertThrows}
import org.junit.jupiter.api.Test
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.CsvFileSource
import org.junit.jupiter.api.function.Executable

class EquationTest {
  private val equation = Equation()
  private val precision = 0.0001

  @ParameterizedTest
  @CsvFileSource(resources = Array("/equation.csv"), delimiter = ',', numLinesToSkip = 1)
  def testEquationWithCsvData(x: Double, expected: Double): Unit = {
    val actual = equation.calculate(BigDecimal(x), BigDecimal(precision))
    assertEquals(expected, actual.doubleValue(), precision)
  }

  @Test
  def testEquationAtZeroThrowsException(): Unit = {
    assertThrows(classOf[IllegalArgumentException], () => {
      equation.calculate(BigDecimal(0), BigDecimal(precision))
    })
  }

  @Test
  def testEquationAtOneThrowsException(): Unit = {
    assertThrows(classOf[ArithmeticException], () => {
      equation.calculate(BigDecimal(1), BigDecimal(precision))
    })
  }

  @Test
  def testEquationThrowsExceptionAtPi(): Unit = {
    assertThrows(classOf[IllegalArgumentException], () => {
      equation.calculate(BigDecimal(Math.PI), BigDecimal(precision))
    })
  }

  @Test
  def testEquationThrowsExceptionAtPiHalf(): Unit = {
    assertThrows(classOf[IllegalArgumentException], () => {
      equation.calculate(BigDecimal(Math.PI / 2), BigDecimal(precision))
    })
  }
}
