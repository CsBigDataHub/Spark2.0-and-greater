import org.apache.spark.SparkConf
import org.apache.spark.streaming.{Seconds, StreamingContext}


object WordCountFileStreaming {
  def main(args: Array[String]) {

    //StreamingExamples.setStreamingLogLevels()
    val sparkConf = new SparkConf().setAppName("File Streaming Word Count").setMaster("local")

    // Create the context
    val ssc = new StreamingContext(sparkConf, Seconds(2))

    // Create the FileInputDStream on the directory and use the
    // stream to count words in new files created
    val lines = ssc.textFileStream("/tmp/ssclisten/subfolder1/subfolder2")
    val words = lines.flatMap(_.split(" "))
    val wordCounts = words.map(x => (x, 1)).reduceByKey(_ + _)
    wordCounts.print()
    ssc.start()
    ssc.awaitTermination()
  }
}