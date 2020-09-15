package org.apache.wang.olap

import org.apache.spark.sql.{DataFrame, SparkSession}
import org.apache.spark.sql.functions.{col, explode, from_json}
import org.apache.spark.sql.types._

object OlapData {
	def main(args: Array[String]): Unit = {
		val session = SparkSession.builder().appName("OLAP data").master("local[*]").enableHiveSupport().getOrCreate()
		//val profile = processProfile("file:///Users/rupeng.wang/Kyligence/Developments/IntelliJProjects/SparkExample/src/resources/olap_2020_profile_test.dat", session)
		//profile.createOrReplaceTempView("profileTempTable")

		session.sql("use olap_2020")
		/*session.sql("drop table if exists olap_2020_profile_test")
		session.sql("create table olap_2020_profile_test as select * from profileTempTable")
		println("Profile done!")*/

		val event = processEvent("file:///Users/rupeng.wang/Kyligence/Developments/IntelliJProjects/SparkExample/src/resources/olap_2020_event_test.dat", session)
		event.createOrReplaceTempView("eventTempTable")
		session.sql("drop table if exists olap_2020_event_test")
		session.sql("create table olap_2020_event_test as select * from eventTempTable")
	}

	def processProfile(path: String, session: SparkSession): DataFrame = {
		val sc = session.sparkContext
		val textFile = sc.textFile(path)
		val dataFrame = session.createDataFrame(textFile.map(_.split("\t")).map(row => ProfileDat(row(0).toLong, row(1))))

		import session.implicits._

		val schemaDetail = new StructType()
				.add($"age".decimal(18,3))
				.add($"gender".string)
				.add($"email".string)
				.add($"lib".string)
				.add($"lib_version".string)
				.add($"total_visit_days".decimal(18,3))
				.add($"vip_level".string)
				.add($"total_amount".decimal(18,3))
				.add($"fq1".int)
				.add($"fq2".int)
				.add($"fq3".int)
				.add($"fq4".int)
				.add($"fq5".int)
				.add($"fq6".int)
				.add($"fq7".int)
				.add($"fq8".int)
				.add($"fq9".int)
				.add($"fq10".int)
				.add($"fq11".int)
				.add($"fq12".int)
				.add($"fq13".int)
				.add($"fq14".int)
				.add($"fq15".int)
				.add($"fq16".int)
				.add($"fq17".int)
				.add($"fq18".int)
				.add($"fq19".int)
				.add($"fq20".int)
				.add($"fq21".int)
				.add($"fq22".int)
				.add($"fq23".int)
				.add($"fq24".int)
				.add($"fq25".int)

		val profileDetail = dataFrame.withColumn("detailJson", from_json($"detailJson", schemaDetail))
		val profile = profileDetail.select($"distinct_id",
			$"detailJson".getItem("age").alias("age"),
			$"detailJson".getItem("gender").alias("gender"),
			$"detailJson".getItem("email").alias("email"),
			$"detailJson".getItem("lib").alias("lib"),
			$"detailJson".getItem("lib_version").alias("lib_version"),
			$"detailJson".getItem("total_visit_days").alias("total_visit_days"),
			$"detailJson".getItem("vip_level").alias("vip_level"),
			$"detailJson".getItem("total_amount").alias("total_amount"),
			$"detailJson".getItem("fq1").alias("fq1"),
			$"detailJson".getItem("fq2").alias("fq2"),
			$"detailJson".getItem("fq3").alias("fq3"),
			$"detailJson".getItem("fq4").alias("fq4"),
			$"detailJson".getItem("fq5").alias("fq5"),
			$"detailJson".getItem("fq6").alias("fq6"),
			$"detailJson".getItem("fq7").alias("fq7"),
			$"detailJson".getItem("fq8").alias("fq8"),
			$"detailJson".getItem("fq9").alias("fq9"),
			$"detailJson".getItem("fq10").alias("fq10"),
			$"detailJson".getItem("fq11").alias("fq11"),
			$"detailJson".getItem("fq12").alias("fq12"),
			$"detailJson".getItem("fq13").alias("fq13"),
			$"detailJson".getItem("fq14").alias("fq14"),
			$"detailJson".getItem("fq15").alias("fq15"),
			$"detailJson".getItem("fq16").alias("fq16"),
			$"detailJson".getItem("fq17").alias("fq17"),
			$"detailJson".getItem("fq18").alias("fq18"),
			$"detailJson".getItem("fq19").alias("fq19"),
			$"detailJson".getItem("fq20").alias("fq20"),
			$"detailJson".getItem("fq21").alias("fq21"),
			$"detailJson".getItem("fq22").alias("fq22"),
			$"detailJson".getItem("fq23").alias("fq23"),
			$"detailJson".getItem("fq24").alias("fq24"),
			$"detailJson".getItem("fq25").alias("fq25"))

		profile
	}

