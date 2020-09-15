/*
 * Licensed to the Apache Software Foundation (ASF) under one
 * or more contributor license agreements.  See the NOTICE file
 * distributed with this work for additional information
 * regarding copyright ownership.  The ASF licenses this file
 * to you under the Apache License, Version 2.0 (the
 * "License"); you may not use this file except in compliance
 * with the License.  You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.apache.wang;

import org.apache.spark.SparkConf;
import org.apache.spark.api.java.JavaSparkContext;
import org.apache.spark.sql.Dataset;
import org.apache.spark.sql.SQLContext;

import java.util.Properties;

public class ParquetTestJava {
    static String url = "jdbc:mysql://localhost:3306/world?" +
            "useUnicode=true&characterEncoding=UTF-8" +
            "&zeroDateTimeBehavior=convertToNull";
    static String table = "jd_trainingdata";
    static String username = "root";
    static String passwd = "root";

    private static void parquetWrite() {
        SparkConf conf = new SparkConf()
                .setAppName("test")
                .setMaster("local")
                .set("spark.executor.memory", "8g")
                .set("spark.rdd.compress true","true")
                .set("spark.testing.memory", "2147480000");
        JavaSparkContext sc = new JavaSparkContext(conf);
        SQLContext sqlContext = new SQLContext(sc);
        sqlContext.setConf("spark.sql.dialect", "sql");
        Properties connectionProperties = new Properties();
        connectionProperties.put("driver","com.mysql.jdbc.Driver");
        connectionProperties.put("user", username);
        connectionProperties.put("password", passwd);
        Dataset vectorTable = sqlContext.read().jdbc(url, table,connectionProperties);
        vectorTable.write().parquet("jdData");// 在项目里生成文件，当然你也可以写绝对路径
    }

    private static void parquetRead() {
        SparkConf conf = new SparkConf()
                .setAppName("test")
                .setMaster("local[4]")
                .set("spark.executor.memory", "8g")
                .set("spark.rdd.compress true","true")
                .set("spark.testing.memory", "2147480000");
        JavaSparkContext sc = new JavaSparkContext(conf);
        SQLContext sqlContext = new SQLContext(sc);
        sqlContext.parquetFile("jdData").registerTempTable("jdData");
        Dataset dbaClos = sqlContext.sql("select * from jdData where Title = 'DBA' and Title <> '' ");
        dbaClos.show();
    }
}
