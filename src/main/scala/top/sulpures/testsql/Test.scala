package top.sulpures.testsql

import java.io.{File, FileWriter}

import scala.util.Random

/**
  * Created by shadow on 2018/8/20.
  */
object Test {

  def main(args: Array[String]): Unit = {
    val fields = Array("name", "male", "age", "address")
    val fieldsType = Array(("varchar", "20"), ("male",""), ("int", "0,100"), ("address", "30"))
    val dataFactory = new DataFactory(fieldsType)
    val userI = new Insertor("user", fields, dataFactory)
    val user2I = new Insertor("user2", fields, dataFactory)
    val user3I = new Insertor("user3", fields, dataFactory)
    val userIs = Array(userI, user2I, user3I)

    for (num <- Array(1000, 10000, 100000, 1000000)){
      val file = new File(s"C:\\Users\\shadow\\Desktop\\测试$num")
      val out = new FileWriter(file)
      userIs.foreach{user =>
        user.execute(num)
        out.write(user.time.toString)
        out.write("\n\r")
      }

      val userQs = userIs.map(user => new Selector(user.table, user.middleData))
      userQs.foreach(user => user.quary())
      val sqls = userQs(0).sqls
      for (i <- 0.until(sqls.length)){
        println()
        println(sqls(i))
        out.write(sqls(i))
        out.write("\n\r")
        userQs.foreach{user =>
          println(user.times(i))
          out.write(user.times(i).toString)
          out.write("\n\r")
        }
      }

      userIs.foreach{user =>
        user.execute(100)
        out.write(user.time.toString)
        out.write("\n\r")
      }
      out.flush()
      out.close()
    }

  }

}
