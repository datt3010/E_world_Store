package com.eworld.repository.blog;

import com.eword.util.Sortable;
import com.eworld.entity.Blog;
import com.eworld.filter.BlogFilter;
import com.eworld.provider.FindPagingJpaRepository;
import org.apache.commons.lang3.StringUtils;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.LinkedHashMap;
import java.util.Map;

public class BlogCustomRepositoryImpl implements BlogCustomRepository, FindPagingJpaRepository<Blog> {
    @PersistenceContext
    EntityManager em;

    @Override
    public EntityManager getEntityManager() {
        return  em;
    }

    @Override
    public Page<Blog> findPaging(BlogFilter filter, Pageable pageable) {
        StringBuilder countSqlBuilder = new StringBuilder(100)
                .append("SELECT COUNT(b.id) FROM Blog b");

        StringBuilder selectSqlBuilder = new StringBuilder(100)
                .append(" SELECT b FROM Blog b");

        StringBuilder conditionSqlBuilder = new StringBuilder(100);

        StringBuilder whereClauseSqlBuilder = new StringBuilder(50)
                .append(" WHERE 1=1");

        Map<String, Object> parameterMap = new LinkedHashMap<>();


        if(StringUtils.isNotBlank(filter.getKeyword())) {
            whereClauseSqlBuilder.append(" AND(");

            if(StringUtils.isNumeric(filter.getKeyword())) {
                whereClauseSqlBuilder.append(" b.id = :keywordId OR");
                parameterMap.put("keywordId", Integer.parseInt(filter.getKeyword()));
            }

            whereClauseSqlBuilder.append(" b.name LIKE :keyword OR b.status LIKE :keyword)");
            parameterMap.put("keyword", "%" + filter.getKeyword() + "%");
        }

        String orderClause = makeOrderClause(pageable.getSort(), Sortable.BLOG, "b");

        countSqlBuilder.append(conditionSqlBuilder)
                .append(whereClauseSqlBuilder);

        selectSqlBuilder
                .append(conditionSqlBuilder)
                .append(whereClauseSqlBuilder)
                .append(orderClause);

        return findPaging(countSqlBuilder.toString(), selectSqlBuilder.toString(), pageable, parameterMap);
    }
}
