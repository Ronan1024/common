package com.ronan.common.stream;

import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.stream.Collector;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class StreamBuild<T> {

    private Stream<T> stream;

    public static <T> StreamBuild<T> of(List<T> list) {
        return new StreamBuild<>(list);
    }


    public StreamBuild(List<T> list) {
        this.stream = list.stream();
    }

    public List<T> toList(Stream<T> stream) {
        return stream.collect(Collectors.toList());
    }

    public <R> List<R> toList(Function<? super T, ? extends R> mapper) {
        return stream.map(mapper).collect(Collectors.toList());
    }

    public <K, U> Map<K, U> toMap(Function<? super T, ? extends K> keyMapper,
                                  Function<? super T, ? extends U> valueMapper) {
        return stream.collect(Collectors.toMap(keyMapper, valueMapper));
    }

    public <A, R> R toCollect(Collector<? super T, A, R> collector) {
        return stream.collect(collector);
    }


    public <K> Map<K, List<T>> groupingBy(Function<? super T, ? extends K> classifier) {
        return this.stream.collect(Collectors.groupingBy(classifier));
    }

    public StreamBuild(Stream<T> stream) {
        this.stream = stream;
    }

    public <R> StreamBuild<R> map(Function<? super T, ? extends R> mapper) {
        return new StreamBuild<>(this.stream.map(mapper));
    }

    public List<T> toList() {
        return this.stream.collect(Collectors.toList());
    }

    public List<T> toDistinctList() {
        return this.stream.distinct().collect(Collectors.toList());
    }


    public String joining(CharSequence delimiter) {
        return this.stream.map(String::valueOf).collect(Collectors.joining(delimiter));
    }

    public StreamBuild<T> filter(Predicate<? super T> predicate) {
        this.stream = this.stream.filter(predicate);
        return this;
    }


    public Stream<T> get() {
        return this.stream;
    }
}
