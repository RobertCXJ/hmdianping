-- 比较线程标示与锁中标示是否一致
if(redis.call('get',KEYS[1]) == ARGV[1]) then
    redis.call('del',KEYS[1])
end
return 0