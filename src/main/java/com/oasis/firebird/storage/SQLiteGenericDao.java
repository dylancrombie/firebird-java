package com.oasis.firebird.storage;

import java.util.List;

import com.oasis.firebird.model.BaseEntity;

@Deprecated
public interface SQLiteGenericDao<E extends BaseEntity> {
	
	E create(E entity);
	E update(E e);
	void delete(E entity);
	
	List<E> findAll();
	E findById(Long id);

}
