# SQL 기본과 활용
## 1. SQL 기본
### 관계형 데이터베이스 (Relation Database)
릴레이션과 릴레이션의 조인 연산을 통해 합집합, 교집합, 차집합 등을 만들 수 있음.  
현재 기업에서 가장 많이 사용하는 데이터베이스  
데이터베이스 : 자료구조에 따라 계층형(트리 구조), 네트워크형(1대N, M대N), 관계형(릴레이션)
데이터베이스 관리 시스템(Database Management System) : 데이터베이스를 관리하기 위한 소프트웨어(DBMS) - Oracle, MS-SQL, MySQL, Sybase 등  

* 집합연산  
  합집합(Union) : 두 개의 릴레이션을 하나로 합하는 것, 중복된 튜플은 한번만 조회  
  차집합(Diference) : 본래 릴레이션에는 존재하고 다른 릴레이션에는 존재하지 않는 것 
  교집합(Intersection) : 두 개의 릴레이션에 공통된 것  
  곱집합(Cartesian product) : 각 릴레이션에 존재하는 모든 데이터 조합  

* 관계연산  
  선택 연산(Selection) : 조건에 맞는 튜플만을 조회  
  투영 연산(Projection) : 조건에 맞는 속성만을 조회  
  결합 연산(Join) : 여러 릴레이션의 공통된 속성을 사용해 새로운 릴레이션을 만들어 냄  
  나누기 연산(Division) : 기준에서 나누는 릴레이션이 가지고 있는 속성과 동일한 값을 가지는 튜플을 추출, 나누는 릴레이션의 속성 삭제, 중복된 튜플을 제거  

테이블 구조 : 릴레이션 -> 테이블
* 기본키 : 유일성(Unique), 최소성(Not null)
* 행(row) : 하나의 테이블에 저장되는 값, 튜플
* 칼럼(column) : 데이터를 저장하기 위한 필드, 속성
* 외래키 : 다른 테이블의 기본키를 참조(조인)하는 컬럼

### SQL종류
SQL(Structured Query Language) : 데이터의 구조를 정의, 데이터 조작, 데이터 제어. ANSI/ISO 표준  
* ANSI/ISO SQL 표준 : INNER JOIN, NATURAL JOIN, USING 조건, ON 조건절을 사용
* ANSI/ISO SQL3 표준 : DBMS 밴더별로 차이가 있었던 SQL을 표준화

|종류|설명|
|---|---|
|DDL(Data Definition Language)|데이터베이스의 구조를 정의<br>Create, Alter, Drop, Rename|
|DML(Data Manipulation Language)|테이블에서 데이터를 입력, 수정, 삭제, 조회<br>Insert, Update, Delete, Select|
|DCL(Data Control Language)|데이터베이스 사용자 권한 부여, 회수<br>Grant, Revoke|
|TCL(Transaction Control Language)|트랜잭션을 제어<br>Commit, Rollback|

트랜잭션 : 데이터베이스의 작업을 처리하는 단위  
원자성, 일관성, 고립성, 연속성  
SQL문 실행 순서 : 파싱(Parsing), 실행(Excution), 인출(Fetch)

### DDL(Data Definition Language)
테이블 생성 : CREATE TABLE  
* constrant로 기본키, 외래키 지정
* cascade : 참조하는 데이터도 자동 삭제 
```text
   CREATE TABLE EMP
   (
     empno number(10),
     ename varchar2(20),
     sal   number(10, 2) default 0,
     deptno varchar2(4) not null,
     createDate date default sysdate,
     constraint e_pk primary key(empno),
     constraint d_fk foreign key(deptno) references dept(deptno) ON DELETE CASCADE
   );
   
```
테이블 변경 : ALTER TABLE  
* 테이블명 변경 : ALTER TABLE ~ RENAME ~ TO 
* 칼럼 추가 : ALTER TABLE ~ ADD ()  
* 칼럼 변경 : ALTER TABLE ~ MODIFY ()   
* 칼럼 삭제 : ALTER TABLE ~ DROP COLUMN  
* 칼럼명 변경 : ALTER TABLE ~ RENAME COLUMN ~ TO

