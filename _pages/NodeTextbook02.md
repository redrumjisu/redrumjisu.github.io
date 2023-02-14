
# ES2015+

## const, let

var : 함수 스코프  
const : 블록 스코프 (상수)  
let : 블록 스코프 (변수)

```javascript
if (true) {
    var x = 3;
}

console.log(x) // 3

if (true) {
    const y = 3;
}

console.log(y); // Uncaught ReferenceError: y is not defined

const a = 0;
a = 1; // Uncaught TypeError: Assignment to constant variable

let b = 0;
b = 1;

const c; // Uncaught SyntaxError: Missing initializer in const declaration
```

## 템플릿 문자열

백틱(`) 으로 감싸 ${변수} 형식으로 넣을 수 있음. 

```javascript
var num1 = 1;
var num2 = 2;
var num3 = 3;
var string = num1 + ' 더하기 ' + num2 + ' 는 \'' + num3 + '\'';

const num1 = 1;
const num2 = 2;
const num3 = 3;
var string = `${num1} 더하기 ${num2}는 '${num3}'`
```
## 객체 리터럴

객체의 메서드에 함수를 연결할 때 클론(:)과 평션(function)을 붙이지 않아도 됨   
객체리터럴 안에서 동적 속성을 선언해도 됨.

```javascript
var sayNode = function () {
    console.log('node');
};
var oldObject = {
  sayJS: function () {
      console.log('JS');
  },
  sayNode: sayNode,  
};
oldObject[es + 6] = 'Fantastic';
oldObject.sayNode();
oldObject.sayJS();
console.log(oldObject.ES6);

const newObject = {
    sayJS() {
        console.log('JS')
    },
    sayNode,
    [es + 6]: 'Fantastic',
};
newObject.sayNode();
newObject.sayJS();
console.log(newObject.ES6)
```
## 화살표 함수
function 대신 => 기호로 함수 선언 
```javascript
function add1(x, y) {
    return x + y;
};
const add2(x, y) => {
    return x + y;
};

const add3 = (x, y) => x + y;
const add4 = (x, y) => (x + y);

function not1(x) {
    return !x;
};
const not2 = x => !x;

```
화살표 함수를 쓰면 상위 스코프의 this 를 그대로 사용할 수 있음.  
```javascript
var relationship1 = {
    name: 'zero',
    friends: ['nero', 'hero', 'xero'],
    logFriends: function() {
        var that = this;
        this.friends.forEach(function (friend){
            console.log(that.name, friend);
        });
    },
};

const relationship2 = {
    name: 'zero',
    friends: ['nero', 'hero', 'xero'],
    logFriends() {
        this.friends.forEach(friend => {
            console.log(this.name, friend);
        });
    },
};
```

## 구조분해 할당
객체와 배열애서 필요한 속성이나 요소를 꺼낼 수 있음. 
```javascript
const candyMachine = {
    status: {
        name: 'node',
        count: 5,
    },
    getCandy() {
        this.status.count--;
        return this.status.count;
    }
};
const { getCandy, status: {count}} = candyMachine;

var array = ['node.js', {}, 10, true];
var node = array[0];
var obj = array[1];
var bool = array[3];

const array = ['node.js', {}, 10, true];
const [node, obj, bool] = array
```

## 클래스

프로토타입 기반 문법  

생성자 함수는 constructor 안으로  
클래스 함수는 static 으로  
상속은 Object.create 에서 extends 로 
```javascript
class Human {
    constructor(type = 'human') {
        this.type = type;
    }
    static isHuman(human) {
        return human instanceof Human;
    }
    breath() {
        alert('h-a-a-m');
    }
}

class Zero extends Human {
    constructor(type, firstName, lastName) {
        super(type);
        this.firstName = firstName;
        this.lastName = lastName;
    }
    sayName() {
        super.breath();
        alert(`${this.firstName} ${this.lastName}`);
    }
}
const newZero = new Zero('human', 'Zero', 'Cho');
Human.isHuman(newZero);
```

## 프로미스
비동기 처리시 콜백 함수 대신 사용.

```javascript
const condition = true;
const promise = new Promise((resolve, reject) => {
    if (condition) {
        resolve('성공');
    } else {
        reject('실패');
    }
});

promise
    .then((message) => {    // resolve 한 경우
        console.log(message);
    })
    .catch((error) => {     // reject 한 경우
        console.error(error);
    })
    .finally(() => {        // 끝나면 무조건 실행
        console.log('무조건')
    })
```
콜백이 세번 중첩 되어 있고, 에러도 콜백마다처리 해야 하나, promise 로 순차적으로 실행하고 에러도 한번에 처리함. 
```javascript
function findAndSaveUser(Users) {
    Users.findOne({}, (err, user) => {
        if (err) {
            return console.error(err);
        }
        user.name = 'zero';
        user.save((err) => {
            if (err) {
                return console.error(err);
            }
            Users.findOne({ gender : 'm'}, (err, user) => {
                // do Somethings
            });
        });
    });
}

function findAndSaveUSer(Users) {
    Users.findOne({})
        .then((user) => {
            user.name = 'zero';
            return user.save();
        })
        .then((user) => {
            return Users.findOne({ gender : 'm'});
        })
        .then((user) => {
            // do Somethings
        })
        .catch(err => {
            console.error(err);
        });
}
```
프로미스가 여러개 있을 때 처리 방법 : Promise.all

모두 resolve 가 될 때 까지 기다렸다가 then 으로 넘어감.  
하나라도 reject 되면 catch로 넘어감.   

```javascript
const promise1 = Promise.resolve('성공1');
const promise2 = Promise.resolve('성공2');

Promise.all([promise1, promise2])
    .then((result) => {
        console.log(result);    // ['성공1', '성공2']        
    })
    .catch((error) => {
        console.error(error);
    });
```

## async / await

ES2017 에서 추가, 비동기 위주에서 많이 사용.   
함수를 async function 으로 선언  
프로미스에 await를 추가
reject은 try / catch로 처리 
=> 프로미스가 resolve 될 때까지 다음 로직으로 넘어감   

```javascript

async function findAndSaveUser(Users){
    try {
        let user = await Users.findOne({});
        user.name = 'zero';
        user = await user.save();
        user = await Users.findOne({ gender : 'm'});
    } catch (error) {
        console.error(error)
    }
    
}
```
for await of문 : ES2018 문법, 배열 순회 
```javascript
const promise1 = Promise.resolve('성공1');
const promise2 = Promise.resolve('성공2');

(async () => {
    for await (promise of [promise1, promise2]) {
        console.log(promise);
    }
})();
```

# 프런트엔드 자바스크립트
## AJAX
Asynchronous Javascript And XML   
페이지 이동 없이 서버에 요청을 보내고 응답을 받는 기술  
JSON 을 많이 사용
```javascript
// Get
(async () => {
    try {
        const result = await axios.get(`${url}`);
        console.log(result);
        console.log(result.data);
    } catch (error) {
        console.error(error);
    }
})();
// Post
(async () => {
    try {
        const result = await axios.post(`${url}`, {
            name : 'zerocho',
            birth : 1994
        });
        console.log(result);
        console.log(result.data);
    } catch (error) {
        console.error(error);
    }
})();
```
## FormData
HTML form 태그 데이터를 제어  
주로 AJAX와 함꼐 사용  
```javascript
(async () => {
    try {
        const formData = new FormData();
        formData.append('name', 'zerocho');
        formData.append('birth', 1994);
        const result = await axios.post(`${url}`, formData);
        console.log(result);
        console.log(result.data);
    } catch (error) {
        console.error(error);
    }
})();
```
## encodeURIComponent, decodeURIComponent
주소에 한글이나 특수문자가 들어가는 경우 처리

```javascript
encodeURIComponent('노드') // '%EB%85%B8%EB%93%9C'
decodeURIComponent('%EB%85%B8%EB%93%9C') // '노드'
```
## 데이터 속성 / dataset
data- 로 시작.  
data-id > id  
data-user-job > userJob으로   
dataset.monthSalary = 10000; > data-month-salary="10000" 속성으로 변경  

```html
<ul>
    <li data-id="1" data-user-job="programmer">Zero</li>
    <li data-id="2" data-user-job="designer">Nero</li>
    <li data-id="3" data-user-job="programmer">Hero</li>
    <li data-id="4" data-user-job="ceo">Kero</li>
</ul>
<script>
    console.log(document.querySelector('li').dataset)
</script>
```