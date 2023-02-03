import org.apache.log4j.{Level, Logger}
import org.apache.spark.sql.SparkSession
//import org.apache.spark.sql.functions.desc
import org.apache.spark.sql.functions._

object Challenges extends App {

  Logger.getLogger("org").setLevel(Level.OFF)
  Logger.getLogger("akka").setLevel(Level.OFF)

  val spark = SparkSession.builder().master("local").appName("SparkTest").getOrCreate()

  /**
    * Reads both csv files into dataframes.
    */
  val filePathStore = "src/main/resources/googleplaystore.csv"
  val filePathUser = "src/main/resources/googleplaystore_user_reviews.csv"

  /**
   * In both files we take the first line as a header to name our Columns.
   * There is no need to specify the delimiter .
   */
  val df = spark.read.option("header", true).csv(filePathUser)
  val df_store = spark.read.option("header", true).csv(filePathStore)


  //-------------------------------------------------------------------------------------------
  // ------------------------------------------------------------------------------------------
  //-------------------------------------------------------------------------------------------

  //df.show()

  /** Part 1
   * Filters only rows with valid "Sentiment_Polarity" values
   * Groups by App name, calculating de average of "Sentiment_Polarity", renaming it "Average_Sentiment_Polarity".
   */
  val df_1 = df.where(col("Sentiment_Polarity").cast("Double").isNotNull && isnan(col("Sentiment_Polarity") ) === false)
                .groupBy("App").agg(mean("Sentiment_Polarity").alias("Average_Sentiment_Polarity"))
                .sort("App")

  //df_1.show()

  //-------------------------------------------------------------------------------------------
  // ------------------------------------------------------------------------------------------
  //-------------------------------------------------------------------------------------------


  //df_store.show()
  /** Part 2
   * Selects both "App" and "Rating" columns, however, only when the rating is between 4.0 and 5.0.
   * Orders by "Rating" starting from 5.0 and going down .
   */
  val df_best_apps = df_store.select("App","Rating")
                              .where(df_store("Rating") >= 4.0 && df_store("Rating") <= 5.0)
                              .orderBy(desc("Rating"))
  //df_best_apps.show()

  /**
   * Writes in a csv file, puts the header in the first line, and sets delimiter "$"
   * (use coalesce(1) to only write in 1 fragment)
   */
  //df_best_apps.write.options(Map("header"->"true", "delimiter"->"$")).csv("src/main/resources/best_apps.csv")
  //df_best_apps.coalesce(1).write.options(Map("header"->"true", "delimiter"->"$")).csv("src/main/resources/best_apps.csv")


}


