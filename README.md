<!-- Improved compatibility of back to top link: See: https://github.com/othneildrew/Best-README-Template/pull/73 -->
<a name="readme-top"></a>
<!--
*** Thanks for checking out the Best-README-Template. If you have a suggestion
*** that would make this better, please fork the repo and create a pull request
*** or simply open an issue with the tag "enhancement".
*** Don't forget to give the project a star!
*** Thanks again! Now go create something AMAZING! :D
-->



<!-- PROJECT SHIELDS -->
<!--
*** I'm using markdown "reference style" links for readability.
*** Reference links are enclosed in brackets [ ] instead of parentheses ( ).
*** See the bottom of this document for the declaration of the reference variables
*** for contributors-url, forks-url, etc. This is an optional, concise syntax you may use.
*** https://www.markdownguide.org/basic-syntax/#reference-style-links
-->

<!-- PROJECT LOGO -->
<br />
<div align="center">
  <a href="https://github.com/github_username/repo_name">
    <img src="https://www.vhv.rs/dpng/d/286-2868787_baby-box-university-logo-clipart-png-download-graduation.png" alt="Logo" width="80" height="80">
  </a>

<h3 align="center">MyUniversity</h3>

  <p align="center">
    This is an application for storing Students, Teachers and Courses information like name, age, group and courses. We have two type of courses -Main and Secondary.
The application should be able to add remove or modify students and teachers. With this application we should be able to create different reports.
  </p>
</div>



## Built With

* [![Hibernate][Hibernate]][Hibernate-url]
* [![Intellij][Intellij]][Intellij-url]
* [![Spring][Spring]][Spring-url]
* [![Java][Java]][Java-url]
* [![Maven][Maven]][Maven-url]
* [![Sonar][Sonar]][Sonar-url]
* [![Git][Git]][Git-url]


## Hot to use it

