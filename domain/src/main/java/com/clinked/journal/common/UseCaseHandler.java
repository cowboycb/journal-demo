package com.clinked.journal.common;

import com.clinked.journal.common.model.UseCase;

public interface UseCaseHandler<E, T extends UseCase> {
    E handle(T useCase);
}