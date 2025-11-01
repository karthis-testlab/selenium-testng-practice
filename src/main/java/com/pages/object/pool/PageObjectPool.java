package com.pages.object.pool;

import java.lang.reflect.Constructor;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.LinkedBlockingQueue;

public class PageObjectPool {
	
	private final static int DEFAULT_MAX_POOL_SIZE = 5;
	// Maintain separate queues for each page class
	private static final Map<Class<?>, LinkedBlockingQueue<Object>> poolMap = new ConcurrentHashMap<>();

	// Create a new instance using reflection
	private static <T> T createPageInstance(Class<T> pageClass) {
		try {
			Constructor<T> constructor = pageClass.getDeclaredConstructor();
			constructor.setAccessible(true); // In case constructor is private
			return constructor.newInstance();
		} catch (Exception e) {
			throw new RuntimeException("Failed to create page instance for " + pageClass.getSimpleName(), e);
		}

	}
	
	/**
	 * Retrieves an object from the pool if available, or creates a new one if under
	 * max limit.
	 */
	public static <T> T getObject(Class<T> pageClass) {
		LinkedBlockingQueue<Object> queue = poolMap.computeIfAbsent(pageClass,
				k -> new LinkedBlockingQueue<>(DEFAULT_MAX_POOL_SIZE));

		synchronized (queue) {

			// 1. If there is an existing idle object, reuse it
			Object instance = queue.poll(); // Take object if available
			if (instance != null) {
				System.out.println("Reusing existing instance of: " + pageClass.getSimpleName());
				return pageClass.cast(instance);
			}

			// 2. If the pool is not full, create and add a new instance
			if (queue.size() < DEFAULT_MAX_POOL_SIZE) {
				T newInstance = createPageInstance(pageClass);
				queue.offer(newInstance); // Add to pool for future reuse
				System.out.println("Created and added new instance of: " + pageClass.getSimpleName());
				return newInstance;
			}

			// 3. If pool is full, return the oldest instance (round-robin style)
			Object reused = queue.poll();
			if (reused != null) {
				queue.offer(reused); // Put it back to maintain pool rotation
				System.out.println("Pool is full. Returning oldest instance of: " + pageClass.getSimpleName());
				return pageClass.cast(reused);
			}

			throw new RuntimeException("Max pool size reached for page type: " + pageClass.getSimpleName());

		}

	}
	
	/**
	 * Returns an object back to the pool for reuse.
	 */
	public static <T> void releaseObject(Class<T> pageClass, T pageObject) {

		LinkedBlockingQueue<Object> queue = poolMap.computeIfAbsent(pageClass,
				k -> new LinkedBlockingQueue<>(DEFAULT_MAX_POOL_SIZE));

		synchronized (queue) {
			if (!queue.offer(pageObject)) {
				System.out.println("Pool is full, discarding object of: " + pageClass.getSimpleName());
			} else {
				System.out.println("Object returned to pool: " + pageClass.getSimpleName());
			}
		}

	}

}