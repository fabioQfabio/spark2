# Spark 2 Recruitment Challenge

## index
* [What it is](#what-it-is)
* [Notes](#exercises)
* [More Notes](#more-notes)

## What it is
A challenge to develop a Spark 2 application (in Scala) that processes 2 CSV files (present in challange/src/main/resources/)

## Exercises
### Part 1
Filters only rows with valid "Sentiment_Polarity" values
Groups by App name, calculating de average of "Sentiment_Polarity", renaming it "Average_Sentiment_Polarity"
```sh
val df_1 = df.where(col("Sentiment_Polarity").cast("Double").isNotNull && isnan(col("Sentiment_Polarity") ) === false)
                .groupBy("App").agg(mean("Sentiment_Polarity").alias("Average_Sentiment_Polarity"))
                .sort("App")
```
### Part 2
Selects both "App" and "Rating" columns, however, only when the rating is between 4.0 and 5.0
Orders by "Rating" starting from 5.0 and going down
```sh
val df_best_apps = df_store.select("App","Rating")
                              .where(df_store("Rating") >= 4.0 && df_store("Rating") <= 5.0)
                              .orderBy(desc("Rating"))
```
Writes in a csv file, puts the header in the first line, and sets delimiter "$"
(use coalesce(1) to only write in 1 fragment)
```sh
df_best_apps.write.options(Map("header"->"true", "delimiter"->"$")).csv("src/main/resources/best_apps.csv")
df_best_apps.coalesce(1).write.options(Map("header"->"true", "delimiter"->"$")).csv("src/main/resources/best_apps.csv")
```
### Part 3
Makes App an unique value, putting all categories in a list and selecting the max value of reviews.
Then it joins with the original df.
```sh
val df_3_category = df_store.groupBy("App")
    .agg(
      collect_set("Category").alias("Categories"),
      max("Reviews").alias("Reviews")
    )

val df_3 = df_store.join(df_3_category, Seq("App","Reviews"),"inner").drop("Category").sort("App")
```
```sh
TODO MORE
```
### Part 4
Joins df_1 and df_3 and saves in a parquet file.
```sh
val df_3and1 = df_1.join(df_3,df_1("App") ===  df_3("App"),"outer")
df_3and1.write.parquet("src/main/resources/googleplaystore_cleaned")
```
### Part 5
```sh
TODO
```
_______________________________________________________________

### More Notes
TODO

