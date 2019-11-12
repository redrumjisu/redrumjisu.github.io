# 프로퍼티
일반적인 프로퍼티, 델리게이트 프로퍼티  
```regexp
   var/val<propertyName>:<PropertyType>[=<property_initializer>]
   [<getter>]
   [<setter>]
```
## 문법과 변형
프로퍼티 타입은 생락 가능  
읽기 전용(val) 일 경우에는 게터 메소드만 갖게 됨
```kotlin
   interface Shape {
     val Area: Double
     get
   }
   class Rectangle(val width: Double, val height: Double): Shape {
     override val Area: Double
     get() = width * height
    
     val isSquare: Boolean = width == height
   }
  
   class Lookup {
     private var _keywords: HashSet<String>? = null
     val keywords: Iterable<String>
     get() {
       if (_keywords == null) { 
         _keywords = HashSet()
       }
       return _keywords ?: throw RuntimeException("Invalid keywords")
     }
   }
```
## 가시성
private, protected, public(기본)  
프로퍼티가 상속의 대상이 되는 경우에는 protected 가 적절함  
## 늦은 초기화
널을 갖지 않는 프로퍼티는 모두 생성자 내부에서 초기화가 필요  
지연 초기화 : lateinit 키워드를 사용
 * 원시타입이 될 수 없음
 * 커스텀 getter / setter 를 사용 할 수 없음
 * 초기화하기 전에 접근하면 UninitializedPropertyAccessException 발생
```kotlin
   class Container {
     lateinit var delayedInitProperty: DelayedInstance
     fun initProperty(instance: DelayedInstanse) {
       this.delayedInitProperty = instance
     }
   }

   class DelayedInstance(val number: Int)

   val container = Container()
   container.initProperty(DelayedInstance(10))
```
## 델리게이트 프로퍼티(!)
프로퍼티 값은 처음 값에 접근했을 떄 연산되도록 지연돼야 한다.  
프로퍼티 값 중 하나가 변경되면 수신자에게 이를 알려야 한다.  
필드 값을 저장하기 위해서는 구체화한 필드를 사용하기 보다는 맵을 사용해야 한다.  
**코틀린의 델리게이트 프로퍼티는 모두 지원함.**
```regexp
   var/val<propertyName>:<PropertyType> by <expression>
```
by 키워드 뒤에 오는 표현식이 델리게이트임
```kotlin
   class TimestampValueDelegate {
     private var timestamp = 0L
     operator fun getValue(ref: Any?, property: KProperty<*>): Long {
       return timestamp 
     }
     operator fun setValue(ref: Any?, property: KProperty<*>, value: Long) {
       timestamp = value
     }
   }
   class Measure {
     var writeTimestamp: Long by TimestampValueDelegate() 
   }

   val measure = Measure()
   measure.writeTimestamp = System.currentTimeMillis()
```
## 지연 초기화
목적 : 성능을 향상하고 메모리를 차지하는 공간을 줄임  
by lazy 를 작성하고 인스턴스를 생성하는 로직 제공
```kotlin
   class WithLazyProperty {
     val foo: Int by lazy {
       println("Initalized foo")
       2
     }
   }
   val withLazyProperty = WithLazyProperty()
   val total = withLazyProperty.foo() + withLazyProperty.foo()
   println("Lazy property total : $total")
```
```text
   Initalized foo
   Lazy property total : 4
```
total 은 4 이지만, Initalized foo 는 한 번만 나옴.  

Lazy 인터페이스 정의
```kotlin
   public interface Lazy<out T> {
     public val value: T
     public fun isInitialized(): Boolean
   }
   
   fun <T> lazy(initializer: () -> T) : Lazy<T> = SynchronizedLazyImpl(initializer) // (1)
```
```kotlin
   fun <T> lazy(mode: LazyThreadSafetyMode, initializer: () -> T): Lazy<T>
```
* LazyThreadSafetyMode 모드  
  SYNCHRONIZED : 락이 오직 싱글 스레드만이 Lazy 인스턴스를 초기화 할 수 있음을 보장, (1) 과 같은 구현
  PUBLICATION : 동시성 접근에서 초기화되지 않은 Lazy 인스턴스 값에 초기화 함수가 여러번 호출될 수 있으나, 첫 번째로 반환된 값으로 사용  
  NONE : Lazy 인스턴스 값에 접근을 동기화하기 위해 락을 사용하지 않았음  
## lateinit와 lazy
* lazy 델리게이트는 val 에서만 사용 가능 / lateinit은 var에서만 사용 가능  
* lateinit 프로퍼티는 final이 아니므로 불변성을 얻을 수 없음  
* lateinit 프로퍼티는 백킹 필드를 갖지만, lazy 델리게이트는 한 번 생성된 값을 위한 컨테이너로 게터를 제공하는 델리게이터 생성  
* lateinit 프로퍼티는 널 값이 가능한 프로퍼티나 자바 원시타입에서는 사용할 수 없음  
* 프로퍼티가 초기화될 수 있는 곳에 관해서는 lateinit 프로퍼티가 유연함 / lazy 델리게이트는 프로퍼티를 위한 유일한 초기화를 정의(오버라이딩을 통해 변경)  
## 옵저버블
Delegates.observable : 프로퍼티 변경여부를 확인 할 수 있음
```kotlin
   fun <T> observable(initialValue: T, crossinline onChange: 
        (property: KProperty<*>, oldValue: T, newValue: T) -> Unit): ReadWriteProperty<Any?, T>
```
## 널 값을 갖지 않는 프로퍼티 델리게이트
Delegate.nonNull : 널 값을 갖지 않는 프로퍼티 델리게이트 지원
```kotlin
   class NonNullProp {
      var value: String by Delegates.nonNull<String>()
   }
   val nonNull = NonNullProp()
   nonNull.value = "Non Null Prop"
   nonNull.value = null // 컴파일되지 않음
```