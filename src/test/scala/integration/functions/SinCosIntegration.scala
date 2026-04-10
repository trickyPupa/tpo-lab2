package integration.functions

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import trig.{Cos, Sin}

class SinCosIntegration {
    private val precision = 0.001
    private val sin = new Sin()
    private val cos = new Cos()

    @Test
    def testCosViaSin(): Unit = {
      // cos(x) = sin(pi/2 - x)
      val x = BigDecimal(1.0472) // pi/3
      val expected = 0.5

      val actual = cos.calculate(x, BigDecimal(precision))
      assertEquals(expected, actual.doubleValue(), 0.001)
    }

    @Test
    def testOsnovnoyeTrigonometricheskoyeTozhdestvo(): Unit = {//не помню как будет на английском, времени гуглить нет, простите за такое название
      val testPoints = List(0.0, 0.5236, 0.7854, 1.0472, 1.5708)

      testPoints.foreach { x =>
        val sinx = sin.calculate(BigDecimal(x), BigDecimal(precision))
        val cosx = cos.calculate(BigDecimal(x), BigDecimal(precision))
        val sum = sinx * sinx + cosx * cosx

        assertEquals(1.0, sum.doubleValue(), 0.01,
          s"sin^2 + cos^2 != 1 for x = $x")
      }
    }
  }
