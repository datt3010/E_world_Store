package com.eworld.repository.brand;

import com.eword.util.Sortable;
import com.eworld.entity.Brand;
import com.eworld.filter.BrandFilter;
import com.eworld.provider.FindPagingJpaRepository;
import org.apache.commons.lang3.StringUtils;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.LinkedHashMap;
import java.util.Map;

public class BrandCustomRepositoryImpl implements BrandCustomRepository, FindPagingJpaRepository<Brand> {
	
	@PersistenceContext
	EntityManager em;
	
	@Override
	public EntityManager getEntityManager() {
		return em;
	}

	@Override
	public Page<Brand> findPaging(BrandFilter filter, Pageable pageable) {
			
		StringBuilder countSqlBuilder = new StringBuilder(100)
				.append("SELECT COUNT(b.id) FROM Brand b");

		StringBuilder selectSqlBuilder = new StringBuilder(100)
				.append(" SELECT b FROM Brand b");

		StringBuilder conditionSqlBuilder = new StringBuilder(100);
		StringBuilder whereClauseSqlBuilder = new StringBuilder(50)
				.append(" WHERE 1=1");
		
		Map<String, Object> parameterMap = new LinkedHashMap<>();

		if(filter.getCategoryId() !=null) {
			conditionSqlBuilder.append(" INNER JOIN CategoryBrand cb ON cb.brandId = b.id");
			whereClauseSqlBuilder.append(" AND cb.categoryId = :categoryId");
			parameterMap.put("categoryId", filter.getCategoryId());
		}

		if(StringUtils.isNotBlank(filter.getKeyword())) {
			whereClauseSqlBuilder.append(" AND(");
			
			if(StringUtils.isNumeric(filter.getKeyword())) {
				whereClauseSqlBuilder.append(" b.id = :keywordId OR");
				parameterMap.put("keywordId", Integer.parseInt(filter.getKeyword()));
			}
			
			whereClauseSqlBuilder.append(" c.name LIKE :keyword");
			parameterMap.put("keyword", "%" + filter.getKeyword() + "%");
		}
		
		String orderClause = makeOrderClause(pageable.getSort(), Sortable.BRAND, "b");
		
		countSqlBuilder.append(whereClauseSqlBuilder);
		
		selectSqlBuilder
		.append(whereClauseSqlBuilder)
		.append(orderClause);
		
		return findPaging(countSqlBuilder.toString(), selectSqlBuilder.toString(), pageable, parameterMap);
	}

}