테이블 삭제 : DROP TABLE  
* 테이블의 구조와 데이터를 모두 삭제

뷰 생성과 삭제
* 뷰 : 테이블로부터 유도된 가상의 테이블  
* 특징 : 참조한 테이블이 변경되면 뷰도 변경됨. 뷰에 대한 입력, 수정, 삭제에는 제약이 발생, 보안성 향상  
```text
   CREATE VIEW V_EMP AS
   SELECT * FROM EMP;    // EMP 테이블에서 V_EMP 뷰 생성
   SELECT * FROM V_EMP;  // 일반 테이블 처럼 조회
   DROP VIEW V_EMP       // 뷰 삭제, 참조한 테이블이 삭제되지 않음
```

### DML(Data Manipulation Language)
데이터 입력 : INSERT
```text
   INSERT INTO table (column1, column2, ...) VALUES (expression1, expression2, ...);
   INSERT INTO EMP VALUES (100, '김지수')  // 모든 칼럼에 입력
   INSERT INTO DEPT_TEST
    SELECT * FROM DEPT;                   // SELECT문으로 입력 가능
``` 
데이터 수정 : UPDATE  
```text
   UPDATE EMP            // 수정되는 테이블
      SET ENAME = '지수'  // 변경되는 컬럽의 값
    WHERE EMPNO = 100;   // 수정 조건
```
데이터 삭제 : DELETE
```text
   DELETE FROM EMP
    WHERE EMPNO = 100;   // where절을 입력하지 않으면 모든 데이터가 삭제
```
* TRUNCATE TABLE table : 모든 데이터를 삭제하고 용량을 초기화 함

데이터 조회 : SELECT
```text
   SELECT *             // 조회를 원하는 칼럼을 선택
     FROM EMP           // 테이블 지정
    WHERE EMPNO = 100;  // 조회 조건
```
* ORDER BY : 오름차순(ASC), 내림차순(DESC), 정렬을 하기 떄문에 메모리를 많이 사용함
* INDEX : 정렬 회피
```text
  SELECT * /*+ INDEX_DESX(A) */ *
    FROM EMP A;     // EMPNO 로 생성된 인덱스를 내림차순으로 읽게 지정
```
* DISTINCT : 중복된 데이터를 한 번만 조회
* ALIAS : 별칭 (~ AS ~)

조건 : WHERE
* 비교연산자  
  = : 같은 것 조회  
  &lt; : 작은 것 조회  
  &lt;= : 작거나 같은 것 조회  
  &gt; : 큰 것 조회  
  &gt;= : 크거나 같은 것 조회  
* 부정 비교 연산자  
 !=, ^=, <>, NOT column = : 같지 않은 것 조회  
 NOT column &gt; : 크지 않은 것 조회  
* 논리 연산자  
  AND : 조건을 모두 만족해야 참  
  OR : 조건을 하나만 만족해도 참  
  NOT : 참은 거짓으로 거짓은 참으로 바꿈  
* SQL 연산자  
  LIKE '문자열' : 비교 문자열을 조회, '%' 는 모든 것, '_'는 단일 문자  
  BETWEEN A AND B : A와 B 사이의 값을 조회  
  IN (list) : OR의 의미, list 의 하나만 일치해도 조회  
  IS NULL : NULL 값 조회  
* 부정 SQL 연산자
  NOT BETWEEN A AND B : A와 B 사이에 해당되지 않는 값을 조회  
  NOT IN (list)|list불일치한 것 조회  
  IS NOT NULL|NULL이 아닌 값 조회  

