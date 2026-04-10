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
      -0.5 -> 1.2116,
      -1.0 -> 0.9731,
      -2.0 -> 23.6419,
      -3.0 -> 1.3098,
      -4.0 -> -2.1407
    )

    expectedResults.foreach { case (x, expected) =>
      val actual = equation.calculate(BigDecimal(x), BigDecimal(precision))
      assertEquals(expected, actual.doubleValue(), 0.02, s"Failed for x=$x")
    }
  }

  @Test
  def testEquationPositiveBranch(): Unit = {
    val expectedResults = Map(
      0.5 -> -3.5602,
      1.5 -> 8.7750,
      2.0 -> 6.7500,
      3.0 -> 6.2325,
      4.0 -> 6.3501,
      5.0 -> 6.5608
    )

    expectedResults.foreach { case (x, expected) =>
      val actual = equation.calculate(BigDecimal(x), BigDecimal(precision))
      assertEquals(expected, actual.doubleValue(), 0.02, s"Failed for x=$x")
    }
  }

  @Test
  def testEquationSpecialPoints(): Unit = {
    assertThrows(classOf[IllegalArgumentException], () => {
      equation.calculate(BigDecimal(0), BigDecimal(precision))
    })
  }
}
