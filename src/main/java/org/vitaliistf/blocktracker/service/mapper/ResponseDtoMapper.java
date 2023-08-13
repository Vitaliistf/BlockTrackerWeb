package org.vitaliistf.blocktracker.service.mapper;

@FunctionalInterface
public interface ResponseDtoMapper<D, T> {
    D mapToDto(T t);
}
