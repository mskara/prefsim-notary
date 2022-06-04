package com.raccoon.prefsimnotary.repository.file;

import java.util.List;

public interface FileRepository<T> {

    List<T> process();

}