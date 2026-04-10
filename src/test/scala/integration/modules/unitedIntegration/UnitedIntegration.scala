package integration.modules.unitedIntegration

import eq.Equation
import org.junit.jupiter.api.Assertions.{assertEquals, assertThrows}
import org.junit.jupiter.api.Test
import trig._
import log._

class UnitedIntegration {
  private val precision = 0.001

  private val equation = new Equation(Sin(), Cos(),
    Tan(), Csc(), Cot(), Ln(), LogN(2), LogN(3), LogN(5), LogN(10))

  @Test
  def testEquationNegativeBranch(): Unit = {
    val expectedResults = Map(
      -0.5 -> 0.122972,
      -1.0 -> 10.5953,
      -2.0 -> -1295.03,
      -3.0 -> -0.00054624,
      -4.0 -> 5.88553,
      -5.0 -> 19577.4
    )

    expectedResults.foreach { case (x, expected) =>
      val actual = equation.calculate(BigDecimal(x), BigDecimal(precision))
      assertEquals(expected, actual.doubleValue(), 0.01, s"Failed for x=$x")
    }
  }

  @Test
  def testEquationPositiveBranch(): Unit = {
    val expectedResults = Map(
      0.5 -> -0.371263,
      1.5 -> 4.00123,
      2.0 -> 2.17487,
      3.0 -> 0.921896,
      4.0 -> 0.307052,
      5.0 -> -0.102364
    )

    expectedResults.foreach { case (x, expected) =>
      val actual = equation.calculate(BigDecimal(x), BigDecimal(precision))
      assertEquals(expected, actual.doubleValue(), 0.01, s"Failed for x=$x")
    }
  }

  @Test
  def testEquationSpecialPoints(): Unit = {
    assertThrows(classOf[IllegalArgumentException], () => {
      equation.calculate(BigDecimal(0), BigDecimal(precision))
    })
  }
}
