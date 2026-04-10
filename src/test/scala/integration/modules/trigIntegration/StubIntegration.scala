package integration.modules.trigIntegration

import eq.Equation
import org.junit.jupiter.api.Assertions.{assertEquals, assertThrows}
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.CsvFileSource
import stub.*

class StubIntegration {
  private val precision = 0.001

  private val sin = new SinStub()
  private val cos = new CosStub()
  private val tan = new TanStub()
  private val cot = new CotStub()
  private val csc = new CscStub()
  private val ln = new LnStub()
  private val log2 = new Log2Stub()
  private val log3 = new Log3Stub()
  private val log5 = new Log5Stub()
  private val log10 = new Log10Stub()

  private val equation = new Equation(sin, cos, tan, csc, cot, ln, log2, log3, log5, log10)
  
  @ParameterizedTest
  @CsvFileSource(resources = Array("/equation.csv"), numLinesToSkip = 1)
  def testEquation(x: Double, expected: Double): Unit = {
    if (x == 0){
      assertThrows(classOf[IllegalArgumentException], () => {
        equation.calculate(BigDecimal(x), BigDecimal(precision))
      })
    } else {
      val actual = equation.calculate(BigDecimal(x), BigDecimal(precision))
      assertEquals(expected, actual.doubleValue(), 0.01)
    }
  }
}
