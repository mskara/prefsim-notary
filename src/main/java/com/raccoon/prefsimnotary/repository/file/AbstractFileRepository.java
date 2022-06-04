package com.raccoon.prefsimnotary.repository.file;

import lombok.SneakyThrows;

import java.io.BufferedReader;
import java.io.FileReader;
import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;

public abstract class AbstractFileRepository<T> implements FileRepository<T> {

    protected abstract String getFilePath();

    protected abstract String getFileName();

    @SneakyThrows
    private List<List<String>> prepare() {

        final String filePath = getFilePath();
        final String fileName = getFileName();
        final Function<String, String[]> lineSplitter = line -> line.split(";");
        final BufferedReader reader = new BufferedReader(new FileReader(filePath + fileName));

        return reader
                .lines()
                .map(lineSplitter)
                .map(List::of)
                .collect(Collectors.toList());
    }

    @Override
    public List<T> process() {
        return prepare().stream()
                .map(this::singleProcess)
                .collect(Collectors.toList());
    }

    protected abstract T singleProcess(List<String> line);
}
