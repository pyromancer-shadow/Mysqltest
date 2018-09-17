package top.sulpures.testsql

import java.lang.StringBuilder
import java.util.ServiceLoader
import scala.collection.JavaConversions._
import scala.util.Random


/**
  * Created by shadow on 2018/8/27.
  */
class DataFactory(fields: Array[(String, String)]) {

  val loader = Thread.currentThread().getContextClassLoader
  val serviceLoader = ServiceLoader.load(classOf[DataType], loader)
  println(serviceLoader.iterator().hasNext)
  val getClasss = (name: String, args: String) => {
    val classs = serviceLoader.toIterable.filter(_.name == name)
    classs.toIterator.next().getClass
  }

  val datas = fields.map{item =>
//    val dataClass = getClasss(item._1, item._2)
//    dataClass.newInstance()
    val dataType = item._1 match {
      case "varchar" => new Varchar()
      case "int" => new int()
      case "male" => new Male()
      case "address" => new Address()
    }
    dataType.initArgs(item._2.split(","))
    dataType
  }

  def next(): Array[String] ={
    datas.map(_.next())
  }

}

class Varchar extends DataType {
  override val name: String = "varchar"

  private var length = 0

  override def next(): String = {
    val sb = new StringBuilder("'")
    for (i <- 0.until(length)){
      val c = (Random.nextInt(26)+97).toChar
      sb.append(c)
    }
    sb.append("'")
    sb.toString
  }

  override def initArgs(args: Array[String]): Unit = {
    super.initArgs(args)
    length = args(0).toInt
  }
}

class int extends DataType{
  override val name: String = "int"

  private var start = 0
  private var end = 0
  private var length = 0

  override def next(): String = {
    (Random.nextInt(length) + start).toString
  }

  override def initArgs(args: Array[String]): Unit = {
    super.initArgs(args)
    start = args(0).toInt
    end = args(1).toInt
    length = end - start
  }
}

class Male extends DataType{
  override val name: String = "male"

  override def next(): String = {
    if(Random.nextBoolean()){
      "'m'"
    } else {
      "'f'"
    }
  }
}

class Address extends DataType{
  override val name: String = "address"

  val string = new Varchar
  string.initArgs(Array("15"))

  private val address = Array("hebei", "henan", "shandong", "shanxi", "beijing", "tianjin", "heilongjiang",
  "shanghai", "zhejiang", "jiangsu", "guangdong", "guangxi", "guizhou", "sichuan", "hefei")

  override def next(): String = {
    "'" + address(Random.nextInt(15)) + string.next().replace('\'','w') + "'"
  }
}