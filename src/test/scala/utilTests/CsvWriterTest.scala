package utilTests

import org.junit.jupiter.api.Assertions.{assertEquals, assertThrows, assertTrue}
import org.junit.jupiter.api.{AfterEach, BeforeEach, Test}
import org.junit.jupiter.api.io.TempDir

import java.nio.file.{Files, Path, Paths}
import java.nio.charset.StandardCharsets
import scala.io.Source
import func.Function
import log.Ln
import trig.Sin
import util.CsvWriter

class CsvWriterTest {

  @TempDir
  var tempDir: Path = _

  private var testFunction: Function = _

  @BeforeEach
  def setUp(): Unit = {
    testFunction = new Sin()
  }

  @AfterEach
  def cleanUp(): Unit = {
    // Временные файлы удалятся автоматически благодаря @TempDir
  }

  @Test
  def testWriteValidFunction(): Unit = {
    val fileName = tempDir.resolve("test_valid").toString
    val start = BigDecimal(0)
    val end = BigDecimal(3.1416)
    val step = BigDecimal(1.0472)
    val precision = BigDecimal(0.0001)

    CsvWriter.write(testFunction, start, end, step, precision, fileName)

    val csvFile = Paths.get(s"$fileName.csv")
    assertTrue(Files.exists(csvFile))

    val lines = Source.fromFile(csvFile.toFile).getLines().toList
    assertTrue(lines.nonEmpty)
    assertEquals("x,result", lines.head)
    assertTrue(lines.length > 1)
  }

  @Test
  def testWriteWithInvalidPoints(): Unit = {
    val fileName = tempDir.resolve("test_invalid").toString
    val start = BigDecimal(-1)
    val end = BigDecimal(1)
    val step = BigDecimal(0.5)
    val precision = BigDecimal(0.0001)

    val lnFunction = new Ln()

    CsvWriter.write(lnFunction, start, end, step, precision, fileName)

    val validFile = Paths.get(s"$fileName.csv")
    val invalidFile = Paths.get(s"$fileName-invalid.csv")

    assertTrue(Files.exists(validFile))
    assertTrue(Files.exists(invalidFile))

    val invalidLines = Source.fromFile(invalidFile.toFile).getLines().toList
    assertEquals("x,result", invalidLines.head)
    assertTrue(invalidLines.exists(_.contains("undefined")))
  }

  @Test
  def testWriteAllValid(): Unit = {
    val fileName = tempDir.resolve("test_all_valid").toString
    val start = BigDecimal(0.5)
    val end = BigDecimal(2.5)
    val step = BigDecimal(1.0)
    val precision = BigDecimal(0.0001)

    CsvWriter.write(testFunction, start, end, step, precision, fileName)

    val invalidFile = Paths.get(s"$fileName-invalid.csv")
    assertTrue(Files.notExists(invalidFile))
  }

  @Test
  def testThrowsWhenStepNotPositive(): Unit = {
    val exception = assertThrows(classOf[IllegalArgumentException], () => {
      CsvWriter.write(testFunction, BigDecimal(0), BigDecimal(1), BigDecimal(-0.1), BigDecimal(0.0001), "test")
    })
    assertEquals("requirement failed: Шаг должен быть >0", exception.getMessage)
  }

  @Test
  def testThrowsWhenPrecisionInvalid(): Unit = {
    val exception1 = assertThrows(classOf[IllegalArgumentException], () => {
      CsvWriter.write(testFunction, BigDecimal(0), BigDecimal(1), BigDecimal(0.1), BigDecimal(0), "test")
    })
    assertEquals("Точность должна быть >0 и <1", exception1.getMessage)

    val exception2 = assertThrows(classOf[IllegalArgumentException], () => {
      CsvWriter.write(testFunction, BigDecimal(0), BigDecimal(1), BigDecimal(0.1), BigDecimal(1.5), "test")
    })
    assertEquals("Точность должна быть >0 и <1", exception2.getMessage)
  }

  @Test
  def testThrowsWhenStartGreaterThanEnd(): Unit = {
    val exception = assertThrows(classOf[IllegalArgumentException], () => {
      CsvWriter.write(testFunction, BigDecimal(5), BigDecimal(1), BigDecimal(0.1), BigDecimal(0.0001), "test")
    })
    assertEquals("requirement failed: Начальная точка должна быть <= конечной", exception.getMessage)
  }

  @Test
  def testCsvFormat(): Unit = {
    val fileName = tempDir.resolve("test_format").toString
    val start = BigDecimal(0)
    val end = BigDecimal(1.5708)
    val step = BigDecimal(0.7854)
    val precision = BigDecimal(0.0001)

    CsvWriter.write(testFunction, start, end, step, precision, fileName)

    val lines = Source.fromFile(Paths.get(s"$fileName.csv").toFile).getLines().toList

    lines.tail.foreach { line =>
      val parts = line.split(",")
      assertEquals(2, parts.length)
      val x = parts(0).toDouble
      val result = parts(1).toDouble
      assertTrue(result >= -1 && result <= 1) // Для синуса
    }
  }

  @Test
  def testPointsIteration(): Unit = {
    val fileName = tempDir.resolve("test_points").toString
    val start = BigDecimal(0)
    val end = BigDecimal(2)
    val step = BigDecimal(0.5)
    val precision = BigDecimal(0.0001)

    CsvWriter.write(testFunction, start, end, step, precision, fileName)

    val lines = Source.fromFile(Paths.get(s"$fileName.csv").toFile).getLines().toList
    val points = lines.tail.map(_.split(",")(0).toDouble)

    val expected = List(0.0, 0.5, 1.0, 1.5, 2.0)
    assertEquals(expected, points)
  }

  @Test
  def testFormatDecimal(): Unit = {
    val value = BigDecimal(1.23456789)
    val formatted = CsvWriter.getClass.getDeclaredMethod("formatDecimal", classOf[BigDecimal], classOf[Int])
    formatted.setAccessible(true)
    val result = formatted.invoke(null, value, Int.box(4)).asInstanceOf[String]

    // Проверяем формат (может быть "1.2346" с округлением)
    assertTrue(result.matches("\\d+\\.\\d{4}"))
  }
}
