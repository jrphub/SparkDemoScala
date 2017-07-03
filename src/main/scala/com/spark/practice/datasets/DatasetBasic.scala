package com.spark.practice.datasets

import org.apache.spark.sql.SparkSession

object DatasetBasic {
  def main(args: Array[String]) {
    val sparkSession = SparkSession.builder.
      master("local[*]")
      .appName("DatasetBasic")
      .getOrCreate()

    val df = sparkSession.read.json("file:///home/jrp/workspace_1/SparkDemoScala/input-data/people.json");

    df.show()
    df.printSchema()
    df.select("name").show()
    df.groupBy("age").count().show()

    //running sql query programmatically
    df.createOrReplaceTempView("people")
    val sqlDF = sparkSession.sql("SELECT * FROM people")
    sqlDF.show()

    // Register the DataFrame as a global temporary view
    df.createGlobalTempView("people_global")

    // Global temporary view is tied to a system preserved database `global_temp`
    sparkSession.sql("SELECT * FROM global_temp.people_global").show()
    sparkSession.newSession().sql("SELECT * FROM global_temp.people_global").show()

  }
}