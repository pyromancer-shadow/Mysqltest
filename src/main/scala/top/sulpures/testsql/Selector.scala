package top.sulpures.testsql

import java.sql.{Connection, DriverManager, SQLException}
import java.util.Date

import scala.collection.mutable.ArrayBuffer

/**
  * Created by shadow on 2018/8/27.
  */
class Selector(table: String, data: Array[String]) {
  val sqls = Array(
    s"select * from $table where name="+data(0),
    s"select * from $table where name like "+(data(0).substring(0, 3))+ "%'",
    s"select name from $table where name like "+(data(0).substring(0, 3))+ "%'",
    s"select name from $table where name like "+(data(0).substring(0, 3))+ "%'" + " order by name",
    s"select * from $table where age = 51 limit 100",
    s"select * from $table where age > 47 and age < 52 limit 100",
    s"select * from $table where age > 47 and age < 52 and address  like 'beijing%' limit 100",
    s"select * from $table where age > 47 and age < 52 and address=${data(3)} limit 100",
    s"select * from $table where address  like  'beijing%' and  age > 47 and age < 52  limit 100",
    s"select * from $table where age =52 and address  like  'beijing%' limit 100",
    s"select * from $table where address like 'beijing%'  limit 100",
    s"select * from $table where male = 'f' and age > 47 and age < 52 limit 100"
  )
  val times = new ArrayBuffer[Long]()

  val conn: Connection = DriverManager.getConnection("jdbc:mysql://192.168.141.137:3306/test", "test", "zong1234")

  def quary(): Unit ={
    for (sql <- sqls) {
      val startTime = new Date
      for (i <- 0.until(100)){
        try { //加载驱动程序
          val statement = conn.createStatement

          val rs = statement.executeQuery(sql)

        } catch {
          case e: ClassNotFoundException =>
            //数据库驱动类异常处理
            System.out.println("Sorry,can`t find the Driver!")
            e.printStackTrace()
          case e: SQLException =>
            //数据库连接失败异常处理
            e.printStackTrace()
          case e: Exception =>
            // TODO: handle exception
            e.printStackTrace()
        }
      }
      val endTime = new Date()
      val millisecond = (endTime.getTime - startTime.getTime)
      times += millisecond

    }
  }

}
