package com.iservport.install.service

import java.io.InputStream

/**
  * Borrowed from http://alvinalexander.com/scala/scala-adler-32-checksum-algorithm
  */
object CheckSum {

  val MOD_ADLER = 65521

  def adler32sum(s: String): Int = {
    var a = 1
    var b = 0
    s.getBytes().foreach(char => {
      a = (char + a) % MOD_ADLER
      b = (b + a) % MOD_ADLER
    })
    b * 65536 + a
  }

  def checksum(inputStream:InputStream): Unit = {

  }

}
