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

    val points = Iterator.iterate(start)(_ + step).takeWhile(_ <= end).toList

    val (valid, invalid) = points.partition { x =>
      try {
        function.calculate(x, precision)
        true
      } catch {
        case NonFatal(_) => false
      }
    }

    writeCsv(s"$fileName.csv", valid.map { x =>
      (x, formatDecimal(function.calculate(x, precision), 4))
    })

    if (invalid.nonEmpty) {
      writeCsv(s"$fileName-invalid.csv", invalid.map(x => (x, "undefined")))
    } else {
      Files.deleteIfExists(Paths.get(s"$fileName-invalid.csv"))
    }
  }

  private def writeCsv(fileName: String, data: List[(BigDecimal, String)]): Unit = {
    val path = Paths.get(fileName)
    Using.resource(Files.newBufferedWriter(path, StandardCharsets.UTF_8)) { writer =>
      writer.write("x,result\n")
      data.foreach { case (x, result) =>
        writer.write(s"$x,$result\n")
      }
    }
  }

  private def formatDecimal(value: BigDecimal, digits: Int): String = {
    value.setScale(digits, BigDecimal.RoundingMode.HALF_EVEN).toString
  }
}