package main

import trig.Sin

@main
def main(): Unit = {
  val precision = BigDecimal.decimal(1.123d)
  val x = BigDecimal.decimal(0.3)
  
  val sin = Sin()
  
  val res = sin.calculate(x, null)
  
  println(res)
}