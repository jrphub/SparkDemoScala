package com.spark.practice.datasets

import org.apache.spark.sql.Row
import org.apache.spark.sql.SparkSession
import org.apache.spark.sql.types._
import org.apache.spark.sql.types.StringType
import org.apache.spark.sql.types.StructField
import org.apache.spark.sql.types.StructType

object DatasetCustomized {
  def main(args: Array[String]) {
    val spark = SparkSession.builder.
      master("local[*]")
      .appName("DatasetCustomized")
      .getOrCreate()
    // Create an RDD
    val peopleRDD = spark.sparkContext.textFile("file:///home/jrp/workspace_1/SparkDemoScala/input-data/people.csv")

    // The schema is encoded in a string
    val schemaString = "name age"

    // Generate the schema based on the string of schema
    val fields = schemaString.split(" ")
      .map(fieldName => StructField(fieldName, StringType, nullable = true))
    val schema = StructType(fields)

    // Convert records of the RDD (people) to Rows
    val rowRDD = peopleRDD
      .map(_.split(","))
      .map(attributes => Row(attributes(0), attributes(1).trim))

    // Apply the schema to the RDD
    val peopleDF = spark.createDataFrame(rowRDD, schema)

    // Creates a temporary view using the DataFrame
    peopleDF.createOrReplaceTempView("people")

    // SQL can be run over a temporary view created using DataFrames
    val results = spark.sql("SELECT name FROM people")

    results.show()
    
/*    +------+
    |  name|
    +------+
    | jenny|
    |  John|
    |thomas|
    | Robin|
    |  Roni|
    | Jacob|
    |  Simi|
    |  Rimi|
    +------+*/
  }
}