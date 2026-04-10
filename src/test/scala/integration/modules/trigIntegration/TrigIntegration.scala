package integration.modules.trigIntegration

import eq.Equation
import org.junit.jupiter.api.Assertions.{assertEquals, assertThrows}
import org.junit.jupiter.api.Test
import stub.*
import trig.*

class TrigIntegration {
    private val precision = 0.001

    private val sin = new Sin()
    private val cos = new Cos()
    private val tan = new Tan()
    private val cot = new Cot()
    private val csc = new Csc()

    private val ln = new LnStub()
    private val log2 = new Log2Stub()
    private val log3 = new Log3Stub()
    private val log5 = new Log5Stub()
    private val log10 = new Log10Stub()

    private val equation = new Equation(sin, cos, tan, csc, cot, ln, log2, log3, log5, log10)

    @Test
    def testTanViaSinCos(): Unit = {
      val x = BigDecimal(0.7854) // pi/4
      val sinx = sin.calculate(x, BigDecimal(precision))
      val cosx = cos.calculate(x, BigDecimal(precision))
      val expected = sinx / cosx

      val actual = tan.calculate(x, BigDecimal(precision))
      assertEquals(expected.doubleValue(), actual.doubleValue(), 0.001)
    }

    @Test
    def testCotViaTan(): Unit = {
      val x = BigDecimal(0.7854) // pi/4
      val tanx = tan.calculate(x, BigDecimal(precision))
      val expected = BigDecimal(1) / tanx

      val actual = cot.calculate(x, BigDecimal(precision))
      assertEquals(expected.doubleValue(), actual.doubleValue(), 0.001)
    }

    @Test
    def testCscViaSin(): Unit = {
      val x = BigDecimal(1.5708) // pi/2
      val sinx = sin.calculate(x, BigDecimal(precision))
      val expected = BigDecimal(1) / sinx

      val actual = csc.calculate(x, BigDecimal(precision))
      assertEquals(expected.doubleValue(), actual.doubleValue(), 0.001)
    }

    @Test
    def testTrigIdentities(): Unit = {
      val x = BigDecimal(1.0)

      // 1 + tan^2 = sec^2 = 1/cos^2
      val tanx = tan.calculate(x, BigDecimal(precision))
      val cosx = cos.calculate(x, BigDecimal(precision))
      val expected = BigDecimal(1) / (cosx * cosx)
      val actual = BigDecimal(1) + tanx * tanx

      assertEquals(expected.doubleValue(), actual.doubleValue(), 0.01)
    }

    @Test
    def testDiscontinuityPoints(): Unit = {
      // tan разрыв в pi/2
      assertThrows(classOf[IllegalArgumentException], () => {
        tan.calculate(BigDecimal(Math.PI / 2), BigDecimal(precision))
      })

      // cot разрыв в 0
      assertThrows(classOf[IllegalArgumentException], () => {
        cot.calculate(BigDecimal(0), BigDecimal(precision))
      })

      // csc разрыв в 0
      assertThrows(classOf[IllegalArgumentException], () => {
        csc.calculate(BigDecimal(0), BigDecimal(precision))
      })
    }

  }
