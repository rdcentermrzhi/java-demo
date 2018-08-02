package com.rdcentermrzhi.java.demo.redis.lock;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.Set;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;
import redis.clients.jedis.Pipeline;
import redis.clients.jedis.Tuple;

public class JedisTool {
	
	private static JedisPool pool;
	
	private static JedisPool getPool(){
		if(pool == null || pool.isClosed()){
			synchronized (JedisTool.class) {
				if(pool == null || pool.isClosed()){
					
						pool = new JedisPool(new JedisPoolConfig(), "localhost", 6379, 3000, "18301527065");
					
				}
			}
		}
		return pool;
	}
	
	
	
	
	/**
	 * 查找所有符合给定模式 pattern 的 key 。
	 * @param pattern
	 * @return
	 */
	public static Set<String> keys(String pattern){
		try (Jedis jedis = getPool().getResource()){
			return jedis.keys(pattern);
		}
	}
	
	/**
	 * 查找所有符合给定模式 pattern 的 key 。
	 * @param pattern
	 * @return
	 */
	public static Set<byte[]> keys(byte[] pattern){
		try (Jedis jedis = getPool().getResource()){
			return jedis.keys(pattern);
		}
	}
	
	/**
	 * 清空redis<br>
	 */
	public static void flushAllAndDb(){
		try (Jedis jedis = getPool().getResource()){
			jedis.flushAll();
			jedis.flushDB();
		}
	}
	
	/**
	 * 设置键值<br>
	 * @param key 键 vpc-0f41486d
	 * @param value 值
	 * @return 设置成功的值("OK")
	 */
	public static String set(String key, String value){
		return set(key, value, -1);
	}
	
	public static String set(String key, String value, int expire){
		try (Jedis jedis = getPool().getResource()){
			boolean e = expire > 0 && !jedis.exists(key);
			String s = jedis.set(key, value);
			if(e){
				jedis.expire(key, expire);
			}
			return s;
		}
	}
	
	/**
	 * 通过键获得值<br>
	 * @param key 键
	 * @return 键对应的值
	 */
	public static String get(String key){
		try (Jedis jedis = getPool().getResource()){
			return jedis.get(key);
		}
	}
	
	public static List<String> mget(String ... keys){
		try (Jedis jedis = getPool().getResource()){
			return jedis.mget(keys);
		}
	}
	
	/**
	 * 删除键值对<br>
	 * @param key 需要删除的键
	 * @return 被删除key的数量
	 */   
	public static long del(String... key){
		try (Jedis jedis = getPool().getResource()){
			return jedis.del(key);
		}
	}
	
	public static void delForPattern(List<String> patterns) {
		for(String pattern : patterns) {
			Set<String> keys = JedisTool.keys(pattern);
			if(keys != null && !keys.isEmpty()) {
				JedisTool.del(keys.toArray(new String[keys.size()]));
			}
		}
	}
	
	/**
	 * 添加一个member元素及其score到有序集key中<br>
	 * @param key 有序集key
	 * @param member
	 * @param score
	 * @return 返回被成功添加的新成员数量（不包括被更新、已存在成员）
	 */
	public static long zadd(String key, String member, Number score){
		return zadd(key, member, score, -1);
	}
	
	/**
	 * 添加一个member元素及其score到有序集key中<br>
	 * @param key 有序集key
	 * @param member
	 * @param score 
	 * @param expire 生命周期（秒）
	 * @return 返回被成功添加的新成员数量（不包括被更新、已存在成员）
	 */
	public static long zadd(String key, String member, Number score, int expire){
		try (Jedis jedis = getPool().getResource()){
			boolean e = expire > 0 && !jedis.exists(key);
			long l = jedis.zadd(key, score.doubleValue(), member);
			if(e){
				jedis.expire(key, expire);
			}
			return l;
		}
	}
	
	/**
	 * 为有序集 key 的成员 member 的 score 值加上增量 increment<br>
	 * @param key
	 * @param member
	 * @param score
	 * @return member 成员的新 score 值
	 */
	public static double zincrby(String key, String member, Number score){
		return zincrby(key, member, score, -1);
	}
	
