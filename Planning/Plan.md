# План автоматизации тестирования приложения "Путешествие дня"
**Цель**: автоматизировать сценарии покупки тура через приложение (позитивные/негативные).
## 1. Перечень автоматизируемых сценариев
###   1.1 Позитивные сценарии
###   Позитивный сценарий №1
1.	Открыть страницу http://localhost:9999/;
2.	Нажать кнопку "Купить";
3.	Ввести номер карты со статусом APPROVED: 4444 4444 4444 4441;
4.	Остальные поля заполнить корректными значениями;
5.	Нажать кнопку "Продолжить";
6.	Появляется сообщение "Успешно. Операция одобрена Банком."
###      Позитивный сценарий №2
1.	Открыть страницу http://localhost:9999/;
2.	Нажать кнопку "Купить в кредит";
3.	Ввести номер карты со статусом APPROVED: 4444 4444 4444 4441;
4.	Остальные поля заполнить корректными значениями;
5.	Нажать кнопку "Продолжить";
6.	Появляется сообщение "Успешно. Операция одобрена Банком."
###      Позитивный сценарий №3
1.	Открыть страницу http://localhost:9999/;
2.	Нажать кнопку "Купить";
3.	Ввести номер карты со статусом DECLINED: 4444 4444 4444 4442;
4.	Остальные поля заполнить корректными значениями;
5.	Нажать кнопку "Продолжить";
6.	Появляется сообщение "Ошибка! Банк отказал в проведении операции."
###      Позитивный сценарий №4
1.	Открыть страницу http://localhost:9999/;
2.	Нажать кнопку "Купить в кредит";
3.	Ввести номер карты со статусом DECLINED: 4444 4444 4444 4442;
4.	Остальные поля заполнить корректными значениями;
5.	Нажать кнопку "Продолжить";
6.	Появляется сообщение "Ошибка! Банк отказал в проведении операции."
## 2. **Тестирование полей формы**
   ### 2.1 Тестовые данные для позитивных тестовых сценариев
   **Корректные тестовые данные:**
1.	**Поле "Номер карты":**
      *	Цифры;
      *	Длина значения 16 цифр.
2.	Поле "Месяц":
      *	Цифры;
      *	Диапазон значений от 01 до 12.
3.	**Поле "Год":**
      *	Цифры;
      *	Диапазон значений от 25 до 30.
4.	**Поле "Владелец":**
      *	Латиница (Alexander Stupnikov);
      *	Длина значения от 3 до 64 символов;
      *	Допустимые спецсимволы: пробел, дефис.
5.	**Поле "CVV":**
      *	Цифры;
      *	Диапазон значений от 001 до 999.
###      2.2 Тестовые данные для негативных сценариев
   **Некорректные тестовые данные:**
1.	**Поле "Номер карты"**:
      * Поле пустое;
      * Длина значения менее 16 цифр;
      *	Номера карты нет в БД банка.
2.	**Поле "Месяц"**:
      *	Поле пустое;
      *	Состоит из одной цифры;
      *	Значение больше 12;
      *	Значение равно 00.
3.	**Поле "Год"**:
      *	Поле пустое;
      *	Состоит из одной цифры;
      *	Значение меньше текущего года (менее 25);
      *	Значение на 5 лет и более, больше текущего года (более 30);
      *	Значение равно 00.
4.	**Поле "Владелец"**:
      *	Поле пустое;
      *	Значение состоит из одного слова;
      *	Поле содержит кириллицу;
      *	Поле содержит цифры;
      *	Поле содержит спецсимволы кроме допустимых (пробел, дефис).
      * Только пробелы или дефисы
5.	**Поле "CVV"**:
      *	Поле пустое;
      *	Состоит из одной цифры;
      *	Состоит из двух цифр.
      * Значение равно 000
## 3. Перечень используемых инструментов с обоснованием выбора
1.	**IntelliJ IDE** - Мощная интегрированная среда разработки, предлагающая бесплатное использование, отличную интеграцию с GitHub и широкую поддержку расширений и плагинов для тестирования.
2.	**Git** - Система контроля версий, предоставляющая бесплатное использование, возможность параллельной разработки и хорошую интеграцию с IntelliJ IDEA.
3.	**JUnit5** - Тестовый фреймворк, совместимый с JVM и IntelliJ IDEA, включающий все необходимые аннотации для тестирования.
4.	**Gradle** - Система сборки проектов, отличающаяся простотой и понятностью кода, меньшим объемом по сравнению с Maven и упрощенным подключением внешних зависимостей.
5.	**Lombok** - Плагин для создания аннотаций, заменяющих значительное количество однообразных конструкторов Java, таких как getters, setters и другие.
6.	**Selenide** - Фреймворк для автоматизированного тестирования веб-приложений на основе Selenium WebDriver, обеспечивающий автоматическое подключение веб-драйвера и простоту написания кода тестов.
7.	**JavaFaker** - Плагин для генерации случайных данных для тестов, предлагающий большое количество настроек, бесплатное использование и достаточную локализацию для России.
8.	**Docker** - Система контейнеризации, используемая для имитации работы IT-системы банка посредством развёртывания баз данных MySQL, PostgreSQL и запуска самого приложения через Node.js.
9.	**Appveyor** - Система непрерывной интеграции (CI), обеспечивающая непрерывный контроль интеграции кода, бесплатное использование, простое подключение и настройку, а также удобную интеграцию с GitHub.
10.	**Allure Report** - Система подготовки отчетов, предлагающая бесплатное решение, хорошую информативную визуализацию отчетов и возможность отслеживания данных на протяжении времени.
11.	**Java 11** - Многофункциональный язык программирования, обеспечивающий совместимость с большинством операционных систем и оборудования.
## 4. Перечень и описание возможных рисков при автоматизации:
1.	Трудности с запуском и настройкой SUT.
2.	Проблемы с настройкой автотестов из-за отсутствия уникальных CSS-селекторов в приложении.
3.	Изменение структуры рабочего сайта (корректировка дизайна с изменением HTML и CSS) в будущем может привести к неработоспособности текущих автотестов.
## 5. Интервальная оценка с учётом рисков (в часах)
1.	Планирование автоматизации тестирования - 4-6 часов.
2.	Написание кода тестов - 40-50 часов.
3.	Подготовка отчётных документов по итогам автоматизированного тестирования - 12-18 часов.
4.	Подготовка отчётных документов по итогам автоматизации - 6-8 часов.
## 6. План сдачи Дипломной работы
1.	Завершение разработки автоматизированных тестов — в течение 10 рабочих дней после утверждения плана тестирования.
2.	Подготовка отчетов по результатам выполнения тестов — в течение 2 рабочих дней после завершения тестирования.
3.	Подготовка итогового отчета по автоматизации — в течение 2 рабочих дней после подготовки отчетов по результатам тестирования.
