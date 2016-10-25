package com.oasis.firebird.storage;

import java.util.HashMap;
import java.util.Map;

import com.oasis.firebird.model.BaseEntity;

@Deprecated
public class DaoFactory {
	
	private Map<Class<?>, SQLiteGenericDao<?>> store;
	
	public DaoFactory() {
		
		store = new HashMap<Class<?>, SQLiteGenericDao<?>>();
		
	}
	
	@SuppressWarnings("unchecked")
	public <T> T getDao(Class<T> clazz) {
		
		return (T) store.get(clazz);
		
	}
	
	public <T extends SQLiteGenericDao<? extends BaseEntity>> void add(T dao) {
		
		store.put(dao.getClass().getInterfaces()[0], dao);
		
	}

}
