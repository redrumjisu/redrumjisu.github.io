# 데이터 클래스
데이터 컨테이너에 불과한 타입  
```java
   public class BlogEntryJ {
     private final String title;
     private final String description;
     private final DateTime publishTime;
     private final Boolean approved;
     private final DateTime lastUpdated;
     private final URI url;
     private final Integer comments;
     private final List<String> tags;
     private final String email;
     
     public BlogEntryJ(String title, String description, DateTime publishTime, Boolean approved, 
            DateTime lastUpdated, URI url, Integer comments, List<String> tags, String email) {
       this.title = title;
       this.description = description;
       this.publishTime = publishTime;
       this.approved = approved;
       this.lastUpdated = lastUpdated;
       this.url = url;
       this.comments = comments;
       this.tags = tags;
       this.email = email;
     }
     public String getTitle() {
       return title;
     }
     // ...
   }
```
```kotlin
   data class BlogEntry(var title: String, var description: String, var publishTime: DateTime, 
        var approved: Boolean?, var lastUpdated: DateTime, var url: URI, var comments: Int?,
        var tags: List<String>, var email: String?) 
```
## 게터와 세터의 자동 생성
컴파일러는 생성자에 있는 var 선언을 위해 자동으로 게터와 세터를 생성함  
checkParameterIsNotNull로 널 암시적 검사, 널 값 참조 여부에 따라 생략하기도 함. 
## copy 메소드
clone 과 유사하지만 새로운 인스턴스의 어떤 필드도 변경 가능
```kotlin
   blogEntry.copy(title = "Properties in kotlin", description = "Properties are awesome in kotlin")
   // blogEntry에서 title 과 description 만 변경된 새로은 인스턴스 생성
```
## toString 바로 사용하기
새로운 필드를 추가 하거나 삭제하면 코드가 자동으로 갱신 됨  
StringBuilder의 인스턴스에 FIELD=VALUE 를 덧붙임
## 사용자를 위해 생성된 hashCode와 equals 메소드
hashCode와 equals의 조건  
* hashCode는 객체가 변하지 않는 한 항상 같은 값을 반환해야 한다.
* 두 객체의 equals메소드가 참일 경우, hashCode는 같다.
* 두 객체의 equals메소드가 거짓일 경우, hashCode는 같을 수도 있다.

역시 변경이 되면 자동으로 갱신 됨.
## 비구조화 선언
데이터 클래스 필드에 상응 하는 componetN 메소드 생성
```kotlin
   val (title, descrption, publishTime, approved, lastUpdated, url, comments, tags, email) = blogEntry
``` 
## 비구조화 타입
데이터 클래스를 사용하지 않고 비구조화 방식을 사용가능  
operator 키워드로 componentN 메소드를 구현
```java
   public class Sensor {
     private final String id;
     private final double value;
     public Sensor(String id, double value) {
       this.id = id;
       this.value = value;
     }
   }
```
```kotlin
   operator fun Sensor.component1() = this.id
   operator fun Sensor.component2() = this.value
```
## 데이터 클래스 정의 규칙
* 기본생성자는 적어도 하나의 매개변수를 가져야 한다
* 모든 기본생성자의 매개변수는 val / var 로 지정되어야 한다
* 데이터 클래스는 abstract, open, sealed, inner 클래스가 될 수 없다
* 데이터 클래스는 다른 클래스를 확장할 수 없다 (인터페이스를 구현할 수 있음)
## 한계
데이터 클래스를 정의할 떄 다른 클래스를 상속할 수 없다.  