	/**
	 * 为有序集 key 的成员 member 的 score 值加上增量 increment<br>
	 * @param key
	 * @param member
	 * @param score 
	 * @param expire 生命周期（秒）
	 * @return member 成员的新 score 值
	 */
	public static double zincrby(String key, String member, Number score, int expire){
		try (Jedis jedis = getPool().getResource()){
			boolean e = expire > 0 && !jedis.exists(key);
			double d = jedis.zincrby(key, score.doubleValue(), member);
			if(e && expire > 0){
				jedis.expire(key, expire);
			}
			return d;
		}
	}
	
	/**
	 * 移除有序集 key 中的一个或多个成员，不存在的成员将被忽略<br>
	 * @param key
	 * @param members
	 * @return 被成功移除的成员的数量，不包括被忽略的成员
	 */
	public static long zrem(String key, String ... members){
		try (Jedis jedis = getPool().getResource()){
			long l = jedis.zrem(key, members);
			return l;
		}
	}
	
	public static int zscore(String key, String member){
		try (Jedis jedis = getPool().getResource()){
			Double d = jedis.zscore(key, member);
			return d.intValue();
		}
	}
	
	/**
	 * 返回有序集 key 中，指定区间内的成员<br>
	 * 其中成员的位置按 score 值递减(从大到小)来排列<br>
	 * 具有相同 score 值的成员按字典序的逆序(reverse lexicographical order)排列。<br>
	 * startIndex和endIndex都是以0表示有序集第一个成员, -1表示最后一个成员，-2表示倒数第二个成员
	 * @param key
	 * @param startIndex 开始序号
	 * @param endIndex 结束序号
	 * @return 指定区间内，有序集成员的列表。
	 */
	public static Set<String> zrevrange(String key, Number startIndex, Number endIndex){
		try (Jedis jedis = getPool().getResource()){
			Set<String> set = jedis.zrevrange(key, startIndex.longValue(), endIndex.longValue());
			return set;
		}
	}
	
	/**
	 * 返回有序集 key 中，指定区间内的成员（带score值）<br>
	 * 其中成员的位置按 score 值递减(从大到小)来排列<br>
	 * 具有相同 score 值的成员按字典序的逆序(reverse lexicographical order)排列。<br>
	 * startIndex和endIndex都是以0表示有序集第一个成员, -1表示最后一个成员，-2表示倒数第二个成员
	 * @param key
	 * @param start 开始序号
	 * @param end 结束序号
	 * @return 指定区间内，有序集成员的列表。
	 */
	public static Set<Tuple> zrevrangeWithScores(String key, long start, long end) {
		try (Jedis jedis = getPool().getResource()){
			Set<Tuple> set = jedis.zrevrangeWithScores(key, start, end);
			return set;
		}
	}
	
	/**
	 * 返回有序集 key 中，所有 score 值介于 min 和 max 之间(包括等于 min 或 max )的成员。有序集成员按 score 值递增(从小到大)次序排列。<br>
	 * 具有相同 score 值的成员按字典序(lexicographical order)来排列(该属性是有序集提供的，不需要额外的计算)。<br>
	 * @param key
	 * @param minScore
	 * @param maxScore
	 * @return 有序集 key 中，所有 score 值介于 min 和 max 之间(包括等于 min 或 max )的成员
	 */
	public static Set<String> zrangebyscore(String key, Number minScore, Number maxScore){
		try (Jedis jedis = getPool().getResource()){
			Set<String> set = jedis.zrangeByScore(key, minScore.doubleValue(), maxScore.doubleValue());
			return set;
		}
	}
	
	/**
	 * 返回有序集 key 中，所有 score 值介于 min 和 max 之间(包括等于 min 或 max )的成员。有序集成员按 score 值递增(从大到小)次序排列。<br>
	 * 具有相同 score 值的成员按字典序的逆序(reverse lexicographical order)来排列(该属性是有序集提供的，不需要额外的计算)。<br>
	 * @param key
	 * @param minScore
	 * @param maxScore
	 * @return 有序集 key 中，所有 score 值介于 max 和 min 之间(包括等于 max 或 min )的成员
	 */
	public static Set<String> zrevrangebyscore(String key, Number minScore, Number maxScore){
		try (Jedis jedis = getPool().getResource()){
			Set<String> set = jedis.zrevrangeByScore(key, maxScore.doubleValue(), minScore.doubleValue());
			return set;
		}
	}
	
