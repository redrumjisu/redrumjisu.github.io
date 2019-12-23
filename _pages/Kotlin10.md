# 컬렉션
변경 가능한(mutable) / 변경 불가능한(immutable)  
## 클래스 계층 구조
네임스페이스 -  kotlin.collections  
변경 가능한 타입 - Mutable 접두사

* Iterable : 공변 조건 

```kotlin
   public interface Iterable<out T> {
     public abstract operator fun iterator(): Iterator<T>
   }
```

* Collection : Iterable 확장, 컬랙션의 크기, 컬랙션 요소의 존재여부 메소드 정의  

```kotlin
   public interface Collection<out E>: Iterator<E> {
     public val size: Int
     public fun isEmpty(): Boolean
     public operator fun  contains(element: @UnsafeVariance E): Boolean
     operator fun iterator(): Iterator<E>
     public fun containsAll(elements: Collection<@UnsafeVariance E>): Boolean 
   }
```

* MutableIterable : 변경가능한 반복자를 반환하도록 부모의 iterator() 재정의

```kotlin
   public interface MutableIterable<out T> {
     operator fun iterator(): MutableIterable<T>
   }
```
* List : 순서를 갖는 컬랙션, 읽기 전용 접근 지원  
get() - 요소의 위치 색인  

```kotlin
   public interface List<out E>: Collection<E> {
     // 질의 연산
     override val size: Int
     override fun isEmpty(): Boolean
     override fun contains(element: @UnsafeVariance E): Boolean
     override fun iterator(): Iterator<E>
     override fun containsAll(elements: Collection<@UnsafeVariance E>): Boolean
     public operator fun get(index: Int): E
     public fun indexOf(element: @UnsafeVariance E): Int
     public fun lastIndexOf(element: @UnsafeVariance E): Int
     // 리스트 반복자
     public fun listIterator(): ListIterator<E>
     public fun listIterator(index: Int): ListIterator<E>
     // 뷰
     public fun subList(fromIndex: Int, toIndex: Int): List<E> 
   }
```
* Set : 순서가 없는 요소의 컬랙션, 중복을 허용하지 않음  
```kotlin
   public interface Set<out E>: Collection<E> {
     // 질의 연산
     override val size: Int
     override fun isEmpty(): Boolean
     override fun contains(element: @UnsafeVariance E): Boolean
     override fun iterator(): Iterator<E>
     // 대규모 연산
     override fun containsAll(elements: Collection<@UnsafeVariance E>): Boolean
   }
```

* MutableCollection : 요소의 추가 / 삭제를 허용하는 컬랙션 지원  

```kotlin
   public interface MutableCollection<E>: Collection<E>, MutableIterable<E> {
     // 질의 연산
     operator fun iterator(): MutableIterable<E>
     // 변경 연산
     public fun add(element: E): Boolean
     public fun remove(element: E): Boolean
     // 대규모 변경 연산
     public fun addAll(elements: Collection<E>): Boolean
     public fun removeAll(elements: Collection<E>): Boolean  
     public fun retainAll(elements: Collection<E>): Boolean
     public fun clear(): Unit  
   }
```
* MutableList : 위치 순서에 기반을 둠, 아이템 변경 / 검색 허용

```kotlin
   public interface MutableList<E>: List<E>, MutableCollection<E> {
     // 변경 연산
     override fun add(element: E): Boolean
     override fun remove(element: E): Boolean
     // 대규모 변경 연산
     override fun addAll(elements: Collection<E>): Boolean
     public fun addAll(index: Int, elements: Collection<E>): Boolean
     override fun removeAll(elements: Collection<E>): Boolean  
     override fun retainAll(elements: Collection<E>): Boolean
     override fun clear(): Unit  
     // 위치 접근 연산
     public operator fun set(index: Int, element: E): E
     public fun add(index: Int, element: E): Unit
     public fun removeAt(index: Int): E
     // 리스트 반복자
     override fun listIterator(): MutableListIterator<E>
     override fun listIterator(index: Int): MutableListIterator<E>
     // 뷰
     override fun subList(fromIndex: Int, toIndex: Int): MutableList<E>
   }
```

* MutableSet  

```kotlin
   public interface MutableSet<E>: Set<E>, MutableCollection<E> {
     // 질의 연산
     override fun iterator(): MutableIterable<E>
     // 변경 연산
     override fun add(element: E): Boolean
     override fun remove(element: E): Boolean
     // 대규모 변경 연산
     override fun addAll(elements: Collection<E>): Boolean
     override fun removeAll(elements: Collection<E>): Boolean  
     override fun retainAll(elements: Collection<E>): Boolean
     override fun clear(): Unit  
   }
```

* Map / MutableMap  

```kotlin
   public interface Map<K, out V> {
     // 질의 연산
     public val size: Int
     public fun isEmpty(): Boolean
     public fun containsKey(key: K): Boolean
     public fun containsValue(value: @UnsafeVariance V): Boolean
     public operator fun get(key: K): V?
     
     public fun getOrDefault(key: K, defaultValue: @UnsafeVariance V): V {
       return null as V
     }
     // 뷰
     public val keys: Set<K>
     public val values: Collection<V>
     public val entries: Set<Map.Entry<K, V>>
     public interface Entry<K, V> {
       public val key: K
       public val value: V
     }
  }
   public interface MutableMap<K, out V> {
     // 변경 연산
     public fun put(key: K, value: V): V?
     public fun remove(key: K): V?
     // 대규모 변경 연산
     public fun putAll(from: Map<K, V>): Unit
     public fun clear(): Unit
     // 뷰
     override val keys: MutableSet<K>
     override val values: MutableCollection<V>
     override val entries: MutableSet<MutableMap.MutableEntry<K, V>>
     public interface MutableEntry<K, V>: Map.Entry<K, V> {
       public fun setValue(newValue: V): V
     }
  }
```


