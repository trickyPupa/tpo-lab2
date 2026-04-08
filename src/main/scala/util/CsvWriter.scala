package util

import java.nio.charset.StandardCharsets
import java.nio.file.{Files, Paths}
import scala.util.Using
import scala.util.control.NonFatal

object CsvWriter {
  def write(function: func.Function,
            start: BigDecimal,
            end: BigDecimal,
            step: BigDecimal,
            precision: BigDecimal,
            fileName: String): Unit = {
    require(step > 0, "Шаг должен быть >0")
    require(precision > 0 && precision < 1, "Точность должна быть >0 и <1")
    require(start <= end, "Начальная точка должна быть <= конечной")

    val path = Paths.get(fileName)

    Using.resource(Files.newBufferedWriter(path, StandardCharsets.UTF_8)) { writer =>
      writer.write("x,result\n")

      var x = start
      while (x <= end) {
        val result =
          try formatDecimal(function.calculate(x, precision), 4)
          catch {
            case NonFatal(_) => "undefined"
          }

        writer.write(s"$x,${result.format()}\n")
        x += step
      }
    }
  }

  private def formatDecimal(value: BigDecimal, digits: Int): String = {
    value.setScale(digits, BigDecimal.RoundingMode.HALF_EVEN).toString
  }
}