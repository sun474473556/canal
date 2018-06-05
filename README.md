# canal

<!-- README.md -->
# 说明

------

### 相关字段说明
字段名|说明
-|-
  canal.type | canal server模式 Single和Cluster
  canal.address       | single模式下为canal server地址，cluster模式下为zookeeper地址
  canal.port | 同上
  canal.dbName | 订阅的Database name
  canal.destination | 订阅的destination，多个用；隔开，每个destination都需要指明订阅的表名，例：example,user;example1,pack。 说明：该字段对应server端的canal.instance.filter.regex配置，但是server端的这个配置实际上是没有用的，应该是个bug，所以需要在客户端配置。
  service.type | 选择由mysql、redis、rabbitmq消费binlog


### 考虑更新
  DTO中Field考虑解析binlog方式更换
  

