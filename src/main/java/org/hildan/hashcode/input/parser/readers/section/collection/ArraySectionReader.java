package org.hildan.hashcode.input.parser.readers.section.collection;

import java.util.function.BiConsumer;
import java.util.function.Function;
import java.util.function.IntFunction;

import org.hildan.hashcode.input.parser.readers.TreeObjectReader;

public class ArraySectionReader<E, P> extends ContainerSectionReader<E, E[], P> {

    public ArraySectionReader(IntFunction<E[]> arrayCreator, TreeObjectReader<E> itemReader,
            Function<P, Integer> getSize, BiConsumer<P, E[]> parentSetter) {
        super(arrayCreator, itemReader, getSize, parentSetter);
    }

    public ArraySectionReader(IntFunction<E[]> arrayCreator, TreeObjectReader<E> itemReader, String sizeVariable,
            BiConsumer<P, E[]> parentSetter) {
        super(arrayCreator, itemReader, sizeVariable, parentSetter);
    }

    @Override
    protected void add(E[] collection, int index, E element) {
        collection[index] = element;
    }
}
