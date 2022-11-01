package com.clinked.journal.list.usecase;

import com.clinked.journal.common.model.UseCase;

import lombok.Data;

@Data
public class ListArticle implements UseCase {
    private Integer page;
    private Integer size;

}
