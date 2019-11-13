# 제네릭
타입에 대한 함수를 작성할 수 있는 기술  
## 매개변수화된 함수
타입 추상화, 타입 매개변수  
2개이상의 타입 매개변수를 가질 수 있음  
```kotlin
   fun random(one: Any, two: Any, three: Any): Any // Any 타입이고 형변환 처리 필요
   
   fun <T> random(one: T, two: T, three: T): T

   val randomGreeting: String = random("hello", "willkommen", "bonjour") // String 타입
   val any: Any = random("a", 1, false)  // Any 타입
```
## 매개변수화된 타입
매개변수화는 타입 자신도 매개변수화 될 수 있음  
이를 인스턴스화 할 때는 반드시 매개변수를 구체적이거나 적절한 타입으로 교체해야 함  
```kotlin
   class Sequence<T>
   val seq = Sequence<String>()
```
## 범위를 갖는 다향성
호출해야 하는 함수를 지원하는 타입으로 제한해서 런타임시에 함수 호출을 가능하게 함  
상한 : 해당 범위의 서브클래스로 타입을 제한  
```kotlin
   fun <T: Comparable<T>> min(first: T, second: T): T {
     val k = first.compareTo(second)
     return if (k < 0) first else second
   }
```
다중범위 : where 절로 분리  
```kotlin
   fun <T> minSerializable(first: T, second: T): T where T: Comparable<T>, T: Serializable {
     val k = first.compareTo(second)
     return if (k < 0) first else second
   }
```
## 타입 변형
매개변수화된 타입의 서브타입을 허용할지에 대한 기술    
```kotlin
   class Fruit
   class Apple: Fruit()
   class Orange: Fruit()

   class Crate<T>(val elements: MutableList<T>) {
     fun add(t: T) = elements.add(t)
     fun last(t: T) = elements.last()
   }
```
불변성 : 타입간에 서브타입 관계가 형성되지 않음
```kotlin
   fun foo(crate: Crate<Fruit>) = crate.add(Apple())

   val oranges = Crate(mutableListOf(Orange(), Orange()))
   foo(oranges)
   val orange: Orange = oranges.last() // ClassCastException - foo에서 Apple() add 했기 때문에
```
공변성 : 타입 매개변수와 서브타입 관계 유지, **out** 키워드 추가  
함수의 반환 타입도 공변성이 될 수 있음
```kotlin
   open class Fruit {
     fun isSafeToEat(): Boolean
   }

   fun isSafe(crate: Crate<Fruit>): Boolean = crate.elements.all {
     it.isSafeToEat()
   }

   class CoveriantCrate<out T>(val elements: List<T>) {
     fun last(): T = elements.last()
   }
   val oranges = CoveriantCrate(mutableListOf(Orange(), Orange()))
   isSafe(ornages) // Orange 가 Fruit 의 서브타입으로 인식
```
반공변성 : 공변성의 반대, 타입 매개변수와 슈퍼타입 관계 유지, **in** 키워드 추가
```kotlin
   interface Generator<in T> {
     fun generate(): T
   }

   class OrangePicker(val generator: Generator<Orange>) {
     fun pick() = {
       val orange = generator.generate()
       peel(Orange)
     }
     fun peel(orange: Orange): Unit = {}
   }

   val generator = object: Generator<Fruit> {
     override fun generate(): Fruit = Tomato()
   }
   val picker = OrangePicker(generator)
   picker.pick()  // 반공변성이기 때문에 런타임익셉션 발생
```
## Nothing 타입
다른 모든 타입의 서브타입  
정상적으로 완료되지 않는 함수를 나타내기 위해  
공변 타입을 갖고 있고 모든 슈퍼타입과 호환이 되는 인스턴스를 만들 경우  
해당 타입의 인스턴스가 비어 있거나 연산을 갖고 있지 않기를 바랄 경우  
Nothing은 타입으로 정의되어 있지만 인스턴스화할 수는 없음  
## 타입 프로젝션
클래스를 불변규칙으로 정의했지만, 공변 / 반공변 규칙으로 사용해야 하는 경우 : 타입 프로젝션  
매개변수화된 타입을 받을 함수를 정의할 떄 out / in 키워드 사용  
타입 매개변수가 허용되는 위치에서만 함수를 호출할 수 있도록 제약  
## 타입 소거(!)
컴파일러가 컴파이하는 동안 타입 매개변수를 삭제하는 프로세스  
해결방법으로 @JvmName 사용이나 구체화의 제한된 형태  
## 타입 구체화
인라인 함수에 대해 런타임에서 타입 정보를 유지할 수 있게 해줌  
reified 키워드 추가  
구체화한 함수는 inline으로 정의  
## 재귀 타입 경계(!)
경계를 갖는 다형성을 확장  
하나 이상의 타입 매개변수를 포함하는 타입, 적어도 타입 매개변수 중 하나가 타입 자신에 사용함  
## 대수적 데이터 타입 
타입에 대한 대수는 해당 타입에 대한 연산이나 함수를 정의  
sealed 키워드를 사용해 닫힌 프로퍼티를 구현  
Either, Try, Option, 트리 자료구조 등에서 구현  
List.kt 참고  