* NULL : 모르는 값, 부재, 비교는 알수 없음
* NULL 함수  
  NVL(column, A) : NULL 이면 A를 반환하는 함수  
  NVL2(column, A, B) : NULL 이 아니면 A, NULL이면 B를 반환하는 함수  
  NULLIF(A, B) : A == B 이면 NULL, 다르면 exp1을 반환하는 함수  
  COALESCE(column, A) : NULL이 아니면 A를 반환
 
GROUP 연산
* GROUP BY : 소규모 행을 그룹화하여 합계, 평균, 최대값, 최소값등을 계산
* HAVING : GROUP BY 의 조건절
* ORDER BY 로 정렬 가능
* 집계함수  
  COUNT() : 행수 조회  
  SUM() : 합계 계산  
  AVG() : 평균 계산  
  MAX() / MIN() : 최대값 최소값 계산  
  STDDEV() : 표준편차 계산  
  VARIAN() : 분산 계산  
* COUNT : COUNT(*) - NULL 값 포함, COUNT(column) - NULL 값 제외

```text
   1. 부서별(DEPNO), 관리자별(MGR) 급여 평균 계산
   SELECT DEPTNO, MGR, AVG(SAL)
     FROM EMP
    GROUP BY DEPNO, MGR;
   2. 직업별(JOB), 급여 합계 중에 급여 합계(SAL)가 1000 이상인 직원
   SELECT JOB, SUM(SAL)
     FROM EMP
    GROUP BY JOB
   HAVING SUM(SAL) >= 1000;
   3. 사원번호(EMPNO) 가 1000 ~ 1003 부서별(DEPTNO) 급여(SAL) 합계
   SELECT DEPNO, SUM(SAL) FROM EMP
    WHERE EMPNO BETWEEN 1000 AND 1003
    GROUP BY DEPNO;
```
**SELECT 문 실행 순서 : FROM - WHERE - GROUP BY - HAVING - SELECT - ORDER BY**

형변환 : 두 개의 데이터의 타입이 일치하도록 변환
* 명시적형변환 : 형변환 함수를 사용 - TO_NUMBER(문자열), TO_CHAR(숫자나 날짜, format), TO_DATE(문자열, format)
* 암시적형변환 : DBMS에서 자동으로 형변환  

내장형 함수 - 형변환함수, 문자형 / 숫자형 / 날짜형 함수
* DUAL 테이블 : Oracle 데이터베이스에서 자동으로 생성되는 테이블, 모든 사용자가 사용가능
* 문자형 함수  
  ASCII(문자) : ASCII 코드 반환  
  CHAR(ASCII코드) : 문자로 반환  
  SUBSTR(문자열, n, m) : 문자열을 n번째 위치에서 m개 자름  
  CONCAT(문자열1, 문자열2) : 문자열 1과 2를 결합, Oracle에서는 '||' MS-SQL에서는 '+' 사용가능  
  LOWER(문자열) : 소문자로 변환  
  UPPER(문자열) : 대문자로 변환  
  LENGTH(문자열) / LEN(문자열) : 공백을 포함한 문자열의 길이  
  LTRIM(문자열, 지정문자) : 왼쪽에 지정된 문자를 삭제, 지정문자를 생략하면 공백을 삭제    
  RTRIM(문자열, 지정문자) : 오른쪽에 지정된 문자를 삭제, 지정문자를 생략하면 공백을 삭제  
  TRIM(문자열, 지정문자) : 왼쪽, 오른쪽에 지정된 문자를 삭제, 지정문자를 생략하면 공백을 삭제  
* 날짜형 함수
  SYSDATE : 오늘 날짜를 날짜형 타입으로 반환  
  EXTRACT('YEAR'|'MONTH'|'DAY' from d) : 날짜에서 년, 월, 일을 조회
