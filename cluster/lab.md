# 1. Create the configuration files for the Redis instances

mkdir 7000 7001 7002 7003 7004 7005

touch 7000/redis.conf 7001/redis.conf 7002/redis.conf 7003/redis.conf 7004/redis.conf 7005/redis.conf

echo "port 7000
cluster-enabled yes
cluster-config-file nodes.conf
cluster-node-timeout 5000
appendonly yes" > 7000/redis.conf

echo "port 7001
cluster-enabled yes
cluster-config-file nodes.conf
cluster-node-timeout 5000
appendonly yes" > 7001/redis.conf

echo "port 7002
cluster-enabled yes
cluster-config-file nodes.conf
cluster-node-timeout 5000
appendonly yes" > 7002/redis.conf

echo "port 7003
cluster-enabled yes
cluster-config-file nodes.conf
cluster-node-timeout 5000
appendonly yes" > 7003/redis.conf

echo "port 7004
cluster-enabled yes
cluster-config-file nodes.conf
cluster-node-timeout 5000
appendonly yes" > 7004/redis.conf

echo "port 7005
cluster-enabled yes
cluster-config-file nodes.conf
cluster-node-timeout 5000
appendonly yes" > 7005/redis.conf

# 2. Start the Redis instances

cd 7000
redis-server redis.conf

cd 7001
redis-server redis.conf

cd 7002
redis-server redis.conf

cd 7003
redis-server redis.conf

cd 7004
redis-server redis.conf

cd 7005
redis-server redis.conf

# 3. Create the Redis cluster

redis-cli --tls --cacert /Users/nag/wl-redis/tls/ca.crt --cert /Users/nag/wl-redis/tls/redis-client.crt --key /Users/nag/wl-redis/tls/redis-client.key --cluster create 127.0.0.1:7000 127.0.0.1:7001 \
127.0.0.1:7002 127.0.0.1:7003 127.0.0.1:7004 127.0.0.1:7005 \
--cluster-replicas 1

# 4. Verify the Redis cluster

redis-cli -c -p 7000 cluster info

# 5. Interact with the cluster using the redis-cli tool.

redis-cli -c -p 7000

# 6. Test the cluster by setting and getting values.

set foo bar
set hello world

get foo
get hello

cd redis-rb-cluster
ruby ./example.rb

Reshard the cluster

redis-cli --cluster reshard 127.0.0.1:7000
redis-cli --cluster check 127.0.0.1:7000

--

ruby consistency-test.rb

redis-cli -p 7000 cluster nodes | grep master

redis-cli -p 7002 debug segfault
redis-cli -p 7000 cluster nodes

--

Add a new node

redis-cli --cluster add-node 127.0.0.1:7006 127.0.0.1:7000

redis 127.0.0.1:7006> cluster nodes

Add a new node as a replica

redis-cli --cluster add-node 127.0.0.1:7006 127.0.0.1:7000 --cluster-slave

redis-cli --cluster add-node 127.0.0.1:7006 127.0.0.1:7000 --cluster-slave --cluster-master-id 3c3a0c74aae0b56170ccb03a76b60cfe7dc1912e

redis 127.0.0.1:7006> cluster replicate 3c3a0c74aae0b56170ccb03a76b60cfe7dc1912e

redis-cli --cluster del-node 127.0.0.1:7000 `<node-id>`
