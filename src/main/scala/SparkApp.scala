/*
# 
# MIT License
#
# Copyright (c) 2016 Saniya Tech Inc.
# 
# Permission is hereby granted, free of charge, to any person obtaining a copy of this software and
# associated documentation files (the "Software"), to deal in the Software without restriction, 
# including without limitation the rights to use, copy, modify, merge, publish, distribute, sublicense,
# and/or sell copies of the Software, and to permit persons to whom the Software is furnished to do so,
# subject to the following conditions:
# 
# The above copyright notice and this permission notice shall be included in all copies or substantial
# portions of the Software.
# 
# THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR IMPLIED, INCLUDING BUT NOT
# LIMITED TO THE WARRANTIES OF MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT.
# IN NO EVENT SHALL THE AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY,
# WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM, OUT OF OR IN CONNECTION WITH THE
# SOFTWARE OR THE USE OR OTHER DEALINGS IN THE SOFTWARE.
# 
# 
*/
/* SparkApp.scala */
import java.io.FileWriter

import org.apache.spark.SparkConf
import org.apache.spark.sql.{Dataset, SparkSession}

object SparkApp {
  def main(args: Array[String]) {
    val sparkConf = new SparkConf().setAppName("Spark App")
    val spark = SparkSession.builder().config(sparkConf).getOrCreate()

    import spark.implicits._
    var end = if (args.size > 0) {
      println("args is: " + args(0))
      args(0)
    }
    else "101"

    val dataset = spark.range(1, Integer.parseInt(end), 1, 2)
    val avg = dataset.agg("id" -> "avg").head.getAs[Double](0)
    spark.stop()

    println(s"Spark App average : $avg")
    val file = new FileWriter("/tmp/bogdan_out.txt")
    file.write(s"Spark App average value is: $avg")
    file.close()
  }
}