* 숫자형 함수  
  ABS(숫자) : 절대값을 반환  
  SIGN(숫자) : 양수, 음수, 0 을 구별  
  MOD(숫자1, 숫자2) : 숫자1을 숫자2로 나눈 나머지를 계산, '%' 사용가능  
  CEIL(숫자) / CEILING(숫자) : 숫자 보다 크거나 같은 최소의 정수 반환  
  FLOOR(숫자) : 숫자 보다 작거나 같은 최대의 정수 반환  
  ROUND(숫자, m) : 소수점 m 자리에서 반올림한다. m의 기본값은 0  
  TRUNC(숫자, m) : 소수점 m 자리에서 절삭한다. m의 기본값은 0  
  
DECODE : IF 문 구현
```text
   DECODE(EMPNO, 1000, 'TRUE', 'FALSE') // EMPNO == 1000 이면 'TRUE' 아니면 'FALSE' 
```
CASE : IF ~ THEN ~ ELSE ~ END 방식의 조건문
````text
    CASE(expression)
      WHEN condition1 THEN resul1
      WHEN condition2 THEN resul2
      ...
      WHEN conditionN THEN resulN
      ELSE result
    END
```` 
ROWNUM : SELECT 문 결과에 논리적인 일련번호를 부여, 한 개의 행을 가지고 올 수 있고, 여러행을 가지고 올떄는 인라인 뷰를 사용해야 함
```text
   SELECT * FROM EMP
    WHERE ROWNUM <= 1;  // ROWNUM < 2 까지 가능
   
   SELECT * FROM (SELECT ROWNUM list, ENAME FROM EMP)   // FROM에 SELECT 사용하면 인라인 뷰라고 함
    WHERE list <= 5;
```
ROWID : Oracle 데이터베이스에서 데이터를 구분하는 유일한 값, 어떤 데이터 파일, 어떤 블록에 저장되어 있는지 알 수 있음  

WITH : 서브쿼리를 사용해서 임시 테이블이나 뷰 처럼 사용
```text
   WITH viewdata AS (
      SELECT * FROM EMP 
       UNION ALL
      SELECT * FROM EMP
   )  // subquery 를 사용해 임시 테이블을 만듬
   SELECT * FROM viewdata WHERE EMPNO = 1000; 
``` 

### DLC(Data Control Language)
GRANT : 데이터베이스 사용자에게 권한 부여  
```text
   GRANT privileges ON objct TO user
```
* Privileges(권한)  
  SELECT : 지정된 테이블에 SELECT 권한  
  INSERT : 지정된 테이블에 INSERT 권한  
  UPDATE : 지정된 테이블에 UPDATE 권한  
  DELETE : 지정된 테이블에 DELETE 권한  
  REFERENCES : 지정된 테이블을 참조하는 제약조건을 생성하는 권한  
  ALTER : 지정된 테이블에 대해 수정할 수 있는 권한  
  INDEX : 지정된 테이블에 대해 인덱스를 생성할 수 있는 권한  
  ALL : 지정된 테이블에 대한 모든 권한
* WITH GRANT OPTION : 특정 사용자에게 권한을 부여할 수 있는 권한을 부여
* WITH ADMIN OPTION : 테이블에 대한 모든 권한을 부여

REVOKE : 데이터베이스 사용자에게 부여된 권한 회수
```text
   REVOKE privileges ON objct TO user
```

### TCL(Transaction Control Language)
COMMIT : INSERT, UPDATE, DELETE로 변경된 데이터를 데이터베이스에 반영  
* 변경 전 이전의 데이터는 잃어버림
* COMMIT 이 완료되면 다른 사용자는 변경된 데이터를 볼 수 있음
* 변경으로 인한 LOCK 이 해제
* COMMIT 이 실행 되면 하나의 트랜잭션이 종료

ROLLBACK : 변경사용을 취소하고 트랜잭션을 종료
* INSERT, UPDATE, DELETE 작업 모두 취소
* 이전 COMMIT 까지 복구

SAVEPOINT : 트랜잭션을 작게 분할해서 관리, 지정된 위치까지 트랜잭션을 ROLLBACK 할 수 있음

