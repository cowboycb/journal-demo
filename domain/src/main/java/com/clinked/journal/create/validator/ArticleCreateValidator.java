package com.clinked.journal.create.validator;

import com.clinked.journal.common.DomainComponent;
import com.clinked.journal.common.validator.ValidationException;
import com.clinked.journal.common.validator.Validator;
import com.clinked.journal.create.usecase.CreateArticle;

import java.time.LocalDateTime;

import org.apache.commons.lang3.StringUtils;

@DomainComponent
public class ArticleCreateValidator implements Validator<CreateArticle> {
    private static final int MAX_TITLE_LENGTH = 100;
    private static final int MAX_AUTHOR_LENGTH = 100;
    @Override
    public void validate(CreateArticle createArticle) {
        ValidationException exception = new ValidationException();
        if (createArticle == null) {
            exception.add("Create Article Request must be valid!");
        } else {
            if (StringUtils.isBlank(createArticle.getTitle()) || createArticle.getTitle().length() > MAX_TITLE_LENGTH) {
                exception.add(String.format("Title is mandatory and length must be between 0-%d!", MAX_TITLE_LENGTH));
            }
            if (StringUtils.isBlank(createArticle.getAuthor()) || createArticle.getAuthor().length() > MAX_AUTHOR_LENGTH) {
                exception.add(String.format("Author is mandatory and length must be between 0-%d!", MAX_AUTHOR_LENGTH));
            }
            if (StringUtils.isBlank(createArticle.getContent())) {
                exception.add("Content is mandatory!");
            }
            if (createArticle.getPublishDate() == null || LocalDateTime.now().isAfter(createArticle.getPublishDate())) {
                exception.add("Publish Date is mandatory and must be greater than today!");
            }
        }
        if (exception.isNotEmpty()) {
            throw exception;
        }
    }
}
