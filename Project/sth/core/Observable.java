package sth.core;

interface Observable {

    void sendNotifications(Notification notification);
    void subscribe(Observer observer);
    void unsubscribe(Observer observer);
}
