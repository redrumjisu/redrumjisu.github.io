https://dartpad.dev

var : 상수  
dynamic : 변수

void f(int a, int b, [int c = 0])
void g(int a, int b, [int? c])
디폴드 값을 주거나, nullabe 로 처리해야함 

리스트 초기화 
final growableList = List.empty(growable: true); // []
growableList.add(1); // [1]

final fixedLengthList = List.empty(growable: false);
fixedLengthList.add(1); // error

foreach(() => {}) : 배열의 각 요소에 대해서 함수 실행

map(() => {}) : 배열의 각 요소를 변환하고 새 목록으로 반환

where(() => 조건문) : 조건문을 만족하는 요소만 반환

class 
```dart
class Class {
    late String param1;
    late int param2;
    late bool _param3;
    
    void set param3(bool b) {
        _param3 = b
    }
    bool get param3() => _param3
    
    Class(String param1, int param2) {
        this.param1 = param1;
        this.param2 = param2;
    }
    
    Class([String param1, int param2]) {
        this.param1 = param1;
        this.param2 = param2;
    }
    
    Class(this.param1, this.param2);
    
    Class.empty();
}
```
* _로 시작하면 private 변수 : getter / setter 가 필요함.
* 대괄호 ([]) 로 매개변수가 묶이면 선택임.
* 빈 생성자 필요
* 매개변수에 this로 바로 객체에 할당.