	/**
	 * 返回有序集 key 中，所有 score 值介于 min 和 max 之间(包括等于 min 或 max )的成员。有序集成员按 score 值递增(从小到大)次序排列。<br>
	 * 具有相同 score 值的成员按字典序(lexicographical order)来排列(该属性是有序集提供的，不需要额外的计算)。<br>
	 * 可选的 LIMIT 参数指定返回结果的数量及区间(就像SQL中的 SELECT LIMIT offset, count )，注意当 offset 很大时，定位 offset 的操作可能需要遍历整个有序集，此过程最坏复杂度为 O(N) 时间。
	 * @param key
	 * @param min
	 * @param max
	 * @param offset
	 * @param count
	 * @return 指定区间内，带有 score 值可选的有序集成员的列表。
	 */
	public static Set<String> zrangebyscore(String key, Number min, Number max, int offset, int count){
		try (Jedis jedis = getPool().getResource()){
			Set<String> set = jedis.zrangeByScore(key, min.doubleValue(), max.doubleValue(), offset, count);
			return set;
		}
	}
	
	/**
	 * 返回有序集 key 中， score 值介于 max 和 min 之间(默认包括等于 max 或 min )的所有的成员。有序集成员按 score 值递减(从大到小)的次序排列。<br>
	 * 具有相同 score 值的成员按字典序的逆序(reverse lexicographical order )排列。<br>
	 * 除了成员按 score 值递减的次序排列这一点外， ZREVRANGEBYSCORE 命令的其他方面和 ZRANGEBYSCORE 命令一样。
	 * @param key
	 * @param min
	 * @param max
	 * @param offset
	 * @param count
	 * @return 指定区间内，带有 score 值的有序集成员的列表。
	 */
	public static Set<String> zrevrangebyscore(String key, Number min, Number max, int offset, int count){
		try (Jedis jedis = getPool().getResource()){
			Set<String> set = jedis.zrevrangeByScore(key, max.doubleValue(), min.doubleValue(), offset, count);
			return set;
		}
	}
	
	/**
	 * 返回有序集 key 中成员 member 的排名。其中有序集成员按 score 值递增(从小到大)顺序排列。<br>
	 * 排名以 0 为底，也就是说， score 值最小的成员排名为 0 。
	 * 使用 ZREVRANK 命令可以获得成员按 score 值递减(从大到小)排列的排名。
	 * @param key
	 * @param member
	 * @return 如果 member 是有序集 key 的成员，返回 member 的排名，否则返回null。
	 */
	public static long zrank(String key, String member){
		try (Jedis jedis = getPool().getResource()){
			if(jedis.exists(key)){
				try{
					Long rank = jedis.zrank(key, member);
					if(rank == null) {
						rank = -1l;
					}
					return rank;
				}catch(Exception e){
					e.printStackTrace();
				
				}
			}
			return -1l;
		}
	}
	
	
	/**
	 * 排序按照从大到小，其它同zrank
	 * @param key
	 * @param member
	 * @return 同zrank
	 */
	public static long zrevrank(String key, String member){
		try (Jedis jedis = getPool().getResource()){
			if(jedis.exists(key)){
				try{
					return jedis.zrevrank(key, member);
				}catch(Exception e){
					//e.printStackTrace();
					//ExceptionUtil.recordException(e, "JedisTool.zrevrank()");
				}
			}
			return -1l;
		}
	}
	
	public static long zcount(String key, Number min, Number max) {
		try (Jedis jedis = getPool().getResource()){
			if(jedis.exists(key)){
				try{
					return jedis.zcount(key, min.doubleValue(), max.doubleValue());
				}catch(Exception e){
					e.printStackTrace();
					
				}
			}
			return 0l;
		}
	}
	
	/**
	 * 将哈希表 key 中的域 field 的值设为 value 。<br>
	 * 如果 key 不存在，一个新的哈希表被创建并进行 HSET 操作。<br>
	 * 如果域 field 已经存在于哈希表中，旧值将被覆盖。
	 * @param key
	 * @param field
	 * @param value
	 * @param expire 生命周期（秒）
	 * @return 如果 field 是哈希表中的一个新建域，并且值设置成功，返回 1 。如果哈希表中域 field 已经存在且旧值已被新值覆盖，返回 0 。
	 */
	public static long hsetForStr(String key, String field, String value, int expire){
		try (Jedis jedis = getPool().getResource()){
			boolean e = expire > 0 && !jedis.exists(key);
			long s = jedis.hset(key, field, value);
			if(e){
				jedis.expire(key, expire);
			}
			return s;
		}
	}
	
