# ToDo Android App (Оценка 4)

**Какво е в проекта**
- Минимално Android Studio проект (Kotlin)
- 2 Activity-та: MainActivity (списък) и AddActivity (добавяне)
- Навигация чрез Intent
- Material components UI
- Room база (local SQLite)
- Create + Read операции (запазване и показване)
- README и структура

**Как работи**
1. Стартираш приложението — виждаш списък с добавени задачи.
2. Натискаш плаващия бутон → отваря се AddActivity.
3. Вписваш заглавие и натискаш "Запази" → новата задача се записва в Room и се вижда в списъка.

**Архитектура**
- Много опростен MV (без сложни слоеве): Activities → Room (DAO / Database).
- AppDatabase.getInstance() предоставя достъп до базата от приложението.

**Потребителски поток**
MainActivity -> (btn Add) -> AddActivity -> Save -> MainActivity (list обновен)

**Стъпки за стартиране**
1. Разархивирай `android_todo_full.zip`.
2. Отвори Android Studio -> File -> Open -> избери папката `android_todo_full`.
3. Изчакай Gradle sync.
4. Run на емулатор или устройство (minSdk 21).

**Тестови акаунти**
Няма акаунти — локално приложение.

**APK**
Не е включен генериран APK в този архив. Мога да го добавя при поискване.

**Снимки**
(Добави скрийншотове тук ако искаш)

