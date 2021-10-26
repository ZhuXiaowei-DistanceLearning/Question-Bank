-- 添加一个延迟任务
-- 1.将延迟任务存放到key为 {PROJECT}:REDIS_DELAY_TABLE 的 HASH_TABLE中
-- 2.将TOPIC:ID 作为member ;执行时间戳作为 score  放入 {PROJECT}:RD_ZSET_BUCKET: 的ZSET队列中;


local jobs_key_ht = KEYS[1];
local bucket_key_zset = KEYS[2];
local topic_id = ARGV[1];
local content = ARGV[2];
local score = ARGV[3];

--[[ 命令格式：EVAL script numkeys key [key …] arg [arg …]
     - script参数是一段 Lua5.1 脚本程序。脚本不必(也不应该[^1])定义为一个 Lua 函数
     - numkeys指定后续参数有几个key，即：key [key …]中key的个数。如没有key，则为0
     - key [key …] 从 EVAL 的第三个参数开始算起，表示在脚本中所用到的那些 Redis 键(key)。在Lua脚本中通过KEYS[1], KEYS[2]获取。
     - arg [arg …] 附加参数。在Lua脚本中通过ARGV[1],ARGV[2]获取。 ]]
redis.call('HSET',jobs_key_ht,topic_id,content) ;

redis.call('ZADD',bucket_key_zset,score,topic_id) ;