	/**
	 * 将哈希表 key 中的域 field 的值设为 value 。<br>
	 * 如果 key 不存在，一个新的哈希表被创建并进行 HSET 操作。<br>
	 * 如果域 field 已经存在于哈希表中，旧值将被覆盖。
	 * @param key
	 * @param field
	 * @param value
	 * @return 如果 field 是哈希表中的一个新建域，并且值设置成功，返回 1 。如果哈希表中域 field 已经存在且旧值已被新值覆盖，返回 0 。
	 */
	public static long hsetForBytes(byte[] key, byte[] field, byte[] value){
		try (Jedis jedis = getPool().getResource()){
			long s = jedis.hset(key, field, value);
			return s;
		}
	}
	
	/**
	 * 返回哈希表 key 中给定域 field 的值。
	 * @param key
	 * @param field
	 * @return 给定域的值。当给定域不存在或是给定 key 不存在时，返回 nil 。
	 */
	public static byte[] hgetForBytes(byte[] key, byte[] field){
		try (Jedis jedis = getPool().getResource()){
			return jedis.hget(key, field);
		}
	}
	
	/**
	 * 删除哈希表 key 中的一个或多个指定域，不存在的域将被忽略。<br>
	 * @param key
	 * @param fields
	 * @return 被成功移除的域的数量，不包括被忽略的域。
	 */
	public static long hdel(String key, String ... fields){
		try (Jedis jedis = getPool().getResource()){
			long l = jedis.hdel(key, fields);
			return l;
		}
	}
	
	/**
	 * 返回哈希表 key 中给定域 field 的值。
	 * @param key
	 * @param field
	 * @return 给定域的值。当给定域不存在或是给定 key 不存在时，返回 nil 。
	 */
	public static byte[] hget(byte[] key, byte[] field) {
		try (Jedis jedis = getPool().getResource()){
			return jedis.hget(key, field);
		}
	}
	
	/**
	 * 将哈希表 key 中的域 field 的值设为 value
	 * @param key 键
	 * @param field 字段
	 * @param value 值
	 * @return 如果 field 是哈希表中的一个新建域，并且值设置成功，返回 1 。<br>如果哈希表中域 field 已经存在且旧值已被新值覆盖，返回 0 。
	 */
	public static long hset(byte[] key, byte[] field, byte[] value) {
		try (Jedis jedis = getPool().getResource()){
			return jedis.hset(key, field, value);
		}
	}
	
	/**
	 * 返回哈希表 key 中给定域 field 的值。
	 * @param key
	 * @param field
	 * @return 给定域的值。当给定域不存在或是给定 key 不存在时，返回 nil 。
	 */
	public static String hget(String key, String field){
		try (Jedis jedis = getPool().getResource()){
			return jedis.hget(key, field);
		}
	}
	
	public static String hmset(String key, Map<String, String> hash) {
		try (Jedis jedis = getPool().getResource()){
			String s = jedis.hmset(key, hash);
			return s;
		}
	}
	
	public static List<String> hmget(String key, String ... fields) {
		try (Jedis jedis = getPool().getResource()){
			return jedis.hmget(key, fields);
		}
	}
	
	/**
	 * 在一段时间内为key加锁
	 * @param key
	 * @param expire 生命周期（秒）
	 * @return 是否枷锁成功
	 */
	/*public static boolean lock(String key, int expire){
		try (Jedis jedis = getPool().getResource()){
			long l = jedis.setnx(key, "1");
			//jedis.set(key, "1", "NX", "PX", expire*1000);
			boolean b = l == 1l;
			if(b && expire > 0){
				jedis.expire(key, expire);
			}
			return b;
		}
	}*/
	
	
	public static boolean lock(String key, int expire,int rd){
		try (Jedis jedis = getPool().getResource()){
			
			return "OK".equals(jedis.set(key, "" +rd, "NX", "PX", expire*1000));
		}
	}
	
	/**
	 * 解锁<br>
	 * @param key
	 */
	public static void unlock(String key,int rd){
	 	if((""+rd).equals(get(key))) {
	 		del(key);
	 	}
	}
	
	/**
	 * 更改key名称<br>
	 * @param oldkey
	 * @param newkey
	 * @return 改名成功时提示 OK ，失败时候返回一个错误。
	 */
	public static String rename(String oldkey, String newkey){
		try (Jedis jedis = getPool().getResource()){
			if(jedis.exists(oldkey)){
				String rslt = jedis.rename(oldkey, newkey);
				return rslt;
			}
			return "FAIL";
		}
	}
	
