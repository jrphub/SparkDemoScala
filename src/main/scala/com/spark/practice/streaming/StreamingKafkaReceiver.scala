package com.spark.practice.streaming

import org.apache.spark.SparkConf
import org.apache.spark.streaming.Seconds
import org.apache.spark.streaming.StreamingContext
import org.apache.spark.streaming.dstream.DStream.toPairDStreamFunctions
import org.apache.spark.streaming.kafka.KafkaUtils

object StreamingKafkaReceiver {
  def main(args: Array[String]) {
    val conf = new SparkConf().setMaster("local[2]").setAppName("StreamingKafkaReceiver")
    val ssc = new StreamingContext(conf, Seconds(2))
    ssc.checkpoint("file:///home/jrp/workspace_1/SparkDemoScala/ckpt-dir")
    
    val numThreads = 2
    val topicMap = {"test"}.split(",").map((_, numThreads.toInt)).toMap
    val lines = KafkaUtils.createStream(ssc, "localhost:2181", "eclipse1", topicMap).map(_._2)
    
    // Split each line into words
    val words = lines.flatMap(_.split(" "))

    // Count each word in each batch
    val pairs = words.map(word => (word, 1))
    val wordCounts = pairs.reduceByKey(_ + _)

    // Print the first ten elements of each RDD generated in this DStream to the console
    wordCounts.print()
    ssc.start() // Start the computation
    ssc.awaitTermination() // Wait for the computation to terminate
  }
}