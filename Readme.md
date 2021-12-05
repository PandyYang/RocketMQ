## RocketMQ集群搭建
```
vmware搭建CentOS集群
nat模式
查看网关
vim /etc/sysconfig/network-scripts/ifcfg-ens33 
更改网关
关闭防火墙
保证虚拟机设置的网关和vmware nat模式一致
ip和网关前三段一致
这时候如果主机无法ping通虚拟机
更改vmware8的网络设置 ipv4的ip和网关
设置和vmware的前三段一致

TYPE="Ethernet"
PROXY_METHOD="none"
BROWSER_ONLY="no"
BOOTPROTO="static"
DEFROUTE="yes"
IPV4_FAILURE_FATAL="no"
IPV6INIT="yes"
IPV6_AUTOCONF="yes"
IPV6_DEFROUTE="yes"
IPV6_FAILURE_FATAL="no"
IPV6_ADDR_GEN_MODE="stable-privacy"
NAME="ens33"
UUID="808c5843-d234-49e9-bdcc-83b1bf0ed0bc"
DEVICE="ens33"
ONBOOT="yes"
GATEWAY="192.168.206.2"
IPADDR="192.168.31.132"
DNS="8.8.8.8"
NETMASK=255.255.255.0
PREFIX=24


```

    

    vim /etc/hosts

    # nameserver
    192.168.174.173 rocketmq-nameserver1
    192.168.174.174 rocketmq-nameserver2
    # broker
    192.168.174.173 rocketmq-master1
    192.168.174.173 rocketmq-slave2
    192.168.174.174 rocketmq-master2
    192.168.174.174 rocketmq-slave1

    #set rocketmq
    ROCKETMQ_HOME=/usr/local/rocketmq
    PATH=$PATH:$ROCKETMQ_HOME/bin
    export ROCKETMQ_HOME PATH

    mkdir /usr/local/rocketmq/store
    mkdir /usr/local/rocketmq/store/commitlog
    mkdir /usr/local/rocketmq/store/consumequeue
    mkdir /usr/local/rocketmq/store/index
    
    mkdir /usr/local/rocketmq/store2
    mkdir /usr/local/rocketmq/store2/commitlog
    mkdir /usr/local/rocketmq/store2/consumequeue
    mkdir /usr/local/rocketmq/store2/index

    启动namesrv
    ./mqnamesrv
    
    关闭namesrv
    ./mqshutdown namesrv
    
    启动broker
    ./mqbroker
    
    关闭broker
    ./mqshutdown broker

服务启动

服务启动

1）启动NameServe集群

分别在192.168.25.135和192.168.25.138启动NameServer

    cd /usr/local/rocketmq/bin
    nohup sh mqnamesrv &

2）启动Broker集群

- 在192.168.25.135上启动master1和slave2



master1：

    cd /usr/local/rocketmq/bin
    nohup sh mqbroker -c /usr/local/rocketmq/conf/2m-2s-sync/broker-a.properties &

slave2：

    cd /usr/local/rocketmq/bin
    nohup sh mqbroker -c /usr/local/rocketmq/conf/2m-2s-sync/broker-b-s.properties &

- 在192.168.25.138上启动master2和slave1



master2

    cd /usr/local/rocketmq/bin
    nohup sh mqbroker -c /usr/local/rocketmq/conf/2m-2s-sync/broker-b.properties &

slave1

    cd /usr/local/rocketmq/bin
    nohup sh mqbroker -c /usr/local/rocketmq/conf/2m-2s-sync/broker-a-s.properties &

3.3.11 查看进程状态

    jps

查看日志

    # 查看nameServer日志
    tail -500f ~/logs/rocketmqlogs/namesrv.log
    # 查看broker日志
    tail -500f ~/logs/rocketmqlogs/broker.log
