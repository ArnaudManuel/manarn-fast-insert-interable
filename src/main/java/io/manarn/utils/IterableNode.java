package io.manarn.utils;

import lombok.NonNull;
import lombok.RequiredArgsConstructor;

import java.util.*;
import java.util.function.Consumer;

public class IterableNode<O> extends Node<O> implements Iterable<O> {
    @Override
    public Iterator<O> iterator() {
        return new NodeIterator(tail);
    }

    @Override
    public void forEach(Consumer<? super O> action) {
        Objects.requireNonNull(action);
        Iterator<O> iterator = this.iterator();
        while (iterator.hasNext())
            action.accept(iterator.next());
    }

    public IterableNode<O> merge(IterableNode<O> o){
        next = o.tail;
        o.tail = this.tail;
        return o;
    }

    @Override
    public Spliterator<O> spliterator() {
        throw new UnsupportedOperationException();
    }

    @RequiredArgsConstructor
    class NodeIterator implements Iterator<O> {
        @NonNull
        private Node<O> current;

        @Override
        public boolean hasNext() {
            return current.next != null;
        }

        @Override
        public O next() {
            if (!hasNext())
                throw new NoSuchElementException();
            current = current.next;
            return current.content;
        }
    }
}
