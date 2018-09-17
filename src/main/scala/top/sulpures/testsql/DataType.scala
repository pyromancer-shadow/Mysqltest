package top.sulpures.testsql

/**
  * Created by shadow on 2018/8/27.
  */
abstract class DataType {

  protected var args: Array[String] = null

  // 数据类型的名字
  val name: String

  // 生成一条数据
  def next(): String

  def initArgs(args: Array[String]): Unit ={
    this.args = args
  }

}
