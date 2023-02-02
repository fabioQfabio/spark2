import org.apache.log4j.{Level, Logger}
import org.apache.spark.sql.SparkSession
import org.apache.spark.sql.functions.desc

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



  //df_store.show()
  /**
   * Selects both "App" and "Rating" columns, however, only when the rating is between 4.0 and 5.0.
   * Orders by "Rating" starting from 5.0 and going down .
   */
  val df_best_apps = df_store.select("App","Rating")
                              .where(df_store("Rating") >= 4.0 && df_store("Rating") <= 5.0)
                              .orderBy(desc("Rating"))
  //df_best_apps.show()


  //df_best_apps.write.options(Map("header"->"true", "delimiter"->"$")).csv("src/main/resources/best_apps.csv")
  //df_best_apps.coalesce(1).write.options(Map("header"->"true", "delimiter"->"$")).csv("src/main/resources/best_apps.csv")


}


