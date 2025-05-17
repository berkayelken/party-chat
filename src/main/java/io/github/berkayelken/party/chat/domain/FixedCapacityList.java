package io.github.berkayelken.party.chat.domain;

import java.util.Collection;
import java.util.LinkedList;

public class FixedCapacityList<E> extends LinkedList<E> {
	private final int maxSize;

	public FixedCapacityList(int maxSize) {
		this.maxSize = maxSize;
	}

	@Override
	public boolean add(E e) {
		if (this.size() >= maxSize) {
			this.removeFirst();
		}
		return super.add(e);
	}

	@Override
	public void add(int index, E element) {
		if (this.size() >= maxSize) {
			this.removeFirst();
		}
		super.add(index, element);
	}

	@Override
	public boolean addAll(Collection<? extends E> c) {
		var skip = c.size() - maxSize;
		var skippedCollection = skip <= 0 ? c : c.stream().skip(skip).toList();
		while (this.size() + skippedCollection.size() > maxSize) {
			this.removeFirst();
		}
		return super.addAll(skippedCollection);
	}

	@Override
	public boolean addAll(int index, Collection<? extends E> c) {
		var skip = c.size() - maxSize + index;
		var skippedCollection = skip <= 0 ? c : c.stream().skip(skip).toList();
		var difference = size() - skippedCollection.size() - index;
		if (difference > 0) {
			this.removeRange(index, size() - difference);
		} else {
			this.removeRange(index, skippedCollection.size() + difference + index);
		}
		return super.addAll(index, skippedCollection);
	}
}
