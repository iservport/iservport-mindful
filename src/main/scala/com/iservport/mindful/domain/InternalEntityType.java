package com.iservport.mindful.domain;

class InternalEntityType(var value: Char) extends KeyNameAdapter {

    def getKey: Serializable = {
      return this.value
    }

    def getCode: String = {
      return value + ""
    }

    def getName: String = {
      return name
    }
  }