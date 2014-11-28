/*
 * #%L
 * wcm.io
 * %%
 * Copyright (C) 2014 wcm.io
 * %%
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 * #L%
 */
package io.wcm.dromas.commons.stream.impl;

import static com.google.common.base.Preconditions.checkNotNull;
import io.wcm.dromas.commons.stream.Stream;
import io.wcm.dromas.commons.stream.function.Consumer;
import io.wcm.dromas.commons.stream.function.Function;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import com.google.common.collect.Lists;
import com.google.common.collect.Sets;

/**
 * Implementation of {@link Stream}.
 * @param <T> Item type
 */
public class StreamImpl<T> implements Stream<T> {

  private final Iterable<T> iterable;

  /**
   * @param iterable Iterable
   */
  public StreamImpl(Iterable<T> iterable) {
    this.iterable = iterable;
  }

  @Override
  public void forEach(Consumer<? super T> action) {
    checkNotNull(action);
    for (T item : iterable) {
      action.accept(item);
    }
  }

  @Override
  public <R> Stream<R> map(Function<? super T, ? extends R> mapper) {
    checkNotNull(mapper);
    List<R> result = new ArrayList<>();
    for (T item : iterable) {
      result.add(mapper.apply(item));
    }
    return new StreamImpl<R>(result);
  }

  @Override
  public Stream<T> filter(Function<? super T, Boolean> predicate) {
    checkNotNull(predicate);
    List<T> result = new ArrayList<>();
    for (T item : iterable) {
      Boolean include = predicate.apply(item);
      if (include != null && include.booleanValue()) {
        result.add(item);
      }
    }
    return new StreamImpl<T>(result);
  }

  @Override
  public List<T> toList() {
    if (iterable instanceof List) {
      return (List<T>)iterable;
    }
    else {
      return Lists.newArrayList(iterable);
    }
  }

  @Override
  public Set<T> toSet() {
    if (iterable instanceof Set) {
      return (Set<T>)iterable;
    }
    else {
      return Sets.newHashSet(iterable);
    }
  }

  @Override
  public Iterator<T> iterator() {
    return iterable.iterator();
  }

}