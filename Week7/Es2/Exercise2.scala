package com.de.spark

import org.apache.spark.{SparkConf, SparkContext}
import org.apache.spark.sql.SparkSession
import scala.util.parsing.json.JSON

object Exercise2 {
  def main(args: Array[String]) {
    val conf = new SparkConf().setAppName("Exercise2").setMaster("local")
    val sc = new SparkContext(conf)

    if (args.length < 2) {
      println("Usage: Exercise <input_path> <output_path>")
      System.exit(1)
    }

    val filePath = args(0)
    val rawData = sc.textFile(filePath)

    val grades = rawData.flatMap { line =>
      if (line.contains("\t") && line.split("\t").length > 1) {
        val jsonPart = line.split("\t")(1)
        val parsedJson = JSON.parseFull(jsonPart)
        parsedJson match {
          case Some(map: Map[String, Any]) =>
            val results = map("results").asInstanceOf[List[Map[String, Any]]]
            results.map(result => (result("matno").toString, result("grade").toString.toDouble))
          case _ => List()
        }
      } else {
        List()
      }
    }

    val sumCount = grades.mapValues(grade => (grade, 1))
                         .reduceByKey((x, y) => (x._1 + y._1, x._2 + y._2))

    val averageGrades = sumCount.mapValues{ case (sum, count) => sum / count }

    averageGrades.saveAsTextFile(args(1))

    sc.stop()
  }
}