	def processEvent(path: String, session: SparkSession): DataFrame = {
		val sc = session.sparkContext
		val textFile = sc.textFile(path)
		val dataFrame = session.createDataFrame(textFile.map(_.split("\t")).map(row => EventDat(row(0).toLong, row(1).toLong, row(2), row(3).toInt, row(4), row(5).toInt, row(0).toLong)))

		import session.implicits._
		val schemaDetail = new StructType()
				.add($"url".string)
				.add($"title".string)
				.add($"traffic_source_type".string)
				.add($"os".string)
				.add($"os_version".string)
				.add($"country".string)
				.add($"province".string)
				.add($"city".string)
				.add($"app_version".string)
				.add($"firstcommodity".string)
				.add($"secondcommodity".string)
				.add($"commodityname".string)
				.add($"price".decimal(18,3))
				.add($"loginmethod".string)
				.add($"signupmethod".string)
				.add($"bannername".string)
				.add($"bannerposition".string)
				.add($"deliverymethod".string)
				.add($"ifusediscount".int)
				.add($"ifuselntegral".int)
				.add($"numberoflntegral".decimal(18,3))
				.add($"orderamount".decimal(18,3))
				.add($"discountamount".decimal(18,3))
				.add($"commoditynumber".decimal(18,3))
				.add($"hasresult".int)
				.add($"isrecommend".int)
				.add($"keyword".string)
				.add($"receivername".string)
				.add($"transportationcosts".decimal(18,3))
				.add($"sharemethod".string)
				.add($"storename".string)
				.add($"discountname".string)
				.add($"duration".decimal(18,3))
				.add($"commodityscore".decimal(18,3))
				.add($"invitationmethod".string)
				.add($"questiontype".string)

		val eventDetail = dataFrame.withColumn("eventJson", from_json($"eventJson", schemaDetail))
		val event = eventDetail.select($"distinct_id",
			$"xwhen",$"xwhat",$"xwhat_id",
			$"eventJson".getItem("url").alias("url"),
			$"eventJson".getItem("title").alias("title"),
			$"eventJson".getItem("traffic_source_type").alias("traffic_source_type"),
			$"eventJson".getItem("os").alias("os"),
			$"eventJson".getItem("os_version").alias("os_version"),
			$"eventJson".getItem("country").alias("country"),
			$"eventJson".getItem("province").alias("province"),
			$"eventJson".getItem("city").alias("city"),
			$"eventJson".getItem("app_version").alias("app_version"),
			$"eventJson".getItem("firstcommodity").alias("firstcommodity"),
			$"eventJson".getItem("secondcommodity").alias("secondcommodity"),
			$"eventJson".getItem("commodityname").alias("commodityname"),
			$"eventJson".getItem("price").alias("price"),
			$"eventJson".getItem("loginmethod").alias("loginmethod"),
			$"eventJson".getItem("signupmethod").alias("signupmethod"),
			$"eventJson".getItem("bannername").alias("bannername"),
			$"eventJson".getItem("bannerposition").alias("bannerposition"),
			$"eventJson".getItem("deliverymethod").alias("deliverymethod"),
			$"eventJson".getItem("ifusediscount").alias("ifusediscount"),
			$"eventJson".getItem("ifuselntegral").alias("ifuselntegral"),
			$"eventJson".getItem("numberoflntegral").alias("numberoflntegral"),
			$"eventJson".getItem("orderamount").alias("orderamount"),
			$"eventJson".getItem("discountamount").alias("discountamount"),
			$"eventJson".getItem("commoditynumber").alias("commoditynumber"),
			$"eventJson".getItem("hasresult").alias("hasresult"),
			$"eventJson".getItem("isrecommend").alias("isrecommend"),
			$"eventJson".getItem("keyword").alias("keyword"),
			$"eventJson".getItem("receivername").alias("receivername"),
			$"eventJson".getItem("transportationcosts").alias("transportationcosts"),
			$"eventJson".getItem("sharemethod").alias("sharemethod"),
			$"eventJson".getItem("storename").alias("storename"),
			$"eventJson".getItem("discountname").alias("discountname"),
			$"eventJson".getItem("duration").alias("duration"),
			$"eventJson".getItem("commodityscore").alias("commodityscore"),
			$"eventJson".getItem("invitationmethod").alias("invitationmethod"),
			$"eventJson".getItem("questiontype").alias("questiontype"),
			$"ds",
			$"distinct_id2")

		event
	}
}
