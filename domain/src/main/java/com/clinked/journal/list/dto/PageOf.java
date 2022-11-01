package com.clinked.journal.list.dto;

import lombok.Data;

@Data
public class PageOf<T> {
    private T data;
    private long total;
    private int size;
    private int page;

    public <V> PageOf<V> clone(V targetData) {
        PageOf<V> target = new PageOf<>();
        target.setTotal(this.getTotal());
        target.setPage(this.getPage());
        target.setSize(this.getSize());
        target.setData(targetData);
        return target;
    }
}
