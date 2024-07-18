package com.ms.learnkanji.commons;

public interface Tuple<T, U> {
    T getFirst();
    U getSecond();
    void setFirst(T first);
    void setSecond(U second);
}
