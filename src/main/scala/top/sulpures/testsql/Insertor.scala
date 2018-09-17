package top.sulpures.testsql

import java.sql.{Connection, DriverManager}
import java.util.Date

/**
  * Created by shadow on 2018/8/27.
  */
class Insertor(val table: String, fields: Array[String], dataFactory: DataFactory) {

  val sqlBasic = getSql()
  Class.forName("com.mysql.cj.jdbc.Driver")
  val conn: Connection = DriverManager.getConnection("jdbc:mysql://192.168.141.137:3306/test", "test", "zong1234")
  var middleData: Array[String] = null
  var time: Long = 0
  def execute(num: Int): Unit ={
    val middle: Int = num/2
    val startTime = new Date()

    for (i <- 0.until(num)){
      val data = insert()
      if (i == middle){
        middleData = data
      }
    }

    val endTime = new Date()
    val millisecond = (endTime.getTime - startTime.getTime)
    time = millisecond
    println(startTime)
    println(endTime)
    println(num)
    println("执行时间：" + millisecond)
  }

  def insert(): Array[String] ={
    val data = dataFactory.next()
    val sb = new StringBuilder(sqlBasic)
    sb ++= " values("
    data.foreach(item => sb ++= (item + ","))
    sb.deleteCharAt(sb.length - 1)
    sb += ')'
    val sql = sb.mkString

    import java.sql.SQLException
    try { //加载驱动程序
      val statement = conn.createStatement

      val rs = statement.executeUpdate(sql)

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

    data
  }



  private def getSql(): String ={
    val sb = new StringBuilder(s"insert into $table(")
    fields.foreach(field => sb ++= (field + ","))
    sb.deleteCharAt(sb.length - 1)
    sb += ')'
    sb.mkString
  }
}