	/**
	 * key是否存在
	 * @param key
	 * @return 如果存在返回true，反之false
	 */
	public static boolean exists(String key){
		try (Jedis jedis = getPool().getResource()){
			return jedis.exists(key);
		}
	}
	
	/**
	 * 将 key 中储存的数字值增一。<br>
	 * 如果 key 不存在，那么 key 的值会先被初始化为 0 ，然后再执行 INCR 操作。<br>
	 * 本操作的值限制在 64 位(bit)有符号数字表示之内。
	 * @param key
	 * @return 执行 INCR 命令之后 key 的值。
	 */
	public static long incr(String key){
		try (Jedis jedis = getPool().getResource()){
			long l = jedis.incr(key);
			return l;
		}
	}
	
	/**
	 * 将 key 所储存的值加上增量 increment 。<br>
	 * 如果 key 不存在，那么 key 的值会先被初始化为 0 ，然后再执行 INCRBY 命令。
	 * @param key
	 * @param increment
	 * @return 加上 increment 之后， key 的值。
	 */
	public static long incrBy(String key, long increment){
		try (Jedis jedis = getPool().getResource()){
			long l = jedis.incrBy(key, increment);
			return l;
		}
	}
	
	/**
	 * 将 key 所储存的值加上增量 increment 。<br>
	 * 如果 key 不存在，那么 key 的值会先被初始化为 0 ，然后再执行 INCRBY 命令。
	 * @param key
	 * @param increment
	 * @return 加上 increment 之后， key 的值。
	 */
	public static double incrByFloat(String key, float increment ) {
		try (Jedis jedis = getPool().getResource()){
			double l = jedis.incrByFloat(key, increment);
			return l;
		}
	}
	
	/**
	 * 将 key 所储存的值减去减量 decrement 。<br>
	 * 如果 key 不存在，那么 key 的值会先被初始化为 0 ，然后再执行 DECRBY 操作。
	 * @param key
	 * @param decrement
	 * @return 减去 decrement 之后， key 的值。
	 */
	public static long decrBy(String key, long decrement){
		try (Jedis jedis = getPool().getResource()){
			long l = jedis.decrBy(key, decrement);
			return l;
		}
	}
	
	/**
	 * 返回集合 key 中的所有成员。<br>
	 * 不存在的 key 被视为空集合。
	 * @param key
	 * @return 集合中的所有成员。
	 */
	public static Set<String> smembers(String key){
		try (Jedis jedis = getPool().getResource()){
			Set<String> set = jedis.smembers(key);
			return set;
		}
	}
	
	/**
	 * 移除并返回集合中的一个随机元素。<br>
	 * @param key
	 * @return 被移除的随机元素。当 key 不存在或 key 是空集时，返回 nil 。
	 */
	public static String spop(String key){
		try (Jedis jedis = getPool().getResource()){
			String member = jedis.spop(key);
			return member;
		}
	}
	
	/**
	 * 将一个或多个 member 元素加入到集合 key 当中，已经存在于集合的 member 元素将被忽略。《比如》
	 * 假如 key 不存在，则创建一个只包含 member 元素作成员的集合。
	 * @param key
	 * @param members
	 * @return 集合中元素的数量
	 */
	public static long sadd(String key, String... members){
		try (Jedis jedis = getPool().getResource()){
			long l = jedis.sadd(key, members);
//			System.out.println(jedis.spop(key));
			l = jedis.scard(key);
			return l;
		}
	}
	
	
	/**
	 * 返回集合 key 的基数(集合中元素的数量)。
	 * @param key
	 * @return 集合的基数。当 key 不存在时，返回 0 。
	 */
	public static long scard(String key){
		try (Jedis jedis = getPool().getResource()){
			long l = jedis.scard(key);
			return l;
		}
	}
	
	/**
	 * 移除集合 key 中的一个或多个 member 元素，不存在的 member 元素会被忽略。<br>
	 * @param key
	 * @param members
	 * @return 被成功移除的元素的数量，不包括被忽略的元素。
	 */
	public static long srem(String key, String ... members){
		try (Jedis jedis = getPool().getResource()){
			long l = jedis.srem(key, members);
			return l;
		}
	}
	
