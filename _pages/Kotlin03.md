#코틀린과 객체 지향 프로그래밍
고차함수(higher-order function), 람다식(lambda), 함수형 프로그래밍  
객체지향프로그래밍 : 캡슐화, 상속, 다형성

## 클래스
타입의 데이터와 행위  
같은 소스에서 여러 개의 클래스 정의 가능  
객체 : 클래스 정의에 대한 런타임 인스턴스, 생성자를 호출  
constructor 키워드, init 블록  
* 접근 레벨 : internal, private, prodected, 기본은 public
* 중첩 클래스 : OuterClass의 접근레벨을 따름  
정적 중첩 클래스 : static 키워드 
내부 중첩 클래스 : 비정적으로 선언, OuterClass의 인스턴스 필요, inner 키워드
* this@label : 바깥 스코프 참조
  ```kotlin
    class A {
      private val someFiled: Int = 1
      inner class B {
        private val someFiled: Int = 2
        fun foo(s: String) {
          println("Field <somefiled> from B" + this.somfiled)
          println("Field <somefiled> from B" + this@B.somfiled)
          println("Field <somefiled> from A" + this@A.somfiled)
        }
      }
    } 
  ```
* 데이터 클래스 : 데이터를 저장하기 위한 목적, data 키워드
* 열거형 클래스 : enum 키워드, 생성자 매개변수 가능
* 정적 메소드, 컴패니언 오브젝트 : 클래스를 위한 정적 메소드를 지원하지 않음  
companion object 로 지정하면 정적 메소드를 호출 가능
   ```kotlin
      object Singleton {
        private var count = 0
        fun doSomething(): Unit {
          println("doSomeThing")
        }
      }
      
      interface StudentFactory {
        fun create(name: String): Student
      }
      class Student private constructor(val name: String) {
        companion object: StudentFactory {
          override fun create(name: String): Student {
            return Student(name)
          }
        }
      }
   ```
## 인터페이스
계약, 연관된 기능의 집합, 프로퍼티는 가질수 있음
```kotlin
   interface Document {
     val version: Long
     val size: Long
     val name: String
     get() = "NoName"
      
     fun save(input: InputStream)
     fun load(stream: OutputStream)
     fun getDescription(): String { return "Document $name has $size byte(-s)"}
   }

   class DocumentImpl: Document {
     override val size: Long
     get() = 0
     override fun save(input: InputStream) {}
     override fun load(stream: OutputStream) {}
     override val version: Long
     get() = 0
   }
```
## 상속
기존 클래스를 재활용 또는 확장해서 새로운 클래스를 생성  
생성자가 가장 먼저 해야 하는 일은 부모 생성자를 호출하는 일  
인터페이스는 여러 개 상속 가능
```kotlin
   enum class CardType {
     VISA, MASTER, AMEX
   }
   open class Payment(val amount: BigDecimal)
   class CardPayment(amount: BigDecimal, val number: String
       , val expiryDate: DateTime, val type: CardType): Payment(amount)

   class ChequePayment: Payment {
     constructor(amount: Bigdecimal, name: String, bankId: String): super(amount) {
       this.name = name
       this.bankId = bankId
     }
     var name: String
     var bankId: String
   }
```
## 가시성 제어자
public, internal, protected, private
부모 클래스가 open 이면 가시성 레벨 변경 가능.
## 추상 클래스
absract 키워드  
추상 클래스는 인스턴스를 생성할 수 없음  
재정의 가능한 함수는 abstract로 표시 가능
```kotlin
   open class Parent protected constructor() {
     open fun someMethod(): Int = Random().nextInt()
   }
   abstract class Derived: Parent {
     abstract override fun someMethod(): Int
   }
   class AlwaysOne: Derived {
     override fun someMethod(): Int = { return 1}
   }
``` 
## 인터페이스 VS 추상 클래스
* Is-a (추상 클래스) / Can-Do (인터페이스) 
* 코드 재사용 촉진 : 공통된 기능이 있을 경우(추상 클래스)
* 버전 관리 : 새로운 맴버가 추가 되는 경우 (추상 클래스)
## 다형성 (!)
'어떻게'로부터 '무엇을' 분리한다. - 코드 조직화와 가독성이 향상
지연 바인딩 (동적 바인딩, 런타임 바인딩)
## 오버라이딩 규칙
재정의 가능한 메소드 open 키워드 추가  
재정의 할때는 override 키워드 추가  
프로퍼티도 오버라이딩 가능 : val -> var  
## 상속 VS 합성 (!)
연관관계 (has-a 관계) : 집합, 합성, 상속  
## 클래스 델리게이션 (!)
타입이 하나 이상의 메소드 호출을 다른 타입으로 전달
## 봉인 클래스
서브클래스를 봉인 클래스 자신 내부에 중첩 클래스로 정의
```kotlin
   sealed class IntBinaryTree {
     class EmptyNode: IntBinaryTree()
     class IntBinaryTreeNode(val left: IntBinaryTree, val value: Int, val right: IntBinaryTree): IntBinaryTree()
   }
   val tree = IntBinaryTree.IntBinaryTreeNode(
        IntBinaryTree.IntBinaryTreeNode(IntBinaryTree.EmptyNode(), 1, IntBinaryTree.EmptyNode()),
        10, 
        IntBinaryTree.EmptyNode())
   fun toCollection(tree: IntBinaryTree): Collection<Int> = when(tree) {
     is IntBinaryTree.EmptyNode -> emptyList<Int>()
     is IntBinaryTree.IntBinaryTreeNode -> toCollection(tree.left) + tree.value + toCollection(tree.right) 
   }
```  