## 2. SQL 활용
### 조인(join)
여러 개의 릴레이션을 사용해서 새로운 릴레이션을 만드는 과정  
조인의 가장 기본은 교집합을 만드는 것  
두 개의 테이블 간에 일치하는 것을 조인 한다  
EQUI JOIN(등가 조인) : 두 테이블에서 같은 칼럼을 사용하여 조인  
* EQUI JOIN : = 으로 테이블을 연결
```text
   SELECT * FROM EMP, DEPT
    WHERE EMP.DEPTNO = DEPT.DEPTNO;     // = 으로 테이블을 연결
``` 
* INNER JOIN : ON 구문으로 조인 조건을 넣음
```text
   SELECT * FROM EMP INNER JOIN DEPT
   ON EMP.DEPTNO = DEPT.DEPTNO;
```
* INTERSECT : 두 개의 테이블에서 교집합을 조회 - 공통된 값을 조회
```text
   SELECT * FROM EMP
   INTERSECT
   SELECT * FOROM DEPT;
```
NON-EQUI(비등가) 조인 : &gt;, &lt;, &gt;=, &lt;= 을 사용. 일치 하지 않는 것을 조인  

OUTER JOIN : 두 개의 테이블 간의 교집합을 조회하고, 한쪽 테이블에만 있는 데이텆도 포함시켜 조회  
* LEFT OUTER JOIN : 왼쪽 테이블에 있는 행도 포함
```text
   SELECT * FROM DEPT LEFT OUTER JOIN EMP
   ON EMP.DEPTNO = DEPT.DEPTNO;
```
* RIGHT OUTER JOIN : 오른쪽 테이블에만 있는 것을 포함
```text
   SELECT * FROM DEPT RIGHT OUTER JOIN EMP
   ON EMP.DEPTNO = DEPT.DEPTNO;
```
CROSS JOIN : 조인 조건 구 없이 2개 테이블을 하나로 조인, 카테시안 곱 발생
```text
   SELECT * FROM EMP CROSS JOIN DEPT;
```
합집합 : 두개의 테이블을 하나로 만듬. 테이블의 칼럼 수, 데이터 형식이 일치 해야 함.
* UNION : 중복된 데이터 제거, SORT 발생
* UNION ALL : 중복 제거하거나 정렬을 하지 않음

차집합 : 두개의 테이블에서 차집합을 조회, MS-SQL 에서는 EXCEPT
```text
   SELECT DEPTNO FROM DEPT      // 10, 20, 30, 40
   MINUS
   SELECT DEPTNO FROM EMP;      // 10, 20, 30 
   // DEPTNO 40만 조회
```
### 계층형 조회(Connect by)
계층형으로 데이터를 조회, Oracle 에서 지원

### 서브쿼리
SELECT문 내에 SELECT문을 사용하는 SQL  
```text
   SELECT * FROM EMP
    WHERE DEPTNO = 
         (SELECT DEPTNO FROM DEPT WHERE DEPTNO = 10);   // 서브쿼리
   
   SELECT * FROM (SELECT ROWNUM NUM, ENAME FROM EMP) A  // 인라인 뷰
    WHERE NUM < 5;
```
* 단일 행 서브쿼리 : 반환하는 행 수가 한 개 - 비교연산자 사용
* 다중 행 서브쿼리 : 여러 개의 행을 반환하는 것  
  IN(subquery) - subquery 의 결과 중 하나만 동일하면 참(OR)  
  ALL(subquery) - subquery 의 결과가 모두 동일하면 참  
  ANY(subquery) - subquery 의 결과 중 하나 이상 동일하면 참  
  EXISTS(subquery) - subquery 의 결과가 하나라도 존재하면 참  
