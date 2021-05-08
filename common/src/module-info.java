/**
 * Created by User on 07.05.2021.
 */
module common {
    requires java.desktop; // Если есть работа с изображениями и мы работаем с модулями, то надо подключить таким образом java.desktop
    exports com.ifmo.lib; // Экспортирует файлы из указанного пакета
    exports com.ifmo.lib.handlers;
}