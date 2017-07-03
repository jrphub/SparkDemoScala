package com.spark.practice.core

import org.apache.spark.SparkConf
import org.apache.spark.SparkContext

object WordCountLocal {
  def main(args: Array[String]) {
    val logFile = "file:///home/jrp/workspace_1/SparkDemoScala/input-data/wordcount.txt" // Should be some file on your system
    val conf = new SparkConf().setAppName("WordCountLocal").setMaster("local[*]")
    val sc = new SparkContext(conf)
    val logData = sc.textFile(logFile, 2).cache()
    val numAs = logData.filter(line => line.contains("a")).count()
    val numBs = logData.filter(line => line.contains("b")).count()
    println(s"Lines with a: $numAs, Lines with b: $numBs") //Lines with a: 44, Lines with b: 29
    sc.stop()
  }
}