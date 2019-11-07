# 좋은 커밋 메시지

[How to Write a Git Commit Message](https://chris.beams.io/posts/git-commit/)

## Commit 영어단어 사전
### FIX
올바르지 않은 동작을 고친 경우, 제일 많이 사용.
* Fix A : A를 수정합니다
* Fix A in B : B의 A 를 수정합니다
* Fix A which B / A that B : B인 A를 수정합니다
* Fix A to B / A to be B : B를 위해서 A를 수정합니다
* Fix A so that B : A를 수정해서 B가 되었습니다
* Fix A where B : B 처럼 발생하는 A를 수정했습니다
* Fix A when B : B일 때 발생하는 A를 수정했습니다
### ADD
코드, 문서 등이 추가가 있을 경우
* Add A : A를 추가합니다
* Add A for B : B를 위해 A를 추가했습니다
* Add A to B : B에 A를 추가했습니다
### REMOVE
코드의 삭제가 있을 경우
* Remove A : A를 삭제합니다
* Remova A from B : B에서 A를 삭제합니다
### USE
특별히 무언가를 사용해 구현을 하는 경우
* Use A for B : B에 A를 사용합니다
* Use A to B : B가 되도록 A를 사용합니다
* Use A in B : B에서 A를 사용합니다
* Use A instead of B : B대신 A를 사용합니다
### REFACTOR
전면 수정이 있을 경우
* Refactor A
### SIMPLIFY
복잡한 코드를 단순화 할 경우, Refactor 보다 약한 수정
* Simplify A
### UPDATE
개정이나 버전 업데이트의 경우. 문서, 라이브러리등 
* Update A to B : A를 B로 업데이트 합니다. / A를 B하기 위해 업데이트 합니다
### IMPROVE
향상이 있을 경우. 성능, 접근성, 호환성 등
* Improve A
### MAKE
기존 동작의 변경
* Make A B : A를 B하게 만듭니다
### IMPLEMENT
Add 보다 큰 단위의 추가의 경우, 클래스, 모듈 등, 목적이 필요할 경우 to 사용
* Implement A : A를 구현합니다
* Implement A to B : B 를 위해 A를 구현합니다
### REVISE
문서의 개정이 있을 경우
* Revise A : A 문서를 개정합니다
### CORRECT
문법 오류, 오타, 타입 변경, 이름 변경
* Correct A : A를 고칩니다
### ENSURE
확실하게 보장 받는 것을 명시, if
* Ensure A : A가 보장되도록 수정했습니다
### PREVENT
처리를 못하게 막음
* Prevent A : A하지 못하게 막습니다
* Prevent A from B : A를 B하지 못하게 막습니다
### AVOID
회피, 조건으로 제외시키는 경우
* Avoid A : A를 회피합니다
* Avoid A if B / A when B : B인 상황에서 A를 회피합니다
### MOVE
코드의 이동
* Move A to B / A int B : A를 B로 옮김니다
### RENAME
이름 변경
* Rename A to B : A를 B로 변경합니다
### ALLOW
허용 표현
* Allow A to B : A가 B할 수 있도록 허용합니다
### VERIFY
검증
* Verify A : A를 검증합니다.
### SET
작은 수정에 사용. 변수 값.
* Set A to B : A를 B로 설정합니다.
### PASS
파라미터 넘기는 처리
* Pass A to B : A를 B로 넘깁니다.