	/**
	 * 判断 member 元素是否集合 key 的成员。
	 * @param key
	 * @param member
	 * @return 如果是返回true，反之false。
	 */
	public static boolean sismember(String key, String member){
		try (Jedis jedis = getPool().getResource()){
			boolean b = jedis.sismember(key, member);
			return b;
		}
	}
	
	/**
	 * 将一个或多个值 value 插入到列表 key 的表头。<br>
	 * 如果有多个 value 值，那么各个 value 值按从左到右的顺序依次插入到表头： 比如说，对空列表 mylist 执行命令 LPUSH mylist a b c ，列表的值将是 c b a ，这等同于原子性地执行 LPUSH mylist a 、 LPUSH mylist b 和 LPUSH mylist c 三个命令。<br>
	 * 如果 key 不存在，一个空列表会被创建并执行 LPUSH 操作。
	 * @param key
	 * @param strings
	 * @return 执行 LPUSH 命令后，列表的长度。
	 */
	public static long lpush(String key, String... strings){
		try (Jedis jedis = getPool().getResource()){
			return jedis.lpush(key, strings);
		}
	}
	
	public static long lpush(byte[] key, byte[] ... strings) {
		try (Jedis jedis = getPool().getResource()){
			return jedis.lpush(key, strings);
		}
	}
	
	/**
	 * 移除并返回列表 key 的头元素。
	 * @param key
	 * @return 列表的头元素。当 key 不存在时，返回 nil 。
	 */
	public static String lpop(String key){
		try (Jedis jedis = getPool().getResource()){
			return jedis.lpop(key);
		}
	}
	
	/**
	 * 移除并返回列表 key 的头元素。
	 * @param key
	 * @return 列表的头元素。当 key 不存在时，返回 nil 。
	 */
	public static byte[] lpop(byte[] key){
		try (Jedis jedis = getPool().getResource()){
			return jedis.lpop(key);
		}
	}
	
	/**
	 * 移除并返回列表 key 的尾元素。
	 * @param key
	 * @return 列表的尾元素。当 key 不存在时，返回 nil 。
	 */
	public static String rpop(String key){
		try (Jedis jedis = getPool().getResource()){
			return jedis.rpop(key);
		}
	}
	
	/**
	 * 移除表中所有与 value 相等的值。
	 * @param key
	 * @param value
	 * @return 被移除元素的数量。因为不存在的 key 被视作空表(empty list)，所以当 key 不存在时， LREM 命令总是返回 0 。
	 */
	public static long lrem(String key, String value){
		try (Jedis jedis = getPool().getResource()){
			return jedis.lrem(key, 0, value);
		}
	}
	
	/**
	 * 移除count个与value相同的元素
	 * @param key
	 * @param count
	 * @param value
	 * @return 返回移除的数量
	 */
	public static long lrem(String key, long count, String value) {
		try (Jedis jedis = getPool().getResource()){
			return jedis.lrem(key, count, value);
		}
	}
	
	public static String rpoplpush(String srckey, String dstkey) {
		try (Jedis jedis = getPool().getResource()){
			return jedis.rpoplpush(srckey, dstkey);
		}
	}
	
	public static long llen(String key) {
		try (Jedis jedis = getPool().getResource()){
			return jedis.llen(key);
		}
	}
	
	public static List<String> lrange(String key, long start, long end){
		try (Jedis jedis = getPool().getResource()){
			return jedis.lrange(key, start, end);
		}
	}
	
	public static String lindex(String key, long index) {
		try (Jedis jedis = getPool().getResource()){
			return jedis.lindex(key, index);
		}
	}
	
	public static byte[] lindex(byte[] key, long index) {
		try (Jedis jedis = getPool().getResource()){
			return jedis.lindex(key, index);
		}
	}
	
	/**
	 * 将 value 关联到 key 。
	 * @param key
	 * @param value
	 * @return
	 */
	public static String setForBytes(byte[] key, byte[] value){
		try (Jedis jedis = getPool().getResource()){
			return jedis.set(key, value);
		}
	}
	
	/**
	 * 通过bytes键获取bytes值
	 * @param key bytes类型的key
	 * @return 相应的bytes值
	 */
	public static byte[] getForBytes(byte[] key){
		try (Jedis jedis = getPool().getResource()){
			return jedis.get(key);
		}
	}
	
