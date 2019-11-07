# 코틀린 기본 구성요소
## 변수와 값
* val : 읽기 전용 변수, 반드시 초기화 필요함. (final)
```kotlin 
    val name = "jisu"
```
* var : 변경 가능한 변수, 나중에 초기화 가능
```kotlin 
    var name: String
    name = "jisu"
```

## 타입추론
표현식에 포함된 정보로 타입을 찾으려 함.  
값, 변수, 클로저, 함수 반환 값.  
```kotlin
    val name = "jisu"  // String
    fun plusOne(x: Int) = x + 1 // Int
```
때로는 **:** 을 사용해서 명시적으로 선언.
```kotlin
    val explicitType: Number = 12.3
``` 

## 기본타입
* 숫자  
  Long(64), Int(32), Short(16), Byte(8)  
  Double(64), Float(32)  
  타입 간 변환 : toByte(), toShort(), toInt(), toLong(), toFloat(), toDouble(), toChar()  
  비트연산자 : shl, shr, ushr, and, or, xor, inv(단항)
* 불리언 (Boolean)  
  부정(!), 논리합(||), 논리곱(&&)
* 문자 (Char)    
  단일 문자('), 이스케이프, 유니코드, 숫자가 아님.
* 문자열 (String)  
  이중따옴표 : 이스케이프된 문자열 생성
  ```kotlin
     val string = "string with \n new line"
  ```  
  삼중따옴표 : 원시문자열을 생성, 이스케이프 문자 필요없음. 모든 문자 포함 가능. 
  ```kotlin
     val rawString = """
     Hello. 
     This is rawString.
     BlahBlahBlahBlahBlahBlahBlahBlahBlah.
     GoodBye
     """ 
  ```  
  문자열 템플릿 : 달러 **($)** 를 추가, 임의의 표현식은 괄호 **({ })** 감싼다.
  ```kotlin
     var name = "Jisu"
     var str = "Hello $name"  // Hello Jisu
     println("Hello $name! My name has ${name.length} characters." ) // Hello Jisu! My name has 4 characters.
  ```
## 배열 (Array)  
  arrayOf() 로 생성, 함수로도 가능
  ```kotlin
     var array = arrayOf(1,2,3)
     val squares = Array(5, {k -> k * K})
  ```  
  iterator, size, get, set 지원, **[ ]** 지원  
  BooleanArray, CharArray, ByteArray, ShortArray, IntArray, LongArray, FloatArray, DoubleArray  
## 범위 (Range)  
  시작값, 끝나는값, 사이의 간격. **..** 연산자 사용  
  **in** 연산자 사용 가능 : 포함여부 확인  
  ```kotlin
     val aToz = "a".."z"
     val isTrue = "c" in aToz
     val oneToNine = 1..9
     val isFalse = 11 in oneToNine
  ```
  * 라이브러리 함수  
  downTo() : 숫자를 하나씩 내리는 방식으로 범위 생성    
  rangeTo() : 숫자를 하나씩 올리는 방식으로 범위 생성  
  step() : 범위 내에 있는 연속정인 항의 델타 값, 감소는 안됨.
  reversed() : 범위 반전.
  ```kotlin
     val countingDown = 100.downTo(0)  // 100, 99, 98, ... , 1, 0 
     val rangeTo = 10.rangeTo(20)      // 10, 11, 12, ... , 19, 20
     val oneToFifty = 1..50   
     val oddNumber = oneToFifty.step(2) // 1, 3, 5, ... , 49
     val countingDownEvenNumbers = (2..100).step(2).rebersed() // 100, 98, 96, ... , 4, 2
  ```
## 루프
  * while
  ```kotlin
     while(true) {
        // ...
     }
  ```
  * for : 이터레이터(iterator) 이름의 함수나 확장 함수를 정의한 객체를 반복하는데 사용, in 키워드 사용
  ```kotlin
     val list = listOf(1, 2, 3, 4)
     for (k in list) {
        println(k)
     }
     // String 은 iterator 확장 함수를 제공
     val string = "print characters"
     for (char in string) {
        println(char)
     }
     // 배열은 indices 확장 함수로 인덱스 반복
     for (index in array.indices) {
        println("E $index is ${array[index]}")
     }
  ```
## 예외처리
모든 예외는 확인되지 않은 예외(unchecked exception)  
try, catch, finally 블록 사용  
catch, finally 는 적어도 1개이상  
finally는 예외 발생 여부와 관계 없이 항상 실행  
## 클래스 인스턴스화하기
new 키워드 없이 가능
```kotlin
   val file = File("/etc/nginx/nginx.conf")
   val date = BigDecimal(1000)
```
## 참조 동등성과 구조 동등성
참조 동등성(**===**) : 두 개읭 각기 다른 참조가 메모리상 같은 인스턴스를 가리키는 경우 (자바의 **==**)  
구조 동등성(**==**) : 메모리상 두 객체는 다른 인스턴스지만 같은 값을 가지고 있는 경우 (자바의 **equals**)
```kotlin
   val a = File("/kotlin.doc")
   val b = File("/kotlin.doc")
   val sameRef = a === b        // 참조 동등성은 false
   val structural = a == b      // 구조 동등성은 true
```
## this 표현식
현재 수신자(current receiver)  
클래스의 맴버에서 this 는 클래스 인스턴스를 참조  
확장 함수에서 this는 확장 함수가 적용된 인스턴스를 참조  
## 가시성 제어자
public, internal, protected, private  
코틀린은 기본 값은 **public**
private : 같은 파일에서만 접근 가능
protected : 최상위 함수, 클래스, 인터페이스 객체는 protected 로 선언 할 수 없음. 서브클래스 맴버까지만 접근 가능
internal : 모듈 개념, 같은 모듈에서는 public처럼 동작 
## 표현식으로서의 흐름 제어
if / else, try / catch 흐름 제어 블록은 표현식으로 사용 가능
```kotlin
   fun isZero(x: Int): Boolean {
      return if (x == 0) true else false
   }

   val success = try {
      readFile()
      true
   } catch(e: IOException) {
      false
   }
```
## 널 문법
널 값을 지정할 수 있는 변수는 **?** 와 함께 선언
```kotlin
   val str: String? = null 
```
is 연산자 : 타입 확인 (자바의 **instanceof**), 타입이 유효하지 않으면 ClassCastException 발생, 암시적으로 형변환  
변수를 검사한 시점부터 사용하는 시점동안 값이 변하지 않음을 컴파일러가 보장할 수 있는 변수로 제한
```kotlin
   fun isString(any: Any): Boolean {
     return if(any is String) true else false
   }
   fun printStringLength(any: Any) {
     if (any is String) {
       println(any.length)   // 암시적으로 형변황
     }
   }
```
as 연산자 : 명시적 형변환 as? 연산자 : 형변환 실패시 널 y값을 전달 받음
```kotlin
   val any = "/home/users"
   val string: String? = any as? String
   val file: File? = any as? File       // null
```
## when 표현식
* when(값)  
   자바의 switch 문과 유사, 마지막은 반드시 else 로, 표현식으로 사용 가능, 분기 코드가 같으면 묶을 수 있음
   같은 타입을 반환하는 함수, 범위, 컬랙션 존재 확인(in 연산자)  
   ```kotlin
      fun whatNumber(x: Int) {
        when(x) {
          0 -> println("x is zero")
          1 -> println("x is one")
          else -> println("x is neither 0 nor 1")
        }
      }
  
      fun isMinOrMax(x: Int): Boolean {
        val isZero = when(x) {
          Int.MIN_VALUE -> true
          Int.MAX_VALUE -> true
          else -> false
        }
        return isZero;
      }
  
      fun isZeroOrOne(x: Int): Boolean {
        return when(x) {
          0, 1 -> true
          else -> false 
        }
      }
  
      fun isAbs(x: Int): Boolean = {
        return when(x) {
          Math.abs(x) -> true
          else -> false
        }
      }
      
      fun isSingleDigit(x: Int): Boolean = {
        return when(x) {
          in -9..9 -> true
          else -> false
        }
      }
  
      fun isDieNumber(x: Int): Boolean = {
        return when(x) {
          in listOf(1, 2, 3, 4, 5, 6) -> true
          else -> false
        }
      }
   ```
 * 인자가 없는 when  
   if / else 구문 대체
   ```kotlin
      fun whenWithoutArgs(x: Int, y: Int) {
        when {
          x > y -> println("x is less then y")
          x < y -> println("x is greater then y")
          else -> println("x equal y")
        }
      }
   ```
## 함수 반환
return 키워드와 함께 사용  
클로저로부터 값을 반환해야 한다면, @로 단서를 달아야 함
```kotlin
   fun printUntilStop() {
     val list = listOf("a", "b", "stop", "c")
     list.forEach stop@ {
       if (it == "stop") return@stop
       else println(it)
     }
   } 
```
암시적 레이블이 상요되는 경우 레이블을 명시하지 않아도 됨. 함수명으로
```kotlin
   fun printUntilStop() {
     val list = listOf("a", "b", "stop", "c")
     list.forEach stop@ {
       if (it == "stop") return@stop
       else println(it)
     }
   } 
```
## 타입체계
Any : 최상위 타입, 자바의 Object 타입과 유사  
Unit : 자바의 void 와 유사, 아무런 값을 반환하지 않는 다는 사실을 알리는 용도  
Nothing : 모든 타입의 서브 클래스
