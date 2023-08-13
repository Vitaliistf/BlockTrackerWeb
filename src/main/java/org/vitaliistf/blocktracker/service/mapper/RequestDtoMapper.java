package org.vitaliistf.blocktracker.service.mapper;

@FunctionalInterface
public interface RequestDtoMapper<D, T> {
    T mapToModel(D dto);
}
