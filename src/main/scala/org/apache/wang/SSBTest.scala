package org.apache.wang

import org.apache.spark.sql.SparkSession
import org.apache.spark.{SparkConf, SparkContext}

object SSBTest {
	def main(args: Array[String]): Unit = {
		val spark = SparkSession.builder().master("local").enableHiveSupport().getOrCreate()
		spark.sql("use ssb")
		val tables = spark.sql("show tables")
		tables.show()
		//val table = spark.sql("select * from line_order join customer on liner_oder.customer = customer.customer")
		//val customer = spark_table.sql("select * from customer")
		//line_order.repartition(10).write.parquet("");
	}
}
