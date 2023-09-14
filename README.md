**Тестовое задание “Справочник автомобилей”**

Реализован следующий функционал:

- [x] Минимальные требования
- [x] Сортировка (возможность передавать разный набор полей таблицы)
- [x] Docker-файл для развертывания сервиса
   > в папке Docker находятся два файла: основной и упрощенный. Упрощенный файл может быть использован, если возникают проблемы с основным файлом, например, если не установлена JDK или что-то еще
- [x] Unit-тесты
- [x] GraphQL для API

Дополнительные поля включают ссылку на статистику по номеру от avtocod и ссылку на поиск машины на avtoru в Москве по марке, модели и году.

Методы в коде прокомментированы.

Статистика была реализована с использованием парсинга данных с [avtocod.ru](https://avtocod.ru). Данные обрабатывались и сравнивались, а затем возвращались пользователю. Однако возникла проблема, заключающаяся в том, что сайт не позволяет выполнять более 3 запросов, после чего начинает выдавать ошибку 429. Для тестирования была создана "заглушка".

Примеры запросов:

<img width="431" alt="request example 4" src="https://github.com/Bionic2113/CarReference/assets/111612573/2a781b99-e7d4-4f5e-8377-a1ada1d45796">
<img width="659" alt="request example 5" src="https://github.com/Bionic2113/CarReference/assets/111612573/a2b6f48f-eb09-4f54-b237-4c152f46b5b6">
<img width="353" alt="request example 3" src="https://github.com/Bionic2113/CarReference/assets/111612573/6da7d70f-0871-4df4-b48e-c7277669dc3c">
<img width="599" alt="request example 2" src="https://github.com/Bionic2113/CarReference/assets/111612573/11bd816e-7404-4e52-99b2-ea663a8a6dde">
<img width="624" alt="request example 1" src="https://github.com/Bionic2113/CarReference/assets/111612573/24d85089-4e47-4574-b5f2-97daeedc983e">

Примеры GrapQL:
<img width="1431" alt="request example 8" src="https://github.com/Bionic2113/CarReference/assets/111612573/89951d1e-9146-4da0-84ce-7aae011b5415">
<img width="1436" alt="request example 7" src="https://github.com/Bionic2113/CarReference/assets/111612573/3cfc6a83-ef16-42b4-940e-7d7c004e3eed">
<img width="1431" alt="request example 6" src="https://github.com/Bionic2113/CarReference/assets/111612573/05775434-3e98-4534-ba78-47d00820b28d">



> P.S.
> Куки в коде от левого аккаунта и апи гугл переводчика найти легко:)


