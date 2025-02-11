## Отчётный документ по итогам тестирования.

### Краткое описание

Проведена автоматизация тестирования web-сервиса, который взаимодействует с СУБД и API банка, при покупке тура двумя способами оплаты.

Тестировались два способа оплаты тура с валидацией полей формы:
1. Обычная оплата по дебетовой карте.
2. Покупка в кредит по данным банковской карты.

Были протестированы сценарии:

1. Успешная покупка с дебетовой и кредитной карты
2. Отказ банка с дебетовой и кредитной картой
3. Негативные проверки полей: невалидные значения, незаполненные поля

Заявлена поддержка двух СУБД:
* MySQL
* PostgreSQL

### Количество тест-кейсов

Проведено по 56 автоматизированных тестов для каждой БД.

### Итоги тестирования:

* 40 успешных (71.42 %)
* 16 не успешных (28.57 %)

#### Подготовлены отчёты:

* [Отчёт Gradle](https://github.com/sputNIK2009PT/QA-Diplom/blob/5cb9dcca0cbec7116b52ba04ef6f9aacd395c962/reports/gradle%20reports.png)
* [Отчёт Allure](https://github.com/sputNIK2009PT/QA-Diplom/blob/5cb9dcca0cbec7116b52ba04ef6f9aacd395c962/reports/allure%20reports.png)
* [Отчёт Allure покупка в кредит](https://github.com/sputNIK2009PT/QA-Diplom/blob/5cb9dcca0cbec7116b52ba04ef6f9aacd395c962/reports/allure%20reports%20credit.png)
* [Отчёт Allure купить](https://github.com/sputNIK2009PT/QA-Diplom/blob/5cb9dcca0cbec7116b52ba04ef6f9aacd395c962/reports/allure%20reports%20payment.png)

### Общие рекомендации
* Исправить баги, указаные в [issue](https://github.com/sputNIK2009PT/QA-Diplom/issues);
