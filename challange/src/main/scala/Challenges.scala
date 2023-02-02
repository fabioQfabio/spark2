import org.apache.log4j.{Level, Logger}
import org.apache.spark.sql.SparkSession

object Challenges extends App {

  Logger.getLogger("org").setLevel(Level.OFF)
  Logger.getLogger("akka").setLevel(Level.OFF)

  val spark = SparkSession.builder().master("local").appName("SparkTest").getOrCreate()


  val filePathStore = "src/main/resources/googleplaystore.csv"
  val filePathUser = "src/main/resources/googleplaystore_user_reviews.csv"

  /** Reads both csv files into dataframes.
   *
   * In both files we take the first line as a header to name our Columns.
   * There is no need to specify the delimiter .
   */
  val df = spark.read.option("header", true).csv(filePathUser)
  val df_store = spark.read.option("header", true).csv(filePathStore)



}


