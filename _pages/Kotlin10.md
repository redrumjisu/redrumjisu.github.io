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
   public interface Collection<out E>: Iterable<E> {
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
* Array : 주어진 타입에 대해 고정된 개수의 값을 갖기 위한 컨테이너. 길이는 생성하는 시점에 정해지고 변경이 불가능
```kotlin
   public class Array<T>: Cloneable {
     public inline constructor(size: Int, init: (Int) -> T)
     public operator fun get(index: Int): T
     public operator fun set(index: Int, value: T): Unit
     public val size: Int
     public operator fun iterator(): Iterator<T>
     public override fun clone(): Array<T>
   }
```

* Iterator / MutableIterator : 반복자. 요소의 시퀀스
```kotlin
   public interface Iterator<out T> {
     public operator fun next(): T
     public operator fun hasNext(): Boolean
   }

   public interface MutableIterator<out T>: Iterator<T> {
     public fun remove(): Unit
   }
```

* ListIterator / MutableListIterator : 되돌아가는 기능 지원  
```kotlin
   public interface ListIterator<out T>: Iterator<T> {
     //질의 연산
     override fun next(): T
     override fun hasNext(): Boolean
     public fun hasPrevious(): Boolean
     public fun previous(): T
     public fun nextIndex(): Int
     public fun previousIndex(): Int
   }
   
   public interface MutableListIterator<out T>: ListIterator<T>, MutableIterator<T> {
     // 질의 연산
     override fun next(): T
     override fun hasNext(): T
     // 변경 연산
     override fun remove(): Unit
     public fun set(element: T): Unit
     public fun add(element: T): Unit
   }
   
```

* Sequence : iterator 를 통해 값을 반환. 지연 연산(무한하게 존재하는 일 발생 가능). 평탄화 시퀀스(flattening sequence)  
```kotlin
   public interface Sequence<out T> {
     public operator fun iterator(): Iterator<T>
   }
``` 
코틀린 컬랙션 타입을 위한 자체 구현을 제공하지 않고, 자바 컬랙션에 존재하는 것을 활용 - 컴파일 단계에서 매핑  

|자바 타입|코틀린 변경 불가 타입|코틀린 변경 가능 타입|플랫폼 타입|
|:---:|:---:|:---:|:---:|
|Iterator&lt;T&gt;|Iterator&lt;T&gt;|MutableIterator&lt;T&gt;|(Mutable) Iterator&lt;T&gt;!|
|Iterable&lt;T&gt;|Iterable&lt;T&gt;|MutableIterable&lt;T&gt;|(Mutable) Iterable&lt;T&gt;!|
|Collection&lt;T&gt;|Collection&lt;T&gt;|MutableCollection&lt;T&gt;|(Mutable) Collection&lt;T&gt;!|
|Set&lt;T&gt;|Set&lt;T&gt;|MutableSet&lt;T&gt;|(Mutable) Set&lt;T&gt;!|
|List&lt;T&gt;|List&lt;T&gt;|MutableList&lt;T&gt;|(Mutable) List&lt;T&gt;!|
|ListIterator&lt;T&gt;|ListIterator&lt;T&gt;|MutableListIterator&lt;T&gt;|(Mutable) ListIterator&lt;T&gt;!|
|Map&lt;K, V&gt;|Map&lt;K, V&gt;|MutableMap&lt;K, V&gt;|(Mutable) Map&lt;K, V&gt;!|

## 배열 (Array)

* 배열의 선언과 초기화  
arrayOf  
arrayOfNulls : 각 요소가 널로 설정된 주어진 크기의 배열 반환  
Array 클래스 생성자 : 배열의 크기와 람다 함수 제공  
emptyArray : 빈 배열 생성  
* 원시 배열 : 원시 타입을 처리할 때는 ***ArrayOf 를 사용 - 박싱 / 언박싱 작업이 필요, Array&lt;Int&gt; / IntArray 의 차이    
자바 int[] 는 IntArray로 (다른 원시타입도 동일), String[] / T[] 는 Array&lt;String / T&gt; 로 매핑

* 차례차례 이동

```kotlin
   // 반복자 사용
   val countries = arrayOf("UK", "Germany", "Italy")
   for (country in countries) { println("$country") }
   // 반복자 사용하지 않음
   val numbers = intArray(10, 20, 30)
   for (i in numbers.indices) {
     numbers[i] *= 10
   }
   val index = Random().nextInt(10)
   if (index in numbers.indices) {  // if (index >=0 && index < numbers.size) 와 동일
     numbers[index] = index
   }
```
* 표준 라이브러리 확장 메소드  
first() : 첫번째 요소 반환  
last() : 마지막 요소 반환  
take(n) : n개 만큼 리스트 반환, Array 가 아니라 List  
takeLast(n) : 뒤에서 n개 만큼 리스트 반환  
takeWhile {조건} : 조건에 만족하는 요소 반환  
filterIndexed {index, element -> {}} : 현재 위치와 요소를 받아 조건에 만족하는 요소 반환  
map : 원본 요소 타입을 다른 타입으로 변환  
flatMap : 변환 람다로부터 반환된 모든 컬렉션을 병합한 리스트 반환  
다른 컬랙션 타입으로 변경 : toSet, toList

## 리스트 (List)
순서가 있는 컬렉션  

* 리스트 선언과 초기화  
listOf, emptyList, nonNulls : 읽기 전용 인스턴스  
arrayListOf, mutableListOf : 변경 가능한 리스트 

* 표준 라이브러리 확장 메소드  
zip : 두 컬렉션을 합쳐 새로운 컬랙션을 생성  
foldLeft / foldRight : 초기값을 받고 타깃 컬렉션을 반복한 다음(왼쪽에서 오른쪽 / 오른쪽에서 왼쪽),  
각 요소에 대한 람다함수를 실행하고, 누산 값을 반환한다. 누산기 역할!  
map / flatMap  
다른 컬렉션 타입으로 변경 : toArray, toSet, toMutableList  

## 맵 (Map)
객체(키)를 또 다른 객체(값)과 연관 짓게 해줌.  
중복된 키를 가질 수 없고, 각 키는 최대 1개의 값고 매핑 됨.  

* 맵 선언과 초기화  
mapOf, mutalbleMapOf : 코틀린 타입 반환    
hashMapOf, linkedMapOf, sortedMapOf : 자바 유틸의 맵 구현체 반환  

* 표준 라이브러리 확장 메소드  
mapKeys : 키 타입 변경  
map : 키와 값의 타입 모두 변경  
mapValues : 값의 타입을 변경  
flatMap  
filterKeys / filterNot : 필터 기반 맵의 요소 체리 픽. 조건을 만족하는 새로운 맵 반환   

## 셋(Set)  
중복되지 않는 아이템을 갖는 컬렉션  

* 맵 선언과 초기화  
setOf, mutableSetOf : 코틀린 타입  
hashSetOf, sortedSetOf, linkedSetOf : 자바 타입  

* 표준 라이브러리 확장 메소드  
contains  
first / last  
drop  
plus / minus : 기존의 컬랙션은 바뀌지 않고 새로운 컬렉션 생성
average  
map, flatMap  
다른 컬렉션 타입으로 변경 : toArray, toList, toMutableList

## 읽기 전용 뷰  
변경 가능한 컬렉션을 변경 불가능한 인터페이스로 형변환  
원본 컬렉션의 변경사항은 자동으로 영향을 줌

```kotlin
   val carManufacturers : MutableList<String> = mutableListof("Masserati", "Aston Martin", "McLaren", "Ferrari", "Koenigsegg")
   val carView: List<String> = carManufacturers

   carManufacturers.add("Lamborghini")
   println("Car View : $carView") // Car View : Masserati, Aston Martin, McLaren, Ferrari, Koenigsegg, Lamborghini
```

## 인덱스 접근  
리스트 요소 접근이나 맵의 키 값 반환을 인덱스 접근 방법으로 쉽게 해줌  
```kotlin
   val capitals = listOf("London", "Tokyo", "Instambul", "Bucharest")  
   capitals[2] // Tokyo
   
   val countries = mapOf("BRA" to "Brazil", "ARG" to "Argentina", "ITA" to "Italy")
   countries["BRA"] // Brazil
   countries["UK"]  // null
```

## 시퀀스  
컬렉션의 크기를 미리 알 수 없는 경우  
모든 컬렉션 타입은 시퀀스로 변환이 가능 : asSequence

* 시퀀스 선언과 초기화  
sequenceOf : 고정된 시퀀스 생성  
generateSequence : 시퀀스의 상한치를 알수 없을 경우. OutOfMemoryError 주의  
