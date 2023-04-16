# Тестовое задание AirportsSearch 

## Запуск

#### 1. Сборка проекта через maven

```bash
mvn clean package
```

#### 2. Запуск приложения

```bash
java -Xmx7m -jar target/airports-search-1.0.jar
```

#### 3. Введите фильтр

```bash
(column[1]>3500&(column[12]=null||column[12]='Europe/Berlin'))||column[6]='LECO'
```

#### 4. Введите начало имени аэропорта

#### 5. Введите `!quit` для выхода из программы

## Реализация

Строки `airports.csv` фильтруются с помощью `SpelExpressionParser` из библиотеки `org.springframework.expression`.\
Для хранения отфильтрованных строк используется `TreeMultimap` из библиотеки `Guava`\

## Замеры скорости поиска

Замеры выполнены на процессоре `Intel(R) Core(TM) i5-10400F CPU @ 2.90GHz`

* Поиск по `Bo`: ~15ms
* Поиск по `Bower`: ~10ms