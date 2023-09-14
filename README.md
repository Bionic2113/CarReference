**Тестовое задание “Справочник автомобилей”**

Реализован следующий функционал:

- Минимальные требования
- Сортировка (возможность передавать разный набор полей таблицы)
- Docker-файл для развертывания сервиса (в папке Docker находятся два файла: основной и упрощенный. Упрощенный файл может быть использован, если возникают проблемы с основным файлом, например, если не установлена JDK или что-то еще)
- Unit-тесты
- GraphQL для API

Дополнительные поля включают ссылку на статистику по номеру от avtocod и ссылку на поиск машины на avtoru в Москве по марке, модели и году.

Методы в коде прокомментированы.

Статистика была реализована с использованием парсинга данных с [avtocod.ru](https://avtocod.ru). Данные обрабатывались и сравнивались, а затем возвращались пользователю. Однако возникла проблема, заключающаяся в том, что сайт не позволяет выполнять более 3 запросов, после чего начинает выдавать ошибку 429. Для тестирования была создана "заглушка".
