# 고차 함수와 함수형 프로그래밍
## 고차 함수(Higher order function)
다른 함수를 매개변수로 받거나, 반환 값으로 함수를 반환하거나, 둘 다 행하는 함수
* Pass a function as an Argument  
* Return a function  
* Pass a function as an Argument OR Return a function

함수 할당 : 변수에 할당가능 
```kotlin
   val isEven: (Int) -> Boolean = { it % 2 == 0}
   val isEven = { k: Int -> k % 2 == 0}
``` 
일급 함수(first class function) : 고차 함수와 함수 할당을 지원
## 클로저
바깥 스코프에 정의된 변수와 매개변수에 접근할 수 있는 함수  
```kotlin
   class Student(val firstName: String, val lastName: String) {
     fun loadStudents(): List = ...
     fun students(nameToMatch: String): List<Student> {
       return loadStudents().filter {  // nameToMatch 매개변수 사용
         it.lastName == nameToMatch
       }
     }
   }
``` 
지역변수 접근 가능
```kotlin
   val counter = AtomicInteger(0)
   val cores = Runtime.getRuntime().availableProcessors()
   val threadPoll = Executors.newFixedThreadPoll(cores)
   threadPoll.submit {
     println("task number ${counter.incrementAndGet()}")  // counter 지역변수 접근
   }
```
## 익명 함수
함수의 이름이 생략.  
매개변수도 추론이 가능하면 생략 가능
```kotlin
   fun(a: String, b: String): String = a + b
   val evens = list(1,2,3,4,5).filter(fun(k) = k % 2 == 0)
```
## 함수 참조
* 최상위 함수 참조 - **::** 문법 사용
```kotlin
   fun isEven(k: Int): Boolean = k % 2 == 0
   val ints = list(1,2,3,4,5)
   ints.filter(::isEven)
``` 
* 맴버 함수 참조 / 확장 함수 참조 - 클래스 이름을 접두사로
```kotlin
   fun Int.isOdd(): Boolean = this % 2 == 1
   val ints = list(1,2,3,4,5)
   ints.filter(Int::isOdd)
   
   fun foo(a: Double, b: Double, f: (Double, Double) -> Double) = f(a, b)
   foo(1.0, 2.0, {a, b -> Math.pow(a,b)})
   foo(1.0, 2.0, Math::pow) // 함수 참조를 사용해 보일러플레이트 코드를 줄일 수 있음
```
* 바인딩 참조 - 특정 인스턴트에 바인딩 되는 함수 참조를 가질 수 있음.
```kotlin
   fun String.equalsIgnoreCase(other: String) = this.toLowerCase() == other.toLowerCase()
   
   listOf("foo", "moo", "boo").filter {
     (String::equalsIgnoreCase)("bar", it)
   }
   listOf("foo", "moo", "boo").filter("bar"::equalsIgnoreCase) // 바인딩 참조
```
## 함수 리터럴 수신자(!)
함수 매개변수가 호출될 때 수신자를 받도록 함수 매개변수를 정의할 수 있음.
## 함수 합성
코틀린에서 지원하지 않지만, 함수 조작 기능으로 구현 가능
```kotlin
   fun <A, B, C> compose(fn1: (A) -> B, fn2: (B) -> C): (A) -> C = { A ->
     val b = fn1(a)
     val c = fn2(b)
     c
   }

   val f = String::length
   val g = Any::hashCode
   val fog = compose(f, g)

   infix fun <P1, R, R2> Function1<P1, R>.compose(fn: (R) -> R2): (P1) -> R2 = {
     fn(this(it))
   }
   val fog2 = f compose g

   operator infix fun <P1, R, R2> Function1<P1, R>.times(fn: (R) -> R2): (P1) -> R2 = {
     fn(this(it))
   }
   val fog3 = f * g
```
## inline 함수(!)
함수는 객체의 인스턴스이며 힙에 할당이 필요 -> 오버헤드를 일으킴  
inline 키워드로 오버헤드를 피하게 해줌  
noinline 함수 : 인라인으로 표시된 함수는 변수를 인라인으로 할당할 수 없기 때문에  
## 커링과 부분적용
커링(currying) : 여러개의 매개변수를 받는 함수를 각각 단일 매개변수를 받는 함수로 변환하는 과정    
```kotlin
   fun foo(a: String, b: Int): Boolean
   fun foo(a:String): (Int) -> Boolean
```
부분적용 : 몇 가지 매개변수를 명시함으로 나머지 매개변수를 받는 새로운 함수를 반환하는 기법
* 몇 가지 매개변수가 모든 스코프에서 이용할 수 없을 경우
* 다른 함수의 더 적은 수의 인자 입력 타입과 일치 시키기 위해
```kotlin
   fun compute(logger: (String) -> Unit)
   fun log(level: Level, appender: Appendable, msg: String): Unit = {}
   
   fun compute {
     msg -> log(Level.Warn, Appendable.Console, msg)
   }
```
## 메모이제이션
주어진 입력 값에 대해 재연산하는 대신 출력 값을 캐싱해 재 사용함으로써 호출 속도를 향상하기 위한 기법  
```kotlin
   fun fib(k: Int): Long = when(k) {
     0 -> 1
     1 -> 1
     else -> fib(k - 1) + fib(k - 2)
   } // 시간 복잡도 면에서 기하급수적 O(2^n)
   val map = mutableMapOf<Int, Long>()
   fun memfib(k: Int): Long {
     return map.getOrPut(k) {
       when(k) {
         0 -> 1
         1 -> 1
         else -> memfib(k - 1) + memfib(k - 2)
       }
     }
   }
```
## 타입 앨리어스
typealias 키워드로 새로운 타입을 선언  
새로운 타입이 생성되거나 할당되지 않음
```kotlin
   fun process(exchange: Exchange<HttpRequest, HttpResponse>): Exchange<HttpRequest, HttpResponse>
   
   typealias HttpExchange = Exchange<HttpRequest, HttpResponse>
   fun process2(exchange: HttpExcange): HttpExchange
```
같은 타입의 다른 타입 앨리어스의 경우 상호 교환적으로 사용 가능
```kotlin
   typealias String1 = String
   typealias String2 = String
   fun printString(str: String1) = prlntln(str)
   
   val a: String2 = "String2"
   printString(a)  // String2 도 String 타입이므로 
```
## Either
두 가지 사용 가능한 타입을 가질수 있는 값  
```kotlin
   sealed class Either<out L, out R>
   class Left<out L>(val value: L): Either<L, Nothing>()
   class Right<out R>(val value: R): Either<Nothing, R>()
```
* fold : 2개의 함수 변수, 각 타입에 따라 함수가 적용되어 반환
```kotlin
   sealed class Either<out L, out R> {
     fun <T> fold(lfn: (L) -> T, rfn: (R) -> T): T = when(this) {
       is Left -> lfn(this.value)
       is Right -> rfn(this.value)
     }
   }
```
* 프로젝션 : 맵, 필터, 값을 가져오는 기능을 한쪽 타입에만 적용되고 나머지는 no-ops 가 되도록 정의
```kotlin
   sealed class Projection<T> { 
     abstract fun <U> map(fn: (T) -> U): Projection<U>
     abstract fun getOrElse(or: () -> T): T
   }
   class ValueProjection<T>(val value: T): Projction<T>() {
     override fun <U> map(fn: (T) -> U): Projection<U> = ValueProjection(fn(value))
     override fun getOrElse(or: () -> T): T = value
   }
   class EmptyProjection<T>(val value: T): Projction<T>() {
     override fun <U> map(fn: (T) -> U): Projection<U> = EmptyProjection(fn(value))
     override fun getOrElse(or: () -> T): T = or()
   }

   fun <T> Projection<T>.getOrElse(or: () -> T): T = when(this) {
     is EmptyProjection -> or()
     is ValueProjcetion -> this.value
   }
```
```kotlin
   sealed class Either<out L, out R> {
     fun <T> fold(lfn: (L) -> T, rfn: (R) -> T): T = when(this) {
       is Left -> lfn(this.value)
       is Right -> rfn(this.value)
     }
     fun leftProjection(): Projection<L> = when(this) {
       is Left -> ValueProjection(this.value)
       is Right -> EmptyProjection<L>()
     }
     fun rightProjection(): Projection<R> = when(this) {
       is Left -> EmptyProjection<R>()
       is Right -> ValueProjection(this.value)
     }
   }
```
* 프로젝션 확장 : Projection.kt 참고  
  exist : 프로젝션이 값을 갖고 있으면 Boolean 결과 리턴  
  filter : 필터 연산 수헹, false 일경우 빈 프로젝션 반환   
  toList : 값들의 리스트  
  orNull : 값을 반환하거나, 비어있을 경우 널 반환
## 커스텀 DSL(!)
도메인에 특화된 언어(domain-specific language)
## 유효성 축적과 에러 축적(!)
입력 값이 올바른 경우 성공한 값들을 반환하고, 올바르지 않는 경우 실패한 값들을 반환한다.  
이러한 개별적인 함수는 결합한다음 에러를 유지한다.  
에러를 얻어오기 위해 축적된 정보를 얻을 수 있다.  
Validation.kt 참고