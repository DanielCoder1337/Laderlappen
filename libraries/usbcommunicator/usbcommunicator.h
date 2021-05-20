#ifndef USBCOMMUNICATOR_H
#define USBCOMMUNICATOR_H
#include <arduino.h>
#include<SoftwareSerial.h>
#include<ArduinoQueue.h>

#define STANDARD_BAUD 9600

#define END_INDICATOR_SEND '>'

#define END_INDICATOR_READUNTIL '>'
#define END_INDICATOR_READCOMPLETE '<'


#define STANDARD_MESSAGE "00011024" 
#define COMPLETED_INDEX 0
#define REST_INDEX 1

#define MAX_QUEUE 50
#define MAX_SIZE 0

class Usbcommunicator{
    private:
      uint32_t _baudrate;
      uint8_t _messageQueSize;
      String _completedMessage = "";
      String _currentMessage = "";
      ArduinoQueue<String> * _messageQue;
      HardwareSerial* _serial;    
    public:
        Usbcommunicator(HardwareSerial& serial, uint8_t messageQueSize = MAX_QUEUE)
          : _messageQueSize(messageQueSize){
             _messageQue = new ArduinoQueue<String>(MAX_QUEUE);
             _serial = &serial;
             }
        
        ~Usbcommunicator(){delete[] _messageQue;}
        
        void begin(uint32_t baud = STANDARD_BAUD);
        int getSizeOfQue();
        void readInSlice();
        bool read();
        void readUntil();
        String tryGetMessage();
        String readGetTry();
        String readGetUntil();
        void send(String message = STANDARD_MESSAGE);
};

#endif // USBCOMMUNICATOR_H