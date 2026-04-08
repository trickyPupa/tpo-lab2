import trig.Sin
import util.CsvWriter

@main
def main(): Unit = {
  val precision = BigDecimal.decimal(0.123)
  val sin = Sin()
  
  CsvWriter.write(sin, 
    BigDecimal(0), 
    BigDecimal(5), 
    BigDecimal(0.2), 
    precision, 
    "sin.csv")
}