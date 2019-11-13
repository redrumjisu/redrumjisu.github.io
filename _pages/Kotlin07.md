# 널 안정성, 리플렉션, 애노테이션
타입 시스템에서부터 널 안정성을 가지고 있음  
## 널을 허용하는 타입
널을 가질 수 있도록 허용함 : **?** 접미사 추가. : 
```kotlin
   var name: String = null // 컴파일 오류
   var name: Stirng? = null
```
매개변수에 사용 가능 - NullPointerException 발생하지 않음 보장.
## 똑똑한 형변환
if 표현식에 조건을 추적하여 변수에 접근 가능
```kotlin
   fun getName(): String? = ...   // 널을 허용하는 타입
   
   val name = getName()   // name - 널을 허용하는 타입
   if (name != null) {    // name이 널이 아니면
     println(name.length) // name이 널을 허용하지 않는 타입으로 똑똑한 형변환
   }
```
## 안전한 널 접근
널 접근 연산자 : **?** 
```kotlin
   class Person(name: String, val address: Address?)
   class Address(name: String, val postcode: String, val city: City?)
   class City(name: String, val country: Country?)
   class Country(val name: String)

   fun getCountryName(person: Person?): String? {
     var countryName: String? = null
     if (person != null) {
        val address = person.address
        if (address != null) {
          val city = address.city
          if (city != null) {
            val country = city.country
            if (country != null) {
               countryNAme = country.name
            }
          }
        }
     }
     return countryName
   }

   fun getCountryNameSafe(person: Person?): String? = person?.address?.city?.country?.name
```
컴파일러의 검사를 생략하고 널을 허용하는 타입을 널을 허용하지 않는 타입으로 강제 : **!!** 연산자  
변수가 널일 경우 NullPointerException 발생
```kotlin
   val nullableName: String? = "Not Null"
   val name: String = nullableName!!
```
## 엘비스 연산자
널이 아닌 경우에는 값을 사용하고 그렇지 않은 경우에는 기본 값 사용 : **?:** 연산자
```kotlin
   val nullableName: String? = ...
   val name: String = nullableName ?: "defaultName"
```
오른쪽은 표현식, 연쇄적인 연산도 가능
```kotlin
   val nullableAddress: Address? = null
   val postcode: String = nullableAddress?.postcode ?: "defaultPostcode"
```
## 안전한 형변환
타입을 안전하게 형변환 할 경우 : **as?** 연산자 사용
```kotlin
   val location: Any = "London"
   val safeString: String? = location as? String
   val safeInt: Int? = location as? Int
```
## 옵셔널
함수 또는 표현식이 값을 반환하거나 반환하지 않는다는 것을 나타냄  
옵셔널 생성 : Optional.of()  
빈 옵셔널 생성 : Optional.empty()
```kotlin
   val optionalName: Optional<String> = Optional.of("William")
   val empty: Optional<String> = Optional.empty() 
```
옵셔널에서 값을 추출 : get / orElse
옵셔널에서 값을 변환 : map / flatmap
## 리플렉션
컴파일 타임에 세부사항을 알지 못한 상태에서 클래스의 인스턴스를 생성 / 함수 검색 및 호출 / 애노테이션, 필드, 매개변수, 제네릭 확인
* KClass : 클래스를 나타내는 타입 **::class** 문법 사용
```kotlin
   val name = "Name"
   val kclass: KClass<String> = name::class
   val kclass2: KClass<String> = String::class
```
* 리플렉션을 사용한 인스턴스화 : createInstance 함수 사용. 매개변수를 사용하지 않음.
```kotlin
   class PostiveInteger(value: Int = 0)
   fun createInteger(kclass: KClass<PositiveInteger>): PositiveInteger = kclass.createInstance()

   interface Ingester {
     fun ingest(): Unit
   }
   val props = Properties()
   props.load(Files.newInputStream(Paths.get("/some/path/ingesters.props")))
   val classNames = (props.getProperty("ingesters") ?: "").split(',')
   val ingesters = classNames.map {
     Class.forName(it).kotlin.createInstance() as Ingester  // 형변환 필요
   }
   ingesters.forEach { it.ingest()}
```
## 생성자(!)
constructors 프로퍼티 사용 - 모든 생성자의 목록을 반환  
callBy로 인스턴스화하기 - 적절한 인자를 반사적으로 만들어냄. KParameter 인스턴스로 이루어진 컬랙션  
1.  Class.forName : KClass 인스턴스 참조
1. 매개변수 검색
1. KParameter 타입 점검 / 매개변수 맵 생성
1. callBy로 전달. 인스턴스 생성
## 객체와 컴패니언
kclass.companionObject 로 참조  
kclass.companionObjectInstance 로 컴패니언 오브젝트의 인스턴스 가능
## 유용한 KClass 프로퍼티
typeParameters : 클래스에서 정의한 매개변수의 타입  
superclasses : 바로 위의 슈퍼클래스의 인스턴스 목록  
allSuperClassses : 슈퍼클래스의 전체목록  
## 리플렉션한 함수와 프로퍼티
memberFunctions : KClass에서 클래스에 있는 각 함수에 대한 반환  
memberExtensionFunctions : 확장 함수에 대한 반환  
functions : 같은 목록 안에 비확장 / 확장 함수를 모두 반환
memberProperties : 프로퍼티 참조
memberExtensionProperties : 확장 프로퍼티 참조  
call : 해당 함수를 호출하는데 사용  
declaredXXX : 타입 자신에 선언된 함수와 프로퍼티 반환
## 애노테이션
컴파일 단계에서 클래스, 인터페이스, 매개변수 등에 별도의 의미를 추가함  
```kotlin
   annotaion class Foo
```
|대상|예제|
|:---:|---|
|클래스|@Foo class MyClass|
|인터페이스|@Foo interface MyInterface|
|오브젝트|@Foo object MyObject|
|매개변수|fun bar(@Foo param: Int): Int = param|
|함수|@Foo fun foo(): Int = 0|
|타입 앨리어스|@Foo typealias MYC = MyClass|
|프로퍼티|class PropertyClass {@Foo var name: String? = null}|
|생성자|class Bar @Foo constructor(name: String)|
|표현식|val str = @Foo "hello foo"|
|반환 값|fun expressionAnnotation(): Int { return (@Foo 12)}|
|함수 리터럴|@Foo { it.size > 0 }|

애노테이션을 사용하기 전에 @Target 을 사용해 허용되는 대상을 명시해야 함  

* 메타 애노테이션  

|이름|사용법|
|---|---|
|@Retention|결과로 생성된 애노테이션이 어떻게 저장 되는지 결정<br><br>소스 : 컴파일단계에서 삭제<br>바이너리 : 클래스파일에 보이지만 리플렉션에서 보이지 않음<br>런타임 : 클래스파일에 저장되고 리플렉션에서도 보임|
|@Repeatable|특정 대상에 두 번 이상 포함가능|
|@MustBeDocumented|Dokka로 문서 생성시 포함됨|
매개변수를 가질 수 있음(val 로 선언)
## 표준라이브러리 애노테이션
* @JvmName
* @JvmStatic
* @Throws
* @JvmOverloads   
## 런타임에서 애노테이션 발견하기 
KClass, KFunction, KParameter, KProperty 에서 annotaion 프로퍼티 사용  
커스텀 애노테이션은 @Retention 에서 런타임 옵션을 가져야 확인 가능
