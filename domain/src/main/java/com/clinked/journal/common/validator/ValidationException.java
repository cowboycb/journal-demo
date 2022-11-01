package com.clinked.journal.common.validator;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.StringUtils;

import lombok.NoArgsConstructor;

@NoArgsConstructor
public class ValidationException extends RuntimeException {
    private List<String> list = new ArrayList<>();

    private String msg;

    public ValidationException(String message) {
        super(message);
        this.msg = message;
    }

    public void add(String msg) {
        list.add(msg);
    }

    @Override
    public String getMessage() {
        return StringUtils.isEmpty(this.msg)
            ? String.join(", ", list)
            : this.msg;
    }

    public boolean isNotEmpty() {
        return !this.list.isEmpty();
    }
}
