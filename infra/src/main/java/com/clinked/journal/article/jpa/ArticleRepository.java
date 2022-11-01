package com.clinked.journal.article.jpa;

import com.clinked.journal.article.jpa.entity.ArticleEntity;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ArticleRepository extends JpaRepository<ArticleEntity, UUID> {
}
