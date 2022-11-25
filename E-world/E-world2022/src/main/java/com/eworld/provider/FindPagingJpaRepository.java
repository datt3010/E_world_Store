package com.eworld.provider;

import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;

import org.springframework.core.GenericTypeResolver;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

import com.eword.util.StringBuilderUtil;

public interface FindPagingJpaRepository<T> {

	EntityManager getEntityManager();
	
	default String makeOrderClause(Sort sort, Collection<String> sortableFields, String tableAlias) {
		String orderClause = "";
		if(sort != null && sort.isSorted()) {
			 String order = sort.stream()
					 .filter(e -> sortableFields.contains(e.getProperty()))
					 .map(e -> StringBuilderUtil.append(new StringBuilder(), tableAlias, ".", e.getProperty(), " ", e.getDirection().toString()))
					 .collect(Collectors.joining(", "));
			 orderClause = " ORDER BY " +order;
		}
		return orderClause;
	}
	
	default Page<T> findPaging(String countSql, String selectSql, Pageable pagebale, Map<String, Object> parameterMap){
		
		TypedQuery<Long> countQuery = getEntityManager().createQuery(countSql, Long.class);
		setParameters(countQuery, parameterMap);
		Long total = countQuery.getSingleResult();
		
		if(total ==0) {
			return new PageImpl<>(Collections.emptyList(), pagebale, 0);
		}
		
		TypedQuery<T> pagingQuery = getEntityManager().createQuery(selectSql, getDomainClass())
				.setFirstResult((int) pagebale.getOffset())
				.setMaxResults(pagebale.getPageSize());
		
		setParameters(pagingQuery, parameterMap);
		List<T> entities = pagingQuery.getResultList();
		return new PageImpl<>(entities, pagebale, total);
	}
	
	private void setParameters(TypedQuery<?> query, Map<String, Object> parameteMap) {
		parameteMap.entrySet().forEach(e ->{
			query.setParameter(e.getKey(), e.getValue());
		});
	}
	
	@SuppressWarnings("unchecked")
	private Class<T> getDomainClass(){
		return (Class<T>) GenericTypeResolver.resolveTypeArgument(getClass(), FindPagingJpaRepository.class);
	}
}
