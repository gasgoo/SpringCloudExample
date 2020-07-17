//第一个参数变量 从外部传入的参数
local key=KEYS[1]
local limit =tonumber(ARGV[1])
local expire_time =ARGV[2]

//执行exists命令
local is_exists=redis.call('EXISTS',key)
if is_exists ==1 then
    if redis.call('INCR',key)>limit then
        return 0
    else
        return 1
    end
else
    redis.call('SET',key,1)
    redis.call('EXPIRE',key,expire_time)
    return 1
end