* 스칼라 서브 쿼리 : 반드시 한 행과 한 칼럼만 반환하는 서브쿼리
```text
   SELECT ENAME, SAL, (SELECT AVG(SAL) FROM EMP) AS AVG // 스칼라 서브쿼리
     FROM EMP 
    WHERE EMPNO = 1000; 
```
* 연관 서브쿼리 : 서브쿼리 내에 메인 쿼리 내의 칼럼을 사용하는 것
```text
   SELECT * FROM EMP a
    WHERE a.DEPTNO = 
     (SELECT DEPTNO FROM DEPT
       WHERE b.DEPTNO = a.DEPTNO);
```

### 그룹 함수
* ROLLUP : GROUP BY 칼럼에 Subtotal 을 만듬, 칼럼이 두 개 이상이면 순서에 따라 값이 달라짐
```text
   SELECT DECODE(DEPTNO, NULL, '합계', DEPTNO), SUM(SAL)
    FROM EMP
    GROUP BY ROLLUP(DEPTNO); 
```
* GROUPING : ROLLUP, CUBE, GROUPING SET 에서 생성되는 합계를 구분, 그룹 함수 일 경우 1 반환
```text
   SELECT DEPTNO, GROUPING(DEPTNO), JOB, GROUPING(JOB), SUM
    FROM EMP GROUP BY ROLLUP(DEPTNO, JOB);
```
* GROUPING SET : GROUP BY에 나오는 칼럼의 순서에 관계없이 개별적으로 모두 처리
```text
   SELECT DEPTNO, JOB, SUM(SAL) FROM EMP
    GROUP BY GROUPING SET(DEPTNO, JOB);     // 순서가 바뀌어도 상관 없음
```
* CUBE : 제시된 칼럼에 대해서 모든 집계를 계산
```text
   SELECT DEPTNO, JOB, SUM(SAL) FROM EMP
    GROUP BY CUBE(DEPTNO, JOB);     // 전체합계, 부서별, 직업별, 부서별 / 직업별 합계 조회 
```
### 윈도우 함수
행과 행간의 관계를 정의하기 위해서 제공되는 함수  
순위, 합계, 평균, 행 위치  
```text
   SELECT window_function(arguments)
     OVER (partition by column
           ORDER BY WINDOWING 절)
   FROM table;
```
|WINDOWING 구조|설명|
|:---:|---|
|ROWS|부분집합인 윈도우 크기를 물리적 단위로 행의 집합을 지정|
|RANGE|논리적 주소에 의해 행 집합을 지정|
|BETWEEN ~ AND|윈도우의 시작과 끝 위치를 지정|
|UNBOUNDED PRECEDING|윈도우의 시작 위치가 첫 번쨰 행임을 의미|
|UNBOUNDED FOLLOWING|윈도우의 시작 위치가 마지막 행임을 의미|
|CURRENT ROW|윈도우의 시작 위치가 현재 행임을 의미|
```text
   SELECT EMPNO, ENAME, SAL,
     SUM(SAL) OVER (ORDER BY SAL
                    ROWS BETWEEN UNBOUNDED PRECEDING
                    AND UNBOUNDED FOLLOWING TOTSAL; // TOTSAL은 첫 번쨰 행부터 마지막 행까지
   SELECT EMPNO, ENAME, SAL,
     SUM(SAL) OVER (ORDER BY SAL
                    ROWS BETWEEN UNBOUNDED PRECEDING
                    AND CURRENT ROW TOTSAL; // TOTSAL은 첫 번쨰 행부터 현재 행까지
   SELECT EMPNO, ENAME, SAL,
     SUM(SAL) OVER (ORDER BY SAL
                    ROWS BETWEEN CURRENT ROW
                    AND UNBOUNDED FOLLOWING TOTSAL; // TOTSAL은 현재행 부터 마지막 행까지
```
* 순위 함수  
  RANK : 동일한 순위는 동일한 값 부여  
  DENSE_RANK : 동일한 순위를 하나의 건수로  
  ROW_NUMBER : 동일한 순위에 고유 순위를 부여  
