# Java Project

콘솔 기반 헬스 관리 Java 애플리케이션입니다. 사용자 프로필을 만들고, 유산소/무산소 운동 기록을 추가한 뒤 운동 시간, 칼로리, 운동 강도 등을 확인할 수 있습니다.

## 주요 기능

- 사용자 프로필 생성, 선택, 수정
- 유산소 운동 기록 추가: 달리기, 사이클, 걷기
- 무산소 운동 기록 추가: 데드리프트, 벤치프레스, 푸쉬업
- 운동별 칼로리 계산
- 전체 운동 기록 및 통계 확인
- 운동 강도 분석

## 프로젝트 구조

```text
src/
  HealthApp/
    Main.java          # 프로그램 실행 진입점
    HealthApp.java     # 메뉴와 주요 앱 흐름
    UserProfile.java   # 사용자 프로필과 운동 기록 관리
    Workout.java       # 운동 공통 추상 클래스
  CardioWorkout/       # 유산소 운동 클래스
  StrengthWorkout/     # 무산소 운동 클래스
```

## 실행 방법

### Eclipse에서 실행

1. Eclipse에서 `File > Import > Existing Projects into Workspace`를 선택합니다.
2. 프로젝트 폴더를 선택합니다.
3. `src/HealthApp/Main.java`를 실행합니다.

### 터미널에서 실행

PowerShell 기준:

```powershell
javac -encoding UTF-8 -d bin src\HealthApp\*.java src\CardioWorkout\*.java src\StrengthWorkout\*.java
java -cp bin HealthApp.Main
```

## 협업 규칙

팀 작업은 `main` 브랜치에 바로 커밋하지 않고, 각자 작업 브랜치를 만들어 진행합니다.

```powershell
git switch main
git pull
git switch -c feature/작업-이름
```

작업 후에는 다음 순서로 올립니다.

```powershell
git status
git add .
git commit -m "작업 내용 요약"
git push -u origin feature/작업-이름
```

GitHub에서 Pull Request를 만들고, 팀원 확인 후 `main`에 병합합니다.

## 브랜치 이름 예시

- `feature/profile-edit`
- `feature/workout-log`
- `fix/calorie-calculation`
- `docs/update-readme`

## 커밋 메시지 예시

- `Add user profile selection`
- `Fix running calorie calculation`
- `Update README run instructions`

## 작업 전 확인 사항

- 작업 시작 전 항상 `main`에서 `git pull`을 실행합니다.
- 본인이 수정한 파일만 커밋합니다.
- `bin/`, `*.class` 같은 컴파일 결과물은 커밋하지 않습니다.
- 실행이 되는지 확인한 뒤 Pull Request를 올립니다.
