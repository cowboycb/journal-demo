package com.clinked.journal.common.validator;

import com.clinked.journal.common.model.UseCase;

public interface Validator<T extends UseCase> {
    void validate(T useCase);
}
