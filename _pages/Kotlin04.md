# 코틀린과 함수
## 함수 정의 하기
매계변수, 반환 값, **fun** 키워드 사용 정의
```kotlin
   fun hello() : String = "Hello"
   fun hello(name: String): String = "Hello $name"
```
반환되는 값이 없을 경우 Unit 타입으로
```kotlin
   fun print1(str: String): Unit { println(str) }
   fun print2(str: String) { println(str) } 
```
## 단일 표현식 함수
함수가 단일 표현식으로 이뤄졌을 떄는 반환타입, 중괄호, return 생략  
```kotlin
   fun square(k: Int) = k * k
```
## 맴버 함수
클래스나 객체, 인터페이스 내부에 선언. 인스턴스가 필요  
맴버 함수끼리 참조할 떄는 인스턴스 이름이 필요하지 않음
```kotlin
   val str = "hello"
   val length = str.take(5)

   object Rectangle {
     fun printArea(width: Int, height: Int): Unit {
       val area = calculateArea(width, height)
       println("Area : $area")
     }
     fun calculateArea(width: Int, height: Int): Int {
       return width * height
     }
   }
```
## 지역 함수
함수 내부에 함수를 선언, 중첩 함수  
스코프 바깥에 선언한 매개변수나 변수에 접근 가능 
```kotlin
   fun printArea(width: Int, height: Int): Unit {
     fun calculateArea(width: Int, height: Int): Int = width * height
     val area = calculateArea(width, height)
     println("Area : $area")
   }

   fun printArea2(width: Int, height: Int): Unit {
     fun calculateArea(): Int = width * height
     val area = calculateArea()
     println("Area : $area")
   }
```
## 최상위 함수
클래스, 객체, 인터페이스 바깥에 존재하는 함수  
파일 내부에서 바로 정의  
도우미 함수, 유틸리티 함수 정의 - 자바 표준 라이브러리 컬랙션 함수  
## 이름이 있는 매개변수
함수에 인자를 전달할 때, 이름을 명시 - 인자의 의도가 명확해짐.  
매개변수 순서 변경 가능 
```kotlin
   val str = "a kindness of ravens"
   str.regionMatches(14, "Red Ravens", 4, 6, true)
   str.regionMatches(thisOffset = 14, other = "Red Ravens", otherOffset = 4, length = 6, ignoreCase = true)
```
## 기본 값을 갖는 매개변수
다수의 매개변수를 갖는 함수에서 기본 값을 설정해 줌으로 보일러플레이트를 줄일 수 있음.  
이름이 있는 매개변수와 혼합하여 사용
```java
   public BigDecimal divide(BigDecimal divisor)
   public BigDecimal divide(BigDecimal divisor, RoundingMode roundingMode)
   public BigDecimal divide(BigDecimal divisor, int scale, RoundingMode roundingMode)
```
```kotlin
   fun divide(dicisor: BigDecimal, scale: Int = 0, roundingMode: RoundingMode = RoundingMode.UNNECESSARY) : BigDecimal
```
## 확장함수(!)
필요로 하는 새로운 함수를 포함하는 서브타입을 생성해서 타입을 확장하는 방법
* 확장함수 우선순위
* 널 값에서의 확장함수
* 맴버 확장 함수
* 맴버 확장 함수 오버라이딩
* 컴패니언 오브젝트 확장
* 다중 값 반환
* 중위 함수
## 연산자
기호를 사용하는 함수  
대부분의 연산자는 중위 표현식과 결합  
* 연산자 오버로딩
operator 키워드와 이름과 일치하는 영어 단어 사용.  

* 기본 연산자

|연산|함수 이름|
|:---:|:---:|
|a + b|a.plus(b)|
|a - b|a.minus(b)|
|a * b|a.times(b)|
|a / b|a.div(b)|
|a % b|a.mod(b)|
|a..b|a.rangeTo(b)|
|+a|a.unaryPlus()|
|-a|a.unaryMinus()|
|!a|a.not()|

* in / contains
* get / set : 배열에서 괄호 접근
* invoke : 클래스를 함수처럼 보이도록. 타입과 매개변수를 변경하여 오버로딩 가능
  ```kotlin
     class RandomLongs(seed: Long) {
       private val random = Random(seed)
       operator fun invoke(): Long = random.nextLong()
     }
     
     fun newSeed(): Long = SEED
     val random = RandomLongs(newSeed())
     val longs = listOf(random(), random(), random()) // 함수처럼 사용   
  ```
* 비교 : compareTo, Comparator 인터페이스와 일치해야 함
* 할당

