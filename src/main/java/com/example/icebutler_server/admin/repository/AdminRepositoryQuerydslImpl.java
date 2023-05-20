package com.example.icebutler_server.admin.repository;

import com.example.icebutler_server.admin.dto.response.UserResponse;
import com.example.icebutler_server.user.entity.User;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.support.PageableExecutionUtils;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.stream.Collectors;

import static com.example.icebutler_server.user.entity.QUser.user;
import static org.springframework.util.StringUtils.hasText;

@RequiredArgsConstructor
@Repository
public class AdminRepositoryQuerydslImpl implements  AdminRepositoryQuerydsl{

    private final JPAQueryFactory queryFactory;

    @Override
    public Page<UserResponse> findAllByNicknameAndActive(
            Pageable pageable,
            String nickname,
            boolean active)
    {
        List<User> result = queryFactory
                .selectFrom(user)
                .where(
                        nicknameLike(nickname),
                        user.isEnable.eq(active)
                )
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .orderBy(user.userIdx.desc())
                .fetch();

        JPAQuery<Long> countQuery = queryFactory
                .select(user.count())
                .from(user)
                .where(
                        nicknameLike(nickname),
                        user.isEnable.eq(active)
                );

        List<UserResponse> collect = result.stream().map(UserResponse::toDto).collect(Collectors.toList());

        return PageableExecutionUtils.getPage(collect, pageable, countQuery::fetchOne);
    }


    private BooleanExpression nicknameLike(String nickname)
    {
        return hasText(nickname) ? user.nickname.contains(nickname) : null;
    }
}
