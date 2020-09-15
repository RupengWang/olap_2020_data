package org.apache.wang

import org.apache.spark.sql.SparkSession
import org.apache.spark.sql.types._
import org.apache.spark.sql.functions._

object SparkJsonTest {
	def main(args: Array[String]): Unit = {
		val session = SparkSession.builder().appName("OLAP data").master("local[*]").getOrCreate()

		import session.implicits._
		val JsonSchema = new StructType()
				.add($"a".string)
				.add($"b".string)
		val schema = new StructType()
				.add($"encrypted_data".string)
				.add($"decrypted_json".array(JsonSchema))

		val schemaAsJson = schema.json

		val rawJsons = Seq(
			"""
			{
    			"encrypted_data" : "eyJleHAiOjE1",
    			"decrypted_json" : [
      			{
        			"a" : "547.65",
        			"b" : "Some Data"
      			}
    		]
  			}""").toDF("rawjson")

		val people = rawJsons
				.select(from_json($"rawjson", schemaAsJson, Map.empty[String, String]) as "json")
				people.show()
				people.select("json.*") // <-- flatten the struct field
				.withColumn("address", explode($"decrypted_json")) // <-- explode the array field
				.drop("decrypted_json") // <-- no longer needed
				.select("encrypted_data", "address.*") // <-- flatten the struct field
		people.show()
	}
}
