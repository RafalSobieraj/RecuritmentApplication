# RecuritmentApplication

Programista: Rafał Sobieraj

Zadanie wykonałem w formie strony internetowej skupiając się na wykonaniu przedstawionych punktów w zadaniu.

Aby uruchomić aplikację należy uruchomić klasę Main.java, odpowiadającą za start całego kodu.
Wewnątrz model/article znajduje się klasa Article, zawierająca typy danych tworzące artykuł, które są zapisywane do bazy danych. 
Każdy typ danych posiada następujące reguły walidacji: każda ma swoją nazwę oraz żadna nie może zawierać ,,null".
W tym samym folderze znajdują się klasy odpowiadające za działanie aplikacji: klasa Controller, zawierająca dane requesty i mappingi,
Repository, zawierającą API wspomagającą m.in. sortowanie, oraz Service, która wspomaga łatwą edycję elementów w bazie danych.
Wewnątrz klasy Controller, każda metoda zawiera dany mapping, które są połączone z danymi w HTML, które się znajdują w resources/templates,
i na ich podstawie przesyła dane i przekierowuje na dany endpoint. Np. metoda ,,search" bierze parametr ,,keyword", który został wpisany przez
uzytkownika w pasku wyszukiwania, który jest zadeklarowany w ,,index.html", następnie sprawdza poprzez metodę ,,search" w ArticleService czy dane słowo kluczowe
istnieje w tytule lub treści publikacji i następnie przekierowuje na ,,result_search.html" i wyświetla wyniki.
