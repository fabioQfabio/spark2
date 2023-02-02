import org.apache.log4j.{Level, Logger}
import org.apache.spark.sql.SparkSession

object Challenges extends App {

  Logger.getLogger("org").setLevel(Level.OFF)
  Logger.getLogger("akka").setLevel(Level.OFF)

  val spark = SparkSession.builder().master("local").appName("SparkTest").getOrCreate()


  val filePathStore = "src/main/resources/googleplaystore.csv"
  val filePathUser = "src/main/resources/googleplaystore_user_reviews.csv"

  val df = spark.read.option("header", true).csv(filePathUser)
  val df_store = spark.read.option("header", true).csv(filePathStore)

}


