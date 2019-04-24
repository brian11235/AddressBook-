package com.cfm.dao;

import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.stereotype.Repository;

import com.cfm.entity.User;

/**
 * 
 * CRUD implemention
 * @author linbrian
 *
 */
@Repository
public class AddressBookDaoImpl implements AddressBookDao {
	
	ConfigurableApplicationContext ctx;
	
	public AddressBookDaoImpl(){
	}

	public void saveUserData(User user) {
		ctx = new AnnotationConfigApplicationContext(SpringRedisConfig.class);          
        RedisTemplate redisTemplate = (RedisTemplate) ctx.getBean("redisTemplate");
        ValueOperations values = redisTemplate.opsForValue();
        values.set(user.getId(), user);
        ctx.close();
	}

	public User getUserData(String id) {
		ctx = new AnnotationConfigApplicationContext(SpringRedisConfig.class);
        RedisTemplate redisTemplate = (RedisTemplate) ctx.getBean("redisTemplate");
        User user = (User)redisTemplate.opsForValue().get(id);
        ctx.close();
        return user;
	}

	public void updateUserData(String id,User user) {
		ctx = new AnnotationConfigApplicationContext(SpringRedisConfig.class);
        RedisTemplate redisTemplate = (RedisTemplate) ctx.getBean("redisTemplate");
        redisTemplate.opsForValue().getAndSet(id, user);
        ctx.close();
	}

	public void deleteUserData(String id) {
		ctx = new AnnotationConfigApplicationContext(SpringRedisConfig.class);
        RedisTemplate redisTemplate = (RedisTemplate) ctx.getBean("redisTemplate");        
        redisTemplate.delete(id);
        ctx.close();
	}
	
}
