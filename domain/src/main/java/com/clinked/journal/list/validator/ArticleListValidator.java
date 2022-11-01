package com.clinked.journal.list.validator;

import com.clinked.journal.common.DomainComponent;
import com.clinked.journal.common.validator.ValidationException;
import com.clinked.journal.common.validator.Validator;
import com.clinked.journal.list.usecase.ListArticle;

@DomainComponent
public class ArticleListValidator implements Validator<ListArticle> {
    @Override
    public void validate(ListArticle useCase) {
        ValidationException exception = new ValidationException();
        if (useCase != null) {
            if (useCase.getPage() == null || useCase.getPage() < 0) {
                exception.add("`page` parameter is mandatory and must be greater than 0(zero)!");
            }
            if (useCase.getSize() == null || useCase.getSize() <= 0) {
                exception.add("`size` parameter is mandatory and must be greater than 0(zero)!");
            }
        } else {
            exception.add("Please set page and size parameters!");
        }
        if (exception.isNotEmpty()) {
            throw exception;
        }
    }
}