1. Get a free API Platform at [https://www.postman.com](https://www.postman.com)
2. Clone the repo
   ```sh
   git clone https://github.com/vasilev02/myuniversity
   ```

## Usage of Course

<details>
  <summary>Details</summary>

1. You can get all courses
```sh
http://localhost:8080/courses
```
2. You can get course with ID
```sh
http://localhost:8080/courses/1
```
3. You can get all courses by type
```sh
http://localhost:8080/courses/filterByType?type=MAIN
```
4. You can create course with automatic attached ID
```sh
http://localhost:8080/courses
```
5. You can update a course when you pass object with ID
```sh
http://localhost:8080/courses/update
```

Course is simple object which can be created, updated, deleted and filter by type.

_For more examples and information, please refer to the Course Controller [here](https://github.com/vasilev02/myuniversity/blob/main/src/main/java/com/lead/consult/interview/controller/CourseController.java)_

</details>

## Usage of Student

<details>
  <summary>Details</summary>

1. You can get all students
```sh
http://localhost:8080/students
```
2. You can get student with ID
```sh
http://localhost:8080/students/1
```
3. You can get all students by groupName
```sh
http://localhost:8080/students/getByGroupName?groupName=QA
```
4. You can create student with automatic attached ID
```sh
http://localhost:8080/students
```
5. You can update a student when you pass object with ID
```sh
http://localhost:8080/students/update
```
6. You can get all students by courseName
```sh
http://localhost:8080/students/getByCourseName?courseName=Math
```
7. You can get all students by courseName and groupName
```sh
http://localhost:8080/students/getByCourseAndGroupName
```
8. You can get all students by courseName and age
```sh
http://localhost:8080/students/getByCourseAndAge
```
9. You can attach student a course with provided student ID and provided object of course
```sh
http://localhost:8080/students/addCourse/{id}
```
10. You can unattach student a course with provided student ID and provided object of course
```sh
http://localhost:8080/students/removeCourse/{id}
```

Student is simple object which can be created, updated, deleted and filter by courseName, group and etc.

_For more examples and information, please refer to the Student Controller [here](https://github.com/vasilev02/myuniversity/blob/main/src/main/java/com/lead/consult/interview/controller/StudentController.java)_


</details>

## Usage of Teacher

<details>
  <summary>Click me</summary>

1. You can get all teachers
```sh
http://localhost:8080/teachers
```
2. You can get teacher with ID
```sh
http://localhost:8080/teachers/1
```
3. You can get all teachers by groupName
```sh
http://localhost:8080/teachers/getByGroupName?groupName=QA
```
4. You can create teacher with automatic attached ID
```sh
http://localhost:8080/teachers
```
5. You can update a teacher when you pass object with ID
```sh
http://localhost:8080/teachers/update
```
6. You can get all teachers by courseName
```sh
http://localhost:8080/teachers/getByCourseName?courseName=Math
```
7. You can get all teachers by courseName and groupName
```sh
http://localhost:8080/teachers/getByCourseAndGroupName
```
8. You can get all teachers by courseName and age
```sh
http://localhost:8080/teachers/getByCourseAndAge
```
9. You can attach teacher a course with provided student ID and provided object of course
```sh
http://localhost:8080/teachers/addCourse/{id}
```
10. You can unattach teacher a course with provided student ID and provided object of course
```sh
http://localhost:8080/teachers/removeCourse/{id}
```

Teacher is simple object which can be created, updated, deleted and filter by courseName, group and etc.

_For more examples and information, please refer to the Teacher Controller [here](https://github.com/vasilev02/myuniversity/blob/main/src/main/java/com/lead/consult/interview/controller/TeacherController.java)_

</details>

<!-- CONTACT -->
## Contact

Your Name - Valentin Vasilev - valentin.vas.vasilev@gmail.com

[![LinkedIn][linkedin-shield]][linkedin-url]

Project Link: [https://github.com/vasilev02/myuniversity](https://github.com/vasilev02/myuniversity)


<p align="right">(<a href="#readme-top">back to top</a>)</p>



<!-- MARKDOWN LINKS & IMAGES -->
<!-- https://www.markdownguide.org/basic-syntax/#reference-style-links -->
[contributors-shield]: https://img.shields.io/github/contributors/github_username/repo_name.svg?style=for-the-badge
[contributors-url]: https://github.com/github_username/repo_name/graphs/contributors
[forks-shield]: https://img.shields.io/github/forks/github_username/repo_name.svg?style=for-the-badge
[forks-url]: https://github.com/github_username/repo_name/network/members
[stars-shield]: https://img.shields.io/github/stars/github_username/repo_name.svg?style=for-the-badge
[stars-url]: https://github.com/github_username/repo_name/stargazers
[issues-shield]: https://img.shields.io/github/issues/github_username/repo_name.svg?style=for-the-badge
[issues-url]: https://github.com/github_username/repo_name/issues
[license-shield]: https://img.shields.io/github/license/github_username/repo_name.svg?style=for-the-badge
[license-url]: https://github.com/github_username/repo_name/blob/master/LICENSE.txt
[linkedin-shield]: https://img.shields.io/badge/-LinkedIn-black.svg?style=for-the-badge&logo=linkedin&colorB=555
[linkedin-url]: https://www.linkedin.com/in/valentin-vasilev-849a8b1a6/
[product-screenshot]: images/screenshot.png
[Hibernate]: https://img.shields.io/badge/Hibernate-59666C?style=for-the-badge&logo=Hibernate&logoColor=white
[Hibernate-url]: https://hibernate.org
[Intellij]: https://img.shields.io/badge/IntelliJ_IDEA-000000.svg?style=for-the-badge&logo=intellij-idea&logoColor=white
[Intellij-url]: https://www.jetbrains.com/idea/
[Spring]: https://img.shields.io/badge/Spring-6DB33F?style=for-the-badge&logo=spring&logoColor=white
[Spring-url]: https://spring.io
[Java]: https://img.shields.io/badge/OpenJDK-ED8B00?style=for-the-badge&logo=java&logoColor=white
[Java-url]: https://openjdk.org
[Maven]: https://img.shields.io/badge/apache_maven-C71A36?style=for-the-badge&logo=apachemaven&logoColor=white
[Maven-url]: https://maven.apache.org
[Sonar]: https://img.shields.io/badge/Sonarqube-5190cf?style=for-the-badge&logo=sonarqube&logoColor=white
[Sonar-url]: https://www.jetbrains.com/qodana/?source=google&medium=cpc&campaign=19640357518&term=sonarqube&content=646717405636&gclid=Cj0KCQjw6KunBhDxARIsAKFUGs8hXIrh0cjYajUIehst74tV2S1JTXCm2uXbUT67mpZGRGC1Yoe0z9IaAkiTEALw_wcB
[Git]: https://img.shields.io/badge/GIT-E44C30?style=for-the-badge&logo=git&logoColor=white
[Git-url]: https://git-scm.com