# Calculator
Применялись: Dagger, RxJava, Retrofit, Room
Модуль CalculatorsList сделан по Clean Architecture
Модуль Calculator - колхозный говнокод

Есть список калькуляторов, в каждом из которых можно изменить вычисляемое выражение и он будет в верху списка. Можно создать новый по кнопке снизу и дать ему название. Дублирование названий не учитывается. Изменять названия нельзя.  Калькулятор открывается по нажатию на элемент списка. Выражения в калькуляторе считаются с помощью польской нотации. Есть косяки по SOLID.
В первый верхний элемент списка приходит погода по Краснодару с https://api.openweathermap.org/