|연산|함수 이름|
|:---:|:---:|
|a += b|a.plusAssign(b)|
|a -= b|a.minusAssign(b)|
|a *= b|a.timesAssign(b)|
|a /= b|a.divAssign(b)|
|a %= b|a.modAssign(b)|
## 함수 리터럴
코드를 중괄호로 감싸면 리터럴이 됨.  
괄호를 사용해 사용할 수 있음.  
매개변수는 화살표 앞에 타입과 함꼐 작성   
```kotlin
   { println("function literal") }
   
   val printHello = { println("hello") }
   printHello()

   val printMsg = { msg: String -> println(msg)}
   printMsg("Hello world")
```
## 꼬리 재귀 함수
특정 함수에 있는 마지막 연산이고 호출 결과가 값을 반환하는 것 : 스택 프레임을 유지할 필요가 없음.  
tailrec 키워드 사용
```kotlin
   // 일반 재귀 함수
   fun fact(k: Int): Int {
     if (k == 0) return 1
     else return k * fact(k - 1)
   }
   // 꼬리 재귀 함수
   fun fact(k: Int) {
     tailrec fun factTail(m: Int, acc: Int): {
       if (m == 0) return acc
       else return factTail(m - 1, m * acc)
     }
     return factTail(k, 1)
   }
```
## 가변 인자
vararg 키워드 사용  
맨 마지막 매개변수를 사용  
이름있는 매개변수를 사용해서 인자 전달 가능
전개 연산자 : 배열의 요소를 풀어서 개별적인 인자로 전달
```kotlin
   fun multiprint(vararg strings: String): Unit {
     for (string in strings) {
       println(string)
     }
   }
   multiprint("a", "b", "c")

   fun multiprint(prefix: String, vararg strings: String, suffix: String): Unit {
     println(prefix)
     for (string in strings) {
       println(string)
     }
     println(suffix)
   }
   multiprint("Start", "a", "b", "c", suffix = "End")
   // 전개연산자
   val strings = arrayOf("a", "b", "c", "d", "e")
   multiprint("Start", "strings", suffix = "End")
```
## 표준 라이브러리 함수
* apply : 수신자와 함께 호출되는 람다식을 받음, 수신자는 apply를 호출하는 인스턴스
```kotlin
   val task = Runnable { println("Running") }
   Thread(task).apply { setDaemon(true) }.start()

   val thread =  Thread(task)
   thread.setDeamon(true)
   thread.start()
```
* let : 클로저 자신의 값을 반환
```kotlin
   val outputPath = Paths.get("/user/home").let {
     val path = it.resolve("output")
     path.toFile().createNewFile()
     path
   }
```
* with : 수신자와 수신자에서 동작하기 위한 클로저를 받음.
```kotlin
   val g2: Graphics2d = ...
   
   g2.storke = BasicStroke(10F)
   g2.setRenderingHint(RenderringHints.KEY_ANTIALIASING, RenderringHints.VALUE_ANTIALIASING_ON)
   g2.setRenderingHint(RenderringHints.KEY_DITHERING, RenderringHints.VALUE_DITHERIN_ENABLE)
   g2.background = Color.BLACK 
  
   with(g2) {
     storke = BasicStroke(10F)
     setRenderingHint(RenderringHints.KEY_ANTIALIASING, RenderringHints.VALUE_ANTIALIASING_ON)
     setRenderingHint(RenderringHints.KEY_DITHERING, RenderringHints.VALUE_DITHERIN_ENABLE)
     background = Color.BLACK
   }
```
* run : with와 let 의 결합한 확장함수
```kotlin
   val outputPath = Paths.get("/user/home").let {
     val path = resolve("output")
     path.toFile().createNewFile()
     path
   }
```
* lazy : 비용이 많이 드는 함수 호출을 최초 요청할 경우 실행되도록 감싼 함수
```kotlin
   fun readString(): String = ... // 비용이 많이 드는 동작
   val lazyString = lazy {readString()}
   
   val string = lazyString()
```
* use : try-whih-resourc와 비슷. Closable 인스턴스의 확장
```kotlin
   val input = Files.newInputStream(Paths.get("input.txt"))
   val byte = input.use({ input.read() })
```
* repeat : k 횟수 만큼 함수 리터럴을 호출
```kotlin
   repeat(10, { println("Hello") })
```
* require / assert  / check  
정형화된 명세(항상 참 또는 거짓)를 프로그램에 추가  
require - 예외 발생, 입력 조건과 일치함을 보장  
assert - AssertionException 발생, 내부 상태가 일관됨을 보장  
check - IllegalStateException 발생, 내부 상태가 일관됨을 보장
## 제네릭 함수
어떤 타입에서든 동작할 수 있는 함수를 작성  
타입 매개변수 정의  
```kotlin
   fun <T> printRepeated(t: T, k: Int): Unit {
     for (x in 0..k) {
        println(t)
     }
   }
```
## 순수 함수
함수는 항상 같은 입력 값에 같은 출력 값을 반환해야만 한다.  
함수는 어떠한 부수효과도 만들어내서는 안된다.

