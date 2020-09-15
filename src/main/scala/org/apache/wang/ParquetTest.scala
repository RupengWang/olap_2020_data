package org.apache.wang

import org.apache.spark.sql.SQLContext
import org.apache.spark.{SparkConf, SparkContext}

object ParquetTest {
	def main(args: Array[String]): Unit = {
		val sc = new SparkContext("local", "Parquet Example", new SparkConf())
		val sqlContext = new SQLContext(sc);
		val data = Seq(("James ","","Smith","36636","M",3000),
			("Michael ","Rose","","40288","M",4000),
			("Robert ","","Williams","42114","M",4000),
			("Maria ","Anne","Jones","39192","F",4000),
			("Jen","Mary","Brown","","F",-1)
		)

	}
}