	/**
	 * 删除bytes键对应的bytes值
	 * @param key bytes键
	 * @return 被删除key的数量
	 */
	public static long delForBytes(byte[]... keys){
		try (Jedis jedis = getPool().getResource()){
			return jedis.del(keys);
		}
	}
	
	public static List<Object> pipelineGet(String ... keys){
		try (Jedis jedis = getPool().getResource()){
			Pipeline p = jedis.pipelined();
			for(String key : keys) {
				p.get(key);
			}
			return p.syncAndReturnAll();
		}
	}
	
	public static List<Object> pipelineHmget(String[] fields, String[] keys){
		try (Jedis jedis = getPool().getResource()){
			Pipeline p = jedis.pipelined();
			for(String key : keys) {
				p.hmget(key, fields);
			}
			return p.syncAndReturnAll();
		}
	}
	
	public static String wrapKey(Object ... components) {
		StringBuilder sb = new StringBuilder();
		for(int i = 0; i < components.length; i ++) {
			if(i > 0) {
				sb.append(":");
			}
			sb.append(components[i]);
		}
		return sb.toString();
	}
	
	public static void main(String[] args) {
		
//		incrByFloat("testincr1", 0.5f);
//		incrByFloat("testincr1", 0.3f);
//		incrByFloat("testincr1", 0.1f);
//		incrBy("testincr2", 1);
//		incrBy("testincr2", 2);
//		incrBy("testincr2", 3);
//		
//		List<Object> list = pipelineGet("testincr1", "testincr2");
//		for(Object o : list) {
//			System.out.println(o.getClass().getSimpleName() + "-->" + o);
//		}
		
		
			//通过新版的redis set 来设置超时value 时间
			// 为了让分布式锁的算法更稳键些，持有锁的客户端在解锁之前应该再检查一次自己的锁是否已经超时，再去做DEL操作，因为可能客户端因为某个耗时的操作而挂起，
			//生成随机值  确定rd 
			int rd = new Random().nextInt(1000);
			System.out.println(lock("mylocked1", 10,rd));
		
			System.out.println("");
			unlock("mylocked1",rd);
		
	/*	flushAllAndDb();
		test();
		flushAllAndDb();*/
	}
	
	private static void test() {
//		MJackpot mj = new MJackpot();
//		
//		Map<Integer, JackpotRollingRecord> addRecord = new HashMap<>();
//		JackpotRollingRecord jrr = new JackpotRollingRecord();
//		jrr.setBasicMoney(1000);
//		jrr.setRandomMoney(2000);
//		jrr.setStartTime(System.currentTimeMillis());
//		jrr.setWinMoney(3000);
//		jrr.setWinnerGuid("1024");
//		jrr.setWinTime(System.currentTimeMillis());
//		
//		addRecord.put(1000, jrr);
//		
//		mj.setMachineId(10);
//		mj.setRecordTimes(20);
//		mj.setReturnRatesTotal(21.56);
//		mj.setAddRecord(addRecord);
//		MJackpotRedis.set(mj);
//		
//		MJackpot mj2 = new MJackpot();
//		
//		Map<Integer, JackpotRollingRecord> addRecord2 = new HashMap<>();
//		JackpotRollingRecord jrr2 = new JackpotRollingRecord();
//		jrr2.setBasicMoney(1001);
//		jrr2.setRandomMoney(2001);
//		jrr2.setStartTime(System.currentTimeMillis());
//		jrr2.setWinMoney(3001);
//		jrr2.setWinnerGuid("1025");
//		jrr2.setWinTime(System.currentTimeMillis());
//		
//		addRecord2.put(1000, jrr2);
//		
//		mj2.setMachineId(11);
//		mj2.setRecordTimes(21);
//		mj2.setReturnRatesTotal(22.56);
//		mj2.setAddRecord(addRecord2);
//		MJackpotRedis.set(mj2);
//		
//		String[] fields = {"machineId", "returnRatesTotal", "recordTimes"};
//		Set<String> keys = keys(Joiner.on(':').join("mjackpot", "*"));
//		List<Object> list = pipelineHmget(fields, keys.toArray(new String[keys.size()]));
//		for(Object o : list) {
//			ArrayList<String> l1 = (ArrayList<String>)o;
//			MJackpot m = MJackpotRedis.toMJackpot(l1);
//			System.out.println(m.getMachineId() + " ," + m.getRecordTimes());
//		}
	}
	
}
