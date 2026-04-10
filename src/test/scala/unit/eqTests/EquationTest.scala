package unit.eqTests

import eq.Equation
import log._
import org.junit.jupiter.api.Assertions.{assertDoesNotThrow, assertEquals, assertThrows}
import org.junit.jupiter.api.Test
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.CsvFileSource
import org.junit.jupiter.api.function.Executable
import trig.*

class EquationTest {
  private val equation = Equation(Sin(), Cos(), Tan(), Csc(), Cot(), Ln(), LogN(2), LogN(3), LogN(5), LogN(10))
  private val precision = 0.0001

  @ParameterizedTest
  @CsvFileSource(resources = Array("/equation.csv"), delimiter = ',', numLinesToSkip = 1)
  def testEquationWithCsvData(x: Double, expected: Double): Unit = {
    if (x == 0) {
      assertThrows(classOf[IllegalArgumentException], () => {
        equation.calculate(BigDecimal(x), BigDecimal(precision))
      })
    } else {
      val actual = equation.calculate(BigDecimal(x), BigDecimal(precision))
      assertEquals(expected, actual.doubleValue(), precision)
    }
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
  def testEquationAtPiIsDefined(): Unit = {
    assertDoesNotThrow(() => {
      equation.calculate(BigDecimal(Math.PI), BigDecimal(precision))
    })
  }

  @Test
  def testEquationAtPiHalfIsDefined(): Unit = {
    assertDoesNotThrow(() => {
      equation.calculate(BigDecimal(Math.PI / 2), BigDecimal(precision))
    })
  }
}