* 집계 함수  
  SUM : 파티션별 합  
  AVG : 파티션별 평균  
  COUNT : 파티션별 행 수  
  MAX / MIN : 파티션별 최대 / 최소 값  
* 행 순서관련 함수  
  FIRST_VALUE : 가장 처음에 나오는 값, MIN 함수  
  LAST_VALUE : 가장 나중에 나오는 값, MAX 함수  
  LAG : 이전 행을 가지고 옴  
  LEAD : 특정 위치의 행을 가지고 옴. 기본 값은 1
* 비율 관련 함수  
  CUME_DIST : 파티션 전체의 건수에서 현재 보다 작거나 같은 건수에 대한 누적 백분율  
  PERCENT_RANK : 행의 순서별 백분율 (0 ~ 1)  
  NTILE : 파티션별 전체 건수를 argument 값으로 n 등분한 결과  
  RATIO_TO_REPORT : 파티션 내의 전체 SUM에 대한 행 별 칼럼 값의 백분율  

### 테이블 파티션
대용량의 테이블을 여러개의 데이터 파일에 분리해서 저장  
성능 향상, 독립적 관리, 전용 인덱스 
* Range partition : 값의 범위를 기준으로 분할  
* List partition : 특정 값을 기준으로 분할  
* Hash partition : 해시 함수를 사용해서 분할 - DBMS에서 관리  
* 파티션 인덱스  
  Global index : 여러 개의 파티션에서 하나의 인덱스 사용 (Oracle 지원 안함)  
  Local index : 해당 파티션 별로 각자의 인덱스 사용  
  Prefixed index : 파티션 키와 인덱스 키가 동일  
  Non Prefixed index : 파티션 키와 인덱스 키가 다름 (Oracle 지원 안함)

## 3. SQL 최적화의 원리
### 옵티마이저와 실행계획
옵티마이저 : SQL의 실행 계획을 수립하고 SQL을 실행하는 DBMS의 소프트웨어  
데이터 딕셔너리에 있는 오브젝트 통계, 시스템 통계등의 정보를 사용해서 비용 산정  
SQL을 어떻게 실행하느냐에 따라서 성능이 달라짐 
실행계획 : PLAN_TABLE에 저장  
### 옵티마이저의 종류
실행방법 
1. Parsing : SQL 문법 검사, 구문주석
2. 실행계획 수립 : 규칙 기반, 비용 기반
3. SQL 실행 -> Fetch
* 규칙 기반 옵티마이저 : 15개의 우선순위
* 비용 기반 옵티마이저 : 오브젝트 통계 및 시스템 통계로 총 비용이 적은 쪽 선택
### 인덱스(Index)
데이터를 빠르게 검색할 수 있는 방법 제공  
인덱스 키로 정렬되어 있기 떄문에 빠르게 조회 가능  
인덱스 생성 : CREATE INDEX, 기본적으로 오름차순(ASC)  
인덱스 스캔
* 인덱스 유일 스캔 : 인덱스 키 값이 중복되지 않는 경우
* 인덱스 범위 스캔 : SELECT 문에서 특정 범위를 조회하는 WHERE 문을 사용할 경우
* 인덱스 전체 스캔 : 검색되는 인덱스 키가 많은 경우
### 실행계획(Execution Plan)
번호 순서대로 읽으면 됨.
### 옵티마이저 조인
* Nested Loop 조인 : 하나의 테이블에서 데이터를 먼저 찾고 그다음 테이블을 조인하는 방식  
* Sort Merge 조인 : 두 개의 테이블을 SORT_AREA라는 메모리 공간에 모두 로딩하고 SORT를 수행  
* Hash 조인 : 두 개의 테이블 중 작은 테이블을 Hash 메모리에 로딩하고 두개의 테이블의 조인키를 사용해서 해시 테이블을 생성
  